package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;
import sun.reflect.generics.tree.Tree;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by John on 4/19/2017.
 */
public class SupervisorController {
    private ScreenUtil screenUtil = new ScreenUtil();
    private DatabaseUtil databaseUtil = new DatabaseUtil();
    AccountsUtil accountsUtil = new AccountsUtil();

    @FXML
    Text text1;
    @FXML
    Text text2;
    @FXML
    Text text3;
    @FXML
    TextField field1;
    @FXML
    TextField field2;
    @FXML
    TextField field3;
    @FXML
    Button search;
    @FXML
    TreeView<TItem> view;

    @FXML
    public void goBack(){
        screenUtil.switchScene("MainMenu.fxml", "Main Menu");
    }

    public void initialize() throws SQLException {

    }

    @FXML
    public void handleSearch(){
        screenUtil = new ScreenUtil();
        databaseUtil = new DatabaseUtil();
        accountsUtil = new AccountsUtil();

        //view = new TreeView<>();

        TreeItem<TItem> root = new TreeItem<TItem>(new AccountItem(0,accountsUtil.getUsername()));
        try{
            ArrayList<TreeItem<TItem>> list = databaseUtil.getAccountItems();
            root.getChildren().addAll(list);


            view.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<TItem>>() {
                @Override
                public void changed(ObservableValue<? extends TreeItem<TItem>> observable, TreeItem<TItem> oldValue, TreeItem<TItem> newValue) {
                    if(newValue == null){
                        return;
                    }
                    else{
                        TItem value = newValue.getValue();
                        text1.setText(value.getText1());
                        text2.setText(value.getText2());
                        text3.setText(value.getText3());
                        field1.setText(value.getData1());
                        field2.setText(value.getData2());
                        field3.setText(value.getData3());
                    }
                }
            });

            view.setRoot(root);
            System.out.println("SET ROOT");
        }catch(SQLException e){
            System.out.println("Uh oh");
        }
        System.out.println("DOne");
    }

    public void needHelp (){
        screenUtil.switchScene("superVisorHelp.fxml","Help");
    }

}
