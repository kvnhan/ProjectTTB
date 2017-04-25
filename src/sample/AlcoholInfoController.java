package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import javafx.scene.control.Hyperlink;

/**
 * Controller for the alcohol info page that appears when an alcohol is selected from a search.
 */
public class AlcoholInfoController {
    DataPasser dataPass = new DataPasser();

    @FXML
    Label alcAID, alcBrandName, alcType, alcAppelation, alcSulfite, alcAlcoholContent, alcNetContent,alcHealthWarning,
          alcProductType, alcClass, alcLegibility, alcSize, alcFormula, alcInfo;
    @FXML
    ImageView alcImage;
    @FXML
    Hyperlink buy, review;
    @FXML

    Button close;
    private boolean foundImage = false;

    public void initialize(){
        alcAID.setText(String.valueOf(dataPass.getAlcData().getAid()));
        alcBrandName.setText(String.valueOf(dataPass.getAlcData().getBrandName()));
        alcType.setText(String.valueOf(dataPass.getAlcData().getAlcoholType()));
        alcAppelation.setText(String.valueOf(dataPass.getAlcData().getAppellation()));
        alcSulfite.setText(String.valueOf(dataPass.getAlcData().getSulfiteDescription()));
        alcAlcoholContent.setText(String.valueOf(dataPass.getAlcData().getAlchContent()));
        alcNetContent.setText(String.valueOf(dataPass.getAlcData().getNetContent()));
        alcHealthWarning.setText(String.valueOf(dataPass.getAlcData().getHealthWarning()));
        alcProductType.setText(String.valueOf(dataPass.getAlcData().getProductType()));
        alcClass.setText(String.valueOf(dataPass.getAlcData().getClassType()));
        alcLegibility.setText(String.valueOf(dataPass.getAlcData().getLabelLegibility()));
        alcSize.setText(String.valueOf(dataPass.getAlcData().getLabelSize()));
        alcFormula.setText(String.valueOf(dataPass.getAlcData().getFormulas()));
        alcInfo.setText(String.valueOf(dataPass.getAlcData().getBottlersInfo()));

        try {
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/"  + dataPass.getAlcData().getAid() + ".jfif");
            alcImage.setImage(new javafx.scene.image.Image(resource, 500.0, 0.0, true, true));
            foundImage = true;
        }
        catch(NullPointerException nullPoint){
            if(foundImage == false) {
                try {
                    String path = getPath();
                    File file = new File(path + "/" + dataPass.getAlcData().getAid() + ".jpg");
                    javafx.scene.image.Image image = new javafx.scene.image.Image(file.toURI().toString());
                    System.out.println(dataPass.getAlcData().getAid());
                    alcImage.setImage(image);
                } catch (Exception e) {
                    InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/imageUnavailable.jpg");
                    alcImage.setImage(new javafx.scene.image.Image(resource, 100.0, 0.0, true, true));
                    System.out.println("Image Was Not Found For " + dataPass.getAlcData().getBrandName() + "'s " + dataPass.getAlcData().getName());
                }
            }
        }

    }

    public void closeWindow(){
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void buy(ActionEvent event){
        Application a = new Application() {
            @Override
            public void start(Stage primaryStage) throws Exception {

            }
        };
        a.getHostServices().showDocument("http://www.wine-searcher.com/find/" + alcBrandName.getText());
    }
    @FXML
    public void review(ActionEvent event){
        Application a = new Application() {
            @Override
            public void start(Stage primaryStage) throws Exception {

            }
        };
        a.getHostServices().showDocument("https://www.google.com/search?q=" + alcBrandName.getText() + " reviews");
        // a.getHostServices().showDocument("https://www.tripadvisor.com/Search?geo=&pid=3825&redirect=&startTime=&uiOrigin=&q=" + alcBrandName.getText());
    }


    public String getPath() throws UnsupportedEncodingException {
        URL url = this.getClass().getProtectionDomain().getCodeSource().getLocation();
        String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
        String parentPath = new File(jarPath).getParentFile().getPath();

        String fileSeparator = System.getProperty("file.separator");
        String newDir = parentPath + fileSeparator + "images" + fileSeparator;

        System.out.println(newDir);

        return newDir;
    }
}
