package johnsUtil.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import johnsUtil.Components.AutoCompleteComboBoxListener;
import johnsUtil.Main;
import johnsUtil.model.SharedResources.Account;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the main menu.
 */
public class MainMenuController implements Initializable{
    private boolean hasSearchBoxMoved;
    private  boolean isSearchBoxMoving;
    @FXML
    private BorderPane mainPane;
    @FXML
    private JFXButton about;
    @FXML
    private JFXButton team;
    @FXML
    private JFXButton login;
    @FXML
    private JFXButton register;
    @FXML
    private ImageView logo;
    @FXML
    private HBox hbox;
    @FXML
    private JFXButton button;
    @FXML
    private JFXButton searchBtn;
    @FXML
    private TextField searchTF;
    @FXML
    private JFXButton dummy;
    @FXML
    private HBox hBoxForSearch;

    JFXPopup loginPopup;
    JFXPopup registerPopup;

    @FXML private ComboBox searchCombo;

    private final Color darkColor = Color.decode("#C0392B");
    private final Color lightColor = Color.decode("#E74C3C");
    private final int WIDTH = 1200;
    private final int HEIGHT = 800;
    private final int NUM_OF_IMGS = 7;
    private final int SLIDE_FREQ = 5;

    @Override
    /**
     * Initializes the main menu.
     */
    public void initialize(URL location, ResourceBundle resources) {
        hasSearchBoxMoved = false;
        searchCombo.setEditable(true);
        searchCombo.setStyle("-fx-font: 27px \"Calibri\"; -fx-background-radius: 0 30 30 0; -fx-border-radius: 0 30 30 0;");

        searchCombo.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                if(!hasSearchBoxMoved) {
                    moveSearchBox(hBoxForSearch);
                    hasSearchBoxMoved = true;
                }
                if(!isSearchBoxMoving){
                    new AutoCompleteComboBoxListener(searchCombo);
                }
            }
        });


        Image img = new Image(Main.class.getResourceAsStream("/alch.png"));
        logo.setImage(img);

        ImageView img1 = new ImageView(new Image(Main.class.getResourceAsStream("/image1.jpg")));
        ImageView img2 = new ImageView(new Image(Main.class.getResourceAsStream("/image2.jpg")));
        ImageView img3 = new ImageView(new Image(Main.class.getResourceAsStream("/image3.jpg")));
        ImageView img4 = new ImageView(new Image(Main.class.getResourceAsStream("/image4.jpg")));
        ImageView img5 = new ImageView(new Image(Main.class.getResourceAsStream("/image5.png")));
        ImageView img6 = new ImageView(new Image(Main.class.getResourceAsStream("/image6.jpg")));
        ImageView img7 = new ImageView(new Image(Main.class.getResourceAsStream("/image7.jpeg")));


        hbox.getChildren().addAll(img1,img2,img3,img4,img5,img6,img7);
        hbox.setSpacing(5);


        //search.setStyle("-fx-text-fill: #E57373; fx-prompt-text-fill: #E57373;-fx-background-color: white; -fx-background-radius: 0 5 5 0");

        searchBtn.setStyle("-fx-background-radius: 30 0 0 30;-fx-background-color: white; -fx-background-image: url('/Search.png');  -fx-background-repeat: no-repeat; -fx-background-position: center center;");
        searchBtn.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/Search.png"))));

        startAnimation(hbox);

        try {
            loginPopup = new JFXPopup(FXMLLoader.load(getClass().getResource("/johnsUtil/Views/Login.fxml")));
            registerPopup = new JFXPopup(FXMLLoader.load(getClass().getResource("/johnsUtil/Views/Register.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO: add search functionality
        /*
        searchTF.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    System.out.println("Searched using enter");
                    try {
                        handleSearchButton(new ActionEvent(searchBtn, (Node) searchBtn));
                    }
                    catch(IOException e){}
                }
            }
        });
        */
    }

    /**
     * Opens/closes the Login popup.
     * @param event Press of the login button.
     */
    @FXML
    protected void handleLoginButton(ActionEvent event) {
        if (!loginPopup.isShowing()) {
            //Bounds rootBounds = mainPane.getScene().getRoot().getLayoutBounds();
            loginPopup.show(dummy, JFXPopup.PopupVPosition.BOTTOM, JFXPopup.PopupHPosition.LEFT);
                    //(rootBounds.getWidth() - mainPane.getPrefWidth()) / 2 + rootBounds.getWidth()/5,
                    //(rootBounds.getHeight() - mainPane.getPrefHeight()) / 2);
        }
    }

    /**
     * Opens the search screen.
     * @param event Search button clicked.
     * @throws IOException
     */
    @FXML
    protected void handleSearchButton(ActionEvent event) throws SQLException, NoSuchMethodException, IllegalAccessException, InstantiationException, IOException {
        Account.getInstance().setSearch(searchCombo.getEditor().getText());

        Stage primaryStage = (Stage) searchCombo.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("johnsUtil/Views/Home.fxml"));
        primaryStage.getScene().setRoot(root);

        SearchMenuController searchMenuController = new SearchMenuController();
        searchMenuController.search2();
    }

    /**
     * Opens the "Register a new account" screen.
     * @param event Register button is clicked.
     */
    @FXML
    void handleRegister(ActionEvent event) {
        if (!registerPopup.isShowing()) {
            Bounds rootBounds = mainPane.getScene().getRoot().getLayoutBounds();
            registerPopup.show(dummy, JFXPopup.PopupVPosition.BOTTOM, JFXPopup.PopupHPosition.LEFT,(rootBounds.getWidth() - mainPane.getWidth()) / 2 - 200,
                    (rootBounds.getHeight() - mainPane.getHeight()) / 2);;
        }
    }

    /**
     * Animates the images for the main menu.
     * @param hbox Slideshow of pictures.
     */
    private void startAnimation(final HBox hbox) {
        EventHandler<ActionEvent> slideAction = (ActionEvent t) -> {
            TranslateTransition trans = new TranslateTransition(Duration.seconds(1.5), hbox);
            trans.setByX(-WIDTH);
            trans.setInterpolator(Interpolator.EASE_BOTH);
            trans.play();
        };

        EventHandler<ActionEvent> resetAction = (ActionEvent t) -> {
            TranslateTransition trans = new TranslateTransition(Duration.seconds(1), hbox);
            trans.setByX((NUM_OF_IMGS - 1) * WIDTH);
            trans.setInterpolator(Interpolator.EASE_BOTH);
            trans.play();
        };

        List<KeyFrame> keyFrames = new ArrayList<>();
        for (int i = 1; i <= NUM_OF_IMGS; i++) {
            if (i == (NUM_OF_IMGS-3)) {
                keyFrames.add(new KeyFrame(Duration.seconds(i * SLIDE_FREQ), resetAction));
            } else {
                keyFrames.add(new KeyFrame(Duration.seconds(i * SLIDE_FREQ), slideAction));
            }
        }

        Timeline anim = new Timeline(keyFrames.toArray(new KeyFrame[NUM_OF_IMGS]));
        anim.setCycleCount(Timeline.INDEFINITE);
        anim.playFromStart();
    }

    @FXML
    public void handleTeamButton(ActionEvent e){

    }
    @FXML
    public void handleAboutButton(ActionEvent e){

    }

    /**
     * Allows one to move the search box around.
     *
     */
    public void moveSearchBox(HBox hbox){
        TranslateTransition tt = new TranslateTransition(Duration.millis(500), hbox);
        tt.setByY(-300f);

        tt.statusProperty().addListener(new ChangeListener<Animation.Status>() {
            @Override
            public void changed(ObservableValue<? extends Animation.Status> observableValue,
                                Animation.Status oldValue, Animation.Status newValue) {
                isSearchBoxMoving = (newValue== Animation.Status.RUNNING);
            }
        });

        tt.play();
    }
}