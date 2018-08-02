package src.main.pattern;
/**
 * @author dxz
 * The class implements this interface should be regarded as a vector of key word and their contents 
 */
public interface Interpretable {
	/**
	 * Add the key word
	 * @param word key word
	 */
	public void add(String word);
	/**
	 * Get current key word`s content
	 * @return The content of key word
	 */
	public String getContents();
}
