package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class AddressCleaner {
	
	ArrayList<String> cleanAddresses = new ArrayList<String>();
	
	ArrayList<String[]> startList = new ArrayList<String[]>();
	ArrayList<String[]> findList = new ArrayList<String[]>();
	String[] empty = {""};
	
	/* public static void main (String[] args){
		File f = new File("C:\\Users\\Bumbum\\Desktop\\address.txt");
		AddressCleaner c = new AddressCleaner();
		c.addStartList("#", "deleteW");
		c.addFindList("/", "replaceW", "&");
		c.addStartList("Bogus", "deleteC");
		ArrayList<String>test = c.getCleanedAddresses(f);
		
		for(int i = 0;i < test.size(); i++){
			System.out.println(test.get(i));
		}
	} 
 
	public AddressCleaner(){	
	} */
	
 	public  ArrayList<String> getCleanedAddresses(File file){
 			cleanAddresses = new ArrayList<String>();
 		 	String line = null;	 	
 		          	try {
 		              	// FileReader reads text files in the default encoding.
 		              	FileReader fileReader =
 		                  	new FileReader(file);

 		              	// Always wrap FileReader in BufferedReader.
 		              	BufferedReader bufferedReader =
 		                  	new BufferedReader(fileReader);

 		              	//if the file reading is successful it will go through each address
 		              	//each address is than checked through our paremeters (startList and findList)
 		              	//the line is split by word and that array is send to our checking methods
 		              	while((line = bufferedReader.readLine()) != null) {
 		              		String[] address = line.split(" ");
 		                  	address = startsWithClean(address);
 		                  	address = findClean(address);
 		                  	StringBuilder sb = new StringBuilder();
 		                  	for (int i = 0; i < address.length; i++){
 		                  		System.out.print(address[i] + " ");
 		                  		
 		                  		sb.append(address[i] + " ");
 		                  	}
 		                  	cleanAddresses.add(sb.toString());
 		                  	System.out.println();
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
 		          	return cleanAddresses;
 		          	//ayylmao
 		  	}

 	public  String[] startsWithClean(String[] address){
 		for (int j = 0; j < startList.size(); j++){
 			String s = startList.get(j)[0];
 			for (int i = 0; i < address.length; i++){
 				if (address[i].startsWith(s)){
 					String sw = startList.get(j)[1];
 					switch (sw){
 					case "replaceW":
 						System.out.println("?");
 						address[i] = startList.get(j)[2];
 						break;
 					case "deleteW":
 						address[i] = ("");
 						break;
 					case "deleteC":
 						address = empty;
 						i = Integer.MAX_VALUE-1;
 						break;
 					default: 
 						System.out.println("flag");
 					}
 				}
 			}
 		}
 		return address;
 	}
 	
 	//this functions the same as the starts with method but instead of checking for a starts with it looks for an identical string
 	public String[] findClean(String[] address){
 		for (int j = 0; j < findList.size(); j++){
 			String s = findList.get(j)[0];
 			for (int i = 0; i < address.length; i++){
 				//System.out.println(i);
 				if (address[i].equals(s)){
 					//System.out.println("slay me?");
 					String sw = findList.get(j)[1];
 					switch (sw){
 					case "replaceW":
 						address[i] = findList.get(j)[2];
 						break;
 					case "deleteW":
 						address[i] = ("");
 						break;
 					case "deleteC":
 						address = empty;
 						i = Integer.MAX_VALUE;
 						break;
 					default: 
 						System.out.println("flag");
 					}
 				}
 			}
 		}
 		return address;
 	}
 	
 	public void addStartList (String s, String type){
 		String[] parameter = {s, type};
 		startList.add(parameter);
 	}
 	
 	public void addStartList (String s, String type, String replace){
 		String[] parameter = {s, type, replace};
 		startList.add(parameter);
 	}
 	
 	public void addFindList (String s, String type){
 		String[] parameter = {s, type};
 		findList.add(parameter);
 	}
 	
 	public void addFindList (String s, String type, String replace){
 		String[] parameter = {s, type, replace};
 		findList.add(parameter);
 	}
 	
 	public void addList(String listType, String s, String type) {
 		if(listType.equals("find")) addFindList(s, type);
 		if(listType.equals("start")) addStartList(s, type);
 	}
 	
 	public void addList(String listType, String s, String type, String replace) {
 		if(listType.equals("find")) {
 			if(replace.equals("")) {
 				addFindList(s, type);
 			} 
 			else addFindList(s, type, replace);
 		}
 		else if(listType.equals("start")) {
 			if(replace.equals("")) {
 				addStartList(s, type);
 			}
 			else addStartList(s, type, replace);
 		}
 	}
 	
 	public ArrayList<String[]> getSettings() {
 		ArrayList<String[]> settings = new ArrayList();
 		for(String[] s : startList) {
 			String[] arr;
 			if(s.length == 3) {
 				arr = new String[]{"start", s[0], s[1], s[2] }; 
 			}
 			else {
 				arr = new String[]{"start", s[0], s[1], "" };
 			}
 			settings.add(arr);
 		}
 		for(String[] s : findList) {
 			String[] arr;
 			if(s.length == 3) {
 				arr = new String[]{"find", s[0], s[1], s[2] }; 
 			}
 			else {
 				arr = new String[]{"find", s[0], s[1], "" };
 			}
 			settings.add(arr);
 		}
 		return settings;
 	}
 	
 	public void clearSettings() {
 		startList = new ArrayList<String[]>();
 		findList = new ArrayList<String[]>();
 	}
}
