package sample;

import javafx.fxml.FXML;

import java.awt.*;

/**
 * Created by peternolan on 4/15/17.
 */
public class SearchHelpController {

    ScreenUtil screenUtil = new ScreenUtil();

    @FXML
    private TextArea text;

    public void goBack(){
        screenUtil.switchScene("SearchMenu.fxml", "Search");

    }


}
