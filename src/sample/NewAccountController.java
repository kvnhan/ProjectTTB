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
 * Controller for new account screen.
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

    ScreenUtil work = new ScreenUtil();

    private String newUsername;
    private String accountChoice;
    private int userType;

    private AccountsUtil accountsUtil = new AccountsUtil();
    private ScreenUtil screenUtil = new ScreenUtil();
    Connection cn;
    Statement sm;
    private DatabaseUtil dbUtil = new DatabaseUtil();

    @FXML
    /**
     * Initializes the New Account screen.
     */
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

    public void goBack(ActionEvent event){
        work.switchScene("Login.fxml", "Login");
    }

    /**
     * Creates an account in the database.
     * @param event The "Create Account" button is pressed.
     */
    public void createAccount(ActionEvent event){

        try{
            newUsername = newUsernameField.getText().toLowerCase();
            accountChoice = accountChoiceBox.getValue().toString();

            if (accountChoice.equals("Government Agent")){
                userType = 1;
            }else if(accountChoice.equals("Manufacturer")){
                userType = 2;
            }else if(accountChoice.equals("Public User")){
                userType = 3;
            }else if(accountChoice.equals("SuperUser")){
                userType = 0;
            }

            if( (newUsername.trim().length() >= 5 && newUsername.trim().length() <= 15) && !dbUtil.contains("ACCOUNT", "USERNAME", newUsername.trim())){
               dbUtil.addAccount(newUsername.trim(), "password", 1, userType);
               screenUtil.switchScene("Login.fxml", "Login");
            }else if(newUsername.trim().length() < 5 || newUsername.trim().length() > 15){
                errorBox.setText("User name must be 5 - 15 characters long");
            }else if(dbUtil.contains("ACCOUNT", "USERNAME", newUsername.trim())){
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
}
