package src.main.simpleSearch;
import java.util.StringTokenizer;

public class SizeSearch extends SimpleSearch{
	/**
	 * Implements SimpleSearch key word is integer value of file size
	 */
	protected int compareTo(int pos, String content){
		return new Integer(contents[pos].substring(contents[pos].indexOf('|') + 1)).compareTo(new Integer(content));
	}
	/**
	 * Implements SimpleSearch
	 */
	protected void toResult(int pos, int left, int right) {
		for(int i = left; i <= right; i++){
			StringTokenizer st = new StringTokenizer(contents[i], "|");
			if(result[pos] == null)
				result[pos] = st.nextToken() + " ";
			else
				result[pos] += st.nextToken() + " ";
		}
	}
}
