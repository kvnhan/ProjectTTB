package sample;

import sun.reflect.generics.tree.Tree;

/**
 * Created by John on 4/19/2017.
 */
public class FormItem implements TreeItem {
    private String ttbid;
    private String fid;
    private String brandName;

    public FormItem(String fid, String ttbid, String brandName){
        this.fid = fid;
        this.ttbid = ttbid;
        this.brandName = brandName;
    }

    @Override
    public String getData1() {
        return fid;
    }

    @Override
    public String getData2() {
        return ttbid;
    }

    @Override
    public String getData3() {
        return brandName;
    }

    public String toString(){
        return ttbid;
    }
}
