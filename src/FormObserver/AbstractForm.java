package FormObserver;

import com.jfoenix.controls.JFXTextField;

/**
 * Created by Kien Nhan on 4/29/2017.
 */
public abstract class AbstractForm {
    protected Subject s;
    protected JFXTextField serial, address, permit, phone,  email,  name, permitAddress;

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
