package avtoSite;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;

import javax.imageio.ImageIO;

import com.google.gson.Gson;

public class SiteApi {

	public static int foundimage(String name,String folder_name,String first_letter) throws IOException{
		
		String query = name;
		File file=new File(config.pathIMG+'\\'+folder_name);
		file.mkdir();
		String charset = "UTF-8";
	 	int q=0; 
		for (int i = 0; i < 25; i = i + 5) {
			String address = "http://ajax.googleapis.com/ajax/services/search/images?v=1.0&start="+i+"&q=";
			URL url = new URL(address + URLEncoder.encode(query, charset));
			Reader reader = new InputStreamReader(url.openStream(), charset);
			GoogleResult results = new Gson().fromJson(reader, GoogleResult.class);
		 
			// Show title and URL of each results
			for (int m = 0; m < 4; m++) {
				
		        try {
		        	q++;
		        	//System.out.println("Title: " + results.getResponseData().getResults().get(m).getTitle());
					//System.out.println("URL: " + results.getResponseData().getResults().get(m).getUrl() + "\n");
					URL url1 = new URL(results.getResponseData().getResults().get(m).getUrl());
		        
		        	String fileName = config.pathIMG+'\\'+folder_name+"\\image_"+first_letter+q+".jpg";         
		            BufferedImage img = ImageIO.read(url1);
		            file = new File(fileName);
		            if (!file.exists()) {
		            	file.createNewFile();
		            }
		            ImageIO.write(img, "jpg", file);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			}
		}
		return 0;
	}
	public SiteApi() {
		// TODO Auto-generated constructor stub
	}

}
