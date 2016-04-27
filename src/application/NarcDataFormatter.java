package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NarcDataFormatter {
	/* public static void main (String[] args) throws IOException{
	File f = new File("APPS Help Test.xlsx");
	System.out.println(Formatter(f));
} */
	
	private ArrayList<Integer> flag = new ArrayList<Integer>();
	private ArrayList<String> original = new ArrayList<String>();
	int rowCount = 1;
	
	File f;
	FileInputStream fis;
	XSSFWorkbook wb;
	XSSFSheet sheet;
	FormulaEvaluator formulaEvaluator;
	
	int white = 0;
	int black = 0;
	int hispanic = 0;
	int asian = 0;
	int other = 0;
	
	int male = 0;
	int female = 0;
	int otherGender = 0;
	
	int total = 0;
	
	public NarcDataFormatter (File f) throws IOException{
		this.f = f;
		
		//FileInputStream fis = new FileInputStream(new File("APPS Help Test.xlsx"));
		fis = new FileInputStream(f);
	
		//create workbook instance that refers to .xls file
		wb = new XSSFWorkbook(fis);
		
		//create a sheet object to retrieve the sheet
		sheet = wb.getSheetAt(0);
		
		//that is for evaluate the cell type
		formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
	}

	public String Formatter () throws IOException{	
		for(Row row: sheet){
			for(Cell cell : row){
				if (cell.getColumnIndex() == 13){
				switch(formulaEvaluator.evaluateInCell(cell).getCellType())
				{
					//if cell is a string
					case Cell.CELL_TYPE_STRING:
						//before we make a change we stick the original version of the string
						//into this array list. To access it the user will input a row and we 
						//take that row and subtract 1 because excel starts counting at 1
						original.add(cell.getStringCellValue());
						
						String[] address = cell.getStringCellValue().split(" ");
						StringBuilder sb = new StringBuilder();
						boolean terminate = false;
						
						for (int i = 0; i < address.length; i++){
						
							//checks to see if the string starts with a quotation, if it does it changes all quotes in the string into nothing
							if (address[i].startsWith("\"")){
								//System.out.println(address[i]);
								//address[i].replace("\"", "");
								address[i] = address[i].substring(1, address[i].length());
								address[i] = address[i].replace('"', ' ');
								flag.add(rowCount);
							}
							
							//if it finds any proposition it deletes it and ignore the rest of the cell by making this the final run of the for loop
							if (address[i].equals(",") || address[i].equals("on") || address[i].equals("at") || address[i].equals("@") || address[i].equals("in") || address[i].equals("of")){
								address[i] = ("");
								terminate = true;
								flag.add(rowCount);
							}
							
							//if a word has a comma at the end it deletes the comma and the rest of the address
							if (address[i].endsWith(",")){
								address[i] = address[i].replace(',', ' ');
								terminate = true;
								flag.add(rowCount);
							}
							
							//deletes any token associated with a '#'
							if (address[i].startsWith("#")){
								address[i] = ("");
								flag.add(rowCount);
							}
							
							//if the program finds an / it checks the rest of the address for another one
							//if it finds one it deletes the / and the rest of the string because that indicates
							//that it is formatted city/state/zip. Otherwise it changes the / to an and
							if (address[i].equals("/")){
								boolean and = true;
								for(int j = i+1; j < address.length; j++){
									if(address[j].equals("/")){
										and = false;
									}
								}
								
								if (and){
									address[i] = "and";
								}
								if (!and){
									address[i] = "";
									terminate = true;
								}
								flag.add(rowCount);
							}
							
							//does the same thing as above but checks for if the / is attached to the end of a word
							if (address[i].endsWith("/")){
								boolean and = true;
								for(int j = i+1; j < address.length; j++){
									if(address[j].endsWith("/")){
										and = false;
									}
								}
								
								if (and){
									address[i] = "and";
								}
								if (!and){
									address[i] = address[i].replace(',', ' ');
									terminate = true;
									terminate = true;
								}
								flag.add(rowCount);
							}
							
							//builds a string with proper spacing
		                  		if (i == 0 || address[i].equals("")){
		                  		System.out.print(address[i]);
		                  		sb.append(address[i]);
		                  		}
		                  		else{
		                  			System.out.print(" "+address[i]);
	 		                  		sb.append(" "+address[i]);
		                  		}
		        				if (terminate){
		        					i = 10000;
		        				}
		        				rowCount++;
						}
						cell.setCellValue(new String(sb));
						break;
				}
				}
				
				//this looks at the ethnicities of those in the narc data
				if (cell.getColumnIndex() == 11){
					total++;
					String ethnicity = cell.getStringCellValue();
					switch (ethnicity){
					case "White": 
						white++;
						break;
					case "Hispanic/Latin/Mexican":
						hispanic++;
						break;
					case "Black":
						black++;
						break;
					case "Asian":
						asian++;
						break;
					default: 
						other++;
						break;
					}
				}
				
				if (cell.getColumnIndex() == 12){
					String gender = cell.getStringCellValue();
					switch (gender){
					case "Male": 
						male++;
						break;
					case "Female":
						female++;
						break;
					default: 
						otherGender++;
						break;
					}
				}
			
				
			}
		}
		fis.close();
		
		try {
			FileOutputStream fOut = new FileOutputStream(f);
			fOut.close();
		}catch (IOException e){
			wb.close();
			return (new String("The file is not closed, please close the spreadsheet and try again"));
		}
		  FileOutputStream fOut = new FileOutputStream(f);
	      wb.write(fOut);
	      fOut.flush();
	      // Done deal. Close it.
	      fOut.close();
	      //wb.close();
	      
	    return (new String("File Formatted Succesfully"));
	}

	//http://stackoverflow.com/questions/14053055/how-to-get-the-value-of-a-cell-at-a-specified-position-in-an-excel-sheet-using-j
	public String restore(String rowText) throws IOException{
		int rowNum;
		try{ 	
			rowNum = Integer.parseInt(rowText);
		}catch(NumberFormatException e){
			return (new String("Please indicate what row you would like to restore"));
		}
			XSSFRow row = sheet.getRow(rowNum-1);
			Cell c = row.getCell(13);
			System.out.println(c.getStringCellValue());
			//we subtract one from the row because rows in excel start at one not 0
			c.setCellValue(original.get(rowNum - 1));

			try {
				FileOutputStream fOut = new FileOutputStream(f);
				fOut.close();
			}catch (IOException e){
				wb.close();
				return (new String("The file is not closed, please close the spreadsheet and try again"));
			}
			  FileOutputStream fOut = new FileOutputStream(f);
		      wb.write(fOut);
		      fOut.flush();
		      // Done deal. Close it.
		      fOut.close();
		      
		      return (new String("Cell Restored Succesfully"));
	}

	public String getDemographics(){
		return ("Total: "+total+"\r\n"
				+"Whites: "+white+", "+(int)((double)white/total*100)+"% \r\n"
				+"Hispanics: "+hispanic+", "+(int)((double)hispanic/total*100)+"% \r\n"
				+"Blacks: "+black+", "+(int)((double)black/total*100)+"% \r\n"
				+"Asians: "+asian+", "+(int)((double)asian/total*100)+"% \r\n"
				+"Other: "+other+", "+(int)((double)other/total*100)+"% \r\n"
				+"\r\n"
				+"Gender Breakdown \r\n"
				+"Males: "+male+", "+(int)((double)male/total*100)+"% \r\n"
				+"Females: "+female+", "+(int)((double)female/total*100)+"% \r\n"
				+"Other: "+otherGender+", "+(int)((double)otherGender/total*100)+"% \r\n");
	}
	
	public ArrayList<Integer> getFlags(){
		return flag;
	}
}
