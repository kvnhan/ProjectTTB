package sample;

/**
 * Contains data relating to specific alcoholic beverages stored in the database.
 */
public class AlcoholData {
    private String ID;
    private String Name;
    private String BrandName;
    private String Appellation;
    private String Type;

    /**
     * Constructor for an AlcoholData object.
     * @param IDC String representing the ID for the alcoholic beverage.
     * @param NameC String representing the name of the alcoholic beverage.
     * @param BrandNameC String representing the official brand name of the alcoholic beverage.
     * @param AppellationC TODO: figure out what this is
     * @param TypeC String representing the type of the alcoholic beverage.
     */
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
