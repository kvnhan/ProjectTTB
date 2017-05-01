package johnsUtil.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Controller for the Manufacturer Inbox screen.
 */
public class InboxManuController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private JFXButton btnMinimize;

    @FXML
    private JFXButton btnExpand;

    @FXML
    private JFXButton btnClose;

    @Override
    /**
     * Initializes the manufacturer inbox.
     */
    public void initialize(URL location, ResourceBundle resources) {



    }


}
