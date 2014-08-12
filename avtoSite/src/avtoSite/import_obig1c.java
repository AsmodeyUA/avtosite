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
	public void ReadRows( product[] prod, int colcategory, int startpos) {
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> it = sheet.iterator();
		String temp_storing = "xxx";
		int colRow = 0;
				
		while (it.hasNext()) {
			Row row = it.next();
			colRow++;
			if (colRow > startpos) {
				Iterator<Cell> cells = row.iterator();
				while (cells.hasNext()) {
					Cell cell = cells.next();
					int cellType = cell.getCellType();
					
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
					System.out.println(temp_storing);
				}

			}
		}
	}

}
