package johnsUtil.Controllers;

/**
 * Created by John on 4/27/2017.
 */


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import wpi.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private JFXTextField nameTF;

    @FXML
    private JFXTextField addressTF;

    @FXML
    private JFXTextField emailTF;

    @FXML
    private JFXTextField phoneTF;

    @FXML
    private JFXComboBox<String> typeCB;

    @FXML
    private JFXButton submit;

    @FXML
    private ImageView icon;

    @FXML
    private JFXButton browse;

    @FXML
    private JFXTextField userTF;

    private boolean haveFields;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        haveFields = false;
        typeCB.setStyle("-fx-font: 20px \"Calibri Light\";");
        typeCB.getItems().addAll("Manufacturer","Government Worker");

        Image img = new Image(Main.class.getResourceAsStream("/defaultIcon.png"));
        if(img != null){
            icon.setImage(img);
        }

    }

    @FXML
    void handleSubmit(ActionEvent event) {
        boolean havefields = true;

        if(nameTF.getText().trim().equals("")){
            nameTF.setStyle("-fx-prompt-text-fill: red;");
            nameTF.setPromptText("Full Name *Required Field*");
            havefields = false;
        }
        else{
           nameTF.setStyle("-fx-prompt-text-fill: rgb(178,178,178);");
            nameTF.setPromptText("Full Name");

        }

        if(userTF.getText().trim().equals("")){
            userTF.setStyle("-fx-prompt-text-fill: red;");
            userTF.setPromptText("Username *Required Field*");
            havefields = false;
        }
        else{
            userTF.setStyle("-fx-prompt-text-fill: rgb(178,178,178);");
            userTF.setPromptText("Username");
        }

        if(typeCB.getValue() == null){
            typeCB.setStyle("-fx-prompt-text-fill: red;");
            typeCB.setPromptText("User Type *Required Field*");
            havefields = false;
        }
        else{
            typeCB.setStyle("-fx-prompt-text-fill: rgb(178,178,178);");
            typeCB.setPromptText("User Type");
        }
    }

    @FXML
    void handleBrowse(ActionEvent event) {

    }

    @FXML
    void handleClose(ActionEvent event) {
        Node currentSource = (Node) event.getSource();
        currentSource.getScene().getWindow().hide();
    }

}
