package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.util.ArrayList;

public class WorkFlowController {

    private FXMLLoader fxmlLoader;
    @FXML private Label numberOfApplicationsLabel;
    @FXML private Button back;
    @FXML private Button first;
    private AccountsUtil accountsUtil = new AccountsUtil();
    private ScreenUtil screenUtil = new ScreenUtil();
    private DatabaseUtil databaseUtil = new DatabaseUtil();

    private String username = accountsUtil.getUsername();
    private int numberOfApps;


    @FXML
    public void initialize() throws SQLException{
        roundRobin();
        numberOfApps = databaseUtil.searchFormWithGovId(databaseUtil.getAccountAid(username)).size();
        numberOfApplicationsLabel.setText(String.valueOf(numberOfApps));
    }

    //goes adds new applications to worker's inboxes
    public void roundRobin() throws  SQLException{
        ArrayList<ApplicationData> unAssignedForms = databaseUtil.searchUnassignedForms();
        if(!(unAssignedForms.size() == 0)){
            int runThroughs = (int)(unAssignedForms.size())/10;
            for(int i = 0; i <= runThroughs; i++) {;
                for (int j = 0; j <= 10; j++) {
                    int govid = databaseUtil.searchMinWorkLoad();
                    databaseUtil.assignForm(govid, unAssignedForms.get(j));
                }
            }
        }

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
    public void viewFirstApplicationButton(ActionEvent event){
        if(numberOfApps <= 0){
            screenUtil.createAlertBox("No applications due","There are no applications assigned to you at the moment.");

        }else{
            screenUtil.switchScene("ApplicationReview.fxml", "Application Review");
        }
    }
}