package src.test;

import src.main.fileManager.FileScanner;

//Test application
public class TestFileScanner {
	public static void main(String[] args){
		long l1=System.currentTimeMillis();
		FileScanner s = new FileScanner("E:\\test-files/");
		s.start("E:\\index");
		long l2=System.currentTimeMillis();
		System.out.println(l2-l1);
	}
}
