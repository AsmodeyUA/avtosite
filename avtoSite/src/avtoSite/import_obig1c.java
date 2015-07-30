package avtoSite;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class import_obig1c {

	public import_obig1c(String name) {
		excelfilenameread = name;
		in = null;
		wb = null;
		myLog = null;
	}

	public boolean open(logger myLog1) {
		try {
			in = new FileInputStream(excelfilenameread);
			wb = new HSSFWorkbook(in);
			myLog = myLog1;
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	boolean is_product(product[] prod, String name) throws IOException {
		for (int i = 0; i < product.getMax_id(); i++)
			if (name.equals(prod[i].getName()))
				return true;
		return false;
	}
	
	int getFreeId(product[] prod){
		int i=1;boolean found;
		while(i < product.getMax_id()){
			 found=false;
			 int j=0;
			 while(j<product.getMax_id()){
				if (prod[j].getId()==i){
					found=true;
					j=product.getMax_id();
				}				
				j++;
			 }
			 if (found==false) return i;
			 i++;	
		}
		return (product.getMax_id()+1);
	}

	int id_product(product[] prod, String name) throws IOException {
		for (int i = 0; i < product.getMax_id(); i++)
			if (name.equals(prod[i].getName()))
				return i;
		return -1;
	}

	protected String excelfilenameread;
	protected InputStream in;
	protected static HSSFWorkbook wb;
	protected logger myLog;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void ImportFromXLS(product[] prod, String path) throws IOException {
		InputStream in1 = new FileInputStream(path);
		HSSFWorkbook wb1 = new HSSFWorkbook(in1);
		Sheet sheet = wb1.getSheetAt(0);
		Iterator<Row> it = sheet.iterator();
		String temp = "undefined";
		int tempint = 99;
		while (it.hasNext()) {
			Row row = it.next();
			Iterator<Cell> cells = row.iterator();
			while (cells.hasNext()) {
				Cell cell = cells.next();
				temp = cell.toString();
				System.out.println(temp);

				cell = cells.next();
				temp = cell.toString();
				int prod_id = product.getMax_id();
				prod[prod_id] = new product(temp);
				System.out.println(temp);

				cell = cells.next();
				temp = cell.toString();
				prod[prod_id].setDescription(temp);
				System.out.println(temp);

				cell = cells.next();
				temp = cell.toString();
				prod[prod_id].setModel(temp);
				System.out.println(temp);

				cell = cells.next();
				temp = cell.toString();
				tempint = (Double.valueOf(temp)).intValue();
				prod[prod_id].setCategory_id(tempint);
				System.out.println(tempint);

				cell = cells.next();
				temp = cell.toString();
				tempint = (Double.valueOf(temp)).intValue();
				prod[prod_id].setManufacturer_id(tempint);
				System.out.println(tempint);

				cell = cells.next();
				temp = cell.toString();
				prod[prod_id].setQuantity(temp);

				cell = cells.next();
				temp = cell.toString();
				prod[prod_id].setPrice(temp);
			}
		}
		in1.close();
	}

	@SuppressWarnings("deprecation")
	public void ExportToXLS(product[] prod, String path) throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		FileOutputStream fileOut = new FileOutputStream(path);
		HSSFSheet sheet;
		HSSFRow row;
		Cell cells;
		sheet = wb.createSheet();
		for (int i = 0; i < product.getMax_id(); i++) {
			row = sheet.createRow((short) i);
			cells = row.createCell((short) 0);
			cells.setCellType(Cell.CELL_TYPE_STRING);
			cells.setCellValue(prod[i].getId());
			cells = row.createCell((short) 1);
			cells.setCellType(Cell.CELL_TYPE_STRING);
			cells.setCellValue(prod[i].getName());
			cells = row.createCell((short) 2);
			cells.setCellType(Cell.CELL_TYPE_STRING);
			cells.setCellValue(prod[i].getDescription());
			cells = row.createCell((short) 3);
			cells.setCellType(Cell.CELL_TYPE_STRING);
			cells.setCellValue(prod[i].getModel());
			cells = row.createCell((short) 4);
			cells.setCellType(Cell.CELL_TYPE_STRING);
			cells.setCellValue(prod[i].getCategory_id());
			cells = row.createCell((short) 5);
			cells.setCellType(Cell.CELL_TYPE_STRING);
			cells.setCellValue(prod[i].getManufacturer_id());
			cells = row.createCell((short) 6);
			cells.setCellType(Cell.CELL_TYPE_STRING);
			cells.setCellValue(prod[i].getQuantity());
			cells = row.createCell((short) 7);
			cells.setCellType(Cell.CELL_TYPE_STRING);
			cells.setCellValue(prod[i].getPrice());
		}
		wb.write(fileOut);
		fileOut.close();

	}

	// public void ReadRows(category[] b, int parent_id, int colcategory, int
	// startpos) {
	public void ReadRows(product[] prod, int man_id, int parent_cat,
			int startpos, int colname, int coldescr, int colprice, int colcount)
			throws IOException {
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> it = sheet.iterator();
		String stylestr = "";
		int colRow = 0;

		while (it.hasNext()) {
			Row row = it.next();
			colRow++;
			int k = 0;
			String name = "";
			String description = "";
			String price = "";
			String count = "";
			if (colRow > startpos) {
				Iterator<Cell> cells = row.iterator();
				while (cells.hasNext()) {
					k++;
					Cell cell = cells.next();
					cell.setCellType(Cell.CELL_TYPE_STRING);
					if (k == colname) {
						CellStyle style = cell.getCellStyle();
						stylestr = style.getDataFormatString();
						String tempname = cell.toString();
						if (stylestr == "General") {
							;
						} else {
							int len1 = stylestr.length();
							int len2 = tempname.length();
							for (int i = len2; i < len1; i++)
								tempname = "0" + tempname;
						}
						name = tempname.replaceAll("'", "_");
					}
					if (k == coldescr) {
						description = (cell.toString()).replaceAll("'", "_");
					}
					if (k == colprice) {
						price = cell.toString();
					}
					if (k == colcount) {
						count = cell.toString();
					}
				}
				if (name != "") {
					int prod_has_id = id_product(prod, name);
					if (prod_has_id == -1) {
						int tempFreeID=getFreeId(prod);
						//int prod_id = product.getMax_id();
						int prod_id = product.getMax_id();
						System.out.println("New prod id = "+tempFreeID);
						prod[prod_id] = new product(name);
						prod[prod_id].setModel(name);
						prod[prod_id].setDescription(description);
						prod[prod_id].setQuantity(count);
						prod[prod_id].setPrice(price);
						prod[prod_id].setManufacturer_id(man_id);
						prod[prod_id].setCategory_id(parent_cat);
						prod[prod_id].setId(tempFreeID);
						myLog.writeln(colRow + " " + name + "  " + description
								+ " " + price + " " + count);
						// myLog.writeln(name);
						// myLog.writeln(stylestr);
					} else {
						prod[prod_has_id].setQuantity(count);
						prod[prod_has_id].setPrice(price);
						prod[prod_has_id].setDescription(description);
					}
				}
				name = "";
				description = "";
				price = "";
				count = "";
			}
		}
	}

}
