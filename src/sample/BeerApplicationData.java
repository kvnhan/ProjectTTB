package sample;

import java.util.Date;

/**

 * Created by Kien Nhan on 4/3/2017.

 */

public class BeerApplicationData extends ApplicationData{



    public BeerApplicationData(int formID, AcceptanceInformation acceptanceInfo, int ttbid, int repid, String serial, String address, String fancyName, String formula, int permit_no,

                               String infoOnBottle, String source_of_product, String type_of_product, String brand_name, String phone_number, String email, String date, String applicantName,

                               String alcoholType, String alcoholContent) {

        super(formID, acceptanceInfo, ttbid, repid, serial, address, fancyName, formula, permit_no, infoOnBottle, source_of_product, type_of_product, brand_name, phone_number, email, date, applicantName, alcoholType, alcoholContent);

    }

    public void setName(String name){
        setApplicantName(name);

    }

    public String getName(){
        return this.getApplicantName();
    }
}
