package sample;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Account class contains information for all types of account.
 */
public class Account implements Serializable{
    private String username;
    private int accessLevel;
    private boolean isLoggedIn;
    private ArrayList<String> inbox = new ArrayList<>();

    /**
     * Constructor for Account class. Account is set to not logged in
     * by default.
     *
     * @param usrnm String representing account username.
     * @param accL Int representing access level for the account.
     * @param inbox ArrayList of strings representing IDs of applications in the account
     *              inbox.
     */
    public Account(String usrnm, int accL, ArrayList<String> inbox){
        username = usrnm;
        accessLevel = accL;
        isLoggedIn = false;
        this.inbox = inbox;
    }

    public String getUsername() {
        return username;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public ArrayList<String> getInbox(){return inbox;}

    /**
     * Returns number of applications in an account's inbox.
     * @return Returns the size of the inbox.
     */
    public int numberOfApplications(){
        return inbox.size();
    }
    /**
     * Adds a specific application to the inbox.
     * @param sf String representing the ID for
     *           the application to be added to the inbox.
     */
    public void addToInbox(String sf){
        inbox.add(0, sf);
    }
    /**
     * Removes a specific application from an account's inbox.
     * @param formID String representing the ID of the application to be
     *               removed.
     */
    public void removeFromInbox(int formID){
        inbox.remove(formID);
    }
    /**
     * Retrieves specific application ID from an account's inbox.
     * @return Returns a string representing the application ID.
     */
    public String getNextForm(){
        return inbox.get(0);
    }
}
