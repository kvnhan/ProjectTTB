package johnsUtil.Controllers;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTreeView;
import com.sun.org.apache.xml.internal.security.Init;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import johnsUtil.Components.AccountItem;
import johnsUtil.Components.TItem;
import johnsUtil.model.SharedResources.*;
import johnsUtil.model.SharedResources.Account;
import sample.DatabaseUtil;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
                        //CHange scene ...
                    }
                }
            });


            view.setRoot(root);
        }catch(SQLException e){
            System.out.println("Couldn't connect to Database");
        }
    }
}
