package johnsUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import johnsUtil.model.SharedResources.Account;

/**
 * Initializes the application.
 */

public class Main extends Application {
    /**
     * Starts the application.
     * @param primaryStage Homescreen for the application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        String fxmlFile = "Views/MainMenu.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        primaryStage.setTitle("TTB Client");
        Scene mainScene = new Scene(root,1100, 700);
        if(fxmlFile.contains("MainMenu.fxml")){
            String mainCss = this.getClass().getResource("Views/Styling/main.css").toExternalForm();
            mainScene.getStylesheets().add(mainCss);
        }
        primaryStage.setScene(mainScene);
        primaryStage.setMaximized(true);
        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(1300);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
