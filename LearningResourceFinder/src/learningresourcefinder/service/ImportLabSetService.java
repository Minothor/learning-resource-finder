package learningresourcefinder.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import learningresourcefinder.controller.ResourceImageController;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.Resource.Topic;
import learningresourcefinder.model.Resource.ValidationStatus;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UrlResourceRepository;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.search.SearchOptions.Format;
import learningresourcefinder.search.SearchOptions.Language;
import learningresourcefinder.search.SearchOptions.Nature;
import learningresourcefinder.search.SearchOptions.Platform;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.util.CurrentEnvironment;
import learningresourcefinder.util.ExcelSheet;
import learningresourcefinder.util.ImageUtil;
import learningresourcefinder.util.Logger;
import learningresourcefinder.web.Slugify;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImportLabSetService{

	@Logger Log log;
	
	private static final boolean PERSIST_FOR_REAL = true;   // need false for tests (impossible to import twice).  

	
    @Autowired ResourceRepository resRep;
    @Autowired UrlResourceRepository urlRep;
    @Autowired UserRepository userRep;
    @Autowired ResourceImageController imgRes;
	@Autowired CurrentEnvironment currentEnvironment;

	private HashMap<Long,String> imageQueue = new HashMap<>();

	public void importFrancais()  {

		//// Find file path
		log.info("Importing: " + this.getClass().getClassLoader().getResource("import").getPath());
		String francaisPath;
		try {
			francaisPath = this.getClass().getClassLoader().getResource("import/activite_français_2012description50char.xlsx").toURI().getPath();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

		ExcelSheet francaisExcelSheet = new ExcelSheet(francaisPath);
		int resourceCount=0;
		// One row per resource to import
		for (int row=2;row<=724;row++) { //hard coded start and stop rows (max rows function buggy)
			
			createAndFillFrenchResource(francaisExcelSheet, row);
			resourceCount++;
		}
		log.info(resourceCount + " resources imported");
	}

	@Transactional
	private void createAndFillFrenchResource(ExcelSheet francaisExcelSheet,
			int row) {
		Resource resource = new Resource();

		//checks if the title string would exceed DB limit (no match for manual cleanup)
		String title = francaisExcelSheet.getValue("titre", row);
		if (title.length() > 50) {
			title = title.substring(0, 49);
		}
		resource.setName(title);
		resource.setAuthor(francaisExcelSheet.getValue("auteur", row));
		resource.setDescription(francaisExcelSheet.getValue("description", row));
		
		
		Set<Platform> newSetPlatform=new HashSet<>(resource.getPlatforms()); 
		newSetPlatform.add(Platform.BROWSER); 
		resource.setPlatforms(newSetPlatform);
		
		resource.setLanguage(Language.FR);
		resource.setSlug(Slugify.slugify(resource.getName()));

		// Analyze the duration string which might be like "5mn".
		String durationVal = francaisExcelSheet.getValue("durée", row);
		String timeSegment;
		String[] stringBits;
		int durationTime=0;
		if (durationVal!=null && !durationVal.trim().isEmpty()){
			if (durationVal.matches(".*mn.")) {
				stringBits = durationVal.split(" ");
				timeSegment = stringBits[0];
			} else {

				//legacy, attempted to read the HH:MM:SS format from excel time cell, results were double values of 0.00235... etc.
				//resorted to manual clean-up of time/duration cells
				stringBits = durationVal.split(":");

				//debug print-out of all array elements snipped by split function, not used.
				System.out.print("\n[");
				for (String string : stringBits) {
					System.out.print(string+",");
				}
				System.out.print("]\n");

				timeSegment =  stringBits[1];
			}

			//strips extra spaces and turns the strings into int values.
			durationTime=Integer.valueOf(timeSegment.trim());
		}

		//changes blank or 0 values into Null
		resource.setDuration((durationVal==null||durationTime==0)?null:durationTime);
		resource.setTopic(Topic.FRENCH);
		resource.setValidationStatus(ValidationStatus.ACCEPT);
		resource.setValidationDate(Calendar.getInstance().getTime());
		resource.setValidator(getAdminUser());
		
		if (PERSIST_FOR_REAL) {
			resRep.persist(resource);
			resource.setShortId(resource.getShortId());
		} else {
			log.info("importing resource: " +resource);
		}
		
		UrlResource urlRes = new UrlResource("", francaisExcelSheet.getValue("url", row), resource);
		if (PERSIST_FOR_REAL) {
			urlRep.persist(urlRes);
		}
		resource.getUrlResources().add(urlRes);
		//checks to see if the url ends in doc, pdf or txt, then sets the format to "document" if true. 
		if (resource.getUrlResources().get(0).getUrl().toLowerCase(Locale.US).matches("^.*.(doc|pdf|txt)")) {
			resource.setFormat(Format.DOC);
			log.debug("Document");
		} else {
			resource.setFormat(Format.INTERACTIVE);
			log.debug("Interactive");
		}
	}

	
	public void importMaths() {
		// this is effectively a copypasta of importFrancais() see the comments above for functionality.
		InputStream mathsExcel = null;
		String imgURL=null;
		int imgNum;
		try {
			mathsExcel = this.getClass().getClassLoader().getResource("import/liste_finale_id50Max.xlsx").openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		ExcelSheet maths = new ExcelSheet(mathsExcel);
		int resourceCount=0;

		for (int row=1;row<=300;row++){
			createAndFillMathResource(maths, row);
			resourceCount++;
		}
		log.info(resourceCount + " resources imported");
	//	log.debug(resource.getId());

		
	}

	@Transactional
	private void createAndFillMathResource(ExcelSheet maths, int row) {
		String imgURL;
		int imgNum;
		Resource resource = new Resource();
		String title = maths.getValue("titre", row);
		if (title.length() > 50) {
			title = title.substring(0, 49);
		}
		resource.setName(title);
		resource.setAuthor(maths.getValue("auteur", row));
		resource.setDescription(maths.getValue("description", row));
		
		
		Set<Platform> newSetPlatform=new HashSet<>(resource.getPlatforms()); 
		newSetPlatform.add(Platform.BROWSER); 
		resource.setPlatforms(newSetPlatform);
		
		resource.setLanguage(Language.FR);
		resource.setSlug(Slugify.slugify(resource.getName()));
		
		imgNum = Math.round(Float.valueOf(maths.getValue("id db", row).trim()));
		imgURL = this.getClass().getClassLoader().getResource("/import/Printscreen_math/Printscreen/"+imgNum+".png").getFile();
		
		resource.setNature(Nature.EVALUATIVE_WITHANSWER);

		String durationVal = maths.getValue("temps", row);
		String timeSegment;
		String[] stringBits;
		int durationTime=0;
		if (durationVal!=null && !durationVal.trim().isEmpty()){
			if (durationVal.matches(".*mn.")){
				stringBits = durationVal.split(" ");
				timeSegment =  stringBits[0];
			} else {
				stringBits = durationVal.split(":");


				System.out.print("\n[");
				for (String string : stringBits) {
					System.out.print(string+",");
				}
				System.out.print("]\n");

				timeSegment =  stringBits[1];
			}
			durationTime=Integer.valueOf(timeSegment.trim());
		}
		resource.setDuration((durationVal==null||durationTime==0)?null:durationTime);
		resource.setTopic(Topic.MATH);

		resource.setValidationStatus(ValidationStatus.ACCEPT);
		resource.setValidationDate(Calendar.getInstance().getTime());
		
		resource.setValidator(getAdminUser());

		if (resource.getName().length() > 50) {
			log.debug(resource);
		}
		if (PERSIST_FOR_REAL) {
			resRep.persist(resource);
			resource.setShortId(resource.getShortId());
			imageQueue.put(resource.getId(), imgURL);
		} else {
			log.info("importing resource: " +resource);
		}
		
		UrlResource urlRes = new UrlResource("", maths.getValue("url", row), resource);
		if (PERSIST_FOR_REAL) {
			urlRep.persist(urlRes);
		}
		resource.getUrlResources().add(urlRes);
		if (resource.getUrlResources().get(0).getUrl().toLowerCase(Locale.US).matches("^.*.(doc|pdf)")){
			resource.setFormat(Format.DOC);
			log.debug("Document");
		} else {
			resource.setFormat(Format.INTERACTIVE);
			log.debug("Interactive");
		}
	}

	@Transactional
	public void processImages() {
		String urlPath;
		long resID;
		Iterator<Long> resources = imageQueue.keySet().iterator();
		int imageCount=1;
		while (resources.hasNext()) {
			resID = (long) resources.next();
			urlPath = imageQueue.get(resID);
			File tempFile = new File(urlPath);
			MultipartFile imageUpload = null;
			try {
				imageUpload = new MockMultipartFile(tempFile.getName(),tempFile.getName(),"image/png",new FileInputStream(tempFile));
			} catch (IOException e1) {
				throw new RuntimeException(e1);
			}
			
			try {
				uploadImage(resID, imageUpload, imageCount);
				imageCount++;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		imageQueue.clear();
	}
  

	
	private void uploadImage(long resourceid,MultipartFile multipartFile, int imageCount) throws Exception{
		Resource resource = resRep.find(resourceid);
		User user = resource.getCreatedBy();
		SecurityContext.assertCurrentUserMayEditThisUser(user);

		BufferedImage image = null;
		image = ImageUtil.readImage(multipartFile.getBytes());
		ImageUtil.createOriginalAndScalesImageFileForResource(resource, image, currentEnvironment);
		log.info(imageCount+" images imported");
	}
  
  
	private User getAdminUser() {
		return userRep.getUserByUserName("admin");
	}
  
}


