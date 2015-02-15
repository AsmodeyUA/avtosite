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

public class updatePrice {

	public updatePrice() {
		// TODO Auto-generated constructor stub
	}

	static int id_product(product[] prod, String name) throws IOException {
		for (int i = 0; i < product.getMax_id(); i++)
			if (name.equals(prod[i].getName()))
				return i;
		return -1;
	}

	static boolean equalRel(relatedProd a, relatedProd b) {

		if (a.getProd_id() == b.getProd_id()) {
			if (a.getRelated_id() == b.getRelated_id()) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Putin Huylo!!!");
		category[] categ = new category[1000];
		int q = category.getMax_id();
		categ[q] = new category("Автолюкс");

		Map<String, Integer> analogMap = new HashMap<String, Integer>();

		manufacture[] manufact = new manufacture[1000];
		int q1 = manufacture.getMax_id();
		manufact[q1] = new manufacture("Автолюкс");

		int manufacturerid = manufact[q1].getId();
		int parent = categ[q].getId();
		logger myLog = new logger();
		myLog.start();
		sqllogger sqlLogD = new sqllogger(config.pathSQLD);
		sqllogger sqlLogS = new sqllogger(config.pathSQLS);
		sqlLogD.start();
		sqlLogS.start();

		product[] prod = new product[20000];
		prodImage[] prod_Image = new prodImage[100000];
		relatedProd[] rel_Prod = new relatedProd[100000];

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
			int z = 0;
			for (int i = 0; i < prod1.length; i++) {
				if (prod1[i] != null) {
					z = i;
					prod[i] = prod1[i];
				}
			}
			product.setMax_id(z + 1);
		}

		// Price update
		import_obig1c Data1002 = new import_obig1c(config.pathPrice1);
		if (Data1002.open(myLog)) {
			int startpos = 11;
			int colname = 3; // artikul
			int coldescr = 2;
			int colprice = 7;
			int colcount = 8;
			// Data1002.ReadRows(prod, manufacturerid, parent, startpos,
			// colname,
			// coldescr, colprice, colcount);
			Data1002.ExportToXLS(prod, config.pathDB);
		}

		import_obig1c DataRes = new import_obig1c(config.pathPrice2);
		if (DataRes.open(myLog)) {
			int startpos = 1;
			int colname = 1; // artikul
			int coldescr = 2;
			int colprice = 6;
			int colcount = 4;
			// DataRes.ReadRows(prod, manufacturerid, parent, startpos, colname,
			// coldescr, colprice, colcount);
			DataRes.ExportToXLS(prod, config.pathDB);
		}
		System.out.println(product.getMax_id());
		// Other action
		if (true) {

			System.out.println("IMAGE!!!");

			for (int i = 0; i < 0; i++)
			// for (int i=0; i <10; i++)
			{
				// System.out.println(prod[i].getName());
				SiteApi.foundimage(prod[i].getName(), prod[i].nameDir, "a");
				SiteApi.foundimage(prod[i].getDescription(), prod[i].nameDir,
						"b");
				SiteApi.foundimage("артикул " + prod[i].getName(),
						prod[i].nameDir, "c");

			}
			String temp = "";
			for (int i = 0; i < product.getMax_id(); i++) {
				// System.out.println(prod[i].getName());
				temp = prod[i].getName();
				temp = temp.replaceAll("\\\\", "_lf");
				prod[i].nameDir = temp.replaceAll("/", "_rf");
				temp = prod[i].getDescription();
				while (temp.indexOf("  ") == 0) {
					temp = temp.replaceAll("  ", " ");
				}
				prod[i].setDescription(temp);

				analogMap.put(prod[i].getName(), prod[i].getId());
				Boolean NotFoundMainPic = true;

				try {
					File[] fList;
					String folder_name = prod[i].nameDir;
					File F = new File(config.pathIMGTecdocJ + '\\'
							+ folder_name);
					fList = F.listFiles();

					if (fList.length != 0) {
						for (int j = 0; j < fList.length; j++) {
							String pathTemp = "";
							if (fList[j].isFile()) {
								int prod_Image_id = prodImage.getMax_Img_Id();
								String path1 = fList[j].getName();
								pathTemp = "/data/ImageT/" + folder_name + "/"
										+ path1;
								prod_Image[prod_Image_id] = new prodImage();
								prod_Image[prod_Image_id].setImg_path(pathTemp);
								prod_Image[prod_Image_id].setSort_order();
								prod_Image[prod_Image_id].setProd_id(prod[i]
										.getId());
								prod_Image[prod_Image_id].pathFileO = "";
								prod_Image[prod_Image_id].pathFileN = "";
								// prod_Image[prod_Image_id].pathFileO =
								// config.pathIMGTecdoc
								// + '\\' + folder_name + '\\' + path1;
								// prod_Image[prod_Image_id].pathFileN =
								// config.pathIMGTecdocJ
								// + '\\' + folder_name + '\\' + path1;
								// System.out.println(prod_Image[prod_Image_id].toSql1String());
								if (NotFoundMainPic) {
									prod[i].setImage(pathTemp);
									NotFoundMainPic = false;
								}
							}
						}
					}
				} catch (Exception e) {
					// e.printStackTrace();
					// System.out.println(config.pathIMGTecdoc + '\\'
					// +prod[i].nameDir);

				}
				if (NotFoundMainPic) {
					try {
						File[] fList;
						String folder_name = prod[i].nameDir;
						File F = new File(config.pathIMGJ + '\\' + folder_name);
						fList = F.listFiles();

						if (fList.length != 0) {
							for (int j = 0; j < fList.length; j++) {
								String pathTemp = "";
								if (fList[j].isFile()) {
									int prod_Image_id = prodImage
											.getMax_Img_Id();
									String path1 = fList[j].getName();
									pathTemp = "/data/Image/" + folder_name
											+ "/" + path1;
									prod_Image[prod_Image_id] = new prodImage();
									prod_Image[prod_Image_id]
											.setImg_path(pathTemp);
									prod_Image[prod_Image_id].setSort_order();
									prod_Image[prod_Image_id]
											.setProd_id(prod[i].getId());
									prod_Image[prod_Image_id].pathFileO = "";
									prod_Image[prod_Image_id].pathFileN = "";
									// prod_Image[prod_Image_id].pathFileO=config.pathIMG
									// + '\\'+folder_name+'\\'+path1;
									// prod_Image[prod_Image_id].pathFileN=config.pathIMGJ
									// + '\\'+folder_name+'\\'+path1;
									// System.out.println(prod_Image[prod_Image_id].toSql1String());
									if (NotFoundMainPic) {
										prod[i].setImage(pathTemp);
										NotFoundMainPic = false;
									}
								}
							}
						}
					} catch (Exception e) {
						// e.printStackTrace();
					}
				}
			}

			System.out.println("Read From Analog File!!!");
			System.out.println(analogMap.size());
			String line1, line2 = null;
			Integer keyN1, keyN2 = null;
			BufferedReader inputStream = null;
			try {
				inputStream = new BufferedReader(new FileReader(
						config.pathAnalog));
				while ((inputStream.readLine()) != null) {
					// String[] test = line.trim().split("\\s+");
					// keyN=analogMap.get(key)
					// System.out.println(test[0]);
					// System.out.println(test[1]);
					line1 = inputStream.readLine();
					line2 = inputStream.readLine();
					// System.out.println(line1);
					// System.out.println(analogMap.get(line1));
					keyN1 = analogMap.get(line1);
					keyN2 = analogMap.get(line2);
					if (keyN1 != null) {
						if (keyN2 != null) {
							if (keyN1 != keyN2) {

								int rel_Prod_id = relatedProd.getMax_id();
								rel_Prod[rel_Prod_id] = new relatedProd(keyN1,
										keyN2);

								for (int j = 0; j < rel_Prod_id; j++) {
									if (equalRel(rel_Prod[j],
											rel_Prod[rel_Prod_id]) == true) {
										// System.out.println(rel_Prod[rel_Prod_id].toSql1String());
										rel_Prod[rel_Prod_id] = null;
										relatedProd.setMax_id(relatedProd
												.getMax_id() - 1);
										break;
									}
								}
							}

						}
					}

				}
				inputStream.close();

			} finally {
				if (inputStream != null)
					inputStream.close();
			}

			System.out.println("SQL!!!");

			for (int i = 0; i < category.getMax_id(); i++) {
				sqlLogD.writeln(categ[i].toSqlDel1String());
				sqlLogD.writeln(categ[i].toSqlDel2String());
				sqlLogD.writeln(categ[i].toSqlDel3String());
			}
			for (int i = 0; i < manufacture.getMax_id(); i++) {
				sqlLogD.writeln(manufact[i].toSqlDel1String());
				sqlLogD.writeln(manufact[i].toSqlDel2String());
			}
			for (int i = 0; i < product.getMax_id(); i++) {
				sqlLogD.writeln(prod[i].toSqlDel1String());
				sqlLogD.writeln(prod[i].toSqlDel2String());
				sqlLogD.writeln(prod[i].toSqlDel3String());
				sqlLogD.writeln(prod[i].toSqlDel4String());
			}
			for (int i = 0; i < prodImage.getMax_Img_Id(); i++) {
				sqlLogD.writeln(prod_Image[i].toSqlDel1String());
			}

			for (int i = 0; i < relatedProd.getMax_id(); i++) {
				sqlLogD.writeln(rel_Prod[i].toSqlDelString());
			}

			for (int i = 0; i < category.getMax_id(); i++) {
				sqlLogS.writeln(categ[i].toSql1String());
				sqlLogS.writeln(categ[i].toSql2String());
				sqlLogS.writeln(categ[i].toSql3String());
			}
			for (int i = 0; i < manufacture.getMax_id(); i++) {
				sqlLogS.writeln(manufact[i].toSql1String());
				sqlLogS.writeln(manufact[i].toSql2String());
			}
			for (int i = 0; i < product.getMax_id(); i++) {
				sqlLogS.writeln(prod[i].toSql1String());
				sqlLogS.writeln(prod[i].toSql2String());
				sqlLogS.writeln(prod[i].toSql3String());
				sqlLogS.writeln(prod[i].toSql4String());
			}
			for (int i = 0; i < prodImage.getMax_Img_Id(); i++) {
				sqlLogS.writeln(prod_Image[i].toSql1String());
			}

			for (int i = 0; i < relatedProd.getMax_id(); i++) {
				sqlLogS.writeln(rel_Prod[i].toSql1String());
			}

			System.out.println("END!!!");

			for (int i = 0; i < product.getMax_id(); i++) {
				if (i != id_product(prod, prod[i].getName())) {
					System.out.println("Error : " + prod[i].getName());
					prod[i].setId(99999);
				}

			}

			for (int i = 0; i < relatedProd.getMax_id() - 1; i++) {
				for (int j = i + 1; j < relatedProd.getMax_id(); j++) {
					if (equalRel(rel_Prod[i], rel_Prod[j]) == true) {
						System.out.println(rel_Prod[i].toSql1String());
					}
				}

			}

			// tecdocimport tecd=new tecdocimport();
			// tecd.printSysTable();

			// tecd.exportPictures("hi",);
			// tecd.exportManufacturers();
			// tecd.exportModels();
			System.out.println("END!!!");

			// Product Image Dump

			Gson gsonI = new GsonBuilder().setPrettyPrinting().create();
			String jsonI = gsonI.toJson(prod_Image);

			FileOutputStream outputStreamI = new FileOutputStream(
					config.pathJsonProdImage);

			try {

				outputStreamI.write(jsonI.getBytes());
				outputStreamI.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("END JSON IMAGE WRITE");

			// Product Dump
			Gson gsonP = new GsonBuilder().setPrettyPrinting().create();
			String jsonP = gsonP.toJson(prod);

			FileOutputStream outputStream = new FileOutputStream(
					config.pathJsonProd);

			try {

				outputStream.write(jsonP.getBytes());
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("END JSON PRODUCT WRITE");

			// Category Dump
			Gson gsonC = new GsonBuilder().setPrettyPrinting().create();
			String jsonC = gsonC.toJson(categ);

			FileOutputStream outputStreamC = new FileOutputStream(
					config.pathJsonCategory);

			try {

				outputStreamC.write(jsonC.getBytes());
				outputStreamC.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("END JSON Category WRITE");

			// Category Dump
			Gson gsonM = new GsonBuilder().setPrettyPrinting().create();
			String jsonM = gsonM.toJson(manufact);

			FileOutputStream outputStreamM = new FileOutputStream(
					config.pathJsonManufact);

			try {

				outputStreamM.write(jsonM.getBytes());
				outputStreamM.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("END JSON Manufact WRITE");

			// Category Dump
			Gson gsonR = new GsonBuilder().setPrettyPrinting().create();
			String jsonR = gsonR.toJson(rel_Prod);

			FileOutputStream outputStreamR = new FileOutputStream(
					config.pathJsonRelated);

			try {

				outputStreamR.write(jsonR.getBytes());
				outputStreamR.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("END JSON Related WRITE");

		}
		sqlLogD.stop();
		sqlLogS.stop();
		myLog.stop();

	}

}
