package src.main.fileManager;
/*This class dynamically identify the file style and use the correct class.
 * The method is static so it can not create a object.
 */
import java.util.ArrayList;
import src.main.decode.TxtDecoder;
import src.main.decode.WordDecoder;
import src.main.pattern.Decodable;
/**
 * 
 * @author dxz
 *
 */
public class FileLoader {
	//Keep the styles of file
	private static ArrayList<Decodable> decoder = new ArrayList<Decodable>();
	//Create a object is forbidden
	private FileLoader(){}
	//Add the style of identifiable files 
	private static void init(){
		decoder.add(new TxtDecoder());
		decoder.add(new WordDecoder());
	}
	
	public static void addDecoder(Decodable d){
		if(decoder.contains(d))
			decoder.add(d);
	}
	/**Identify the file style with using the method interface "Decodable" supported
	 * 
	 * @param directory The absolute path
	 * @param fileName The file to be read
	 */ 
	protected static void load(String directory, String fileName){
		init();
		Decodable d = null;
		
		for(int i = 0; i < decoder.size(); i++){
			d = (Decodable)decoder.get(i);
			if(d.confirm(fileName)){
				d.load(directory, fileName);
				break;
			}
		}
	}
}