package sample;



import java.util.Date;



/**

 * Created by Kien Nhan on 4/3/2017.

 */

public class WineApplicationData extends ApplicationData{



    private int vintage_date;

    private double ph_level;

    private String grape_varietal;

    private String appellation;



    public WineApplicationData(int formID, AcceptanceInformation acceptanceInfo, int ttbid, int repid, String serial, String address,

                               String fancyName, String formula, String grape_varietal, String appellation, int permit_no, String infoOnBottle,

                               String source_of_product, String type_of_product, String brand_name, String phone_number, String email, String date, String applicantName,

                               String alcoholType, String alcoholContent, int vintage_date, double ph_level) {

        super(formID, acceptanceInfo, ttbid, repid, serial, address, fancyName, formula,permit_no, infoOnBottle, source_of_product, type_of_product, brand_name, phone_number, email, date, applicantName, alcoholType, alcoholContent);

        this.vintage_date = vintage_date;

        this.ph_level = ph_level;

        this.grape_varietal = grape_varietal;

        this.appellation = appellation;

    }



    public int getVintage_date() {

        return vintage_date;

    }



    public double getPh_level() {

        return ph_level;

    }



    public String getGrape_varietal() {

        return grape_varietal;

    }



    public String getAppellation() {

        return appellation;

    }

}