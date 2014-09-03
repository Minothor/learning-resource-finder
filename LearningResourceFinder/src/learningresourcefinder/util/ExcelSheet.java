package learningresourcefinder.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @author Nicholas Boyd
 * @author Laurent Demey
 * 
 * @since 29/08/2014
 * @version 1.0
 * 
 * Sweetener class for handling Excel xlsx files loaded via the Apache POI library.
 * Assumes first row to be title columns and implements Regular Expression matching for title searching.  
 *
 */
public class ExcelSheet {
    
	Log log = LogFactory.getLog(ExcelSheet.class);
	
    String path;
    File target;
    Workbook sheet;
    List<String> keys;
    

    /**
     * Instantiates an ExcelSheet Object from a given Path.
     * N.B. the path will be translated into a file stream in the loadExcel method.
     * @param pathName
     */
    public ExcelSheet(String pathName) {
        if(pathName==null || pathName.isEmpty()|| !verifyExt(pathName))
            throw new IllegalArgumentException("Path Null or Empty, The string should resolve to a local .xlsx file.");
        sheet = loadExcel(pathName);
        ParseKeys();
        log.debug("\n---\n");
        for (String key : getKeys()) {
            
            System.out.print("|"+key+"|");
            
        }
        log.debug("\n---\n");
    }
    
    /**
     * Instantiates an ExcelSheet Object from a given FileStream
     * @param fileStream
     */
	public ExcelSheet(InputStream fileStream) {
        if(fileStream==null)
            throw new IllegalArgumentException("Input Stream Null or Empty, The string should resolve to a streamed .xlsx file.");
        sheet = loadExcel(fileStream);
        ParseKeys();
        log.debug("\n---\n");
        for (String key : getKeys()) {
            
            System.out.print("|"+key+"|");
            
        }
        log.debug("\n---\n");
    }
    
    /**
     * Function implements RegEx matching to return matches of whole or partial column names (e.g. "titre" will match to "Titre Proposé") 
     * 
     * @param searchKey whole or partial key word
     * @return the index (0->n) of that column key 
     */
	public int findKey(String searchKey) {
        for (String key : keys) {
            if (key.toLowerCase(Locale.US).matches(".*"+searchKey+"+.*"))
                return keys.indexOf(key);
        }
        return -1;
    }
    
    /**
     * 
     * @param row index of the row to be returned
     * @return An array of String values for the cells in the retrieved row.
     */
	public String[] getRow(int row) {
		return ParseRow(row);
	}    
    
    /**
	 * Caution! It recommended not to use this function, due to blank lines and
	 * spaces, it does not reliably retrieve the correct value.
	 * 
	 * @return the total number of rows in the spreadsheet.
	 */
	public int numRows() {
		return currentSheet().getLastRowNum() - 1;
	}

    /**
     * Gets the current sheet from the xlsx file.
     * @return the sheet from the document's last active tab.
     */
    private Sheet currentSheet() {
        Sheet currentSheet = sheet.getSheetAt(sheet.getActiveSheetIndex());
        return currentSheet;
    }
    
    /**
     * Caution! It recommended not to use this function, due to blank lines and spaces, it does not reliably retrieve the correct value for the last row.
     * @see numRows()
     * 
     * @param key whole or partial name of the column.
     * @return Array of String values corresponding to the values in that column.
     */
    public String[] getColumn(String key)  {
        Sheet currentSheet = currentSheet();
        int rowNum=0;
        List<String> values=new ArrayList<>();
        Iterator<Row> rows = currentSheet.iterator();
        int finalRow = currentSheet.getLastRowNum();
        while(rows.hasNext()&&(rowNum<finalRow))  {
            if(rowNum>0)
                values.add(parseCell(key, rowNum)==null?"":parseCell(key, rowNum));
            rowNum++;
        }
        return (String[])values.toArray();
        
    }
    
    /**
     * 
     * @param key whole or partial name of the column.
     * @param row Index of the Row
     * @return String value of the requested Cell's contents
     */
    public String getValue(String key, int row)   {
        return parseCell(key,row);
    }
    
    private String parseCell(String key, int row) {
        String value;
        Row currentRow = currentSheet().getRow(row);
        if( (currentRow.getCell(findKey(key))!=null) && (readValue(currentRow.getCell(findKey(key)))!=null))  {
            Cell currentCell = currentRow.getCell(findKey(key));
            log.debug("Reading Cell at row: "+row+" for column with key: "+key); 
            value = readValue(currentCell);
            log.debug("Cell Value: "+value);
        } else {
            value = "";    
        }
        return value;

    }

    private String readValue(Cell currentCell)  {
        String value="";
        switch(currentCell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value+=currentCell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                value+=currentCell.getNumericCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value+=currentCell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_ERROR:
                value+="[Error!]"+currentCell.getErrorCellValue();
                break;
            case Cell.CELL_TYPE_FORMULA:
                value+=currentCell.getCellFormula();
                break;
            default:
                break;
        }
        return value;
    }

    /**
     * 
     * @return Returns an array of all keys from the Excel sheet.
     */
    public String[] getKeys() {
        String[] response = new String[keys.size()];
        return keys.toArray(response);
    }
    
    private void ParseKeys() {
        keys = new ArrayList<>();
        boolean blankLimitReached = false;
        int blanksAllowed = 1;
        int blanksFound=0;
        Row currentRow = currentSheet().getRow(0);
        Iterator rowIter = currentRow.cellIterator();
        while(rowIter.hasNext()&&!blankLimitReached)  {
            Cell currentCell = (Cell) rowIter.next();
            if (currentCell.getCellType()==Cell.CELL_TYPE_BLANK)
            {
               blankLimitReached=(++blanksFound>=blanksAllowed);
            } else {
                blanksFound=0;
            }
            String value=readValue(currentCell);
            
            keys.add(value);
            
                
        }
        
    }
    
    private String[] ParseRow(int rowNum) {
        String[] values = new String [keys.size()];
        boolean LimitReached = false;
        int cellNum=0;
        int cellLimit = keys.size();
        Row currentRow = currentSheet().getRow(rowNum);
        Iterator rowIter = currentRow.cellIterator();
        while(rowIter.hasNext()&&!LimitReached)  {
            Cell currentCell = (Cell) rowIter.next();
            String value="";
            switch(currentCell.getCellType())
            {
                case Cell.CELL_TYPE_STRING:
                    value+=currentCell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    value+=currentCell.getNumericCellValue();
                    break;
                default:
                    break;
            }
            values[cellNum]=value;
            LimitReached=(++cellNum>=cellLimit);
        }
        return values;
    }


    private boolean verifyExt(String pathName) {
        int finalDot = pathName.lastIndexOf('.');
        return pathName.substring(finalDot).equalsIgnoreCase(".xlsx");
    }

    /**
     * Loads the xlsx file into the object as an Apache POI workbook
     * 
     * @param pathName path to the xlsx file
     * @return loaded POI workbook for this class to use
     */
	public Workbook loadExcel(String pathName) {
		Workbook tempSheet = null;
		try {
			target = new File(pathName);
			FileInputStream targetStream = new FileInputStream(target);
			tempSheet = loadExcel(targetStream);
		} catch (IOException e) {
			throw new IllegalStateException("Error when attempting to access "
					+ pathName, e);
		}
		return (tempSheet);
	}
    
    /**
     * Loads the xlsx filestream into the object as an Apache POI workbook
     * 
     * @param pathName path to the xlsx file
     * @return loaded POI workbook for this class to use
     */
	public Workbook loadExcel(InputStream fileStream) {
		Workbook tempSheet = null;
		try {
			tempSheet = new XSSFWorkbook(fileStream);
		} catch (IOException e) {
			throw new IllegalStateException(
					"Error when attempting to access FileInputStream\n", e);
		}
		return (tempSheet);
	}
    
}
