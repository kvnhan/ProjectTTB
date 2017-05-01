package johnsUtil.Components;

/**
 * Form information.
 */
public class FormItem implements TItem {
    private String ttbid;
    private String fancyName;
    private String brandName;

    /**
     * Creates a form item.
     * @param fancyName Name of alcohol.
     * @param ttbid ID for application.
     * @param brandName Brand name of alcohol.
     */
    public FormItem(String fancyName, String ttbid, String brandName){
        this.fancyName = fancyName;
        this.ttbid = ttbid;
        this.brandName = brandName;
    }

    /**
     * Gets the TTBID.
     * @return Returns the TTBID.
     */
    @Override
    public String getData1() {
        return ttbid;
    }

    /**
     * Gets the brand name of the alcohol.
     * @return Returns the brand name.
     */
    @Override
    public String getData2() {
        return brandName;
    }

    /**
     * Gets the name of the alcohol.
     * @return Returns the alcohol name.
     */
    @Override
    public String getData3() {
        return fancyName;
    }

    /**
     * Gets a string.
     * @return Returns the string "TTBID".
     */
    @Override
    public String getText1() {
        return "TTBID";
    }

    /**
     * Gets a string.
     * @return Returns the string "Brandname".
     */
    @Override
    public String getText2() {
        return "Brandname";
    }

    /**
     * Gets a string.
     * @return Returns the string "Fanciful Name".
     */
    @Override
    public String getText3() {
        return "Fanciful Name";
    }

    public String toString(){
        return ttbid;
    }
}
