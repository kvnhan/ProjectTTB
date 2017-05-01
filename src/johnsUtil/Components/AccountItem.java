package johnsUtil.Components;

import javafx.scene.control.TreeItem;

/**
 * Class for containing a persistent account item for use in the application.
 */
public class AccountItem extends TreeItem<TItem> implements TItem {
    private int aid;
    private String username;
    private String userType;

    /**
     * Creates an AccountItem.
     * @param aid Account ID number.
     * @param username Account username.
     */
    public AccountItem(int aid, String username){
        this.aid = aid;
        this.username = username;
        this.userType = "Government Worker";
    }

    /**
     * Gets the AID for the account.
     * @return Returns a string representing the account ID.
     */
    @Override
    public String getData1() {
        if(aid == 0){
            return "";
        }
        else{
            return aid+"";
        }
    }

    /**
     * Gets the username for the account.
     * @return Returns the account username.
     */
    @Override
    public String getData2() {
        return username;
    }

    /**
     * Returns the type of the user.
     * @return Returns a string representing the type of the account.
     */
    @Override
    public String getData3() {
        return userType;
    }

    /**
     * Gets a string.
     * @return Returns the string "Account ID".
     */
    @Override
    public String getText1() {
        return "Account ID";
    }

    /**
     * Gets a string.
     * @return Returns the string "Username".
     */
    @Override
    public String getText2() {
        return "Username";
    }

    /**
     * Gets a string.
     * @return Returns the string "UserType".
     */
    @Override
    public String getText3() {
        return "UserType";
    }

    public String toString(){
        return username;
    }

}
