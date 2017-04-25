package sample;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.InputStream;

/**
 * Created by peternolan on 4/25/17.
 */
public class WorkFlowInboxHelpController {
    ScreenUtil screenUtil = new ScreenUtil();
    @FXML
    ImageView screenImage;
    @FXML
    private TextArea text;

    public void goBack(){
        screenUtil.switchScene("WorkFlow.fxml", "Inbox");
    }
}
