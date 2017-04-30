package johnsUtil.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.Main;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by John on 4/27/2017.
 */
public class toolbarController implements Initializable {
    @FXML
    private JFXButton btnMinimize;
    @FXML
    private JFXButton btnExpand;
    @FXML
    private JFXButton btnClose;
    @FXML
    private HBox toolbar;

    private static double xOffset = 0;
    private static double yOffset = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnClose.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/closeOld.png"))));
        btnExpand.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/maximizeOld.png"))));
        btnMinimize.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/minimize.png"))));
    }

    @FXML
    void handleClose(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(btnClose.getScene().getWindow());
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Are you sure you want to do this, all unsaved progress will be lost?");
        alert.setContentText("");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Platform.exit();
            System.exit(0);
        } else {

        }
    }

    @FXML
    void handleMaximize(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.setMaximized(true);
    }

    @FXML
    void handleMinimize(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.setIconified(true);
    }

}
