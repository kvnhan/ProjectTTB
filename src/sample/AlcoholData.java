package sample;

/**
 * Created by Sam Winter on 4/2/2017.
 */
public class AlcoholData {
    private String ID;
    private String Name;
    private String BrandName;
    private String Appellation;
    private String Type;

    //Constructor
    public AlcoholData(String IDC, String NameC, String BrandNameC, String AppellationC, String TypeC) {
        ID = IDC;
        Name = NameC;
        BrandName = BrandNameC;
        Appellation = AppellationC;
        Type = TypeC;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public String getAppellation() {
        return Appellation;
    }

    public void setAppellation(String appellation) {
        Appellation = appellation;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
