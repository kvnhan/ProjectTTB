package sample;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.event.ChangeListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
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
    @FXML private CheckBox dom1111;
    @FXML private JFXRadioButton dom;
    @FXML private JFXRadioButton imp;
    @FXML private JFXRadioButton wineCheckBox;
    @FXML private JFXRadioButton beerCheckBox;
    @FXML private JFXRadioButton distilledCheckBox;
    @FXML private TextField type2Box;
    @FXML private TextField type3box;
    @FXML private TextField ttb;
    @FXML private Label Status;
    @FXML private Button Submit;
    @FXML private Button clearButton;
    @FXML private Button back;
    @FXML private Button find;
    @FXML private ChoiceBox formChoiceBox;
    private String revisionImagePath = "";
    private String ttbid;
    private String filepath;
    private File tempFile;
    private boolean changeImage = false;
    private ArrayList<ApplicationData> formsFound = new ArrayList<>();
    private ObservableList<String> formsObservableList;
    private DataPasser dataPasser = new DataPasser();
    private DatabaseUtil databaseUtil =  new DatabaseUtil();
    private AccountsUtil accountsUtil = new AccountsUtil();
    private ScreenUtil screenUtil = new ScreenUtil();



    @FXML
    /**
     * Initializes the Application Revision Screen.
     */
    public void initialize()throws SQLException{
        formsObservableList = FXCollections.observableArrayList();
        formsFound = databaseUtil.searchFormWithAid(databaseUtil.getAccountAid(accountsUtil.getUsername()));

        for(int i = 0; i < formsFound.size(); i ++){
            formsObservableList.add(formsFound.get(i).getTtbID());
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
            dataPasser.setIsRevised(1);
            find.setDisable(true);
            ttbid = dataPasser.getTtbID();
            formChoiceBox.setValue(ttbid);
            formChoiceBox.setDisable(true);
            int type1;
            int type2;
            int type3;
            String type;
            String source;
            ApplicationData data;
            AlcoholData a;
            dom1.setSelected(false);
            dom11.setSelected(false);
            dom111.setSelected(false);
            if(!type3box.getText().trim().isEmpty()){
                type3box.clear();
            }
            if(!type2Box.getText().trim().isEmpty()){
                type2Box.clear();
            }
            type = databaseUtil.checkforType(ttbid);
            type1 = databaseUtil.checkforType1(ttbid);
            type2 = databaseUtil.checkforType2(ttbid);
            type3 = databaseUtil.checkforType3(ttbid);
            source = databaseUtil.checkforSource(ttbid);

            if(dataPasser.getDisableRestField() == 1){
                type2Box.setDisable(true);
                type3box.setDisable(true);
                beerCheckBox.setDisable(true);
                wineCheckBox.setDisable(true);
                distilledCheckBox.setDisable(true);
                //ID1.setDisable(true);
                RepID.setDisable(true);
                PlantReg.setDisable(true);
                SerialNo.setDisable(true);
                ApplicantName.setDisable(true);
                BrandName.setDisable(true);
                Name.setDisable(true);
                Formula.setDisable(true);
                PhoneNumber.setDisable(true);
                EmailAddress.setDisable(true);
                Address.setDisable(true);
                MailingAddress.setDisable(true);
                //clearButton.setDisable(true);
                dom.setDisable(true);
                imp.setDisable(true);
                dom11.setDisable(true);
                dom111.setDisable(true);
                dom1.setDisable(true);
                dom1111.setDisable(true);
                ttb.setDisable(true);
            }
            if (type.equals("WINE")) {
                if(source.equals("DOMESTIC")){
                    dom.setSelected(true);
                }
                if(source.equals("IMPORTED")){
                    imp.setSelected(true);
                }
                data = databaseUtil.fillSubmittedForm(ttbid);
                a = databaseUtil.getAlcohoforWine(ttbid);
                setImage(a.getAid());

                if(type1 == 0){
                    dom1.setSelected(true);
                }
                if(type2 == 0){
                    dom11.setSelected(true);
                    type2Box.setText(data.getType2());
                }
                if(type3 == 0){
                    dom111.setSelected(true);
                    type3box.setText(Integer.toString(data.getType3()));
                }
                wineCheckBox.setSelected(true);
                //ID1.setText(data.getTtbID());
                RepID.setText(Integer.toString(data.getRepid()));
                PlantReg.setText(Integer.toString(data.getPermitNo()));
                SerialNo.setText(data.getSerial());
                ApplicantName.setText(data.getApplicantName());
                grapeVarietal.setText(a.getGrapeVarietal());
                Appellation.setText(a.getAppellation());
                BrandName.setText(a.getBrandName());
                Name.setText(a.getName());
                Formula.setText(a.getFormulas());
                PhoneNumber.setText(data.getPhoneNumber());
                EmailAddress.setText(data.getEmail());
                Vintage.setText(Integer.toString(a.getWineVintage()));
                pH.setText(Double.toString(a.getPhLevel()));
                Address.setText(data.getAddress());
                MailingAddress.setText(data.getAddress());
                alcoholContent.setText(Double.toString(a.getAlchContent()));
                netContentField.setText(Double.toString(a.getNetContent()));
                originField.setText(Integer.toString(a.getOriginCode()));
                bottlerField.setText(a.getBottlersInfo());
                sulfiteField.setText(a.getSulfiteDescription());

                if(dataPasser.getDisableVintageField() == 1){
                    Vintage.setDisable(true);
                }
                if(dataPasser.getDisablepHField() == 1){
                    pH.setDisable(true);
                }
                if(dataPasser.getDisableAppellationField() == 1){
                    Appellation.setDisable(true);
                }
                if(dataPasser.getDisableVarietalField() == 1){
                    grapeVarietal.setDisable(true);
                }
                if(dataPasser.getDisableAlcoContentField() == 1){
                    alcoholContent.setDisable(true);
                }


            } else if (type.equals("BEER")) {
                if(source.equals("DOMESTIC")){
                    dom.setSelected(true);
                }
                if(source.equals("IMPORTED")){
                    imp.setSelected(true);
                }

                data = databaseUtil.fillSubmittedForm(ttbid);
                a = databaseUtil.getAlcohoforBeer(ttbid);
                setImage(a.getAid());
                if(type1 == 0){
                    dom1.setSelected(true);
                }
                if(type2 == 0){
                    dom11.setSelected(true);
                    type2Box.setText(data.getType2());
                }
                if(type3 == 0){
                    dom111.setSelected(true);
                    type3box.setText(Integer.toString(data.getType3()));
                }
                if(dataPasser.getDisableRestField() == 1){
                    Vintage.setDisable(true);
                    pH.setDisable(true);
                    Appellation.setDisable(true);
                    grapeVarietal.setDisable(true);
                }
                beerCheckBox.setSelected(true);
                //ID1.setText(data.getTtbID());
                RepID.setText(Integer.toString(data.getRepid()));
                PlantReg.setText(Integer.toString(data.getPermitNo()));
                SerialNo.setText(data.getSerial());
                ApplicantName.setText(data.getApplicantName());
                //Varietal1.setText(wine.getGrape_varietal());
                //Appellation1.setText(wine.getAppellation());
                BrandName.setText(a.getBrandName());
                Name.setText(a.getName());
                Formula.setText(a.getFormulas());
                PhoneNumber.setText(data.getPhoneNumber());
                EmailAddress.setText(data.getEmail());
                //Vintage1.setText(Integer.toString(wine.getVintage_date()));
                //pH1.setText(Double.toString(wine.getPh_level()));
                Address.setText(data.getAddress());
                MailingAddress.setText(data.getAddress());
                alcoholContent.setText(Double.toString(a.getAlchContent()));
                netContentField.setText(Double.toString(a.getNetContent()));
                originField.setText(Integer.toString(a.getOriginCode()));

                if(dataPasser.getDisableAlcoContentField() == 1){
                    alcoholContent.setEditable(false);
                }
            } else {
                if(source.equals("DOMESTIC")){
                    dom.setSelected(true);
                }
                if(source.equals("IMPORTED")){
                    imp.setSelected(true);
                }
                data = databaseUtil.fillSubmittedForm(ttbid);
                a = databaseUtil.getAlcohoforBeer(ttbid);
                setImage(a.getAid());
                if(type1 == 0){
                    dom1.setSelected(true);
                }
                if(type2 == 0){
                    dom11.setSelected(true);
                    type2Box.setText(data.getType2());
                }
                if(type3 == 0){
                    dom111.setSelected(true);
                    type3box.setText(Integer.toString(data.getType3()));
                }
                if(dataPasser.getDisableRestField() == 1){
                    Vintage.setDisable(true);
                    pH.setDisable(true);
                    Appellation.setDisable(true);
                    grapeVarietal.setDisable(true);
                }

                distilledCheckBox.setSelected(true);
                //ID1.setText(data.getTtbID());
                RepID.setText(Integer.toString(data.getRepid()));
                PlantReg.setText(Integer.toString(data.getPermitNo()));
                SerialNo.setText(data.getSerial());
                ApplicantName.setText(data.getApplicantName());
                //Varietal1.setText(wine.getGrape_varietal());
                //Appellation1.setText(wine.getAppellation());
                BrandName.setText(a.getBrandName());
                Name.setText(a.getName());
                Formula.setText(a.getFormulas());
                PhoneNumber.setText(data.getPhoneNumber());
                EmailAddress.setText(data.getEmail());
                //Vintage1.setText(Integer.toString(wine.getVintage_date()));
                //pH1.setText(Double.toString(wine.getPh_level()));
                Address.setText(data.getAddress());
                MailingAddress.setText(data.getAddress());
                alcoholContent.setText(Double.toString(a.getAlchContent()));
                netContentField.setText(Double.toString(a.getNetContent()));
                originField.setText(Integer.toString(a.getOriginCode()));

                if(dataPasser.getDisableAlcoContentField() == 1){
                    alcoholContent.setDisable(true);
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


    public void goBack (javafx.event.ActionEvent event){
        if(dataPasser.isIsRevised() == 1){
            dataPasser.setIsRevised(0);
            screenUtil.switchScene("ReviseMenu.fxml", "Revise Menu");
        }else{
            screenUtil.switchScene("MainMenu.fxml", "Main Menu");
        }


    }
    @FXML
    /**
     * Autofills a selected form into the screen.
     *
     */
    public void autoFillSelectedForm(javafx.event.ActionEvent event)throws SQLException{

        String ttbid = formChoiceBox.getValue().toString().trim();
        ApplicationData data;
        AlcoholData a;
        String type;
        int type1;
        int type2;
        int type3;
        String source;
        dom1.setSelected(false);
        dom111.setSelected(false);
        dom111.setSelected(false);
        if(!type3box.getText().trim().isEmpty()){
            type3box.clear();
        }
        if(!type2Box.getText().trim().isEmpty()){
            type2Box.clear();
        }

        type = databaseUtil.checkforType(ttbid);
        type1 = databaseUtil.checkforType1(ttbid);
        type2 = databaseUtil.checkforType2(ttbid);
        type3 = databaseUtil.checkforType3(ttbid);
        source = databaseUtil.checkforSource(ttbid);
        if(source.equals("DOMESTIC")){
            dom.setSelected(true);
        }
        if(source.equals("IMPORTED")){
            imp.setSelected(true);
        }
        if(event.getSource() == find) {
            if (type.equals("WINE")) {
                data = databaseUtil.fillSubmittedForm(ttbid);
                a = databaseUtil.getAlcohoforWine(ttbid);
                System.out.println(dom1.isSelected());
                if(type1 == 0){
                    dom1.setSelected(true);
                }
                System.out.println(dom111.isSelected());
                if(type2 == 0){
                    dom11.setSelected(true);
                    type2Box.setText(data.getType2());

                }
                System.out.println(dom111.isSelected());
                if(type3 == 0){
                    dom111.setSelected(true);
                    type3box.setText(Integer.toString(data.getType3()));
                }
                wineCheckBox.setSelected(true);
                beerCheckBox.setSelected(false);
                distilledCheckBox.setSelected(false);
                Status.setText(data.getStatus());
                setImage(a.getAid());
                //ID1.setText(data.getTtbID());
                RepID.setText(Integer.toString(data.getRepid()));
                PlantReg.setText(Integer.toString(data.getPermitNo()));
                SerialNo.setText(data.getSerial());
                ApplicantName.setText(data.getApplicantName());
                grapeVarietal.setText(a.getGrapeVarietal());
                Appellation.setText(a.getAppellation());
                BrandName.setText(a.getBrandName());
                Name.setText(a.getName());
                Formula.setText(a.getFormulas());
                PhoneNumber.setText(data.getPhoneNumber());
                EmailAddress.setText(data.getEmail());
                Vintage.setText(Integer.toString(a.getWineVintage()));
                pH.setText(Double.toString(a.getPhLevel()));
                Address.setText(data.getAddress());
                MailingAddress.setText(data.getAddress());
                netContentField.setText(Double.toString(a.getNetContent()));
                originField.setText(Integer.toString(a.getOriginCode()));
                bottlerField.setText(a.getBottlersInfo());
                sulfiteField.setText(a.getSulfiteDescription());
                alcoholContent.setText(Double.toString(a.getAlchContent()));

            } else if (type.equals("BEER")) {
                data = databaseUtil.fillSubmittedForm(ttbid);
                a = databaseUtil.getAlcohoforBeer(ttbid);
                System.out.println(dom1.isSelected());
                if(type1 == 0){
                    dom1.setSelected(true);
                }
                System.out.println(dom111.isSelected());
                if(type2 == 0){
                    dom11.setSelected(true);
                    type2Box.setText(data.getType2());

                }
                System.out.println(dom111.isSelected());
                if(type3 == 0){
                    dom111.setSelected(true);
                    type3box.setText(Integer.toString(data.getType3()));
                }
                Status.setText(data.getStatus());
                beerCheckBox.setSelected(true);
                wineCheckBox.setSelected(false);
                distilledCheckBox.setSelected(false);
                setImage(a.getAid());
                //ID1.setText(data.getTtbID());
                RepID.setText(Integer.toString(data.getRepid()));
                PlantReg.setText(Integer.toString(data.getPermitNo()));
                SerialNo.setText(data.getSerial());
                ApplicantName.setText(data.getApplicantName());
                BrandName.setText(a.getBrandName());
                Name.setText(a.getName());
                Formula.setText(a.getFormulas());
                PhoneNumber.setText(data.getPhoneNumber());
                EmailAddress.setText(data.getEmail());
                Address.setText(data.getAddress());
                MailingAddress.setText(data.getAddress());
                netContentField.setText(Double.toString(a.getNetContent()));
                originField.setText(Integer.toString(a.getOriginCode()));
                alcoholContent.setText(Double.toString(a.getAlchContent()));

            } else {
                data = databaseUtil.fillSubmittedForm(ttbid);
                Status.setText(data.getStatus());
                a = databaseUtil.getAlcohoforBeer(ttbid);
                System.out.println(dom1.isSelected());
                if(type1 == 0){
                    dom1.setSelected(true);
                }
                System.out.println(dom111.isSelected());
                if(type2 == 0){
                    dom111.setSelected(true);
                    type2Box.setText(data.getType2());

                }
                System.out.println(dom111.isSelected());
                if(type3 == 0){
                    dom111.setSelected(true);
                    type3box.setText(Integer.toString(data.getType3()));
                }
                distilledCheckBox.setSelected(true);
                beerCheckBox.setSelected(false);
                wineCheckBox.setSelected(false);
                setImage(a.getAid());
                //ID.setText(data.getTtbID());
                RepID.setText(Integer.toString(data.getRepid()));
                PlantReg.setText(Integer.toString(data.getPermitNo()));
                SerialNo.setText(data.getSerial());
                ApplicantName.setText(data.getApplicantName());
                BrandName.setText(a.getBrandName());
                Name.setText(a.getName());
                Formula.setText(a.getFormulas());
                PhoneNumber.setText(data.getPhoneNumber());
                EmailAddress.setText(data.getEmail());
                Address.setText(data.getAddress());
                MailingAddress.setText(data.getAddress());
                netContentField.setText(Double.toString(a.getNetContent()));
                originField.setText(Integer.toString(a.getOriginCode()));
                alcoholContent.setText(Double.toString(a.getAlchContent()));

            }
        }
    }

    /**
     * Resubmits an application.
     * @throws SQLException
     */
    public void submitAgain()throws SQLException{
        String ttbid = formChoiceBox.getValue().toString().trim();
        int repid = Integer.parseInt(RepID.getText());
        String serial = SerialNo.getText();
        String address = Address.getText();
        String fancyName = Name.getText();
        String formula = Formula.getText();
        int permit_no = Integer.parseInt(PlantReg.getText());
        String source_of_product = "";
        String type_of_product = "";
        String brand_name = BrandName.getText();
        String phone_number = PhoneNumber.getText();
        String email = EmailAddress.getText();
        String applicantName = ApplicantName.getText();
        String alcoholType = "";
        String date ="";
        String info = "";
        double content = Double.parseDouble(alcoholContent.getText());
        double netcontent = Double.parseDouble(netContentField.getText());
        int origin = Integer.parseInt(originField.getText());

        int type1 = 0;
        String type2 = "";
        int type3 = -1;


        if (dom.isSelected()) {
            source_of_product = "DOMESTIC";
        } else if (imp.isSelected()) {
            source_of_product = "IMPORTED";
        }if(!(dom.isSelected()) && !(imp.isSelected())) {
            su.createAlertBox("ERROR", "Please select Domestic or Imported");

        }
        if(dom1.isSelected()){
            type1 = 1;
        }
        if(dom11.isSelected()){
            type2  = type2Box.getText();
        }
        if(dom111.isSelected()){
            type3 = Integer.parseInt(type3box.getText());
        }


        if (wineCheckBox.isSelected()) {
            type_of_product = "WINE";
            alcoholType = "WINE";
            int vintage_date = Integer.parseInt(Vintage.getText());
            double ph_level = Double.parseDouble(pH.getText());
            String grape_varietal = grapeVarietal.getText();
            String appellation = Appellation.getText();
            String bottler = bottlerField.getText();
            String sulfite = sulfiteField.getText();
            ApplicationData a = new ApplicationData(ttbid,repid,serial,address,permit_no,phone_number,email,applicantName,type1,type2,type3, null);
            AlcoholData ac = new AlcoholData(0, fancyName, appellation, sulfite, content, netcontent, "n/a", 0, 0, "n/a","n/a",
                    formula, 2, bottler, brand_name, "", vintage_date, ph_level, grape_varietal, "", source_of_product, null, origin);
            int aid = du.updateFormSubmission(a, ac);
            if(changeImage){
                saveImage(aid);
            }
            su.switchScene("NewApp.fxml", "Application Menu");
            su.createAlertBox("Notification", "Update is successful");

        } else if (beerCheckBox.isSelected()) {
            type_of_product = "MALT BEVERAGES";
            alcoholType = "MALT BEVERAGES";
            ApplicationData a = new ApplicationData(ttbid,repid,serial,address,permit_no,phone_number,email,applicantName,type1,type2,type3, null);
            AlcoholData ac = new AlcoholData(0, fancyName, "n/a", "", content, netcontent, "n/a", 0, 0, "n/a","n/a",
                    formula, 1, "n/a", brand_name, "n/a", 0, 0, "n/a", "n/a", source_of_product, null, origin);
            int aid = du.updateFormSubmission(a, ac);
            if(changeImage){
                saveImage(aid);
            }
            su.switchScene("NewApp.fxml", "Application Menu");
            su.createAlertBox("Notification", "Update is successful");
        } else if (distilledCheckBox.isSelected()) {
            type_of_product = "DISTILLED SPIRITS";
            alcoholType = "DISTILLED SPIRITS";
            ApplicationData a = new ApplicationData(ttbid,repid,serial,address,permit_no,phone_number,email,applicantName,type1,type2,type3, null);
            AlcoholData ac = new AlcoholData(0, fancyName, "n/a", "", content, netcontent, "n/a", 0, 0, "n/a","n/a",
                    formula, 3, "n/a", brand_name, "n/a", 0, 0, "n/a", "n/a", source_of_product, null, origin);
            int aid = du.updateFormSubmission(a, ac);
            if(changeImage){
                saveImage(aid);
            }
            su.switchScene("NewApp.fxml", "Application Menu");
            su.createAlertBox("Notification", "Update is successful");
        }else{
            su.createAlertBox("ERROR", "Please select the type of product");
        }

        databaseUtil.roundRobin();

    }

    /**
     * Uploads an image to the system.
     * @param Event Upload Image button is pressed.
     */
    public void uploadImage(ActionEvent Event){
        tempFile = screenUtil.openFileChooser();
        if (tempFile != null) {
            //myFilePath.setText(tempFile.getPath());
            filepath = tempFile.toURI().toString();
            System.out.println(filepath);
            javafx.scene.image.Image img = new javafx.scene.image.Image(filepath);
            image.setImage(img);
        }
        changeImage = true;
    }

    @FXML
    /**
     * Clears information from the screen.
     *//*
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

    public void setImage(int aid){
        try {
            String path = getPath();
            File file = new File(path + "/" + aid + ".jpg");
            Image image1 = new Image(file.toURI().toString());
            image.setImage(image1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    /**
     * Clears information from the screen.
     */
    private void setClear(){
        ScreenUtil work = new ScreenUtil();
        work.switchScene("ReviseApp.fxml", "Revise Application");
    }

    public void needHelp (){
        screenUtil.switchScene("ReviseHelp.fxml","Help");
    }

}