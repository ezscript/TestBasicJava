package ma.org.nio.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ServerOne implements Runnable {
	// 服务器端SocketChannel
	private ServerSocketChannel server = null;
	// 服务器端的选择器
	private Selector acceptSelector = null;
	
	private final static Map<SocketChannel, SocketBean> mapClientSockets =  new HashMap<SocketChannel, SocketBean>();

	public ServerOne() throws IOException {
		// 创建服务器端SocketChannel
		server = ServerSocketChannel.open();
		// 非阻塞型I/O
		server.configureBlocking(false);
		// 创建选择器
		acceptSelector = Selector.open();
		// 向选择器注册该通道(类型：接受连接)
		server.register(acceptSelector, SelectionKey.OP_ACCEPT);
		// 绑定监听的端口
		server.socket().bind(new java.net.InetSocketAddress(19099));
	}

	public static void main(String[] args) throws Exception {
		ServerOne one = new ServerOne();

		one.startServer();
	}

	public void startServer() {
		new Thread(this).start();
	}

	public void run() {
		System.out.println("nio Server thread start(port:" + 19099 + ")......");
		// 无限循环
		while (true) {
			try {
				// 等待事件
				int readyChannels = acceptSelector.select();
				if(readyChannels == 0) continue;
				
				// 选择一组键
				Iterator<SelectionKey> itKeys = acceptSelector.selectedKeys()
						.iterator();

				// 遍列每个键 For each keys...
				while (itKeys.hasNext()) {
					// 选择当前的键，并移除
					SelectionKey key = (SelectionKey) itKeys.next();
					itKeys.remove();

					// 确定该键的通道是否准备接受新的客户端Socket连接
					if (key.isAcceptable()) {
						// 获取客户端通道
						SocketChannel client = server.accept();
						// 非阻塞型I/O
						synchronized (mapClientSockets) {
							mapClientSockets
									.put(client, new SocketBean(client));
						}
						client.configureBlocking(false);
						// 向选择器注册客户端Socket通道(类型：读取数据）
						System.out.println(client + " is connected");
						// 创建选择器
						occupySelector(client);
					}
					// 该键的通道是否接收到客户端Socket通道的数据
				}// end of while(itKeys.hasNext())
			} catch (Exception e) {
				e.printStackTrace();
			}
		}// end of while(true)
	}

	private void occupySelector(SocketChannel client) throws Exception {
		
		ReceiveFromClient rfc = new ReceiveFromClient();
		
		rfc.putClientChannel(client);
		
		new Thread(rfc).start();
		
		rfc.wakeupSelector();
	}

}
