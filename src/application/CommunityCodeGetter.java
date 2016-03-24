package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class CommunityCodeGetter {
	HashMap <String , String> map = new HashMap<String , String>();
	public static void main (String[] args){
		CommunityCodeGetter test = new CommunityCodeGetter();
		File f = new File("sampleRD.txt");
		ArrayList<String> array = test.getCommunityCodes(f);
		
		for (int i = 0; i < array.size(); i++){
			System.out.println(array.get(i));
		}
	}
	public CommunityCodeGetter(){
		map = getMap();
	}
	public ArrayList<String> getCommunityCodes (File file){
		ArrayList<String> communityCodes = new ArrayList<String>();
		 	String line = null;	 	
		          	try {
		              	// FileReader reads text files in the default encoding.
		              	FileReader fileReader =
		                  	new FileReader(file);

		              	// Always wrap FileReader in BufferedReader.
		              	BufferedReader bufferedReader =
		                  	new BufferedReader(fileReader);

		              	//will go through the file and compare rds to our hashmap and return community codes
		              	while((line = bufferedReader.readLine()) != null) {
		              		communityCodes.add(map.get(line));
		              	// Always close files.
		              	}
		              	bufferedReader.close();  
		              	fileReader.close();
		          	}
		          	catch(FileNotFoundException ex) {
		              	System.out.println(
		                  	"Unable to open file '" +
		                  	file + "'");            	
		          	}
		          	catch(IOException ex) {
		              	System.out.println(
		                  	"Error reading file '"
		                  	+ file + "'");              	
		              	// Or we could just do this:
		              	// ex.printStackTrace();
		              	//changes
		          	}
		          	communityCodes.set(0, "Community Codes");
		          	return communityCodes;
		          	//ayylmao
	}
	
	public HashMap<String , String> getMap (){
	      HashMap<String , String> map = null;
	      try
	      {
	         FileInputStream fis = new FileInputStream("hashmap.ser");
	         ObjectInputStream ois = new ObjectInputStream(fis);
	         map = (HashMap) ois.readObject();
	         ois.close();
	         fis.close();
	      }catch(IOException ioe)
	      {
	         ioe.printStackTrace();
	         return null;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Class not found");
	         c.printStackTrace();
	         return null;
	      }
	      return map;
	}
}
