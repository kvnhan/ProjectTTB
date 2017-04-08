package sample;
import java.util.Date;

/**
 * Abstract Class for Application Data.
 */
public abstract class ApplicationData extends SubmissionForm{

    private int id;
    private int permit_no;
    private String source_of_product;
    private String type_of_product;
    private String brand_name;
    private String phone_number;
    private String email;
    private Date date;
    public String applicantName;
    private String alcoholType;
    private String alcoholContent;



    public ApplicationData(int formID, acceptanceInformation acceptanceInfo, int id, int permit_no, String source_of_product, String type_of_product, String brand_name, String address, String phone_number,
                           String email, Date date, String applicantName, String alcoholType, String alcoholContent) {
        super(formID, acceptanceInfo);
        this.id = id;
        this.permit_no = permit_no;
        this.source_of_product = source_of_product;
        this.type_of_product = type_of_product;
        this.brand_name = brand_name;
        this.phone_number = phone_number;
        this.email = email;
        this.date = date;
        this.applicantName = applicantName;
        this.alcoholType = alcoholType;
        this.alcoholContent = alcoholContent;
    }

    public String getSource_of_product() {
        return source_of_product;
    }

    public String getType_of_product() {
        return type_of_product;
    }

    public String getAlcoholType() {
        return alcoholType;
    }
    public int getId() {
        return id;
    }


    public int getPermit_no() {
        return permit_no;
    }


    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getAlcoholContent() {
        return alcoholContent;
    }

    public void setAlcoholContent(String alcoholContent) {
        this.alcoholContent = alcoholContent;
    }





}
