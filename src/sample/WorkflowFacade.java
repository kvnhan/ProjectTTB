package sample;

import java.sql.*;
import java.util.ArrayList;


/**
 * Facade for Workflow-related methods.
 */
public class WorkflowFacade {

    DatabaseUtil db = new DatabaseUtil();

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


}
