package johnsUtil.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import johnsUtil.model.SharedResources.Account;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Controller for the home page.
 */
public class HomeController implements Initializable {

    @FXML
    private JFXDrawer drawer;

    private VBox vbox;
    private ImageView logo;
    private JFXButton btnHome;
    private JFXButton btnInbox;
    private JFXButton btnSearch;
    private JFXButton btnApp;
    private JFXButton btnRevise;
    private JFXButton btnWorker;
    private JFXButton btnNewAcc;
    private JFXButton btnAccount;

    @FXML
    private JFXHamburger hamburger;

    @Override
    /**
     * initializes the homepage.
     */
    public void initialize(URL location, ResourceBundle resources) {
        //btnClose.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/closeOld.png"))));
       // btnExpand.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/maximizeOld.png"))));
        //btnMinimize.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/minimize.png"))));
        //btnLogout.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/logout.png"))));

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        Account.getInstance().getWindow().setHeight(primaryScreenBounds.getHeight());
        Account.getInstance().getWindow().setWidth(primaryScreenBounds.getWidth());
        Account.getInstance().getWindow().setMaximized(true);

        try {
            vbox = FXMLLoader.load(getClass().getClassLoader().getResource("johnsUtil/Views/NavigationPane.fxml"));
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
    }


}
