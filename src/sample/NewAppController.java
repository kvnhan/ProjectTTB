package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

/**
 * Controller for the New Application screen.
 */
public class NewAppController {

    private FXMLLoader fxmlLoader;
    @FXML Button back, newApp, oldApp;
    ScreenUtil work = new ScreenUtil();

    /**
     * Checks which button was pressed and switches scenes accordingly.
     * Pressing the back button brings the user to the main menu, pressing the
     * newApp button brings the user to the NewLabel screen,
     * and pressing the oldApp button brings the user to the ReviseMenu screen.
     * @param event ActionEvent representing a button press.
     */
    public void buttonClicked (javafx.event.ActionEvent event){
        if(event.getSource() == back){
            work.switchScene("MainMenu.fxml","Main Menu");
        }else if (event.getSource() == newApp) {
            work.switchScene("NewLabel.fxml","New Application");
        } else if (event.getSource() == oldApp) {
            work.switchScene("ReviseMenu.fxml","Revise Application");
        }

    }
}