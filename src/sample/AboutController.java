package sample;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.awt.*;

/**
 * Created by peternolan on 4/15/17.
 */
public class AboutController {
    ScreenUtil screenUtil = new ScreenUtil();

    @FXML
    private TextArea text;

    public void goBack(){
        screenUtil.switchScene("MainMenu.fxml", "Main Menu");

    }

}
