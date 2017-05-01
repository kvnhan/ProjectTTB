package sample;

import javafx.scene.control.*;
import org.mindrot.jbcrypt.BCrypt;

import java.awt.*;
import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * Set of functions for interacting with the database.
 */
public class DatabaseUtil {

    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String connectionURL = "jdbc:derby:DATABASE/ProjectC;create=true";
    private String ACCOUNT_FIELDS = " (AID, USERNAME, PASSWORDHASH, ISLOGGEDIN, USER_TYPE)";
    private String ALCH_TYPE_FIELDS = " (ATID, CLASS)";
    private String ALCOHOL_FIELDS = " (AID, NAME, APPELLATION, SULFITE_DESC, ALCH_CONTENT, NET_CONTENT, HEALTH_WARNING, PRODUCT_TYPE, CLASS, LABEL_LEGIBILLITY, LABEL_SIZE, FORMULAS, ALCOHOL_TYPE, BOTTLERS_INFO, BRAND_NAME, STATUS, WINE_VINTAGE, PH_LEVEL, GRAPE_VARIETAL, INFO_ON_BOTTLE, SOURCE_OF_PRODUCT, DATE_APPROVED, ORIGIN_CODE)";
    private String CLASS_FIELDS = " (CID, CLASS)";
    private String FORM_FIELDS = " (FID, TTBID, REPID, SERIAL, ADDRESS, PERMITNO, PHONE, EMAIL, APPLICANTNAME, STATUS, AID, APP_TYPE_1, APP_TYPE_2, APP_TYPE_3, PERMIT_ADDRESS, DATE_SUBMITTED)";
    /*private String FORM_FIELDS_WINE = " (FID, TTBID, REPID, SERIAL, ADDRESS, FORMULA, GRAPEVAR, APPELLATION, PERMITNO, INFO_ON_BOTTLE, SOURCE, TYPE, PHONE, EMAIL" +
            ", DATE, APPLICANTNAME, VINTAGE, PH, STATUS, AID, APP_TYPE_1, APP_TYPE_2, APP_TYPE_3)";
    private String FORM_FIELDS_BEER = " (FID, TTBID, REPID, SERIAL, ADDRESS, FORMULA, PERMITNO, INFO_ON_BOTTLE, SOURCE, TYPE, PHONE, " +
            "EMAIL, DATE, APPLICANTNAME, STATUS, AID, APP_TYPE_1, APP_TYPE_2, APP_TYPE_3)";*/
    private String PRODUCT_TYPE_FIELDS = " (PID, TYPE)";
    private String REVIEWS_FIELDS = " (FID, STATUS, DECIDER, DATE, GENERAL, ORIGINCODE, BRANDNAME, FACIFULNAME, GRAPEVAR, WINEVINTAGE, APPELLATION, BOTTLER, FORMULA, SULFITE, LEGIBILITY, LABELSIZE, DESCRIP)";
    private String STATUS_FIELDS = " (SID, STATUS)";
    private String USER_TYPE_FIELDS = " (UID, TYPE)";


    private Connection conn = null;
    private ResultSet rset;
    private Statement stmt = null;
    private ScreenUtil screenUtil;
    private AccountsUtil accountsUtil;

    /**
     * Creates a connection to the database.
     */
    public DatabaseUtil() {
        conn = connect();
        screenUtil = new ScreenUtil();
        accountsUtil = new AccountsUtil();
    }

    /**
     * Used for headless client testing
     * @param accounts
     * @param screen
     */
    @Deprecated
    public DatabaseUtil(AccountsUtil accounts, ScreenUtil screen){
        conn = connect();
        screenUtil = screen;
        accountsUtil = accounts;
    }

    /**
     * Connects a user to the database.
     * @return Returns a Connection object for querying and using the database.
     */
    public static Connection connect() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("Java DB Driver not found. Add the classpath to your module.");
            e.printStackTrace();

        }

        System.out.println("Java DB driver registered!");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(connectionURL);
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return connection;
        }
        System.out.println("Java DB connection established!");

        return connection;
    }

    /**
     * Returns a boolean representing whether a query can be found in teh database.
     * @param query SQL string representing the query to be fed to the database.
     * @return Returns true if the query would have results; false otherwise.
     * @throws SQLException
     */
    private boolean containsQuery(String query) throws SQLException {
        boolean contains = false;

        stmt = conn.createStatement();

        rset = stmt.executeQuery(query);


        return !(rset.next() == false);
    }

    /**
     * Checks if the database contains a string.
     * @param TABLENAME Name of table to search.
     * @param FIELDNAME Name of field to search.
     * @param value String to search for.
     * @return Returns true if the table contains the query.
     * @throws SQLException
     */
    public boolean contains(String TABLENAME, String FIELDNAME, String value) throws SQLException {
        return containsQuery("SELECT * FROM " + TABLENAME + " WHERE " + TABLENAME + "." + FIELDNAME + " = '" + value + "'");
    }

    /**
     * Checks if an int is contained in the database.
     * @param TABLENAME Name of table to search.
     * @param FIELDNAME Name of field to search.
     * @param value Int to search for.
     * @return Returns true if the table contains the query.
     * @throws SQLException
     */
    public boolean containsInt(String TABLENAME, String FIELDNAME, int value) throws SQLException {

        return containsQuery("SELECT * FROM " + TABLENAME + " WHERE " + TABLENAME + "." + FIELDNAME + " = " + value);

    }

    /**
     * checks if a double is in the database.
     * @param TABLENAME Name of table to search.
     * @param FIELDNAME Name of field to search.
     * @param value Double to search for.
     * @return Returns true if the table contains the query.
     * @throws SQLException
     */
    public boolean containsDouble(String TABLENAME, String FIELDNAME, double value) throws SQLException {

        return containsQuery("SELECT * FROM " + TABLENAME + " WHERE " + TABLENAME + "." + FIELDNAME + " = " + value);

    }

    /**
     * Adds an account to the database.
     * @param username Username of the account.
     * @param password Password of the account.
     * @param isLoggedIn Check for if the account is logged in or not.
     * @param userType Type of user.
     * @throws SQLException
     */
    public void addAccount(String username, String password, int isLoggedIn, int userType) throws SQLException {
        String values = "'" + username + "', '" + password + "', " + isLoggedIn + "," + userType + " )";
        addToTable("ACCOUNT", ACCOUNT_FIELDS, values, "AID");
    }

    /**
     * Adds an alcohol type to the database.
     * @param classType Type of alcohol to add.
     * @throws SQLException
     */
    public void addAlcoholType(String classType) throws SQLException {
        String values = "'" + classType + "' )";
        addToTable("ALCH_TYPE", ALCH_TYPE_FIELDS, values, "ATID");
    }

    /**
     * Adds an alcoholic beverage to the database. See the AlcoholData class for information
     * on the specific parameters.
     * @param name
     * @param appellation
     * @param sulfiteDesc
     * @param alcoholContent
     * @param netContent
     * @param healthWarning
     * @param productType
     * @param classType
     * @param labelLegibility
     * @param labelSize
     * @param formulas
     * @param alcoholType
     * @param bottlersInfo
     * @param brandName
     * @throws SQLException
     */
    public int addAlcohol(String name, String appellation, String sulfiteDesc, double alcoholContent, String netContent, String healthWarning, int productType, int classType, String labelLegibility, String labelSize, String formulas, int alcoholType, String bottlersInfo, String brandName, String status, int wineVintage, double phLevel, String grapeVarietal, String infoOnBottle, String sourceOfProduct, java.sql.Date dateApproved, int originCode) throws SQLException {
        System.out.println("ADDING ALCOHOL");
        String values = "'" + name + "', '" + appellation + "', '" + sulfiteDesc + "', " + alcoholContent + ",'" + netContent + "','" + healthWarning + "', " + productType + ", " + classType + ", '" + labelLegibility + "', '" + labelSize + "', '" + formulas + "', " + alcoholType + ", '" + bottlersInfo + "', '" + brandName + "', '" + status + "', " + wineVintage + ", " + phLevel + ", '" + grapeVarietal  + "', '" + infoOnBottle + "', '" + sourceOfProduct + "', " + dateApproved + ", " + originCode + " )";
        return addToTable("ALCOHOL", ALCOHOL_FIELDS, values, "AID");
    }

    /**
     * Adds a class to the database.
     * @param classType Class to add to the database.
     * @throws SQLException
     */
    public void addClass(String classType) throws SQLException {
        String values = "'" + classType + "' )";
        addToTable("CLASS", CLASS_FIELDS, values, "CID");
    }

    /**
     * Adds a form to the database. See ApplicationData class for information on the parameters.
     * @return Returns the TTBID for the added form.
     * @throws SQLException
     */
    public String addForm(String ttbid, int repid, String serial, String address, String permitNo, String phone, String email, String applicantName, String status, int appType1, String appType2, int appType3, String permitAddress, java.sql.Date dateSubmitted) throws SQLException{
        int aid = getAccountAid(AccountsUtil.getUsername());
        String values = "'" + ttbid + "', " + repid + ", '" + serial + "', '" + address + "', '" + permitNo + "', '" + phone + "', '" + email + "', '" + applicantName + "', '" + status + "', " + aid + ", " + appType1 + ", '" + appType2 + "', " + appType3 + ", '" + permitAddress + "', '" + dateSubmitted + "')";
        addToTable("FORM", FORM_FIELDS, values, "FID");
        return ttbid;
    }

    /**
     * Adds a beer form to the database. See BeerApplicationData and ApplicationData
     * for specific information on the parameters passed into the function.
     * @param ttbid
     * @param repid
     * @param serial
     * @param address
     * @param formula
     * @param permit_no
     * @param infoOnBottle
     * @param source_of_product
     * @param type_of_product
     * @param phone_number
     * @param email
     * @param dateFormat
     * @param applicantName
     * @param status
     * @param type1
     * @param type2
     * @param type3
     * @throws SQLException
     */
   /* public String addBeerForm(String ttbid, int repid, String serial, String address, String formula, int permit_no, String infoOnBottle, String source_of_product,
                            String type_of_product, String phone_number, String email, String dateFormat, String applicantName,
                            String status, int type1, String type2, int type3) throws SQLException {

        int aid = getAccountAid(AccountsUtil.getUsername());
        String values = "'" + ttbid + "'," + repid + ",'" + serial + "','" + address + "', '" + formula + "', " + permit_no + ", '" + infoOnBottle + "','" + source_of_product + "', '" + type_of_product + "'" +
                ", '" + phone_number + "', '" + email + "', '" + dateFormat + "', '" + applicantName + "', '" + status + "', " + aid + ", "+type1+", '"+type2+"', "+type3+")";

        addToTable("FORM", FORM_FIELDS_BEER, values, "FID");
        return ttbid;
    }*/

    /**
     * Adds a wine application to the database. See ApplicationData and WineApplicationData
     * for more information on specific parameters.
     * @param ttbid
     * @param repid
     * @param serial
     * @param address
     * @param formula
     * @param grapeVar
     * @param appellation
     * @param permit_no
     * @param infoOnBottle
     * @param source_of_product
     * @param type_of_product
     * @param phone_number
     * @param email
     * @param dateFormat
     * @param applicantName
     * @param vintage
     * @param ph
     * @param status
     * @param type1
     * @param type2
     * @param type3
     * @throws SQLException
     */
   /* public String addWineForm(String ttbid, int repid, String serial, String address, String formula, String grapeVar, String appellation, int permit_no, String infoOnBottle, String source_of_product,
                            String type_of_product, String phone_number, String email, String dateFormat, String applicantName,
                            int vintage, double ph, String status, int type1, String type2, int type3) throws SQLException {
        int aid = getAccountAid(AccountsUtil.getUsername());
        String values = "'" + ttbid + "'," + repid + ",'" + serial + "','" + address + "', '" + formula + "','" + grapeVar + "','" + appellation + "'," + permit_no + ", '" + infoOnBottle + "','" + source_of_product + "', '" + type_of_product + "'" +
                ", '" + phone_number + "', '" + email + "', '" + dateFormat + "', '" + applicantName + "', " + vintage + ", " + ph + ",'" + status + "', " + aid + ", "+type1+", '"+type2+"', "+type3+")";
        addToTable("FORM", FORM_FIELDS_WINE, values, "FID");
        return ttbid;
    }*/

    /*public String addDistilledSpiritsForm(String ttbid, int repid, String serial, String address, String formula, int permit_no, String infoOnBottle, String source_of_product,
                                       String type_of_product, String phone_number, String email, String dateFormat, String applicantName,
                                       String status, int type1, String type2, int type3) throws SQLException {

        int aid = getAccountAid(AccountsUtil.getUsername());
        String values = "'" + ttbid + "'," + repid + ",'" + serial + "','" + address + "', '" + formula + "', " + permit_no + ", '" + infoOnBottle + "','" + source_of_product + "', '" + type_of_product + "'" +
                ", '" + phone_number + "', '" + email + "', '" + dateFormat + "', '" + applicantName + "', '" + status + "', " + aid + ", "+type1+", '"+type2+"', "+type3+")";

        addToTable("FORM", FORM_FIELDS_BEER, values, "FID");
        return ttbid;
    }*/

    /**
     * Adds a product type to the database.
     * @param type Type of product to add.
     * @throws SQLException
     */
    public void addProductType(String type) throws SQLException {
        String values = "'" + type + "' )";
        addToTable("PRODUCT_TYPE", PRODUCT_TYPE_FIELDS, values, "PID");
    }

    /**
     * Adds a review object to the database.
     * This function needs the fid value to be the same as one row in form table.
     * @param fid Form ID number.
     * @param status Application status.
     * @param decider Reviewer of the application.
     * @param date Review date.
     * @param general General information.
     * @param originCode Code of origin location for the application.
     * @param brandName Application brand name.
     * @param facifulName Application fanciful name.
     * @param grapeVar Grape type for wines.
     * @param wineVintage Vintage year for wines.
     * @param appellation Place of origin for wines.
     * @param bottler Bottler name.
     * @param formula Formula for the alcohol.
     * @param sulfite Sulfite content of the alcohol.
     * @param legibility Metric representing legibility of the label.
     * @param labelSize Size of the label.
     * @param description Label description.
     * @throws SQLException
     */
    public void addReview(int fid, int status, int decider, Date date, String general, String originCode, String brandName, String facifulName, String grapeVar, String wineVintage, String appellation, String bottler, String formula, String sulfite, String legibility, String labelSize, String description) throws SQLException {
        String values;

        values = "" + status + ", " + decider + ", '" + date + "', '" + general + "', '" + originCode + "', '" + brandName + "', '" + facifulName + "', '" + grapeVar + "', '" + wineVintage + "', '" + appellation + "', '" + bottler + "', '" + formula + "', '" + sulfite + "', '" + legibility + "', '" + labelSize + "', '" + description + "')";

        addToTable("REVIEWS", REVIEWS_FIELDS, values, "FID", fid);
    }

    /**
     * Adds status type to the database.
     * @param status Type of status to be added.
     * @throws SQLException
     */
    private void addStatus(String status) throws SQLException {
        String values = "'" + status + "' )";
        addToTable("STATUS", STATUS_FIELDS, values, "SID");
    }

    /**
     * Adds type of user to the database.
     * @param type Type of user to be added.
     * @throws SQLException
     */
    public void addUserType(String type) throws SQLException {
        String values = "'" + type + "' )";
        addToTable("USER_TYPE", USER_TYPE_FIELDS, values, "UID");
    }


    /**
     * Clears information from a table in the database.
     * Currently inoperative.
     * @param TABLENAME Name of table to clear.
     * @throws SQLException
     */
    public void clearTable(String TABLENAME) throws SQLException {

        String query = "DELETE FROM " + TABLENAME;
        stmt = conn.createStatement();

        rset = stmt.executeQuery(query);
    }

    /**
     *Automatically gives primary ID  for row inserted by incrementing from previous row's ID in table.
     * @param TABLENAME Name of table to add to.
     * @param FIELDS Fields to change in table.
     * @param values Values to add to the table.
     * @param IDNAME Name of ID to lookup.
     * @return Returns ID assigned to new addition.
     * @throws SQLException
     */
    // Automatically gives primary ID  for row inserted by incrementing from previous row's ID in table
    private int addToTable(String TABLENAME, String FIELDS, String values, String IDNAME) throws SQLException {
        boolean isAdded = false;

        int ID;
        int uRows = 0;
        stmt = conn.createStatement();

        rset = stmt.executeQuery("SELECT * FROM " + TABLENAME + " ORDER BY " + IDNAME + " DESC");

        // checks if table is empty. adds one to previous row's ID for new one if it is not empty. sets ID = 1 if it is
        if (rset.next()) {
            ID = rset.getInt(IDNAME) + 1;
        } else {
            ID = 1;
        }

        uRows = stmt.executeUpdate("INSERT INTO " + TABLENAME + FIELDS + " VALUES (" + ID + ", " + values);

        isAdded = uRows > 0;

        System.out.println(uRows + " Row(s) Updated");

        return ID;
    }

    // (METHOD OVERLOAD) Same method as before but requests input for value of primary ID for the row inserted
    private boolean addToTable(String TABLENAME, String FIELDS, String values, String IDNAME, int ID) throws SQLException {
        boolean isAdded = false;
        int uRows = 0;
        stmt = conn.createStatement();

        rset = stmt.executeQuery("SELECT * FROM " + TABLENAME + " ORDER BY " + IDNAME + " DESC");

        uRows = stmt.executeUpdate("INSERT INTO " + TABLENAME + FIELDS + " VALUES (" + ID + ", " + values);

        isAdded = uRows > 0;

        System.out.println(uRows + " Row(s) Updated");

        return isAdded;
    }

    public int getAccountAid(String username) throws SQLException {
        int AID = 0;

        stmt = conn.createStatement();

        rset = stmt.executeQuery("SELECT * FROM ACCOUNT WHERE ACCOUNT.USERNAME = " + "'" + username + "'");

        while (rset.next()) {
            AID = rset.getInt("AID");
        }

        return AID;
    }

    public ArrayList<Account> searchAccount(String query) throws SQLException { // We should make username a key
        ArrayList<Account> resultAccounts = new ArrayList<>();

        stmt = conn.createStatement();

        rset = stmt.executeQuery(query);

        while (rset.next()) {
            int AID = rset.getInt("AID");
            String username = rset.getString("USERNAME");
            String password = rset.getString("PASSWORDHASH");
            int isLoggedIn = rset.getInt("ISLOGGEDIN");
            int userType = rset.getInt("USER_TYPE");

            Account account = new Account(username, userType);
            resultAccounts.add(account);
        }

        return resultAccounts;

    }

    public ArrayList<Account> searchAccountWithUserType(int userType) throws SQLException {
        String query = "SELECT * FROM ACCOUNT WHERE ACCOUNT.USER_TYPE = " + userType + "";
        return searchAccount(query);
    }

    public ArrayList<Account> searchAccountWithUsername(String username) throws SQLException {
        String query = "SELECT * FROM ACCOUNT WHERE UPPER(ACCOUNT.USERNAME) = UPPER('" + username + "')";
        return searchAccount(query);
    }

    // Code used to search Alcohol table based on alcohol type
    public List<AlcoholData> searchAlcoholWithType(int alcoholType) throws SQLException{
        String query = "SELECT * FROM ALCOHOL WHERE ALCOHOL.ALCOHOL_TYPE = " + alcoholType + " AND STATUS = 'APPROVED'";

        return searchAlcoholTable(query);
    }

    // Code used to search Alcohol table based on brand name. Uses partial search
    public List<AlcoholData> searchAlcoholBrand(String brandName) throws SQLException{
        String query = "SELECT * FROM ALCOHOL WHERE UPPER(ALCOHOL.BRAND_NAME) LIKE UPPER('%" + brandName + "%')  AND STATUS = 'APPROVED'";

        return searchAlcoholTable(query);
    }
    public List<AlcoholData> searchAlcoholWithID(int number) throws SQLException{
        String query = "SELECT * FROM ALCOHOL WHERE ALCOHOL.AID = " + number + " AND STATUS = 'APPROVED'";

        return searchAlcoholTable(query);
    }
    public List<AlcoholData> searchAlcoholID(int number) throws SQLException{
        String query = "SELECT * FROM ALCOHOL WHERE ALCOHOL.AID = " + number;

        return searchAlcoholTable(query);
    }
    public List<AlcoholData> searchAlcoholName(String name) throws SQLException{
        String query = "SELECT * FROM ALCOHOL WHERE UPPER(ALCOHOL.NAME) LIKE UPPER('%" + name + "%')  AND STATUS = 'APPROVED'";

        return searchAlcoholTable(query);
    }
    public List<AlcoholData> searchAlcoholAppellation(String appellation) throws SQLException{
        String query = "SELECT * FROM ALCOHOL WHERE UPPER(ALCOHOL.APPELLATION) LIKE UPPER('%" + appellation + "%') AND STATUS = 'APPROVED'";

        return searchAlcoholTable(query);
    }
    public List<AlcoholData> searchAlcoholContent(double alcCont) throws SQLException{
        String query = "SELECT * FROM ALCOHOL WHERE ALCOHOL.ALCH_CONTENT = " + alcCont + " AND STATUS = 'APPROVED'";

        return searchAlcoholTable(query);
    }

    public List<AlcoholData> searchAlcoholByDate(java.sql.Date date, String BEFORE_OR_AFTER) throws SQLException{
        String query = "";
        if(BEFORE_OR_AFTER.equals("AFTER")){
            query = "SELECT * FROM ALCOHOL WHERE '" + date + "' <= ALCOHOL.DATE_APPROVED  AND STATUS = 'APPROVED'";
        }else if(BEFORE_OR_AFTER.equals("BEFORE")){
            query = "SELECT * FROM ALCOHOL WHERE '" + date + "' >= ALCOHOL.DATE_APPROVED  AND STATUS = 'APPROVED'";
        }

        return searchAlcoholTable(query);
    }

    public List<AlcoholData> searchAlcoholByDate(java.sql.Date startDate, java.sql.Date endDate) throws SQLException{
        String query = "SELECT * FROM ALCOHOL WHERE '" + startDate + "' <= ALCOHOL.DATE_APPROVED AND '" + endDate+ "' >= '2013-01-09'" +  "AND STATUS = 'APPROVED'";

        return searchAlcoholTable(query);
    }

    public List<AlcoholData> getAllAlcoholEntries() throws SQLException{
        String query = "SELECT * FROM ALCOHOL";

        return searchAlcoholTable(query);
    }

    /*
    public List<AlcoholData> searchAllFields(double brandName) throws SQLException{
        int value = Integer.valueOf(brandName);
        String query = "SELECT * FROM ALCOHOL WHERE ALCOHOL.AID = '" + value + "OR ALCOHOL.ALC_CONTENT = '" + brandName;

        return searchAlcoholTable(query);
    }
    */

    private List<AlcoholData> searchAlcoholTable(String query) throws SQLException{
        List<AlcoholData> AlcoholDataList = new ArrayList<AlcoholData>();
        AlcoholData a;

        stmt = conn.createStatement();

        rset = stmt.executeQuery(query);

        while(rset.next()){
            int AID = rset.getInt("AID");
            String name = String.format("%1$"+25+ "s", rset.getString("NAME")).trim();
            String appelation = String.format("%1$"+22+ "s", rset.getString("APPELLATION")).trim();
            String sulfiteDesc = String.format("%1$"+22+ "s", rset.getString("SULFITE_DESC")).trim();
            double alchContent = rset.getDouble("ALCH_CONTENT");
            String netContent = rset.getString("NET_CONTENT");
            String healthWarning = String.format("%1$"+22+ "s", rset.getString("HEALTH_WARNING")).trim();
            int productType = rset.getInt("PRODUCT_TYPE");
            int classType = rset.getInt("CLASS");
            String labelLegibility = String.format("%1$"+22+ "s", rset.getString("LABEL_LEGIBILLITY")).trim();
            String labelSize = rset.getString("LABEL_SIZE");
            String formulas = String.format("%1$"+22+ "s", rset.getString("FORMULAS")).trim();
            int alchType = rset.getInt("ALCOHOL_TYPE");
            String bottlersInfo = String.format("%1$"+22+ "s", rset.getString("BOTTLERS_INFO")).trim();
            String brandname = String.format("%1$"+25+ "s", rset.getString("BRAND_NAME")).trim();
            String status = String.format("%1$"+25+ "s", rset.getString("STATUS")).trim();
            int wineVintage = rset.getInt("WINE_VINTAGE");
            double phLevel = rset.getInt("PH_LEVEL");
            String grapeVarietal = String.format("%1$"+25+ "s", rset.getString("GRAPE_VARIETAL")).trim();
            String infoOnBottle = String.format("%1$"+25+ "s", rset.getString("INFO_ON_BOTTLE")).trim();
            String sourceOfProduct = String.format("%1$"+25+ "s", rset.getString("SOURCE_OF_PRODUCT")).trim();
            java.sql.Date dateApproved = rset.getDate("DATE_APPROVED");
            int originCode = rset.getInt("ORIGIN_CODE");


            a = new AlcoholData(AID, name, appelation, sulfiteDesc, alchContent, netContent, healthWarning, productType, classType, labelLegibility, labelSize, formulas, alchType, bottlersInfo, brandname, status, wineVintage, phLevel, grapeVarietal, infoOnBottle, sourceOfProduct, dateApproved, originCode);
            AlcoholDataList.add(a);
        }

        return AlcoholDataList;

    }
    /*
    public List<ApplicationData> getWineForm(String name, String s)throws SQLException{
        List<ApplicationData> appData = new ArrayList<>();
        ApplicationData a;
        PreparedStatement pm;
        if(s.equals("NONE")){
            String sql = "SELECT * FROM APP.FORM WHERE APPLICANTNAME = ?";
            pm = conn.prepareStatement(sql);
            pm.setString(1, name);
        }else {
            String sql = "SELECT * FROM APP.FORM WHERE APPLICANTNAME = ? AND STATUS = ?";
            pm = conn.prepareStatement(sql);
            pm.setString(1, name);
            pm.setString(2, s);
        }
        rset = pm.executeQuery();

        while(rset.next()){
            int fid = rset.getInt("FID");
            int ttbid = rset.getInt("TTBID");
            int repid = rset.getInt("REPID");
            String serial = rset.getString("SERIAL");
            String address = rset.getString("ADDRESS");
            String fancyName = rset.getString("FANCYNAME");
            String formula =  rset.getString("FORMULA");
            String grape_varietal = rset.getString("GRAPEVAR");
            String appellation = rset.getString("APPELLATION");
            int permit_no = rset.getInt("PERMITNO");
            String infoOnBottle = rset.getString("INFO_ON_BOTTLE");
            String source_of_product = rset.getString("SOURCE");
            String type_of_product = rset.getString("TYPE");
            String brand_name = rset.getString("BRANDNAME");
            String phone_number = rset.getString("PHONE");
            String email = rset.getString("EMAIL");
            String applicantName = rset.getString("APPLICANTNAME");
            String alcoholType = rset.getString("ALCOHOLTYPE");
            String alcoholContent = "";
            String date = rset.getString("DATE");
            int vintage_date = rset.getInt("VINTAGE");
            double ph_level = rset.getDouble("PH");
            String status = rset.getString("STATUS");
            AcceptanceInformation info = new AcceptanceInformation(null, applicantName, null, status);

            a = new WineApplicationData(fid, info,ttbid, repid, serial,address,
                    fancyName, formula, grape_varietal, appellation, permit_no, infoOnBottle,
                    source_of_product, type_of_product, brand_name, phone_number, email, null, applicantName,
                    alcoholType, alcoholContent, vintage_date, ph_level);
            appData.add(a);
        }

        return appData;
    }
    */

    public ArrayList<ApplicationData> searchFormWithGovId(int GOVID) throws SQLException{
        String query = "SELECT * FROM FORM WHERE FORM.GOVID = " + GOVID + " AND (FORM.STATUS = 'ASSIGNED' OR FORM.STATUS = 'UNASSIGNED')";

        return searchForm(query);
    }

    public ArrayList<ApplicationData> searchFormWithAid(int AID) throws SQLException{
        String query = "SELECT * FROM FORM WHERE FORM.AID = " + AID;

        return searchForm(query);
    }

    public ApplicationData searchFormWithRepID(int rep) throws SQLException{
        stmt = conn.createStatement();
        String query = "SELECT * FROM FORM WHERE FORM.REPID = " + rep;
        rset = stmt.executeQuery(query);
        String ttbid = "";
        String serial = "";
        String address = "";
        String permit_no = "";
        String phone_number = "";
        String email = "";
        String applicantName = "";
        int type1 = 0;
        String type2 = "";
        int type3 = 0;
        String permitAddress = "";
        java.sql.Date dateSubmitted = null;
        ApplicationData a = new ApplicationData(ttbid, rep, serial, address, permit_no, phone_number, email, applicantName, type1, type2, type3, permitAddress, dateSubmitted);
        while(rset.next()) {
            ttbid = rset.getString("TTBID");
            serial = rset.getString("SERIAL");
            address = rset.getString("ADDRESS");
            permit_no = rset.getString("PERMITNO");
            phone_number = rset.getString("PHONE");
            email = rset.getString("EMAIL");
            applicantName = rset.getString("APPLICANTNAME");
            type1 = rset.getInt("APP_TYPE_1");
            type2 = rset.getString("APP_TYPE_2");
            type3 = rset.getInt("APP_TYPE_3");
            permitAddress = rset.getString("PERMIT_ADDRESS");
            dateSubmitted = rset.getDate("DATE_SUBMITTED");


            a = new ApplicationData(ttbid, rep, serial, address, permit_no, phone_number, email, applicantName, type1, type2, type3,permitAddress,dateSubmitted);
            return a;
        }
        return a;
    }

    public ArrayList<ApplicationData> searchFormWithTTBID(String ttbid) throws SQLException{
        String query = "SELECT * FROM FORM WHERE FORM.TTBID = '" + ttbid + "'";

        return searchForm(query);
    }

    public ArrayList<ApplicationData> searchForm(String query) throws SQLException{
        ArrayList<ApplicationData> AppDataList = new ArrayList<>();
        ApplicationData a;

        stmt = conn.createStatement();

        rset = stmt.executeQuery(query);

        while(rset.next()){
            String ttbid = rset.getString("TTBID");
            int repid = rset.getInt("REPID");
            String serial = rset.getString("SERIAL");
            String address = rset.getString("ADDRESS");
            String permit_no = rset.getString("PERMITNO");
            String phone_number = rset.getString("PHONE");
            String email = rset.getString("EMAIL");
            String applicantName = rset.getString("APPLICANTNAME");
            int type1 = rset.getInt("APP_TYPE_1");
            String type2 = rset.getString("APP_TYPE_2");
            int type3 = rset.getInt("APP_TYPE_3");
            String permitAddress = rset.getString("PERMIT_ADDRESS");
            java.sql.Date dateSubmitted = rset.getDate("DATE_SUBMITTED");

            a = new ApplicationData(ttbid, repid, serial, address, permit_no, phone_number, email, applicantName, type1, type2, type3, permitAddress, dateSubmitted);
            AppDataList.add(a);
        }


        return AppDataList;
    }

    /*

    public List<ApplicationData> getBeerForm(String name, String s)throws SQLException{
        List<ApplicationData> appData = new ArrayList<>();
        ApplicationData a;
        PreparedStatement sm;
        if(s.equals("NONE")){
            String sql = "SELECT * FROM APP.FORM WHERE APPLICANTNAME = ?";
            sm = conn.prepareStatement(sql);
            sm.setString(1, name);
        }else {
            String sql = "SELECT * FROM APP.FORM WHERE APPLICANTNAME = ? AND STATUS = ?";
            sm = conn.prepareStatement(sql);
            sm.setString(1, name);
            sm.setString(2, s);
        }

        rset = sm.executeQuery();

        while(rset.next()){
            int fid = rset.getInt("FID");
            int ttbid = rset.getInt("TTBID");
            int repid = rset.getInt("REPID");
            String serial = rset.getString("SERIAL");
            String address = rset.getString("ADDRESS");
            String fancyName = rset.getString("FANCYNAME");
            String formula = rset.getString("FORMULA");
            int permit_no = rset.getInt("PERMITNO");
            String infoOnBottle = rset.getString("INFO_ON_BOTTLE");
            String source_of_product = rset.getString("SOURCE");
            String type_of_product = rset.getString("TYPE");
            String brand_name = rset.getString("BRANDNAME");
            String phone_number = rset.getString("PHONE");
            String email = rset.getString("EMAIL");
            String applicantName = rset.getString("APPLICANTNAME");
            String alcoholType = rset.getString("ALCOHOLTYPE");
            String alcoholContent = "";
            String date = rset.getString("DATE");
            String status = rset.getString("STATUS");
            AcceptanceInformation info = new AcceptanceInformation(null, applicantName, null, status);

            a = new BeerApplicationData(fid, info,ttbid, repid, serial,address,
                    fancyName, formula, permit_no, infoOnBottle,
                    source_of_product, type_of_product, brand_name, phone_number, email, null, applicantName,
                    alcoholType, alcoholContent);
            appData.add(a);
        }

        return appData;
    }
    */
    // Change status after government agents finish reviewing an application
    public void changeFormStatus(String newStatus, String ttbid) throws SQLException{
        String query = "UPDATE FORM SET STATUS = ? WHERE TTBID = ?";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, newStatus);
        pstmt.setString(2, ttbid);
        pstmt.executeUpdate();
    }

    public void updateAlcoholIDForForm(int aid, String ttbid) throws SQLException{
        String query = "UPDATE FORM SET ALCHID = ? WHERE TTBID = ?";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, aid);
        pstmt.setString(2, ttbid);
        pstmt.executeUpdate();
    }

    public void changeAlcoholStatus(String newStatus, int alcoholID) throws SQLException{
        java.sql.Date currentDate = new java.sql.Date((new Date()).getTime());

        String updateStatusQuery = "UPDATE ALCOHOL SET STATUS = ? WHERE AID = ?";

        PreparedStatement pstmt = conn.prepareStatement(updateStatusQuery);
        pstmt.setString(1, newStatus);
        pstmt.setInt(2, alcoholID);
        pstmt.executeUpdate();

        String updateDateQuery = "UPDATE ALCOHOL SET DATE_APPROVED = ? WHERE AID = ?";

        pstmt = conn.prepareStatement(updateDateQuery);
        pstmt.setDate(1, currentDate);
        pstmt.setInt(2, alcoholID);
        pstmt.executeUpdate();
    }

    public void decideApplicationAction(String status, ApplicationData thisForm, javafx.scene.control.TextArea commentsField) throws  SQLException{
        // change alcohol status to approved
        changeAlcoholStatus("APPROVED", thisForm.getAssociatedAlchID());
        changeFormStatus(status.toUpperCase(), thisForm.getTtbID());


        //AlcoholData associatedAlcohol = thisForm.getAssociatedAlcoholData();

        //String ReviewerUsername = AccountsUtil.getUsername();

        //int GOVID = getAccountAid(ReviewerUsername);

        //get comments
        //String comments = commentsField.getText();

/*
        int statusInInteger;

        if(status.toUpperCase().equals("INCOMPLETE")){
            statusInInteger = 1;
        }else if(status.toUpperCase().equals("ACCEPTED")){
            statusInInteger = 2;
        }else if(status.toUpperCase().equals("REJECTED")){
            statusInInteger = 3;
        }else if(status.toUpperCase().equals("ASSIGNED")){
            statusInInteger = 4;
        }else{
            statusInInteger = 5;
        }

        String date = thisForm.getDate();
        String general = comments;
        String originCode = thisForm.getSource_of_product();
        String brandName = associatedAlcohol.getBrandName();
        String fancifulName = associatedAlcohol.getName();
        String grapevar;
        String wineVintage;
        String appellation;


        int alcoholType = associatedAlcohol.getAlcoholType();

        if(alcoholType == 2) {
            grapevar = thisForm.getGrapevar();
            wineVintage = thisForm.getVintage();
            appellation = thisForm.getAppellation();
        }
        else{
            grapevar = "This type of alcohol does not have a varietal.";
            wineVintage = "This type of alcohol does not have a vintage.";
            appellation = "This type of alcohol does not have an appellation.";
        }
        String bottler = thisForm.getBottler();
        String formula = thisForm.getFormula();
        String sulfite = thisForm.getSulfite();
        String legibility = "0";
        String labelSize = "0";
        String descrip = thisForm.getInfoOnBottle();

        //add review

       *//* addReview(FID, statusInInteger, GOVID, date, general, originCode, brandName, fancifulName, grapevar, wineVintage, appellation, bottler, formula, sulfite, legibility, labelSize, descrip);
*/
    }
    /*
        public BeerApplicationData fillSubmittedBeerForm(String ttbid) throws SQLException{

            String sql = "SELECT * FROM APP.FORM WHERE TTBID = ?";
            BeerApplicationData a;
            PreparedStatement sm;
            sm = conn.prepareStatement(sql);
            sm.setString(1, ttbid);
            rset = sm.executeQuery();

            int repid = 0;
            String serial = "";
            String address = "";
            String fancyName = "";
            String formula = "";
            int permit_no = 0;
            String infoOnBottle = "";
            String source_of_product = "";
            String type_of_product = "";
            String brand_name = "";
            String phone_number = "";
            String email = "";
            String applicantName = "";
            String alcoholType = "";
            String alcoholContent = "";
            String date = "";
            String status = "";
            int type1 = 0;
            String type2 = "";
            int type3 = 0;

            while(rset.next()){
                repid = rset.getInt("REPID");
                serial = rset.getString("SERIAL");
                address = rset.getString("ADDRESS");
                //fancyName = rset.getString("FANCYNAME");
                formula = rset.getString("FORMULA");
                permit_no = rset.getInt("PERMITNO");
                infoOnBottle = rset.getString("INFO_ON_BOTTLE");
                source_of_product = rset.getString("SOURCE");
                type_of_product = rset.getString("TYPE");
                //brand_name = rset.getString("BRANDNAME");
                phone_number = rset.getString("PHONE");
                email = rset.getString("EMAIL");
                applicantName = rset.getString("APPLICANTNAME");
                //alcoholType = rset.getString("ALCOHOLTYPE");
                //alcoholContent = Double.toString(rset.getDouble("ALCOHOL_CONTENT"));
                type1 = rset.getInt("APP_TYPE_1");
                type2 = rset.getString("APP_TYPE_2");
                type3 = rset.getInt("APP_TYPE_3");
                //date = rset.getString("DATE");
                status = rset.getString("STATUS");
                info = new AcceptanceInformation(null, applicantName, null, status);

            }

            a = new BeerApplicationData(ttbid, info, repid, serial,address,
                    formula, permit_no, infoOnBottle,
                    source_of_product, type_of_product, phone_number, email, null, applicantName,
                    type1, type2, type3);

            return a;

        }
        */
    public ApplicationData fillSubmittedForm(String ttbid) throws SQLException{

        String sql = "SELECT * FROM APP.FORM WHERE TTBID = ?";
        ApplicationData a;
        PreparedStatement sm;
        sm = conn.prepareStatement(sql);
        sm.setString(1, ttbid);
        rset = sm.executeQuery();

        int repid = 0;
        String serial = "";
        String address = "";
        String permit_no = "";
        String phone_number = "";
        String email = "";
        String applicantName = "";
        Date date;
        java.sql.Date d = null;
        String status = "";
        int type1 = 0;
        String type2 = "";
        int type3 = 0;
        String permitAddress = "";


        while(rset.next()){
            ttbid = rset.getString("TTBID");
            repid = rset.getInt("REPID");
            serial = rset.getString("SERIAL");
            address = rset.getString("ADDRESS");
            permit_no = rset.getString("PERMITNO");
            phone_number = rset.getString("PHONE");
            email = rset.getString("EMAIL");
            applicantName = rset.getString("APPLICANTNAME");
            type1 = rset.getInt("APP_TYPE_1");
            type2 = rset.getString("APP_TYPE_2");
            type3 = rset.getInt("APP_TYPE_3");
            permitAddress = rset.getString("PERMIT_ADDRESS");
            d = rset.getDate("DATE_SUBMITTED");

        }

        date = d;

        a = new ApplicationData(ttbid, repid, serial, address, permit_no, phone_number, email, applicantName, type1, type2, type3, permitAddress, d);

        return a;
    }

    public AlcoholData getAlcohoforBeer(String ttbid) throws SQLException{

        String sql = "SELECT * FROM APP.ALCOHOL WHERE AID = ?";
        int aid = getAidOfForm(ttbid);
        AlcoholData a;
        PreparedStatement sm;
        sm = conn.prepareStatement(sql);
        sm.setInt(1, aid);
        rset = sm.executeQuery();

        String fancyName = "";
        String formula = "";
        String source_of_product = "";
        String brand_name = "";
        int  alcoholType = 0;
        double alcoholContent = 0;
        int origin = 0;
        String net = "";




        while(rset.next()){
            fancyName = rset.getString("NAME");
            formula = rset.getString("FORMULAS");
            source_of_product = rset.getString("SOURCE_OF_PRODUCT");
            brand_name = rset.getString("BRAND_NAME");
            alcoholContent = rset.getDouble("ALCH_CONTENT");
            alcoholType = rset.getInt("ALCOHOL_TYPE");
            origin = rset.getInt("ORIGIN_CODE");
            net = rset.getString("NET_CONTENT");


        }

        a = new AlcoholData(aid, fancyName, "", "", alcoholContent, net, "", 0, 0, "", "",
                formula, alcoholType, "", brand_name, "", 0, 0, "", "", source_of_product, null, origin);

        return a;
    }

    public AlcoholData getAlcohoforWine(String ttbid) throws SQLException{

        String sql = "SELECT * FROM APP.ALCOHOL WHERE AID = ?";
        int aid = getAidOfForm(ttbid);
        AlcoholData a;
        PreparedStatement sm;
        sm = conn.prepareStatement(sql);
        sm.setInt(1, aid);
        rset = sm.executeQuery();

        String fancyName = "";
        String formula = "";
        String source_of_product = "";
        String brand_name = "";
        int  alcoholType = 0;
        double alcoholContent = 0;
        int origin = 0;
        String grape_varietal = "";
        String appellation = "";
        int vintage_date = 0;
        double ph_level = 0;
        String net = "";


        while(rset.next()){
            fancyName = rset.getString("NAME");
            formula = rset.getString("FORMULAS");
            source_of_product = rset.getString("SOURCE_OF_PRODUCT");
            brand_name = rset.getString("BRAND_NAME");
            alcoholContent = rset.getDouble("ALCH_CONTENT");
            alcoholType = rset.getInt("ALCOHOL_TYPE");
            grape_varietal = rset.getString("GRAPE_VARIETAL");
            appellation = rset.getString("APPELLATION");
            vintage_date = rset.getInt("WINE_VINTAGE");
            ph_level = rset.getDouble("PH_LEVEL");
            origin = rset.getInt("ORIGIN_CODE");
            net = rset.getString("NET_CONTENT");

        }

        a = new AlcoholData(aid, fancyName, appellation, "", alcoholContent, net, "", 0, 0, "", "",
                formula, alcoholType, "", brand_name, "", vintage_date, ph_level, grape_varietal, "", source_of_product, null, origin);

        return a;
    }

    public void reviseAlcohol(String ttbid) throws SQLException{}

    /**
     * Gets a list of all Applications that have the status "UNASSIGNED".
     *
     * @return Returns an ArrayList of the IDs of the unassigned Applications. The IDs are represented
     * by Strings.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ArrayList<ApplicationData> searchUnassignedForms() throws SQLException{
        String query = "SELECT * FROM FORM WHERE UPPER(FORM.STATUS) = 'UNASSIGNED'";

        return searchForm(query);
    }

    public ArrayList<ApplicationData> searchFormWithStatus(String STATUS) throws SQLException{
        String query = "SELECT * FROM FORM WHERE UPPER(FORM.STATUS) = UPPER('" + STATUS + "')";

        return searchForm(query);
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
    public int searchMinWorkLoad() throws SQLException{//TODO: find out fields + name for govt. worker\
        stmt = conn.createStatement();

        int GOVID = 0;

        // CHECK IF ALL GOVERNMENT OFFICIALS ARE ASSIGNED TO A FORM, IF NOT ASSIGNS FORMS TO ONES WHICH DONT HAVE A FORM
        for(Account account: searchAccountWithUserType(1)){
            if(!containsInt("FORM", "GOVID", getAccountAid(account.getUsername()))){
                GOVID = getAccountAid(account.getUsername());
                return GOVID;
            }
        }
        //NEED TO FIGURE OUT HOW TO FIND THE ACCOUNT WITH THE MINIMUM AMOUNT OF TIMES THE FORMS REFERENCE IT
        String query = "SELECT GOVID, SUM(CNT) AS CNT\n" +
                "FROM\n" +
                "  (SELECT  FORM.GOVID, COUNT(GOVID) AS CNT\n" +
                "  FROM FORM\n" +
                "  WHERE STATUS ='ASSIGNED'\n" +
                "  GROUP BY  GOVID) T\n" +
                "GROUP BY GOVID\n" +
                "ORDER BY CNT ASC";

        rset = stmt.executeQuery(query);

        //Should give asc db of govid of government works id and the number of forms they have assigned to themrset = stm.executeQuery(sql);

       /* int occ;
        while(rset.next()){
            GOVID = rset.getInt("GOVID");
            occ = rset.getInt("CNT");

            System.out.println("Gov ID: " + GOVID + "Occurences: " + occ);
        }
*/
        if(rset.next()){
            while ((rset.getInt("GOVID") == 0)){
                rset.next();
            }
            GOVID = rset.getInt("GOVID");
        }else{
            GOVID = 0;
            screenUtil.createAlertBox("Add a Government Official account to the system",
                    "There are no government officials registered on the system. Therefore you cannot add a form because no one will review them.");
        }

        return GOVID;
    }

    /**
     * Assigns a form to a government worker.
     * @param govid ID to assign application to.
     * @param unAssignedForm Unassigned application.
     * @throws SQLException
     */
    public void assignForm(int govid, ApplicationData unAssignedForm) throws SQLException{
        int resultCount;

        stmt = conn.createStatement();

        //update alcohol status
        String query = "UPDATE FORM SET FORM.STATUS = 'ASSIGNED', FORM.GOVID = " + govid + " WHERE TTBID = '"+ unAssignedForm.getTtbID()+ "'";


        resultCount = stmt.executeUpdate(query);

    }

    /**
     * Checks the type of an alcohol application.
     * @param ttbid Application to check.
     * @return Returns either "WINE", "BEER", or "OTHER".
     * @throws SQLException
     */
    public String checkforType(String ttbid) throws SQLException {
        int type = 0;
        int aid = getAidOfForm(ttbid);
        String sql = "SELECT * FROM APP.ALCOHOL WHERE AID = ?";
        PreparedStatement sm;
        sm = conn.prepareStatement(sql);
        sm.setInt(1, aid);
        rset = sm.executeQuery();
        while (rset.next()) {
            type = rset.getInt("ALCOHOL_TYPE");
            if (type == 2) {
                return "WINE";
            }
            if (type == 1) {
                return "BEER";
            }
        }
        return "OTHER";
    }

    /**
     * Checks whether an application is for an imported or a domestic product.
     * @param ttbid TTBID of application to check.
     * @return Returns String - Either "DOMESTIC" or "IMPORTED".
     * @throws SQLException
     */
    public String checkforSource(String ttbid) throws SQLException {
        String type = "";
        int aid = getAidOfForm(ttbid);
        String sql = "SELECT * FROM APP.ALCOHOL WHERE AID = ?";
        PreparedStatement sm;
        sm = conn.prepareStatement(sql);
        sm.setInt(1, aid);
        rset = sm.executeQuery();
        while (rset.next()) {
            type = rset.getString("SOURCE_OF_PRODUCT");
            if (type.equals("DOMESTIC")) {
                return "DOMESTIC";
            }
        }
        return "IMPORTED";
    }

    /**
     * Checks if application is a normal application.
     * @param ttbid TTBID of application to check.
     * @return Returns 1 if not normal.
     * @throws SQLException
     */
    public int checkforType1(String ttbid) throws SQLException {
        String sql = "SELECT * FROM APP.FORM WHERE TTBID = ?";
        PreparedStatement sm;
        int empty = 1;
        int notempty = 0;
        sm = conn.prepareStatement(sql);
        sm.setString(1, ttbid);
        rset = sm.executeQuery();
        while (rset.next()) {
            int type = rset.getInt("APP_TYPE_1");
            if(type == -1) {
                return empty;
            }
        }
        return notempty;
    }

    /**
     * Checks if application is only for sale in a certain state.
     * @param ttbid TTBID of application to check.
     * @return Returns 1 if not for sale only in a certain state.
     * @throws SQLException
     */
    public int checkforType2(String ttbid) throws SQLException {
        String sql = "SELECT * FROM APP.FORM WHERE TTBID = ?";
        PreparedStatement sm;
        int empty = 1;
        int notempty = 0;
        sm = conn.prepareStatement(sql);
        sm.setString(1, ttbid);
        rset = sm.executeQuery();
        while (rset.next()) {
            String type = rset.getString("APP_TYPE_2");
            if(type.isEmpty()) {
                return empty;
            }
        }
        return notempty;
    }

    /**
     * Checks if an application is of type 3.
     * @param ttbid TTBID of application to check.
     * @return Returns 1 if not of type 3.
     * @throws SQLException
     */
    public int checkforType3(String ttbid) throws SQLException {
        String sql = "SELECT * FROM APP.FORM WHERE TTBID = ?";
        PreparedStatement sm;
        int empty = 1;
        int notempty = 0;
        sm = conn.prepareStatement(sql);
        sm.setString(1, ttbid);
        rset = sm.executeQuery();
        while (rset.next()) {
            int type = rset.getInt("APP_TYPE_3");
            if(type == -1) {
                return empty;
            }
        }
        return notempty;
    }

    /**
     * Gets the status of an application by the TTBID.
     * @param ttbid TTBID to get status from.
     * @return String representing application status.
     * @throws SQLException
     */
    public String getStatus(String ttbid) throws SQLException {
        String sql = "SELECT * FROM APP.FORM WHERE TTBID = ?";
        PreparedStatement sm;
        String status = "";
        sm = conn.prepareStatement(sql);
        sm.setString(1, ttbid);
        rset = sm.executeQuery();
        if (rset.next()) {
            status = rset.getString("STATUS");
            return status;
        }
        return status;
    }

    /**
     * Returns AID associated with a form.
     * @param ttbid TTBID of the form.
     * @return Returns the alcohol ID.
     * @throws SQLException
     */
    public int getAidOfForm(String ttbid) throws SQLException {
        String sql = "SELECT * FROM APP.FORM WHERE TTBID = ?";
        PreparedStatement sm;
        int aid = 0;
        sm = conn.prepareStatement(sql);
        sm.setString(1, ttbid);
        rset = sm.executeQuery();
        if (rset.next()) {
            aid = rset.getInt("ALCHID");
            return aid;
        }
        return aid;
    }

    /**
     * Updates a form submission.
     * @param applicationData Data to update for the application.
     * @param alcoholData Data to update for the alcohol.
     * @return Returns integer representing successful update.
     * @throws SQLException
     */
    public int updateFormSubmission(ApplicationData applicationData, AlcoholData alcoholData) throws SQLException{
        String formStatus = "UNASSIGNED";
        String alcoholStatus = "PROCESSING";
        java.sql.Date currentDate = new java.sql.Date((new Date()).getTime());
        int aid = getAidOfForm(applicationData.getTtbID());

        String formSQL = "UPDATE APP.FORM SET REPID = ?, SERIAL = ?, ADDRESS = ?," +
                "PERMITNO = ?, PHONE = ?, EMAIL = ?, APPLICANTNAME = ?, STATUS = ?, APP_TYPE_1 = ?, APP_TYPE_2 = ?, APP_TYPE_3 = ?, DATE_SUBMITTED = ? , PERMIT_ADDRESS = ? WHERE TTBID = ?";

        PreparedStatement sm;
        sm = conn.prepareStatement(formSQL);
        sm.setInt(1, applicationData.getRepid());
        sm.setString(2, applicationData.getSerial());
        sm.setString(3, applicationData.getAddress());
        sm.setString(4, applicationData.getPermitNo());
        sm.setString(5, applicationData.getPhoneNumber());
        sm.setString(6, applicationData.getEmail());
        sm.setString(7, applicationData.getApplicantName());
        sm.setString(8, formStatus);
        sm.setInt(9, applicationData.getType1());
        sm.setString(10, applicationData.getType2());
        sm.setInt(11, applicationData.getType3());
        sm.setDate(12, currentDate);
        sm.setString(13, applicationData.getPermitAddress());
        sm.setString(14, applicationData.getTtbID());

        sm.executeUpdate();

        String alcoholSQL = "UPDATE APP.ALCOHOL SET NAME = ?, APPELLATION = ?, SULFITE_DESC = ?," +
                "ALCH_CONTENT = ?, NET_CONTENT = ?, HEALTH_WARNING = ?, PRODUCT_TYPE = ?, CLASS = ?, LABEL_LEGIBILLITY = ?, " +
                "LABEL_SIZE = ?, FORMULAS = ?, ALCOHOL_TYPE = ?, BOTTLERS_INFO = ?, BRAND_NAME = ?, STATUS = ?, WINE_VINTAGE = ?, " +
                "PH_LEVEL = ?, GRAPE_VARIETAL = ?, INFO_ON_BOTTLE = ?, SOURCE_OF_PRODUCT = ?, DATE_APPROVED = ?, ORIGIN_CODE = ? WHERE AID = ?";

        sm = conn.prepareStatement(alcoholSQL);
        sm.setString(1, alcoholData.getName());
        sm.setString(2, alcoholData.getAppellation());
        sm.setString(3, alcoholData.getSulfiteDescription());
        sm.setDouble(4, alcoholData.getAlchContent());
        sm.setString(5, alcoholData.getNetContent());
        sm.setString(6, alcoholData.getHealthWarning());
        sm.setInt(7, alcoholData.getProductType());
        sm.setInt(8, alcoholData.getClassType());
        sm.setString(9, alcoholData.getLabelLegibility());
        sm.setString(10, alcoholData.getLabelSize());
        sm.setString(11, alcoholData.getFormulas());
        sm.setInt(12, alcoholData.getAlcoholType());
        sm.setString(13, alcoholData.getBottlersInfo());
        sm.setString(14, alcoholData.getBrandName());
        sm.setString(15, alcoholStatus);
        sm.setInt(16, alcoholData.getWineVintage());
        sm.setDouble(17, alcoholData.getPhLevel());
        sm.setString(18, alcoholData.getGrapeVarietal());
        sm.setString(19, alcoholData.getInfoOnBottle());
        sm.setString(20, alcoholData.getSourceOfProduct());
        sm.setDate(21, null);
        sm.setInt(22, alcoholData.getOriginCode());
        sm.setInt(23, aid);

        sm.executeUpdate();


        return aid;

    }

    /*public void resubmitWine(WineApplicationData a) throws SQLException{

        String status = "UNASSIGNED";
        Date date = new Date();
        String dateFormat = date.toString();
        String sql = "UPDATE APP.FORM SET REPID = ?, SERIAL = ?, ADDRESS = ?," +
                "FORMULA = ?, GRAPEVAR = ?, APPELLATION = ?, PERMITNO = ?, SOURCE = ?, TYPE = ?," +
                "PHONE = ?, EMAIL = ?, DATE_SUBMITTED = ?, APPLICANTNAME = ?, VINTAGE = ?, PH = ?, STATUS = ? WHERE TTBID = ?";

        PreparedStatement sm;
        sm = conn.prepareStatement(sql);
        sm.setInt(1, a.getRepid());
        sm.setString(2, a.getSerial());
        sm.setString(3, a.getAddress());
        sm.setString(4, a.getFormula());
        sm.setString(5, a.getGrape_varietal());
        sm.setString(6, a.getAppellation());
        sm.setInt(7, a.getPermit_no());
        sm.setString(8, a.getSource_of_product());
        sm.setString(9, a.getType_of_product());
        sm.setString(10, a.getPhone_number());
        sm.setString(11, a.getEmail());
        sm.setString(12, dateFormat);
        sm.setString(13, a.getApplicantName());
        sm.setInt(14, a.getVintage_date());
        sm.setDouble(15, a.getPh_level());
        sm.setString(16, status);
        sm.setString(17, a.getTtbID());

        sm.executeUpdate();
    }

    public void resubmitBeer(BeerApplicationData a) throws SQLException{

        String status = "UNASSIGNED";
        Date date = new Date();
        String dateFormat = date.toString();
        String sql = "UPDATE APP.FORM SET REPID = ?, SERIAL = ?, ADDRESS = ?," +
                "FORMULA = ?,PERMITNO = ?, SOURCE = ?, TYPE = ?," +
                "PHONE = ?, EMAIL = ?, DATE = ?, APPLICANTNAME = ?, STATUS = ? WHERE TTBID = ?";

        PreparedStatement sm;
        sm = conn.prepareStatement(sql);
        sm.setInt(1, a.getRepid());
        sm.setString(2, a.getSerial());
        sm.setString(3, a.getAddress());
        sm.setString(4, a.getFormula());
        sm.setInt(5, a.getPermit_no());
        sm.setString(6, a.getSource_of_product());
        sm.setString(7, a.getType_of_product());
        sm.setString(8, a.getPhone_number());
        sm.setString(9, a.getEmail());
        sm.setString(10, dateFormat);
        sm.setString(11, a.getApplicantName());
        sm.setString(12, status);
        sm.setString(13, a.getTtbID());

        sm.executeUpdate();
    }*/

    /**
     * Creates a new TTBID
     * @return String representation of the TTBID
     * @throws SQLException
     */
    synchronized public String getNewTTBID() throws SQLException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat year = new SimpleDateFormat("yyyy");
        DateFormat day = new SimpleDateFormat("D");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        PreparedStatement addDate = null;
        PreparedStatement incrementDate = null;
        PreparedStatement checkDate = null;
        ResultSet rs;
        String ttbid = "";
        int id = 0;

        checkDate = conn.prepareStatement("SELECT * FROM TTBID WHERE CURRENTDATE = ?");
        checkDate.setString(1,curDate);
        rs = checkDate.executeQuery();


        if(rs.next() == false){
            addDate = conn.prepareStatement("INSERT INTO TTBID(CURRENTDATE) VALUES(?)");
            addDate.setString(1,curDate);
            addDate.executeUpdate();

        }
        else{
            id = rs.getInt("CURRENTCOUNT");
        }

        incrementDate = conn.prepareStatement("UPDATE TTBID SET CURRENTCOUNT = ? WHERE CURRENTDATE = ?");
        incrementDate.setInt(1,id+1);
        incrementDate.setString(2, curDate);
        incrementDate.executeUpdate();


        ttbid += year.format(date).substring(2,4);

        if(day.format(date).length() < 3){
            ttbid += "0" + day.format(date);
        }
        else{
            ttbid += day.format(date);
        }
        ttbid += "001";

        for(int i = 0; i < 6 - (id+"").length(); i++) { //Gets length of id and determines number of spaces needed to added
            ttbid += "0";
        }
        ttbid +=  id+"";

        checkDate.close();
        incrementDate.close();

        if(addDate != null){
            addDate.close();
        }

        return ttbid;
    }

    /**
     * Returns a list of TItem Accounts with children of the forms assigned to them
     * @return
     * @throws SQLException
     */
    public ArrayList<TreeItem<TItem>> getAccountItems() throws SQLException {
        ArrayList<TreeItem<TItem>> list = new ArrayList<>();
        PreparedStatement getAccs = conn.prepareStatement("SELECT AID, USERNAME, USER_TYPE FROM ACCOUNT WHERE USER_TYPE = 1");

        ResultSet rs =  getAccs.executeQuery();

        while(rs.next()){
            int aid = rs.getInt("AID");
            TreeItem<TItem> item = new TreeItem<TItem>(new AccountItem(aid,rs.getString("USERNAME")));
            item.getChildren().addAll(getFormItems(aid));
            list.add(item);
        }

        rs.close();
        getAccs.close();

        return list;
    }

    /**
     * Returns a list of TItem forms that are assigned to government worker with aid
     * @param aid
     * @return
     * @throws SQLException
     */
    public ArrayList<TreeItem<TItem>> getFormItems(int aid) throws SQLException {
        ArrayList<TreeItem<TItem>> list = new ArrayList<>();
        PreparedStatement getForms = conn.prepareStatement("SELECT ALCHID, TTBID FROM FORM WHERE GOVID = ?");
        getForms.setInt(1,aid);

        ResultSet rs =  getForms.executeQuery();

        while(rs.next()){
            AlcoholData currentDate = searchAlcoholID(rs.getInt("ALCHID")).get(0);
            list.add(new TreeItem<TItem>(new FormItem(currentDate.getName(), rs.getString("TTBID"),currentDate.getBrandName())));
        }

        return list;
    }

    /**
     * Assigns new applications to government workers.
     * @throws SQLException
     */
    public void roundRobin() throws  SQLException{
        System.out.println("Running roundrobin");
        ArrayList<ApplicationData> unAssignedForms = searchUnassignedForms();
        System.out.println("Unassigned forms = "+ unAssignedForms.size());
        if(!(unAssignedForms.size() == 0)){
            for(int i = 0; i < unAssignedForms.size(); i++) {;
                int GOVID = searchMinWorkLoad();
                System.out.println("Found govid with min workload = " + GOVID);
                assignForm(GOVID, unAssignedForms.get(i));
                System.out.println("FORM ID "+ unAssignedForms.get(i) + " ASSIGNED");
            }
        }

    }

    /**
     * Logs a user into the application.
     * @param userName Username to login.
     * @param password Password to check.
     * @return Returns True if successful.
     * @throws SQLException
     */
    public boolean logIn(String userName, String password) throws SQLException {
        PreparedStatement getAcc = conn.prepareStatement("SELECT * FROM ACCOUNT WHERE  USERNAME = ?");
        String ref;
        getAcc.setString(1,userName);

        ResultSet rs =  getAcc.executeQuery();
        if(rs.next()){
            ref = rs.getString("PASSWORDHASH");
        }
        else{
            return false;
        }
        if(null == ref){
            return false;
        }
        else if(!(rs.next() == false)){
            if (BCrypt.checkpw(password,ref)) {
                johnsUtil.model.SharedResources.Account acc = johnsUtil.model.SharedResources.Account.getInstance();
                acc.setAccountID(rs.getInt("AID"));
                acc.setUserName(rs.getString("USERNAME"));
                acc.setUserType(rs.getInt("USER_TYPE"));
                acc.setAddress(rs.getString("ADDRESS"));
                acc.setName(rs.getString("YOUR_NAME"));
                acc.setEmail(rs.getString("EMAIL"));
                acc.setPhoneNum(rs.getString("PHONE"));
                acc.setPicPath(new File(rs.getString("IMAGE_PATH")));
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    /**
     * Logs an account out of the system.
     * @param aid Account ID of the account to log out.
     * @throws SQLException
     */
    public void logOut(int aid) throws SQLException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat year = new SimpleDateFormat("yyyy");
        DateFormat day = new SimpleDateFormat("D");
        Date date = new Date();
        String curDate = dateFormat.format(date);

        PreparedStatement acc = conn.prepareStatement("UPDATE ACCOUNT SET LAST_LOGGED_IN = ? WHERE AID = ?");
        acc.setString(1,curDate);
        acc.setInt(2,aid);
    }




}






