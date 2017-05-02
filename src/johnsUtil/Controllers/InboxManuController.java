package johnsUtil.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import sample.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Controller for the Manufacturer Inbox screen.
 */
public class InboxManuController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private JFXButton btnMinimize;

    @FXML
    private JFXButton btnExpand;

    @FXML
    private JFXButton btnClose;

    private AccountsUtil accountsUtil = new AccountsUtil();
    private ScreenUtil screenUtil = new ScreenUtil();
    private DatabaseUtil databaseUtil = new DatabaseUtil();
    private String username = accountsUtil.getUsername();
    private Account activeUser;
    private int numberOfApps;
    private DataPasser dataPasser = new DataPasser();
    private static ApplicationData rowChosen;
    private ArrayList<ApplicationData> formsList = new ArrayList<>();
    private ObservableList<ApplicationData> observableFormsList;
    private ArrayList<ApplicationData> form = new ArrayList<>();
    private List<AlcoholData> data = new ArrayList<>();

    @FXML private ListView<ApplicationData> listView = new ListView<>();
    @Override
    /**
     * Initializes the manufacturer inbox.
     */
    public void initialize(URL location, ResourceBundle resources) {


        String user = johnsUtil.model.SharedResources.Account.getInstance().getUserName();
        try {
            databaseUtil.roundRobin();
            activeUser = databaseUtil.searchAccountWithUsername(user).get(0);
            System.out.println(user);

            if (activeUser.getUserType() == 1) {
                formsList = databaseUtil.searchFormWithGovId(databaseUtil.getAccountAid(user));
            } else if (activeUser.getUserType() == 2) {
                formsList = databaseUtil.searchFormWithAid(databaseUtil.getAccountAid(user));
            }

            numberOfApps = formsList.size();
            observableFormsList = FXCollections.observableList(formsList);
            listView.setItems(observableFormsList);
            System.out.println(observableFormsList.get(0).getTtbID());
            listView.setCellFactory(param -> new ListCell<ApplicationData>() {
                @Override
                protected void updateItem(ApplicationData item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null) {
                        setText(null);
                    } else {
                        try {
                            setText(item.getTtbID() + " - " + item.getStatus());
                        }catch (Exception e){

                        }
                    }
                }
            });
            listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent click) {
                    if (click.getClickCount() == 2) {
                        ApplicationData a = listView.getSelectionModel().getSelectedItems().get(0);
                        System.out.println(a.getTtbID());

                        try {
                            form = databaseUtil.searchFormWithTTBID(a.getTtbID());
                            data = databaseUtil.searchAlcoholID(databaseUtil.getAidOfForm(form.get(0).getTtbID()));
                            System.out.println(form.size());
                            System.out.println("Pleaseeee");
                            dataPasser.setAlcData(data.get(0));
                            System.out.println("Pleaseee");
                            dataPasser.setApplicationData(form.get(0));
                            System.out.println("Pleaseee");
                            dataPasser.setIsInvokedByManu(1);
                            System.out.println(dataPasser.getAlcData().getAid());
                            System.out.println(dataPasser.getIsInvokedByManu());
                            try {
                                borderPane.setRight(FXMLLoader.load(getClass().getResource("/sample/NewLabel.fxml")));
                            }catch (Exception e1){
                                e1.printStackTrace();
                            }
                            System.out.println("Pleaseeeee");
                            //hbox = FXMLLoader.load(getClass().getResource("/wpi/Toolbar.fxml")); toolbar
                        } catch (Exception e) {

                        }

                    }
                }
            });

        } catch (Exception e) {

        }

    }

}
