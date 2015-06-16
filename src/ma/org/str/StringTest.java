package ma.org.str;

public class StringTest {

	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer("abc");

		System.out.println(sb.append(getObjInt()));
	}
	
	private static Integer getObjInt() {
		return 65538;
	}

	public void a(){
		String strA = new String("A");
		// String strA = "A";
	}

	public static String b(){
		String abc = "abc";
		for(int  i = 0; i< 500000; i++){
			abc = abc + i;
		}
		//StringBuffer
		return abc;
	}
	public static String b_1(){
		int  i = 0;
		StringBuffer sb = new StringBuffer("abc");
		
		for(; i< 500000; i++){
			sb.append(i);// = abc + i;
		}
		return sb.toString();
	}
	
	public void c(){
		String str = "abc";  
        String str1 = "abc";  
        String str2 = new String("abc");  
        System.out.println(str == str1);  
        System.out.println(str1 == "abc");  
        System.out.println(str2 == "abc");  
        System.out.println(str1 == str2);  
        System.out.println(str1 == str2.intern());  
        System.out.println(str2 == str2.intern());  
        System.out.println(str1.hashCode() == str2.hashCode());  
        
        //System.out.println(str1.equals(str2));  
	}
	
	public void d(){
		
	}
}
