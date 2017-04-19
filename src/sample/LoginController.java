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
 * Controller for Login screen.
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
    private DatabaseUtil dbUtil = new DatabaseUtil();

    @FXML
    /**
     * Initializes the Login screen.
     */
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

    /**
     * Logs in a user by checking the username and password, both of which are of type Text.
     * If the username specified is not in the database, the screen displays an error message
     * indicating that the username does not exist and will not log the user in.
     * @param event Function is run when the user clicks the Login button in the User Interface.
     */
    public void login(ActionEvent event) {
        username = usernameField.getText().toLowerCase().trim();
        password = passwordField.getText();

        try {
            if (dbUtil.contains("ACCOUNT", "USERNAME", username)) {

                aUtil.setUsername(username);
                screenUtil.switchScene("MainMenu.fxml", "Main Menu");
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

    /**
     * Logs in a guest with limited accessibility to the rest of the application.
     * @param event Triggers when the "Log in as Guest" button is pressed.
     */
    public void guestLogin(ActionEvent event) {
        aUtil.setUsername("guest");
        screenUtil.switchScene("MainMenu.fxml", "Main Menu");
    }

    /**
     * Opens the Create Account screen when the "Create Account" button is pressed
     * in the user interface.
     * @param event ActionEvent object representing the button click.
     */
    public void openCreateAccount(ActionEvent event) {
        screenUtil.switchScene("NewAccount.fxml", "New Account");
    }

    /**
     * Clears account data from the system.
     */
    public void clearData() {
        try{
            dbUtil.clearTable("ACCOUNT");
        }catch(Exception e){
            errorBox.setText("Database Error!");
            e.printStackTrace();
        }
    }
}


