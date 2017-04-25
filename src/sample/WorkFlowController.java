package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;

public class WorkFlowController {

    @FXML private Label numberOfApplicationsLabel;
    private @FXML TableView inboxTable;
    private @FXML TableColumn ttbIDColumn, fancifulNameColumn, brandNameColumn, alcoholTypeColumn, submissionDateColumn;
    private AccountsUtil accountsUtil = new AccountsUtil();
    private ScreenUtil screenUtil = new ScreenUtil();
    private DatabaseUtil databaseUtil = new DatabaseUtil();
    private String username = accountsUtil.getUsername();
    private Account activeUser;
    private int numberOfApps;
    private static ApplicationData rowChosen;
    private ArrayList<ApplicationData> formsList = new ArrayList<>();
    @FXML private Button viewAllButton;

    private ObservableList<ApplicationData> observableFormsList;

    @FXML
    public void initialize() throws SQLException{
        databaseUtil.roundRobin();

        activeUser = databaseUtil.searchAccountWithUsername(username).get(0);

        if(activeUser.getUserType() == 1){
            formsList = databaseUtil.searchFormWithGovId(databaseUtil.getAccountAid(username));
        }else if (activeUser.getUserType() == 2){
            viewAllButton.setDisable(true);
            formsList = databaseUtil.searchFormWithAid(databaseUtil.getAccountAid(username));
        }

        numberOfApps = formsList.size();
        numberOfApplicationsLabel.setText(String.valueOf(numberOfApps));

        observableFormsList = FXCollections.observableList(formsList);
        if(numberOfApps > 0 && activeUser.getUserType() == 1){
            inboxTable.setRowFactory(tv -> {
                TableRow<ApplicationData> row = new TableRow<ApplicationData>();
                final ApplicationData[] rowData = new ApplicationData[1];
                row.setOnMouseClicked(event -> {
                    rowData[0] = row.getItem();
                    rowChosen = rowData[0];
                    if(event.getClickCount() == 2 && (! row.isEmpty())){
                        ApplicationReviewController.setAppReviewMode(2);
                        screenUtil.switchScene("ApplicationReview.fxml", "Application Review");
                    }
                });
                row.setTooltip(new Tooltip("Double click to open application"));
                return row;
            });
        }


        displayResults();
    }

    public void displayResults(){
        inboxTable.getColumns().clear();

        if(activeUser.getUserType() == 2) {
            alcoholTypeColumn.setText("Status");
            alcoholTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));
        }else{
                alcoholTypeColumn.setCellValueFactory(new PropertyValueFactory<>("AlcoholType"));
        }
        ttbIDColumn.setCellValueFactory(new PropertyValueFactory<>("TtbID"));
        fancifulNameColumn.setCellValueFactory(new PropertyValueFactory<>("FancyName"));
        brandNameColumn.setCellValueFactory(new PropertyValueFactory<>("Brand_name"));
        submissionDateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        inboxTable.setItems(this.getObservableFormsList());
        inboxTable.getColumns().addAll(ttbIDColumn, fancifulNameColumn, brandNameColumn, alcoholTypeColumn, submissionDateColumn);
    }

    /**
     * Checks if the back button has been clicked and returns user to Main Menu.
     * @param event ActionEvent representing a button press.
     */
    public void goBack (ActionEvent event){
        screenUtil.switchScene("MainMenu.fxml","Main Menu");
    }

    /**
     * Switches to the ApplicationReview screen.
     * @param event Button press representing the ViewFirst button.
     */
    public void viewAllApplicationsButton(ActionEvent event){
        if(numberOfApps <= 0){
            screenUtil.switchScene("WorkFlowController.fxml", "Inbox");
            screenUtil.createAlertBox("No applications due","There are no applications assigned to you at the moment.");
        }else{
            ApplicationReviewController.setAppReviewMode(1);
            screenUtil.switchScene("ApplicationReview.fxml", "Application Review");
        }
    }

    public ObservableList<ApplicationData> getObservableFormsList() {
        return observableFormsList;
    }

    public static ApplicationData getRowChosen() {
        return rowChosen;
    }

    public void needHelp (){
        screenUtil.switchScene("WorkFlowInboxHelp.fxml","Help");
    }

}