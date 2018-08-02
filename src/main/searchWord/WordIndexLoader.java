package src.main.searchWord;

import java.io.BufferedReader;
import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import src.main.pattern.AdvancedObserver;
import src.main.pattern.Subject;
/**
 * 
 * @author dxz
 * WordIndexLoader works as a subject of observer pattern to control the searching contents update
 */
public class WordIndexLoader implements Subject {
	ArrayList<AdvancedObserver> observer = new ArrayList<AdvancedObserver>();
	FileInputStream fis;
	InputStreamReader isr;
	BufferedReader br;
	boolean isStop = false;
	
	@SuppressWarnings("unused")
	private WordIndexLoader(){}
	/**
	 * Create a file input stream to get index
	 * @param indexDir The index directory
	 */
	public WordIndexLoader(String indexDir){
		try{
			fis = new FileInputStream(indexDir + File.separator + "index.txt");
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
		}
		catch(IOException e){e.printStackTrace();}
	}
	/**
	 * This method will update the data when get a line and autostop when the observer list is empty
	 */
	public void notifyObserver() {
		String s = null;
		if(!observer.isEmpty()){
			try{
				 s = br.readLine();
			}
			catch(IOException e){e.printStackTrace();}
			for(int i = 0; i < observer.size(); i++){
				AdvancedObserver ao = (AdvancedObserver)observer.get(i);
				if(ao.isEnough())
					observer.remove(i);
				else
					ao.update(s);
			}
		}
	}
	/**
	 * Implements Subject
	 */
	public void registerObserver(AdvancedObserver ao) {
		observer.add(ao);
	}
	/**
	 * Implements Subject
	 */
	public void removeObserver(AdvancedObserver ao) {
		int i = observer.indexOf(ao);
		if(i >= 0)
			observer.remove(ao);
	}
	/**
	 * Implements Subject
	 */
	public void stop(){
		try{
			br.close();
		}
		catch(IOException e){e.printStackTrace();}
	}
	/**
	 * Implements Subject
	 */
	public boolean canStop(){
		return observer.isEmpty();
	}
}