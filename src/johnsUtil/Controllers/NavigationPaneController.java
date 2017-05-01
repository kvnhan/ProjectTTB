package johnsUtil.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import johnsUtil.Main;
import johnsUtil.model.SharedResources.Account;
import johnsUtil.model.SharedResources.Screen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the navigation screen.
 */
public class NavigationPaneController implements Initializable {
    @FXML
    private VBox vbox;

    @FXML
    private ImageView logo;

    @FXML
    private JFXButton inboxBtn;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXButton appBtn;

    @FXML
    private JFXButton reviseBtm;

    @FXML
    private JFXButton workerBtn;

    @FXML
    private JFXButton mainMenuBtn;

    @FXML
    private JFXButton accountBtn;

    @FXML
    private JFXButton logoutBtn;

    /**
     * Initializes the navigation screen.
     * @param location Location of alcohol image.
     * @param resources ResourceBundle for resources for the application.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image(Main.class.getResourceAsStream("/alch.png"));
        if(img != null){
            logo.setImage(img);
        }

        int type = Account.getInstance().getUserType();


        if(3 == type || 0 == type){
            vbox.getChildren().remove(inboxBtn);
        }
        if(2 != type){
           vbox.getChildren().removeAll(appBtn,reviseBtm);
        }
        if(0 != type){
            vbox.getChildren().remove(workerBtn);
        }
        if(3 == type){
            vbox.getChildren().removeAll(accountBtn,logoutBtn);
        }
        else{
            vbox.getChildren().remove(mainMenuBtn);
        }



        logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(Screen.getInstance().createConfirmBox("Logout", "Are you sure you want to logout, all of your unsaved progress will be lose?")){
                    Account.getInstance().logout();
                    Stage primaryStage = Account.getInstance().getWindow();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getClassLoader().getResource("johnsUtil/Views/MainMenu.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    primaryStage.getScene().setRoot(root);
                }
            }
        });



        //TODO Add listerns to change scenes
    }
}
