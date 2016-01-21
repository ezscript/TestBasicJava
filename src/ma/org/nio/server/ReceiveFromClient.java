package ma.org.nio.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import ma.org.nio.util.DESUtil;
import ma.org.nio.util.MyUtils;

public class ReceiveFromClient implements Runnable{
	// 本线程获取数据的选择器
	private Selector readSelector;

	// 选择器的负载，即选择对应的SocketChannel总数
	private int selectorLoader;
	private Object selectorLoaderLock = new Object();

	// 存储客户端通道SocketChannel
	private final Queue<SocketChannel> clientSocketChannels = new ConcurrentLinkedQueue<SocketChannel>();


	//接收客户端数据缓存区
    private ByteBuffer buffer = null;
	//客户端数据的缓存区大小
	private final static int BUFFER_SIZE = 1024*8;
    
	public ReceiveFromClient()throws Exception {
		this.readSelector = Selector.open();
		//缓存区
		this.buffer = ByteBuffer.allocate(BUFFER_SIZE);
	}

	
	public void putClientChannel(SocketChannel client) {
		clientSocketChannels.add(client);
	}

	public int getWaitSize(){
		return clientSocketChannels.size();
	}

	public void wakeupSelector() {
		readSelector.wakeup();
	}


	public void run() {
		System.out.println("ReceiveFromClient run ...");
		
		while (true) {
			try {
				System.out.println("receiveFromClient  start wait...");
				// 等待事件 ， 没事的时候就挂在这里吧
				int readyChannels  =  readSelector.select();
				if(readyChannels == 0)
					continue;
				
				System.out.println("receiveFromClient  start wait over...");
				
				// 选择一组键
				Iterator<SelectionKey> itKeys = readSelector.selectedKeys().iterator();

				//获取全部的客户端通道发送的数据帧
				while (itKeys.hasNext()) {
					// 选择当前的键，并移除
					SelectionKey key = (SelectionKey) itKeys.next();
					itKeys.remove();
					if(key.isConnectable()){
						
					}else if (key.isAcceptable()) {
						
					}else if (key.isReadable()) {
						// 获取客户端发送的数据
						readIt(key);
					}else if (key.isWritable()) {
						
					}
					
				}// end while
				
				//将还没有注册的客户端通道全部注册到选择器
				while(true){
					try{
						if(clientSocketChannels.size()  == 0){
							break;
						}
						//获取还没有选择器的客户端通道
						SocketChannel client = (SocketChannel)(clientSocketChannels.remove());
						
						client.register(readSelector, SelectionKey.OP_READ);
						
					//	client.register(readSelector,  SelectionKey.OP_READ | SelectionKey.OP_WRITE);
					
					}catch(NoSuchElementException e1){
						e1.printStackTrace();
						break;
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}// end while
		
	}


	private void readIt(SelectionKey key) {
		System.out.println("***********************start Read ***********************");
		SocketChannel clientSocket = (SocketChannel) key.channel();
		try{
			
			buffer.clear();
			int numBytesRead = clientSocket.read(buffer);
	 	  	if (numBytesRead < 0){
		  		throw new IOException("none data , Client socket channel is closed");
	 	  	}

	 	  	buffer.flip();
			System.out.println("<< " + numBytesRead + " bytes: " + MyUtils.toHexString(buffer.array(), 0, numBytesRead));
			
	 	  	byte[] frame = Arrays.copyOf(buffer.array(), numBytesRead);
			if (frame != null)
				actionOnFrame(frame,clientSocket);
			else
				System.out.println("Invalid frame (" +  MyUtils.toHexString(buffer.array(), 0, numBytesRead) + ")");
		}catch(IOException e){
			try{
				e.printStackTrace();
			  	System.out.println("ScadaServer close client socket: " + clientSocket);
				clientSocket.close();
			} catch (Exception e1) {
		  		System.out.println("Close client err");
		  		e1.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("***********************end read  ***********************");
	}


	private void actionOnFrame(byte[] frame,SocketChannel clientSocket) throws Exception {
		System.out.println("src:" + new String(frame,"utf-8"));
		String str = DESUtil.decrypt(new String(frame), "haha");
		System.out.println("receive str:" + str);
		byte [] b = "hello world".getBytes();
		ByteBuffer tempBuff = ByteBuffer.wrap(b);
		synchronized(clientSocket){
			clientSocket.write(tempBuff );
		}
		
	}

	public static void main(String [] args){
		int a = 1;
		
		System.out.println(a << 1);
		System.out.println(a << 2);
		System.out.println(a << 3);
		System.out.println(a >> 1);
		System.out.println(2 | 1 );
		System.out.println((2 | 1 ) & 1);
		System.out.println(2 | 1  & 1);
	}
}
