package avtoSite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class JsonRead {

	public JsonRead() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			BufferedReader br = new BufferedReader(new FileReader(
					config.pathJsonProd1));

			// convert the json string back to object
			Gson gson = new Gson();
			product[] obj = gson.fromJson(br, product[].class);

			System.out.println(obj);
			System.out.println(obj.length);
			int z = 0;

			for (int i = 0; i < obj.length; i++) {
				if (obj[i] != null) {
					z = i + 1;
				}
			}
			product.setMax_id(z);
			System.out.println(product.getMax_id());

			tecdocimport tecd = new tecdocimport();

			for (int i = product.getMax_id(); i < product.getMax_id(); i++) {
				// System.out.println(i);
				if (!obj[i].artikulShort.isEmpty()) {
					System.out.println(i);
					// System.out.println("Start import for "+obj[i].artikulShort);
					tecd.exportPictures(obj[i].artikulShort, obj[i].nameDir);// System.out.println(obj[i].getName());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
