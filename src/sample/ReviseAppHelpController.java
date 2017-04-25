package sample;

/**
 * Created by peternolan on 4/25/17.
 */
public class ReviseAppHelpController {

    ScreenUtil screenUtil = new ScreenUtil();

    public void goBack(){
        screenUtil.switchScene("ReviseMenu.fxml", "Revisions");

    }

}
