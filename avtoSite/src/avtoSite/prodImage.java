package avtoSite;

public class prodImage {

	private static int Max_img_id = 0;

	public String toSql1String() {
		String sql = "INSERT INTO `oc_product_image` (`product_image_id`,`product_id`, "
				+ "`image`, `sort_order`) ";
		sql += "VALUES (";
		sql += img_id;
		sql += ",";
		sql += prod_id;
		sql += ",";
		sql += "'" + img_path + "'";
		sql += ",";
		sql += sort_order;
		sql += ");";
		return sql;
	}

	public String toSqlDel1String() {
		String sql = "";
		sql += "DELETE FROM  `oc_product_image` WHERE product_image_id =";
		sql += img_id;
		sql += ";";
		return sql;
	}

	private int img_id;
	private int prod_id;
	private int sort_order;
	private String img_path;
	public String pathFileO;
	public String pathFileN;

	public prodImage() {
		setimg_id();
		// TODO Auto-generated constructor stub
	}

	public int getimg_id() {
		return img_id;
	}

	public void setimg_id(int id) {
		this.img_id = id;
	}

	public void setimg_id() {
		setMax_img_id(getMax_Img_Id() + 1);
		this.img_id = getMax_Img_Id();
	}

	public static int getMax_Img_Id() {
		return Max_img_id;
	}

	public static void setMax_img_id(int max_id) {
		Max_img_id = max_id;
	}

	public int getProd_id() {
		return prod_id;
	}

	public void setProd_id(int prod_id) {
		this.prod_id = prod_id;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public int getSort_order() {
		return sort_order;
	}

	public void setSort_order(int sort_order) {
		this.sort_order = sort_order;
	}

	public void setSort_order() {
		this.sort_order = 0;
	}
}
