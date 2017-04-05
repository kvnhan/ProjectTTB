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
import java.util.Date;

import javafx.scene.control.TextField;
import java.util.Random;
/**
 * Created by Sam Winter on 3/28/2017.
 */
public class NewLabelController {

  //  ApplicationUtil appUtil = new ApplicationUtil();

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
    Connection conn = connect();

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

        int id;
        int permit_no;
        String source_of_product = "";
        String type_of_product = "";
        String brand_name;
        String phone_number;
        String email;
        String address;
        Date date = new Date();
        String applicantName;
        String alcoholType = "";
        String alcoholContent = "";
        int vintage;
        double pHLevel;
        int formID;

        id = Integer.parseInt(ID.getText());
        permit_no = Integer.parseInt(RepID.getText());

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
        if(!(dom.isSelected()) && !(imp.isSelected())){
            type_of_product = "";
            alcoholType = "";
        }

        brand_name = BrandName.getText();
        phone_number = PhoneNumber.getText();
        email = EmailAddress.getText();
        applicantName = Name.getText();
        address = Address.getText();
        vintage = Integer.parseInt(Vintage.getText());
        pHLevel = Double.parseDouble(pH.getText());

        Random rn = new Random();
        // May need to check is there are other forms in the database that has same form id
        // If the the form ID exists in the database, rerun the rn()
        formID = rn.nextInt(1000 - 1 + 1) + 1;


        acceptanceInformation acceptanceInfo = new acceptanceInformation(date, applicantName,
                null, "SUBMITED");
        if (wine.isSelected()) {
            ApplicationData Data = new WineApplicationData(formID, acceptanceInfo, id, permit_no,
                    brand_name, source_of_product, type_of_product, address, phone_number,
                    email, date, applicantName, alcoholType, alcoholContent,
                    vintage, pHLevel);
            submit(Data, conn);
            System.out.println("This somewhat works");

        } else if (beer.isSelected()) {
            ApplicationData Data = new BeerApplicationData(formID, acceptanceInfo, id, permit_no, source_of_product, type_of_product, brand_name,
                    address, phone_number, email, date, applicantName, alcoholType, alcoholContent);
            submit(Data, conn);
            System.out.println("This works too");

        } else if (other.isSelected()) {
            ApplicationData Data = new BeerApplicationData(formID, acceptanceInfo, id, permit_no, source_of_product, type_of_product, brand_name,
                    address, phone_number, email, date, applicantName, alcoholType, alcoholContent);
            submit(Data, conn);
            System.out.println("This works too");

        }else{
            ApplicationData Data = new BeerApplicationData(formID, acceptanceInfo, id, permit_no, source_of_product, type_of_product, brand_name,
                    address, phone_number, email, date, applicantName, alcoholType, alcoholContent);
            submit(Data, conn);
            System.out.println("Hi");
        }
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

    public void submit(ApplicationData d, Connection cn)throws SQLException{

        int id = d.getId();
        int permit_no = d.getPermit_no();
        String source_of_product;
        String type_of_product = "";
        String brand_name = d.getBrand_name();
        String phone_number = d.getPhone_number();
        String email = d.getEmail();
        String address = null;
        Date date = d.getDate();
        String applicantName = d.getApplicantName();
        String alcoholType = d.getAlcoholType();
        String alcoholContent = d.getAlcoholContent();
        int vintage = 0;
        double pHLevel = 0;
        int formID = d.getFormID();
        int COMPLETE = 1;

        Statement sm = cn.createStatement();
        if(d.getType_of_product().equals("WINE")){
            sm.executeUpdate("INSERT INTO FORM (FID, DATECOMPLETED, AID, STATUS, ALCID, TTBID, PERMITNO, BRANDNAME, ADDRESS, " +
                    "PHONENUMBER, EMAIL, PHLEVEL, " +
                    "VINTAGEDATE, NAME_OF_APPLICANT) VALUE ('"+formID+"','"+date+"','"+id+"','"+COMPLETE+"','"+id+"','"+id+"','"+permit_no+"'" +
                    ",'"+brand_name+"','"+address+"','"+phone_number+"','"+email+"','"+pHLevel+"','"+vintage+"','"+applicantName+"')");
        }else{
            sm.executeUpdate("INSERT INTO FORM (FID, DATECOMPLETED, AID, STATUS, ALCID, TTBID, PERMITNO, BRANDNAME, ADDRESS, " +
                    "PHONENUMBER, EMAIL, PHLEVEL, " +
                    "VINTAGEDATE, NAME_OF_APPLICANT) VALUE ('"+formID+"','"+date+"','"+id+"','"+COMPLETE+"','"+id+"','"+id+"','"+permit_no+"'" +
                    ",'"+brand_name+"','"+address+"','"+phone_number+"','"+email+"','"+applicantName+"')");






        }
    }


}
