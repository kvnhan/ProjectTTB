package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.sql.*;
import java.util.Scanner;

/**
 * Created by Adonay on 3/26/2017.
 */
public class LoginController {
    @FXML
    private TextField usernameField, passwordField;
    @FXML
    private Label errorBox;

    ScreenUtil screenUtil = new ScreenUtil();

    private String username;
    private String password;
    private AccountsUtil aUtil = new AccountsUtil();

    private Connection conn = connect();

    @FXML
    public void initialize(){
        usernameField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    login(new ActionEvent());
                }
            }
        });
    }

    public void login(ActionEvent event){
        username = usernameField.getText();
        password = passwordField.getText();

        try{
             /*if(aUtil.contains(username)){*/
            if(databaseContainsUser(conn)){
                aUtil.setUser_id(username);
                screenUtil.pullUpScreen("MainMenu.fxml", "Main Menu", event);
            }else{
                errorBox.setText("Username does not exist!");
            }
        }catch(SQLException e) {
            errorBox.setText("Database Error");
            e.printStackTrace();
        }
    }

    public void guestLogin(ActionEvent event){
           aUtil.setUser_id("guest");
           screenUtil.pullUpScreen("MainMenu.fxml", "Main Menu", event);
    }

    public void openCreateAccount(ActionEvent event){

        screenUtil.pullUpScreen("NewAccount.fxml", "New Account", event);

    }

    public void clearData(){
        aUtil.clearData();
    }



    private boolean databaseContainsUser(Connection conn) throws SQLException {
        boolean contains = false;

        ResultSet rset;
        Statement stmt;

            String usernameQuery = "SELECT * FROM ACCOUNT WHERE ACCOUNT.USERNAME = " + "'" + username + "'";
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
