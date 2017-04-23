package sample;

/**
 * passes data to the database. Contains an instance of AlcoholData.
 */
public class DataPasser {

    private static AlcoholData alcData;
    private static int isRevised;
    private static String ttbidID;
    private static int isInvokebyReviseMenu;
    private static int disableVintageField;
    private static int disablepHField;
    private static int disableAppellationField;
    private static int disableVarietalField;
    private static int disableRestField;
    private static int disableImageField;
    private static int disableImageButton;
    private static int disableAlcoContentField;

    public static String getTtbID() {
        return ttbidID;
    }

    public void setTtbID(String ttbidID){
        this.ttbidID = ttbidID;
    }

    public int isIsRevised() {
        return isRevised;
    }

    public void setIsRevised(int isRevised) {
        this.isRevised = isRevised;
    }

    public int getDisableAlcoContentField() {
        return disableAlcoContentField;
    }

    public void setDisableAlcoContentField(int disableAlcoContentField) {
        this.disableAlcoContentField = disableAlcoContentField;
    }

    public int getDisableVintageField() {
        return disableVintageField;
    }

    public  void setDisableVintageField(int disableVintageField) {
        this.disableVintageField = disableVintageField;
    }

    public  int getDisablepHField() {
        return disablepHField;
    }

    public  void setDisablepHField(int disablepHField) {
        this.disablepHField = disablepHField;
    }

    public int getDisableAppellationField() {
        return disableAppellationField;
    }

    public  void setDisableAppellationField(int disableAppellationField) {
        this.disableAppellationField = disableAppellationField;
    }

    public  int getDisableVarietalField() {
        return disableVarietalField;
    }

    public  void setDisableVarietalField(int disableVarietalField) {
        this.disableVarietalField = disableVarietalField;
    }

    public int getDisableRestField() {
        return disableRestField;
    }

    public void setDisableRestField(int disableRestField) {
        this.disableRestField = disableRestField;
    }

    public  int getDisableImageField() {
        return disableImageField;
    }

    public void setDisableImageField(int disableImageField) {
        this.disableImageField = disableImageField;
    }

    public int getDisableImageButton() {
        return disableImageButton;
    }

    public void setDisableImageButton(int disableImageButton) {
        this.disableImageButton = disableImageButton;
    }


    public int isIsInvokebyReviseMenu() {
        return isInvokebyReviseMenu;
    }

    public void setIsInvokebyReviseMenu(int isInvokebyReviseMenu) {
        this.isInvokebyReviseMenu = isInvokebyReviseMenu;
    }

    public AlcoholData getAlcData() {
        return alcData;
    }

    public void setAlcData(AlcoholData alcData) {
        this.alcData = alcData;
    }
}
