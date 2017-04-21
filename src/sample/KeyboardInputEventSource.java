package sample;

/**
 * Created by Adonay on 4/20/2017.
 */
import java.util.Observable;
import java.util.Scanner;

public class KeyboardInputEventSource extends Observable implements Runnable {
    public void run() {
        while (true) {
            String response = new Scanner(System.in).next();
            setChanged();
            notifyObservers(response);
        }
    }
}