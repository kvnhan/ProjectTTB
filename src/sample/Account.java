package sample;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Adonay on 4/3/2017.
 */
public class Account implements Serializable{
    private String username;
    /*private String password;*/
    private boolean isLoggedIn;
    private int userType;

    protected enum userTypes {
        GOVERNMENT_AGENT, MANUFACTURER, PUBLIC_USER
    };

    public Account(String usrnm, int uType){
        username = usrnm;
        userType = uType;
        isLoggedIn = false;
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
}
