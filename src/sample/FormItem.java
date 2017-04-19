package sample;

/**
 * Created by John on 4/19/2017.
 */
public class FormItem implements TItem {
    private String ttbid;
    private String fancyName;
    private String brandName;

    public FormItem(String fancyName, String ttbid, String brandName){
        this.fancyName = fancyName;
        this.ttbid = ttbid;
        this.brandName = brandName;
    }

    @Override
    public String getData1() {
        return ttbid;
    }

    @Override
    public String getData2() {
        return brandName;
    }

    @Override
    public String getData3() {
        return fancyName;
    }

    @Override
    public String getText1() {
        return "TTBID";
    }

    @Override
    public String getText2() {
        return "Brandname";
    }

    @Override
    public String getText3() {
        return "Fanciful Name";
    }

    public String toString(){
        return ttbid;
    }
}
