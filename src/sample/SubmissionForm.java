package sample;

/**
 * Contains information about submissions.
 */
public class SubmissionForm {
    private int formID;
    private enum status{
        INCOMPLETE,
        SUBMITTED,
        ACCEPTED,
        REJECTED,
        UNASSIGNED,
        ASSIGNED,

    }
    acceptanceInformation acceptanceInfo;

    /**
     * Constructor for submission form object.
     * @param formID Int representing the ID of a form.
     * @param acceptanceInfo Information about the form's acceptance.
     */
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
