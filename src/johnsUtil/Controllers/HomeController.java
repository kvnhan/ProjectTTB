package johnsUtil.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
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
    private BorderPane center;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private VBox burgerBox;

    private VBox vbox;
    private BorderPane borderPane;
    private ImageView logo;
    private GridPane gridPane;



    @FXML
    private JFXHamburger hamburger;

    @Override
    /**
     * initializes the homepage.
     */
    public void initialize(URL location, ResourceBundle resources) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        Account.getInstance().getWindow().setHeight(primaryScreenBounds.getHeight());
        Account.getInstance().getWindow().setWidth(primaryScreenBounds.getWidth());
        Account.getInstance().getWindow().setMaximized(true);

        try {
            vbox = FXMLLoader.load(getClass().getClassLoader().getResource("johnsUtil/Views/NavigationPane.fxml"));
            borderPane = FXMLLoader.load(getClass().getClassLoader().getResource("johnsUtil/Views/SearchMenu.fxml"));
            for(Node node : borderPane.getChildren()){
                if(node.getAccessibleText() != null && node.getAccessibleText().equals("gridPane")){
                    gridPane = (GridPane) node;
                }
            }
            gridPane.add(hamburger,0,2);
            center.getChildren().remove(burgerBox);
            center.setCenter(borderPane);
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
