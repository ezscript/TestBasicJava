package ma.org.eu;

public enum ConstantEnum {
	A,
	B,
	C,
	D,
	E;
	private ConstantEnum(){
		
	}
	
	public static void main(String [] args){
		read(ConstantEnum.A);
		
		
		
	}
	
	public static void read(ConstantEnum ce){
		switch(ce){
			case A:
				break;
			case B:
				break;
			case C:
				break;
			case D:
				break;
			case E:
				break;
		}
	}
}
