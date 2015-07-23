package ma.org.num;

import java.util.Random;

public class RandomTest {

	public static void main(String[] args) {
		Random r = new Random();
		for(int i = 0 ; i< 10; i++){
			int a = r.nextInt(100);
			System.out.println(a +";");
		}
		
	}

}
