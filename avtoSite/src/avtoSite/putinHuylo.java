package avtoSite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class putinHuylo {

	public putinHuylo() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Putin Huylo!!!");
		category[] categ=new category[1000];
		int q=category.getMax_id(); 
		categ[q]=new category("Автолюкс");
		
		Map<String, Integer> analogMap = new HashMap<String, Integer>();

		manufacture[] manufact=new manufacture[1000];
		int q1=manufacture.getMax_id(); 
		manufact[q1]=new manufacture("Автолюкс");  	

		int startpos=9;
		int manufacturerid=manufact[q1].getId();
		int parent=categ[q].getId(); 
		int colname=1; //artikul
		int coldescr=4;
		int colprice=10;
		int colcount=14;
		logger myLog=new logger();
		myLog.start();
		sqllogger sqlLog=new sqllogger();
		sqlLog.start();
		import_obig1c Data3007 = new import_obig1c(config.pathPrice);
		if (Data3007.open(myLog))
		{
			product[] prod = new product[20000];	
			prodImage[] prod_Image = new prodImage[100000];	
			relatedProd[] rel_Prod = new relatedProd[100000];
			Data3007.ReadRows(prod,manufacturerid,parent,startpos,colname,coldescr,colprice,colcount);

			Data3007.ExportToXLS(prod,config.pathDB);
			/*product.setMax_id(0);		
			product[] prod1 = new product[20000];
			Data3007.ImportFromXLS(prod1,config.pathDB);
			Data3007.ExportToXLS(prod1,config.pathDBold);*/
			System.out.println("IMAGE!!!");		


			for (int i=1269; i <1271; i++)
				//for (int i=0; i <10; i++) 
			{
				//System.out.println(prod[i].getName());
				SiteApi.foundimage(prod[i].getName(),prod[i].getName(),"a");
				SiteApi.foundimage(prod[i].getDescription(),prod[i].getName(),"b");
				SiteApi.foundimage("артикул "+prod[i].getName(),prod[i].getName(),"c");
				
				

			}
					
			for (int i=0; i <product.getMax_id(); i++)
			{
				//System.out.println(prod[i].getName());
				
				analogMap.put(prod[i].getName(),prod[i].getId());				
				
				try{    
					File []fList;       
					String folder_name=prod[i].getName();
					File F = new File(config.pathIMG+'\\'+folder_name);
					fList = F.listFiles();

					if (fList.length!=0){
						for(int j=0; j<fList.length; j++)           
						{
							String pathTemp="";
							if(fList[j].isFile()){
								int prod_Image_id = prodImage.getMax_Img_Id();
								String path1 = fList[j].getName();
								pathTemp="/data/Image/"+folder_name+"/"+path1;
								prod_Image[prod_Image_id] = new prodImage();
								prod_Image[prod_Image_id].setImg_path(pathTemp);
								prod_Image[prod_Image_id].setSort_order();
								prod_Image[prod_Image_id].setProd_id(prod[i].getId());
								//System.out.println(prod_Image[prod_Image_id].toSql1String());
								if (j==1){
									prod[i].setImage(pathTemp);
								}
							}
						}		
					}	
				}catch(Exception e){
					//					e.printStackTrace();
				}
			}
		
			System.out.println("Read From Analog File!!!");	
			System.out.println(analogMap.size());
	        String line1,line2=null;
	        Integer keyN1,keyN2= null;
	        BufferedReader inputStream = null;
	        try {
	            inputStream = new BufferedReader(new FileReader(config.pathAnalog));
	            while ((inputStream.readLine()) != null){
	            	//String[] test =  line.trim().split("\\s+");
	            	//keyN=analogMap.get(key)
	            	//System.out.println(test[0]);
	            	//System.out.println(test[1]);
	            	line1= inputStream.readLine();
	            	line2= inputStream.readLine();
	            	//System.out.println(line1);
	            	//System.out.println(analogMap.get(line1));
	            	keyN1=analogMap.get(line1);
	            	keyN2=analogMap.get(line2);
	            	if (keyN1!=null){
	            		if (keyN2!=null) {
	            			int rel_Prod_id = relatedProd.getMax_id();
	            			rel_Prod[rel_Prod_id] = new relatedProd(keyN1,keyN2);
	            			}
	            		}
	            	
	            }
	            inputStream.close();
	            
	        } finally {
	            if (inputStream != null)
	                inputStream.close();
	        }

			System.out.println("SQL!!!");	

			for(int i=0;i<category.getMax_id();i++) {
				sqlLog.writeln(categ[i].toSqlDel1String());
				sqlLog.writeln(categ[i].toSqlDel2String());
				sqlLog.writeln(categ[i].toSqlDel3String());	
			}
			for(int i=0;i<manufacture.getMax_id();i++) {
				sqlLog.writeln(manufact[i].toSqlDel1String());
				sqlLog.writeln(manufact[i].toSqlDel2String());
			}
			for(int i=0;i<product.getMax_id();i++) {
				sqlLog.writeln(prod[i].toSqlDel1String());
				sqlLog.writeln(prod[i].toSqlDel2String());
				sqlLog.writeln(prod[i].toSqlDel3String());
				sqlLog.writeln(prod[i].toSqlDel4String());
			}		
			for(int i=0;i<prodImage.getMax_Img_Id();i++) {
				sqlLog.writeln(prod_Image[i].toSqlDel1String());
			}
			
			for(int i=0;i<relatedProd.getMax_id();i++) {
				sqlLog.writeln(rel_Prod[i].toSqlDelString());
			}
		
			for(int i=0;i<category.getMax_id();i++) {
				sqlLog.writeln(categ[i].toSql1String());
				sqlLog.writeln(categ[i].toSql2String());
				sqlLog.writeln(categ[i].toSql3String());	
			}
			for(int i=0;i<manufacture.getMax_id();i++) {
				sqlLog.writeln(manufact[i].toSql1String());
				sqlLog.writeln(manufact[i].toSql2String());
			}
			for(int i=0;i<product.getMax_id();i++) {
				sqlLog.writeln(prod[i].toSql1String());
				sqlLog.writeln(prod[i].toSql2String());
				sqlLog.writeln(prod[i].toSql3String());
				sqlLog.writeln(prod[i].toSql4String());
			}	
			for(int i=0;i<prodImage.getMax_Img_Id();i++) {
				sqlLog.writeln(prod_Image[i].toSql1String());
			}

			for(int i=0;i<relatedProd.getMax_id();i++) {
				sqlLog.writeln(rel_Prod[i].toSql1String());
			}
		
			System.out.println("END!!!");
			
			//tecdocimport tecd=new tecdocimport();
//			tecd.printSysTable();

			//tecd.exportPictures("hi",);
//			tecd.exportManufacturers();
			//tecd.exportModels();
			System.out.println("END!!!");
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(prod);
			
			FileOutputStream outputStream=new FileOutputStream(config.pathJsonProd);
	

			try {

			  outputStream.write(json.getBytes());
			  outputStream.close();
			} catch (Exception e) {
			  e.printStackTrace();
			}		
				System.out.println("END JSON WRITE");
				System.out.println(product.getMax_id());
			
		}
		sqlLog.stop();
		myLog.stop();

		
	}

}
