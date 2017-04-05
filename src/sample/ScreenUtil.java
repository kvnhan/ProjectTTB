package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Adonay on 4/3/2017.
 */
public class ScreenUtil {
    FXMLLoader fxmlLoader;

    public void pullUpScreen(String fxmlName,String title, ActionEvent event){
        fxmlLoader = new FXMLLoader(getClass().getResource(fxmlName));

        try{
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle(title);
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
            Stage oldStage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            oldStage.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
