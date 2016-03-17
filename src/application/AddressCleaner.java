package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class AddressCleaner {
	
	ArrayList<String> startsList = new ArrayList<String>();
	ArrayList<String> findList = new ArrayList<String>();
	
 
 	public ArrayList<String> getCleanedAddresses(File file){
 		 	String line = null;
 		 	ArrayList<String> cleanAddresses = new ArrayList<String>();
 		 	ArrayList<String> dirtyAddresses = new ArrayList<String>();
 		          	try {
 		              	// FileReader reads text files in the default encoding.
 		              	FileReader fileReader =
 		                  	new FileReader(file);
 		              	System.out.println("FILE READING");
 		              	// Always wrap FileReader in BufferedReader.
 		              	BufferedReader bufferedReader =
 		                  	new BufferedReader(fileReader);
 		              	System.out.println("FILE READING");
 		              	while((line = bufferedReader.readLine()) != null) {
 		              		dirtyAddresses.add(line);
 		                  	String[] address = line.split(" ");
 		                  	//startsWithClean(address);
 		                  	//findClean(address);
 		                  	
 		                  	System.out.println(line);   	
 		              	}
 		              	// Always close files.
 		              	bufferedReader.close();  
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
 		          	return dirtyAddresses;
 		          	//ayylmao
 		  	}

 	/*public  String[] startsWithClean(String[] address){
 		for (String s : startsList){
 			for (int i = 0; i < address.length; i++){
 				if (address[i].startsWith(s)){
 					
 				}
 			}
 		}
 	}
 	
 	public String[] findClean(String address){
 		
 	}
 	*/
}