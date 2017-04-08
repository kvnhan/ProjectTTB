package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for ErrorState Screen.
 */
public class ErrorStateController {
    ScreenUtil screenUtil = new ScreenUtil();

    @FXML
    private Text errorMessageText;

    public void goBack(){
        screenUtil.switchScene("MainMenu.fxml", "Main Menu");

    }

}
