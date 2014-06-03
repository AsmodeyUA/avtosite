package avtoSite;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class cagegory_preparing {
	protected String excelfilenameread;
	protected InputStream in;
	protected static HSSFWorkbook wb;

	public cagegory_preparing() {
	}

	public cagegory_preparing(String name) {
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

	public void ReadRows(category[] b, int parent_id, int colcategory, int startpos) {
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> it = sheet.iterator();
		String category_name = "", old_temp_storing = "xxx", temp_storing = "xxx";
		int colRow = 0;
		int current_parent_id = parent_id;
				
		while (it.hasNext()) {
			Row row = it.next();
			colRow++;
			if (colRow > startpos) {
				Iterator<Cell> cells = row.iterator();
				int k = 1;
				int cat_id = 0;

				while (cells.hasNext()) {
					k++;
					Cell cell = cells.next();
					int cellType = cell.getCellType();
					if (k == colcategory) {
						if (cellType == Cell.CELL_TYPE_STRING) {
							category_name = cell.getStringCellValue();
						}
					}

					if (k == (colcategory + 1)) {
						int z_id = 0;
						old_temp_storing = temp_storing;
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
						// System.out.println(temp_storing);
						// System.out.println(category_name);
						if (temp_storing == "") {
							z_id = 0;
							//System.out.println("++++" + temp_storing);
//							for (int i = 0; i < category.getMax_id(); i++) {
//								if (b[i].getName() == category_name) {
//									z_id = i;
//									cat_id = i;
//									break;
//								}
//							}
							if (z_id == 0) {
								// System.out.println(category.getMax_id());
								int q = category.getMax_id();
								//System.out.println(category_name);
								if (old_temp_storing ==""){
								b[q-1].setParent_id(parent_id);	
								current_parent_id=q;
								System.out.println(category_name);
								}
								b[q] = new category(category_name);
								b[q].setParent_id(current_parent_id);
								cat_id = q;
								// System.out.println(category.getMax_id());
								// System.out.println(b[q].toSql1String());
								// System.out.println(b[q].toSql2String());
							}
						}

					}
				}

			}
		}
	}

}
