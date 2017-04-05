package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class WorkFlowController {

    private FXMLLoader fxmlLoader;
    @FXML private Button back;
    @FXML private Button first;
    ScreenUtil work = new ScreenUtil();

    public void buttonClicked (ActionEvent event){
        if(event.getSource() == back){
            work.pullUpScreen("MainMenu.fxml","Main Menu", event);
        }
    }
    public void firstButton(ActionEvent event){
        work.pullUpScreen("ApplicationReview.fxml", "Application Review", event);
    }
}