package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.control.TextField;
import java.util.Random;
/**
 * Created by Sam Winter on 3/28/2017.
 */
public class NewLabelController{

    //  ApplicationUtil appUtil = new ApplicationUtil();
    DatabaseUtil databaseUtil = new DatabaseUtil();
    ScreenUtil su = new ScreenUtil();
    WorkflowFacade facadeWork = new WorkflowFacade();
    ScreenUtil work = new ScreenUtil();

    private FXMLLoader fxmlLoader;
    @FXML private TextField myFilePath;
    @FXML private TextField ID;
    @FXML private TextField RepID;
    @FXML private TextField PlantReg;
    @FXML private TextField SerialNo;
    @FXML private TextField ApplicantName;
    @FXML private TextField Varietal;
    @FXML private TextField Appellation;
    @FXML private TextField BrandName;
    @FXML private TextField Name;
    @FXML private TextField Formula;
    @FXML private TextField PhoneNumber;
    @FXML private TextField EmailAddress;
    @FXML private CheckBox dom;
    @FXML private CheckBox imp;
    @FXML private CheckBox wine;
    @FXML private CheckBox beer;
    @FXML private CheckBox other;
    @FXML private TextField Vintage;
    @FXML private TextField pH;
    @FXML private TextField Address;
    @FXML private TextField MailingAddress;
    @FXML private Button Submit;
    @FXML private Button back;
    @FXML private Button clear;
    Connection cn;
    Statement sm;
    DatabaseUtil db = new DatabaseUtil();

    @FXML
    private void setClear(){
        ScreenUtil work = new ScreenUtil();
        work.switchScene("NewLabel.fxml", "New Label");
    }

    @FXML
    private void handledomBox(){
        if(dom.isSelected()){
            imp.setSelected(false);
        }
    }
    @FXML
    private void handleimpBox(){
        if(imp.isSelected()){
            dom.setSelected(false);
        }
    }

    @FXML
    private void handlewineBox(){
        if(wine.isSelected()){
            beer.setSelected(false);
            other.setSelected(false);

        }
    }

    @FXML
    private void handlebeerBox(){
        if(beer.isSelected()){
            wine.setSelected(false);
            other.setSelected(false);
        }
    }

    @FXML
    private void handleotherBox(){
        if(other.isSelected()){
            beer.setSelected(false);
            wine.setSelected(false);
        }
    }
    public void buttonClicked (javafx.event.ActionEvent event){
        try {
            if(event.getSource() == back){
                fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
            Parent root1 = null;
            root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("MainMenu");
            stage.setScene(new Scene(root1));
            stage.show();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void fillOutApplication() throws SQLException{

        int fid = 1;
        int ttbid = 0;
        int repid = 0;
        String serial = "";
        String address;
        String fancyName = "";
        String formula = "";
        String grape_varietal = "";
        String appellation = "";
        int permit_no = 0;
        String infoOnBottle = "";
        String source_of_product = "";
        String type_of_product = "";
        String brand_name;
        String phone_number;
        String email;
        String date = "";
        String applicantName = "";
        String alcoholType = "";
        String alcoholContent = "";
        int vintage_date = 0;
        double ph_level = 0;
        int max = 999999999;


        if(!ID.getText().trim().isEmpty()) {
            try {
                ttbid = Integer.parseInt(ID.getText());
                if (ttbid > max) {
                    su.createAlertBox("ERROR", "Input for TTBID is too big");
                }
            } catch (NumberFormatException e) {
                su.createAlertBox("ERROR", "Invalid Input for TTBID");
            }
        }else{
            su.createAlertBox("ERROR", "TTBID is empty");
        }


        if(!RepID.getText().trim().isEmpty()) {
            try {
                repid = Integer.parseInt(RepID.getText());
                if (repid > max) {
                    su.createAlertBox("ERROR", "Input for RepID is too big");
                }
            } catch (NumberFormatException e) {
                su.createAlertBox("ERROR", "Invaid Input for RepID");
            }
        }else{
            su.createAlertBox("ERROR", "Rep ID is empty");
        }

        if(!PlantReg.getText().trim().isEmpty()) {
            try {
                permit_no = Integer.parseInt(PlantReg.getText());
                if (permit_no > max) {
                    su.createAlertBox("ERROR", "Input for PlantReg is too big");
                }
            } catch (NumberFormatException e) {
                su.createAlertBox("ERROR", "Invalid Input for PlantReg");
            }
        }else{
            su.createAlertBox("ERROR", "Plant Reg is empty");
        }

        if(!Formula.getText().trim().isEmpty()){
            formula = Formula.getText();
        }else{
            su.createAlertBox("ERROR", "Formula is empty");
        }

        if(!Appellation.getText().trim().isEmpty()){
            appellation = Appellation.getText();
      }else if(wine.isSelected()){
            su.createAlertBox("ERROR", "Appellation is empty");
        }

        if(!MailingAddress.getText().trim().isEmpty()){
            address = MailingAddress.getText();
        }else{
            su.createAlertBox("ERROR", "Mailling Address is empty");
        }

        if(!Varietal.getText().trim().isEmpty()){
            grape_varietal = Varietal.getText();
        }else if(wine.isSelected()){
            su.createAlertBox("ERROR", "Varietal is empty");
        }

        if(!ApplicantName.getText().trim().isEmpty()){
            applicantName = ApplicantName.getText();
        }else{
            su.createAlertBox("ERROR", "Applicant Name is empty");
        }

        if(!SerialNo.getText().trim().isEmpty()){
            serial = (SerialNo.getText());
        }else{
            su.createAlertBox("ERROR", "Serial No is empty");
        }


        if (dom.isSelected()) {
            source_of_product = "DOMESTIC";
        } else if (imp.isSelected()) {
            source_of_product = "IMPORTED";
        }if(!(dom.isSelected()) && !(imp.isSelected())) {
            su.createAlertBox("ERROR", "Please select Domestic or Imported");

        }

        if (wine.isSelected()) {
            type_of_product = "WINE";
            alcoholType = "WINE";
        } else if (beer.isSelected()) {
            type_of_product = "MALT BEVERAGES";
            alcoholType = "MALT BEVERAGES";
        } else if (other.isSelected()) {
            type_of_product = "DISTILLED SPIRITS";
            alcoholType = "DISTILLED SPIRITS";
        }else{
            su.createAlertBox("ERROR", "Please selecct the type of product");
        }

        if(!BrandName.getText().trim().isEmpty()) {
            brand_name = BrandName.getText();
        }else{
            su.createAlertBox("ERROR", "BrandName is empty");
            brand_name = "";
        }
        if(!PhoneNumber.getText().trim().isEmpty()){
            phone_number = PhoneNumber.getText();
        }else{
            su.createAlertBox("ERROR", "PhoneNumber is empty");
            phone_number = "";
        }
        if(!EmailAddress.getText().trim().isEmpty()){
            email = EmailAddress.getText();
        }else{
            su.createAlertBox("ERROR", "Email is empty");
            email = "";
        }

        if(!Name.getText().trim().isEmpty()){
            fancyName = Name.getText();
        }else{
            su.createAlertBox("ERROR", "FancyName is empty");
        }

        if(!Address.getText().trim().isEmpty()){
            address = Address.getText();
        }else{
            su.createAlertBox("ERROR", "Address is empty");
            address = "";
        }

        if(!Vintage.getText().trim().isEmpty()){
            try{
                vintage_date = Integer.parseInt(Vintage.getText());
                if(vintage_date > max || vintage_date < 0){
                    su.createAlertBox("ERROR", "Invalid Input for VintageDate");
                }
            }catch (NumberFormatException e){
                su.createAlertBox("ERROR", "Not a Number");
            }
        }else if(wine.isSelected()){
            su.createAlertBox("ERROR", "Vintage is empty");
        }
        if(!pH.getText().trim().isEmpty()){
            try{
                ph_level = Double.parseDouble(pH.getText());
                if(ph_level > max || ph_level > 12 || ph_level < 0){
                    su.createAlertBox("ERROR", "Invalid Input");
                }
            }catch (NumberFormatException e){
                su.createAlertBox("ERROR", "Not a Number");
            }
        }else if(wine.isSelected()){
            su.createAlertBox("ERROR", "PH is empty");
        }
        fid = 1;

        AcceptanceInformation acceptanceInfo = new AcceptanceInformation(date, applicantName,
                null, "UNASSIGNED");
        if (wine.isSelected()) {
            WineApplicationData Data = new WineApplicationData(fid, acceptanceInfo,ttbid, repid, serial,address,
                    fancyName, formula, grape_varietal, appellation, permit_no, infoOnBottle,
                    source_of_product, type_of_product, brand_name, phone_number, email, date, applicantName,
                    alcoholType, alcoholContent, vintage_date, ph_level);
            submitWine(Data);
            System.out.println("This somewhat works");

        } else if (beer.isSelected()) {
            BeerApplicationData Data = new BeerApplicationData(fid, acceptanceInfo,ttbid, repid, serial,address,
                    fancyName, formula, permit_no, infoOnBottle,
                    source_of_product, type_of_product, brand_name, phone_number, email, date, applicantName,
                    alcoholType, alcoholContent);
            submitBeer(Data);
            System.out.println("This works too");

        } else if (other.isSelected()) {
            BeerApplicationData Data = new BeerApplicationData(fid, acceptanceInfo,ttbid, repid, serial,address,
                    fancyName, formula, permit_no, infoOnBottle,
                    source_of_product, type_of_product, brand_name, phone_number, email, date, applicantName,
                    alcoholType, alcoholContent);
            submitBeer(Data);
            System.out.println("This works too");
        }

    }

    public void submitWine(WineApplicationData wd)throws SQLException{
        int fid = wd.getFormID();
        int ttbid = wd.getId();
        int repid = wd.getRepid();
        String serial = wd.getSerial();
        String address = wd.getAddress();
        String fancyName = wd.getFancyName();
        String formula = wd.getFormula();
        String grape_varietal = wd.getGrape_varietal();
        String appellation = wd.getAppellation();
        int permit_no = wd.getPermit_no();
        String infoOnBottle = wd.getInfoOnBottle();
        String source_of_product = wd.getSource_of_product();
        String type_of_product = wd.getType_of_product();
        String brand_name = wd.getBrand_name();
        String phone_number = wd.getPhone_number();
        String email = wd.getEmail();
        String date = wd.getDate();
        String dateFormat = date.toString();
        String applicantName = wd.getApplicantName();
        String alcoholType = wd.getAlcoholType();
        String alcoholContent = wd.getAlcoholContent();
        int vintage_date = wd.getVintage_date();
        double ph_level = wd.getPh_level();
        String status = wd.getAcceptanceInfo().getStatus();

        db.addWineForm(ttbid,repid, serial,address, fancyName, formula, grape_varietal, appellation, permit_no, infoOnBottle, source_of_product,
                type_of_product, brand_name, phone_number, email, dateFormat, applicantName,alcoholType,
                vintage_date, ph_level, alcoholContent, status);

    }

    public void chooseFile(ActionEvent event){
        File tempFile = work.openFileChooser();
        if (tempFile != null && tempFile.getPath() !=null) {
            myFilePath.setText(tempFile.getPath());
        }
    }

    public void submitBeer(BeerApplicationData bd) throws SQLException{
        int ttbid = bd.getId();
        int repid = bd.getRepid();
        String serial = bd.getSerial();
        String address = bd.getAddress();
        String fancyName = bd.getFancyName();
        String formula = bd.getFormula();
        int permit_no = bd.getPermit_no();
        String infoOnBottle = bd.getInfoOnBottle();
        String source_of_product = bd.getSource_of_product();
        String type_of_product = bd.getType_of_product();
        String brand_name = bd.getBrand_name();
        String phone_number = bd.getPhone_number();
        String email = bd.getEmail();
        String date = bd.getDate();
        String dateFormat = date.toString();
        String applicantName = bd.getApplicantName();
        String alcoholType = bd.getAlcoholType();
        String alcoholContent = bd.getAlcoholContent();
        String status = bd.getAcceptanceInfo().getStatus();

        db.addBeerForm(ttbid, repid, serial, address, fancyName, formula, permit_no, infoOnBottle, source_of_product, type_of_product, brand_name, phone_number, email,
                dateFormat, applicantName, alcoholType, alcoholContent, status);
    }


}
