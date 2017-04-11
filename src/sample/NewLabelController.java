package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.awt.*;
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

    WorkflowFacade facadeWork = new WorkflowFacade();
    private FXMLLoader fxmlLoader;
    @FXML private TextField ID;
    @FXML private TextField RepID;
    @FXML private TextField PlantReg;
    @FXML private TextField SerialNo;
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
    @FXML private Button Submit;
    @FXML private Button back;

    Connection cn;
    Statement sm;
    DatabaseUtil db = new DatabaseUtil();

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

        int fid;
        int ttbid;
        int repid;
        String serial;
        String address;
        String fancyName = "";
        String formula = "";
        String grape_varietal = "";
        String appellation = "";
        int permit_no;
        String infoOnBottle = "";
        String source_of_product = "";
        String type_of_product = "";
        String brand_name;
        String phone_number;
        String email;
        String date = "";
        String applicantName;
        String alcoholType = "";
        String alcoholContent = "";
        int vintage_date;
        double ph_level;

        ttbid = Integer.parseInt(ID.getText());
        repid = Integer.parseInt(RepID.getText());
        permit_no = Integer.parseInt(PlantReg.getText());
        formula = Formula.getText();
        serial = (SerialNo.getText());


        if (dom.isSelected()) {
            source_of_product = "DOMESTIC";
        } else if (imp.isSelected()) {
            source_of_product = "IMPORTED";
        }if(!(dom.isSelected()) && !(imp.isSelected())) {
            source_of_product = "";

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
        }

        brand_name = BrandName.getText();
        phone_number = PhoneNumber.getText();
        email = EmailAddress.getText();
        applicantName = Name.getText();
        address = Address.getText();
        vintage_date = Integer.parseInt(Vintage.getText());
        ph_level = Double.parseDouble(pH.getText());
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

        //roundRobin();
    }
    //goes adds new applications to worker's inboxes
    public void roundRobin() throws  SQLException{
        ArrayList<ApplicationData> unAssignedForms = databaseUtil.searchUnassigForms();
        int runThroughs = (int)(unAssignedForms.size())/10;
        for(int i = 0; i <= runThroughs; i++) {;
            for (int j = 0; j <= 10; j++) {
                Account worker = databaseUtil.searchMinWorkLoad();
                databaseUtil.assignForm(worker, unAssignedForms.get(j));
            }
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
        facadeWork.addToInbox(ttbid);

    }


}