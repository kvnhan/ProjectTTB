package johnsUtil.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import johnsUtil.Main;
import johnsUtil.model.SharedResources.Account;
import johnsUtil.model.SharedResources.Screen;
import sample.ScreenUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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

    @FXML
    private JFXButton supervisorBtn;


    ScreenUtil work = new ScreenUtil();

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


        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent scroll = FXMLLoader.load(getClass().getClassLoader().getResource("johnsUtil/Views/SearchMenu.fxml"));
                    getRoot().setCenter(scroll);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        workerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent scroll = FXMLLoader.load(getClass().getClassLoader().getResource("johnsUtil/Views/Supervisor.fxml"));
                    getRoot().setCenter(scroll);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        inboxBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent scroll = FXMLLoader.load(getClass().getClassLoader().getResource("johnsUtil/Views/InboxManu.fxml"));
                    getRoot().setCenter(scroll);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        reviseBtm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent scroll = FXMLLoader.load(getClass().getClassLoader().getResource("sample/ReviseApp.fxml"));
                    getRoot().setCenter(scroll);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        appBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent scroll = FXMLLoader.load(getClass().getClassLoader().getResource("johnsUtil/Views/NewLabel.fxml"));
                    getRoot().setCenter(scroll);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        inboxBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent scroll = FXMLLoader.load(getClass().getClassLoader().getResource("johnsUtil/Views/InboxManu.fxml"));
                    getRoot().setCenter(scroll);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(Screen.getInstance().createConfirmBox("Logout", "Are you sure you want to logout, all of your unsaved progress will be lost?")){
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

        mainMenuBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(Screen.getInstance().createConfirmBox("Logout", "Are you sure you want to logout, all of your unsaved progress will be lose?")){
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

        inboxBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent scroll = FXMLLoader.load(getClass().getClassLoader().getResource("johnsUtil/Views/InboxManu.fxml"));
                    getRoot().setCenter(scroll);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        accountBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent acc = FXMLLoader.load(getClass().getClassLoader().getResource("johnsUtil/Views/Account.fxml"));
                    getRoot().setCenter(acc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private BorderPane getRoot(){
        BorderPane parent = (BorderPane) Account.getInstance().getWindow().getScene().getRoot();
        for(Node node: parent.getChildren()){
            if(node.getAccessibleText() != null && node.getAccessibleText().equals("center")){
                return (BorderPane) node;
            }
        }
        return null;
    }
}
