package johnsUtil.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import johnsUtil.Main;
import johnsUtil.model.SharedResources.Account;
import johnsUtil.model.SharedResources.Database;
import sample.AccountsUtil;
import sample.DatabaseUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller for the registration screen.
 */
public class AccountUIController implements Initializable {

    @FXML
    private BorderPane root;




    @FXML
    private Label FullName, Username, PhoneNumber, Address, Password, EmailAddress, UserType;

    private File file;


    /**
     * Initializes the registration screen.
     * @param location Location of the icon.
     * @param resources ResourceBundle for the application.
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources){
        {
            AccountsUtil accountUtil = new AccountsUtil();
            DatabaseUtil dataUtil = new DatabaseUtil();
           // Account acc = new Account();
            //System.out.print("getUsername()");
            //accountUtil.loadFile();
            Account acc = Account.getInstance();
            if(acc.getUserType() == 1){
                UserType.setText("Government Agent");
            }else if(acc.getUserType() == 2){
                UserType.setText("Manufacturer");
            }else{
                UserType.setText("Super");
            }
            FullName.setText(acc.getName());
            Username.setText(acc.getUserName());
            PhoneNumber.setText(acc.getPhoneNum());
            Address.setText(acc.getAddress());
           // Password.setText(acc.);
            EmailAddress.setText(acc.getEmail());


            //System.out.println(Account.getInstance().getUserName());
           // System.out.println(dataUtil.getAccountItems());
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
