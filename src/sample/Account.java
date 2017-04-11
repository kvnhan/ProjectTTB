package sample;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Adonay on 4/3/2017.
 */
public class Account implements Serializable{
    private String username;/*
    private String password;*/
    private boolean isLoggedIn;
    private int userType;
    private ArrayList<String> inbox = new ArrayList<>();

    protected enum userTypes {
        GOVERNMENT_AGENT, MANUFACTURER, PUBLIC_USER
    };

    public Account(String usrnm, int uType, ArrayList<String> inbox){
        username = usrnm;
        userType = uType;
        isLoggedIn = false;
        this.inbox = inbox;
    }

    public String getUsername() {
        return username;
    }

    public int getAccessLevel() {
        return userType;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public ArrayList<String> getInbox(){return inbox;}

    //returns the number of applications in the worker's inbox
    public int numberOfApplications(){
        return inbox.size();
    }
    //adds a specific application to the inbox
    public void addToInbox(String sf){
        inbox.add(0, sf);
    }
    //removes a specific appliation from the inbox
    public void removeFromInbox(int formID){
        inbox.remove(formID);
    }
    //gets a specific application from the inbox
    public String getNextForm(){
        return inbox.get(0);
    }
}
