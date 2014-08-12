package avtoSite;

public class putinHuylo {

	public putinHuylo() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print("Putin Huylo!!!");
		category[] categ=new category[1000];
		//            String path = new String("/local/proba/3007.xls");
		String path = new String("D:\\work\\Dani\\3007.xls");
		int q=category.getMax_id(); 
		categ[q]=new category("Загальний");  	
		int startpos=9;
		int manufacturer=0;
		int parent=categ[q].getId(); 
		int colname=1; //artikul
		int coldescr=4;
		int colprice=10;
		int colcount=14;

		import_obig1c Data3007 = new import_obig1c(path);
		product[] prod = new product[20000];
		if (Data3007.open())
			Data3007.ReadRows(prod,manufacturer,parent,startpos,colname,coldescr,colprice,colcount);
	}

}
