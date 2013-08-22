package learningresourcefinder.service;

import learningresourcefinder.model.Resource;
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
	
	public void resourceImageDelete(Resource resource){
		FileUtil.deleteFilesWithPattern(FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.RESOURCE_SUB_FOLDER + FileUtil.RESOURCE_ORIGINAL_SUB_FOLDER, resource.getId()+".*");
		FileUtil.deleteFilesWithPattern(FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.RESOURCE_SUB_FOLDER + FileUtil.RESOURCE_RESIZED_SUB_FOLDER + FileUtil.RESOURCE_RESIZED_LARGE_SUB_FOLDER, resource.getId()+".*");
		FileUtil.deleteFilesWithPattern(FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.RESOURCE_SUB_FOLDER + FileUtil.RESOURCE_RESIZED_SUB_FOLDER +  FileUtil.RESOURCE_RESIZED_SMALL_SUB_FOLDER, resource.getId()+".*");
	}
}
