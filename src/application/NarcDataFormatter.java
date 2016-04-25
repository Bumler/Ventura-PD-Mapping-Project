package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NarcDataFormatter {
	/* public static void main (String[] args) throws IOException{
	File f = new File("APPS Help Test.xlsx");
	System.out.println(Formatter(f));
} */
	
	private ArrayList<Integer> flag = new ArrayList<Integer>();
	int rowCount = 1;

public String Formatter (File f) throws IOException{
		//FileInputStream fis = new FileInputStream(new File("APPS Help Test.xlsx"));
		FileInputStream fis = new FileInputStream(f);
	
		//create workbook instance that refers to .xls file
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		
		//create a sheet object to retrieve the sheet
		XSSFSheet sheet = wb.getSheetAt(0);
		
		//that is for evaluate the cell type
		FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
		
		for(Row row: sheet){
			for(Cell cell : row){
				if (cell.getColumnIndex() == 13){
				switch(formulaEvaluator.evaluateInCell(cell).getCellType())
				{
					//if cell is a string
					case Cell.CELL_TYPE_STRING:
						String[] address = cell.getStringCellValue().split(" ");
						StringBuilder sb = new StringBuilder();
						boolean terminate = false;
					
						//checks to see if the string starts with a quotation, if it does it changes all quotes in the string into nothing
						for (int i = 0; i < address.length; i++){
							if (address[i].startsWith("\"")){
								//System.out.println(address[i]);
								//address[i].replace("\"", "");
								address[i] = address[i].substring(1, address[i].length());
								address[i] = address[i].replace('"', ' ');
							}
							
							//if it finds any proposition it deletes it and ignore the rest of the cell by making this the final run of the for loop
							if (address[i].equals(",") || address[i].equals("on") || address[i].equals("at") || address[i].equals("@") || address[i].equals("in") || address[i].equals("of")){
								address[i] = ("");
								terminate = true;
								flag.add(rowCount);
							}
							
							//deletes any token associatted with a '#'
							if (address[i].startsWith("#")){
								address[i] = ("");
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
	      wb.close();
	      
	    return (new String("File Formatted Succesfully"));
	}
}
