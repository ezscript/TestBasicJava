package ma.org.arr;

public class Test123 {
	
	public static void main(String[] args) {
		show(6);
	}

	private static void show(int n) {
		if(n <= 0 || n >= 10){
			System.out.println("....");
			return;
		}
		int row = n;
		int col = 2*n -1;
		int [] arrLast = new int[col];
		int center = col/2;
	
		for(int rIndex = 1 ; rIndex <= row; rIndex++){
			int maxStep = rIndex;
			int start = center - (maxStep -1);
			int [] arrNow = new int[col];
			for(int step = 0; step < maxStep; step++){
				int num = getNum(arrLast,start);
				arrNow[start] =num;
				start =start +2;
			}
			printRow(arrNow);
			arrLast = arrNow;
		}
		
	}

	private static void printRow(int[] arrNow) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i< arrNow.length ; i++){
			int num = arrNow[i];
			sb.append(num == 0 ?" ": num).append("\t");
		}
		System.out.println(sb);
	}

	private static int getNum(int[] arrLast, int start) {
		if(start == 0 || (arrLast.length  - 1) == start)
			return 1;
		int num = arrLast[start -1] + arrLast[start +1];
		return num ==  0? 1 : num;
	}
	
}
