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

        if(aUtil.contains(username)){
            aUtil.setUser_id(username);
            screenUtil.pullUpScreen("MainMenu.fxml", event);
        }
        else{
            errorBox.setText("Username does not exist!");
        }

    }

    public void guestLogin(ActionEvent event){
           aUtil.setUser_id("guest");
           screenUtil.pullUpScreen("MainMenu.fxml", event);
    }

    public void openCreateAccount(ActionEvent event){

        screenUtil.pullUpScreen("NewAccount.fxml", event);

    }

    public void clearData(){
        aUtil.clearData();
    }
}
