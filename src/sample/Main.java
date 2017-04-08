package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application{
    ScreenUtil screenUtil = new ScreenUtil();


    @Override
    public void start(Stage primaryStage) throws Exception{
        screenUtil.switchScene("Login.fxml", "Login");
   }



    public static void main(String[] args) throws SQLException{
        DatabaseUtil db = new DatabaseUtil();

        launch(args);
    }




}
