package src.main.simpleSearch;

public class FileNameSearch extends SimpleSearch {
	/**
	 * Implements SimpleSearch key word is file name
	 */
	protected int compareTo(int pos, String content){
		return contents[pos].compareTo(content);
	}
	/**
	 * Implements SimpleSearch
	 */
	protected void toResult(int pos, int left, int right) {
		for(int i = left; i <= right; i++){
			if(result[pos] == null)
				result[pos] = contents[i] + " ";
			else
				result[pos] += contents[i] + " ";
		}
	}
}
