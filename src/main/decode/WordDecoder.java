package src.main.decode;
// This class use the org.apache.poi to help user read a document of word.
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import org.apache.poi.hwpf.extractor.WordExtractor;
/**@author dxz
 *@version 1.2
 */
public class WordDecoder extends Decoder{
	FileInputStream fin = null;
	WordExtractor extractor = null;
	//The content of the file
	private static String text = null;
	private static int i = 0;
	
	public WordDecoder(){}
	
	public boolean confirm(String fileName){
		if(fileName.endsWith(".doc"))
			return true;
		return false;
	}
	@Override
	void createStream(String directory, String fileName) {
		i = 0;
		try{
			fin = new FileInputStream(directory + File.separator + fileName);
			extractor = new WordExtractor(fin);
			text = extractor.getText();
		}
		catch(IOException e){e.printStackTrace();}
	}

	@Override
	boolean isEnd() {
		if(i == text.length())
			return true;
		return false;
	}

	@Override
	char read() {
		return text.charAt(i++);
	}
}
