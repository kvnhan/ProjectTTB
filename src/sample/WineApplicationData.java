package sample;



import java.util.Date;


/**

 * Created by Kien Nhan on 4/3/2017.

 */

public class WineApplicationData extends ApplicationData{
    /**
     * Constructs a wine application data object. All parameters are taken from
     * ApplicationData; see the ApplicationData class for more information.
     */
    public WineApplicationData(String ttbID, int repid, String serial, String address, String permitNo, String phoneNumber, String email, String applicantName, int type1, String type2, int type3, String permitAddress, java.sql.Date submittedDate) {
        super(ttbID, repid, serial, address, permitNo, phoneNumber, email, applicantName, type1, type2, type3, permitAddress, submittedDate);
    }
}