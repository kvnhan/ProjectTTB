package sample;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Runs application.
 */
public class Main extends Application{
    ScreenUtil screenUtil = new ScreenUtil();


    @Override
    public void start(Stage primaryStage) throws Exception{
        screenUtil.switchScene("Login.fxml", "Login");
    }

    public static void main(String[] args) {
        launch(args);
    }




}
