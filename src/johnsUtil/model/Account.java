package johnsUtil.model;

import java.io.File;

/**
 * Created by John on 4/21/2017.
 */
public class Account {

    private static Account instance = new Account();

    private int accountID;
    private String userName;
    private int userType;
    private String name;
    private String address;
    private String email;
    private String phoneNum;
    private File picPath;
    private boolean loggedIn;

    private Account(){
        this.accountID  = -1;
        this.userName = "";
        this.userType = -1;
        this.name = "";
        this.address =  "";
        this.email = "";
        this.phoneNum = "";
        this.picPath = null;
        loggedIn = false;
    }

    /**Tries to gather account info from usrName
     * Yea I realize holding a password in a string is a vulnrability but, this is software security engineering class.
     * @author John
     * @param usrName
     * @param password
     */
    @Deprecated
    public void login(String usrName, String password){
        //call database util, get info, and plug in, otherwise throw exception

        loggedIn = true;
    }

    public void logout(){
        this.accountID  = -1;
        this.userName = "";
        this.userType = -1;
        this.name = "";
        this.address =  "";
        this.email = "";
        this.phoneNum = "";
        this.picPath = null;
        loggedIn = false;
    }


    public int getAccountID() {
        return accountID;
    }

    public File getPicPath() {
        return picPath;
    }

    public void setPicPath(File picPath) {
        this.picPath = picPath;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public static Account getInstance(){
        return instance;
    }


}
