package johnsUtil.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import johnsUtil.model.SharedResources.Account;
import sample.AccountsUtil;
import johnsUtil.model.*;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the registration screen.
 */
public class AccountController implements Initializable {

    @FXML
    private Label aid;

    @FXML
    private Label email;

    @FXML
    private Label username;

    @FXML
    private Label phone;

    @FXML
    private Label name;

    @FXML
    private Label address;

    @FXML
    private Label type;


    /**
     * Initializes the registration screen.
     * @param location Location of the icon.
     * @param resources ResourceBundle for the application.
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources){
        Account acc = Account.getInstance();
        if(acc.getUserType() == 1){
            type.setText("Government Agent");
        }else if(acc.getUserType() == 2){
            type.setText("Manufacturer");
        }else{
            type.setText("Super");
        }
        name.setText(acc.getName());
        username.setText(acc.getUserName());
        phone.setText(acc.getPhoneNum());
        address.setText(acc.getAddress());
        email.setText(acc.getEmail());
        aid.setText(acc.getAccountID()+"");
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
