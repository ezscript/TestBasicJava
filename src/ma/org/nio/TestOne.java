package ma.org.nio;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class TestOne {

	private static final String f = "C:/test/file/a.txt";

	public static void main(String[] args) throws Exception {
		readB();

	}

	private static void readB() throws IOException {
		FileInputStream fis = new FileInputStream(f);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis,"utf-8"));
		
		for (String line = br.readLine(); line != null; line = br.readLine())
			System.out.println(line);

	}

	public static void read() throws IOException {
		FileInputStream fis = new FileInputStream(f);
		FileChannel fc = fis.getChannel();
		ByteBuffer br = ByteBuffer.allocate(255);
		br.flip();
		for (int max = 0, l = fc.read(br); l != -1 && l != 0; l = fc.read(br,
				max)) {
			byte[] bs = br.array();
			String str = new String(bs, 0, l, "gbk");

			System.out.print(str);
			br.clear();
			max = max + l;
		}
		fc.close();

		fis.close();
		br.flip();
		br.put(2, (byte)12);
		br.compact();
		br.clear();
		br.putChar('c');
		br.capacity();
		
	}

	
	public static void testList(){
		ArrayList list =new ArrayList();
		list.add("");
		list.clear();
	}
}
