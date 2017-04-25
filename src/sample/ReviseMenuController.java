package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;


/**
* Controller for the Revisions Menu screen.
 *  */
//Change to a relevant path. Specify the name of the subfolder in the path and that will help to connect the db.
//Go back to the notes from the JDBC slides. One uses a prepared statemnt and one does not
//Figure out how the Data is set up.
//Find the attributes of the object to contain the information.
//Create a program that does a search on the table and maps that to the resulting java object
//Have it be separate for each class.
//Quick Exercise: pick a small table, write the code that querys the table and query for one or several records and figure out how I would stick those records into a java class
//After that, modify it to return the java object.
//Also, might want to create code that updates the information in the database.


public class ReviseMenuController {

    private @FXML CheckBox rev1En, rev2En, rev3En, rev4En, rev5En, rev6En, rev7En,rev8En, rev9En, rev10En, rev11En, rev12En, rev13En, rev14En, rev15En, rev16En, rev17En, rev18En, rev19En,rev20En, rev21En;

    private @FXML TextArea rev1Data, rev2Data, rev3Data, rev4Data, rev5Data, rev6Data, rev7Data, rev8Data, rev9Data,rev10Data, rev11Data, rev12Data;

    @FXML private Button back;
    @FXML private Button submit;
    @FXML private ChoiceBox applicationChoiceBox;
    @FXML private Button reviseHelpButton;

    private String revisionData = "";
    private DataPasser dataPasser = new DataPasser();
    private DatabaseUtil databaseUtil = new DatabaseUtil();
    private AccountsUtil accountsUtil = new AccountsUtil();
    private ScreenUtil screenUtil = new ScreenUtil();
    private String revisionImagePath = "";


    private ArrayList<ApplicationData> formsFound = new ArrayList<>();
    private ObservableList<String> formsObservableList;

    @FXML
    /**
     * Initializes the screen.
     */
    public void initialize() throws SQLException{
        formsObservableList = FXCollections.observableArrayList();
        formsFound = databaseUtil.searchFormWithAid(databaseUtil.getAccountAid(accountsUtil.getUsername()));

        for(int i = 0; i < formsFound.size(); i ++){
            formsObservableList.add(formsFound.get(i).getTtbID());
        }

        applicationChoiceBox.setItems(formsObservableList);
        applicationChoiceBox.getSelectionModel().selectFirst();
    }


    /**
     * Sends user back to the main menu.
     *
     * @param event Represents a press of the back button.
     */
    public void goBack(ActionEvent event){
        screenUtil.switchScene("MainMenu.fxml", "Main Menu");

    }

/*
    /**
     * Uploads an image to the system.
     * @param Event Upload Image button is pressed.
     */
   /* public void uploadImage(ActionEvent Event){
        openFileChooser();
        UploadImageLabel.setText(revisionImagePath);
    }
*/
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

    /**
     * Function that runs when the Submit button is clicked.
     * Enters revisions to the database.
     */

    public void submitButtonClicked() throws IOException, SQLException{
        String ttbid = applicationChoiceBox.getValue().toString().trim();
        dataPasser.setTtbID(ttbid);
        dataPasser.setIsInvokebyReviseMenu(1);
        if(!rev1En.isSelected()){
            dataPasser.setDisableVintageField(1);
        }
        if(!rev2En.isSelected()){
            dataPasser.setDisablepHField(1);
        }
        if(!rev3En.isSelected()){
            dataPasser.setDisableAlcoContentField(1);
        }
        if(!rev4En.isSelected()){
            dataPasser.setDisableAlcoContentField(1);
        }
        if(!rev8En.isSelected()){
            dataPasser.setDisableAppellationField(1);
            dataPasser.setDisableVarietalField(1);
        }
        dataPasser.setDisableRestField(1);
        /*
        if(!rev5En.isSelected()){
            dataPasser.setDisableImageField(1);
            dataPasser.setDisableImageField(1);
        }
        if(!rev6En.isSelected()){

        }
        if(!rev7En.isSelected()){

        }
        if(!rev9En.isSelected()){

        }
        if(!rev10En.isSelected()){

        }
        if(!rev11En.isSelected()){

        }
        if(!rev12En.isSelected()){

        }
        */
        screenUtil.switchScene("ReviseApp.fxml", "Revision Updates");
        System.out.println("TTBID: " + ttbid);

        /*
        if (rev1En.isSelected()) {
            revisionData = rev1Data.getText() + "\n\r";
        } else {
            revisionData = "\n\r";
        }
        if (rev2En.isSelected()) {
            revisionData = revisionData + rev2Data.getText() + "\n\r";
        } else {
            revisionData = revisionData + "\n\r";
        }
        if (rev3En.isSelected()) {
            revisionData = revisionData + rev3Data.getText()+ "\n\r";
        } else {
            revisionData = revisionData + "\n\r";
        }
        if (rev4En.isSelected()) {
            revisionData = revisionData + rev4Data.getText()+ "\n\r";
        } else {
            revisionData = revisionData + "\n\r";
        }
        if (rev5En.isSelected()) {
            revisionData = revisionData + rev5Data.getText()+ "\n\r";
        } else {
            revisionData = revisionData + "\n\r";
        }
        if (rev6En.isSelected()) {
            revisionData = revisionData + rev6Data.getText()+ "\n\r";
        } else {
            revisionData = revisionData + "\n\r";
        }
        if (rev7En.isSelected()) {
            revisionData = revisionData + rev7Data.getText() + "\n\r";
        } else {
            revisionData = revisionData + "\n\r";
        }
        if (rev8En.isSelected()) {
            revisionData = revisionData + rev8Data.getText()+ "\n\r";
        } else {
            revisionData = revisionData + "\n\r";
        }

        if (rev9En.isSelected()) {
            revisionData = revisionData + rev9Data.getText()+ "\n\r";
        } else {
            revisionData = revisionData + "\n\r";
        }
        if (rev10En.isSelected()) {
            revisionData = revisionData + rev10Data.getText()+ "\n\r";
        } else {
            revisionData = revisionData + "\n\r";
        }
        if (rev11En.isSelected()) {
            revisionData = revisionData + rev11Data.getText()+ "\n\r";
        } else {
            revisionData = revisionData + "\n\r";
        }
        if (rev12En.isSelected()) {
            revisionData = revisionData + rev12Data.getText()+ "\n\r";
        } else {
            revisionData = revisionData + "\n\r";
        }

        System.out.println(revisionData);
        System.out.println(revisionImagePath);
        System.out.println(applicationChoiceBox.getValue().toString().trim());
        updateData(Integer.valueOf(applicationChoiceBox.getValue().toString().trim()));
        screenUtil.switchScene("NewApp.fxml", "New Application");


    }


    public void changeDbValue() {
        /*
        String dataToChange = changeToSubmit.getText();
        BeerApplicationData beerData = new BeerApplicationData(1234, null, 1111, 0, "hell", "carcinogen", "brewbrew",
                "100 institure", "555-867-5309", "Team65@wpi.edu", null, "wong", "distilled", "100");
        if (dataToChange.isEmpty()) {
            System.out.println("Nothing Happens");
            return;
        }
        if (firstChoiceBox.getValue().endsWith(choiceOne)) {
            beerData.setName(dataToChange);
            System.out.println(beerData.applicantName);
        } else if (firstChoiceBox.getValue().endsWith(choiceTwo)) {
            beerData.setApplicantName(dataToChange);
            System.out.println(beerData.getApplicantName());
        } else if (firstChoiceBox.getValue().endsWith(choiceThree)) {
            beerData.setApplicantName(dataToChange);
            System.out.println(beerData.getApplicantName());
        } else if (firstChoiceBox.getValue().endsWith(choiceFour)) {
            beerData.setApplicantName(dataToChange);
            System.out.println(beerData.getApplicantName());
        }
        */
    }

    /**
     * Updates data for a form in the database.
     * @param TTBID of form to update.
     */
    public void updateData(String TTBID) {
       /* Statement stmt = null;
        String query = "UPDATE ApplicationDB\n\r" +
                "SET firstChoiceBox.getValue() = " + data + "\n\r" +
                "WHERE (applicationID) = " + data;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String coffeeName = rs.getString("COF_NAME");
                int supplierID = rs.getInt("SUP_ID");
                float price = rs.getFloat("PRICE");
                int sales = rs.getInt("SALES");
                int total = rs.getInt("TOTAL");
                System.out.println(coffeeName + "\t" + supplierID +
                        "\t" + price + "\t" + sales +
                        "\t" + total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }


    public void needHelp (){
        screenUtil.switchScene("ReviseHelp.fxml","Help");
    }

}