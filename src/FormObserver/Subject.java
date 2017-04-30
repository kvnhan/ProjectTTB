package FormObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kien Nhan on 4/29/2017.
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

    public void attachObserver(AbstractForm observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (AbstractForm observer : observers) {
            observer.update();
        }
    }
}
