package avtoSite;

public class relatedProd {
	
	private static int Max_id=0;
	private int prod_id;
	private int related_id;

	public relatedProd() {
		// TODO Auto-generated constructor stub
	}
	
	public relatedProd(int id1,int id2) {
		setMax_id(getMax_id() + 1);	
		prod_id = id1;
		related_id = id2;
	}

	public int getProd_id() {
		return prod_id;
	}

	public void setProd_id(int prod_id) {
		this.prod_id = prod_id;
	}

	public int getRelated_id() {
		return related_id;
	}

	public void setRelated_id(int related_id) {
		this.related_id = related_id;
	}

	public String toSql1String(){
		String sql="INSERT INTO `oc_product_related` (`product_id`, `related_id`)  ";
		sql+="VALUES (";
		sql+=prod_id;
		sql+=",";
		sql+=related_id;
		sql+=");";
		return sql;
	}

	public String toSqlDelString(){
		String sql="";
		sql+="DELETE FROM  `oc_product_related` WHERE product_id =";
		sql+=prod_id;
	    sql+=";";
	    
		return sql;
	}		
	
	public static int getMax_id() {
		return Max_id;
	}

	public static void setMax_id(int max_id) {
		Max_id = max_id;
	}
	
}
