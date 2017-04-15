package sample;

import javafx.fxml.FXML;

import java.awt.*;

/**
 * Created by peternolan on 4/15/17.
 */
public class NewHelpController {
    ScreenUtil screenUtil = new ScreenUtil();

    @FXML
    private TextArea text;

    public void goBack(){
        screenUtil.switchScene("NewLabel.fxml", "New Label");

    }


}
