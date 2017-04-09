package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

/**
 * Controller for Workflow screen.
 */

public class WorkFlowController {

    private FXMLLoader fxmlLoader;
    @FXML private Button back;
    @FXML private Button first;
    ScreenUtil work = new ScreenUtil();

    /**
     * Checks if the back button has been clicked and returns user to Main Menu.
     * @param event ActionEvent representing a button press.
     */
    public void buttonClicked (ActionEvent event){
        if(event.getSource() == back){
            work.switchScene("MainMenu.fxml","Main Menu");
        }

    }

    /**
     * Switches to the ApplicationReview screen.
     * @param event Button press representing the ViewFirst button.
     */
    public void firstButton(ActionEvent event){
        work.switchScene("ApplicationReview.fxml", "Application Review");
    }
}
//Hello