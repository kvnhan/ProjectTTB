package sample;

import java.util.Date;

/**
 * Created by Kien Nhan on 4/3/2017.
 */
public class BeerApplicationData extends ApplicationData{

    public BeerApplicationData(String formID, acceptanceInformation acceptanceInfo, String id, String permit_no, String source_of_product,
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
