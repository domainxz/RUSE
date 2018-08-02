package src.main.simpleIndex;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 
 * @author dxz
 *
 */
public class FileInformation extends Index{
	
	public FileInformation(){
		key = new String[size];
		contents = new String[size];
	}
	
	public FileInformation(int size){
		key = new String[size];
		contents = new String[size];
		this.size = size;
	}
	/**
	 * Use the long value as the key
	 */
	public void createIndex() {
		String s = f.getName();
		int i = 0;
		for(; i < count; i++)
			if(((String)key[i]).compareTo(s) > 0){
				for(int j = count; j != i; j--){
					key[j] = key[j-1];
					contents[j] = contents[j-1];
				}
			break;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		key[i] = s;
		contents[i] = sdf.format(new Date(f.lastModified()))+ "," + f.length(); 
		count ++;
	}
	/**
	 * The index format is filename, filemodtime, filesize
	 */
	public void toFile(String indexDir) {
		String content = "";
		for(int i = 0; i < count; i++)
			content += key[i] + "," + contents[i] + " ";
		toFile(indexDir, "FileInformation.txt", content);
	}
}
