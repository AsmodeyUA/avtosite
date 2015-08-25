package avtoSite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;
import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;

public class XLSMReader {

	public static int SizeDBT = 200000;
	public static int SizeAnalog = 60000;
	private static String[] IDC1Base = new String[SizeDBT];
	private static String[] NAMEBase = new String[SizeDBT];
	private static String[] ARTIKULC1Base = new String[SizeDBT];
	private static String[] CENAC1Base = new String[SizeDBT];
	private static String[] KVOBase = new String[SizeDBT];
	private static String[] ANALOG_IDC1Base = new String[SizeDBT];
	private static String[] AnalogBase = new String[SizeAnalog];
	static Set<String> stringSet = new HashSet<String>();
	static Set<String> stringSetNew = new HashSet<String>();
	static Set<String> stringSetOLD = new HashSet<String>();
	private static product[] prod = new product[20000];

	public static void main(String[] args) throws IOException {
		readDBF(config.pathXLSX);

	}

	static boolean is_product(product[] prod, String name) throws IOException {
		for (int i = 0; i < product.getMax_id(); i++)
			if (name.equals(prod[i].getName()))
				return true;
		return false;
	}
	
	public static int readDBF(String FilenameXLS) throws IOException {
		int result = 0;
		int z = 0;
		int q = 0;
		int ss = 0;

		// Json format
		if (true) {
			product[] prod1;
			BufferedReader br = new BufferedReader(new FileReader(
					config.pathJsonProd));

			// convert the json string back to object
			Gson gson = new Gson();
			prod1 = gson.fromJson(br, product[].class);
			System.out.println(prod1);
			System.out.println(prod1.length);
			int zq = 0;
			for (int i = 0; i < prod1.length; i++) {
				if (prod1[i] != null) {
					zq = i;
					// if (prod1[i].getId()>zq) zq=prod1[i].getId();
					prod[i] = prod1[i];
					prod[i].setQuantity("0");
				}
			}
			product.setMax_id(zq + 1);
		}
		System.out.println(product.getMax_id());
		try {


            // Finds the workbook instance for XLSX file
            @SuppressWarnings("deprecation")
			XSSFWorkbook myWorkBook = new XSSFWorkbook (FilenameXLS);
           
            // Return first sheet from the XLSX workbook
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);


		    Integer tempIDC1Base = 0;
			Integer tempAnalogBase = 0;

		    Iterator <Row> rowIterator = mySheet.iterator();
		    while(rowIterator.hasNext()) {
		        Row row = rowIterator.next();
		        System.out.println("s");


				z = z + 1;

			}

			int tempvalue = 0;
			for (String valuestr : stringSet) {
				// System.out.println(valuestr);

				tempvalue = Integer.parseInt(valuestr.substring(valuestr
						.indexOf(";") + 1));
				if (IDC1Base[tempvalue] != null) {
					stringSetNew.add(valuestr);
				} else {
					stringSetOLD.add(valuestr);
				}
				// System.out.println(tempvalue);
				ss = ss + 1;
			}
			;
			// for(String valuestr:stringSetOLD){
			// System.out.println(valuestr);
			// }
			int n=0;int qn=0;
			System.out.println("END");
			for (int i=0; i<SizeDBT;i++){
				if (IDC1Base[i]!=null) {
					qn=qn+1;
					if (!is_product(prod,ARTIKULC1Base[i])){
						System.out.println(ARTIKULC1Base[i]+" "+NAMEBase[i]);	
						n=n+1;
					};
				}
			}
			System.out.println(qn);
			System.out.println(n);
		} catch (DBFException ex) {
			System.out.println("Database Exception");
			result = 2;
		} catch (FileNotFoundException ex) {
			System.out.println("File Exception");
			result = 1;
		} finally {
		}
		 System.out.println(z+" - "+q);
		// System.out.println(stringSet.size());
		// System.out.println(stringSetNew.size());

		// System.out.println("END");

		return result;
	}
}