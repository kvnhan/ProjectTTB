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


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 370, 400));
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(370);
        primaryStage.setMaxHeight(400);
        primaryStage.setMaxWidth(370);
        primaryStage.setMaximized(false);
        primaryStage.show();

   }



    public static void main(String[] args) throws SQLException{
        DatabaseUtil db = new DatabaseUtil();

        launch(args);
    }




}
