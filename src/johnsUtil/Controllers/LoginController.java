package johnsUtil.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import johnsUtil.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label errorLabel;

    @FXML
    private JFXButton close;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXButton login;

    @FXML
    private JFXPasswordField password;

    @FXML
    private BorderPane pane;

    @FXML
    private ImageView icon;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image(Main.class.getResourceAsStream("/user2.png"));
        if(img != null){
            icon.setImage(img);
        }
    }

    @FXML
    void close(ActionEvent event) {
        System.out.println("CLicked");
        Node currentSource = (Node) event.getSource();
        currentSource.getScene().getWindow().hide();
    }

    @FXML
    void submit(ActionEvent event) {
        //
    }

}
