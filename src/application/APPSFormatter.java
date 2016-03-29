package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class APPSFormatter {

	/* public static void main (String[] args) throws IOException{
		File f = new File("APPS Help Test.xlsx");
		System.out.println(Formatter(f));
	} */
	
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
					switch(formulaEvaluator.evaluateInCell(cell).getCellType())
					{
					//if cell is a numeric format
					case Cell.CELL_TYPE_NUMERIC:
						if (cell.getNumericCellValue() == 0){
							cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						    cell.setCellValue("NO");
						}
						else{
							cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						    cell.setCellValue("YES");
						}
						break;
					
					case Cell.CELL_TYPE_STRING:
						break;
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
