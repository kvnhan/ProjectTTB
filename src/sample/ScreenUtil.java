package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Adonay on 4/3/2017.
 */
public class ScreenUtil {
    private FXMLLoader fxmlLoader;
    private static final Stage mainWindow = new Stage();
    private Stage alertWindow;
    private Parent root1;

    public void switchScene(String fxmlName,String title){
        fxmlLoader = new FXMLLoader(getClass().getResource(fxmlName));

        try{
            root1 = fxmlLoader.load();
            mainWindow.setTitle(title);
            mainWindow.setScene(new Scene(root1));
            mainWindow.setResizable(false);
            mainWindow.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void createAlertBox(String title, String message){
        alertWindow = new Stage();

        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle(title);
        alertWindow.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("OK");
        closeButton.setOnAction(e -> alertWindow.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        alertWindow.setScene(scene);
        alertWindow.showAndWait();
    }
}
