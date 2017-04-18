package sample;

import java.awt.event.ActionEvent;
import java.io.*;
import java.util.HashMap;

/**
 * Utility functions for accounts.
 */
public class AccountsUtil {

    private static HashMap usernameMap;
    private String saveFileName = "usernameMap";
    private static String username = "";
    private ScreenUtil screenUtil;


    public AccountsUtil(){
        loadFile();
        usernameMap = new HashMap<String, Account>();
        screenUtil = new ScreenUtil();
    }

    /**
     * Used for testing
     * @param screen
     */
    @Deprecated
    public AccountsUtil(ScreenUtil screen){
        loadFile();
        usernameMap = new HashMap<String, Account>();
        screenUtil = screen;
    }


    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        AccountsUtil.username = username;
    }

    /**
     * Saves a file to the system.
     */
    public void saveFile(){

        FileOutputStream fileOutputStream;

        try {
            File file = new File(saveFileName);
            fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream= new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(usernameMap);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Loads a file that the account owns from the system.
     */
    public void loadFile(){

        FileInputStream fileInputStream;

        try{
            File file = new File(saveFileName);
            fileInputStream = new FileInputStream(file);
            ObjectInputStream is = new ObjectInputStream(fileInputStream);
            usernameMap = (HashMap<String, Account>) is.readObject();
            is.close();
            fileInputStream.close();
        }catch(Exception e){
            usernameMap = new HashMap<String, Account>();
            saveFile();
        }
    }

    public boolean contains(String username){
        return usernameMap.containsKey(username.toLowerCase());
    }

    /**
     * Puts a username into the system and adds it to an account.
     * @param username Username to be added.
     * @param account Account to be added.
     */
    public void put(String username, Account account){
        usernameMap.put(username.toLowerCase(), account);
        saveFile();
    }

    /**
     * Clears data from the username map.
     */
    public void clearData(){
        usernameMap.clear();
        saveFile();
    }

    /**
     * Logs an account out of the system.
     * @param event Represents a press of the log out button.
     */
    public void logOut(javafx.event.ActionEvent event){
        username = "";
        screenUtil.switchScene("Login.fxml", "Login");
    }
}
