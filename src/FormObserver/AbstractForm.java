package FormObserver;

import com.jfoenix.controls.JFXTextField;

/**
 * Class for holding basic information contained by forms.
 */
public abstract class AbstractForm {
    protected Subject s;
    protected JFXTextField serial, address, permit, phone,  email,  name, permitAddress;

    /**
     * Creates an instance of an abstract form.
     * @param s Represents the active form.
     * @param serial Serial number of the active form.
     * @param address Address attached to the active form.
     * @param permit Permit number for the form.
     * @param phone Phone number of the applicant.
     * @param email Email address of the applicant.
     * @param name Name of the applicant.
     * @param permitAddress Address attached to the permit.
     */
    public AbstractForm(Subject s, JFXTextField serial, JFXTextField address, JFXTextField permit, JFXTextField phone, JFXTextField email, JFXTextField name, JFXTextField permitAddress) {
        this.s = s;
        this.serial = serial;
        this.address = address;
        this.permit = permit;
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.permitAddress = permitAddress;
    }

    public abstract void update();
}
