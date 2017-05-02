package johnsUtil.Controllers;

import FormObserver.REST;
import FormObserver.Subject;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import johnsUtil.model.SharedResources.Account;
import johnsUtil.model.SharedResources.Database;
import johnsUtil.model.SharedResources.Screen;
import johnsUtil.model.Database.DatabaseUtil;
import sample.ScreenUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Date;

/**
 * Controller for new label screen.
 */
public class NewLabelController{

    //  ApplicationUtil appUtil = new ApplicationUtil();
    DatabaseUtil db = Database.getInstance();
    ScreenUtil screenUtil = Screen.getInstance();

    @FXML private ScrollPane scroll;
    @FXML private ImageView image;
    @FXML private JFXTextField RepID;
    @FXML private JFXTextField PlantReg;
    @FXML private JFXTextField SerialNo;
    @FXML private JFXTextField ApplicantName;
    @FXML private JFXTextField Name;
    @FXML private Button autoFill;
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
    private Subject subject;
    @FXML private Button Submit;
    @FXML private Button back;
    @FXML private Button clear;
    @FXML private Button helpNewButton;
    private String filepath;
    private File tempFile;

    private int getRandomInt(){
        return 4;
    }


    @FXML
    private void autoFill(ActionEvent e){
        RepID.setText(String.valueOf(getRandomInt()));
        PlantReg.setText(String.valueOf(getRandomInt()));
        SerialNo.setText(String.valueOf(getRandomInt()));
        ApplicantName.setText("Ari G");
        Name.setText("Funky Llama Malbec");
        PhoneNumber.setText("9089301270");
        MailingAddress.setText("Jacobs house");
        originField.setText("26");
        BrandName.setText("Mendoza Argentina");
        Address.setText("Brandons House");
        Formula.setText("Krabby Patties");
        EmailAddress.setText("jdasjdjkasj@dkajskldjaslk.com");
        alcoholContent.setText("13.0");
        netContentField.setText("750");
        grapeVarietal.setText("French");
        Appellation.setText("France");
        sulfiteField.setText("5");
        Vintage.setText("2004");
        bottlerField.setText("17th one from the left");
        additionalInfoField.setText("Jose Wong");
        pH.setText("7.0");
    }

    @FXML
    /**
     * Clears information from the screen.
     */
    private void setClear(){
        screenUtil.switchScene("NewLabel.fxml", "New Label");
    }

    /**
     * Initializes the new label screen.
     */
    public void initialize(){
        subject = new Subject();
        new REST(subject, SerialNo, MailingAddress, PlantReg, PhoneNumber, EmailAddress, ApplicantName, Address);
    }

    /**
     * Notifies observers of changes to the representative ID.
     */
    public void notifyObservers(){
        String rep = RepID.getText();
        subject.setText(rep);
    }
    public void goBack (ActionEvent event){
        screenUtil.switchScene("Home.fxml","Main Menu");
    }

    /**
     * Fills out an application in the database.
     * @throws SQLException
     */
    @FXML
    public void fillOutApplication(ActionEvent event) throws SQLException{
        boolean valid = true;
        int repid = 0;
        String serial = "";
        String address = "";
        String fancyName = "";
        String formula = "";
        String grape_varietal = "";
        String appellation = "";
        String permit_no = "";
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
        String permitAddress = "";

        int originCode = 0;
        String netContentDouble = "";
        String sulfiteDesc= "";
        String bottlerInfo= "";
        String healthWarningText= "";
        String errorMessage = "The Following Errors Were Found with Your Application:\n";


        if(!dom1.isSelected() && !dom11.isSelected() && !dom111.isSelected()){
            valid = false;
            errorMessage += "Application Box Unselected";
            //screenUtil.createAlertBox("ERROR", "Please choose applicable box(es)");
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
                    errorMessage += "Amount invalid \n";
                    //screenUtil.createAlertBox("ERROR", "Invalid Input for Amount");
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
                    errorMessage += "RepID invalid.\n";
                    //screenUtil.createAlertBox("ERROR", "Input for RepID is too big");
                    valid = false;
                }
            } catch (NumberFormatException e) {
                errorMessage += "RepID invalid.\n";
                //screenUtil.createAlertBox("ERROR", "Invaid Input for RepID");
                valid = false;
            }
        }else{
            errorMessage += "RepID is empty.\n";
            //screenUtil.createAlertBox("ERROR", "Rep ID is empty");
            valid = false;
        }

        if(!PlantReg.getText().trim().isEmpty()) {
            permit_no = PlantReg.getText();
        }else{
            errorMessage += "Plant Reg is empty.\n";
            //screenUtil.createAlertBox("ERROR", "Plant Reg is empty");
            valid = false;
        }

        if(!Formula.getText().trim().isEmpty()){
            formula = Formula.getText();
        }else{
            errorMessage += "Formula is empty.\n";
            //screenUtil.createAlertBox("ERROR", "Formula is empty");
        }

        if(!Appellation.getText().trim().isEmpty()){
            appellation = Appellation.getText();
        }else if(wineCheckBox.isSelected()){
            errorMessage += "Appellation is empty.\n";
            //screenUtil.createAlertBox("ERROR", "Appellation is empty");
            valid = false;
        }

        if(!MailingAddress.getText().trim().isEmpty()){
            address = MailingAddress.getText();
        }else{
            errorMessage += "Mailing Address is empty.\n";
            //screenUtil.createAlertBox("ERROR", "Mailing Address is empty");
            valid = false;
        }

        if(!grapeVarietal.getText().trim().isEmpty()){
            grape_varietal = grapeVarietal.getText();
        }else if(wineCheckBox.isSelected()){
            errorMessage += "Varietal is empty.\n";
            //screenUtil.createAlertBox("ERROR", "Varietal is empty");
            valid = false;
        }

        if(!ApplicantName.getText().trim().isEmpty()){
            applicantName = ApplicantName.getText();
        }else{
            errorMessage += "Applicant Name is empty.\n";
            //screenUtil.createAlertBox("ERROR", "Applicant Name is empty");
            valid = false;
        }

        if(!alcoholContent.getText().trim().isEmpty()){
            try {

                alcoholContentDouble = Double.parseDouble(alcoholContent.getText());
                if(alcoholContentDouble > 200){
                    errorMessage += "Alcohol content is above limit.\n";
                    //screenUtil.createAlertBox("ERROR", "Alcohol Content is above the allowed input limit");
                    valid = false;
                }
            } catch (NumberFormatException e) {
                errorMessage += "Alcohol content is invalid.\n";
                //screenUtil.createAlertBox("ERROR", "Invaid Input for Alcohol content");
                valid = false;
            }
        }else{
            errorMessage += "Alcohol content is empty.\n";
            //screenUtil.createAlertBox("ERROR", "Alcohol Content is empty");
            valid = false;
        }

        if(!SerialNo.getText().trim().isEmpty()){
            serial = (SerialNo.getText());
        }else{
            errorMessage += "Serial number is empty.\n";
            //screenUtil.createAlertBox("ERROR", "Serial No is empty");
            valid = false;
        }


        if (dom.isSelected()) {
            source_of_product = "DOMESTIC";
        } else if (imp.isSelected()) {
            source_of_product = "IMPORTED";
        }if(!(dom.isSelected()) && !(imp.isSelected())) {
            errorMessage += "Domestic/Imported Unselected.\n";
            //screenUtil.createAlertBox("ERROR", "Please select Domestic or Imported");
            valid = false;
        }


        if(!BrandName.getText().trim().isEmpty()) {
            brand_name = BrandName.getText();
        }else{
            errorMessage += "Brandname empty.\n";
            //screenUtil.createAlertBox("ERROR", "BrandName is empty");
            valid = false;
            brand_name = "";
        }
        if(!PhoneNumber.getText().trim().isEmpty()){
            phone_number = PhoneNumber.getText();
        }else{
            errorMessage += "Phone Number is empty.\n";
            //screenUtil.createAlertBox("ERROR", "PhoneNumber is empty");
            valid = false;
            phone_number = "";
        }
        if(!EmailAddress.getText().trim().isEmpty()){
            email = EmailAddress.getText();
        }else{
            errorMessage += "Email is empty.\n";
            //screenUtil.createAlertBox("ERROR", "Email is empty");
            valid = false;
            email = "";
        }

        if(!Name.getText().trim().isEmpty()){
            fancyName = Name.getText();
        }else{
            errorMessage += "Fancy Name is empty.\n";
            //screenUtil.createAlertBox("ERROR", "FancyName is empty");
            valid = false;
        }


        if(!Address.getText().trim().isEmpty()){
            address = Address.getText();
        }else{
            errorMessage += "Address is empty.\n";
            //screenUtil.createAlertBox("ERROR", "Address is empty");
            address = "";
        }

        if(!Vintage.getText().trim().isEmpty()){
            try{
                vintage_date = Integer.parseInt(Vintage.getText());
                if(vintage_date > max || vintage_date < 0){
                    errorMessage += "Invalid input for vintage date.\n";
                    //screenUtil.createAlertBox("ERROR", "Invalid Input for VintageDate");
                    valid = false;
                }
            }catch (NumberFormatException e){
                errorMessage += "Invalid input for vintage date.\n";
                //screenUtil.createAlertBox("ERROR", "Not a Number");
                valid = false;
            }
        }else if(wineCheckBox.isSelected()){
            errorMessage += "Vintage Date is empty.\n";
            //screenUtil.createAlertBox("ERROR", "Vintage is empty");
            valid = false;
        }
        if(!pH.getText().trim().isEmpty()){
            try{
                ph_level = Double.parseDouble(pH.getText());
                if(ph_level > 14 || ph_level < 0){
                    errorMessage += "Invalid input for pH.\n";
                    //screenUtil.createAlertBox("ERROR", "Invalid Input for PH ");
                    valid = false;
                }
            }catch (NumberFormatException e){
                errorMessage += "Invalid input for pH.\n";
                //screenUtil.createAlertBox("ERROR", "Not a Number");
                valid = false;
            }
        }else if(wineCheckBox.isSelected()){
            errorMessage += "pH is empty.\n";
            screenUtil.createAlertBox("ERROR", "PH is empty");
            valid = false;
        }

        if(!originField.getText().trim().isEmpty()) {
            try {
                originCode = Integer.parseInt(originField.getText());

            } catch (NumberFormatException e) {
                errorMessage += "Origin code invalid.\n";
                //screenUtil.createAlertBox("ERROR", "Invaid Input for Origin Code");
                valid = false;
            }
        }else{
            errorMessage += "Origin code invalid.\n";
            //screenUtil.createAlertBox("ERROR", "Origin Code is empty");
            valid = false;
        }

        if(!netContentField.getText().trim().isEmpty()) {
            try {
                netContentDouble = (netContentField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid input for net content.\n";
                //screenUtil.createAlertBox("ERROR", "Invalid Input for Net Content");
                valid = false;
            }
        }else{
            errorMessage += "Net Content is empty.\n";
            //screenUtil.createAlertBox("ERROR", "Net content is empty");
            valid = false;
        }

        sulfiteDesc= sulfiteField.getText();
        bottlerInfo= bottlerField.getText();
        healthWarningText= additionalInfoField.getText();
        permitAddress = Address.getText();


        if(valid){
            if(screenUtil.createConfirmBox("Confirm", "Would you like to submit the form?")) {
                String newTTBID;
                int alcoholID;
                java.sql.Date currentDate = new java.sql.Date((new Date()).getTime());
                Stage primaryStage = Account.getInstance().getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("johnsUtil/Views/Home.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (wineCheckBox.isSelected()) {
                    newTTBID = db.addForm(db.getNewTTBID(), repid, serial, address, permit_no, phone_number, email, applicantName, "UNASSIGNED", type1, type2, type3, permitAddress, currentDate);

                    // Add to alcohol
                    alcoholID = db.addAlcohol(fancyName, appellation, sulfiteDesc, alcoholContentDouble, netContentDouble, healthWarningText, 0, 0, "n/a", "n/a", formula, 2, bottlerInfo, brand_name, "PROCESSING",vintage_date, ph_level, grape_varietal, infoOnBottle, source_of_product, null, originCode);
                    saveImage(alcoholID);
                    //connect form and alcohol
                    db.updateAlcoholIDForForm(alcoholID, newTTBID);
                    primaryStage.setScene(new Scene(root));

                } else if (beerCheckBox.isSelected()) {
                    newTTBID = db.addForm(db.getNewTTBID(), repid, serial, address, permit_no, phone_number, email, applicantName, "UNASSIGNED", type1, type2, type3, permitAddress, currentDate);

                    // Add to alcohol
                    alcoholID = db.addAlcohol(fancyName, appellation, sulfiteDesc, alcoholContentDouble, netContentDouble, healthWarningText, 0, 0, "n/a", "n/a", formula, 1, bottlerInfo, brand_name, "PROCESSING",vintage_date, ph_level, grape_varietal, infoOnBottle, source_of_product, null, originCode);
                    saveImage(alcoholID);
                    //connect form and alcohol
                    db.updateAlcoholIDForForm(alcoholID, newTTBID);
                    primaryStage.setScene(new Scene(root));

                } else if (distilledCheckBox.isSelected()) {
                    newTTBID = db.addForm(db.getNewTTBID(), repid, serial, address, permit_no, phone_number, email, applicantName, "UNASSIGNED", type1, type2, type3, permitAddress, currentDate);

                    // Add to alcohol
                    alcoholID = db.addAlcohol(fancyName, appellation, sulfiteDesc, alcoholContentDouble, netContentDouble, healthWarningText, 0, 0, "n/a", "n/a", formula, 3, bottlerInfo, brand_name, "PROCESSING",vintage_date, ph_level, grape_varietal, infoOnBottle, source_of_product, null, originCode);
                    saveImage(alcoholID);
                    //connect form and alcohol
                    db.updateAlcoholIDForForm(alcoholID, newTTBID);
                    primaryStage.setScene(new Scene(root));

                }else{
                    errorMessage += "Product Unselected.\n";
                    //screenUtil.createAlertBox("ERROR", "Please select the type of product");
                }
                db.roundRobin();
            }
        }else{
            screenUtil.createAlertBox("ERROR", errorMessage);
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
            Image img = new Image(filepath);
            image.setImage(img);
        }

    }

    /**
     * Switches users to the New Label Help screen.
     */
    public void buttonClicked (){
        screenUtil.switchScene("NewHelp.fxml","Help");
    }

    /**
     * Exports a PDF of the form.
     */
    public void exportPDF () {


    }

    /**
     * Gets path for the label image.
     * @return Returns string representing path to the label image.
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

    /**
     * Saves alcohol label image to the system.
     * @param aid AID to associate with the label image.
     */
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
    /**
     * Switches user to the help screen for searches.
     */
    public void needHelp (){
        screenUtil.switchScene("NewHelp.fxml","Help");
    }

}
