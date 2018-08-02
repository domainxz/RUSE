package src.main.fileManager;
// This class is to organise the io function from the FileLoader and HashTable
import java.io.*;

import src.main.simpleIndex.IndexSet;
/**
 * 
 * @author dxz
 *
 */
public class FileScanner {
	//Save the path of the files to be scanned
	private String directory;
	IndexSet index;
	/**Keep the list of all the files**/
	
	//Create a default object is forbidden
	private FileScanner(){}
	
	public FileScanner(String directory){
		this.directory = directory;
	}
	
	public String getDirectory(){
		return directory;
	}
	
	public void setDirectory(String directory){
		this.directory = directory;
	}
	
	public void start(String indexDir){
		index = new IndexSet();
		scan(directory);
		HashTable.toFile(indexDir);
		index.toFile(indexDir);
		System.out.println("The index has been built!");
	}
	
	private void scan(String directory){
		File f = new File(directory);
		String temp = "";
		if(f.isDirectory()){
			File[] contents = f.listFiles();
			if(contents.length == 0)
				return;
			for(int i = 0; i < contents.length; i++){
				if(!contents[i].isDirectory()){
					if(!contents[i].isHidden()){
						temp = contents[i].getName();
						FileLoader.load(directory, temp);
						index.creatIndex(contents[i]);
					}
				}
				else
					scan(contents[i].getAbsolutePath());
			}
		}
	}
}