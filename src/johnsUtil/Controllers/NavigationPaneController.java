package johnsUtil.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import wpi.Main;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by John on 4/27/2017.
 */
public class NavigationPaneController implements Initializable {
    @FXML
    private ImageView logo;

    @FXML
    private JFXButton inboxBtn;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXButton appBtn;

    @FXML
    private JFXButton reviseBtm;

    @FXML
    private JFXButton workerBtn;

    @FXML
    private JFXButton newAccBtn;

    @FXML
    private JFXButton accountBtn;

    @FXML
    private JFXButton logoutBtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image(Main.class.getResourceAsStream("/alch.png"));
        if(img != null){
            logo.setImage(img);
        }
    }
}
