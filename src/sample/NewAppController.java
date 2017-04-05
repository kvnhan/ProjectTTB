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
            work.pullUpScreen("MainMenu.fxml","Main Menu", event);
        }else if (event.getSource() == newApp) {
            work.pullUpScreen("NewLabel.fxml","New Application", event);
        } else if (event.getSource() == oldApp) {
            work.pullUpScreen("ReviseMenu.fxml","Revise Application", event);
        }

    }
}