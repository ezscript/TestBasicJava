package ma.org.proxy.arr;

import java.util.Arrays;

public class TestStore {

	private static byte [] bytes = new byte[2048];
	
	public static void main(String[] args) {
		init();
		
		print(ItemEnum.id);
		
		print(ItemEnum.name);
		
		print(ItemEnum.addr);
	}
	
	public static void init(){
		for(int i = 0 ; i< bytes.length; i++){
			bytes[i] = (byte) (i & 0xff);
		}
	}
	
	public static void print(ItemEnum item ){
		byte[] bs = Arrays.copyOfRange(bytes, item.index,item.len);
		
		System.out.println(item.toString() +":" + Arrays.toString(bs));
	}

	public enum ItemEnum{
		id(0,10),
		name(11,50),
		addr(51,200);
		
		public final int index;
		public final int len;
		
		private ItemEnum(int index,int len){
			this.index = index;
			this.len = len;
		}
	}
}
