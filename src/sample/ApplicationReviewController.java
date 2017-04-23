package sample;


import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sun.java2d.pipe.AlphaPaintPipe;

import java.lang.reflect.*;
import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Controller for Application Review Screen.
 */
public class ApplicationReviewController{
    @FXML
    private Button approve, reject, goBack, ReviewHelpButton;
    @FXML
    private TextField repID, registryNo, prodSource, prodType, address,  phoneNo, email, dateApp, nameApp;
    @FXML private  TextArea commentsField;

    private DatabaseUtil dbUtil = new DatabaseUtil();
    private ScreenUtil screenUtil = new ScreenUtil();
    private AccountsUtil accountsUtil = new AccountsUtil();
    private String username = accountsUtil.getUsername();
    private int numberOfApps;
    private ApplicationData thisForm;
    List<ApplicationData> listForms = new ArrayList<ApplicationData>();
    private static int appReviewMode = 1; // 1 = view all forms, 2 = choose form highlighted in inbox then return, 3 choose form highlighted in inbox then go to next available form;

    @FXML
    /**
     * Initializes the ApplicationReview Screen.
     */
    public void initialize() throws SQLException{
        listForms = dbUtil.searchFormWithGovId(dbUtil.getAccountAid(username));
        if(appReviewMode == 2){
            listForms = new ArrayList<ApplicationData>();
            listForms.add(WorkFlowController.getRowChosen());
        }
        numberOfApps = listForms.size();
        thisForm = listForms.get(0);
        repID.setText(Integer.toString(thisForm.getRepid()));
        registryNo.setText(Integer.toString(thisForm.getPermitNo()));
        prodSource.setText(thisForm.getAssociatedAlcoholData().getSourceOfProduct());
        prodType.setText(String.valueOf(thisForm.getAssociatedAlcoholData().getAlcoholType()));
        address.setText(thisForm.getAddress());
        phoneNo.setText(thisForm.getPhoneNumber());
        email.setText(thisForm.getEmail());
        dateApp.setText(thisForm.getSubmittedDate().toString());
        nameApp.setText(thisForm.getApplicantName());
}



    @FXML
    void setGoBack(ActionEvent event){
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
}
