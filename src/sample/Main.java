package sample;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

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
        try {
            URL url = Main.class.getProtectionDomain().getCodeSource().getLocation();
            String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
            String parentPath = new File(jarPath).getParentFile().getPath();

            String fileSeparator = System.getProperty("file.separator");
            String newDir = parentPath + fileSeparator + "images" + fileSeparator;
            File file = new File(newDir);
            if(!file.exists()) {
                file.mkdir();
                System.out.println("It works!!!");
                System.out.println(newDir);
            }else{
                System.out.println("Directory already exists");
                System.out.println(newDir);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        launch(args);
    }




}
