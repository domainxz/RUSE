package src.main.searchWord;

import src.main.indexSearch.Operation;
import src.main.pattern.AdvancedObserver;
import src.main.pattern.Interpretable;
/**
 * 
 * @author dxz
 *This class is a vector of single word
 */
public class SingleWordSearch implements AdvancedObserver, Interpretable {
	//Key words
	String[] words;
	//Key words` contents
	String[] contents;
	//The capacity of vector
	int size  = 20;
	//Current used space
	int bound = 0;
	//Current element which will return
	int point = 0;
	
	public SingleWordSearch(){
		words = new String[size];
		contents = new String[size];
	}
	
	public void setSize(int size){
		this.size = size;
		contents = new String[size];
		words = new String[size];
	}
	/**
	 * Implements AdvancedObserver
	 */
	public boolean isEnough() {
		if(point ==  bound){
			point = 0;
			return true;
		}
		return false;
	}
	/**
	 * Implements AdvancedObserver
	 */
	public void update(String word) {
		if(word == null){
			point = bound;
			return;
		}
		int pos1 = word.indexOf(':');
		String ref = word.substring(0, pos1);
		for(int j = 0; j < bound; j++){
			if(words[j].equalsIgnoreCase(ref)){
				contents[j] = Operation.And(word.substring(pos1+1));
				point ++;
				break;
			}
		}
	}
	/**
	 * Implements Interpretable
	 */
	public String getContents() {
		if(point != bound)
			return contents[point++];
		return null;
	}
	/**
	 * Implements Interpretable
	 */
	public void add(String word){
		words[bound++] = word;
	}
}
