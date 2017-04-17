package sample;

import java.util.Date;

/**

 * Beer-specific application data.

 */

public class BeerApplicationData extends ApplicationData{


    /**
     * Constructs a beer application data object. All parameters are taken from
     * ApplicationData; see the ApplicationData class for more information.
     * @param formID
     * @param acceptanceInfo
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
     * @param date
     * @param applicantName
     * @param alcoholType
     * @param alcoholContent
     */
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
