package avtoSite;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class putinHuylo {

	public putinHuylo() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.print("Putin Huylo!!!");
		category[] categ=new category[1000];
		//            String path = new String("/local/proba/3007.xls");
		String path = new String("D:\\work\\Dani\\3007.xls");
		String path1 = new String("D:\\work\\Dani\\3007_2.xls");
		int q=category.getMax_id(); 
		categ[q]=new category("Загальний");  	
		int startpos=9;
		int manufacturer=0;
		int parent=categ[q].getId(); 
		int colname=1; //artikul
		int coldescr=4;
		int colprice=10;
		int colcount=14;

		import_obig1c Data3007 = new import_obig1c(path);
		product[] prod = new product[20000];
		if (Data3007.open())
		{
			Data3007.ReadRows(prod,manufacturer,parent,startpos,colname,coldescr,colprice,colcount);

			HSSFWorkbook wb          = new HSSFWorkbook();
			FileOutputStream fileOut = new FileOutputStream(path1);

			HSSFSheet sheet;
			
			sheet = wb.createSheet();
			HSSFRow row;
			Cell cells;
			for(int i=0;i<product.getMax_id();i++){
				row     = sheet.createRow((short)i);
				cells =row.createCell((short)0);
				cells.setCellType(Cell.CELL_TYPE_STRING);
				cells.setCellValue(prod[i].getId());					
				cells =row.createCell((short)1);
				cells.setCellType(Cell.CELL_TYPE_STRING);
				cells.setCellValue(prod[i].getName());	
				cells =row.createCell((short)2);
				cells.setCellType(Cell.CELL_TYPE_STRING);
				cells.setCellValue(prod[i].getDescription());					
			}
			

			startpos=9;
			Data3007.ReadRows(prod,manufacturer,parent,startpos,colname,coldescr,colprice,colcount);
			
			sheet = wb.createSheet();
			for(int i=0;i<product.getMax_id();i++){
				row     = sheet.createRow((short)i); 
				cells =row.createCell((short)0);
				cells.setCellType(Cell.CELL_TYPE_STRING);
				cells.setCellValue(prod[i].getId());					
				cells =row.createCell((short)1);
				cells.setCellType(Cell.CELL_TYPE_STRING);
				cells.setCellValue(prod[i].getName());	
				cells =row.createCell((short)2);
				cells.setCellType(Cell.CELL_TYPE_STRING);
				cells.setCellValue(prod[i].getDescription());					
			}
						

			wb.write(fileOut);
			fileOut.close();
			
		}
	}

}
