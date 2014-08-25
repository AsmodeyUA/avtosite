package avtoSite;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class logger {

	private String FileNameLog;
	private OutputStream LogFile;
	public logger() {
		// TODO Auto-generated constructor stub
		FileNameLog = "d:\\work\\Dani\\log.txt";
	}
	public boolean start() throws FileNotFoundException{
		LogFile = new FileOutputStream(FileNameLog);
		return true;
	}
	public boolean stop() throws IOException{
		LogFile.close();
		return true;
	}
	public boolean write(String msg) throws IOException{
		LogFile.write(msg.getBytes());
		return true;
	}
	public boolean write(int msg) throws IOException{
		String msg1=Integer.toString(msg);
		write(msg1);
		return true;
	}
	public boolean writeln(int msg) throws IOException{
		String msg1=Integer.toString(msg);
		writeln(msg1);
		return true;
	}
	public boolean writeln(String msg) throws IOException{
		msg=msg+"\n";
		LogFile.write(msg.getBytes());
		return true;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
