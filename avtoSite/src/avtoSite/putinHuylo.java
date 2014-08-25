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

	private static final String DEPRECATION = "deprecation";

	public putinHuylo() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings(DEPRECATION)
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.print("Putin Huylo!!!");
		category[] categ=new category[1000];
		int q=category.getMax_id(); 
		categ[q]=new category("Загальний");
		
		manufacture[] manufact=new manufacture[1000];
		int q1=manufacture.getMax_id(); 
		manufact[q1]=new manufacture("Загальний");  	
		
		int startpos=9;
		int manufacturerid=manufact[q1].getId();
		int parent=categ[q].getId(); 
		int colname=1; //artikul
		int coldescr=4;
		int colprice=10;
		int colcount=14;
		logger myLog=new logger();
		myLog.start();
		import_obig1c Data3007 = new import_obig1c(config.pathPrice);
		if (Data3007.open(myLog))
		{
			product[] prod = new product[20000];			
			Data3007.ReadRows(prod,manufacturerid,parent,startpos,colname,coldescr,colprice,colcount);
			Data3007.ExportToXLS(prod,config.pathDB);
			product.setMax_id(0);		
			product[] prod1 = new product[20000];
			Data3007.ImportFromXLS(prod1,config.pathDB);
			Data3007.ExportToXLS(prod1,config.pathDBold);
			myLog.stop();
			System.out.print("End!!!");
			
		}
	}

}
