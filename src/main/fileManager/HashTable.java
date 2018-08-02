package src.main.fileManager;
//This class is built in a static way to store the index after hashed.
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
/**
 * 
 * @author dxz
 *
 */
public class HashTable {
	//The original size of hash-table. 
	private static int size = 30000;
	//Save the indexs
	private static String[] key = new String[size];
	//Save the file names
	private static PositionNode[] contents = new PositionNode[size];
	//Create a default object is forbidden
	protected HashTable(){}
	
	protected void setSize(int num){
		size = num;
		init();
	}
	/**
	 * add the index into the table.
	 * @param value the hash-code
	 * @param index the value of the index
	 * @param fileName the file contains the index
	 */
	public static void add(int value, String index, String fileName, int pos){
		int num = value % size;
		for(;key[num] != null ;num = (num + 1) % size){
			if(key[num].equals(index)){
				if(!contents[num].contains(fileName))
					contents[num].add(fileName, pos);
				else
					contents[num].add(pos);
				return;
			}
		}
		key[num] = index;
		contents[num] = new PositionNode();
		contents[num].add(fileName, pos);
	}
	//Build a new hash-table. 
	private static void init(){
		key = new String[size];
		contents = new PositionNode[size];
	}
	/**
	 * 
	 * @param indexDir the path to save the index
	 * @param fileList the list of all the scanned files
	 */
	protected static void toFile(String indexDir){
		File dir = new File(indexDir);
		dir.mkdir();
		File f = new File(indexDir + File.separator + "index.txt");
		FileOutputStream fout = null; 
        OutputStreamWriter isw = null;
        BufferedWriter bw = null;
        try{
        	fout = new FileOutputStream(f);
			isw  = new OutputStreamWriter(fout);
			bw = new BufferedWriter(isw);
			bw.append("");
        	for(int i = 0; i < size; i++){
        		if(key[i] != null){
        			bw.append(key[i]+":");
        			bw.append(contents[i].toString());
        			bw.newLine();
        		}
        	}
        	bw.close();
        }
        catch(IOException e){e.printStackTrace();}
	}
}
