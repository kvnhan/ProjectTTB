package sample;

import java.awt.event.ActionEvent;
import java.io.*;
import java.util.HashMap;

public class AccountsUtil {

    private static HashMap usernameMap = new HashMap<String, Account>();
    private String saveFileName = "usernameMap";
    private static String user_id = "";
    private ScreenUtil screenUtil = new ScreenUtil();


    AccountsUtil(){
        loadFile();
    }

    public static String getUser_id() {
        return user_id;
    }

    public static void setUser_id(String user_id) {
        AccountsUtil.user_id = user_id;
    }

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

    public void put(String username, Account account){
        usernameMap.put(username.toLowerCase(), account);
        saveFile();
    }

    public void clearData(){
        usernameMap.clear();
        saveFile();
    }

    public void logOut(javafx.event.ActionEvent event){
        user_id = "";
        screenUtil.pullUpScreen("Login.fxml", "Login", event);
    }
}
