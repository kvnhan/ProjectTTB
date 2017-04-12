package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;

/**
 * Created by peternolan on 4/2/17.
 */
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

    private FXMLLoader fxmlLoader;

    private @FXML CheckBox rev1En, rev2En, rev3En, rev4En, rev5En, rev6En, rev7En,rev8En, rev9En, rev10En, rev11En, rev12En;

    private @FXML TextArea rev1Data, rev2Data, rev3Data, rev4Data, rev5Data, rev6Data, rev7Data, rev8Data, rev9Data,rev10Data, rev11Data, rev12Data;

    private @FXML TextField applicationID;

    @FXML private Button back;
    @FXML private Button submit;
    @FXML private Button UploadImage;

    String revisionData = "";

    DatabaseUtil dbUtil = new DatabaseUtil();

    Connection conn = dbUtil.connect();

    ScreenUtil screen = new ScreenUtil();

    /**
     * This is buttonClicked, the function that dictates events depending on which button has been clicked.
     *
     * @param event
     */
    public void buttonClicked(ActionEvent event) {
        try {
            if (event.getSource() == back) {
                fxmlLoader = new FXMLLoader(getClass().getResource("NewApp.fxml"));
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
            Parent root1 = null;
            root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Main Menu");
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void submitButtonClicked(ActionEvent event) {
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
        System.out.println(applicationID.getText());
        updateData(applicationID.getText());
        screen.switchScene("NewApp.fxml", "New Application");


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

    public void updateData(String data) {
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

}