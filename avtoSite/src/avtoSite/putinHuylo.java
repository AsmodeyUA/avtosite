package avtoSite;


public class putinHuylo {

	public putinHuylo() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
            System.out.print("Putin Huylo!!!");
            String path = new String("/local/proba/3007.xls");
            import_obig1c Data3007 = new import_obig1c(path);
            product[] b = null;
            Data3007.open();
            Data3007.ReadRows(b,2,9);
	}

}
