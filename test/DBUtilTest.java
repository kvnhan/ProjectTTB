import johnsUtil.model.SharedResources.Account;
import johnsUtil.model.SharedResources.Database;
import org.junit.Test;
import sample.DatabaseUtil;

import javax.xml.crypto.Data;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * Created by John on 4/11/2017.
 */
public class DBUtilTest {
//    /** OFFICIALLY TESTED AND WORKS - John
//     * Will fail because it interacts with database, only passes under artificial database configuration
//     * for more clarification msg @John
//     * @throws SQLException
//     */
//    @Test
//    public void ttbid() throws SQLException {
//        DatabaseUtil db = new DatabaseUtil(null,null); //use to test methods that don't use accounts or screen util
//        assertEquals("17108001000001",db.getNewTTBID());
//    }

    @Test
    public void logIn() throws SQLException{
        Account acc = Account.getInstance();
        assertEquals(false,new DatabaseUtil(null,null).logIn("adonay","password"));
        assertEquals("adonay",acc.getUserType());
    }

    @Test
    public void logIn2() throws SQLException{
        Account acc = Account.getInstance();
        assertEquals(false,new DatabaseUtil(null,null).logIn("adonay","password"));
    }

    @Test
    public void logout() throws SQLException{
        Account acc = Account.getInstance();
        new DatabaseUtil(null,null).logIn("adonay","password");
        acc.logout();

        assertEquals(acc.getAccountID(),-1);
        assertEquals(acc.getUserName(), "");

    }

}
