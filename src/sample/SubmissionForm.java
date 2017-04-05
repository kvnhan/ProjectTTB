package sample;

/**
 * Created by Kien Nhan on 4/3/2017.
 */
public class SubmissionForm {
    private int formID;
    private enum status{
        INCOMPLETE,
        SUBMITED,
        ACCEPTED,
        REJECTED,
        UNASSIGNED,
        ASSIGNED,

    }
    acceptanceInformation acceptanceInfo;

    public SubmissionForm(int formID, acceptanceInformation acceptanceInfo) {
        this.formID = formID;
        this.acceptanceInfo = acceptanceInfo;
    }

    public int getFormID() {
        return formID;
    }

    public void setFormID(int formID) {
        this.formID = formID;
    }

    public acceptanceInformation getAcceptanceInfo() {
        return acceptanceInfo;
    }

    public void setAcceptanceInfo(acceptanceInformation acceptanceInfo) {
        this.acceptanceInfo = acceptanceInfo;
    }
}
