package src.main.pattern;

public interface AdvancedObserver {
	/**
	 * Update the contents of observer
	 * @param words contents of key word
	 */
	public void update(String words);
	/**
	 * If the implement class get enough information it will return true
	 */
	public boolean isEnough();
}
