package learningresourcefinder.service;

import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import learningresourcefinder.batch.ImportLabsetBatch;
import learningresourcefinder.controller.ResourceImageController;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.model.User;
import learningresourcefinder.model.Resource.Topic;
import learningresourcefinder.model.Resource.ValidationStatus;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UrlResourceRepository;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.search.SearchOptions.Format;
import learningresourcefinder.search.SearchOptions.Language;
import learningresourcefinder.search.SearchOptions.Nature;
import learningresourcefinder.search.SearchOptions.Platform;
import learningresourcefinder.util.ExcelSheet;
import learningresourcefinder.util.Logger;
import learningresourcefinder.web.Slugify;

@Transactional
@Service
public class ImportLabSetService {

	@Logger Log log;
	
	private static final boolean PERSIST_FOR_REAL = false;   // need false for tests (impossible to import twice).  
	
    @Autowired ResourceRepository resRep;
    @Autowired UrlResourceRepository urlRep;
    @Autowired UserRepository userRep;
    

	public void importLabSetResources() {
		importFrancais();
		importMaths();
	}

	public void importFrancais()  {

		//// Find file path
		log.info("Importing: " + ClassLoader.getSystemClassLoader().getSystemResource("import").getPath());
		String francaisPath;
		try {
			francaisPath = ClassLoader.getSystemResource("import/activite_français_2012description50char.xlsx").toURI().getPath();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

		ExcelSheet francaisExcelSheet = new ExcelSheet(francaisPath);
		int resourceCount=0;
		// One row per resource to import
		for (int row=2;row<=724;row++) { //hard coded start and stop rows (max rows function buggy)
			
			Resource resource = new Resource();

			//checks if the title string would exceed DB limit (no match for manual cleanup)
			String title = francaisExcelSheet.getValue("titre", row);
			if (title.length() > 50) {
				title = title.substring(0, 49);
			}
			resource.setName(title);
			resource.setAuthor(francaisExcelSheet.getValue("auteur", row));
			resource.setDescription(francaisExcelSheet.getValue("description", row));
			UrlResource urlRes = new UrlResource("", francaisExcelSheet.getValue("url", row), resource);
			if (PERSIST_FOR_REAL) {
				urlRep.persist(urlRes);
			}
			resource.getUrlResources().add(urlRes);
			resource.setPlatform(Platform.BROWSER);
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

			//checks to see if the url ends in doc, pdf or txt, then sets the format to "document" if true. 
			if (resource.getUrlResources().get(0).getUrl().toLowerCase(Locale.US).matches("^.*.(doc|pdf|txt)")) {
				resource.setFormat(Format.DOC);
				log.debug("Document");
			} else {
				resource.setFormat(Format.INTERACTIVE);
				log.debug("Interactive");
			}

			resource.setTopic(Topic.FRENCH);
			resource.setValidationStatus(ValidationStatus.ACCEPT);
			resource.setValidationDate(Calendar.getInstance().getTime());
			
			User admin = userRep.getUserByUserName("Admin");
			resource.setValidator(admin);
			
			if (PERSIST_FOR_REAL) {
				resRep.persist(resource);
			} else {
				log.info("importing resource: " +resource);
			}
			resourceCount++;
		}
		log.info(resourceCount + " resources imported");
	}

	@Transactional
	public void importMaths() {
		// this is effectively a copypasta of importFrancais() see the comments above for functionality.
		String mathsExcel;
		try {
			mathsExcel = ClassLoader.getSystemResource("import/liste_finale_id50Max.xlsx").toURI().getPath();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

		ExcelSheet maths = new ExcelSheet(mathsExcel);
		int resourceCount=0;

		for (int i=1;i<=300;i++){
			Resource resource = new Resource();
			String title = maths.getValue("titre", i);
			if (title.length() > 50) {
				title = title.substring(0, 49);
			}
			resource.setName(title);
			resource.setAuthor(maths.getValue("auteur", i));
			resource.setDescription(maths.getValue("description", i));
			UrlResource urlRes = new UrlResource("", maths.getValue("url", i), resource);
			if (PERSIST_FOR_REAL) {
				urlRep.persist(urlRes);
			}
			resource.getUrlResources().add(urlRes);
			resource.setPlatform(Platform.BROWSER);
			resource.setLanguage(Language.FR);
			resource.setSlug(Slugify.slugify(resource.getName()));


			resource.setNature(Nature.EVALUATIVE_WITHANSWER);

			String durationVal = maths.getValue("temps", i);
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

			if (resource.getUrlResources().get(0).getUrl().toLowerCase(Locale.US).matches("^.*.(doc|pdf)")){
				resource.setFormat(Format.DOC);
				log.debug("Document");
			} else {
				resource.setFormat(Format.INTERACTIVE);
				log.debug("Interactive");
			}






			resource.setTopic(Topic.MATH);

			resource.setValidationStatus(ValidationStatus.ACCEPT);
			resource.setValidationDate(Calendar.getInstance().getTime());
			User admin = userRep.getUserByUserName("Admin");
			resource.setValidator(admin);

			if (resource.getName().length() > 50) {
				log.debug(resource);
			}
			if (PERSIST_FOR_REAL) {
				resRep.persist(resource);
			} else {
				log.info("importing resource: " +resource);
			}
			resourceCount++;
		}
		log.info(resourceCount + " resources imported");
	//	log.debug(resource.getId());

		}
	}

//  @Transactional
//	private void processImages() {
//		String urlPath;
//		long resForImage;
//		Iterator resources = imageQueue.keySet().iterator();
//		while (resources.hasNext()) {
//			resForImage = (long) resources.next();
//			urlPath = imageQueue.get(resForImage);
//			try {
//				resImg.resourceImageAddUrl(resForImage, urlPath);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		imageQueue.clear();
//	}
//}


