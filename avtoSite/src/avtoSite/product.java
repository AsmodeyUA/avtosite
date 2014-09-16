package avtoSite;

/*`oc_product` 

`product_id`, `model`, `sku`, `upc`, `ean`, 
`jan`, `isbn`, `mpn`, `location`, `quantity`,
 `stock_status_id`, `image`, `manufacturer_id`, 
 `shipping`, `price`, `points`, `tax_class_id`, 
 `date_available`, `weight`, `weight_class_id`, `length`,
  `width`, `height`, `length_class_id`, `subtract`, `minimum`, 
  `sort_order`, `status`, `date_added`, `date_modified`, `viewed`) VALUES
  
(28, 'Product 1', '', '', '', '', '', '', '', 
939, 7, 'data/demo/htc_touch_hd_1.jpg', 5, 1, '100.0000', 
200, 9, '2009-02-03', '146.40000000', 2, '0.00000000', '0.00000000', 
'0.00000000', 1, 1, 1, 0, 1, '2009-02-03 16:06:50', '2011-09-30 01:05:39', 0),  

*/
public class product {
	private static int Max_id=0;
	
	
	private int id;
	private String name;
	private int language_id;
	private String description, meta_description, meta_keyword;
	
	private String model;
	public String sku,upc,ean,jan,isbn,mpn,location;
	private String quantity;
	private int stock_status_id;	
	private String image;
	private int manufacturer_id;
	public int shipping;
	private String price;
	public int points,tax_class_id;
	public String date_available;
	public int weight, weight_class_id, length, width, height, length_class_id, subtract, minimum; 
	private int sort_order;	  
	private int status;
	private int store_id;
	private String date_added;
	private String date_modified;	
	public int viewed;
	private String tag;	
	private int category_id;	
    
	public String toSql1String(){
		String sql="INSERT INTO `oc_product` (`product_id`, "
				+ "`model`, `sku`, `upc`, `ean`, `jan`, `isbn`, `mpn`, `location`,"
				+ " `quantity`, `stock_status_id`, `image`, `manufacturer_id`, "
				+ "`shipping`, `price`, `points`, `tax_class_id`, `date_available`, "
				+ "`weight`, `weight_class_id`, `length`, `width`, `height`, "
				+ "`length_class_id`, `subtract`, `minimum`, `sort_order`, `status`, "
				+ "`date_added`, `date_modified`, `viewed`) ";
		sql+="VALUES (";
		sql+=id;
		sql+=",";
		sql+="'"+model+"'";
		sql+=",";		
		sql+="'"+sku+"'";
		sql+=",";		
		sql+="'"+upc+"'";
		sql+=",";		
		sql+="'"+ean+"'";
		sql+=",";	
		sql+="'"+jan+"'";
		sql+=",";	
		sql+="'"+isbn+"'";
		sql+=",";	
		sql+="'"+mpn+"'";
		sql+=",";
		sql+="'"+location+"'";
		sql+=",";	
		sql+=quantity;
		sql+=",";	
		sql+=stock_status_id;
		sql+=",";		
		sql+="'"+image+"'";
		sql+=",";
		sql+=manufacturer_id;
		sql+=",";
		sql+=shipping;
		sql+=",";
		sql+="'"+price+"'";
		sql+=",";		
		sql+=points;
		sql+=",";
		sql+=tax_class_id;
		sql+=",";
		sql+="'"+date_available+"'";
		sql+=",";
		sql+="'"+weight+"'";
		sql+=",";	
		sql+=weight_class_id;
		sql+=",";	
		sql+="'"+length+"'";
		sql+=",";	
		sql+="'"+width+"'";
		sql+=",";	
		sql+="'"+height+"'";
		sql+=",";	
		sql+=length_class_id;
		sql+=",";	
		sql+=subtract;
		sql+=",";	
		sql+=minimum;
		sql+=",";	
		sql+=sort_order;
		sql+=",";	
		sql+=status;
		sql+=",";				
		sql+="'"+date_added+"'";
		sql+=",";
		sql+="'"+date_modified+"'";	
		sql+=",";
		sql+=viewed;
		sql+=");";
		return sql;
	}
	
	
	public String toSql2String(){
		String sql="INSERT INTO `oc_product_description` (`product_id`, `language_id`, `name`, `description`, `meta_description`, `meta_keyword`, `tag`) ";
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
		sql+=",";		
		sql+="'"+tag+"'";
		sql+=");";		
		return sql;
	}	
	
	public String toSql3String(){
		String sql="INSERT INTO `oc_product_to_store` (`product_id`, `store_id`) ";
// 	private int language_id;private String description, meta_description, meta_keyword;
// INSERT INTO `oc_product_to_store` (`product_id`, `store_id`) VALUES (17, 0),
		sql+="VALUES (";
		sql+=id;
		sql+=",";
		sql+=store_id;
		sql+=");";
		return sql;
	}	
	public String toSql4String(){
		String sql="INSERT INTO `oc_product_to_category` (`product_id`, `category_id`) ";
// 	private int language_id;private String description, meta_description, meta_keyword;
// INSERT INTO `oc_product_to_store` (`product_id`, `store_id`) VALUES (17, 0),
		sql+="VALUES (";
		sql+=id;
		sql+=",";
		sql+=category_id;
		sql+=");";
		return sql;
	}		
		
	public String toSqlDel1String(){
		String sql="";
		sql+="DELETE FROM  `oc_product` WHERE product_id =";
		sql+=id;
	    sql+=";";
		return sql;
	}	

	public String toSqlDel2String(){
		String sql="";
		sql+="DELETE FROM  `oc_product_description` WHERE product_id =";
		sql+=id;
	    sql+=";";
		return sql;
	}	
	
	public String toSqlDel3String(){
		String sql="";
	    sql+="DELETE FROM  `oc_product_to_store` WHERE product_id =";
		sql+=id;
	    sql+=";";
		return sql;
	}	
	
	public String toSqlDel4String(){
		String sql="";
		sql+="DELETE FROM  `oc_product_to_category` WHERE product_id =";
		sql+=id;
	    sql+=";";	    	    
		return sql;
	}	
	
	public product(String name1) {
		// TODO Auto-generated constructor stub
		setId();
		model = name1;
		setImage();
		setName(name1);
		setLanguage_id();		
		setDescription(name1);
		setMeta_description();
		setMeta_keyword();
		setStore_id();
		sku=upc=ean=jan=isbn=mpn=location="";
	    setQuantity();
		setStock_status_id();	
		setImage();
		setManufacturer_id();
		shipping=0;
		setPrice();
		points=tax_class_id=0;
		date_available="2000-01-31 00:00:01";
		weight=weight_class_id=length=width=height=length_class_id=subtract=minimum=0; 
		setSort_order();	  
		setStatus();
		setDate_added();
		setDate_modified();
		setStore_id();			
		viewed=0;
		tag="";
		setCategory_id();
	}
	

	public product(int id1,String name1) {
		// TODO Auto-generated constructor stub
		setId(id1);
		model = name1;
		setImage();
		setName(name1);
		setLanguage_id();		
		setDescription(name1);
		setMeta_description();
		setMeta_keyword();
		setStore_id();
		sku=upc=ean=jan=isbn=mpn=location="";
	    setQuantity();
		setStock_status_id();	
		setImage();
		setManufacturer_id();
		shipping=0;
		setPrice();
		points=tax_class_id=0;
		date_available="2000-01-31 00:00:01";
		weight=weight_class_id=length=width=height=length_class_id=subtract=minimum=0; 
		setSort_order();	  
		setStatus();
		setDate_added();
		setDate_modified();
		setStore_id();			
		viewed=0;	
		tag="";	
		setCategory_id();		
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


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public String getQuantity() {
		return quantity;
	}


	public void setQuantity(String quantity) {
		if(quantity==">50") quantity="100"; 
		this.quantity = quantity;
	}
	public void setQuantity() {
		this.quantity = "0";
	}

	public int getStock_status_id() {
		return stock_status_id;
	}

	public void setStock_status_id(int stock_status_id) {
		this.stock_status_id = stock_status_id;
	}

	public void setStock_status_id() {
		this.stock_status_id = 1;
	}

	public int getManufacturer_id() {
		return manufacturer_id;
	}

	public void setManufacturer_id(int manufacturer_id) {
		this.manufacturer_id = manufacturer_id;
	}	
	public void setManufacturer_id() {
		this.manufacturer_id = 0;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}	
	public void setPrice() {
		this.price ="0";
	}


	public int getCategory_id() {
		return category_id;
	}


	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}		
	public void setCategory_id() {
		this.category_id = 0;
	}			
		
	
}
