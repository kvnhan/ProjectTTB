package johnsUtil.model.SharedResources;

import sample.DatabaseUtil;

/**
 * Class containing database info.
 */
public class Database {
    private static DatabaseUtil database;

    static{
        database = new DatabaseUtil();
    }

    private Database(){}
    /**
     * Gets an instance of the database utility class.
     */
    public static DatabaseUtil getInstance(){ return  database; }
}
