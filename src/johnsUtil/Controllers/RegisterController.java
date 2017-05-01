package johnsUtil.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import johnsUtil.Main;
import johnsUtil.model.SharedResources.Account;
import johnsUtil.model.SharedResources.Database;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller for the registration screen.
 */
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

    @FXML
    private JFXPasswordField passTF;

    @FXML
    private Label errorText;

    private File file;


    /**
     * Initializes the registration screen.
     * @param location Location of the icon.
     * @param resources ResourceBundle for the application.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeCB.setStyle("-fx-font: 20px \"Calibri Light\";");
        typeCB.getItems().addAll("Manufacturer","Government Worker");

        Image img = new Image(Main.class.getResourceAsStream("/defaultIcon.png"));
        if(img != null){
            icon.setImage(img);
        }

        file = null;
    }

    /**
     * Handles a submission of a new account.
     * @param event Submit button clicked.
     */
    @FXML
    void handleSubmit(ActionEvent event) {
        boolean havefields = true;

        try {
            if(userTF.getText().trim().equals("")){
                userTF.setStyle("-fx-prompt-text-fill: red;");
                userTF.setPromptText("Full Name *Required Field*");
                havefields = false;
            }
            else if(userTF.getText().trim().length() > 15 || userTF.getText().trim().length() < 5){
                userTF.setStyle("-fx-prompt-text-fill: red;");
                userTF.setPromptText("Full Name *Must be 5 - 15 characters long*");
                havefields = false;
            }
            else if(Database.getInstance().contains("ACCOUNT","USERNAME",userTF.getText().trim())){
                userTF.setStyle("-fx-prompt-text-fill: red;");
                userTF.setPromptText("Username *Username Taken!*");
                havefields = false;
            }
            else{
                nameTF.setStyle("-fx-prompt-text-fill: rgb(178,178,178);");
                nameTF.setPromptText("Username");
            }
            errorText.setText("");
        } catch (SQLException e) {
            errorText.setText("There was a problem connecting to the database (X2)");
            havefields = false;
        }

        if(nameTF.getText().trim().equals("")){
            nameTF.setStyle("-fx-prompt-text-fill: red;");
            nameTF.setPromptText("Full Name *Required Field*");
            havefields = false;
        }
        else{
            nameTF.setStyle("-fx-prompt-text-fill: rgb(178,178,178);");
            nameTF.setPromptText("Full Name");
        }
        if(passTF.getText().trim().equals("")){
            passTF.setStyle("-fx-prompt-text-fill: red;");
            passTF.setPromptText("Password *Required Field*");
            havefields = false;
        }
        else{
            passTF.setStyle("-fx-prompt-text-fill: rgb(178,178,178);");
            passTF.setPromptText("Password");
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

        if(havefields){
            //TODO add other fields and verify usertype
            String userName = userTF.getText().trim();
            String pass = passTF.getText();
            String name = nameTF.getText().trim();
            String address = addressTF.getText().trim();
            String email = emailTF.getText().trim();
            String phone = phoneTF.getText().trim();
            int type;
            if(typeCB.getSelectionModel().getSelectedItem().equals("Manufacturer")){
                type = 2;
            }
            else{
                type = 1;
            }

            try {
                Account.getInstance().createAccount(userName,pass,name,address,email,phone,type,file);
                Account.getInstance().login(userName,pass);

            } catch (SQLException e) {
                e.printStackTrace();
                errorText.setText("There was a problem connecting to the database (X2)");
            }
            //TODO change scene
        }
    }

    /**
     * Handles the browse button being clicked.
     * @param event Browse button is clicked.
     */
    @FXML
    void handleBrowse(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Icon Picture");
        file = fileChooser.showOpenDialog(nameTF.getScene().getWindow());

        if (file != null) {
            Image img = new Image(file.toURI().toString());
            icon.setImage(img);
        }
    }

    /**
     * Closes the registration screen.
     * @param event Close button clicked.
     */
    @FXML
    void handleClose(ActionEvent event) {
        Node currentSource = (Node) event.getSource();
        currentSource.getScene().getWindow().hide();
    }

}
