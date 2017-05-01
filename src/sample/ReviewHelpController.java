package sample;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.InputStream;

/**
 * Help screen for searching applications.
 */
public class ReviewHelpController {
    ScreenUtil screenUtil = new ScreenUtil();

    @FXML
    private TextArea text;
    @FXML
    ImageView screenImage;

    /**
     * Initializes the search help screen.
     */
    public void initialize(){

        try {
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("resources/ReviewHelp.png");
            screenImage.setImage(new javafx.scene.image.Image(resource, 500.0, 0.0, true, true));
        }
        catch(NullPointerException nullPoint){
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/imageUnavailable.jpg");
            screenImage.setImage(new javafx.scene.image.Image(resource, 100.0, 0.0, true, true));
            System.out.println("Image Was Not Found");
        }

    }

    /**
     * Returns user to the search page.
     */
    public void goBack(){
        screenUtil.switchScene("WorkFlow.fxml", "Search");

    }
}
