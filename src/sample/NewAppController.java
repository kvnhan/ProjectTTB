package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import javafx.scene.image.ImageView;
import java.io.InputStream;

/**
 * Controller for the New Application screen.
 */
public class NewAppController {

    private @FXML Button back, newApp, oldApp, Updates;
    private @FXML ImageView symbolImage;
    private ScreenUtil screenUtil = new ScreenUtil();

    /**
     * Checks which button was pressed and switches scenes accordingly.
     * Pressing the back button brings the user to the main menu, pressing the
     * newApp button brings the user to the NewLabel screen,
     * and pressing the oldApp button brings the user to the ReviseMenu screen.
     * @param event ActionEvent representing a button press.
     */
    public void buttonClicked (javafx.event.ActionEvent event){
        if(event.getSource() == back){
            screenUtil.switchScene("MainMenu.fxml","Main Menu");
        }else if (event.getSource() == newApp) {
            screenUtil.switchScene("NewLabel.fxml","New Application");
        } else if (event.getSource() == oldApp) {
            screenUtil.switchScene("ReviseApp.fxml","Revise Application");
        } else if (event.getSource() == Updates) {
            screenUtil.switchScene("ReviseMenu.fxml", "Revise Application");
        }
    }
/*
    public void initialize(){

        try {
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("resources/Symbol.png");
            symbolImage.setImage(new javafx.scene.image.Image(resource, 500.0, 0.0, true, true));
        }
        catch(NullPointerException nullPoint){
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/imageUnavailable.jpg");
            symbolImage.setImage(new javafx.scene.image.Image(resource, 100.0, 0.0, true, true));
            System.out.println("Image Was Not Found");
        }

    }
*/

}