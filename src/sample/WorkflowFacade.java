package sample;

import java.sql.*;
import java.util.ArrayList;


/**
 * Facade for Workflow-related methods.
 */
public class WorkflowFacade extends DatabaseUtil {

    Connection conn = connect();

    void addToInbox(int apptoassgn) /*throws ClassNotFoundException, SQLException*/ {

        System.out.print("This Facade Has Sent a Form to WorkFlowFacade, HUZZAH! The Application ID to be Sent is " + apptoassgn + "\n");
        /*
        Statement stm;
        stm = conn.createStatement();
        //update alcohol status
        String sql = "UPDATE ALCOHOL SET status = 'assigned', aid = " + w.getUsername() + "WHERE FORM.FID = "+ apptoassgn;
        stm.executeUpdate(sql);
        //update inbox for worker
        sql = "UPDATE REVIEWS SET inbox = " + w.getInbox().add(apptoassgn) +" WHERE id = " + w.getUsername() +"";//TODO: Check syntax on set inbox
        stm.executeUpdate(sql);

       */
    }

    /**
     * Converts an Array object to an ArrayList object. The datatype stored is String.
     *
     * @param input The Array of Strings to be converted to an ArrayList.
     * @return Returns an ArrayList of Strings.
     */

    ArrayList<String> ArrayToArrayList(String[] input){
        ArrayList<String> returnThing = new ArrayList<String>();
        for(int i=0; i<input.length; i++){
            returnThing.add(input[i]);
        }
        return returnThing;
    }

    /**
     * Gets a list of all Applications that have the status "UNASSIGNED".
     *
     * @return Returns an ArrayList of the IDs of the unassigned Applications. The IDs are represented
     * by Strings.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
     static ArrayList<String> getUnassigForms() throws SQLException {
        Connection conn = connect();
        Statement stm;
        stm = conn.createStatement();
        ArrayList<String> unassforms = new ArrayList<>();
        try {
            String sql = "SELECT * FROM FORM WHERE FORM.STATUS = 'IN PROGRESS'"; // Use Select _ from _ Where _ format and set this statement = sql

            ResultSet unassAlc = stm.executeQuery(sql);
            ResultSetMetaData rsmd = unassAlc.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (unassAlc.next()) {
                int i = 1;
                unassforms.add(unassAlc.getString("FID"));
                i++;
            }
            for (int i = 0; i <= unassforms.size() - 1; i++) {
                System.out.println(unassforms.get(i));
            }
            unassAlc.close();
            stm.close();
            conn.close();
        } catch (SQLException se){
            se.printStackTrace();
        }

        return unassforms;
    }

    /**
     * Finds the government account in the database with the least number of applications in its
     * inbox.
     *
     * @return Returns the government Account with the smallest number of applications in its
     * inbox.
     * @throws ClassNotFoundException
     * @throws SQLException
     */

    Account getSmallWorker() throws ClassNotFoundException, SQLException{//TODO: find out fields + name for govt. worker
        Statement stm;
        stm = conn.createStatement();
        String sql = "SELECT AID FROM ALCOHOL HAVING MIN(COUNT(ForeignKeyForAccount))";
        ResultSet smallWorker = stm.executeQuery(sql);
        //Creates a new Account Object based on the information found in the previous
        Account worker = new Account(smallWorker.getString("id"), 0,
                ArrayToArrayList((String[]) smallWorker.getArray("inbox").getArray()));
        return worker;
    }
    void addToInbox(Account w, String apptoassgn) throws ClassNotFoundException, SQLException{
        Statement stm;
        stm = conn.createStatement();
        //update alcohol status
        String sql = "UPDATE ALCOHOL SET status = 'assigned', ForeignKeyForAccount = apptoassgn WHERE ACCOUNT.AID = "+ w.getUsername();
        stm.executeUpdate(sql);
        //update inbox for worker
        //sql = "UPDATE REVIEWS SET inbox = " + w.getInbox().add(apptoassgn) +" WHERE id = " + w.getUsername();//TODO: Check syntax on set inbox
        stm.executeUpdate(sql);
    }

}