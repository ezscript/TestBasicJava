package ma.org.num;

import java.math.BigDecimal;

public class BigDecTest {

	public static void main(String[] args) {
		test();
		double d1 = 0.05+0.01;
		double d2 = 1.0-0.42;
		
		double d10 = 0.05;
		double d11 = 0.01;
		d1 = d10 + d11;
		System.out.println(d1);
		//------------
		System.out.println(addDouble(d10,d11));
		System.out.println(addBig(d10,d11));
	}
	
	public static void test(){
        System.out.println(0.05+0.01);
        System.out.println(1.0-0.42);
        System.out.println(4.015*100);
        System.out.println(123.3/100);
        System.out.println("-------------test over ----------------");
	}
	
	public static double addDouble(double d1, double d2){
		return d1 + d2;
	}
	
	public static double addBig(double d1,double d2){
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
		BigDecimal b2 = new BigDecimal(String.valueOf(d2));
		return b1.add(b2).doubleValue();
	}

}
