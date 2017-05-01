package sample;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.awt.*;

/**
 * Controller for the application changes screen.
 */
public class AppChangesController {

    ScreenUtil screenUtil = new ScreenUtil();
    @FXML
    ImageView screenImage;
    @FXML
    private TextArea text;


    /*
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
    */

    /**
     * Brings user back to the Revisions Help screen.
     */
    public void goBack(){
        screenUtil.switchScene("ReviseMenu.fxml", "Revisions");
    }

}
