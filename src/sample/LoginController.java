package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.sql.*;
/**
 * Created by Adonay on 3/26/2017.
 */
public class LoginController {
    @FXML
    private TextField usernameField, passwordField;
    @FXML
    private Label errorBox;
    @FXML
    private Button loginButton;

    ScreenUtil screenUtil = new ScreenUtil();

    private String username;
    private String password;
    private AccountsUtil aUtil = new AccountsUtil();

    // for database
    private DatabaseUtil dbUtil = new DatabaseUtil();

    @FXML
    public void initialize() {
        usernameField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    login(new ActionEvent(loginButton, (Node) loginButton));
                }
            }
        });
    }

    public void login(ActionEvent event) {
        username = usernameField.getText().toLowerCase();
        password = passwordField.getText();

        try {
            if (dbUtil.contains("ACCOUNT", "USERNAME", username)) {


                dbUtil.addUserType("Government Agent");
                dbUtil.addUserType("Manufacturer");
                dbUtil.addUserType("Public User");
                aUtil.setUser_id(username);
                screenUtil.pullUpScreen("MainMenu.fxml", "Main Menu", event);
            } else {
                errorBox.setText("Username does not exist!");
            }
        } catch (SQLException e) {
            errorBox.setText("Database Error");
            e.printStackTrace();
        }catch (Exception e) {
            errorBox.setText("Unknown Exception");
            e.printStackTrace();
        }
    }

    public void guestLogin(ActionEvent event) {
        aUtil.setUser_id("guest");
        screenUtil.pullUpScreen("MainMenu.fxml", "Main Menu", event);
    }

    public void openCreateAccount(ActionEvent event) {

        screenUtil.pullUpScreen("NewAccount.fxml", "New Account", event);

    }

    public void clearData() {
        try{
            dbUtil.clearTable("ACCOUNT");
        }catch(Exception e){
            errorBox.setText("Database Error!");
            e.printStackTrace();
        }
    }
}


