package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelTest {
	public static void main (String[] args) throws IOException{
		FileInputStream fis = new FileInputStream(new File("dummyFile.xlsx"));
		//FileInputStream fis = new FileInputStream(f);
	
		//create workbook instance that refers to .xls file
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		
		//create a sheet object to retrieve the sheet
		XSSFSheet sheet = wb.getSheetAt(0);
		
		//that is for evaluate the cell type
		FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
		boolean topRow = true;
		
		for(Row row: sheet){
			for(Cell cell : row){
				if (row.getRowNum() == 0){
					switch(formulaEvaluator.evaluateInCell(cell).getCellType())
					{
					//if cell is a numeric format				
					case Cell.CELL_TYPE_STRING:
					if(cell.getStringCellValue().equals("cuck")){
						readExcelContentByColumnIndex(cell.getColumnIndex());
					}
					break;
					}
					}
				//else 
					//topRow = false;
			}
		}
		fis.close();
		
		try {
			FileOutputStream fOut = new FileOutputStream("APPS Help Test.xlsx");
			fOut.close();
		}catch (IOException e){
			wb.close();
			System.out.println("The file is not closed, please close the spreadsheet and try again");
		}
		  FileOutputStream fOut = new FileOutputStream("APPS Help Test.xlsx");
	      wb.write(fOut);
	      fOut.flush();
	      // Done deal. Close it.
	      fOut.close();
	      wb.close();
	      System.out.println("File modification succesful");
	}
	public static void readExcelContentByColumnIndex(int columnIndex){
        try {
            File f = new File("dummyFile.xlsx");
            FileInputStream ios = new FileInputStream(f);
            XSSFWorkbook workbook = new XSSFWorkbook(ios);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
        
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if(row.getRowNum() > 0){ //To filter column headings
                        if(cell.getColumnIndex() == columnIndex){// To match column index
                            switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_NUMERIC:
                                System.out.println(cell.getNumericCellValue()+"");
                                break;
                            case Cell.CELL_TYPE_STRING:
                            	cell.setCellValue("c/d");
                            	System.out.println(cell.getStringCellValue());
                                break;
                            }
                        }
                    }
                }
            }
            ios.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
