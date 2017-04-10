package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Adonay on 4/6/2017.
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
            ", DATE, APPLICANTNAME, ALCOHOLTYPE, VINTAGE, PH, STATUS)";
    private String FORM_FIELDS_BEER = " (FID, TTBID, REPID, SERIAL, ADDRESS, FANCYNAME, FORMULA, PERMITNO, INFO_ON_BOTTLE, SOURCE, TYPE, BRANDNAME, PHONE, " +
            "EMAIL, DATE, APPLICANTNAME, ALCOHOLTYPE, STATUS)";
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
            connection = DriverManager.getConnection(connectionURL);
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return connection;
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

    //To check if a double value exists in the table
    public boolean containsDouble(String TABLENAME, String FIELDNAME, double value) throws SQLException {

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
/*
    public void addForm(String dateCompleted, int aid, int status, int alcid, int ttbid, int permitno, String brandName, String address, String phoneNumber, String email, double phLevel, String vintageDate, String nameOfApplicant) throws SQLException{
        String values = "DATE('" + dateCompleted + "'), " + aid + ", " + status + ", " + alcid + ", " + ttbid + ", " + permitno + ", '" + brandName + "', '" + address + "', '"+ phoneNumber + "', '" + email + "', " + phLevel + ", DATE('" + vintageDate + "'), '" + nameOfApplicant + "')" ;
        addToTable("FORM", FORM_FIELDS, values, "FID");
    }*/

    public void addBeerForm( int ttbid, int repid, String serial, String address, String fancyName, String formula, int permit_no, String infoOnBottle, String source_of_product,
                            String type_of_product, String brand_name, String phone_number, String email, String dateFormat, String applicantName, String alcoholType, String alcoholContent, String status) throws  SQLException{

        String values = ""+ttbid+","+repid+",'"+serial+"','"+address+"', '"+fancyName+"', '"+formula+"', "+permit_no+", '"+infoOnBottle+"','"+source_of_product+"', '"+type_of_product+"'" +
                ", '"+brand_name+"','"+phone_number+"', '"+email+"', '"+dateFormat+"', '"+applicantName+"', '"+alcoholType+"', '"+status+"')";

        addToTable("FORM", FORM_FIELDS_BEER, values, "FID");
    }

    public void addWineForm( int ttbid, int repid, String serial, String address, String fancyName, String formula, String grapeVar, String appellation, int permit_no, String infoOnBottle, String source_of_product,
                             String type_of_product, String brand_name, String phone_number, String email, String dateFormat, String applicantName, String alcoholType,
                             int vintage, double ph, String alcoholContent, String status) throws  SQLException{
        String values = ""+ttbid+","+repid+",'"+serial+"','"+address+"', '"+fancyName+"', '"+formula+"','"+grapeVar+"','"+appellation+"',"+permit_no+", '"+infoOnBottle+"','"+source_of_product+"', '"+type_of_product+"'" +
                ", '"+brand_name+"','"+phone_number+"', '"+email+"', '"+dateFormat+"', '"+applicantName+"', '"+alcoholType+"', "+vintage+", "+ph+",'"+status+"')";
        addToTable("FORM", FORM_FIELDS_WINE, values, "FID");
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

        String query = "DROP TABLE " + TABLENAME;
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

    // Code used to search Alcohol table based on alcohol type
    public List<AlcoholData> searchAlcoholWithType(int alcoholType) throws SQLException{
        String query = "SELECT * FROM ALCOHOL WHERE ALCOHOL.ALCOHOL_TYPE = " + alcoholType;

        return searchAlcoholTable(query);
    }

    // Code used to search Alcohol table based on brand name. Uses partial search
    public List<AlcoholData> searchAlcoholBrand(String brandName) throws SQLException{
        String query = "SELECT * FROM ALCOHOL WHERE ALCOHOL.BRAND_NAME LIKE '"+brandName+"%'";

        return searchAlcoholTable(query);
    }

    protected List<AlcoholData> searchAlcoholTable(String query) throws SQLException{
        List<AlcoholData> AlcoholDataList = new ArrayList<AlcoholData>();
        AlcoholData a;

        stmt = conn.createStatement();

        rset = stmt.executeQuery(query);

        while(rset.next()){
            String ID = String.format("%1$"+3+ "s", rset.getString("AID"));
            String name = String.format("%1$"+25+ "s", rset.getString("NAME"));
            String brandname = String.format("%1$"+25+ "s", rset.getString("BRAND_NAME"));
            String app = String.format("%1$"+22+ "s", rset.getString("APPELLATION"));
            String type = String.format("%1$"+10+ "s", rset.getString("ALCOHOL_TYPE"));

            a = new AlcoholData(ID, name, brandname, app, type);
            AlcoholDataList.add(a);
        }

        return AlcoholDataList;

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
