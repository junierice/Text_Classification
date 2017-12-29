import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NB {
	
	public static List<String> getFileList(File file) {  
		  
        List<String> result = new ArrayList<String>();  
  
        if (!file.isDirectory()) {  
            System.out.println(file.getAbsolutePath());  
            result.add(file.getAbsolutePath());  
        } else {  
            File[] directoryList = file.listFiles();  
            for (int i = 0; i < directoryList.length; i++) {  
                result.add(directoryList[i].getPath());  
            }  
        }  
  
        return result;  
    }
	
	
	
	public static void main(String args[]) throws IOException{
		String FILE_IN1 = "train\\spam";  
        File f1 = new File(FILE_IN1);  
        List<String> list1 = new ArrayList<String>();  
        list1 = getFileList(f1);  
        double allwdinspam = 0.0;
        double numofspam = list1.size();  
        System.out.println(numofspam);
        HashMap<String, Integer> hssp = new HashMap<String, Integer>();
        HashMap<String, Integer> all = new HashMap<String, Integer>();
        for (String l : list1) {  
        	
        	BufferedReader br = new BufferedReader(new FileReader(new File(l)));  
        	
            String lineString = br.readLine();
            
           
            while (lineString != null) {
            	lineString = lineString.replaceAll("[^a-zA-Z]", " ");
                lineString = lineString.toLowerCase();
            	String[] spwd =lineString.split("\\s+");
            	allwdinspam = allwdinspam + (double)spwd.length;
            	for(int i = 0; i < spwd.length; i++){
            		
            		if(all.get(spwd[i])==null)//word does not exist in mapping
            		{
            			all.put(spwd[i], 1);
            		}else{
            			int value = all.get(spwd[i]);
            			all.put(spwd[i], value+1);
            		}
            		
            		
            		if(hssp.get(spwd[i])==null)//word does not exist in mapping
            		{
            			hssp.put(spwd[i], 1);
            		}else{
            			int value = hssp.get(spwd[i]);
            			hssp.put(spwd[i], value+1);
            		}
            	}
                lineString = br.readLine();
            }
            
        }
		
        String FILE_IN2 = "train\\ham";  
        File f2 = new File(FILE_IN2);  
        List<String> list2 = new ArrayList<String>();  
        list2 = getFileList(f2);  
        double allwdinham = 0.0;
        double numofham = list2.size();
        System.out.println(numofham);
        HashMap<String, Integer> hshm = new HashMap<String, Integer>();
       
        for (String l : list2) {  
        	
        	BufferedReader br = new BufferedReader(new FileReader(new File(l)));  
        	
            String lineString = br.readLine();
            
            while (lineString != null) {
            	lineString = lineString.replaceAll("[^a-zA-Z]", " ");
                lineString = lineString.toLowerCase();
            	String[] hmwd =lineString.split("\\s+");
            	allwdinham = allwdinham + (double)hmwd.length;
            	for(int i = 0; i < hmwd.length; i++){
            		
            		if(all.get(hmwd[i])==null)//word does not exist in mapping
            		{
            			all.put(hmwd[i], 1);
            		}else{
            			int value = all.get(hmwd[i]);
            			all.put(hmwd[i], value+1);
            		}
            		
            		
            		if(hshm.get(hmwd[i])==null)//word does not exist in mapping
            		{
            			hshm.put(hmwd[i], 1);
            		}else{
            			int value = hshm.get(hmwd[i]);
            			hshm.put(hmwd[i], value+1);
            		}
            	}
                lineString = br.readLine();
            }
            
        }
        
        String FILE_IN3 = "test\\spam";  
        File f3 = new File(FILE_IN3);  
        List<String> list3 = new ArrayList<String>();  
        list3 = getFileList(f3);  
        
        double numoftestspam = list3.size();  
        double numofspissp = 0;
    //    System.out.println(spisps);
    //    System.out.println(spishm);
        
        for (String l : list3) {  
        	
        	double isps =  Math.log(numofspam/(numofspam+numofham));
            double ishm =  Math.log(numofham/(numofspam+numofham));
        	BufferedReader br = new BufferedReader(new FileReader(new File(l)));  
        	
            String lineString = br.readLine();
            
         //   System.out.println(lineString);
            while (lineString != null) {
            	lineString = lineString.replaceAll("[^a-zA-Z]", " ");
                lineString = lineString.toLowerCase();
            	String[] wd =lineString.split("\\s+");
            	for(int i = 0; i < wd.length; i++){
            		
            		if(hssp.get(wd[i])==null)//word does not exist in mapping
            		{
            			isps = isps + Math.log((0.0 + 1.0)/((double)allwdinspam + (double)all.size()));
            		}else{
            			double value = hssp.get(wd[i]);
            			isps = isps + Math.log((value + 1.0)/((double)allwdinspam + (double)all.size()));;
            		}
            		
            		if(hshm.get(wd[i])==null)//word does not exist in mapping
            		{
            			ishm = ishm + Math.log((0.0 + 1.0)/((double)allwdinham + (double)all.size()));
            		}else{
            			double value = hshm.get(wd[i]);
            			ishm = ishm + Math.log((value + 1.0)/((double)allwdinham + (double)all.size()));
            		}
            		
            		
            	}
                lineString = br.readLine();
            }
            
            if(isps>ishm){
            	numofspissp = numofspissp + 1;
            }
            
        }
        
        System.out.println(numofspissp);
        
        
        String FILE_IN4 = "test\\ham";  
        File f4 = new File(FILE_IN4);  
        List<String> list4 = new ArrayList<String>();  
        list4 = getFileList(f4);  
        
        double numoftestham = list4.size();  
        double numofhmishm = 0;

        
        for (String l : list4) {  
        	
        	double isps =  Math.log(numofspam/(numofspam+numofham));
            double ishm =  Math.log(numofham/(numofspam+numofham));
        	BufferedReader br = new BufferedReader(new FileReader(new File(l)));  
        	
            String lineString = br.readLine();
            lineString = lineString.replaceAll("[^a-zA-Z]", " ");
            lineString = lineString.toLowerCase();
            while (lineString != null) {
            	lineString = lineString.replaceAll("[^a-zA-Z]", " ");
                lineString = lineString.toLowerCase();
            	String[] wd =lineString.split("\\s+");
            	for(int i = 0; i < wd.length; i++){
            		
            		if(hssp.get(wd[i])==null)//word does not exist in mapping
            		{
            			isps = isps + Math.log((0.0 + 1.0)/((double)allwdinspam + (double)all.size()));
            		}else{
            			double value = hssp.get(wd[i]);
            			isps = isps + Math.log((value + 1.0)/((double)allwdinspam + (double)all.size()));;
            		}
            		
            		if(hshm.get(wd[i])==null)//word does not exist in mapping
            		{
            			ishm = ishm + Math.log((0.0 + 1.0)/((double)allwdinham + (double)all.size()));
            		}else{
            			double value = hshm.get(wd[i]);
            			ishm = ishm + Math.log((value + 1.0)/((double)allwdinham + (double)all.size()));
            		}
            		
            		
            	}
                lineString = br.readLine();
            }
            
            if(ishm>isps){
            	numofhmishm = numofhmishm + 1;
            }
            
        }
        
        System.out.println(numofhmishm);
        
        double accuracy = (numofhmishm+numofspissp)/(numoftestham+numoftestspam);
        System.out.println("The accuracy without improving is " + accuracy);
        
        
        
        
        
        
        
	}
	
	
	
	
	
	
}
