package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Adonay on 3/27/2017.
 */
public class MainMenuController{

    private FXMLLoader fxmlLoader;

    @FXML
    private Button openSearchButton, openInboxButton, openNewApplicationButton, logOutButton;
    @FXML
    private Text userID;

    private AccountsUtil accountsUtil = new AccountsUtil();

    public void initialize(){
        userID.setText(accountsUtil.getUser_id());
    }


    public void buttonClicked(ActionEvent e) {
        try{
            if(e.getSource() == openSearchButton){
                fxmlLoader = new FXMLLoader(getClass().getResource("SearchMenu.fxml"));
                ((Node)(e.getSource())).getScene().getWindow().hide();
            }else if(e.getSource() == openInboxButton){
                fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));

            }else if(e.getSource() == openNewApplicationButton){
                fxmlLoader = new FXMLLoader(getClass().getResource("NewAccount.fxml"));

            }else if(e.getSource() == logOutButton){
                fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            }else{
                fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            }
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Search");
            stage.setScene(new Scene(root1));
            stage.show();
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }


}
