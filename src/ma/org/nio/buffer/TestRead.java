package ma.org.nio.buffer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class TestRead {
	static String fileName = "C:/test/file/a.txt";
	
	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(fileName); //throws FileNotFoundException
		FileChannel fc = fis.getChannel();
		ByteBuffer bb = ByteBuffer.allocate(6);
		
		try {
			fc.read(bb);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	//	 cb.flip();
	}

}
