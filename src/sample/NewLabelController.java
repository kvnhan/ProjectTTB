package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

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

    public void fillOutApplication() {
        /*
        String id;
        String permit_no;
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
        String vintage;
        String pHLevel;
        int formID;

        id = ID.getText();
        permit_no = RepID.getText();

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
        vintage = Vintage.getText();
        pHLevel = pH.getText();

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
            submit(Data);
            System.out.println("This somewhat works");

        } else if (beer.isSelected()) {
            ApplicationData Data = new BeerApplicationData(formID, acceptanceInfo, id, permit_no, source_of_product, type_of_product, brand_name,
                    address, phone_number, email, date, applicantName, alcoholType, alcoholContent);
            submit(Data);
            System.out.println("This works too");

        } else if (other.isSelected()) {
            ApplicationData Data = new BeerApplicationData(formID, acceptanceInfo, id, permit_no, source_of_product, type_of_product, brand_name,
                    address, phone_number, email, date, applicantName, alcoholType, alcoholContent);
            submit(Data);
            System.out.println("This works too");

        }else{
            ApplicationData Data = new BeerApplicationData(formID, acceptanceInfo, id, permit_no, source_of_product, type_of_product, brand_name,
                    address, phone_number, email, date, applicantName, alcoholType, alcoholContent);
            submit(Data);
            System.out.println(Data.source_of_product);

        }*/
    }

    public void submit(/*ApplicationData appData*/){
         /*   appUtil.put(appData);
            appUtil.UpdateList();
            */
        }
}
