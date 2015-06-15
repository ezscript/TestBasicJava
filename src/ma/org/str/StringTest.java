package ma.org.str;

public class StringTest {

	public static void main(String[] args) {
		

		
	}
	
	public void a(){
		String strA = new String("A");
		
		// String strA = "A";
	}

	public void b(){
		String abc = "abc";
		for(int  i = 0; i< 1000; i++){
			abc = abc + i;
		}
		
		//StringBuffer
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
