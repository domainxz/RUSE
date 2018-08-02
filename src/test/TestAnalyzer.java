package src.test;
//Test the search.class
import java.util.StringTokenizer;

import src.main.indexSearch.Operation;
import src.main.indexSearch.Search;

public class TestAnalyzer {
	public static void main(String[] args){
		long l1 = System.currentTimeMillis();
		Operation.getList("E:/index/");
		Search s = new Search("E:/index/", "fileSize:{001134-001140} AND fileSize:[001134-001140]");
		String s1 = s.getResult();
		display(s1);
		long l2 = System.currentTimeMillis();
		System.out.println(l2-l1);
	}
	
	public static void display(String s){
		if(s != null){
			StringTokenizer st = new StringTokenizer(s);
			while(st.hasMoreTokens())
			System.out.println(st.nextToken());
		}
	}
}
