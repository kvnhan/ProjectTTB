package sample;

/**
 * passes data to the database. Contains an instance of AlcoholData.
 */
public class DataPasser {

    private static AlcoholData alcData;
    private static ApplicationData applicationData;
    private static int isInvokedByManu;
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
    private static int disableBottler;
    private static int disableComment;
    private static int disableMailingAndName;
    private static int disableAddress;
    private static int disableNet;

    public  int getDisableNet() {
        return disableNet;
    }

    public  void setDisableNet(int disableNet) {
        this.disableNet = disableNet;
    }

    public int getDisableBottler() {
        return disableBottler;
    }

    public void setDisableBottler(int disableBottler) {
        this.disableBottler = disableBottler;
    }

    public  int getDisableComment() {
        return disableComment;
    }

    public  void setDisableComment(int disableComment) {
        this.disableComment = disableComment;
    }

    public  int getDisableMailingAndName() {
        return disableMailingAndName;
    }

    public  void setDisableMailingAndName(int disableMailingAndName) {
        this.disableMailingAndName = disableMailingAndName;
    }

    public  int getDisableAddress() {
        return disableAddress;
    }

    public  void setDisableAddress(int disableAddress) {
        this.disableAddress = disableAddress;
    }

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

    public  ApplicationData getApplicationData() {
        return applicationData;
    }

    public void setApplicationData(ApplicationData applicationData) {
        this.applicationData = applicationData;
    }

    public  int getIsInvokedByManu() {
        return isInvokedByManu;
    }

    public void setIsInvokedByManu(int isInvokedByManu) {
        this.isInvokedByManu = isInvokedByManu;
    }


    public static int getIsRevised() {
        return isRevised;
    }

    public static String getTtbidID() {
        return ttbidID;
    }

    public static void setTtbidID(String ttbidID) {
        DataPasser.ttbidID = ttbidID;
    }

    public static int getIsInvokebyReviseMenu() {
        return isInvokebyReviseMenu;
    }
}
