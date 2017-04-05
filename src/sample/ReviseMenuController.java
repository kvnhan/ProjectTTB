package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

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

    @FXML private Button back;
    ScreenUtil work = new ScreenUtil();

    /**
     * This is buttonClicked, the function that dictates events depending on which button has been clicked.
     * @param event
     */
    public void buttonClicked (javafx.event.ActionEvent event){
            if(event.getSource() == back){
                work.pullUpScreen("NewApp.fxml", "New Application", event);
            }
    }
}