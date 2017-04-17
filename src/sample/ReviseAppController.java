package sample;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.event.ChangeListener;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Application Revision Screen controller.
 */
public class ReviseAppController {
    ScreenUtil su = new ScreenUtil();
    DatabaseUtil du = new DatabaseUtil();

    @FXML private TextField myFilePath1;
    @FXML private TextField form;
    @FXML private TextField ID1;
    @FXML private TextField RepID1;
    @FXML private TextField PlantReg1;
    @FXML private TextField SerialNo1;
    @FXML private TextField ApplicantName1;
    @FXML private TextField Varietal1;
    @FXML private TextField Appellation1;
    @FXML private TextField BrandName1;
    @FXML private TextField Name1;
    @FXML private TextField Formula1;
    @FXML private TextField PhoneNumber1;
    @FXML private TextField EmailAddress1;
    @FXML private CheckBox dom1111;
    @FXML private CheckBox imp1;
    @FXML private CheckBox wine1;
    @FXML private CheckBox beer1;
    @FXML private CheckBox other1;
    @FXML private TextField Vintage1;
    @FXML private TextField pH1;
    @FXML private TextField Address1;
    @FXML private TextField MailingAddress1;
    @FXML private TextField Content1;
    @FXML private Button Submit;
    @FXML private Button clearButton;
    @FXML private Button back;
    @FXML private Button find;
    @FXML private ChoiceBox formChoiceBox;
    private String revisionImagePath = "";
    int fid;

    private ArrayList<ApplicationData> formsFound = new ArrayList<>();
    private ObservableList<Integer> formsObservableList;
    private DataPasser dataPasser = new DataPasser();
    private DatabaseUtil databaseUtil =  new DatabaseUtil();
    private AccountsUtil accountsUtil = new AccountsUtil();
    private ScreenUtil screenUtil = new ScreenUtil();


    @FXML
    public void setFid(int fid)throws SQLException{
        this.fid = fid;
    }


    @FXML
    /**
     * Initializes the Application Revision Screen.
     */
    public void initialize()throws SQLException{
        formsObservableList = FXCollections.observableArrayList();
        formsFound = databaseUtil.searchFormWithAid(databaseUtil.getAccountAid(accountsUtil.getUsername()));

        for(int i = 0; i < formsFound.size(); i ++){
            formsObservableList.add(formsFound.get(i).getFormID());
        }

        /*formChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new javafx.beans.value.ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                autoFillSelectedForm(new ActionEvent(formChoiceBox, (Node) formChoiceBox));
            }
        });*/

        formChoiceBox.setItems(formsObservableList);
        System.out.println(dataPasser.isIsInvokebyReviseMenu());
        if(dataPasser.isIsInvokebyReviseMenu() == 1) {
            fid = dataPasser.getFormId();
            formChoiceBox.setValue(fid);
            formChoiceBox.setDisable(true);
            String type;
            String source;
            WineApplicationData wine;
            BeerApplicationData beer;
            type = databaseUtil.checkforType(fid);
            source = databaseUtil.checkforSource(fid);
            if (type.equals("WINE")) {
                if(source.equals("DOMESTIC")){
                    dom1111.setSelected(true);
                }
                if(source.equals("IMPORTED")){
                    imp1.setSelected(true);
                }
                wine = databaseUtil.fillSubmittedWineForm(fid);
                wine1.setSelected(true);
                ID1.setText(Integer.toString(wine.getTtbid()));
                RepID1.setText(Integer.toString(wine.getRepid()));
                PlantReg1.setText(Integer.toString(wine.getPermit_no()));
                SerialNo1.setText(wine.getSerial());
                ApplicantName1.setText(wine.getApplicantName());
                Varietal1.setText(wine.getGrape_varietal());
                Appellation1.setText(wine.getAppellation());
                BrandName1.setText(wine.getBrand_name());
                Name1.setText(wine.getFancyName());
                Formula1.setText(wine.getFormula());
                PhoneNumber1.setText(wine.getPhone_number());
                EmailAddress1.setText(wine.getEmail());
                Vintage1.setText(Integer.toString(wine.getVintage_date()));
                pH1.setText(Double.toString(wine.getPh_level()));
                Address1.setText(wine.getAddress());
                MailingAddress1.setText(wine.getAddress());

                if(dataPasser.getDisableVintageField() == 1){
                    Vintage1.setEditable(false);
                }
                if(dataPasser.getDisablepHField() == 1){
                    pH1.setEditable(false);
                }
                if(dataPasser.getDisableAppellationField() == 1){
                    Appellation1.setEditable(false);
                }
                if(dataPasser.getDisableVarietalField() == 1){
                    Varietal1.setEditable(false);
                }
                if(dataPasser.getDisableAlcoContentField() == 1){
                    Content1.setEditable(false);
                }
                if(dataPasser.getDisableRestField() == 1){
                    beer1.setDisable(true);
                    wine1.setDisable(true);
                    other1.setDisable(true);
                    ID1.setEditable(false);
                    RepID1.setEditable(false);
                    PlantReg1.setEditable(false);
                    SerialNo1.setEditable(false);
                    ApplicantName1.setEditable(false);
                    BrandName1.setEditable(false);
                    Name1.setEditable(false);
                    Formula1.setEditable(false);
                    PhoneNumber1.setEditable(false);
                    EmailAddress1.setEditable(false);
                    Address1.setEditable(false);
                    MailingAddress1.setEditable(false);
                    clearButton.setDisable(true);
                    dom1111.setDisable(true);
                    imp1.setDisable(true);
                }
            } else if (type.equals("BEER")) {
                if(source.equals("DOMESTIC")){
                    dom1111.setSelected(true);
                }
                if(source.equals("IMPORTED")){
                    imp1.setSelected(true);
                }
                beer = databaseUtil.fillSubmittedBeerForm(fid);
                beer1.setSelected(true);
                ID1.setText(Integer.toString(beer.getTtbid()));
                RepID1.setText(Integer.toString(beer.getRepid()));
                PlantReg1.setText(Integer.toString(beer.getPermit_no()));
                SerialNo1.setText(beer.getSerial());
                ApplicantName1.setText(beer.getApplicantName());
                BrandName1.setText(beer.getBrand_name());
                Name1.setText(beer.getFancyName());
                Formula1.setText(beer.getFormula());
                PhoneNumber1.setText(beer.getPhone_number());
                EmailAddress1.setText(beer.getEmail());
                Address1.setText(beer.getAddress());
                MailingAddress1.setText(beer.getAddress());
                if(dataPasser.getDisableAlcoContentField() == 1){
                    Content1.setEditable(false);
                }

                if(dataPasser.getDisableRestField() == 1){
                    beer1.setDisable(true);
                    wine1.setDisable(true);
                    other1.setDisable(true);
                    Vintage1.setEditable(false);
                    pH1.setEditable(false);
                    Appellation1.setEditable(false);
                    Varietal1.setEditable(false);
                    ID1.setEditable(false);
                    RepID1.setEditable(false);
                    PlantReg1.setEditable(false);
                    SerialNo1.setEditable(false);
                    ApplicantName1.setEditable(false);
                    BrandName1.setEditable(false);
                    Name1.setEditable(false);
                    Formula1.setEditable(false);
                    PhoneNumber1.setEditable(false);
                    EmailAddress1.setEditable(false);
                    Address1.setEditable(false);
                    MailingAddress1.setEditable(false);
                    clearButton.setDisable(true);
                    dom1111.setDisable(true);
                    imp1.setDisable(true);
                }

            } else {
                if(source.equals("DOMESTIC")){
                    dom1111.setSelected(true);
                }
                if(source.equals("IMPORTED")){
                    imp1.setSelected(true);
                }
                beer = databaseUtil.fillSubmittedBeerForm(fid);
                other1.setSelected(true);
                ID1.setText(Integer.toString(beer.getTtbid()));
                RepID1.setText(Integer.toString(beer.getRepid()));
                PlantReg1.setText(Integer.toString(beer.getPermit_no()));
                SerialNo1.setText(beer.getSerial());
                ApplicantName1.setText(beer.getApplicantName());
                BrandName1.setText(beer.getBrand_name());
                Name1.setText(beer.getFancyName());
                Formula1.setText(beer.getFormula());
                PhoneNumber1.setText(beer.getPhone_number());
                EmailAddress1.setText(beer.getEmail());
                Address1.setText(beer.getAddress());
                MailingAddress1.setText(beer.getAddress());


                if(dataPasser.getDisableAlcoContentField() == 1){
                    Content1.setEditable(false);
                }
                if(dataPasser.getDisableRestField() == 1){
                    beer1.setDisable(true);
                    wine1.setDisable(true);
                    other1.setDisable(true);
                    ID1.setEditable(false);
                    RepID1.setEditable(false);
                    PlantReg1.setEditable(false);
                    SerialNo1.setEditable(false);
                    ApplicantName1.setEditable(false);
                    BrandName1.setEditable(false);
                    Name1.setEditable(false);
                    Formula1.setEditable(false);
                    PhoneNumber1.setEditable(false);
                    EmailAddress1.setEditable(false);
                    Address1.setEditable(false);
                    MailingAddress1.setEditable(false);
                    Vintage1.setEditable(false);
                    pH1.setEditable(false);
                    Appellation1.setEditable(false);
                    Varietal1.setEditable(false);
                    clearButton.setDisable(true);
                    dom1111.setDisable(true);
                    imp1.setDisable(true);
                }
            }
        }
        dataPasser.setIsInvokebyReviseMenu(0);
        dataPasser.setDisableRestField(0);
        dataPasser.setDisableAppellationField(0);
        dataPasser.setDisableVintageField(0);
        dataPasser.setDisableVarietalField(0);
        dataPasser.setDisablepHField(0);
        dataPasser.setDisableAlcoContentField(0);
        System.out.println(dataPasser.isIsInvokebyReviseMenu());
    }

    @FXML
    private void handledomBox(){
        if(dom1111.isSelected()){
            imp1.setSelected(false);
        }
    }
    @FXML
    private void handleimpBox(){
        if(imp1.isSelected()){
            dom1111.setSelected(false);
        }
    }

    @FXML
    private void handlewineBox(){
        if(wine1.isSelected()){
            beer1.setSelected(false);
            other1.setSelected(false);

        }
    }

    @FXML
    private void handlebeerBox(){
        if(beer1.isSelected()){
            wine1.setSelected(false);
            other1.setSelected(false);
        }
    }

    @FXML
    private void handleotherBox(){
        if(other1.isSelected()){
            beer1.setSelected(false);
            wine1.setSelected(false);
        }
    }
    public void goBack (javafx.event.ActionEvent event){
        screenUtil.switchScene("MainMenu.fxml", "Main Menu");

    }
    @FXML
    /**
     * Autofills a selected form into the screen.
     *
     */
    public void autoFillSelectedForm(javafx.event.ActionEvent event)throws SQLException{

        int fid = Integer.valueOf(formChoiceBox.getValue().toString().trim());
        String type;
        WineApplicationData wine;
        BeerApplicationData beer;
        if(event.getSource() == find) {
            type = databaseUtil.checkforType(fid);
            if (type.equals("WINE")) {
                wine = databaseUtil.fillSubmittedWineForm(fid);
                ID1.setText(Integer.toString(wine.getTtbid()));
                RepID1.setText(Integer.toString(wine.getRepid()));
                PlantReg1.setText(Integer.toString(wine.getPermit_no()));
                SerialNo1.setText(wine.getSerial());
                ApplicantName1.setText(wine.getApplicantName());
                Varietal1.setText(wine.getGrape_varietal());
                Appellation1.setText(wine.getAppellation());
                BrandName1.setText(wine.getBrand_name());
                Name1.setText(wine.getFancyName());
                Formula1.setText(wine.getFormula());
                PhoneNumber1.setText(wine.getPhone_number());
                EmailAddress1.setText(wine.getEmail());
                Vintage1.setText(Integer.toString(wine.getVintage_date()));
                pH1.setText(Double.toString(wine.getPh_level()));
                Address1.setText(wine.getAddress());
                MailingAddress1.setText(wine.getAddress());
            } else if (type.equals("BEER")) {
                beer = databaseUtil.fillSubmittedBeerForm(fid);
                ID1.setText(Integer.toString(beer.getTtbid()));
                RepID1.setText(Integer.toString(beer.getRepid()));
                PlantReg1.setText(Integer.toString(beer.getPermit_no()));
                SerialNo1.setText(beer.getSerial());
                ApplicantName1.setText(beer.getApplicantName());
                BrandName1.setText(beer.getBrand_name());
                Name1.setText(beer.getFancyName());
                Formula1.setText(beer.getFormula());
                PhoneNumber1.setText(beer.getPhone_number());
                EmailAddress1.setText(beer.getEmail());
                Address1.setText(beer.getAddress());
                MailingAddress1.setText(beer.getAddress());
            } else {
                beer = databaseUtil.fillSubmittedBeerForm(fid);
                ID1.setText(Integer.toString(beer.getTtbid()));
                RepID1.setText(Integer.toString(beer.getRepid()));
                PlantReg1.setText(Integer.toString(beer.getPermit_no()));
                SerialNo1.setText(beer.getSerial());
                ApplicantName1.setText(beer.getApplicantName());
                BrandName1.setText(beer.getBrand_name());
                Name1.setText(beer.getFancyName());
                Formula1.setText(beer.getFormula());
                PhoneNumber1.setText(beer.getPhone_number());
                EmailAddress1.setText(beer.getEmail());
                Address1.setText(beer.getAddress());
                MailingAddress1.setText(beer.getAddress());
            }
        }
    }

    /**
     * Resubmits an application.
     * @throws SQLException
     */
    public void submitAgain()throws SQLException{
        int fid = Integer.valueOf(formChoiceBox.getValue().toString().trim());
        int ttbid = Integer.parseInt(ID1.getText());
        int repid = Integer.parseInt(RepID1.getText());
        String serial = SerialNo1.getText();
        String address = Address1.getText();
        String fancyName = Name1.getText();
        String formula = Formula1.getText();
        int permit_no = Integer.parseInt(PlantReg1.getText());
        String source_of_product = "";
        String type_of_product = "";
        String brand_name = BrandName1.getText();
        String phone_number = PhoneNumber1.getText();
        String email = EmailAddress1.getText();
        String applicantName = ApplicantName1.getText();
        String alcoholType = "";
        String date ="";
        AcceptanceInformation ac = null;
        String info = "";
        String content = "";

        if (dom1111.isSelected()) {
            source_of_product = "DOMESTIC";
        } else if (imp1.isSelected()) {
            source_of_product = "IMPORTED";
        }if(!(dom1111.isSelected()) && !(imp1.isSelected())) {
            su.createAlertBox("ERROR", "Please select Domestic or Imported");

        }

        if (wine1.isSelected()) {
            type_of_product = "WINE";
            alcoholType = "WINE";
            int vintage_date = Integer.parseInt(Vintage1.getText());
            double ph_level = Double.parseDouble(pH1.getText());
            String grape_varietal = Varietal1.getText();
            String appellation = Appellation1.getText();
            WineApplicationData a = new WineApplicationData(fid, ac, ttbid, repid, serial, address, fancyName, formula, grape_varietal, appellation, permit_no, info,
                    source_of_product, type_of_product, brand_name, phone_number, email, date, applicantName, alcoholType, content, vintage_date, ph_level);
            du.resubmitWine(fid, a);
        } else if (beer1.isSelected()) {
            type_of_product = "MALT BEVERAGES";
            alcoholType = "MALT BEVERAGES";
            BeerApplicationData a = new BeerApplicationData(fid, ac, ttbid, repid, serial, address, fancyName, formula, permit_no, info,
                    source_of_product, type_of_product, brand_name, phone_number, email, date, applicantName, alcoholType, content);
            du.resubmitBeer(fid, a);
        } else if (other1.isSelected()) {
            type_of_product = "DISTILLED SPIRITS";
            alcoholType = "DISTILLED SPIRITS";
            BeerApplicationData a = new BeerApplicationData(fid, ac, ttbid, repid, serial, address, fancyName, formula, permit_no, info,
                    source_of_product, type_of_product, brand_name, phone_number, email, date, applicantName, alcoholType, content);
            du.resubmitBeer(fid, a);
        }else{
            su.createAlertBox("ERROR", "Please select the type of product");
        }



    }

    /**
     * Uploads an image to the system.
     * @param Event Upload Image button is pressed.
     */
    public void uploadImage(ActionEvent Event){
        openFileChooser();
        myFilePath1.setText(revisionImagePath);
    }

    @FXML
    /**
     * Clears information from the screen.
     */
    private void setClear(){
        ScreenUtil work = new ScreenUtil();
        work.switchScene("ReviseApp.fxml", "Revise Application");
    }


    /**
     * Opens a file explorer to choose an image to upload.
     */
    public void openFileChooser(){
        Stage ReviseMenu = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose label picture");
        File selectedFile = fileChooser.showOpenDialog(ReviseMenu);
        revisionImagePath = selectedFile.getPath();
    }



}