package avtoSite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class StartUpdatePrice {

	public StartUpdatePrice() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			BufferedReader br = new BufferedReader(new FileReader(
					config.pathJsonProd));

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


			for (int i = 0; i < product.getMax_id(); i++) {
				System.out.println(obj[i].getName());
				if (!obj[i].artikulShort.isEmpty()) {
					System.out.println(i);

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}