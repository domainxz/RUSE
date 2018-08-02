package src.main.simpleIndex;

public class FileSizeIndex extends Index{
	public FileSizeIndex(){
		key = new Long[size];
		contents = new String[size];
	}
	/**
	 * @param size a new size of the file size index
	 */
	public FileSizeIndex(int size){
		this.size = size;
		key = new Long[size];
		contents = new String[size];
	}
	/**
	 * The key to sort is file legnth
	 */
	public void createIndex(){
		Long s = f.length();
		createIndex(s);
	}
	/**
	 * This method will keep the index format as file sizes and file names
	 */
	public void toFile(String indexDir){
		String content = "";
		for(int i = 0; i < count; i++)
			content += contents[i] + "|" + key[i] + " ";
		toFile(indexDir, "FileSizeIndex.txt", content);
	}
}
