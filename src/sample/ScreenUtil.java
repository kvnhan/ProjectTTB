package sample;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.*;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import static com.sun.org.apache.xalan.internal.utils.SecuritySupport.getResourceAsStream;

/**
 * Created by Adonay on 4/3/2017.
 */
public class ScreenUtil {
    private FXMLLoader fxmlLoader;
    private static final Stage mainWindow = new Stage();
    private Stage alertWindow;
    private Parent root1;
    private Scene previousScene;

    public void switchScene(String fxmlName,String title){
        fxmlLoader = new FXMLLoader(getClass().getResource(fxmlName));

        try{
            root1 = fxmlLoader.load();
            previousScene = mainWindow.getScene();
            mainWindow.setTitle(title);
            mainWindow.setScene(new Scene(root1));
            mainWindow.setResizable(false);
            mainWindow.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void createAlertBox(String title, String message){
        alertWindow = new Stage();

        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle(title);
        alertWindow.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("OK");
        closeButton.setOnAction(e -> alertWindow.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        alertWindow.setScene(scene);
        alertWindow.showAndWait();
    }

    public Scene getPreviousScene() {
        return previousScene;
    }

    public void showAlcoholDetail(AlcoholData alcoholData){
        alertWindow = new Stage();

        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle(alcoholData.getName() + " Details");
        alertWindow.setMinWidth(1000);

        Label name = new Label();
        name.setText(alcoholData.getName());
        name.setStyle("-fx-font-weight: bold");
        name.setFont(Font.font("Veranda", 18));

        Label aid = new Label();
        aid.setText("AID: " + String.valueOf(alcoholData.getAid()));

        Label brandName = new Label();
        brandName.setText("Brand name: " + alcoholData.getBrandName());

        Label alcoholType = new Label();
        alcoholType.setText("Alcohol type : " + String.valueOf(alcoholData.getAlcoholType()));

        Label appellation = new Label();
        appellation.setText("Appellation: " + alcoholData.getAppellation());

        Label sulfiteDesc = new Label();
        sulfiteDesc.setText("Sulfite description: " + alcoholData.getSulfiteDescription());

        Label alchContent = new Label();
        alchContent.setText("Alcohol content: " + String.valueOf(alcoholData.getAlchContent()));

        Label netContent = new Label();
        netContent.setText("Net content: " + String.valueOf(alcoholData.getNetContent()));

        Label healthWarning = new Label();
        healthWarning.setText("Health warning: " + alcoholData.getHealthWarning());

        Label productType = new Label();
        productType.setText("Product type: " + String.valueOf(alcoholData.getProductType()));

        Label classType = new Label();
        classType.setText("Class: " + String.valueOf(alcoholData.getClassType()));

        Label labelLegibility = new Label();
        labelLegibility.setText("Label legibility: " + alcoholData.getLabelLegibility());

        Label labelSize = new Label();
        labelSize.setText("Label size: " + String.valueOf(alcoholData.getLabelSize()));

        Label formulas = new Label();
        formulas.setText("Formula: " + alcoholData.getFormulas());

        Label bottlersInfo = new Label();
        bottlersInfo.setText("Bottler's information: " + alcoholData.getName());

        ImageView image = new ImageView();
        try {
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/"  + alcoholData.getAid() + ".jfif");
            image.setImage(new Image(resource, 500.0, 0.0, true, true));
        }
        catch(NullPointerException nullPoint){
            InputStream resource = ScreenUtil.class.getClassLoader().getResourceAsStream("labels/imageUnavailable.jpg");
            image.setImage(new Image(resource, 100.0, 0.0, true, true));
            System.out.println("Image Was Not Found For " + alcoholData.getBrandName() + "'s "+ alcoholData.getName());
        }


        Button closeButton = new Button("Back");
        closeButton.setOnAction(e -> alertWindow.close());

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(60, 50, 50, 50));
        layout.setSpacing(10);
        layout.getChildren().addAll(name, aid, brandName, alcoholType, appellation,
                sulfiteDesc, alchContent, netContent, healthWarning, productType,
                classType, labelLegibility, labelSize, formulas, bottlersInfo, image, closeButton);
        layout.setAlignment(Pos.CENTER_LEFT);

        Scene scene = new Scene(layout);
        alertWindow.setScene(scene);
        alertWindow.showAndWait();
    }
    public File openFileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose label picture");
        File selectedFile = fileChooser.showOpenDialog(mainWindow);
        return selectedFile;
    }
}
