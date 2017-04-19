package sample;

import javafx.fxml.FXML;
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
    private ScreenUtil screenUtil;
    private DatabaseUtil databaseUtil;
    AccountsUtil accountsUtil;

    @FXML Text text1;
    @FXML Text text2;
    @FXML Text text3;
    @FXML TextField field1;
    @FXML TextField field2;
    @FXML TextField field3;
    @FXML TextField search;
    @FXML TreeView<TItem> view;


    public void initialize() throws SQLException {
        screenUtil = new ScreenUtil();
        databaseUtil = new DatabaseUtil();
        accountsUtil = new AccountsUtil();

        view = new TreeView<>();
        TreeItem<TItem> root = new TreeItem<TItem>(new AccountItem(0,accountsUtil.getUsername()));
        ArrayList<TreeItem<TItem>> list = databaseUtil.getAccountItems();

        root.getChildren().addAll(list);


    }

    private void addAccount(TreeItem<TItem> root) {

    }
}
