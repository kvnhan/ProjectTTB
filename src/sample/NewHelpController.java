package sample;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.InputStream;

/**
 * Created by peternolan on 4/15/17.
 */
public class NewHelpController {
    ScreenUtil screenUtil = new ScreenUtil();

    @FXML
    private TextArea text;
    @FXML
    ImageView screenImage;

    public void initialize(){

        try {
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("resources/NewLabelHelp.png");
            screenImage.setImage(new javafx.scene.image.Image(resource, 500.0, 0.0, true, true));
        }
        catch(NullPointerException nullPoint){
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/imageUnavailable.jpg");
            screenImage.setImage(new javafx.scene.image.Image(resource, 100.0, 0.0, true, true));
            System.out.println("Image Was Not Found");
        }

    }

    public void goBack(){
        screenUtil.switchScene("NewLabel.fxml", "New Label");

    }


}
