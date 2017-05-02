package johnsUtil.Controllers;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import johnsUtil.Components.AccountItem;
import johnsUtil.Components.TItem;
import johnsUtil.model.SharedResources.*;
import johnsUtil.model.SharedResources.Account;
import johnsUtil.model.Database.DatabaseUtil;
import sample.ScreenUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by John on 5/1/2017.
 */
public class SupervisorController implements Initializable{
    private ScreenUtil screenUtil;
    private DatabaseUtil databaseUtil;
    private Account accountsUtil;

    @FXML
    private BorderPane borderPane;

    @FXML
    private JFXTreeView<TItem> view;

    @FXML
    private HBox hamBox;

    @FXML
    private JFXHamburger hamburger;
    @FXML
    private ScrollPane formView;

    @FXML
    private TextField ttbid;

    @FXML
    private TextField capacity;

    @FXML
    private TextField state;

    @FXML
    private CheckBox toa1;

    @FXML
    private CheckBox toa2;

    @FXML
    private CheckBox toa3;

    @FXML
    private CheckBox toa4;

    @FXML
    private JFXRadioButton imp;

    @FXML
    private ToggleGroup Source;

    @FXML
    private JFXRadioButton dom;

    @FXML
    private JFXRadioButton beerCB;

    @FXML
    private ToggleGroup Type;

    @FXML
    private JFXRadioButton wineCB;

    @FXML
    private JFXRadioButton distilledCB;

    @FXML
    private JFXTextField RepID;

    @FXML
    private JFXTextField PlantReg;

    @FXML
    private JFXTextField SerialNo;

    @FXML
    private JFXTextField Address;

    @FXML
    private JFXTextField ApplicantName;

    @FXML
    private JFXTextField MailingAddress;

    @FXML
    private JFXTextField EmailAddress;

    @FXML
    private JFXTextField PhoneNumber;

    @FXML
    private JFXTextField BrandName;

    @FXML
    private JFXTextField Name;

    @FXML
    private JFXTextField Formula;

    @FXML
    private JFXTextField alcoholContent;

    @FXML
    private JFXTextField netContentField;

    @FXML
    private JFXTextField Appellation;

    @FXML
    private JFXTextField grapeVarietal;

    @FXML
    private JFXTextField Vintage;

    @FXML
    private JFXTextField pH;

    @FXML
    private JFXTextField sulfiteField;

    @FXML
    private ImageView image;

    @FXML
    private JFXTextField bottlerField;

    @FXML
    private TextArea additionalInfoField;

    @FXML
    private JFXTextField originField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Parent scene will inject its own hamburger into hbox
        hamBox.getChildren().remove(hamburger);


        screenUtil = Screen.getInstance();
        databaseUtil = Database.getInstance();
        accountsUtil = johnsUtil.model.SharedResources.Account.getInstance();

        TreeItem<TItem> root = new TreeItem<TItem>(new AccountItem(0,accountsUtil.getUserName()));
        try{
            ArrayList<TreeItem<TItem>> list = databaseUtil.getAccountItems();
            for(TreeItem<TItem> node: list){
                node.setExpanded(true);
            }
            root.getChildren().addAll(list);


            view.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<TItem>>() {
                @Override
                public void changed(ObservableValue<? extends TreeItem<TItem>> observable, TreeItem<TItem> oldValue, TreeItem<TItem> newValue) {
                    if(newValue == null){
                        return;
                    }
                    else{

                    }
                }
            });

            view.setRoot(root);
        }catch(SQLException e){
            System.out.println("Couldn't connect to Database");
        }
    }


}
