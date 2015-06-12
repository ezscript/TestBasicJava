package ma.org.err;

public class TestErrOne {

	public static void main(String [] agrs){
		Exception  exception = new Exception("err");
		RuntimeException runtimeException = new RuntimeException("RuntimeException");
		try{
			throw exception;
		}catch(Exception e){
			//TODO xxx
			e.printStackTrace();
		}finally{
			//TODO ...
		}
		
		throw runtimeException;
	}
}	
