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
    private int userType = 0;

    private AccountsUtil accountsUtil = new AccountsUtil();
    private ScreenUtil screenUtil = new ScreenUtil();
    private DatabaseUtil dbUtil = new DatabaseUtil();

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
            newUsername = newUsernameField.getText().toLowerCase();
            accountChoice = accountChoiceBox.getValue().toString();

            if (accountChoice.equals("Government Agent")){
                userType = 1;
            }else if(accountChoice.equals("Manufacturer")){
                userType = 2;
            }else if(accountChoice.equals("Public User")){
                userType = 3;
            }

            if(!dbUtil.contains("ACCOUNT", "USERNAME", newUsername) && usernameIsRightLength(newUsername)){
               dbUtil.addAccount(newUsername, "password", 1, 2);
               screenUtil.pullUpScreen("Login.fxml", "Login", event);
            }else if (!usernameIsRightLength(newUsername)){
                errorBox.setText("User name must be 5 - 15 characters long");
            }else if(dbUtil.contains("ACCOUNT", "USERNAME", newUsername)){
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

    //made to improve readability and find a bug in account creation
    private boolean usernameIsRightLength(String username){
        return (username.length() >= 5 && username.length() <= 15);
    }
}
