package sample;


import java.util.Observable;
import java.util.Scanner;
/**
 * Sources keyboard input events.
 */
public class KeyboardInputEventSource extends Observable implements Runnable {
    /**
     * Runs function to notify observers for keyboard events.
     */
    public void run() {
        while (true) {
            String response = new Scanner(System.in).next();
            setChanged();
            notifyObservers(response);
        }
    }
}