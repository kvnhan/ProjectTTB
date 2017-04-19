package sample;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *Account class holds information regarding user accounts.
 */
public class Account implements Serializable{
    private String username;
    /*private String password;*/
    private boolean isLoggedIn;
    private int userType;
/*
    protected enum userTypes {
        GOVERNMENT_AGENT(1), MANUFACTURER(2), PUBLIC_USER(3);
        private final int id;
        userTypes(int id) { this.id = id; }
        public int getValue() { return id; }
    };*/


    /**
     * Creates an account that is not logged in.
     * @param usrnm String representing the username of the account.
     * @param uType int dfferentiating between different types of account.
     *
     */
    public Account(String usrnm, int uType){
        username = usrnm;
        userType = uType;
        isLoggedIn = false;
    }

    public String getUsername() {
        return username;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

}
