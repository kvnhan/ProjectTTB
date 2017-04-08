package sample;

import java.sql.*;

/**
 * Created by Adonay on 4/6/2017.
 */
public class DatabaseUtil {

    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String connectionURL = "jdbc:derby:DATABASE\\ProjectC;create=true";
    private String ACCOUNT_FIELDS = " (AID, USERNAME, PASSWORDHASH, ISLOGGEDIN, USER_TYPE)";
    private String ALCH_TYPE_FIELDS = " (ATID, CLASS)";
    private String ALCOHOL_FIELDS = " (AID, NAME, APPELLATION, SULFITE_DESC, ALCH_CONTENT, NET_CONTENT, HEALTH_WARNING, PRODUCT_TYPE, CLASS, LABEL_LEGIBILLITY, LABEL_SIZE, FORMULAS, ALCOHOL_TYPE, BOTTLERS_INFO, BRAND_NAME)";
    private String CLASS_FIELDS = " (CID, CLASS)";
    private String FORM_FIELDS = "(FID, TTBID, REPID, SERIAL, ADDRESS, FANCYNAME, FORMULA, GRAPEVAR, APPELLATION, PERMITNO, INFO_ON_BOTTLE, SOURCE, TYPE, BRANDNAME, PHONE, EMAIL" +
            ", DATE, APPLICANTNAME, ALCOHOLTYPE, VINTAGE, PH, STATUS)";
    private String PRODUCT_TYPE_FIELDS = " (PID, TYPE)";
    private String REVIEWS_FIELDS = " (FID, STATUS, DECIDER, DATE, GENERAL, ORIGINCODE, BRANDNAME, FACIFULNAME, GRAPEVAR, WINEVINTAGE, APPELLATION, BOTTLER, FORMULA, SULFITE, LEGIBILITY, LABELSIZE, DESCRIP)";
    private String STATUS_FIELDS = " (SID, STATUS)";
    private String USER_TYPE_FIELDS = " (UID, TYPE)";


    private Connection conn = null;
    private ResultSet rset;
    private Statement stmt = null;

    DatabaseUtil(){
        conn = connect();

    }

    // Connect
    public static Connection connect(){
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("Java DB Driver not found. Add the classpath to your module.");
            e.printStackTrace();

        }

        System.out.println("Java DB driver registered!");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:derby:C:/Users/Kien/Downloads/search with db test/Iteration_2/Database/ProjectC");
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
        }
        System.out.println("Java DB connection established!");

        return connection;
    }


    private boolean containsQuery(String query) throws SQLException {
        boolean contains = false;

        stmt = conn.createStatement();

        rset = stmt.executeQuery(query);

        contains = rset.next();

        rset.close();
        stmt.close();

        return contains;
    }

    public boolean contains(String TABLENAME, String FIELDNAME, String value) throws SQLException {
        return containsQuery("SELECT * FROM " + TABLENAME+ " WHERE ACCOUNT." + FIELDNAME + " = '" + value + "'");
    }

    public boolean containsInt(String TABLENAME, String FIELDNAME, int value) throws SQLException {

        return containsQuery("SELECT * FROM " + TABLENAME+ " WHERE ACCOUNT." + FIELDNAME + " = " + value);

    }


    public void addAccount(String username, String password, int isLoggedIn, int userType) throws SQLException{
        String values = "'" + username + "', '" + password + "', " + isLoggedIn + "," + userType + " )";
        addToTable("ACCOUNT", ACCOUNT_FIELDS, values, "AID");
    }

    public void addAlcoholType(String classType) throws SQLException{
        String values = "'" + classType + "' )";
        addToTable("ALCH_TYPE", ALCH_TYPE_FIELDS, values, "ATID");
    }

    public void addAlcohol(String name, String appellation, String sulfiteDesc, double alcoholContent, double netContent, String healthWarning, int productType, int classType, String labelLegibility, int labelSize, String formulas, int alcoholType, String bottlersInfo, String brandName) throws SQLException{
        String values = "'" + name + "', '" + appellation + "', '" + sulfiteDesc + "', " + alcoholContent + "," + netContent + ",'" + healthWarning + "', " + productType + ", " + classType + ", '" + labelLegibility + "', " + labelSize + ", '" + formulas + "', " + alcoholType + ", '" + bottlersInfo + "', '" + brandName + "')";
        addToTable("ALCOHOL", ALCOHOL_FIELDS, values, "AID");
    }

    public void addClass(String classType) throws SQLException{
        String values = "'" + classType + "' )";
        addToTable("CLASS", CLASS_FIELDS, values, "CID");
    }

    public void addBeerForm( int ttbid, int repid, String serial, String address, String fancyName, String formula, int permit_no, String infoOnBottle, String source_of_product,
                            String type_of_product, String brand_name, String phone_number, String email, String dateFormat, String applicantName, String alcoholType, String alcoholContent, String status) throws  SQLException{
        stmt = this.conn.createStatement();
        int fid;
        stmt = conn.createStatement();
        rset = stmt.executeQuery("SELECT * FROM FORM ORDER BY FID DESC");
        // checks if table is empty. adds one to previous row's ID for new one if it is not empty. sets ID = 1 if it is
        if (rset.next()) {
            fid = rset.getInt("FID") + 1;
        } else {
            fid = 1;
        }
        stmt.executeUpdate("INSERT INTO FORM (FID, TTBID, REPID, SERIAL, ADDRESS, FANCYNAME, FORMULA, PERMITNO, INFO_ON_BOTTLE, SOURCE, TYPE, BRANDNAME, PHONE, " +
                "EMAIL, DATE, APPLICANTNAME, ALCOHOLTYPE, STATUS) " +
                "VALUES ("+fid+","+ttbid+","+repid+",'"+serial+"','"+address+"', '"+fancyName+"', '"+formula+"', "+permit_no+", '"+infoOnBottle+"','"+source_of_product+"', '"+type_of_product+"'" +
                ", '"+brand_name+"','"+phone_number+"', '"+email+"', '"+dateFormat+"', '"+applicantName+"', '"+alcoholType+"', '"+status+"')");
    }

    public void addWineForm( int ttbid, int repid, String serial, String address, String fancyName, String formula, String grapeVar, String appellation, int permit_no, String infoOnBottle, String source_of_product,
                             String type_of_product, String brand_name, String phone_number, String email, String dateFormat, String applicantName, String alcoholType,
                             int vintage, double ph, String alcoholContent, String status) throws  SQLException{
        stmt = this.conn.createStatement();
        int fid;
        rset = stmt.executeQuery("SELECT * FROM FORM ORDER BY FID DESC");
        // checks if table is empty. adds one to previous row's ID for new one if it is not empty. sets ID = 1 if it is
        if (rset.next()) {
            fid = rset.getInt("FID") + 1;
        } else {
            fid = 1;
        }
        stmt.executeUpdate("INSERT INTO FORM (FID, TTBID, REPID, SERIAL, ADDRESS, FANCYNAME, FORMULA, GRAPEVAR, APPELLATION, PERMITNO, INFO_ON_BOTTLE, SOURCE, TYPE, BRANDNAME, PHONE, EMAIL" +
                ", DATE, APPLICANTNAME, ALCOHOLTYPE, VINTAGE, PH, STATUS) " +
                "VALUES ("+fid+","+ttbid+","+repid+",'"+serial+"','"+address+"', '"+fancyName+"', '"+formula+"','"+grapeVar+"','"+appellation+"',"+permit_no+", '"+infoOnBottle+"','"+source_of_product+"', '"+type_of_product+"'" +
                ", '"+brand_name+"','"+phone_number+"', '"+email+"', '"+dateFormat+"', '"+applicantName+"', '"+alcoholType+"', "+vintage+", "+ph+",'"+status+"')");
    }

    public void addProductType(String type) throws SQLException{
        String values = "'" + type + "' )";
        addToTable("PRODUCT_TYPE", PRODUCT_TYPE_FIELDS, values, "PID");
    }

    // needs fid value to be the same as one row in form table
    public void addReview(int fid, int status, int decider, String date, String general, String originCode, String brandName, String facifulName, String grapeVar, String wineVintage, String appellation, String bottler, String formula, String sulfite, String legibility, String labelSize, String description) throws SQLException{
        String values = "" + status + ", "+ decider + ", DATE('" + date + "'), '" + general + "', '" + originCode + "', '"+ brandName + "', '" + facifulName + "', '" + grapeVar + "', '" + wineVintage + "', '"+ appellation + "', '" + bottler + "', '" + formula + "', '" + sulfite + "', '"+ legibility + "', '" + labelSize + "', '" + description + "')";
        addToTable("REVIEWS", REVIEWS_FIELDS, values, "FID", fid);
    }

    public void addStatus(String status) throws SQLException{
        String values = "'" + status + "' )";
        addToTable("STATUS", STATUS_FIELDS, values, "SID");
    }

    public void addUserType(String type) throws SQLException{
        String values = "'" + type + "' )";
        addToTable("USER_TYPE", USER_TYPE_FIELDS, values, "UID");
    }



    // DOES NOT WORK
    public void clearTable(String TABLENAME) throws SQLException{

        String query = "DELETE FROM " + TABLENAME;
        stmt = conn.createStatement();

        rset = stmt.executeQuery(query);
    }


    // Automatically gives primary ID  for row inserted by incrementing from previous row's ID in table
    private boolean addToTable(String TABLENAME, String FIELDS, String values, String IDNAME) throws SQLException{
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

        rset.close();
        stmt.close();

        return isAdded;
    }

    // (METHOD OVERLOAD) Same method as before but requests input for value of primary ID for the row inserted
    private boolean addToTable(String TABLENAME, String FIELDS, String values, String IDNAME, int ID) throws SQLException{
        boolean isAdded = false;
        int uRows = 0;
        stmt = conn.createStatement();

        rset = stmt.executeQuery("SELECT * FROM " + TABLENAME + " ORDER BY " + IDNAME + " DESC");

        uRows = stmt.executeUpdate("INSERT INTO " + TABLENAME + FIELDS + " VALUES (" + ID + ", " + values);

        isAdded = uRows > 0;

        System.out.println(uRows + " Row(s) Updated");

        rset.close();
        stmt.close();

        return isAdded;
    }

    // Change status after government agents finish reviewing an application
    public void changeSatus(String newStatus, int fid) throws SQLException{
        String query = "UPDATE FORM SET STATUS = ? WHERE FID = ?";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, newStatus);
        pstmt.setInt(2, fid);
        pstmt.executeUpdate();
    }
}
