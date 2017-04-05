package sample;

import java.util.Date;

/**
 * Created by Kien Nhan on 4/3/2017.
 */
public class WineApplicationData extends ApplicationData{
    private enum alcholType{
        WINE
    }
    private int vintage_date;
    private double ph_level;

    public WineApplicationData(String formID, acceptanceInformation acceptanceInfo, String id, String permit_no,
                               String brand_name, String source_of_product, String type_of_product, String address, String phone_number,
                               String email, Date date, String applicantName, String alcoholType, String alcoholContent,
                               int vintage_date, double ph_level) {
        super(formID, acceptanceInfo, id, permit_no, brand_name,source_of_product, type_of_product,
                address, phone_number, email, date, applicantName, alcoholType, alcoholContent);
        this.vintage_date = vintage_date;
        this.ph_level = ph_level;

    }

    public int getVintage_date() {
        return vintage_date;
    }

    public void setVintage_date(int vintage_date) {
        this.vintage_date = vintage_date;
    }

    public double getPh_level() {
        return ph_level;
    }

    public void setPh_level(double ph_level) {
        this.ph_level = ph_level;
    }
}
