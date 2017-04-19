package sample;

/**
 * Created by John on 4/19/2017.
 */
public class AccountItem implements TItem {
    private int aid;
    private String username;
    private String userType;

    public AccountItem(int aid, String username){
        this.aid = aid;
        this.username = username;
        this.userType = "Government Worker";
    }

    @Override
    public String getData1() {
        if(aid == 0){
            return "";
        }
        else{
            return aid+"";
        }
    }

    @Override
    public String getData2() {
        return username;
    }

    @Override
    public String getData3() {
        return userType;
    }

    public String toString(){
        return username;
    }

}
