package johnsUtil.model.SharedResources;

import johnsUtil.model.Database.DatabaseUtil;

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
