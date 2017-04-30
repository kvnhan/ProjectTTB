package FormObserver;

import com.jfoenix.controls.JFXTextField;
import sample.ApplicationData;
import sample.DatabaseUtil;

/**
 * Created by Kien Nhan on 4/29/2017.
 */
public class REST extends AbstractForm {
    DatabaseUtil bd = new DatabaseUtil();
    public REST(Subject s, JFXTextField serial, JFXTextField address, JFXTextField permit, JFXTextField phone, JFXTextField email, JFXTextField name, JFXTextField permitAddress){
        super(s, serial, address, permit, phone, email, name, permitAddress);
        this.s.attachObserver(this);
    }

    @Override
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
