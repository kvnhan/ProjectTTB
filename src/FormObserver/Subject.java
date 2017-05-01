package FormObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a subject to which observers are attached.
 */
public class Subject {

    private List<AbstractForm> observers =  new ArrayList<AbstractForm>();
    private String text;

    public String getText(){
        return text;
    }
    public void setText(String Text){
        this.text = Text;
        notifyAllObservers();
    }

    /**
     * Adds an observer to the list of observers.
     * @param observer Abstract form observer to attach.
     */
    public void attachObserver(AbstractForm observer){
        observers.add(observer);
    }

    /**
     * Notifies all observers of an update to the active form.
     */
    public void notifyAllObservers(){
        for (AbstractForm observer : observers) {
            observer.update();
        }
    }
}
