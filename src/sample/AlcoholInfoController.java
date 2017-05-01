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
          alcProductType, alcClass, alcLegibility, alcSize, alcFormula, alcInfo, alcFancyName;
    @FXML
    ImageView alcImage;
    @FXML
    Hyperlink buy, review;
    @FXML

    Button close;
    private boolean foundImage = false;
    //removes null text
    private String setTextHelper(String s){
        if(s.equals("null"))
                return " ";
        else if(s.equals(".")){
            return " ";
        }
        else
            return s;
    }
    //returns class
    private String setTextClassHelper(int alcClass, int alcType){
        if(alcType==1){
            switch(alcClass){
                case 0:
                    return "ALE";
                case 1:
                    return "BEER";
                case 2:
                    return "CEREAL BEVERAGE";
                case 3:
                    return "LAGER";
                case 4:
                    return "SPECIALTY MALT BEVERAGE";
                case 5:
                    return "MALT LIQUOR";
                case 6:
                    return "NEAR BEER";
                case 7:
                    return "PORTER";
                case 8:
                    return "STOUT";
                default:
                    return "BEER";
            }
        }
        else if(alcType==2){
            switch(alcClass){
                case 80:
                    return "RED WINE";
                default:
                    return "WINE";
            }
        }
        else{
            return "Distilled Spirits";
        }
    }

    /**
     * Converts an alcohol type to a readable string.
      * @param alcType Int representing the alcohol type. 1 = Malt Beverage,
     *                2 = Wine, 3 = Distilled Spirits, and anything else =
     *                 Alcohol.
     * @return Returns the readable string type as enumerated above.
     */
    private String setTextTypeHelper(int alcType){
        switch(alcType){
            case 1:
                return "Malt Beverage";
            case 2:
                return "Wine";
            case 3:
                return "Distilled Spirits";
            default:
                return "Alcohol";
        }
    }

    /**
     * Initializes the alcohol info screen.
     */
    public void initialize(){
        alcAID.setText(setTextHelper(String.valueOf(dataPass.getAlcData().getAid())));
        alcBrandName.setText(setTextHelper(String.valueOf(dataPass.getAlcData().getBrandName())));
        alcFancyName.setText(setTextHelper(String.valueOf(dataPass.getAlcData().getName())));
        alcType.setText(setTextTypeHelper(Integer.valueOf(dataPass.getAlcData().getAlcoholType())));
        alcAppelation.setText(setTextHelper(String.valueOf(dataPass.getAlcData().getAppellation())));
        alcSulfite.setText(setTextHelper(String.valueOf(dataPass.getAlcData().getSulfiteDescription())));
        alcAlcoholContent.setText(setTextHelper(String.valueOf(dataPass.getAlcData().getAlchContent())));
        alcNetContent.setText(setTextHelper(String.valueOf(dataPass.getAlcData().getNetContent())));
        alcHealthWarning.setText(setTextHelper(String.valueOf(dataPass.getAlcData().getHealthWarning())));
        alcProductType.setText(setTextHelper(String.valueOf(dataPass.getAlcData().getProductType())));
        alcClass.setText(setTextClassHelper(Integer.valueOf(dataPass.getAlcData().getClassType()),Integer.valueOf(dataPass.getAlcData().getAlcoholType())));
        alcLegibility.setText(setTextHelper(String.valueOf(dataPass.getAlcData().getLabelLegibility())));
        alcSize.setText(setTextHelper(String.valueOf(dataPass.getAlcData().getLabelSize())));
        alcFormula.setText(setTextHelper(String.valueOf(dataPass.getAlcData().getFormulas())));
        alcInfo.setText(setTextHelper(String.valueOf(dataPass.getAlcData().getBottlersInfo())));

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

    /**
     * Closes the alcohol info screen.
     */
    public void closeWindow(){
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    /**
     * Takes user to search for the alcohol online.
     * @param event Button pressed.
     */
    @FXML
    public void buy(ActionEvent event){
        Application a = new Application() {
            @Override
            public void start(Stage primaryStage) throws Exception {

            }
        };
        a.getHostServices().showDocument("http://www.wine-searcher.com/find/" + alcFancyName.getText() + "%20" + alcBrandName.getText());
    }

    /**
     * Shows reviews for the alcohol.
     * @param event Button clicked.
     */
    @FXML
    public void review(ActionEvent event){
        Application a = new Application() {
            @Override
            public void start(Stage primaryStage) throws Exception {

            }
        };
        a.getHostServices().showDocument("https://www.google.com/search?q=" + alcFancyName.getText() + " " + alcBrandName.getText() + " reviews");
        // a.getHostServices().showDocument("https://www.tripadvisor.com/Search?geo=&pid=3825&redirect=&startTime=&uiOrigin=&q=" + alcBrandName.getText());
    }

    /**
     * Gets the path to the alcohol image.
     * @return Returns the image path.
     * @throws UnsupportedEncodingException
     */
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
