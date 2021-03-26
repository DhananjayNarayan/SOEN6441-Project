package utils.logger;

import utils.Observable;
import utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class gets all the actions of the game. It is an Observable.
 * Singleton class
 *
 * @author Dhananjay Narayan
 * @author Surya Manian
 */
public class LogEntryBuffer implements Observable {
    /**
     * A static object of LogEntryBuffer
     */
    private static LogEntryBuffer Logger;
    private List<Observer> d_ObserverList = new ArrayList<>();

    private LogEntryBuffer() {

    }

    public static LogEntryBuffer getInstance() {
        if (Objects.isNull(Logger)) {
            Logger = new LogEntryBuffer();
        }
        return Logger;
    }

    /**
     * This method gets the information from the game and notifies the Observer.
     *
     * @param p_s The message to be notified
     */
    public void log(String p_s) {
        notifyObservers(p_s);
    }


    /**
     * Clear logs
     */
    public void clear() {
        clearObservers();
    }

    /**
     * This method updates the Observer with the message.
     *
     * @param p_s The message to be updated
     */
    @Override
    public void notifyObservers(String p_s) {
        d_ObserverList.forEach(p_observer -> p_observer.update(p_s));
    }

    @Override
    public void addObserver(Observer p_Observer) {
        this.d_ObserverList.add(p_Observer);
    }


    @Override
    public void clearObservers() {
        d_ObserverList.forEach(Observer::clearLogs);
    }

}
