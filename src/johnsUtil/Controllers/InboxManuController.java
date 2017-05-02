package johnsUtil.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import johnsUtil.model.SharedResources.*;
import johnsUtil.model.Database.DatabaseUtil;
import sample.*;
import sample.Account;

import java.net.URL;
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
                    ListCell<String> cell = new ListCell<>();
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

            final ContextMenu contextMenu = new ContextMenu();
            MenuItem editItem = new MenuItem("Edit Label");
            editItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    ApplicationData selectedItem = listView.getSelectionModel().getSelectedItem();
                    System.out.println(selectedItem.getTtbID());
                        try {
                            if (selectedItem.getStatus().equals("ACCEPTED")) {
                                dataPasser.setTtbID(selectedItem.getTtbID());
                                Parent screen = FXMLLoader.load(getClass().getResource("/sample/ReviseMenu.fxml"));
                                getRoot().setCenter(screen);

                            }
                        }catch(Exception o){

                        }


                }
            });
            contextMenu.getItems().add(editItem);
            listView.setContextMenu(contextMenu);
            listView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                @Override
                public void handle(ContextMenuEvent event) {
                    contextMenu.show(listView, event.getScreenX(), event.getScreenY());
                    event.consume();

                }
            });

            if(activeUser.getUserType() == 2) {
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
                                    Parent screen = FXMLLoader.load(getClass().getResource("/johnsUtil/Views/NewLabel.fxml"));
                                    getRoot().setCenter(screen);
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                                System.out.println("Pleaseeeee");
                                //hbox = FXMLLoader.load(getClass().getResource("/wpi/Toolbar.fxml")); toolbar
                            } catch (Exception e) {

                            }

                        }
                    }
                });
            }else{
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
                                    Parent screen = FXMLLoader.load(getClass().getResource("/sample/ApplicationReview.fxml"));
                                    getRoot().setCenter(screen);
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                                System.out.println("Pleaseeeee");
                                //hbox = FXMLLoader.load(getClass().getResource("/wpi/Toolbar.fxml")); toolbar
                            } catch (Exception e) {

                            }

                        }
                    }
                });
            }

        } catch (Exception e) {

        }

    }

    private BorderPane getRoot(){
        BorderPane parent = (BorderPane) johnsUtil.model.SharedResources.Account.getInstance().getWindow().getScene().getRoot();
        for(Node node: parent.getChildren()){
            if(node.getAccessibleText() != null && node.getAccessibleText().equals("center")){
                return (BorderPane) node;
            }
        }
        return null;
    }

}
