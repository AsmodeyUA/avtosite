package avtoSite;

 
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;



import javax.imageio.ImageIO;

import com.google.gson.Gson;
 
public class TestGoogleSea {
	
	public static int foundimage(String name) throws IOException{
		
		String query = name;
		File file=new File(name);;
		file.mkdir();
		String charset = "UTF-8";
 
		for (int i = 0; i < 25; i = i + 4) {
			String address = "http://ajax.googleapis.com/ajax/services/search/images?v=1.0&start="+i+"&q=";
		 	 
			URL url = new URL(address + URLEncoder.encode(query, charset));
			Reader reader = new InputStreamReader(url.openStream(), charset);
			GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);
		 
			// Show title and URL of each results
			for (int m = 0; m <= 3; m++) {
				System.out.println("Title: " + results.getResponseData().getResults().get(m).getTitle());
				System.out.println("URL: " + results.getResponseData().getResults().get(m).getUrl() + "\n");
				URL url1 = new URL(results.getResponseData().getResults().get(m).getUrl());

		        try {
		            String fileName = name+"\\image"+i+"_"+m+".jpg";         
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
 
	public static void main(String[] args) throws IOException {
 
		foundimage("11.8116-0191.1");
		foundimage("DAEWOO LANOS 1,4 SOHC ПРОВОДА ЗАЖИГАНИЯ");
		
	}
}
 


class GoogleResults{

   private ResponseData responseData;
   public ResponseData getResponseData() { return responseData; }
   public void setResponseData(ResponseData responseData) { this.responseData = responseData; }
   public String toString() { return "ResponseData[" + responseData + "]"; }

   static class ResponseData {
       private List<Result> results;
       public List<Result> getResults() { return results; }
       public void setResults(List<Result> results) { this.results = results; }
       public String toString() { return "Results[" + results + "]"; }
   }

   static class Result {
       private String url;
       private String title;
       public String getUrl() { return url; }
       public String getTitle() { return title; }
       public void setUrl(String url) { this.url = url; }
       public void setTitle(String title) { this.title = title; }
       public String toString() { return "Result[url:" + url +",title:" + title + "]"; }
   }
}
