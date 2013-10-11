package learningresourcefinder.batch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import learningresourcefinder.search.SearchOptions;
import learningresourcefinder.search.SearchOptions.Platform;

public class TestBach  {
	

	
	
	public static void main(String[] args){

		int i=1,sizeplatform=Platform.values().length;
		String dataEnumPlatform="[";
		
		
		for(Platform p: Platform.values()){
		
			if(i==sizeplatform){
				dataEnumPlatform=dataEnumPlatform+"{value:"+i+","+"text:"+"'"+p.getDescription()+"'}";
				break;
			}
		dataEnumPlatform=dataEnumPlatform+"{value:"+i+","+"text:"+"'"+p.getDescription()+"'},";
		i++;
			
			
		}
		System.out.println(dataEnumPlatform);
		
	}
	
	public void run() {	
		
		
		
		
		
		
		
		
		

}
	

	
}
