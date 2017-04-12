import org.junit.Test;
import sample.DatabaseUtil;

import javax.xml.crypto.Data;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * Created by John on 4/11/2017.
 */
public class DBUtilTest {
    @Test
    public void least() throws SQLException {
        DatabaseUtil db = new DatabaseUtil();
        assertEquals(13,db.searchMinWorkLoad(),0);
    }
}
