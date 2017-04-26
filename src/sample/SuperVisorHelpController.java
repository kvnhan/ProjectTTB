package sample;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.awt.*;

/**
 * Created by peternolan on 4/26/17.
 */
public class SuperVisorHelpController {
    ScreenUtil screenUtil = new ScreenUtil();
    @FXML
    ImageView screenImage;
    @FXML
    private TextArea text;

    public void goBack(){
        screenUtil.switchScene("supervisorView.fxml", "Assignments");
    }

}
