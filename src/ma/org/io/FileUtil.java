package ma.org.io;

import java.io.File;

public class FileUtil {
	
	public static void main(String [] args){
		createDir("c:/util/kettleor/home/pdi_user/kettle");
	}

	private static void createDir(String dir) {
		File f = new File(dir);
		f.mkdirs();
	}
	
	
}
