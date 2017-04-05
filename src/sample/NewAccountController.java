package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.sql.*;

/**
 * Created by Adonay on 3/27/2017.
 */
public class NewAccountController {

    @FXML
    private TextField newUsernameField;
    @FXML
    private ChoiceBox accountChoiceBox;
    @FXML
    private Label errorBox;
    @FXML
    private Button createAccountButton;

    private String newUsername;
    private String accountChoice;
    private int accountType = 0;

    private AccountsUtil accountsUtil = new AccountsUtil();
    private ScreenUtil screenUtil = new ScreenUtil();

    private Connection conn = connect();

    @FXML
    public void initialize(){
        newUsernameField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    createAccount(new ActionEvent(createAccountButton, (Node) createAccountButton));
                }
            }
        });
    }

    public void createAccount(ActionEvent event){

        try{
            newUsername = newUsernameField.getText();
            accountChoice = accountChoiceBox.getValue().toString();

            if (accountChoice.equals("Government Agent")){
                accountType = 1;
            }else if(accountChoice.equals("Manufacturer")){
                accountType = 2;
            }else if(accountChoice.equals("Public User")){
                accountType = 3;
            }

           /* if(!accountsUtil.contains(newUsername) && newUsername.length() >= 5){
                accountsUtil.put(newUsername, new Account(newUsername,0));
                screenUtil.pullUpScreen("Login.fxml", "Login", event);
            }else if(newUsername.length() < 5){
                errorBox.setText("User name must be at least five character long");
            }else if(accountsUtil.contains(newUsername)){
                errorBox.setText("Username taken!");
            }else{
                errorBox.setText("Unknown error!");
            }*/

            if(!databaseContainsUser(conn) && newUsername.length() >= 5){
                addToDatabase();
                screenUtil.pullUpScreen("Login.fxml", "Login", event);
            }else if(newUsername.length() < 5){
                errorBox.setText("User name must be at least five character long");
            }else if(accountsUtil.contains(newUsername)){
                errorBox.setText("Username taken!");
            }else{
                errorBox.setText("Unknown error!");
            }
        }catch(SQLException e){
            errorBox.setText("Database error");
            e.printStackTrace();

        }catch(NullPointerException e){
            errorBox.setText("Please select an account type");
        }
    }

    public void addToDatabase() throws SQLException {
        int ID;
        Statement stmt;
        int uRows = 0;
        stmt = conn.createStatement();

        ResultSet rset = stmt.executeQuery("SELECT * FROM ACCOUNT ORDER BY AID DESC");

        if(rset.next()){
            ID = rset.getInt("AID") + 1;
        }else{
            ID = 1;
        }

        uRows = stmt.executeUpdate("INSERT INTO ACCOUNT (AID, USERNAME, USER_TYPE) VALUES ("+ ID  + ", '" + newUsername + "', " + accountType + ")");

        System.out.println(uRows + " Row(s) Updated");

        rset.close();
        stmt.close();
    }

    private boolean databaseContainsUser(Connection conn) throws SQLException {
        boolean contains = false;

        ResultSet rset;
        Statement stmt;

        String usernameQuery = "SELECT * FROM ACCOUNT WHERE ACCOUNT.USERNAME = " + "'" + newUsername + "'";
        stmt = conn.createStatement();

        rset = stmt.executeQuery(usernameQuery);

        contains = rset.next();

        rset.close();
        stmt.close();

        return contains;
    }

    public static Connection connect(){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Java DB Driver not found. Add the classpath to your module.");
            e.printStackTrace();
            return null;
        }

        System.out.println("Java DB driver registered!");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:derby:DATABASE\\ProjectC;create=true");
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return connection;
        }
        System.out.println("Java DB connection established!");

        return connection;
    }
}
