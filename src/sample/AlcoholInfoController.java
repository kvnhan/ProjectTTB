package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.InputStream;

/**
 * Created by Chris on 4/13/2017.
 */
public class AlcoholInfoController {
    DataPasser dataPass = new DataPasser();
    @FXML
    Label alcAID, alcBrandName, alcType, alcAppelation, alcSulfite, alcAlcoholContent, alcNetContent,alcHealthWarning,
          alcProductType, alcClass, alcLegibility, alcSize, alcFormula, alcInfo;
    @FXML
    ImageView alcImage;
    @FXML
    Button close;

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
        }
        catch(NullPointerException nullPoint){
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/imageUnavailable.jpg");
            alcImage.setImage(new javafx.scene.image.Image(resource, 100.0, 0.0, true, true));
            System.out.println("Image Was Not Found For " + dataPass.getAlcData().getBrandName() + "'s "+ dataPass.getAlcData().getName());
        }

    }

    public void closeWindow(){
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
}
