package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

/**
 * Created by peternolan on 4/2/17.
 */
//Change to a relevant path. Soecify the name of the subfolder in the path and that will help to connect the db.
    //Go back to the notes from the JDBC slides. One uses a prepared statemnt and one does not
    //Figure out how the Data is set up.
    //Find the attributes of the object to contain the information.
    //Create a program that does a search on the table and maps that to the resulting java object
    //Have it be seperate for each class.
    //Quick Exercise: pick a small table, write the code that querys the table and query for one or several records and figure out how I would stick those records into a java class
    //After that, modify it to return the java object.
    //Also, might want to create code that updates the information in the database.


public class ReviseMenuController {

    private FXMLLoader fxmlLoader;

    @FXML
    private Button back;
    @FXML
    private Button submit;
    @FXML
    private TextField changeToSubmit;
    @FXML
    private TextField applicationID;
    @FXML
    private ChoiceBox<String> firstChoiceBox;
    final String choiceOne = "Add, change, or delete vintage date (WINE ONLY)";
    final String choiceTwo = "Add, change or delete the stated amount of acid and/or the pH level (WINE ONLY)";
    final String choiceThree = "Change the mandatory statement of alcohol content (WINE ONLY)";
    final String choiceFour = "Add, delete, or change optional statement of alcohol content (MALT ONLY)";

    Connection conn = connect();

    ScreenUtil screen = new ScreenUtil();

    /**
     * This is buttonClicked, the function that dictates events depending on which button has been clicked.
     *
     * @param event
     */
    public void buttonClicked(javafx.event.ActionEvent event) {
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

    public void submitButtonClicked(javafx.event.ActionEvent event) {

        updateData(applicationID.getText());
        screen.pullUpScreen("NewApp.fxml", "New Application", event);


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
            }


        }*/
    }

    public static Connection connect(){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Java DB Driver not found. Add the classpath to your module.");
            e.printStackTrace();
            return null;
        }

        System.out.println("Java DB driver registered!");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:derby:DATABASE\\ProjectC;create=true");
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return connection;
        }
        System.out.println("Java DB connection established!");

        return connection;
    }


}