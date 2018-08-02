package src.main.simpleIndex;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileDateIndex extends Index{
	
	public FileDateIndex(){
		key = new Long[size];
		contents = new String[size];
	}
	/**
	 * @param size a new size of the file date index
	 */
	public FileDateIndex(int size){
		this.size = size;
		key = new Long[size];
		contents = new String[size];
	}
	/**
	 * The key to sort is file last modified time
	 */
	public void createIndex(){
		createIndex(new Long(f.lastModified()));
	}
	/**
	 * This method will keep the index format as "yy/mm/dd/hh/mm/ss" and file names
	 */
	public void toFile(String indexDir){
		String content = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		for(int i = 0; i < count; i++)
			content += contents[i] + "|" + sdf.format(new Date((Long)key[i])) + " ";
		toFile(indexDir, "FileDateIndex.txt", content);
	}
}
