package sample;
import johnsUtil.model.*;

import java.sql.Date;
import java.sql.SQLException;

/**
 * Abstract Class for Application Data.
 */
public class ApplicationData{
    private DatabaseUtil databaseUtil = new DatabaseUtil();


    private String ttbID;
    private int repid;
    private String serial;
    private String address;
    private String permitNo;
    private String phoneNumber;
    private String email;
    private String applicantName;
    private int type1;
    private String type2;
    private int type3;
    private String permitAddress;
    private Date submittedDate;

    private AlcoholData alcoholData = null;


    /**
     * Creates an instance of Application Data.
     * @param ttbID Int representing the ID of a form.
     * @param repid Representative ID.
     * @param serial Serial number for an application.
     * @param address Manufacturer's address.
     * @param permitNo Number of the permit.
     * @param phoneNumber Phone number for the manufacturer.
     * @param email Manufacturer's email address.
     * @param applicantName Name of the applicant.
     * @params type1, type2, type3 Various metrics relating to the application data.
     * @param permitAddress Address for the permit.
     * @param submittedDate Date application was submitted.
     */
    public ApplicationData(String ttbID, int repid, String serial, String address, String permitNo, String phoneNumber, String email, String applicantName, int type1, String type2, int type3, String permitAddress, Date submittedDate) {
        this.ttbID = ttbID;
        this.repid = repid;
        this.serial = serial;
        this.address = address;
        this.permitNo = permitNo;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.applicantName = applicantName;
        this.type1 = type1;
        this.type2 = type2;
        this.type3 = type3;
        this.permitAddress = permitAddress;

        this.submittedDate = submittedDate;
    }


    public String getTtbID() {
        return ttbID;
    }

    public void setTtbID(String ttbID) {
        this.ttbID = ttbID;
    }

    public int getRepid() {
        return repid;
    }

    public void setRepid(int repid) {
        this.repid = repid;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPermitNo() {
        return permitNo;
    }

    public void setPermitNo(String permitNo) {
        this.permitNo = permitNo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public int getType1() {
        return type1;
    }

    public void setType1(int type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public int getType3() {
        return type3;
    }

    public void setType3(int type3) {
        this.type3 = type3;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    public String getStatus() throws SQLException{
        return databaseUtil.getStatus(this.getTtbID());
    }

    public int getAssociatedAlchID() throws SQLException{
        return databaseUtil.getAidOfForm(this.getTtbID());
    }
    public String getPermitAddress() {
        return permitAddress;
    }
    public AlcoholData getAssociatedAlcoholData()throws SQLException{
        alcoholData = databaseUtil.searchAlcoholID(getAssociatedAlchID()).get(0);
        return alcoholData;
    }

}
