package ma.org.str;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

	public static void main(String[] args) {
		String name = "nbxodService";
		String reg = "*Service";
		
		
		reg = reg.replaceAll("\\*", "\\\\w*");
		System.out.println(reg);
		Pattern r = Pattern.compile(reg);

	    // 现在创建 matcher 对象
	    Matcher m = r.matcher(name);
	    System.out.println(  m.matches());
	    

	    
	}

}
