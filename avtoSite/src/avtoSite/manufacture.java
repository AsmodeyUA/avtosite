package avtoSite;

// manufacturer_id`, `name`, `image`, `sort_order` manufacturer_id`, `store_id
public class manufacture {
	
	private static int Max_id=0;
	
	private int id;
	private String name;
	private String image;

	private int sort_order;	
	private int store_id;
	
	public manufacture(int id1,String name1) {
		// TODO Auto-generated constructor stub
		setId(id1);
		setImage();
		setSort_order();
		setName(name1);	
		setStore_id();	
	}
	
	public manufacture(String name1) {
		// TODO Auto-generated constructor stub
		setId();
		setImage();
		setSort_order();
		setName(name1);	
		setStore_id();	
	}	
	
	public String toSql1String(){
		String sql="INSERT INTO `oc_manufacturer` (`manufacturer_id`, `name`, `image`, `sort_order`)";
//		(25, '', 0, 1, 1, 3, 1, '2009-01-31 01:04:25', '2011-05-30 12:14:55')		
		sql+="VALUES (";
		sql+=id;
		sql+=",";
		sql+="'"+name+"'";
		sql+=",";
		sql+="'"+image+"'";
		sql+=",";		
		sql+=sort_order;
		sql+=");";
		return sql;
	}	
	
	public String toSql2String(){
		String sql="INSERT INTO `oc_manufacturer_to_store` (`manufacturer_id`, `store_id`) ";
//		(25, '', 0, 1, 1, 3, 1, '2009-01-31 01:04:25', '2011-05-30 12:14:55')		
		sql+="VALUES (";
		sql+=id;
		sql+=",";		
		sql+=store_id;
		sql+=");";
		return sql;
	}	
		
	
	public String toSqlDel1String(){
		String sql="";
		sql+="DELETE FROM  `oc_manufacturer` WHERE manufacturer_id =";
		sql+=id;
	    sql+=";";
	
		return sql;
	}	
	public String toSqlDel2String(){
		String sql="";
		sql+="DELETE FROM  `oc_manufacturer_to_store` WHERE manufacturer_id =";
		sql+=id;
	    sql+=";";
		
		return sql;
	}		
	
	
	public int getSort_order() {
		return sort_order;
	}


	public void setSort_order(int sort_order) {
		this.sort_order = sort_order;
	}	
		
	public void setSort_order() {
		this.sort_order = 1;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	public void setImage() {
		this.image = "";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setId() {
		setMax_id(getMax_id() + 1);
		this.id = getMax_id();
	}
	
	public static int getMax_id() {
		return Max_id;
	}


	public static void setMax_id(int max_id) {
		Max_id = max_id;
	}


	public int getStore_id() {
		return store_id;
	}


	public void setStore_id(int store) {
		this.store_id = store;
	}	
	public void setStore_id() {
		this.store_id = 0;
	}		
	

}
