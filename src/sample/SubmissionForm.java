package sample;

/**
 * Created by Kien Nhan on 4/3/2017.
 */
public class SubmissionForm {
    private String formID;
    private enum status{
        INCOMPLETE,
        SUBMITED,
        ACCEPTED,
        REJECTED

    }
    acceptanceInformation acceptanceInfo;

    public SubmissionForm(String formID, acceptanceInformation acceptanceInfo) {
        this.formID = formID;
        this.acceptanceInfo = acceptanceInfo;
    }

    public String getFormID() {
        return formID;
    }

    public void setFormID(String formID) {
        this.formID = formID;
    }

    public acceptanceInformation getAcceptanceInfo() {
        return acceptanceInfo;
    }

    public void setAcceptanceInfo(acceptanceInformation acceptanceInfo) {
        this.acceptanceInfo = acceptanceInfo;
    }
}
