package src.main.searchWord;

import java.util.ArrayList;
import java.util.StringTokenizer;

import src.main.pattern.AdvancedObserver;
import src.main.pattern.Interpretable;
/**
 * 
 * @author dxz
 *This class is a vector of SerialWord
 */
public class SerialWordSearch implements AdvancedObserver, Interpretable{
	ArrayList<SerialWord> sw = new ArrayList<SerialWord>();
	int bound = 0;
	int count = 0;
	
	public SerialWordSearch(){}
	/**
	 * Implements AdvancedObserver
	 */
	public boolean isEnough() {
		if(bound == SerialWord.hm.size()){
			for(int i  = 0; i < sw.size(); i++)
				sw.get(i).getContent();
			return true;
		}
		return false;
	}
	/**
	 * Implements AdvancedObserver
	 */
	public void update(String words) {
		if(words != null){
			String word = words.substring(0, words.indexOf(':'));
			if(SerialWord.hm.containsKey(word)){
				StringTokenizer st = new StringTokenizer(words, ":");
				st.nextToken();
				SerialWord.hm.put(word, st.nextToken());
				bound++;
			}
		}
		//If indexes finish reading
		else
			bound = SerialWord.hm.size();
	}
	/**
	 * Implements Interpretable
	 */
	public void add(String word) {
		SerialWord temp = new SerialWord();
		temp.setContents(word);
		sw.add(temp);
	}
	/**
	 * Implements Interpretable
	 */
	public String getContents() {
		if(count < sw.size())
			return sw.get(count++).getResult();
		else
			return null;
	}
}
