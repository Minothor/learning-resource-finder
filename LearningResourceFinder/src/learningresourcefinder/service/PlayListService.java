package learningresourcefinder.service;

import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import learningresourcefinder.model.PlayList;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;
import learningresourcefinder.util.EnumUtil;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value="playListService")
@Scope("singleton")
public class PlayListService {


    public Set<PlayList> getAllUserPlayListsDontContainAResource(User user, Resource resource){

        // We want the playlist list sorted by name
        Comparator<PlayList> comparator = new Comparator<PlayList>() {
            @Override  public int compare(PlayList pl1, PlayList pl2) {
                return pl1.getName().compareToIgnoreCase(pl2.getName());
            }
        };
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
    
    public String setPlayListToJSONWithIdAndName(Set<PlayList> spl){
        
        String json = "[";

        for(PlayList pl : spl){
            json = json +"{value:'"+ pl.getId() + "',text:'"+ pl.getName() + "'},";
        }
        if(!json.equals("[")){
            json = json.substring(0, json.length() - 1);
        }
        
        return json + "]";
        
        // example below of key/value template to display playlist in x-editable combobox 
        //[{value:'false',text:'Non'},{value:'true',text:'Oui'}]
    }
    

    


}
