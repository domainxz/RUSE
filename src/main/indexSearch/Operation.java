package src.main.indexSearch;
//This class support a set of static operation to do the logical computing
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Operation {
    //Keep the all element int the set
	static String fileList = new String();
	private Operation(){}
	public static void getList(String indexDir){
		try{
			FileInputStream fin = new FileInputStream(indexDir + File.separator + "FileNameIndex.txt");
			InputStreamReader isr = new InputStreamReader(fin);
			BufferedReader br = new BufferedReader(isr);
			fileList = br.readLine();
			br.close();
		}
		catch(IOException e){e.printStackTrace();}
	}
	/**
	 * 
	 * @param s1 
	 * @param s2
	 * @return temp The result of two string`s public part.
	 */
	public static String And(String s1, String s2){
		if(s1 == null)
			return s1;
		if(s2 == null)
			return s2;
		StringTokenizer s = new StringTokenizer(s1, " ");
		String temp = "";
		String e = "";
		while(s.hasMoreTokens()){
			e = s.nextToken();
			if(s2.contains(e + " ") && !temp.contains(e + " "))
				temp += e + " ";
		}
		return temp;
	}
	/**
	 * 
	 * @param s1
	 * @param s2
	 * @return s return the united of the two strings.
	 */
	public static String Or(String s1, String s2){
		if(s1 == null)
			return s2;
		if(s2 == null)
			return s1;
		StringTokenizer s = new StringTokenizer(s2, " ");
		String e = "";
		while(s.hasMoreTokens()){
			e = s.nextToken();
			if(!s1.contains(e + " "))
				s1 += e + " ";
		}
		return s1;
	}
	/**
	 * 
	 * @param s
	 * @return temp return the rest of the element in list.
	 */
	public static String Not(String s){
		if(s == null)
			return fileList;
		StringTokenizer s1 = new StringTokenizer(fileList, " ");
		String e = "";
		String temp = "";
		while(s1.hasMoreTokens()){
			e = s1.nextToken();
			if(!s.contains(e + " ") && !temp.contains(e + " "))
				temp += e + " ";
		}
		return temp;
	}
	/**
	 * Get the file list of a complex search 
	 * @param s Complex search 
	 * @return File list
	 */
	public static String And(String s){
		if(s == null)
			return s;
		StringTokenizer st = new StringTokenizer(s, " ");
		String temp = "";
		String e = "";
		while(st.hasMoreTokens()){
			e = st.nextToken();
			if(fileList.contains(e + " ") && !temp.contains(e + " "))
				temp += e + " ";
		}
		return temp;
	}
}
