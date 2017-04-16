package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller for the alcohol info page that appears when an alcohol is selected from a search.
 */
public class AlcoholInfoController {
    DataPasser dataPass = new DataPasser();
    @FXML
    Label alcAID, alcBrandName, alcType, alcAppelation, alcSulfite, alcAlcoholContent, alcNetContent,alcHealthWarning,
          alcProductType, alcClass, alcLegibility, alcSize, alcFormula, alcInfo;


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
    }
}
