package src.main.simpleSearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import src.main.pattern.Interpretable;

public abstract class SimpleSearch implements Interpretable{
	//All element of a derived class of SimpleSearch
	String[] contents;
	//Each single query
	String[] query;
	//Each query`s result
	String[] result;
	//Search range
	String[][] side;
	//Condition of bound
	boolean[][] limits;
	//The capacity of vactor
	int size = 20;
	//Current number of element
	int bound = 0;
	//Current element to return
	int point = 0;
	//Compare to the side, the value is similiar to Comparable
	protected abstract int compareTo(int pos, String content);
	//Keep the result of each single search
	protected abstract void toResult(int pos, int left, int right);
	
	public SimpleSearch(){
		contents = new String[size];
		query = new String[size];
		result = new String[size];
		side = new String[size][2];
		limits = new boolean[size][2];
	}
	
	public void setSize(int size){
		contents = new String[size];
		query = new String[size];
		result = new String[size];
		side = new String[size][2];
		limits = new boolean[size][2];
		this.size = size;
	}
	/**
	 * Implements Interpretable
	 */
	public void add(String word){
		int length = word.length() - 1;
		limits[bound][0] = getLimit(word.charAt(0));
		limits[bound][1] = getLimit(word.charAt(length));
		query[bound++] = word.substring(1, length);
	}
	/**
	 * Get all list from related index
	 * @param indexDir index directory
	 * @param indexFile index file name
	 */
	public void update(String indexDir, String indexFile) {
		try{
			FileInputStream fis = new FileInputStream(indexDir + File.separator + indexFile);
			InputStreamReader isr = new InputStreamReader(fis);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(isr);
			contents = (br.readLine().trim()).split(" ");
		}
		catch(IOException e){e.printStackTrace();}
		process();
	}
	/**
	 * 
	 * @param c bound charactor
	 * @return false the boundary is open
	 * @return true the boudary is close
	 */
	private boolean getLimit(char c){
		if(c == '[' || c == ']')
			return true;
		return false;
	}
	/**
	 * Implements Interpretable
	 */
	public String getContents() {
		if(point != bound)
			return result[point++];
		return null;
	}
	/**
	 * According to the compareTo implemented by derived class get two posistion
	 * toResult method keep the result based on the position
	 */
	protected void process(){
		for(int i = 0; i < bound; i++)
			side[i] = query[i].split("-");
		for(int i = 0; i < bound; i++){
			int left = 0;
			int right = contents.length - 1;
			if(limits[i][0]){
				for(;left < contents.length;left++)
					if(compareTo(left, side[i][0]) >= 0)
						break;
			}
			else{
				for(;left < contents.length;left++){
					if(compareTo(left, side[i][0]) > 0)
						break;
				}
			}
			if(limits[i][1]){
				for(;right > left;right--)
					if(compareTo(right, side[i][1]) <= 0)
						break;
			}
			else{
				for(;right > left;right--)
					if(compareTo(right, side[i][1]) < 0)
						break;
			}
			toResult(i, left, right);
		}
	}
}
