package src.main.fileManager;

import java.util.ArrayList;
/**
 * 
 * @author dxz
 *This class used when the system create its index 
 */
public class PositionNode{
	//Current element postion 
	int count = 0;
	//Keep the file name
	ArrayList<String> fileNames;
	//Keep the word position of related file name
	ArrayList<String> position;
	
	public PositionNode(){
		fileNames = new ArrayList<String>();
		position = new ArrayList<String>();
	}
	/**Confirm if the array contains this file name
	 * @param fileName The current file name
	 */
	public boolean contains(String fileName){
		return fileNames.get(count - 1).equals(fileName);
	}
	/**When meet a new file, use this class
	 * @param fileName The current file name
	 * @param pos The first word position of current file
	 */
	public void add(String fileName, int pos){
		fileNames.add(fileName);
		position.add(String.valueOf(pos));
		count++;
	}
	/**When meet a same word, use this class
	 * @param pos The current postion of the word
	 */
	public void add(int pos){
		String temp = position.remove(count - 1);
		temp += "-" + pos;
		position.add(temp);
	}
	/**
	 * Get the string of this node
	 */
	public String toString(){
		String temp = "";
		for(int i = 0; i < fileNames.size(); i++)
			temp += fileNames.get(i) + " " + position.get(i) + " ";
		return temp;
	}
}
