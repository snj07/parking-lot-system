package observers;

/**
 * CommandObservable class to make CommandController observable and react based on the output of it
 */
public interface CommandObservable {

    boolean addObserver(CommandObserver observer);

    boolean removeObserver(CommandObserver observer);

    void notifyObserver(String message);
}
