package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for Application Review Screen.
 */
public class ApplicationReviewController{
    @FXML
    Button approve, reject, goBack, forwardApp;
    @FXML
     Label RepID_Label, PlantID_Label, SerialNo_Label, PlantAddress_Label,
            ApplicantName_Label, MaillingAddress_Label, Email_Label, PhoneNumber_Label,
            BrandName_Label, FancyName_Label, Formula_Label, AlcoholContent_Label, NetContent_Label,
            Origin_Label, Sulfite_Label, Varietal_Label, Appellation_Label, Vintage_Label, Bottler_Label,
            pH_Label;
    @FXML
    TextArea commentsField;
    @FXML
    ChoiceBox acctToChoose;
    @FXML
    ImageView LabelImage;

    private DatabaseUtil dbUtil = new DatabaseUtil();
    private ScreenUtil screenUtil = new ScreenUtil();
    private AccountsUtil accountsUtil = new AccountsUtil();
    private String username = accountsUtil.getUsername();
    private int numberOfApps;
    private ApplicationData thisForm;
    List<ApplicationData> listForms = new ArrayList<ApplicationData>();
    private static int appReviewMode = 1; // 1 = view all forms, 2 = choose form highlighted in inbox then return, 3 choose form highlighted in inbox then go to next available form;

    private ArrayList<Account> acctsFound = new ArrayList<>();
    private ObservableList<String> acctsObservableList;

    @FXML
    /**
     * Initializes the ApplicationReview Screen.
     */
    public void initialize() throws SQLException{
        boolean foundImage = false;
        listForms = dbUtil.searchFormWithGovId(dbUtil.getAccountAid(username));
        if(appReviewMode == 2){
            listForms = new ArrayList<>();
            listForms.add(WorkFlowController.getRowChosen());
        }
        thisForm = listForms.get(0);

        RepID_Label.setText(String.valueOf(thisForm.getRepid()));
        PlantID_Label.setText(String.valueOf(thisForm.getPermitNo()));
        SerialNo_Label.setText(String.valueOf(thisForm.getSerial()));
        PlantAddress_Label.setText(String.valueOf(thisForm.getAddress()));

        ApplicantName_Label.setText(String.valueOf(thisForm.getApplicantName()));
        MaillingAddress_Label.setText(String.valueOf(thisForm.getAddress()));
        Email_Label.setText(String.valueOf(thisForm.getEmail()));
        PhoneNumber_Label.setText(String.valueOf(thisForm.getPhoneNumber()));

        BrandName_Label.setText(String.valueOf(thisForm.getAssociatedAlcoholData().getBrandName()));
        FancyName_Label.setText(String.valueOf(thisForm.getAssociatedAlcoholData().getName()));
        Formula_Label.setText(String.valueOf(thisForm.getAssociatedAlcoholData().getFormulas()));
        AlcoholContent_Label.setText(String.valueOf(thisForm.getAssociatedAlcoholData().getAlchContent()));
        NetContent_Label.setText(String.valueOf(thisForm.getAssociatedAlcoholData().getNetContent()));

        Origin_Label.setText(String.valueOf(thisForm.getAssociatedAlcoholData().getOriginCode()));
        Sulfite_Label.setText(String.valueOf(thisForm.getAssociatedAlcoholData().getSulfiteDescription()));
        Varietal_Label.setText(String.valueOf(thisForm.getAssociatedAlcoholData().getGrapeVarietal()));
        Appellation_Label.setText(String.valueOf(thisForm.getAssociatedAlcoholData().getAppellation()));
        Vintage_Label.setText(String.valueOf(thisForm.getAssociatedAlcoholData().getWineVintage()));
        Bottler_Label.setText(String.valueOf(thisForm.getAssociatedAlcoholData().getBottlersInfo()));

        pH_Label.setText(String.valueOf(thisForm.getAssociatedAlcoholData().getPhLevel()));

        try {
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/"  + thisForm.getAssociatedAlcoholData().getAid() + ".jfif");
            LabelImage.setImage(new javafx.scene.image.Image(resource, 500.0, 0.0, true, true));
            foundImage = true;
        }
        catch(NullPointerException nullPoint){
            if(foundImage == false) {
                try {
                    String path = getPath();
                    File file = new File(path + "/" + thisForm.getAssociatedAlcoholData().getAid() + ".jpg");
                    javafx.scene.image.Image image = new javafx.scene.image.Image(file.toURI().toString());
                    System.out.println(thisForm.getAssociatedAlcoholData().getAid());
                    LabelImage.setImage(image);
                } catch (Exception e) {
                    InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/imageUnavailable.jpg");
                    LabelImage.setImage(new javafx.scene.image.Image(resource, 100.0, 0.0, true, true));
                    System.out.println("Image Was Not Found For " + thisForm.getAssociatedAlcoholData().getBrandName() + "'s " + thisForm.getAssociatedAlcoholData().getName());
                }
            }
        }
        acctsObservableList = FXCollections.observableArrayList();
        acctsFound = dbUtil.searchAccountWithUserType(1);
        for(int i = 0; i < acctsFound.size(); i++){
            acctsObservableList.add(acctsFound.get(i).getUsername());
        }
        //acctToChoose.setItems(acctsObservableList);

    }



    @FXML
    void setGoBack(){
        screenUtil.switchScene("WorkFlow.fxml", "Main Menu");
    }

    @FXML
    /**
     * Sets an Application status to "ACCEPTED" and adds comments to the Application.
     */
    void setAccepted(ActionEvent event) throws SQLException{
        dbUtil.decideApplicationAction("ACCEPTED", thisForm, commentsField);
        nextApplication();
    }


    @FXML
    void setIncomplete(ActionEvent event) throws SQLException{
        dbUtil.decideApplicationAction("INCOMPLETE", thisForm, commentsField);
        nextApplication();
    }

    @FXML
    /**
     * Sets an Application status to "REJECTED" and adds comments to the Application.
     */

    public void setReject(ActionEvent event) throws SQLException {
        dbUtil.decideApplicationAction("REJECTED", thisForm, commentsField);
        nextApplication();
    }

    @FXML
    public void forwardApp(ActionEvent event) throws SQLException{
        String govUsernm = acctToChoose.getValue().toString().trim();
        int newGovID = dbUtil.getAccountAid(govUsernm);
        dbUtil.assignForm(newGovID, thisForm);
        nextApplication();
    }

    /**
     * Brings a worker to the next application in their inbox.
     */
    public void nextApplication(){

        if(appReviewMode == 1){
            if(numberOfApps <= 1){
                screenUtil.switchScene("WorkFlow.fxml", "Inbox");
                screenUtil.createAlertBox("No more assigned forms", "There are no more forms assigned to you.");
            }else{
                screenUtil.switchScene("ApplicationReview.fxml","Application Review");
            }
        }else if(appReviewMode == 2){
            screenUtil.switchScene("WorkFlow.fxml", "Inbox");
        }
    }


    public void helpClick () {
            screenUtil.switchScene("ReviewHelp.fxml","Help");
    }


    public static int getAppReviewMode() {
        return appReviewMode;
    }

    public static void setAppReviewMode(int appReviewMode) {
        ApplicationReviewController.appReviewMode = appReviewMode;
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
