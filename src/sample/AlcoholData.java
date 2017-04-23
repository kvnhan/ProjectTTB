package sample;

import java.sql.Date;

/**
 * Contains data relating to different alcohols.
 */
public class AlcoholData {

    private int aid;
    private String name;
    private String appellation;
    private String sulfiteDescription;
    private double alchContent;
    private double netContent;
    private String healthWarning;
    private int productType;
    private int classType;
    private String labelLegibility;
    private String labelSize;
    private String formulas;
    private int alcoholType;
    private String bottlersInfo;
    private String brandName;
    private String status;
    private int wineVintage;
    private double phLevel;
    private String grapeVarietal;
    private String infoOnBottle;
    private String sourceOfProduct;
    private Date dateApproved;
    private int originCode;


    /**
     * Creates an AlcoholData object.
     * @param aid Account ID linked to the object.
     * @param name Fanciful name of the alcohol.
     * @param brandName Brand name for the alcohol.
     * @param appellation For wines, the area of origination for the alcohol.
     * @param sulfiteDescription Description of the sulfite content of the alcohol.
     * @param alchContent Proof of the alcohol.
     * @param netContent Net alcohol content.
     * @param healthWarning Text of the alcohol's label's health warning.
     * @param productType Type of product.
     * @param classType Type of class? not sure
     * @param labelLegibility Metric for legibility of the label.
     * @param labelSize Actual size of the label.
     * @param formulas Formulas for the alcoholic beverage.
     * @param alcoholType Type of alcohol.
     * @param bottlersInfo General information about the bottler.
     */
    public AlcoholData(int aid, String name, String appellation, String sulfiteDescription, double alchContent, double netContent, String healthWarning, int productType, int classType, String labelLegibility, String labelSize, String formulas, int alcoholType, String bottlersInfo, String brandName, String status, int wineVintage, double phLevel, String grapeVarietal, String infoOnBottle, String sourceOfProduct, Date dateApproved, int originCode) {
        this.aid = aid;
        this.name = name;
        this.appellation = appellation;
        this.sulfiteDescription = sulfiteDescription;
        this.alchContent = alchContent;
        this.netContent = netContent;
        this.healthWarning = healthWarning;
        this.productType = productType;
        this.classType = classType;
        this.labelLegibility = labelLegibility;
        this.labelSize = labelSize;
        this.formulas = formulas;
        this.alcoholType = alcoholType;
        this.bottlersInfo = bottlersInfo;
        this.brandName = brandName;
        this.status = status;
        this.wineVintage = wineVintage;
        this.phLevel = phLevel;
        this.grapeVarietal = grapeVarietal;
        this.infoOnBottle = infoOnBottle;
        this.sourceOfProduct = sourceOfProduct;
        this.dateApproved = dateApproved;
        this.originCode = originCode;
    }


    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppellation() {
        return appellation;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation;
    }

    public String getSulfiteDescription() {
        return sulfiteDescription;
    }

    public void setSulfiteDescription(String sulfiteDescription) {
        this.sulfiteDescription = sulfiteDescription;
    }

    public double getAlchContent() {
        return alchContent;
    }

    public void setAlchContent(double alchContent) {
        this.alchContent = alchContent;
    }

    public double getNetContent() {
        return netContent;
    }

    public void setNetContent(double netContent) {
        this.netContent = netContent;
    }

    public String getHealthWarning() {
        return healthWarning;
    }

    public void setHealthWarning(String healthWarning) {
        this.healthWarning = healthWarning;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public int getClassType() {
        return classType;
    }

    public void setClassType(int classType) {
        this.classType = classType;
    }

    public String getLabelLegibility() {
        return labelLegibility;
    }

    public void setLabelLegibility(String labelLegibility) {
        this.labelLegibility = labelLegibility;
    }

    public String getLabelSize() {
        return labelSize;
    }

    public void setLabelSize(String labelSize) {
        this.labelSize = labelSize;
    }

    public String getFormulas() {
        return formulas;
    }

    public void setFormulas(String formulas) {
        this.formulas = formulas;
    }

    public int getAlcoholType() {
        return alcoholType;
    }

    public void setAlcoholType(int alcoholType) {
        this.alcoholType = alcoholType;
    }

    public String getBottlersInfo() {
        return bottlersInfo;
    }

    public void setBottlersInfo(String bottlersInfo) {
        this.bottlersInfo = bottlersInfo;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWineVintage() {
        return wineVintage;
    }

    public void setWineVintage(int wineVintage) {
        this.wineVintage = wineVintage;
    }

    public double getPhLevel() {
        return phLevel;
    }

    public void setPhLevel(double phLevel) {
        this.phLevel = phLevel;
    }

    public String getGrapeVarietal() {
        return grapeVarietal;
    }

    public void setGrapeVarietal(String grapeVarietal) {
        this.grapeVarietal = grapeVarietal;
    }

    public String getInfoOnBottle() {
        return infoOnBottle;
    }

    public void setInfoOnBottle(String infoOnBottle) {
        this.infoOnBottle = infoOnBottle;
    }

    public String getSourceOfProduct() {
        return sourceOfProduct;
    }

    public void setSourceOfProduct(String sourceOfProduct) {
        this.sourceOfProduct = sourceOfProduct;
    }

    public Date getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(Date dateApproved) {
        this.dateApproved = dateApproved;
    }

    public int getOriginCode() {
        return originCode;
    }

    public void setOriginCode(int originCode) {
        this.originCode = originCode;
    }
}
