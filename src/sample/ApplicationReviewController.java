package sample;


import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sun.java2d.pipe.AlphaPaintPipe;

import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ApplicationReviewController {
    @FXML
    Button approve;
    @FXML
    Button reject;
    @FXML
    Button goBack;
    @FXML
    TextField repID;
    @FXML
    TextField registryNo;
    @FXML
    TextField prodSource;
    @FXML
    TextField prodType;
    @FXML
    TextField address;
    @FXML
    TextField phoneNo;
    @FXML
    TextField email;
    @FXML
    TextField dateApp;
    @FXML
    TextField nameApp;
    @FXML
    TextArea commentsField;
    //for when switching to this scene from inbox
    /*      repID.setText(application.ID);
            registryNo.setText("0000");
            prodSource.setText("TEST");
            prodType.setText("TEST");
            address.setText("00 Test Address");
            phoneNo.setText("Test");
            email.setText("Sample@test");
            dateApp.setText("00/00/TEST");
            nameApp.setText("TEST");*/
    Connection conn = connect();

    @FXML
    void setGoBack(ActionEvent event){
        ScreenUtil work = new ScreenUtil();
        work.pullUpScreen("WorkFlow.fxml", "Main Menu", event);
    }

    @FXML

    //TODO: Fix setApprove - needs correct query fields for sql
    void setApprove() throws SQLException{
        Statement stm;
        stm = conn.createStatement();
        //get comments
        String comments = commentsField.getText();
        //update alcohol status
        String sql = "UPDATE ALCOHOL SET status = 'approved', comments = 'comments'  WHERE id = apptoassgn";
        stm.executeUpdate(sql);
        //update inbox for account
        sql = "UPDATE REVIEWS SET w.inbox.remove(apptoassgn) WHERE username = w.username";
        stm.executeUpdate(sql);
    }

    @FXML

    //TODO: fix setReject - needs correct query fields for sql
    void setReject() throws SQLException{
        Statement stm;
        stm = conn.createStatement();
        //get comments
        String comments = commentsField.getText();
        //update alcohol status
        String sql = "UPDATE ALCOHOL SET status = 'rejected', comments = comments WHERE id = apptoassgn";
        stm.executeUpdate(sql);
        //update inbox for worker
        sql = "UPDATE REVIEWS SET w.inbox.remove(apptoassgn) WHERE username = w.username";
        stm.executeUpdate(sql);
    }



    //creates a list of unassigned applications
    private static ArrayList<String> getUnassigForms() throws ClassNotFoundException, SQLException {
        Connection conn = connect();
        Statement stm;
        stm = conn.createStatement();
        String sql = "SELECT * FROM ALCOHOL WHERE ALCOHOL.STATUS = 'Unassigned'"; // Use Select _ from _ Where _ format and set this statement = sql
        ArrayList<String> unassforms = new ArrayList<>();
        ResultSet unassAlc = stm.executeQuery(sql);
        ResultSetMetaData rsmd = unassAlc.getMetaData();
        int columnCount = rsmd.getColumnCount();
        while(unassAlc.next()){
            int i = 1;
            while(i <= columnCount){
                unassforms.add(unassAlc.getString("id"));
            }
        }
        return unassforms;
    }

    //goes through a list of unassigned applications
    //finds worker with the least amount of applications
    Account getSmallWorker() throws ClassNotFoundException, SQLException{//TODO: find out fields + name for govt. worker
        Statement stm;
        stm = conn.createStatement();
        String sql = "SELECT AID.MIN(CNT(FID)) FROM REVIEWS";
        ResultSet smallWorker = stm.executeQuery(sql);
        Account worker = new Account(smallWorker.getString("id"), 0,
                ArrayToArrayList((String[]) smallWorker.getArray("inbox").getArray()));
        return worker;
    }

    ArrayList<String> ArrayToArrayList(String[] input){
        ArrayList<String> returnThing = new ArrayList<String>();
        for(int i=0; i<input.length; i++){
            returnThing.add(input[i]);
        }
        return returnThing;
    }


    //adds an application to a worker
    //alters the status of the application to assigned
    //pushes the changes to the worker and the application
    void addToInbox(Account w, String apptoassgn) throws ClassNotFoundException, SQLException{
        Statement stm;
        stm = conn.createStatement();
        //update alcohol status
        String sql = "UPDATE ALCOHOL SET status = 'assigned' WHERE id = 'apptoassgn'";
        stm.executeUpdate(sql);
        //update inbox for worker
        sql = "UPDATE REVIEWS SET inbox.add(apptoassgn) WHERE id = w.id";
        stm.executeUpdate(sql);
    }

    /**
     * Gets a list of unassigned forms then assigns them to government account inboxes.
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    //adds all the unassigned forms to workers inboxes
    void addAllUnassigned() throws ClassNotFoundException, SQLException{
        ArrayList<String> unassigForms = getUnassigForms();
        for (int i = 0; i < unassigForms.size(); i++) {
            addToInbox(getSmallWorker(), unassigForms.get(i));
        }
    }

    public static Connection connect(){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Java DB Driver not found. Add the classpath to your module.");
            e.printStackTrace();
            return null;
        }

        System.out.println("Java DB driver registered!");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:derby:ProjectC;create=true");
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return connection;
        }
        System.out.println("Java DB connection established!");

        return connection;
    }
}
