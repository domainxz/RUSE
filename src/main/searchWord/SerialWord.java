package src.main.searchWord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import src.main.indexSearch.Operation;
/**
 * @author dxz
 *This class keep a single proximity query
 */
public class SerialWord {
	//This table is shared by all the object
	static HashMap<String, String> hm = new HashMap<String, String>(50);
	//proximity distance
	int distance;
	//The number of key word
	int bound = 0;
	//Temp result of first scanning
	String fileString;
	//the contents of query
	String sentence;
	//Final result
	private String result;
	//Key words
	String[] words;
	//Key words` index
	String[] contents;
	
	public SerialWord(){
		words = new String[10];
		contents = new String[10];
		distance = 0;
	}
	
	public void setContents(String s){
		sentence = s;
		toArray();
	}
	//First scanning, keep the key words and store them into hash map.
	private void toArray(){
		int pos = sentence.indexOf('~');
		if(pos != -1)
			distance = Integer.parseInt(sentence.substring(pos + 1));
		String temp = sentence.substring(sentence.indexOf('"') + 1, sentence.lastIndexOf('"'));
		StringTokenizer st = new StringTokenizer(temp, " ");
		while(st.hasMoreTokens()){
			temp = st.nextToken();
			if(temp != null){
				temp = temp.toLowerCase();
				words[bound++] = temp;
				hm.put(temp, null);
			}
		}
	}
	/**
	 * Update the contents from index
	 *
	 */
	public void getContent(){
		result = "";
		contents[0] = hm.get(words[0]);
		fileString = Operation.And(hm.get(words[0]));
		for(int i = 1; i < bound; i++){
			contents[i] = hm.get(words[i]);
			if(fileString == null || fileString.isEmpty())
				return;
			fileString = Operation.And(fileString, contents[i]);
		}
		process();
	}
	/**
	 * @return Final result of one single query
	 */
	public String getResult(){
		return result;
	}
	/**
	 * According to the distance and key word sequence get final answer
	 *
	 */
	private void process(){
		if(!(fileString == null || fileString.isEmpty())){
			String[] fileNames = fileString.split(" ");
			ArrayList<String> queue = new ArrayList<String>();
			for(int i = 0; i < fileNames.length; i++)
				queue.add(fileNames[i]);
			String temp1 = "";
			for(int i = 0; i < bound; i++){
				int size = queue.size();
				if(size == 0)
					return;
				for(int j = 0; j < size; j++){
					temp1 = queue.remove(0);
					for(int m = 0; m < fileNames.length; m++){
						if(temp1.contains(fileNames[m])){
							if(contents[i] == null){
								result = null;
								return;
							}
							StringTokenizer st = new StringTokenizer(contents[i], " ");
							String temp2 = "";
							while(st.hasMoreTokens()){
								temp2 = st.nextToken(); 
								if(temp2.equals(fileNames[m]))
									break;
							}
							String[] positions = st.nextToken().split("-");
							if(temp1.indexOf(' ') > 0){
								int value1 = Integer.parseInt(temp1.substring(temp1.lastIndexOf(' ') + 1));
								for(int k = 0; k < positions.length; k++){
									int value2 = Integer.parseInt(positions[k]) - value1;
									if(value2 <= distance + 1 && value2 > 0){
										temp2 = temp1 + " " + positions[k];
										queue.add(temp2);
									}
								}
							}
							else{
								for(int k = 0; k < positions.length; k++){
									temp2 = temp1 + " " + positions[k];
									queue.add(temp2);
								}
							}
							break;
						}
					}
				}
			}
		if(queue.size() != 0){
			String subs = "";
			for(int i = 0; i < queue.size(); i++){
				String temp = queue.get(i);
					subs = temp.substring(0, temp.indexOf(' '));
					if(!result.endsWith(subs + " "))
						result += subs + " ";
				}
			}
		}
	}
}