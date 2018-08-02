package src.main.simpleIndex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public abstract class Index {
	File f;
	Comparable[] key;
	String[] contents;
	int size = 200;
	int count = 0;
	
	public void setFile(File f){
		this.f = f;
	}
	/**
	 * @return size the size of the index table
	 */
	public int getSize(){
		return size;
	}
	
	public abstract void createIndex();
	/**
	 * This method will compare the element and append them
	 * @param s the element to be appended into the table
	 */
	protected void createIndex(Comparable s){
		int i = 0;
		for(; i < count; i++)
			if(key[i].compareTo(s) > 0){
				for(int j = count; j != i; j--){
					key[j] = key[j-1];
					contents[j] = contents[j-1];
				}
			break;
			}
		key[i] = s;
		contents[i] = f.getName();
		count ++;
	}
	
	public abstract void toFile(String indexDir);
	/**
	 * This method will keep the index into a file
	 * @param indexDir the path to save the index
	 * @param fileName the index file`s name
	 * @param content the content to be kept into the index file
	 */
	protected void toFile(String indexDir, String fileName, String content){
		File dir = new File(indexDir);
		dir.mkdir();
		File f = new File(indexDir + File.separator + fileName);
		FileOutputStream fout = null; 
        OutputStreamWriter isw = null;
        BufferedWriter bw = null;
        try{
        	fout = new FileOutputStream(f);
			isw  = new OutputStreamWriter(fout);
			bw = new BufferedWriter(isw);
			bw.append(content);
        	bw.close();
        }
        catch(IOException e){e.printStackTrace();}
	}
}
