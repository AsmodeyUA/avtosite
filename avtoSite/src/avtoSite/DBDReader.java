package avtoSite;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;

public class DBDReader {


	public static byte[] serialize(Object obj) throws IOException {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(out);
	    os.writeObject(obj);
	    return out.toByteArray();
	}
	
public static void main(String[] args) throws UnsupportedEncodingException {
		readDBF(config.pathDBF);

}


public static int readDBF(String FilenameDBF){
    int result = 0;
    try {

        InputStream f = new FileInputStream(FilenameDBF);
    
        DBFReader file=new DBFReader(f);
        //PrintStream ps = new PrintStream(System.out, true, "Windows-1251");
        Object[]rowObject;
        file.setCharactersetName("CP1251");
        int z=0;
        while ((rowObject=file.nextRecord())!=null)
        {
        	for (int i=0;i<rowObject.length;i++)
            {   
            	String str1 = rowObject[i].toString();
           
            	System.out.println(str1);
            //    ps.println(newString);
            }
        	z=z+1;
        	if (z>100) break;
        }
    } catch (DBFException ex) {
    	System.out.println("Database Exception");
    	result = 2;
    } catch (FileNotFoundException ex) {
    	System.out.println("File Exception");
    	result = 1;
    } finally {}
	
	
	
	return result;
}
}