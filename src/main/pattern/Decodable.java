//This interface is created to support identifying the file style dynamically
package src.main.pattern;
/**@author dxz
 * @version 1.0
 */
public interface Decodable {
	/**Confirm whether the file style is correct. **/
	public boolean confirm(String fileName);
	/**Help the user to load the file **/
	public void load(String directory, String fileName);
}
