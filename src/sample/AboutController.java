package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;

import javafx.scene.image.ImageView;
import java.awt.*;
import java.io.InputStream;

/**
 * Controller for the "About" page.
 */
public class AboutController {
    ScreenUtil screenUtil = new ScreenUtil();

    @FXML
    private TextArea text;
    @FXML
    ImageView adonayPic, ariPic, elsaPic, jonPic, jacobPic, peterPic, chrisPic, samPic, lucyPic, kienPic;

    public void initialize(){

        try {
            InputStream resource1 = ScreenUtil.class.getClassLoader().getResourceAsStream("resources/ADONAYPIC.png");
            adonayPic.setImage(new javafx.scene.image.Image(resource1, 500.0, 0.0, true, true));

        }
        catch(NullPointerException nullPoint){
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/imageUnavailable.jpg");
            adonayPic.setImage(new javafx.scene.image.Image(resource, 100.0, 0.0, true, true));
            System.out.println("Image Was Not Found");
        }
        try {
            InputStream resource2 = ScreenUtil.class.getClassLoader().getResourceAsStream("resources/ELSAPIC.png");
            elsaPic.setImage(new javafx.scene.image.Image(resource2, 500.0, 0.0, true, true));

        }
        catch(NullPointerException nullPoint){
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/imageUnavailable.jpg");
            elsaPic.setImage(new javafx.scene.image.Image(resource, 100.0, 0.0, true, true));
            System.out.println("Image Was Not Found");
        }
        try {
            InputStream resource3 = ScreenUtil.class.getClassLoader().getResourceAsStream("resources/ARIPIC.png");
            ariPic.setImage(new javafx.scene.image.Image(resource3, 500.0, 0.0, true, true));

        }
        catch(NullPointerException nullPoint){
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/imageUnavailable.jpg");
            ariPic.setImage(new javafx.scene.image.Image(resource, 100.0, 0.0, true, true));
            System.out.println("Image Was Not Found");
        }

        try {
            InputStream resource4 = ScreenUtil.class.getClassLoader().getResourceAsStream("resources/JONPIC.png");
            jonPic.setImage(new javafx.scene.image.Image(resource4, 500.0, 0.0, true, true));

        }
        catch(NullPointerException nullPoint){
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/imageUnavailable.jpg");
            jonPic.setImage(new javafx.scene.image.Image(resource, 100.0, 0.0, true, true));
            System.out.println("Image Was Not Found");
        }
        try {
            InputStream resource5 = ScreenUtil.class.getClassLoader().getResourceAsStream("resources/JACOBPIC.png");
            jacobPic.setImage(new javafx.scene.image.Image(resource5, 500.0, 0.0, true, true));

        }
        catch(NullPointerException nullPoint){
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/imageUnavailable.jpg");
            jacobPic.setImage(new javafx.scene.image.Image(resource, 100.0, 0.0, true, true));
            System.out.println("Image Was Not Found");
        }
        try {
            InputStream resource6 = ScreenUtil.class.getClassLoader().getResourceAsStream("resources/MEPIC.png");
            peterPic.setImage(new javafx.scene.image.Image(resource6, 500.0, 0.0, true, true));
        }
        catch(NullPointerException nullPoint){
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/imageUnavailable.jpg");
            peterPic.setImage(new javafx.scene.image.Image(resource, 100.0, 0.0, true, true));
            System.out.println("Image Was Not Found");
        }

        try {
            InputStream resource7 = ScreenUtil.class.getClassLoader().getResourceAsStream("resources/CHRISPIC.png");
            chrisPic.setImage(new javafx.scene.image.Image(resource7, 500.0, 0.0, true, true));
        }
        catch(NullPointerException nullPoint){
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/imageUnavailable.jpg");
            chrisPic.setImage(new javafx.scene.image.Image(resource, 100.0, 0.0, true, true));
            System.out.println("Image Was Not Found");
        }
        try {
            InputStream resource8 = ScreenUtil.class.getClassLoader().getResourceAsStream("resources/SAMPIC.png");
            samPic.setImage(new javafx.scene.image.Image(resource8, 500.0, 0.0, true, true));
        }
        catch(NullPointerException nullPoint){
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/imageUnavailable.jpg");
            samPic.setImage(new javafx.scene.image.Image(resource, 100.0, 0.0, true, true));
            System.out.println("Image Was Not Found");
        }
        try {
            InputStream resource9 = ScreenUtil.class.getClassLoader().getResourceAsStream("resources/LUCYPIC.png");
            lucyPic.setImage(new javafx.scene.image.Image(resource9, 500.0, 0.0, true, true));
        }
        catch(NullPointerException nullPoint){
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/imageUnavailable.jpg");
            lucyPic.setImage(new javafx.scene.image.Image(resource, 100.0, 0.0, true, true));
            System.out.println("Image Was Not Found");
        }
        try {
            InputStream resource10 = ScreenUtil.class.getClassLoader().getResourceAsStream("resources/KIENPIC.png");
            kienPic.setImage(new javafx.scene.image.Image(resource10, 500.0, 0.0, true, true));
        }
        catch(NullPointerException nullPoint){
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/imageUnavailable.jpg");
            kienPic.setImage(new javafx.scene.image.Image(resource, 100.0, 0.0, true, true));
            System.out.println("Image Was Not Found");
        }

    }

    @FXML
    void handleClose(ActionEvent event) {
        Node currentSource = (Node) event.getSource();
        currentSource.getScene().getWindow().hide();
    }

}
