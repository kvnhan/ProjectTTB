package sample;
import java.util.Date;
/**
 * Acceptance Information is a class containing metadata relating to accepted alcohols.
 *
 *
 */
public class AcceptanceInformation {
    private String status;
    private String approvalDate;
    private String name;
    private String expirationDate;

    /**
     * Acceptance Information Constructor
     * @param approvalDate Date that the application was approved
     * @param name String representing the name of the alcohol being approved
     * @param expirationDate Date of expiration of approval
     * @param status String representing the status of the application
     */
    public AcceptanceInformation(String approvalDate, String name, String expirationDate, String status) {
        this.approvalDate = approvalDate;
        this.name = name;
        this.expirationDate = expirationDate;
        this.status = status;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
