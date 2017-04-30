package johnsUtil.model.SharedResources;

import sample.DatabaseUtil;

/**
 * Created by John on 4/30/2017.
 */
public class Database {
    private static DatabaseUtil database = new DatabaseUtil();

    private Database(){};

    public DatabaseUtil getInstance(){ return  database; }
}
