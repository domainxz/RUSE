package src.main.simpleIndex;

import java.io.File;
import java.util.ArrayList;

/**
 * This class keep a series of derived class of "Index"
 */
public class IndexSet {
	ArrayList<Index> index;
	/**
	 * Create a empty set of "Index" object and add the basic class into the set.
	 */
	public IndexSet(){
		index = new ArrayList<Index>();
		init();
	}
	
	public void addIndex(Index i){
		index.add(i);
	}
	private void init(){
		index.add(new FileNameIndex());
		index.add(new FileSizeIndex());
		index.add(new FileDateIndex());
		index.add(new FileInformation());
	}
	/**
	 * Call the method to create index
	 * @param f The file to be indexed
	 */
	public void creatIndex(File f){
		for(int j = 0; j < index.size(); j++){
			index.get(j).setFile(f);
			index.get(j).createIndex();
		}
	}
	/**
	 * Call the method to store index into file
	 * @param indexDir The index`s directory
	 */
	public void toFile(String indexDir){
		for(int j = 0; j < index.size(); j++)
			index.get(j).toFile(indexDir);
	}
}
