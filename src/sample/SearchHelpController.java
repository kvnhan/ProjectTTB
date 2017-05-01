package sample;

import javafx.fxml.FXML;

import java.awt.*;
import java.io.InputStream;
import javafx.scene.image.ImageView;

/**
 * Help screen for searches.
 */
public class SearchHelpController {

    ScreenUtil screenUtil = new ScreenUtil();
    @FXML ImageView screenImage;

    /**
     * Initializes search help screen.
     */
    public void initialize(){

        try {
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("resources/SearchUI.png");
            screenImage.setImage(new javafx.scene.image.Image(resource, 500.0, 0.0, true, true));
        }
        catch(NullPointerException nullPoint){
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/imageUnavailable.jpg");
            screenImage.setImage(new javafx.scene.image.Image(resource, 100.0, 0.0, true, true));
            System.out.println("Image Was Not Found");
        }

    }

    @FXML
    private TextArea text;

    /**
     * Returns users to the search menu.
     */
    public void goBack(){
        screenUtil.switchScene("SearchMenu.fxml", "Search");

    }


}
