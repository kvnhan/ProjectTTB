package johnsUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import johnsUtil.model.SharedResources.Account;
import johnsUtil.model.SharedResources.Database;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

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
        primaryStage.setScene(mainScene);
        primaryStage.setMaximized(true);
        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(1300);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/alch.png")));
        Account.getInstance().setWindow(primaryStage);
        primaryStage.show();
    }


    public static void main(String[] args) {
        try {
            URL url = sample.Main.class.getProtectionDomain().getCodeSource().getLocation();
            String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
            String parentPath = new File(jarPath).getParentFile().getPath();

            String fileSeparator = System.getProperty("file.separator");
            String newDir = parentPath + fileSeparator + "images" + fileSeparator;
            File file = new File(newDir);
            if(!file.exists()) {
                file.mkdir();
            }else{
                System.out.println(newDir);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        launch(args);
    }
}
