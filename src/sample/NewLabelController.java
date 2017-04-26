package sample;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TitledPane;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import javafx.scene.control.TextArea;

import javafx.scene.control.TextField;

import javax.imageio.ImageIO;
import java.util.Random;
/**
 * Controller for new label screen.
 */
public class NewLabelController{

    //  ApplicationUtil appUtil = new ApplicationUtil();
    DatabaseUtil db = new DatabaseUtil();
    ScreenUtil screenUtil = new ScreenUtil();

    @FXML private ImageView image;
    @FXML private JFXTextField RepID;
    @FXML private JFXTextField PlantReg;
    @FXML private JFXTextField SerialNo;
    @FXML private JFXTextField ApplicantName;
    @FXML private JFXTextField Name;
    @FXML private JFXTextField PhoneNumber;
    @FXML private JFXTextField MailingAddress;
    @FXML private JFXTextField originField;
    @FXML private JFXTextField BrandName;
    @FXML private JFXTextField Address;
    @FXML private JFXTextField Formula;
    @FXML private JFXTextField EmailAddress;
    @FXML private JFXTextField alcoholContent;
    @FXML private JFXTextField netContentField;
    @FXML private JFXTextField grapeVarietal;
    @FXML private JFXTextField Appellation;
    @FXML private JFXTextField sulfiteField;
    @FXML private JFXTextField Vintage;
    @FXML private JFXTextField bottlerField;
    @FXML private TextArea additionalInfoField;
    @FXML private JFXTextField pH;
    @FXML private TitledPane WinePane;
    @FXML private CheckBox dom1;
    @FXML private CheckBox dom11;
    @FXML private CheckBox dom111;
    @FXML private JFXRadioButton dom;
    @FXML private JFXRadioButton imp;
    @FXML private JFXRadioButton wineCheckBox;
    @FXML private JFXRadioButton beerCheckBox;
    @FXML private JFXRadioButton distilledCheckBox;
    @FXML private TextField type2Box;
    @FXML private TextField type3box;

    @FXML private Button Submit;
    @FXML private Button back;
    @FXML private Button clear;
    @FXML private Button helpNewButton;
    private String filepath;
    private File tempFile;
    @FXML
    /**
     * Clears information from the screen.
     */
    private void setClear(){
        screenUtil.switchScene("NewLabel.fxml", "New Label");
    }


    public void goBack (ActionEvent event){
        screenUtil.switchScene("MainMenu.fxml","Main Menu");
    }

    /**
     * Fills out an application in the database.
     * @throws SQLException
     */
    @FXML
    public void fillOutApplication(ActionEvent event) throws SQLException{
        System.out.print("Submitting");

        boolean valid = true;
        int repid = 0;
        String serial = "";
        String address = "";
        String fancyName = "";
        String formula = "";
        String grape_varietal = "";
        String appellation = "";
        int permit_no = 0;
        String infoOnBottle = "";
        String source_of_product = "";
        String type_of_product = "";
        String brand_name;
        String phone_number = "";
        String email = "";
        String date = "";
        String applicantName = "";
        double alcoholContentDouble = 0;
        int type1 = -1;
        String type2 = "";
        int type3 = 0;
        int vintage_date = 0;
        double ph_level = 0;
        int max = 999999999;

        int originCode = 0;
        double netContentDouble = 0;
        String sulfiteDesc= "";
        String bottlerInfo= "";
        String healthWarningText= "";


        if(!dom1.isSelected() && !dom11.isSelected() && !dom111.isSelected()){
            valid = false;
            screenUtil.createAlertBox("ERROR", "Please choose applicable box(es)");
        }else{
            if(dom1.isSelected()){
                type1 = 1;
            }
            if(dom11.isSelected()){
                type2  = type2Box.getText();
            }
            if(dom111.isSelected()){
                try {
                    type3 = Integer.parseInt(type3box.getText());
                }catch (NumberFormatException e){
                    screenUtil.createAlertBox("ERROR", "Invalid Input for Amount");
                    valid = false;
                }
            }else{
                type3 = -1;
            }
        }

        if(!RepID.getText().trim().isEmpty()) {
            try {
                repid = Integer.parseInt(RepID.getText());
                if (repid > max) {
                    screenUtil.createAlertBox("ERROR", "Input for RepID is too big");
                    valid = false;
                }
            } catch (NumberFormatException e) {
                screenUtil.createAlertBox("ERROR", "Invaid Input for RepID");
                valid = false;
            }
        }else{
            screenUtil.createAlertBox("ERROR", "Rep ID is empty");
            valid = false;
        }

        if(!PlantReg.getText().trim().isEmpty()) {
            try {
                permit_no = Integer.parseInt(PlantReg.getText());
                if (permit_no > max) {
                    screenUtil.createAlertBox("ERROR", "Input for PlantReg is too big");
                    valid = false;
                }
            } catch (NumberFormatException e) {
                screenUtil.createAlertBox("ERROR", "Invalid Input for PlantReg");
                valid = false;
            }
        }else{
            screenUtil.createAlertBox("ERROR", "Plant Reg is empty");
            valid = false;
        }

        if(!Formula.getText().trim().isEmpty()){
            formula = Formula.getText();
        }else{
            screenUtil.createAlertBox("ERROR", "Formula is empty");
        }

        if(!Appellation.getText().trim().isEmpty()){
            appellation = Appellation.getText();
      }else if(wineCheckBox.isSelected()){
            screenUtil.createAlertBox("ERROR", "Appellation is empty");
            valid = false;
        }

        if(!MailingAddress.getText().trim().isEmpty()){
            address = MailingAddress.getText();
        }else{
            screenUtil.createAlertBox("ERROR", "Mailling Address is empty");
            valid = false;
        }

        if(!grapeVarietal.getText().trim().isEmpty()){
            grape_varietal = grapeVarietal.getText();
        }else if(wineCheckBox.isSelected()){
            screenUtil.createAlertBox("ERROR", "Varietal is empty");
            valid = false;
        }

        if(!ApplicantName.getText().trim().isEmpty()){
            applicantName = ApplicantName.getText();
        }else{
            screenUtil.createAlertBox("ERROR", "Applicant Name is empty");
            valid = false;
        }

        if(!alcoholContent.getText().trim().isEmpty()){
            try {

                alcoholContentDouble = Double.parseDouble(alcoholContent.getText());
                if(alcoholContentDouble > 99999){
                    screenUtil.createAlertBox("ERROR", "Alcohol Content is above the allowed input limit");
                    valid = false;
                }
            } catch (NumberFormatException e) {
                screenUtil.createAlertBox("ERROR", "Invaid Input for Alcohol content");
                valid = false;
            }
        }else{
            screenUtil.createAlertBox("ERROR", "Alcohol Content is empty");
            valid = false;
        }

        if(!SerialNo.getText().trim().isEmpty()){
            serial = (SerialNo.getText());
        }else{
            screenUtil.createAlertBox("ERROR", "Serial No is empty");
            valid = false;
        }


        if (dom.isSelected()) {
            source_of_product = "DOMESTIC";
        } else if (imp.isSelected()) {
            source_of_product = "IMPORTED";
        }if(!(dom.isSelected()) && !(imp.isSelected())) {
            screenUtil.createAlertBox("ERROR", "Please select Domestic or Imported");
            valid = false;
        }


        if(!BrandName.getText().trim().isEmpty()) {
            brand_name = BrandName.getText();
        }else{
            screenUtil.createAlertBox("ERROR", "BrandName is empty");
            valid = false;
            brand_name = "";
        }
        if(!PhoneNumber.getText().trim().isEmpty()){
            phone_number = PhoneNumber.getText();
        }else{
            screenUtil.createAlertBox("ERROR", "PhoneNumber is empty");
            valid = false;
            phone_number = "";
        }
        if(!EmailAddress.getText().trim().isEmpty()){
            email = EmailAddress.getText();
        }else{
            screenUtil.createAlertBox("ERROR", "Email is empty");
            valid = false;
            email = "";
        }

        if(!Name.getText().trim().isEmpty()){
            fancyName = Name.getText();
        }else{
            screenUtil.createAlertBox("ERROR", "FancyName is empty");
            valid = false;
        }




        if(!Address.getText().trim().isEmpty()){
            address = Address.getText();
        }else{
            screenUtil.createAlertBox("ERROR", "Address is empty");
            address = "";
        }

        if(!Vintage.getText().trim().isEmpty()){
            try{
                vintage_date = Integer.parseInt(Vintage.getText());
                if(vintage_date > max || vintage_date < 0){
                    screenUtil.createAlertBox("ERROR", "Invalid Input for VintageDate");
                    valid = false;
                }
            }catch (NumberFormatException e){
                screenUtil.createAlertBox("ERROR", "Not a Number");
                valid = false;
            }
        }else if(wineCheckBox.isSelected()){
            screenUtil.createAlertBox("ERROR", "Vintage is empty");
            valid = false;
        }
        if(!pH.getText().trim().isEmpty()){
            try{
                ph_level = Double.parseDouble(pH.getText());
                if(ph_level > 14 || ph_level < 0){
                    screenUtil.createAlertBox("ERROR", "Invalid Input for PH ");
                    valid = false;
                }
            }catch (NumberFormatException e){
                screenUtil.createAlertBox("ERROR", "Not a Number");
                valid = false;
            }
        }else if(wineCheckBox.isSelected()){
            screenUtil.createAlertBox("ERROR", "PH is empty");
            valid = false;
        }

        if(!originField.getText().trim().isEmpty()) {
            try {
                originCode = Integer.parseInt(originField.getText());

            } catch (NumberFormatException e) {
                screenUtil.createAlertBox("ERROR", "Invaid Input for Origin Code");
                valid = false;
            }
        }else{
            screenUtil.createAlertBox("ERROR", "Origin Code is empty");
            valid = false;
        }

        if(!netContentField.getText().trim().isEmpty()) {
            try {
                netContentDouble = Double.parseDouble(netContentField.getText());
                if(netContentDouble > 99999){
                    screenUtil.createAlertBox("ERROR", "Net content is above the allowed input limit");
                    valid = false;
                }

            } catch (NumberFormatException e) {
                screenUtil.createAlertBox("ERROR", "Invalid Input for Net Content");
                valid = false;
            }
        }else{
            screenUtil.createAlertBox("ERROR", "Net content is empty");
            valid = false;
        }

        sulfiteDesc= sulfiteField.getText();
        bottlerInfo= bottlerField.getText();
        healthWarningText= additionalInfoField.getText();

        if(valid){
            if(screenUtil.createConfirmBox("Confirm", "Would you like to submit the form?")) {
                String newTTBID;
                int alcoholID;
                java.sql.Date currentDate = new java.sql.Date((new java.util.Date()).getTime());

                if (wineCheckBox.isSelected()) {
                    newTTBID = db.addForm(db.getNewTTBID(), repid, serial, address, permit_no, phone_number, email, applicantName, "UNASSIGNED", type1, type2, type3, currentDate);

                    // Add to alcohol
                    alcoholID = db.addAlcohol(fancyName, appellation, sulfiteDesc, alcoholContentDouble, netContentDouble, healthWarningText, 0, 0, "n/a", "n/a", formula, 2, bottlerInfo, brand_name, "PROCESSING",vintage_date, ph_level, grape_varietal, infoOnBottle, source_of_product, null, originCode);
                    saveImage(alcoholID);
                    //connect form and alcohol
                    db.updateAlcoholIDForForm(alcoholID, newTTBID);

                    System.out.println("It Works");
                    screenUtil.switchScene("NewApp.fxml", "New Application");


                } else if (beerCheckBox.isSelected()) {
                    newTTBID = db.addForm(db.getNewTTBID(), repid, serial, address, permit_no, phone_number, email, applicantName, "UNASSIGNED", type1, type2, type3, currentDate);

                    // Add to alcohol
                    alcoholID = db.addAlcohol(fancyName, appellation, sulfiteDesc, alcoholContentDouble, netContentDouble, healthWarningText, 0, 0, "n/a", "n/a", formula, 1, bottlerInfo, brand_name, "PROCESSING",vintage_date, ph_level, grape_varietal, infoOnBottle, source_of_product, null, originCode);
                    saveImage(alcoholID);
                    //connect form and alcohol
                    db.updateAlcoholIDForForm(alcoholID, newTTBID);

                    System.out.println("This works");
                    screenUtil.switchScene("NewApp.fxml", "New Application");

                } else if (distilledCheckBox.isSelected()) {
                    newTTBID = db.addForm(db.getNewTTBID(), repid, serial, address, permit_no, phone_number, email, applicantName, "UNASSIGNED", type1, type2, type3, currentDate);

                    // Add to alcohol
                    alcoholID = db.addAlcohol(fancyName, appellation, sulfiteDesc, alcoholContentDouble, netContentDouble, healthWarningText, 0, 0, "n/a", "n/a", formula, 3, bottlerInfo, brand_name, "PROCESSING",vintage_date, ph_level, grape_varietal, infoOnBottle, source_of_product, null, originCode);
                    saveImage(alcoholID);
                    //connect form and alcohol
                    db.updateAlcoholIDForForm(alcoholID, newTTBID);

                    System.out.println("This works too");
                    screenUtil.switchScene("NewApp.fxml", "New Application");

                }else{
                    screenUtil.createAlertBox("ERROR", "Please selecct the type of product");
                }
                db.roundRobin();
            }
        }

    }

    /**
     * Chooses an image file for the label.
     * @param event "Choose File" button pressed.
     */

    public void chooseFile(ActionEvent event)throws Exception{
        tempFile = screenUtil.openFileChooser();
            if (tempFile != null) {
                //myFilePath.setText(tempFile.getPath());
                filepath = tempFile.toURI().toString();
                System.out.println(filepath);
                javafx.scene.image.Image img = new Image(filepath);
                image.setImage(img);
            }

    }

    public void buttonClicked (){
            screenUtil.switchScene("NewHelp.fxml","Help");
    }

    public void exportPDF () {


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

    public void saveImage(int aid){
        BufferedImage image2 = null;
        BufferedImage image3 = null;
        try {
            String path = getPath();

            image3 = ImageIO.read(tempFile);
            ImageIO.write(image3, "jpg", new File(path + "/" + aid + ".jpg"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
