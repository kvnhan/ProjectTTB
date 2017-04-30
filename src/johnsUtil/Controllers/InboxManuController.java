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
 * Created by John on 4/21/2017.
 * //
 */
public class InboxManuController implements Initializable {

    @FXML
    private JFXDrawer drawer;

    private VBox vbox;
    private HBox hbox;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private BorderPane borderPane;

    @FXML
    private JFXButton btnMinimize;

    @FXML
    private JFXButton btnExpand;

    @FXML
    private JFXButton btnClose;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            vbox = FXMLLoader.load(getClass().getResource("/wpi/Views/NavigationPane.fxml"));
            //hbox = FXMLLoader.load(getClass().getResource("/wpi/Toolbar.fxml")); toolbar

            drawer.setSidePane(vbox);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HamburgerBasicCloseTransition transition = new HamburgerBasicCloseTransition(hamburger);
        transition.setRate(1);

        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            transition.setRate(transition.getRate()*-1);
            transition.play();

            if(drawer.isShown()){
                drawer.close();
            }
            else{
                drawer.open();
            }
        });
        drawer.open();
        //borderPane.setTop(hbox); toolbar
    }


}
