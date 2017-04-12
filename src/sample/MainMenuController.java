package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.sql.SQLException;

/**
 * Controller class for Main Menu screen.
 */
public class MainMenuController{

    @FXML private Button openSearchButton, openInboxButton, createNewApplicationButton, logOutButton;
    @FXML private Text userIDText;

    ScreenUtil work = new ScreenUtil();
    DatabaseUtil databaseUtil = new DatabaseUtil();
    AccountsUtil accountsUtil = new AccountsUtil();
    private String username;

    @FXML
    public void initialize() throws SQLException{

        username = accountsUtil.getUsername();
        userIDText.setText(username);

        if(username.toLowerCase().equals("guest")){
            openInboxButton.setDisable(true);
            createNewApplicationButton.setDisable(true);
            logOutButton.setDisable(true);
        }else if(databaseUtil.searchAccountWithUsername(username).get(0).getUserType() == 3){
            openInboxButton.setDisable(true);
            createNewApplicationButton.setDisable(true);
        }else if(databaseUtil.searchAccountWithUsername(username).get(0).getUserType() == 2){
            openInboxButton.setDisable(true);
        }else if(databaseUtil.searchAccountWithUsername(username).get(0).getUserType() == 1){
            createNewApplicationButton.setDisable(true);
        }


    }

    /**
     *This is the code that figures out where the UI will go when certain buttons are clicked. Right now, the only
     * transition that can be seen from this is an instant change and deletion of the different panels.
     * @param event - action that activates the changing menu procedure, i.e. clicking on a button in the menu
     */
    public void buttonClicked(ActionEvent event) {

        if(event.getSource() == openSearchButton){
            work.switchScene("SearchMenu.fxml", "Search");

        }else if(event.getSource() == openInboxButton){
            work.switchScene("WorkFlow.fxml", "Inbox");

        }else if(event.getSource() == createNewApplicationButton){
            work.switchScene("NewApp.fxml","New Application");

        }else if(event.getSource() == logOutButton){
            work.switchScene("Login.fxml","Login");

        }else{
            work.switchScene("ErrorState.fxml","Error");
        }
    }
}