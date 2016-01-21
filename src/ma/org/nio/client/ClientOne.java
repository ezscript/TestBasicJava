package ma.org.nio.client;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

import ma.org.nio.util.DESUtil;

public class ClientOne {
	
	public static void main(String []args)throws Exception{
	//	testStr();
		testSend();
	}

	private static void testStr()throws Exception {
		String src = "人";
		
		byte[] bs =src.getBytes("UTF-8");
		System.out.println(bs.length);
		
		System.out.println(new String(bs,"UTF-8"));
		
	}

	private static void testSend()throws Exception {
		InetSocketAddress s = new InetSocketAddress("localhost",19099);
		SocketChannel sc = SocketChannel.open(s);
		
		byte [] b = DESUtil.encrypt("看哦士大夫123123123","haha").getBytes();
		ByteBuffer tempBuff = ByteBuffer.wrap(b);
		sc.write(tempBuff );
		
		Character c;
		ByteBuffer read = ByteBuffer.wrap(new byte[1024*2]);
		int numBytesRead = sc.read(read);
		if(numBytesRead > 0){
			byte [] bs = Arrays.copyOf(read.array(), numBytesRead);
			System.out.println(new String(bs,"utf-8"));
		}
		sc.close();
	}
}
