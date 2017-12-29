import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LR {
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
       
        HashMap<String, Double> allweights = new HashMap<String, Double>();
        
        for (String l : list1) {  
        	
        	BufferedReader br = new BufferedReader(new FileReader(new File(l)));  
        	
            String lineString = br.readLine();
            
           
            while (lineString != null) {
            	lineString = lineString.replaceAll("[^a-zA-Z]", " ");
                lineString = lineString.toLowerCase();
            	String[] spwd =lineString.split("\\s+");
            	
            	for(int i = 0; i < spwd.length; i++){
            		
            		if(allweights.get(spwd[i])==null)//word does not exist in mapping
            		{
            			allweights.put(spwd[i], 1.0);
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
       
       
        for (String l : list2) {  
        	
        	BufferedReader br = new BufferedReader(new FileReader(new File(l)));  
        	
            String lineString = br.readLine();
            
            while (lineString != null) {
            	lineString = lineString.replaceAll("[^a-zA-Z]", " ");
                lineString = lineString.toLowerCase();
            	String[] hmwd =lineString.split("\\s+");
            	
            	for(int i = 0; i < hmwd.length; i++){
            		
            		if(allweights.get(hmwd[i])==null)//word does not exist in mapping
            		{
            			allweights.put(hmwd[i], 1.0);
            		}
            		
            	}
                lineString = br.readLine();
            }
            
        }
	
	System.out.println("There are "+ allweights.size()+ " words.");
	
	double rate = 0.01;
	double lumda = 0.01;
	
	//training
	for(int iterations = 10; iterations>0 ; iterations--){
		//train the spam
		String FILE_IN3 = "train\\spam";  
        File f3 = new File(FILE_IN3);  
        List<String> list3 = new ArrayList<String>();  
        list3 = getFileList(f3);  
        
        
        
        for (String l : list3) { 
        	
        	HashMap<String, Integer> wordcount = new HashMap<String, Integer>();
        	BufferedReader br = new BufferedReader(new FileReader(new File(l)));
            String lineString = br.readLine();
            while (lineString != null) {
            	lineString = lineString.replaceAll("[^a-zA-Z]", " ");
                lineString = lineString.toLowerCase();
            	String[] spwd =lineString.split("\\s+");
            	
            	for(int i = 0; i < spwd.length; i++){
            		
            		if(wordcount.get(spwd[i])==null)//word does not exist in mapping
            		{
            			wordcount.put(spwd[i], 1);
            		}else{
            			int value = wordcount.get(spwd[i]);
            			wordcount.put(spwd[i], value+1);
            		}
            	}
                lineString = br.readLine();
            }
            
            double y = 0;
            double p;
            double sum = 0.0;
            for(String key: wordcount.keySet()){
            	double xi = wordcount.get(key);
            	double wi = allweights.get(key);
            	sum = sum + wi*xi;
            }
            p = 1.0/(1.0+Math.exp(-sum));
            
            //update weights
            for(String key: wordcount.keySet()){
            	double wi = allweights.get(key);
            	double value = wordcount.get(key);
            	double neww = wi + rate*(y-p)*value-rate*lumda*wi;
            	allweights.put(key, neww);
            }
        }
		
		
		
		
		//train the ham
		String FILE_IN4 = "train\\ham";  
        File f4 = new File(FILE_IN4);  
        List<String> list4 = new ArrayList<String>();  
        list4 = getFileList(f4);  
        
        for (String l : list4) { 
        	
        	HashMap<String, Integer> wordcount = new HashMap<String, Integer>();
        	BufferedReader br = new BufferedReader(new FileReader(new File(l)));
            String lineString = br.readLine();
            while (lineString != null) {
            	lineString = lineString.replaceAll("[^a-zA-Z]", " ");
                lineString = lineString.toLowerCase();
            	String[] spwd =lineString.split("\\s+");
            	
            	for(int i = 0; i < spwd.length; i++){
            		
            		if(wordcount.get(spwd[i])==null)//word does not exist in mapping
            		{
            			wordcount.put(spwd[i], 1);
            		}else{
            			int value = wordcount.get(spwd[i]);
            			wordcount.put(spwd[i], value+1);
            		}
            	}
                lineString = br.readLine();
            }
            
            double y = 1;
            double p;
            double sum = 0.0;
            for(String key: wordcount.keySet()){
            	double xi = wordcount.get(key);
            	double wi = allweights.get(key);
            	sum = sum + wi*xi;
            }
            p = 1.0/(1.0+Math.exp(-sum));
            
            //update weights
            for(String key: wordcount.keySet()){
            	double wi = allweights.get(key);
            	double value = wordcount.get(key);
            	double neww = wi + rate*(y-p)*value-rate*lumda*wi;
            	allweights.put(key, neww);
            }
        }
		
		
		
		
		
	}
	
	String FILE_IN5 = "test\\spam";  
    File f5 = new File(FILE_IN5);  
    List<String> list5 = new ArrayList<String>();  
    list5 = getFileList(f5);  
    
    double numoftestspam = list5.size();  
    System.out.println(numoftestspam);
    double spissp = 0;
    for (String l : list5) {  
    	HashMap<String, Integer> wordcount = new HashMap<String, Integer>();
    	BufferedReader br = new BufferedReader(new FileReader(new File(l)));  
    	String lineString = br.readLine();
        while (lineString != null) {
        	lineString = lineString.replaceAll("[^a-zA-Z]", " ");
            lineString = lineString.toLowerCase();
        	String[] spwd =lineString.split("\\s+");
        	
        	for(int i = 0; i < spwd.length; i++){
        		
        		if(wordcount.get(spwd[i])==null)//word does not exist in mapping
        		{
        			wordcount.put(spwd[i], 1);
        		}else{
        			int value = wordcount.get(spwd[i]);
        			wordcount.put(spwd[i], value+1);
        		}
        		
        	}
            lineString = br.readLine();
        }
        
        double sum = 0.0;
        for(String key: wordcount.keySet()){
        	double wi;
        	if(allweights.get(key)==null){
        		wi =0.0;
        		}
        	else{
        		wi = allweights.get(key);
        		}
        	double xi = wordcount.get(key);
        	sum = sum + wi*xi;
        	
        }
        if(sum<=0){
        	spissp++;
        }
        
    }
	
	System.out.println(spissp);
	
	
	String FILE_IN6 = "test\\ham";  
    File f6 = new File(FILE_IN6);  
    List<String> list6 = new ArrayList<String>();  
    list6 = getFileList(f6);  
    
    double numoftestham = list6.size();  
    System.out.println(numoftestham);
    double hmishm = 0;
    for (String l : list6) {  
    	HashMap<String, Integer> wordcount = new HashMap<String, Integer>();
    	BufferedReader br = new BufferedReader(new FileReader(new File(l)));  
    	String lineString = br.readLine();
        while (lineString != null) {
        	lineString = lineString.replaceAll("[^a-zA-Z]", " ");
            lineString = lineString.toLowerCase();
        	String[] spwd =lineString.split("\\s+");
        	
        	for(int i = 0; i < spwd.length; i++){
        		
        		if(wordcount.get(spwd[i])==null)//word does not exist in mapping
        		{
        			wordcount.put(spwd[i], 1);
        		}else{
        			int value = wordcount.get(spwd[i]);
        			wordcount.put(spwd[i], value+1);
        		}
        		
        	}
            lineString = br.readLine();
        }
        
        double sum = 0.0;
        for(String key: wordcount.keySet()){
        	double wi;
        	if(allweights.get(key)==null){
        		wi =0.0;
        		}
        	else{
        		wi = allweights.get(key);
        		}
        	double xi = wordcount.get(key);
        	sum = sum + wi*xi;
        	
        }
        if(sum>0){
        	hmishm++;
        }
        
    }
	
	System.out.println(hmishm);
	
	double ac = (hmishm+spissp)/(numoftestham+numoftestspam);
	System.out.println(ac);
	
	
	}
	
	
}
