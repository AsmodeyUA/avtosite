package avtoSite;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;


public class DBDReader {

	public static int SizeDBT =200000;
	public static int SizeAnalog =60000;
	private static String[] IDC1Base=new String[SizeDBT];
	private static String[] NAMEBase=new String[SizeDBT];
	private static String[] ARTIKULC1Base=new String[SizeDBT];
	private static String[] CENAC1Base=new String[SizeDBT];
	private static String[] KVOBase=new String[SizeDBT];
	private static String[] ANALOG_IDC1Base=new String[SizeDBT];
	private static String[] AnalogBase=new String[SizeAnalog];
	static Set<String> stringSet = new HashSet<String>();
	static Set<String> stringSetNew = new HashSet<String>();
	static Set<String> stringSetOLD = new HashSet<String>();
	private static product[] prod = new product[20000];
	
public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
		readDBF(config.pathDBF);

}


public static int readDBF(String FilenameDBF) throws FileNotFoundException{
    int result = 0;
    int z=0;int q=0;        int ss=0;
    
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
				zq=i;
				//if (prod1[i].getId()>zq) zq=prod1[i].getId();
				prod[i] = prod1[i];
				prod[i].setQuantity("0");
			}
		}
		product.setMax_id(zq+1);
	}
	System.out.println(product.getMax_id());
    try {

        InputStream f = new FileInputStream(FilenameDBF);
    
        DBFReader file=new DBFReader(f);
        //PrintStream ps = new PrintStream(System.out, true, "Windows-1251");
        Object[]rowObject;
        file.setCharactersetName("CP1251");

        Integer tempIDC1Base=0;
        Integer tempAnalogBase=0;
        while ((rowObject=file.nextRecord())!=null)
        {
        	if (Float.parseFloat(rowObject[7].toString())>0){
        	tempIDC1Base=(int)(Float.parseFloat(rowObject[1].toString()));
        	tempAnalogBase=(int)(Float.parseFloat(rowObject[8].toString()));
        	IDC1Base[tempIDC1Base]=(tempIDC1Base.toString());
        	ARTIKULC1Base[tempIDC1Base]=rowObject[2].toString();
        	NAMEBase[tempIDC1Base]=rowObject[5].toString();
        	CENAC1Base[tempIDC1Base]=rowObject[6].toString();
        	KVOBase[tempIDC1Base]=rowObject[7].toString();
        	ANALOG_IDC1Base[tempIDC1Base]=tempAnalogBase.toString();
        	//System.out.println(NAMEBase[tempIDC1Base]);
        	if (tempAnalogBase>0) {
        		q=q+1;
        		String tempSTR=IDC1Base[tempIDC1Base]+";"+ANALOG_IDC1Base[tempIDC1Base];
        		String tempSTRold=ANALOG_IDC1Base[tempIDC1Base]+";"+IDC1Base[tempIDC1Base];
        		if (!stringSet.contains(tempSTRold)) stringSet.add(tempSTR);
        		
        		AnalogBase[q]=tempSTR;
        		//System.out.println(IDC1Base[tempIDC1Base]+";"+ANALOG_IDC1Base[tempIDC1Base]);
        	}
        	}

        	z=z+1;
        	
        }
        
        int tempvalue=0;
    	for(String valuestr:stringSet){
    		//System.out.println(valuestr);
    		
    		tempvalue = Integer.parseInt(valuestr.substring(valuestr.indexOf(";")+1));
    		if (IDC1Base[tempvalue]!=null) {
    			stringSetNew.add(valuestr); 
    		}else {
    			stringSetOLD.add(valuestr); 
    		}
    		//System.out.println(tempvalue);
    		ss=ss+1;
    	};
    	//for(String valuestr:stringSetOLD){
    	//	System.out.println(valuestr);
    	//}
        
        
        System.out.println("END");
        //for (int i=0; i<SizeDBT;i++){
        //	if (IDC1Base[i]!=null) System.out.println(NAMEBase[i]);
        //}
    } catch (DBFException ex) {
    	System.out.println("Database Exception");
    	result = 2;
    } catch (FileNotFoundException ex) {
    	System.out.println("File Exception");
    	result = 1;
    } finally {}
    //System.out.println(z+" - "+q);
    //System.out.println(stringSet.size());
    //System.out.println(stringSetNew.size());

    //System.out.println("END");
	
	return result;
}
}