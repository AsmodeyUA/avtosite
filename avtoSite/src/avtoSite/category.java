package avtoSite;

/*`oc_category` 
(`category_id`, `image`, `parent_id`, `top`, `column`, 
		`sort_order`, `status`, `date_added`, `date_modified`)
		
(25, '', 0, 1, 1, 3, 1, '2009-01-31 01:04:25', '2011-05-30 12:14:55')

`oc_category_description`
INSERT INTO `oc_category_description` (`category_id`, `language_id`, `name`, `description`, `meta_description`, `meta_keyword`) VALUES
(28, 1, 'Monitors', '', '', ''),

*/
public class category {

	private static int Max_id=0;
	
	private int id;
	private String name;
	private int language_id;
	private String description, meta_description, meta_keyword;
	
	private String image;
	private int parent_id;
	private int top;
	private int column;
	private int sort_order;
	private int status;
	private int store_id;
	private String date_added;
	private String date_modified;	
    
	public String toSql1String(){
		String sql="INSERT INTO `oc_category` (`category_id`, `image`, `parent_id`, `top`, `column`, `sort_order`, `status`, `date_added`, `date_modified`) ";
//		(25, '', 0, 1, 1, 3, 1, '2009-01-31 01:04:25', '2011-05-30 12:14:55')		
		sql+="VALUES (";
		sql+=id;
		sql+=",";
		sql+="'"+image+"'";
		sql+=",";
		sql+=parent_id;
		sql+=",";
		sql+=top;
		sql+=",";
		sql+=column;
		sql+=",";
		sql+=sort_order;
		sql+=",";
		sql+=status;
		sql+=",";
		sql+="'"+date_added+"'";
		sql+=",";
		sql+="'"+date_modified+"'";	
		sql+=");";
		return sql;
	}
	
	
	public String toSql2String(){
		String sql="INSERT INTO `oc_category_description` (`category_id`, `language_id`, `name`, `description`, `meta_description`, `meta_keyword`) ";
// 	private int language_id;private String description, meta_description, meta_keyword;
// (28, 1, 'Monitors', '', '', ''),
		sql+="VALUES (";
		sql+=id;
		sql+=",";
		sql+=language_id;
		sql+=",";		
		sql+="'"+name+"'";
		sql+=",";
		sql+="'"+description+"'";
		sql+=",";
		sql+="'"+meta_description+"'";
		sql+=",";
		sql+="'"+meta_keyword+"'";
		sql+=");";
		return sql;
	}	
	
	public String toSql3String(){
		String sql="INSERT INTO `oc_category_to_store` (`category_id`, `store_id`) ";
// 	private int language_id;private String description, meta_description, meta_keyword;
// INSERT INTO `oc_category_to_store` (`category_id`, `store_id`) VALUES (17, 0),
		sql+="VALUES (";
		sql+=id;
		sql+=",";
		sql+=store_id;
		sql+=");";
		return sql;
	}	
		
	public String toSqlDel1String(){
		String sql="";
		sql+="DELETE FROM  `oc_category` WHERE category_id =";
		sql+=id;
	    sql+=";";    
		return sql;
	}	
	public String toSqlDel2String(){
		String sql="";
		sql+="DELETE FROM  `oc_category_to_store` WHERE category_id =";
		sql+=id;
	    sql+=";";
	    
		return sql;
	}	
	public String toSqlDel3String(){
		String sql="";
		sql+="DELETE FROM  `oc_category_description` WHERE category_id =";
		sql+=id;
	    sql+=";";
	    
		return sql;
	}		
	
	public category(String name1) {
		// TODO Auto-generated constructor stub
		setId();
		setImage();
		setParent_id();
		setTop();
		setColumn();
		setStatus();
		setSort_order();
		setDate_added();
		setDate_modified();
		setName(name1);
		setLanguage_id(2);		
		setDescription(name1);
		setMeta_description();
		setMeta_keyword();
		setStore_id();
	}
	

	public category(int id1,String name1) {
		// TODO Auto-generated constructor stub
		setId(id1);
		setImage();
		setParent_id();
		setTop();
		setColumn();
		setStatus();
		setSort_order();
		setDate_added();
		setDate_modified();
		setName(name1);	
		setLanguage_id(2);
		setDescription(name1);
		setMeta_description();
		setMeta_keyword();
		setStore_id();		
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


	public int getParent_id() {
		return parent_id;
	}


	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}	
	public void setParent_id() {
		this.parent_id = 0;
	}


	public int getTop() {
		return top;
	}


	public void setTop(int top) {
		this.top = top;
	}

	public void setTop() {
		this.top = 0;
	}


	public int getColumn() {
		return column;
	}


	public void setColumn(int column) {
		this.column = column;
	}	
	public void setColumn() {
		this.column = 0;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}

	public void setStatus() {
		this.status = 1;
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

	public String getDate_added() {
		return date_added;
	}

	public void setDate_added(String date_added) {
		this.date_added = date_added;
	}
	
	public void setDate_added() {
		this.date_added = "2000-01-31 00:00:01";
	}	
	
	public String getDate_modified() {
		return date_modified;
	}

	public void setDate_modified(String date_modified) {
		this.date_modified = date_modified;
	}			

	public void setDate_modified() {
		this.date_modified = "2000-01-31 00:00:01";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(int language_id) {
		this.language_id = language_id;
	}		
	public void setLanguage_id() {
		this.language_id = 1;
	}

	public String getMeta_keyword() {
		return meta_keyword;
	}

	public void setMeta_keyword(String meta_keyword) {
		this.meta_keyword = meta_keyword;
	}
	
	public void setMeta_keyword() {
		this.meta_keyword = "";
	}	

	public String getMeta_description() {
		return meta_description;
	}

	public void setMeta_description(String meta_description) {
		this.meta_description = meta_description;
	}
	
	public void setMeta_description() {
		this.meta_description = "";
	}	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
	public void setDescription() {
		this.description = "";
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
