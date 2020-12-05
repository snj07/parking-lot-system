package observers;

/**
 * CommandObservable class to make CommandController observable and react based on the output of it
 */
public interface CommandObservable {

    public boolean addObserver(CommandObserver observer);

    public boolean removeObserver(CommandObserver observer);

    public void notifyObserver(String message);
}
