package src.main.decode;
//This class help the user to read a txt and save the contents into the Hashtable
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**@author dxz
 *@version 1.2
 */
public class TxtDecoder extends Decoder{
	FileReader fr = null;
	static int ch = 0;
    static char temp = 0;
	/**
	 * @param fileName the name of the file.
	 * @return if the style is correct, return true.
	 */
	public boolean confirm(String fileName){
		if(fileName.endsWith(".txt"))
			return true;
		return false;
	}
	/**
	 * This method creates a txt input stream and uses the method of super class to append new character
	 * @param directory the absolute path of files
	 * @param fileName currently scanned file`s name 
	 */
	@Override
	void createStream(String directory, String fileName) {
		ch = 0;
		temp = 0;
		try{
			fr = new FileReader(directory + File.separator + fileName);
		}
		catch(FileNotFoundException e){e.printStackTrace();}
	}
	@Override
	boolean isEnd() {
		if(ch == -1){
			try{
				fr.close();
			}
			catch(IOException e){e.printStackTrace();}
			return true;
		}
		return false;
	}
	@Override
	char read() {
		 try{
	         ch = fr.read();
	         temp = (char)ch;
	     }
	     catch(IOException e){e.printStackTrace();}
	     return temp;
	}
}
