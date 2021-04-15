package utils;

/**
 * An interface for implementation of Observable with a notifyObservers function.
 *
 * @author Surya Manian
 */
public interface Observable {

    /**
     * A function to send a message/notification to Observer.
     *
     * @param p_String the observable
     */
    public void notifyObservers(String p_String);

    public void addObserver(Observer p_Observer);

    public void clearObservers();
}
