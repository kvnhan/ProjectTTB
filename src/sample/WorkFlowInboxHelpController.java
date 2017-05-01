package sample;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.InputStream;

/**
 * Help screen for the workflow inbox.
 */
public class WorkFlowInboxHelpController {
    ScreenUtil screenUtil = new ScreenUtil();
    @FXML
    ImageView screenImage;
    @FXML
    private TextArea text;

    /**
     * Returns user to the workflow inbox screen.
     */
    public void goBack(){
        screenUtil.switchScene("WorkFlow.fxml", "Inbox");
    }
}
