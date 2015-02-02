package avtoSite;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.*;
import java.util.*;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;

@SuppressWarnings("unused")
public class tecdocimport {
	// private static final String dbDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
	// private static final String dbUrl = "jdbc:odbc:";
	private static final String dbDriver = "transbase.jdbc.Driver";
	private static final String dbUrl = "jdbc:transbase://localhost/";
	private static final String dbDatabase = "TECDOC_CD_1_2013";
	private static final String dbUser = "tecdoc";
	private static final String dbPassword = "tcd_error_0";
	private Connection connection = null;
	private Connection mysqlConnection = null;
	private static final int ukraineCode = 210;
	private static final int russianId = 16;

	public tecdocimport() {
		try {
			Class.forName(dbDriver);
			connection = DriverManager.getConnection(dbUrl + dbDatabase,
					dbUser, dbPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void startmysqlConnection() {
		String url = "jdbc:mysql://localhost/";
		String dbName = "test";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "";
		try {
			Class.forName(driver).newInstance();
			mysqlConnection = DriverManager.getConnection(url + dbName,
					userName, password);
			System.out.println("Connected to the mysql database");
		} catch (Exception e) {
			System.out.println("NO CONNECTION =(");
		}
	}

	private void stopmysqlConnection() {
		try {
			mysqlConnection.close();
			System.out.println("Disconnected from mysql database");
		} catch (Exception e) {
			System.out.println("NO CONNECTION =(");
		}

	}

	public void printSysTable() {
		Statement st;
		try {
			st = connection.createStatement();
			ResultSet result = st.executeQuery("SELECT * FROM systable");
			while (result.next()) {
				String tableName = result.getString(1);

				// Just TecDoc
				if (tableName.indexOf("TOF_") != -1) {
					System.out.println(tableName);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void exportManufacturers() {

		final String tableName = "TOF_MANUFACTURERS";
		final String mysqlTable = "tof_manufacturers";

		final String sqlDropTable = "DROP TABLE IF EXISTS " + mysqlTable;
		final String sqlCreateTable = " CREATE TABLE IF NOT EXISTS "
				+ mysqlTable + " (" + "id int(11) PRIMARY KEY, "
				+ "passenger_car TINYINT, " + "commercial_vehicle TINYINT, "
				+ "axle TINYINT, " + "engine TINYINT, "
				+ "engine_type TINYINT, " + "code VARCHAR(20), "
				+ "brand VARCHAR(100), " + "number SMALLINT" + ")"
		// +" DEFAULT CHARSET=cp1251"
		;

		Statement st;
		Statement mysqlSt;
		try {
			startmysqlConnection();
			System.out.println("Export manufacturers");

			st = connection.createStatement();
			ResultSet result = st
					.executeQuery("SELECT MFA_ID, MFA_PC_MFC, MFA_CV_MFC,"
							+ " MFA_AXL_MFC, MFA_ENG_MFC, MFA_ENG_TYP, MFA_MFC_CODE, MFA_BRAND,"
							+ " MFA_MF_NR FROM " + tableName + " WHERE "
							+ " MFA_CV_CTM SUBRANGE (" + ukraineCode
							+ " CAST INTEGER) = 1 OR"
							+ " MFA_PC_CTM SUBRANGE (" + ukraineCode
							+ " CAST INTEGER) = 1");

			java.sql.ResultSetMetaData metaResult = result.getMetaData();
			int numberOfColumns = metaResult.getColumnCount();

			mysqlSt = mysqlConnection.createStatement();
			System.out.println(sqlDropTable);
			mysqlSt.executeUpdate(sqlDropTable);

			mysqlSt = mysqlConnection.createStatement();
			System.out.println(sqlCreateTable);
			mysqlSt.executeUpdate(sqlCreateTable);

			exportTableData(result, numberOfColumns, mysqlTable);
			stopmysqlConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void exportModels() {

		final String tableName = "TOF_MODELS";
		final String tableCountry = "TOF_COUNTRY_DESIGNATIONS";
		final String tableDescriptions = "TOF_DES_TEXTS";
		final String mysqlTable = "tof_models";

		final String sqlDropTable = "DROP TABLE IF EXISTS " + mysqlTable;
		final String sqlCreateTable = " CREATE TABLE IF NOT EXISTS "
				+ mysqlTable + " (" + "id INT(11) PRIMARY KEY, "
				+ "manufacturer_id int(11), " + "description_id int(11), "
				+ "start_date int(6), " + "end_date int(6), "
				+ "passenger_car TINYINT, " + "commercial_vehicle TINYINT, "
				+ "axle TINYINT, " + "description VARCHAR(255)" + ")"
		// +" DEFAULT CHARSET=cp1251"
		;

		final String[] sqlIndexes = { "ALTER TABLE " + mysqlTable
				+ " ADD INDEX (manufacturer_id)" };

		Statement st;
		Statement mysqlSt;
		try {

			System.out.println("Export models");
			startmysqlConnection();
			st = connection.createStatement();
			ResultSet result = st
					.executeQuery("SELECT MOD_ID, MOD_MFA_ID, MOD_CDS_ID,"
							+ " MOD_PCON_START, MOD_PCON_END, MOD_PC, MOD_CV, MOD_AXL, TEX_TEXT "
							+ " FROM "
							+ tableName
							+ ", "
							+ tableCountry
							+ ", "
							+ tableDescriptions
							+ " WHERE"
							+ " (MOD_PC_CTM SUBRANGE ("
							+ ukraineCode
							+ " CAST INTEGER) = 1 OR"
							+ " MOD_CV_CTM SUBRANGE ("
							+ ukraineCode
							+ " CAST INTEGER) = 1) AND"
							+ " CDS_LNG_ID = "
							+ russianId
							+ " AND CDS_TEX_ID = TEX_ID AND MOD_CDS_ID = CDS_ID"
							+ " AND  CDS_CTM SUBRANGE (" + ukraineCode
							+ " CAST INTEGER) = 1");

			java.sql.ResultSetMetaData metaResult = result.getMetaData();
			int numberOfColumns = metaResult.getColumnCount();

			mysqlSt = mysqlConnection.createStatement();
			System.out.println(sqlDropTable);
			mysqlSt.executeUpdate(sqlDropTable);

			mysqlSt = mysqlConnection.createStatement();
			System.out.println(sqlDropTable);
			mysqlSt.executeUpdate(sqlCreateTable);

			for (String sql : sqlIndexes) {
				mysqlSt = mysqlConnection.createStatement();
				mysqlSt.executeUpdate(sql);
			}

			exportTableData(result, numberOfColumns, mysqlTable);
			stopmysqlConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void exportPictures(String ArtikulShort, String ArtikulFull) {

		final String mysqlTable = "tof_graphics";
		int numPic = 1;

		final String sqlDropTable = "DROP TABLE IF EXISTS " + mysqlTable;
		final String sqlCreateTable = " CREATE TABLE IF NOT EXISTS "
				+ mysqlTable + " (" + "article_id int(11), "
				+ "image VARCHAR(100)" + ") ENGINE=MYISAM DEFAULT CHARSET=utf8";

		final String[] sqlIndexes = { "ALTER TABLE " + mysqlTable
				+ " ADD INDEX (article_id)" };

		Statement st;
		Statement mysqlSt;

		try {
			startmysqlConnection();
			System.out.println("Export pictures");

			st = connection.createStatement();
			String SQl1Temp = "SELECT LGA_ART_ID, GRA_ID, GRA_TAB_NR, GRA_GRD_ID, DOC_EXTENSION, GRA_LNG_ID FROM TOF_LINK_GRA_ART, TOF_GRAPHICS, TOF_DOC_TYPES WHERE DOC_TYPE=GRA_DOC_TYPE AND LGA_GRA_ID=GRA_ID AND LGA_ART_ID=";
			SQl1Temp = SQl1Temp + ArtikulShort
					+ " AND GRA_TAB_NR IS NOT NULL ORDER BY GRA_TAB_NR";
			// System.out.println(SQl1Temp);
			ResultSet result = st.executeQuery(SQl1Temp);

			// ResultSetMetaData metaResult = result.getMetaData();

			mysqlSt = mysqlConnection.createStatement();
			mysqlSt.executeUpdate(sqlDropTable);
			mysqlSt = mysqlConnection.createStatement();
			mysqlSt.executeUpdate(sqlCreateTable);

			for (String sql : sqlIndexes) {
				mysqlSt = mysqlConnection.createStatement();
				mysqlSt.executeUpdate(sql);
			}

			int count = 0;

			ArrayList<Integer> tableNumber = new ArrayList<Integer>();
			ArrayList<Integer> ids = new ArrayList<Integer>();
			ArrayList<Integer> articles = new ArrayList<Integer>();

			while (result.next()) {
				tableNumber.add(count, result.getInt(3));
				ids.add(count, result.getInt(4));
				articles.add(count, result.getInt(1));
				count++;
				// System.out.println(count);
			}

			int limit = 1000;

			String sql = "INSERT INTO " + mysqlTable + " VALUES (?, ?)";
			PreparedStatement ps = (PreparedStatement) mysqlConnection
					.prepareStatement(sql);
			mysqlConnection.setAutoCommit(false);
			for (int i = 0; i < ids.size(); i++) {

				ps.setLong(1, articles.get(i));
				// System.out.println(tableNumber.get(i)+ "/" + ids.get(i) +
				// ".jpg");
				ps.setString(2, tableNumber.get(i) + "/" + ids.get(i) + ".jpg");
				ps.addBatch();

				if (i % 5000 == 4500) {
					// System.out.println(i);
					ps.executeBatch();
					mysqlConnection.commit();
				}
			}
			ps.executeBatch();
			mysqlConnection.commit();
			ps.close();

			for (int i = 0; i < ids.size(); i++) {

				try {
					OutputStream output = null;
					try {
						File dir1 = new File(config.pathIMGTecdoc + "\\"
								+ ArtikulFull + "\\");
						if (!dir1.exists()) {
							dir1.mkdir();
						}

						File file = new File(config.pathIMGTecdoc + "\\"
								+ ArtikulFull + "\\tecd" + numPic + ".jpx");
						numPic++;

						Statement st3 = connection.createStatement();
						String sql1 = "SELECT GRD_GRAPHIC FROM TOF_GRA_DATA_"
								+ tableNumber.get(i) + " WHERE GRD_ID="
								+ ids.get(i);
						ResultSet result2 = st3.executeQuery(sql1);
						if (result2.next()) {
							byte[] data = result2.getBytes(1);

							output = new BufferedOutputStream(
									new FileOutputStream(file));
							output.write(data);
							System.out.println(config.pathIMGTecdoc + "\\"
									+ ArtikulFull + "\\tecd" + (numPic - 1)
									+ ".jpx");
							output.close();
						}
						this.convertImage(config.pathIMGTecdoc + "\\"
								+ ArtikulFull + "\\tecd" + (numPic - 1)
								+ ".jpx");
					} catch (FileNotFoundException ex) {
						ex.printStackTrace();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		stopmysqlConnection();

	}

	public boolean convertImage(String path) {
		try {

			Iterator<ImageReader> iter = ImageIO
					.getImageReadersByFormatName("PPM");
			ImageReader reader = null;

			while (iter.hasNext()) {
				reader = (ImageReader) iter.next();
			}

			if (reader == null) {
				System.out
						.println("Could not locate any Readers for the JPEG 2000 format image.");
				return true;
			} else {
				// System.out.println(reader.getFormatName());
			}

			BufferedImage image = null;
			image = ImageIO.read(new File(path));
			File outputFile = new File(path);
			ImageWriter writer = ImageIO.getImageWritersByFormatName("jpx")
					.next();
			ImageWriteParam param = writer.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // Needed
																		// see
																		// javadoc
			param.setCompressionQuality(0.9F); // Highest quality
			writer.setOutput(ImageIO.createImageOutputStream(outputFile));
			writer.write(null, new IIOImage(image, null, null), param);
			writer.dispose();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	private void exportTableData(ResultSet result, int numberOfColumns,
			String table) {
		try {
			int count = 0;
			int counter = 0;
			result.setFetchSize(2000);
			while (true) {
				String sql = "INSERT INTO " + table + " VALUES";
				while (result.next() && count != 10) {

					if (count != 0) {
						sql += ',';
					}
					sql += "(";
					for (int i = 1; i <= numberOfColumns; i++) {
						if (result.getObject(i) == null) {
							sql += "NULL";
						} else {
							sql += "\"" + ((result.getString(i))) + "\"";
						}
						if (i != numberOfColumns) {
							sql += ", ";
						} else {
							sql += " ";
						}
					}
					sql += ")";
					count++;
					counter++;
				}
				if (count > 0) {
					System.out.println(sql);
					Statement st = mysqlConnection.createStatement();
					st.executeUpdate(sql);
					st.close();
					System.out.println(counter);
					count = 0;
				} else {
					break;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}