package learningresourcefinder.service;

import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import learningresourcefinder.model.PlayList;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.PlayListRepository;
import learningresourcefinder.util.CurrentEnvironment;
import learningresourcefinder.util.FileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value="playListService")
@Scope("singleton")
public class PlayListService {

    @Autowired PlayListRepository playListRepository;
    @Autowired   private CurrentEnvironment currentEnvironment;

    public Set<PlayList> getAllUserPlayListsDontContainAResource(User user, Resource resource){

        // We want the playlist list sorted by name
        Comparator<PlayList> comparator = new PlayListNameComparator();

        SortedSet<PlayList> playListSet =  new TreeSet<PlayList>(comparator);
        SortedSet<PlayList> playListSetToReturn =  new TreeSet<PlayList>(comparator);
        playListSet.addAll(user.getPlayListList());
        
        for(PlayList pl : playListSet){
            if(!pl.getResources().contains(resource)){
                playListSetToReturn.add(pl);
            }
        }
        
        return playListSetToReturn;
    }   
    

    public Set<PlayList> getUserPlayListsWithResource(Resource resource,User user){
        
        // We want the playlist list sorted by name
        Comparator<PlayList> comparator = new PlayListNameComparator();
        
        Set<PlayList> spl = user.getPlayListList();
        Set<PlayList> splContainingResource = new TreeSet<PlayList>(comparator);
        
        for(PlayList pl : spl){
            if(pl.getResources().contains(resource)){
                splContainingResource.add(pl);
            }
        }
        
        return splContainingResource;
    }
    
    public Set<PlayList> getOtherPeoplePlayListsWithResource(Resource resource,User user){
        
        // We want the playlist list sorted by name
        Comparator<PlayList> comparator = new PlayListNameComparator();
        
        Set<PlayList> spl = new TreeSet<PlayList>(comparator);
        spl.addAll(playListRepository.getAllPlayLists());
        Set<PlayList> splOtherPeopleWithResource = new TreeSet<PlayList>(comparator);
        
        for(PlayList pl : spl){
            if(pl.getResources().contains(resource) && pl.getCreatedBy() != user){
                splOtherPeopleWithResource.add(pl);
            }
        }
        
        return splOtherPeopleWithResource;
    }
    

    public String setPlayListToJSONWithIdAndName(Set<PlayList> spl){
        
        String json = "[";

        for(PlayList pl : spl){
            json = json +"{value:'"+ pl.getId() + "',text:'"+ pl.getName().replace("'", "\\'") + "'},";
        }
        if(!json.equals("[")){
            json = json.substring(0, json.length() - 1);
        }
        
        return json + "]";
        
        // example below of key/value template to display playlist in x-editable combobox 
        //[{value:'false',text:'Non'},{value:'true',text:'Oui'}]
    }
        
	public void playlistImageDelete(PlayList playlist) {
		String originalDirectory = FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.PLAYLIST_SUB_FOLDER + FileUtil.PLAYLIST_RESIZED_SUB_FOLDER 
        		+  FileUtil.PLAYLIST_RESIZED_LARGE_SUB_FOLDER;
		FileUtil.deleteFilesWithPattern(originalDirectory, playlist.getId()+".jpg");
		
		String resizedDirectory = FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.PLAYLIST_SUB_FOLDER + FileUtil.PLAYLIST_RESIZED_SUB_FOLDER 
        		+ FileUtil.PLAYLIST_RESIZED_SMALL_SUB_FOLDER;
		FileUtil.deleteFilesWithPattern(resizedDirectory, playlist.getId()+".jpg");
		
		playlist.setPicture(false);
		
	}

    static class PlayListNameComparator implements Comparator<PlayList> {
        @Override  public int compare(PlayList pl1, PlayList pl2) {
            return pl1.getName().compareToIgnoreCase(pl2.getName());
        }
    };

}
