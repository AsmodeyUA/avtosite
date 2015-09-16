package avtoSite;

import java.awt.Label;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;

public class DBDReader {

	public static int SizeDBT = 200000;
	public static int SizeAnalog = 60000;
	private static String[] IDC1Base = new String[SizeDBT];
	private static String[] NAMEBase = new String[SizeDBT];
	private static String[] ARTIKULC1Base = new String[SizeDBT];
	private static String[] CENAC1Base = new String[SizeDBT];
	private static String[] KVOBase = new String[SizeDBT];
	private static String[] ANALOG_IDC1Base = new String[SizeDBT];
	private static String[] AnalogBase = new String[SizeAnalog];
	private static int[] ProductIDJSON = new int[SizeDBT];
	//private static int[] ProductIDJSON = new int[SizeDBT];
	static Set<String> stringSet = new HashSet<String>();
	static Set<String> stringSetNew = new HashSet<String>();
	static Set<String> stringSetOLD = new HashSet<String>();
	private static product[] prod = new product[20000];

	public static void main(String[] args) throws IOException {
		readDBF(config.pathDBF, 0.001,config.pathJsonProd,config.pathJsonProd1,null);

	}

	static int is_product(product[] prod, String name) throws IOException {
		String nameCompare = name.replaceAll(" ", "");
		int rez = 999999;
		for (int i = 0; i < product.getMax_id(); i++){
			if (nameCompare.equals(prod[i].getName()))
				return i;
			if (nameCompare.equals(prod[i].getName().replaceAll(" ", "")))
				return i;
		}
		return rez;
	}
	
	static String remove_zero(String name)  {
		int i=0;
		for(; (i+1<name.length())&&(name.charAt(i)=='0');i++) ;
		return (name.substring(i, name.length()-1));
	}
	
	static int is_product00(product[] prod, String name) throws IOException {
		String nameCompare = remove_zero(name.replaceAll(" ", ""));
		int rez = 999999;
		
		for (int i = 0; i < product.getMax_id(); i++)
			if (nameCompare.equals(remove_zero(prod[i].getName().replaceAll(" ", ""))))
				return i;
		return rez;
	}
	
	static int is_productDescr(product[] prod, String name) throws IOException {
		String nameCompare = name.replaceAll(" ", "");
		int rez = 999999;
		for (int i = 0; i < product.getMax_id(); i++){
			if (nameCompare.equals(prod[i].getDescription()))
				return i;
			if (nameCompare.equals(prod[i].getDescription().replaceAll(" ", "")))
				return i;
		}
		return rez;
	}
	
	/**
	 * @param FilenameDBF
	 * @return
	 * @throws IOException
	 */
	/**
	 * @param FilenameDBF
	 * @return
	 * @throws IOException
	 */
	public static int readDBF(String FilenameDBF, double koef, String JSONFile, String JSONFileNew, Label newL) throws IOException {
		int result = 0;
		int z = 0;
		int q = 0;
		int ss = 0;
		sqllogger sqlLogD = new sqllogger(config.pathSQLD);
		sqllogger sqlLogS = new sqllogger(config.pathSQLS);
		sqllogger sqlLogX = new sqllogger(config.pathSQLX);
		sqlLogD.start();
		sqlLogS.start();
		sqlLogX.start();
		product[] prod1;
		relatedProd[] rel_Prod = new relatedProd[100000];		
		// Json format
		if (true) {
			BufferedReader br = new BufferedReader(new FileReader(
					JSONFile));

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
		if (newL!=null){
			newL.setText("Number of Products - " + product.getMax_id());
			}
		try {
			if (newL!=null){
				newL.setText("Read DBF");
				}
			InputStream f = new FileInputStream(FilenameDBF);

			DBFReader file = new DBFReader(f);
			// PrintStream ps = new PrintStream(System.out, true,
			// "Windows-1251");
			Object[] rowObject;
			file.setCharactersetName("CP1251");

			Integer tempIDC1Base = 0;
			Integer tempAnalogBase = 0;
			while ((rowObject = file.nextRecord()) != null) {
			//while (z<100) {
			//	rowObject = file.nextRecord();
				if (Float.parseFloat(rowObject[6].toString()) > 0) {
					tempIDC1Base = (int) (Float.parseFloat(rowObject[1]
							.toString()));
					tempAnalogBase = (int) (Float.parseFloat(rowObject[8]
							.toString()));
					IDC1Base[tempIDC1Base] = (tempIDC1Base.toString());
					ARTIKULC1Base[tempIDC1Base] = rowObject[2].toString();
					ARTIKULC1Base[tempIDC1Base] = ARTIKULC1Base[tempIDC1Base].replaceAll(" ", "");
					NAMEBase[tempIDC1Base] = rowObject[5].toString();
					CENAC1Base[tempIDC1Base] = rowObject[6].toString();
					KVOBase[tempIDC1Base] = rowObject[7].toString();
					ANALOG_IDC1Base[tempIDC1Base] = tempAnalogBase.toString();
					//System.out.println(NAMEBase[tempIDC1Base]);
					if (tempAnalogBase > 0) {
						q = q + 1;
						String tempSTR = IDC1Base[tempIDC1Base] + ";"
								+ ANALOG_IDC1Base[tempIDC1Base];
						String tempSTRold = ANALOG_IDC1Base[tempIDC1Base] + ";"
								+ IDC1Base[tempIDC1Base];
						if (!stringSet.contains(tempSTR))
							if (!stringSet.contains(tempSTRold))
								stringSet.add(tempSTR);

						AnalogBase[q] = tempSTR;
						// System.out.println(IDC1Base[tempIDC1Base]+";"+ANALOG_IDC1Base[tempIDC1Base]);
					}
				}

				z = z + 1;

			}
			if (newL!=null){
				newL.setText("Read from DBF - " + z + ". Analog pos - " + q);
				}
			int tempvalue = 0;
			for (String valuestr : stringSet) {
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

			int n=0;int qn=0;
			System.out.println("END");
			if (newL!=null){
				newL.setText("Calculating Price");
				}
			for (int i=0; i<SizeDBT;i++){
				if (IDC1Base[i]!=null) {
					qn=qn+1;
					//System.out.println(""+i+ARTIKULC1Base[i]+" "+NAMEBase[i] +KVOBase[i]);
					
					int num1 = is_product(prod,ARTIKULC1Base[i]);
					if (num1 == 999999){
						num1 = is_productDescr(prod,NAMEBase[i]);
						if (num1 == 999999){
							num1 = is_product00(prod,ARTIKULC1Base[i]);
						}						
					}
					if (num1 == 999999){
						n=n+1;
						//sqlLogX.writeln(ARTIKULC1Base[i]+" "+NAMEBase[i]+" " +KVOBase[i]+" "+CENAC1Base[i]);						
					}
					else {
						if (!prod[num1].getTag().equals("")){
							sqlLogX.writeln(num1);
							sqlLogX.writeln(prod[num1].getTag());
							sqlLogX.writeln(ARTIKULC1Base[i]+" "+NAMEBase[i] +KVOBase[i]+CENAC1Base[i]);
						}else{
						//System.out.println(ARTIKULC1Base[i]+" "+NAMEBase[i] +KVOBase[i]+CENAC1Base[i]);
						prod[num1].setTag(ARTIKULC1Base[i]+" i = "+i);
						ProductIDJSON[i] = num1;
						prod[num1].setQuantity(KVOBase[i]);
						prod[num1].setPrice(Double.parseDouble(CENAC1Base[i])*koef);}
						//sqlLogX.writeln(num1);
					};
				
				}
			}
			if (newL!=null){
				newL.setText("Storing JSON. Not found products - " + n);
				}

			 for(String valuestr:stringSetNew){
				System.out.println(valuestr);
			 	String[] arrayID = valuestr.split(";");
			 	int keyN1 = Integer.parseInt(arrayID[0]);
			 	int keyN2 = Integer.parseInt(arrayID[1]);
     		 	if (ProductIDJSON[keyN1]>0){
     		 		if (ProductIDJSON[keyN2]>0){
     		 			int rel_Prod_id = relatedProd.getMax_id();
     		 			rel_Prod[rel_Prod_id] = new relatedProd(ProductIDJSON[keyN1], ProductIDJSON[keyN2]);
     		 		}   		 		
     		 	}
			 }
			
			System.out.println(z);
			System.out.println(qn);
			System.out.println(n);
			
			Gson gsonR = new GsonBuilder().setPrettyPrinting().create();
			String jsonR = gsonR.toJson(prod);

			FileOutputStream outputStreamR = new FileOutputStream(JSONFileNew);

			try {

				outputStreamR.write(jsonR.getBytes());
				outputStreamR.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = 0; i < product.getMax_id(); i++){
				sqlLogS.writeln(prod[i].toSqlUpdatePriceQuantity());
				}
			sqlLogD.writeln("TRUNCATE `oc_product_related`;");
			for (int i = 0; i < relatedProd.getMax_id(); i++){
				sqlLogD.writeln(rel_Prod[i].toSql1String());
				}
			if (newL!=null){
				newL.setText("Done.");
				}
			System.out.println("Done.");
			
		} catch (DBFException ex) {
			System.out.println("Database Exception");
			result = 2;
		} catch (FileNotFoundException ex) {
			System.out.println("File Exception");
			result = 1;
		} finally {
		}
		sqlLogD.stop();
		sqlLogS.stop();
		sqlLogX.stop();

		return result;
	}
}