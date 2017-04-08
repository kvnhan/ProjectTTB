package sample;
import java.util.Date;

/**
 * Created by Kien Nhan on 4/3/2017.
 */
public class acceptanceInformation {

    private String status;

    private Date approvalDate;
    private String name;
    private Date expirationDate;

    public acceptanceInformation(Date approvalDate, String name, Date expirationDate, String status) {
        this.approvalDate = approvalDate;
        this.name = name;
        this.expirationDate = expirationDate;
        this.status = status;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
