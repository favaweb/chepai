package test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {

	public static void readExcel(String pathname, PrintStream printStream) {
		try {
//System.out.println("pathname:" + pathname);
			Workbook book = Workbook.getWorkbook(new File(pathname));
			Sheet sheet = book.getSheet(0);
			
			int rows = sheet.getRows();
			for(int i=0; i<rows; i++) {
				Cell[] cell = sheet.getRow(i);
				for(int j=0; j<cell.length; j++) {
					printStream.print(sheet.getCell(j,i).getContents());
//System.out.println(sheet.getCell(j,i).getContents());
				}
				printStream.println();
			}
			book.close();
			
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
