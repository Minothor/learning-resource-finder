package learningresourcefinder.service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.util.CurrentEnvironment;
import learningresourcefinder.util.FileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service(value="resourceService")
@Scope("singleton")
public class ResourceService {


	@Autowired   private CurrentEnvironment currentEnvironment;
	@Autowired ResourceRepository resourceRepository;

	public void resourceImageDelete(Resource resource, long idimage) {
		String originalDirectory = FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.RESOURCE_SUB_FOLDER + FileUtil.RESOURCE_ORIGINAL_SUB_FOLDER;
		FileUtil.deleteFilesWithPattern(originalDirectory, resource.getId()+"-"+idimage+".jpg");
		deleteOneImage(originalDirectory, resource);
		
		String largeDirectory = FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.RESOURCE_SUB_FOLDER + FileUtil.RESOURCE_RESIZED_SUB_FOLDER + FileUtil.RESOURCE_RESIZED_LARGE_SUB_FOLDER;
		FileUtil.deleteFilesWithPattern(largeDirectory, resource.getId()+"-"+idimage+".jpg");
		deleteOneImage(largeDirectory, resource);
		
		String smallDirectory = FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.RESOURCE_SUB_FOLDER + FileUtil.RESOURCE_RESIZED_SUB_FOLDER +  FileUtil.RESOURCE_RESIZED_SMALL_SUB_FOLDER;
		FileUtil.deleteFilesWithPattern(smallDirectory, resource.getId()+"-"+idimage+".jpg");
		deleteOneImage(smallDirectory, resource);
		
		
	}

	// This method renames files to match the order of the given name list
	// imageNameList names the image files in the desired order. 
	// Files are supposed to have the structure nameBody + "-" + number + extention + suffix.
	// for example: 4859-3.jpg-tmp  ("-tmp" being the extention")
	private static void renumberImageFiles(String directory, String prefix, ArrayList<String> imageNameList) {
		FileUtil.renameFileSetWithSuffix(directory, prefix+"*", "-tmp");

		int i = 1;	 
		for(String imageNameFromList : imageNameList){
			FileUtil.renameFile(directory + "", imageNameFromList + ".jpg-tmp", prefix+i+".jpg");
			i++;
		}
	}
	
	private void deleteOneImage(String directory, Resource resource) {
		String prefix= resource.getId()+"-";
		
		// Collect the current image file names for that resource.
		ArrayList<String> listImages = new ArrayList<String>();
		
		Path p = FileSystems.getDefault().getPath(directory);
		
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(p, prefix+"*")) {
			for (Path path: directoryStream){
				listImages.add(path.getFileName().toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		renumberImageFiles(directory, prefix, listImages);
		
		resource.setNumberImage(listImages.size());
		resourceRepository.merge(resource);
	}
	
	public void changeGalleryOrder(ArrayList<String> listImages, Resource resource) {
		String originalDirectory = FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.RESOURCE_SUB_FOLDER + FileUtil.RESOURCE_ORIGINAL_SUB_FOLDER;		
		changeOneGalleryOrder(originalDirectory, listImages, resource);
		String largeDirectory = FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.RESOURCE_SUB_FOLDER + FileUtil.RESOURCE_RESIZED_SUB_FOLDER + FileUtil.RESOURCE_RESIZED_LARGE_SUB_FOLDER;		
		changeOneGalleryOrder(largeDirectory, listImages, resource);
		String smallDirectory = FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.RESOURCE_SUB_FOLDER + FileUtil.RESOURCE_RESIZED_SUB_FOLDER +  FileUtil.RESOURCE_RESIZED_SMALL_SUB_FOLDER;
		changeOneGalleryOrder(smallDirectory, listImages, resource);
		
	
		
	}
	
	public void changeOneGalleryOrder(String directory, ArrayList<String> listImages, Resource resource) {
		renumberImageFiles(directory+"/", resource.getId()+"-", listImages);
		
	}
}
