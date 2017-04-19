package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;

import java.sql.SQLException;

/**
 * Created by John on 4/19/2017.
 */
public class SupervisorController {
    private ScreenUtil screenUtil;
    private DatabaseUtil databaseUtil;

    @FXML Text text1;
    @FXML Text text2;
    @FXML Text text3;
    @FXML TextField field1;
    @FXML TextField field2;
    @FXML TextField field3;
    @FXML TextField search;
    @FXML TreeView<Object> view;




    public void initialize() throws SQLException {
        screenUtil = new ScreenUtil();
        databaseUtil = new DatabaseUtil();

    }
}
