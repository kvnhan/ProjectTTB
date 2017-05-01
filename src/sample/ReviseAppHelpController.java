package sample;

/**
 * Help screen for revisions.
 */
public class ReviseAppHelpController {

    ScreenUtil screenUtil = new ScreenUtil();

    /**
     * Returns users to the revisions menu.
     */
    public void goBack(){
        screenUtil.switchScene("ReviseMenu.fxml", "Revisions");

    }

}
