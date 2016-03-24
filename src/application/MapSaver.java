package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class MapSaver {
    public static void main(String [] args)
    {
         HashMap<String, String> map = new HashMap<String , String>();
         //Adding elements to HashMap
         map = makeMap();
         try
         {
                FileOutputStream fos =
                   new FileOutputStream("hashmap.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(map);
                oos.close();
                fos.close();
                System.out.printf("Serialized HashMap data is saved in hashmap.ser");
         }catch(IOException ioe)
          {
                ioe.printStackTrace();
          }
    }
    
	public static HashMap<String, String> makeMap(){
		HashMap <String , String> map = new HashMap<String, String>(); 
		File rd = new File("ReportingDistrict.txt");
		File cc = new File("CommunityCodes.txt");
		String lineRD = null;
		String lineCC = null;
		 try {
		 // FileReader reads text files in the default encoding.
		 FileReader fileReaderRD = new FileReader(rd);
		 FileReader fileReaderCC = new FileReader(cc);
		 // Always wrap FileReader in BufferedReader.
		 BufferedReader bufferedReaderRD = new BufferedReader(fileReaderRD);
		 BufferedReader bufferedReaderCC = new BufferedReader(fileReaderCC);
		 while((lineRD = bufferedReaderRD.readLine()) != null) {
			lineCC = bufferedReaderCC.readLine(); 
		 	map.put(lineRD, lineCC);
		 }

		 // Always close files.
		 bufferedReaderRD.close();
		 bufferedReaderCC.close();
		 }
		 catch(FileNotFoundException ex) {
		 System.out.println(
		 "Unable to open file '" +
		 rd + "' or '" + cc + "'");
		 }
		 catch(IOException ex) {
		 System.out.println(
		 "Error reading file '"
		 + rd + "' or '"+ cc + "'");
		 }
		return map;
	}
}
