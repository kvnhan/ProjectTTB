package sample;

import java.util.Date;

/**
 * Class containing specific information about applications for beers.
 */
public class BeerApplicationData extends ApplicationData{
    /**
     * Constructor for beer application objects. Currently, all data fields are
     * pulled directly from the ApplicationData abstract class.
     */
    public BeerApplicationData(int formID, acceptanceInformation acceptanceInfo, int id, int permit_no, String source_of_product,
                               String type_of_product, String brand_name, String address, String phone_number,
                               String email, Date date, String applicantName, String alcoholType, String alcoholContent) {
        super(formID, acceptanceInfo, id, permit_no, source_of_product, type_of_product, brand_name,
                address, phone_number, email, date, applicantName, alcoholType, alcoholContent);


    }

    public void setName(String name){
        setApplicantName(name);

    }

    public String getName(){
        return this.getApplicantName();
    }
}
