package src.main.decode;
//This class helps derived class to finish some common job
import src.main.fileManager.HashTable;
import src.main.pattern.Decodable;
/**@author dxz
 * @version 1.0
 */
public abstract class Decoder implements Decodable{
    //Save the hash-code of current word
	protected static int value = 0;
	//Save the indexs
	protected static int count = 1;
	//The relative value of current letter to 'a'
	protected static int n = 0;
	//Keep current word
	protected static StringBuffer buffer = new StringBuffer();
	//Identify the current string is number or word
	protected static boolean isDigit = false;
	protected static int pos = 1;
	
	abstract void createStream(String directory, String fileName);
	abstract boolean isEnd();
	abstract char read();
	/**
	 * This method creates a file input stream and uses the method "process" to append new character
	 * @param directory the absolute path of files
	 * @param fileName currently scanned file`s name 
	 */
	public void load(String directory, String fileName){
		pos = 1;
		createStream(directory, fileName);
		while(!isEnd()){
			process(read(), fileName);
		}
	}
	//Get the hash-value of a string
	private void getHashCode(char temp){
		buffer.append(Character.toLowerCase(temp));
		n = Character.toLowerCase(temp) % 'a';
		value += n * count * count * count * count * count;//The hash method is ¡Æbit(i)*count^5
		count++;
	}
	//Append the index into the HashTable
	private void add(String fileName, int pos){
		String index = buffer.toString();
		HashTable.add(value, index, fileName, pos);
		value = 0;
		count = 1;
		buffer = new StringBuffer();
	}
	/**
	 * This method will gather the character`s and append it into the table when meet a interpunction 
	 * @param temp current char which is read from a FileInputStream
	 * @param pos the positon of the current string
	 * @param fileName currently scanned file`s name
	 * @return refresh the change to the postion of current string
	 */
	protected int process(char temp, String fileName){
		if(Character.isDigit(temp)){
			if(!isDigit && buffer.length() != 0){
				add(fileName, pos);
				pos ++;
			}
			isDigit = true;
			getHashCode(temp);
		}
		else if(Character.isLetter(temp)){
			if(isDigit && buffer.length() != 0){
				add(fileName, pos);
				pos ++;
			}
			isDigit = false;
			getHashCode(temp);
		}
		else if(buffer.length() != 0){
			add(fileName, pos);
			pos ++;
		}
		return pos;
	}
}
