package sample;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.awt.*;

/**
 * Controller for the "About" page.
 */
public class AboutController {
    ScreenUtil screenUtil = new ScreenUtil();

    @FXML
    private TextArea text;

    /**
     * Brings user back to the main menu.
     */
    public void goBack(){
        screenUtil.switchScene("MainMenu.fxml", "Main Menu");

    }

}
