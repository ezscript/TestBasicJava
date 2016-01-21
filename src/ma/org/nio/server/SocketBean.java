package ma.org.nio.server;

import java.nio.channels.SocketChannel;

public class SocketBean {
	//客户端Socket
	private SocketChannel socketChannel;
	//Socket连接时间
	private long connectedTime;
	//上线提示(握手帧)
	private boolean online;
	//客户端IP
	private String IP;
	//客户端端口
	private int port;
	
	public SocketBean(SocketChannel socketChannel){
		this.socketChannel = socketChannel;
		this.connectedTime = System.currentTimeMillis();
		this.online = false;
		this.IP = socketChannel.socket().getInetAddress().getHostAddress();
		this.port = socketChannel.socket().getPort();
	}
	
	
	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public SocketChannel getSocketChannel() {
		return socketChannel;
	}
	public void setSocketChannel(SocketChannel socketChannel) {
		this.socketChannel = socketChannel;
	}
	public long getConnectedTime() {
		return connectedTime;
	}
	public void setConnectedTime(long connectedTime) {
		this.connectedTime = connectedTime;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String IP) {
		this.IP = IP;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
}
