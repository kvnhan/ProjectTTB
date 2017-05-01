package johnsUtil.model.SharedResources;

import org.mindrot.jbcrypt.BCrypt;

import javax.imageio.ImageIO;
import javax.xml.crypto.Data;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * Contains account information.
 */
public class Account {

    private static Account account = new Account();

    private int accountID;
    private String userName;
    private int userType;
    private String name;
    private String address;
    private String email;
    private String phoneNum;
    private File picPath;
    private boolean loggedIn;
    private String searchHack; //for guests
/**
 * Creates a sample account.
 */
    private Account(){
        this.accountID  = -1;
        this.userName = "";
        this.userType = 3;
        this.name = "";
        this.address =  "";
        this.email = "";
        this.phoneNum = "";
        this.picPath = null;
        this.loggedIn = false;
        this.searchHack = "";
    }

    /**Tries to gather account info from usrName
     * @param usrName Username of account.
     * @param password Password of account.
     */
    public boolean login(String usrName, String password) throws SQLException {
        if(Database.getInstance().logIn(usrName,password)){
            loggedIn = true;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Logs a user out of the application.
     */
    public void logout(){
        try {
            Database.getInstance().logOut(accountID);
        } catch (SQLException e) {
            //Couldn't set last logged in .. oh well
        }

        this.accountID  = -1;
        this.userName = "";
        this.userType = 3;
        this.name = "";
        this.address =  "";
        this.email = "";
        this.phoneNum = "";
        this.picPath = null;
        loggedIn = false;
    }

    /**
     * Creates an account with the given fields
     * @param userName
     * @param pass Password.
     * @param name
     * @param address
     * @param email
     * @param phone
     * @param type Type of account.
     * @param file Account image.
     */
    public void createAccount(String userName, String pass,String name, String address, String email, String phone, int type, java.io.File file) throws SQLException {
        File image =  new File("");
        if(file != null){
            try {
                image = saveImage(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Database.getInstance().addAccount(userName, bCryptSaltedPassword(pass), 0, type, name, phone, phone, address, image);
    }


    /**
     * Saves alcohol label image to the system.
     */
    private java.io.File saveImage(java.io.File file) throws IOException {
        BufferedImage image = null;

        File newFile;
        String path = getPath();
        image = ImageIO.read(file);
        ImageIO.write(image, "jpg", newFile = new File(path + "/" + accountID + ".jpg"));
        return newFile;
    }

    /**
     * Gets path for the label image.
     * @return Returns string representing path to the label image.
     * @throws UnsupportedEncodingException
     */
    private String getPath() throws UnsupportedEncodingException {
        URL url = this.getClass().getProtectionDomain().getCodeSource().getLocation();
        String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
        String parentPath = new File(jarPath).getParentFile().getPath();

        String fileSeparator = System.getProperty("file.separator");
        String newDir = parentPath + fileSeparator + "icons" + fileSeparator;

        return newDir;
    }

    /**
     * Creates a non salted sha256 hashed string from the given password
     * @param password
     * @return Returns a nonsalted sha256 hashed string.
     * @throws NoSuchAlgorithmException
     */
    @Deprecated
    private static String sha256NoSaltPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        StringBuffer hexString = new StringBuffer();
        for (int i=0;i<byteData.length;i++) {
            String hex =Integer.toHexString(0xff & byteData[i]);
            if(hex.length()==1){
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    /**
     * Generates a BCrypted Salted hash for a password
     * @param password
     * @return hash
     */
    private static String bCryptSaltedPassword(String password){
        String hashed =  BCrypt.hashpw(password,BCrypt.gensalt());
        return hashed;
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

    public static Account getInstance(){ return account; }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getSearch() {
        return searchHack;
    }

    public void setSearch(String searchHack) {
        this.searchHack = searchHack;
    }

}
