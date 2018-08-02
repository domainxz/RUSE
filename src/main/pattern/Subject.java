package src.main.pattern;

public interface Subject {
	/**
	 * Add the observer
	 */
	public void registerObserver(AdvancedObserver ao);
	/**
	 * Remove the observer
	 */
	public void removeObserver(AdvancedObserver ao);
	/**
	 * Provide the information
	 */
	public void notifyObserver();
	/**
	 * Judge whether the subject can stop notifying
	 */
	public boolean canStop();
	/**
	 *Stop the subject
	 */
	public void stop();
}
