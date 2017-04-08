package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class NewAppController {

    private FXMLLoader fxmlLoader;
    @FXML Button back, newApp, oldApp;
    ScreenUtil work = new ScreenUtil();


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