package avtoSite;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class import_obig1c {

	public import_obig1c(String name) {
		excelfilenameread = name;
		in = null;
		wb = null;
	}

	public boolean open() {
		try {
			in = new FileInputStream(excelfilenameread);
			wb = new HSSFWorkbook(in);
			// wb1 = new HSSFWorkbook(out);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}


	protected String excelfilenameread;
	protected InputStream in;
	protected static HSSFWorkbook wb;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	//	public void ReadRows(category[] b, int parent_id, int colcategory, int startpos) {
	public void ReadRows( product[] prod, int man_id, int parent_cat, int startpos, 
			int colname, int coldescr, int colprice, int colcount) {
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> it = sheet.iterator();
		String temp_storing = "xxx";
		int colRow = 0;
		while (it.hasNext()) {
			Row row = it.next();
			colRow++; int k=0;
			String name="";
			String description="";
			String price="";
			String count="";


			if ((colRow > startpos)&&(colRow < 100)) {
				Iterator<Cell> cells = row.iterator();
				while (cells.hasNext()) {
					k++;
					Cell cell = cells.next();
					int cellType = cell.getCellType();
					if (k == colname) {
						name = cell.toString();
					}
					if (k == coldescr) {
						description = cell.toString();
					}		
					if (k == colprice) {
						price = cell.toString();
					}
					if (k == colcount) {
						count = cell.toString();
					}					

					switch (cellType) {
					case Cell.CELL_TYPE_STRING:
						temp_storing = cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						temp_storing = "" + cell.getNumericCellValue();
						break;
					default:
						temp_storing = "";
						break;
					}
					//System.out.println(k+"  "+temp_storing);
				}
				if (name!="") {
					System.out.println(colRow+" "+name+"  "+description+" "+price+ " "+count);
					System.out.println(product.getMax_id());
					int prod_id = product.getMax_id();
					prod[prod_id] = new product(name);
					prod[prod_id].setName(name);
					// System.out.println(p[prod_id].getName());
					prod[prod_id].setModel(name);
					prod[prod_id].setDescription(description);
					prod[prod_id].setQuantity(count);
					prod[prod_id].setPrice(price);
					prod[prod_id].setManufacturer_id(man_id);
					prod[prod_id].setCategory_id(parent_cat);
				}
			}
		}
	}

}
