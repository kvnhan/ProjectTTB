package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import johnsUtil.model.Database.DatabaseUtil;

import java.sql.SQLException;

/**
 * Controller class for Main Menu screen.
 */
public class MainMenuController{

    @FXML private Button openSearchButton, openInboxButton, createNewApplicationButton, logOutButton, aboutButton, superUserButton;
    @FXML private Text userIDText;
    @FXML private ImageView colaImage;
    @FXML private ImageView symbolImage;

    ScreenUtil work = new ScreenUtil();
    DatabaseUtil databaseUtil = new DatabaseUtil();
    private String username;

    @FXML
    /**
     * Initializes the main menu.
     */
    public void initialize() throws SQLException{

        username = johnsUtil.model.SharedResources.Account.getInstance().getUserName();
        userIDText.setText(username);

        if(username.toLowerCase().equals("guest")){
            openInboxButton.setVisible(false);
            superUserButton.setVisible(false);
            createNewApplicationButton.setVisible(false);
            superUserButton.setVisible(false);
        }else if(databaseUtil.searchAccountWithUsername(username).get(0).getUserType() == 3){
            openInboxButton.setVisible(false);
            superUserButton.setVisible(false);
            createNewApplicationButton.setVisible(false);
        }else if(databaseUtil.searchAccountWithUsername(username).get(0).getUserType() == 2){
            superUserButton.setVisible(false);
        }else if(databaseUtil.searchAccountWithUsername(username).get(0).getUserType() == 1){
            createNewApplicationButton.setVisible(false);
            superUserButton.setVisible(false);
        }




        /*
        try {
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("resources/COLARegistry.png");
            colaImage.setImage(new javafx.scene.image.Image(resource, 500.0, 0.0, true, true));
        }
        catch(NullPointerException nullPoint){
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/imageUnavailable.jpg");
            colaImage.setImage(new javafx.scene.image.Image(resource, 100.0, 0.0, true, true));
            System.out.println("Image Was Not Found");
        }

        try {
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("resources/Symbol.png");
            symbolImage.setImage(new javafx.scene.image.Image(resource, 500.0, 0.0, true, true));
        }
        catch(NullPointerException nullPoint){
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/imageUnavailable.jpg");
            symbolImage.setImage(new javafx.scene.image.Image(resource, 100.0, 0.0, true, true));
            System.out.println("Image Was Not Found");
        }
        */


    }

    /**
     *This is the code that figures out where the UI will go when certain buttons are clicked. Right now, the only
     * transition that can be seen from this is an instant change and deletion of the different panels.
     * @param event - action that activates the changing menu procedure, i.e. clicking on a button in the menu
     */
    public void buttonClicked(ActionEvent event) {

        if(event.getSource() == openSearchButton){
            work.switchScene("SearchMenu.fxml", "Search");

        }else if(event.getSource() == openInboxButton){
            work.switchScene("WorkFlow.fxml", "Inbox");

        }else if(event.getSource() == createNewApplicationButton){
            work.switchScene("NewApp.fxml","New Application");

        }else if(event.getSource() == logOutButton){
            work.switchScene("Login.fxml","Login");

        } else if (event.getSource() == aboutButton){
            work.switchScene("About.fxml","About");
        } else{
            work.switchScene("ErrorState.fxml","Error");
        }
    }

    /**
     * Switches screen to the Supervisor View if the Superuser button is pressed.
     * @param event Superuser button is pressed.
     */
    public void SuperUser(ActionEvent event){
      work.switchScene("supervisorView.fxml", "Super User");
    }


}