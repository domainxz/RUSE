package src.main.simpleIndex;
/**
 * 
 * @author dxz
 * 
 */
public class FileNameIndex extends Index{
	public FileNameIndex(){
		key = new String[size];
	}
	/**
	 * @param size a new size of the file name index
	 */
	public FileNameIndex(int size){
		this.size = size;
		key = new String[size];
	}
	/**
	 * The key to sort is file name
	 */
	public void createIndex(){
		String s = f.getName();
		int i = 0;
		for(; i < count; i++)
			if(((String)key[i]).compareTo(s) > 0){
				for(int j = count; j != i; j--)
					key[j] = key[j-1];
			break;
		}
		key[i] = s;
		count ++;
	}
	/**
	 * This method will keep the index format as file names
	 */
	public void toFile(String indexDir){
		String content = "";
		for(int i = 0; i < count; i++)
			content += key[i] + " ";
		toFile(indexDir, "FileNameIndex.txt", content);
	}
}
