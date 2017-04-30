package johnsUtil.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import johnsUtil.Main;
import johnsUtil.model.SharedResources.Account;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
        Node currentSource = (Node) event.getSource();
        currentSource.getScene().getWindow().hide();
    }

    @FXML
    void submit(ActionEvent event) throws IOException {
        errorLabel.setText("");
        try {
            boolean successful = Account.getInstance().login(username.getText().trim(),password.getText().trim());

            if(successful){
                Stage primaryStage = (Stage) username.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("Views/Home.fxml"));
                primaryStage.setScene(new Scene(root));
            }
            else{
                errorLabel.setText("The username or password you've entered doesn't match any account");
            }
        } catch (SQLException e) {
            errorLabel.setText("There was a problem connecting to the database");
        }
    }

}
