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
    AcceptanceInformation acceptanceInfo;

    /**
     * Constructor for submission form object.
     * @param formID Int representing the ID of a form.
     * @param acceptanceInfo Information about the form's acceptance.
     */
    public SubmissionForm(int formID, AcceptanceInformation acceptanceInfo) {
        this.formID = formID;
        this.acceptanceInfo = acceptanceInfo;
    }

    public int getFormID() {
        return formID;
    }

    public void setFormID(int formID) {
        this.formID = formID;
    }

    public AcceptanceInformation getAcceptanceInfo() {
        return acceptanceInfo;
    }

    public void setAcceptanceInfo(AcceptanceInformation acceptanceInfo) {
        this.acceptanceInfo = acceptanceInfo;
    }
}
