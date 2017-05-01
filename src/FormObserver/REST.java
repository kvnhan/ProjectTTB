package FormObserver;

import com.jfoenix.controls.JFXTextField;
import sample.ApplicationData;
import sample.DatabaseUtil;

/**
 * Class for impementation of observer pattern for an active form.
 */
public class REST extends AbstractForm {
    DatabaseUtil bd = new DatabaseUtil();

    /**
     * Creates an instance of REST. Holds same information as an abstract form
     * and attaches an observer to the created abstract form.
     */
    public REST(Subject s, JFXTextField serial, JFXTextField address, JFXTextField permit, JFXTextField phone, JFXTextField email, JFXTextField name, JFXTextField permitAddress){
        super(s, serial, address, permit, phone, email, name, permitAddress);
        this.s.attachObserver(this);
    }

    @Override
    /**
     * Updates the information for the active form.
     */
    public void update() {
        ApplicationData a;
        try {
            a = bd.searchFormWithRepID(Integer.parseInt(s.getText()));
            address.setText(a.getAddress());
            serial.setText(a.getSerial());
            permit.setText(a.getPermitNo());
            phone.setText(a.getPhoneNumber());
            name.setText(a.getApplicantName());
            email.setText(a.getEmail());
            permitAddress.setText(a.getPermitAddress());
        }catch (Exception e){

        }
    }
}
