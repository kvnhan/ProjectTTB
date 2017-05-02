import johnsUtil.model.SharedResources.Account;
import org.junit.Test;
import johnsUtil.model.SharedResources.Database;
import sample.DatabaseUtil;

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
        assertEquals(true,new DatabaseUtil(null,null).logIn("jaavant","password"));
        assertEquals("jaavant",acc.getUserName().trim());
    }

    @Test
    public void logIn2() throws SQLException{
        Account acc = Account.getInstance();
        assertEquals(false,new DatabaseUtil(null,null).logIn("jaavant","pssword"));
    }



}
