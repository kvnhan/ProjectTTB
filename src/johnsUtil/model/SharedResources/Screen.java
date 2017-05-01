package johnsUtil.model.SharedResources;

/**
 * Created by John on 5/1/2017.
 */

import sample.DatabaseUtil;
import sample.ScreenUtil;

/**
 * Class containing database info.
 */
public class Screen {
    private static ScreenUtil screenUtil;

    static {
        screenUtil = new ScreenUtil();
    }

    private Screen(){}
    /**
     * Gets an instance of the database utility class.
     */
    public static ScreenUtil getInstance(){ return screenUtil; }
}
