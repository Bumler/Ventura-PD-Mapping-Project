package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CityInserter {
	ArrayList<String> completeCities = new ArrayList<String>();
	ArrayList<String> beatList = new ArrayList<String>();
	ArrayList<Integer> flag = new ArrayList<Integer>();
	
	public CityInserter(){
	}
	
	//takes in a city file and a beat file and inserts cities into
	public ArrayList<String> insertCities(File cityFile, File beatFile ){
		 String line = null;
		 try {
		 // FileReader reads text files in the default encoding.
		 FileReader fileReader =
		 new FileReader(beatFile);

		 // Always wrap FileReader in BufferedReader.
		 BufferedReader bufferedReader =
		 new BufferedReader(fileReader);

		 while((line = bufferedReader.readLine()) != null) {
		 	beatList.add(line);
		 }

		 // Always close files.
		 bufferedReader.close();
		 }
		 catch(FileNotFoundException ex) {
		 System.out.println(
		 "Unable to open file '" +
		 beatList + "'");
		 }
		 catch(IOException ex) {
		 System.out.println(
		 "Error reading file '"
		 + beatList + "'");
		 // Or we could just do this:
		 // ex.printStackTrace();
		 }
		
		 line = null;
		 int iterator = 0;
		 //we need to flag if there is no beat to enter for a city
		 try {
		 // FileReader reads text files in the default encoding.
		 FileReader fileReader =
		 new FileReader(cityFile);

		 // Always wrap FileReader in BufferedReader.
		 BufferedReader bufferedReader =
		 new BufferedReader(fileReader);
		 

		 while((line = bufferedReader.readLine()) != null) {	
			 System.out.print(iterator+1 +" ");
			 if (line.equals("")){
		 			//System.out.print("flag");
		 			String beat = beatList.get(iterator);
		 			if (beat.startsWith("8") || beat.startsWith("3C")){
		 				completeCities.add("Camarillo");
		 			}
		 			else if(beat.startsWith("3D")){
		 				completeCities.add("Headquarters");
		 			}
		 			else if(beat.startsWith("3A")||beat.startsWith("1")){
		 				completeCities.add("Ojai");
		 			}
		 			else if(beat.startsWith("4F")||beat.startsWith("2")){
		 				completeCities.add("Moorpark");
		 			}
		 			else if(beat.startsWith("4E")||beat.startsWith("9")){
		 				completeCities.add("Thousand Oaks");
		 			}
		 			else if(beat.startsWith("6")){
		 				completeCities.add("Fillmore");
		 			}
		 			else {
		 				completeCities.add(line);
		 				flag.add((iterator)+1);
		 			}
			 }
			 else{
				 completeCities.add(line);
			 }
		 	iterator++;
		 }

		 // Always close files.
		 bufferedReader.close();
		 }
		 catch(FileNotFoundException ex) {
			 ArrayList<String> err = new ArrayList<String>();
		 System.out.println(
		 "Unable to open file '" +
		 cityFile + "'");
		 return err;
		 }
		 catch(IOException ex) {
			 ArrayList<String> err = new ArrayList<String>();
		 err.add(new String(
		 "Error reading file '"
		 + cityFile + "'"));
		 return err;
		 
		 // Or we could just do this:
		 // ex.printStackTrace();
		 }
		
		 return completeCities;
		 }
	
		public ArrayList<Integer> getFlags(){
			return flag;
		}
	}
