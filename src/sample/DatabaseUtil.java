package sample;

import java.sql.*;
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
    private String ALCOHOL_FIELDS = " (AID, NAME, APPELLATION, SULFITE_DESC, ALCH_CONTENT, NET_CONTENT, HEALTH_WARNING, PRODUCT_TYPE, CLASS, LABEL_LEGIBILLITY, LABEL_SIZE, FORMULAS, ALCOHOL_TYPE, BOTTLERS_INFO, BRAND_NAME)";
    private String CLASS_FIELDS = " (CID, CLASS)";
    private String FORM_FIELDS = " (FID, TTBID, REPID, SERIAL, ADDRESS, FANCYNAME, FORMULA, GRAPEVAR, APPELLATION, PERMITNO, INFO_ON_BOTTLE, SOURCE, TYPE, BRANDNAME, PHONE, EMAIL" +
            ", DATE, APPLICANTNAME, ALCOHOLTYPE, VINTAGE, PH, STATUS)";
    private String FORM_FIELDS_WINE = " (FID, TTBID, REPID, SERIAL, ADDRESS, FANCYNAME, FORMULA, GRAPEVAR, APPELLATION, PERMITNO, INFO_ON_BOTTLE, SOURCE, TYPE, BRANDNAME, PHONE, EMAIL" +
            ", DATE, APPLICANTNAME, ALCOHOLTYPE, VINTAGE, PH, STATUS, AID)";
    private String FORM_FIELDS_BEER = " (FID, TTBID, REPID, SERIAL, ADDRESS, FANCYNAME, FORMULA, PERMITNO, INFO_ON_BOTTLE, SOURCE, TYPE, BRANDNAME, PHONE, " +
            "EMAIL, DATE, APPLICANTNAME, ALCOHOLTYPE, STATUS, AID)";
    private String PRODUCT_TYPE_FIELDS = " (PID, TYPE)";
    private String REVIEWS_FIELDS = " (FID, STATUS, DECIDER, DATE, GENERAL, ORIGINCODE, BRANDNAME, FACIFULNAME, GRAPEVAR, WINEVINTAGE, APPELLATION, BOTTLER, FORMULA, SULFITE, LEGIBILITY, LABELSIZE, DESCRIP)";
    private String STATUS_FIELDS = " (SID, STATUS)";
    private String USER_TYPE_FIELDS = " (UID, TYPE)";


    private Connection conn = null;
    private ResultSet rset;
    private Statement stmt = null;
    private ScreenUtil screenUtil = new ScreenUtil();
    private AccountsUtil accountsUtil = new AccountsUtil();

    /**
     * Creates a connection to the database.
     */
    public DatabaseUtil() {
        conn = connect();

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

        contains = rset.next();

        return contains;
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
    public void addAlcohol(String name, String appellation, String sulfiteDesc, double alcoholContent, double netContent, String healthWarning, int productType, int classType, String labelLegibility, int labelSize, String formulas, int alcoholType, String bottlersInfo, String brandName) throws SQLException {
        String values = "'" + name + "', '" + appellation + "', '" + sulfiteDesc + "', " + alcoholContent + "," + netContent + ",'" + healthWarning + "', " + productType + ", " + classType + ", '" + labelLegibility + "', " + labelSize + ", '" + formulas + "', " + alcoholType + ", '" + bottlersInfo + "', '" + brandName + "')";
        addToTable("ALCOHOL", ALCOHOL_FIELDS, values, "AID");
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
/*
    public void addForm(String dateCompleted, int aid, int status, int alcid, int ttbid, int permitno, String brandName, String address, String phoneNumber, String email, double phLevel, String vintageDate, String nameOfApplicant) throws SQLException{
        String values = "DATE('" + dateCompleted + "'), " + aid + ", " + status + ", " + alcid + ", " + ttbid + ", " + permitno + ", '" + brandName + "', '" + address + "', '"+ phoneNumber + "', '" + email + "', " + phLevel + ", DATE('" + vintageDate + "'), '" + nameOfApplicant + "')" ;
        addToTable("FORM", FORM_FIELDS, values, "FID");
    }*/

    /**
     * Adds a beer form to the database. See BeerApplicationData and ApplicationData
     * for specific information on the parameters passed into the function.
     * @param ttbid
     * @param repid
     * @param serial
     * @param address
     * @param fancyName
     * @param formula
     * @param permit_no
     * @param infoOnBottle
     * @param source_of_product
     * @param type_of_product
     * @param brand_name
     * @param phone_number
     * @param email
     * @param dateFormat
     * @param applicantName
     * @param alcoholType
     * @param alcoholContent
     * @param status
     * @throws SQLException
     */
    public void addBeerForm(int ttbid, int repid, String serial, String address, String fancyName, String formula, int permit_no, String infoOnBottle, String source_of_product,
                            String type_of_product, String brand_name, String phone_number, String email, String dateFormat, String applicantName, String alcoholType, String alcoholContent, String status) throws SQLException {

        int aid = getAccountAid(AccountsUtil.getUsername());
        String values = "" + ttbid + "," + repid + ",'" + serial + "','" + address + "', '" + fancyName + "', '" + formula + "', " + permit_no + ", '" + infoOnBottle + "','" + source_of_product + "', '" + type_of_product + "'" +
                ", '" + brand_name + "','" + phone_number + "', '" + email + "', '" + dateFormat + "', '" + applicantName + "', '" + alcoholType + "', '" + status + "', " + aid + ")";

        addToTable("FORM", FORM_FIELDS_BEER, values, "FID");
    }

    /**
     * Adds a wine application to the database. See ApplicationData and WineApplicationData
     * for more information on specific parameters.
     * @param ttbid
     * @param repid
     * @param serial
     * @param address
     * @param fancyName
     * @param formula
     * @param grapeVar
     * @param appellation
     * @param permit_no
     * @param infoOnBottle
     * @param source_of_product
     * @param type_of_product
     * @param brand_name
     * @param phone_number
     * @param email
     * @param dateFormat
     * @param applicantName
     * @param alcoholType
     * @param vintage
     * @param ph
     * @param alcoholContent
     * @param status
     * @throws SQLException
     */
    public void addWineForm(int ttbid, int repid, String serial, String address, String fancyName, String formula, String grapeVar, String appellation, int permit_no, String infoOnBottle, String source_of_product,
                            String type_of_product, String brand_name, String phone_number, String email, String dateFormat, String applicantName, String alcoholType,
                            int vintage, double ph, String alcoholContent, String status) throws SQLException {
        int aid = getAccountAid(AccountsUtil.getUsername());
        String values = "" + ttbid + "," + repid + ",'" + serial + "','" + address + "', '" + fancyName + "', '" + formula + "','" + grapeVar + "','" + appellation + "'," + permit_no + ", '" + infoOnBottle + "','" + source_of_product + "', '" + type_of_product + "'" +
                ", '" + brand_name + "','" + phone_number + "', '" + email + "', '" + dateFormat + "', '" + applicantName + "', '" + alcoholType + "', " + vintage + ", " + ph + ",'" + status + "', " + aid + ")";
        addToTable("FORM", FORM_FIELDS_WINE, values, "FID");
    }

    public void addDistilledSpiritsForm(int ttbid, int repid, String serial, String address, String fancyName, String formula, int permit_no, String infoOnBottle, String source_of_product,
                                        String type_of_product, String brand_name, String phone_number, String email, String dateFormat, String applicantName, String alcoholType, String alcoholContent, String status) throws SQLException {

        int aid = getAccountAid(AccountsUtil.getUsername());
        String values = "" + ttbid + "," + repid + ",'" + serial + "','" + address + "', '" + fancyName + "', '" + formula + "', " + permit_no + ", '" + infoOnBottle + "','" + source_of_product + "', '" + type_of_product + "'" +
                ", '" + brand_name + "','" + phone_number + "', '" + email + "', '" + dateFormat + "', '" + applicantName + "', '" + alcoholType + "', '" + status + "', " + aid + ")";

        addToTable("FORM", FORM_FIELDS_BEER, values, "FID");
    }

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
    public void addReview(int fid, int status, int decider, String date, String general, String originCode, String brandName, String facifulName, String grapeVar, String wineVintage, String appellation, String bottler, String formula, String sulfite, String legibility, String labelSize, String description) throws SQLException {
        String values = "" + status + ", " + decider + ", DATE('" + date + "'), '" + general + "', '" + originCode + "', '" + brandName + "', '" + facifulName + "', '" + grapeVar + "', '" + wineVintage + "', '" + appellation + "', '" + bottler + "', '" + formula + "', '" + sulfite + "', '" + legibility + "', '" + labelSize + "', '" + description + "')";
        addToTable("REVIEWS", REVIEWS_FIELDS, values, "FID", fid);
    }

    /**
     * Adds status type to the database.
     * @param status Type of status to be added.
     * @throws SQLException
     */
    public void addStatus(String status) throws SQLException {
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
     * @return Returns true if successful.
     * @throws SQLException
     */
    // Automatically gives primary ID  for row inserted by incrementing from previous row's ID in table
    private boolean addToTable(String TABLENAME, String FIELDS, String values, String IDNAME) throws SQLException {
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

        return isAdded;
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
        String query = "SELECT * FROM ACCOUNT WHERE ACCOUNT.USER_TYPE = " + userType;
        return searchAccount(query);
    }

    public ArrayList<Account> searchAccountWithUsername(String username) throws SQLException {
        String query = "SELECT * FROM ACCOUNT WHERE ACCOUNT.USERNAME = '" + username + "'";
        return searchAccount(query);
    }

    // Code used to search Alcohol table based on alcohol type
    public List<AlcoholData> searchAlcoholWithType(int alcoholType) throws SQLException{
        String query = "SELECT * FROM ALCOHOL WHERE ALCOHOL.ALCOHOL_TYPE = " + alcoholType;

        return searchAlcoholTable(query);
    }

    // Code used to search Alcohol table based on brand name. Uses partial search
    public List<AlcoholData> searchAlcoholBrand(String brandName) throws SQLException{
        String query = "SELECT * FROM ALCOHOL WHERE UPPER(ALCOHOL.BRAND_NAME) LIKE UPPER('"+brandName+"%')";

        return searchAlcoholTable(query);
    }

    //TODO: make a new search function which takes a String (query) and int (column) so we only need 2 functions: search column, and search all

    //TODO Make this search all fields
    // Code used to search Alcohol table based on brand name. Uses partial search
    public List<AlcoholData> searchAllFields(String brandName) throws SQLException{
        String query = "SELECT * FROM ALCOHOL WHERE UPPER(ALCOHOL.BRAND_NAME) LIKE UPPER('"+brandName+"%')";

        return searchAlcoholTable(query);
    }


    private List<AlcoholData> searchAlcoholTable(String query) throws SQLException{
        List<AlcoholData> AlcoholDataList = new ArrayList<AlcoholData>();
        AlcoholData a;

        stmt = conn.createStatement();

        rset = stmt.executeQuery(query);

        while(rset.next()){
            int AID = rset.getInt("AID");
            String name = String.format("%1$"+25+ "s", rset.getString("NAME")).trim();
            String brandname = String.format("%1$"+25+ "s", rset.getString("BRAND_NAME")).trim();
            String appelation = String.format("%1$"+22+ "s", rset.getString("APPELLATION")).trim();
            String sulfiteDesc = String.format("%1$"+22+ "s", rset.getString("SULFITE_DESC")).trim();
            double alchContent = rset.getDouble("ALCH_CONTENT");
            double netContent = rset.getDouble("NET_CONTENT");
            String healthWarning = String.format("%1$"+22+ "s", rset.getString("HEALTH_WARNING")).trim();
            int productType = rset.getInt("PRODUCT_TYPE");
            int classType = rset.getInt("CLASS");
            String labelLegibility = String.format("%1$"+22+ "s", rset.getString("LABEL_LEGIBILLITY")).trim();
            int labelSize = rset.getInt("LABEL_SIZE");
            String formulas = String.format("%1$"+22+ "s", rset.getString("FORMULAS")).trim();
            int alchType = rset.getInt("ALCOHOL_TYPE");
            String bottlersInfo = String.format("%1$"+22+ "s", rset.getString("BOTTLERS_INFO")).trim();
            String imageName = String.format("%1$"+22+ "s", rset.getString("PICTURE")).trim();

            a = new AlcoholData(AID, name, brandname, appelation, sulfiteDesc, alchContent, netContent, healthWarning, productType, classType, labelLegibility, labelSize, formulas, alchType, bottlersInfo, imageName);
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

    public ArrayList<ApplicationData> searchFormWithFid(int FID) throws SQLException{
        String query = "SELECT * FROM FORM WHERE FORM.FID = " + FID;

        return searchForm(query);
    }

    public ArrayList<ApplicationData> searchForm(String query) throws SQLException{
        ArrayList<ApplicationData> AppDataList = new ArrayList<>();
        ApplicationData a;

        stmt = conn.createStatement();

        rset = stmt.executeQuery(query);

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
            String status = rset.getString("STATUS");
            AcceptanceInformation info = new AcceptanceInformation(null, applicantName, null, status);

            a = new ApplicationData(fid, info,ttbid, repid, serial,address,
                    fancyName, formula, permit_no, infoOnBottle,
                    source_of_product, type_of_product, brand_name, phone_number, email, date, applicantName,
                    alcoholType, alcoholContent);
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
    public void changeSatus(String newStatus, int fid) throws SQLException{
        String query = "UPDATE FORM SET STATUS = ? WHERE FID = ?";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, newStatus);
        pstmt.setInt(2, fid);
        pstmt.executeUpdate();
    }

    public BeerApplicationData fillSubmittedBeerForm(int fid) throws SQLException{

        String sql = "SELECT * FROM APP.FORM WHERE FID = ?";
        BeerApplicationData a;
        PreparedStatement sm;
        sm = conn.prepareStatement(sql);
        sm.setInt(1, fid);
        rset = sm.executeQuery();

        int ttbid = 0;
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
        AcceptanceInformation info = new AcceptanceInformation(null, "", null, status);

        while(rset.next()){
            fid = rset.getInt("FID");
            ttbid = rset.getInt("TTBID");
            repid = rset.getInt("REPID");
            serial = rset.getString("SERIAL");
            address = rset.getString("ADDRESS");
            fancyName = rset.getString("FANCYNAME");
            formula = rset.getString("FORMULA");
            permit_no = rset.getInt("PERMITNO");
            infoOnBottle = rset.getString("INFO_ON_BOTTLE");
            source_of_product = rset.getString("SOURCE");
            type_of_product = rset.getString("TYPE");
            brand_name = rset.getString("BRANDNAME");
            phone_number = rset.getString("PHONE");
            email = rset.getString("EMAIL");
            applicantName = rset.getString("APPLICANTNAME");
            alcoholType = rset.getString("ALCOHOLTYPE");
            alcoholContent = "";
            date = rset.getString("DATE");
            status = rset.getString("STATUS");
            info = new AcceptanceInformation(null, applicantName, null, status);

        }

        a = new BeerApplicationData(fid, info,ttbid, repid, serial,address,
                fancyName, formula, permit_no, infoOnBottle,
                source_of_product, type_of_product, brand_name, phone_number, email, null, applicantName,
                alcoholType, alcoholContent);

        return a;

    }

    public WineApplicationData fillSubmittedWineForm(int fid) throws SQLException{

        String sql = "SELECT * FROM APP.FORM WHERE FID = ?";
        WineApplicationData a;
        PreparedStatement sm;
        sm = conn.prepareStatement(sql);
        sm.setInt(1, fid);
        rset = sm.executeQuery();

        int ttbid = 0;
        int repid = 0;
        String grape_varietal = "";
        int vintage_date = 0;
        double ph_level = 0;
        String appellation = "";
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
        AcceptanceInformation info = new AcceptanceInformation(null, "", null, status);

        while(rset.next()){
            fid = rset.getInt("FID");
            ttbid = rset.getInt("TTBID");
            repid = rset.getInt("REPID");
            serial = rset.getString("SERIAL");
            address = rset.getString("ADDRESS");
            fancyName = rset.getString("FANCYNAME");
            formula = rset.getString("FORMULA");
            grape_varietal = rset.getString("GRAPEVAR");
            appellation = rset.getString("APPELLATION");
            permit_no = rset.getInt("PERMITNO");
            infoOnBottle = rset.getString("INFO_ON_BOTTLE");
            source_of_product = rset.getString("SOURCE");
            type_of_product = rset.getString("TYPE");
            brand_name = rset.getString("BRANDNAME");
            phone_number = rset.getString("PHONE");
            email = rset.getString("EMAIL");
            applicantName = rset.getString("APPLICANTNAME");
            alcoholType = rset.getString("ALCOHOLTYPE");
            alcoholContent = "";
            date = rset.getString("DATE");
            vintage_date = rset.getInt("VINTAGE");
            ph_level = rset.getDouble("PH");
            status = rset.getString("STATUS");
            info = new AcceptanceInformation(null, applicantName, null, status);

        }

        a = new WineApplicationData(fid, info,ttbid, repid, serial,address,
                fancyName, formula, grape_varietal, appellation, permit_no, infoOnBottle,
                source_of_product, type_of_product, brand_name, phone_number, email, date, applicantName,
                alcoholType, alcoholContent, vintage_date, ph_level);

        return a;
    }

    public void reviseAlcohol(int fid) throws SQLException{}

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

    public void assignForm(int govid, ApplicationData unAssignedForm) throws SQLException{
        int resultCount;

        stmt = conn.createStatement();

        //update alcohol status
        String query = "UPDATE FORM SET FORM.STATUS = 'ASSIGNED', FORM.GOVID = " + govid + " WHERE FID = "+ unAssignedForm.getFormID();


        resultCount = stmt.executeUpdate(query);

    }

    public String checkforType(int fid) throws SQLException {
        String type = "";
        String sql = "SELECT * FROM APP.FORM WHERE FID = ?";
        PreparedStatement sm;
        sm = conn.prepareStatement(sql);
        sm.setInt(1, fid);
        rset = sm.executeQuery();
        while (rset.next()) {
            type = rset.getString("TYPE");
            if (type.equals("WINE")) {
                return "WINE";
            }
            if (type.equals("BEER")) {
                return "BEER";
            }
        }
        return "OTHER";
    }

    public void resubmitWine(int fid, WineApplicationData a) throws SQLException{

        String status = "REVISED";
        Date date = new Date();
        String dateFormat = date.toString();
        String sql = "UPDATE APP.FORM SET TTBID = ?, REPID = ?, SERIAL = ?, ADDRESS = ?, FANCYNAME = ?," +
                "FORMULA = ?, GRAPEVAR = ?, APPELLATION = ?, PERMITNO = ?, SOURCE = ?, TYPE = ?, BRANDNAME = ?," +
                "PHONE = ?, EMAIL = ?, DATE = ?, APPLICANTNAME = ?, ALCOHOLTYPE = ?, VINTAGE = ?, PH = ?, STATUS = ? WHERE FID = ?";

        PreparedStatement sm;
        sm = conn.prepareStatement(sql);
        sm.setInt(1, a.getTtbid());
        sm.setInt(2, a.getRepid());
        sm.setString(3, a.getSerial());
        sm.setString(4, a.getAddress());
        sm.setString(5, a.getFancyName());
        sm.setString(6, a.getFormula());
        sm.setString(7, a.getGrape_varietal());
        sm.setString(8, a.getAppellation());
        sm.setInt(9, a.getPermit_no());
        sm.setString(10, a.getSource_of_product());
        sm.setString(11, a.getType_of_product());
        sm.setString(12, a.getBrand_name());
        sm.setString(13, a.getPhone_number());
        sm.setString(14, a.getEmail());
        sm.setString(15, dateFormat);
        sm.setString(16, a.getApplicantName());
        sm.setString(17, a.getAlcoholType());
        sm.setInt(18, a.getVintage_date());
        sm.setDouble(19, a.getPh_level());
        sm.setString(20, status);
        sm.setInt(21, fid);

        sm.executeUpdate();
    }

    public void resubmitBeer(int fid, BeerApplicationData a) throws SQLException{

        String status = "REVISED";
        Date date = new Date();
        String dateFormat = date.toString();
        String sql = "UPDATE APP.FORM SET TTBID = ?, REPID = ?, SERIAL = ?, ADDRESS = ?, FANCYNAME = ?," +
                "FORMULA = ?,PERMITNO = ?, SOURCE = ?, TYPE = ?, BRANDNAME = ?," +
                "PHONE = ?, EMAIL = ?, DATE = ?, APPLICANTNAME = ?, ALCOHOLTYPE = ?, STATUS = ? WHERE FID = ?";

        PreparedStatement sm;
        sm = conn.prepareStatement(sql);
        sm.setInt(1, a.getTtbid());
        sm.setInt(2, a.getRepid());
        sm.setString(3, a.getSerial());
        sm.setString(4, a.getAddress());
        sm.setString(5, a.getFancyName());
        sm.setString(6, a.getFormula());
        sm.setInt(7, a.getPermit_no());
        sm.setString(8, a.getSource_of_product());
        sm.setString(9, a.getType_of_product());
        sm.setString(10, a.getBrand_name());
        sm.setString(11, a.getPhone_number());
        sm.setString(12, a.getEmail());
        sm.setString(13, dateFormat);
        sm.setString(14, a.getApplicantName());
        sm.setString(15, a.getAlcoholType());
        sm.setString(16, status);
        sm.setInt(17, fid);

        sm.executeUpdate();
    }


}

