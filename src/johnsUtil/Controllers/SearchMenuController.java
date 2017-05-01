package johnsUtil.Controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.*;
import java.util.ArrayList;

import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import johnsUtil.model.SharedResources.Account;
import johnsUtil.model.SharedResources.Database;
import johnsUtil.model.SharedResources.Screen;
import sample.AlcoholData;
import sample.DatabaseUtil;
import sample.ImageViewPane;
import sample.ScreenUtil;

import static javafx.application.Application.launch;

/**
 * Controller for the search menu.
 */
public class SearchMenuController {

    private String searchText;
    private @FXML CheckBox isWineBox, isBeerBox, isDistilledBox;
    private @FXML TextField searchTextField;
    private @FXML RadioButton normalSearchRadio, intersectSearchRadio, unionSearchRadio;
    private @FXML Button helpSearchButton;
    private @FXML Button searchButton;
    private @FXML Label Result;
    //private @FXML JFXHamburger Back;

    private @FXML RadioButton csvDownload, tabDownload, customDownload;
    private @FXML TextField CustomDelimiter;// customDirectoryField;
    //  private @FXML CheckBox CustomDirectoryCheckBox;
    private @FXML JFXComboBox<String> choiceBox;
    private @FXML DatePicker startDate, endDate;

    private @FXML TableColumn idColumn, nameColumn, brandNameColumn, alcoholTypeColumn, locationColumn, contentColumn;
    private @FXML TableView table;
    private @FXML GridPane resultsMainGridPane;
    private GridPane alcoholLabelGridPane = new GridPane();
    private ScrollPane imageScrollPane = new ScrollPane(alcoholLabelGridPane);
    private @FXML ToggleGroup toggleView;

    private ScreenUtil screenUtil = Screen.getInstance();
    private int alcoholChoice = 0;
    private final int BEER = 1;
    private final int WINE = 2;
    private final int DISTILLED = 3;

    private List<AlcoholData> alcoholDataList = new ArrayList<AlcoholData>();
    private ObservableList<AlcoholData> observableList;

    private DatabaseUtil dbUtil = Database.getInstance();
    private String choiceSearch;
    private boolean hasViewChanged = false;
    private boolean isSearchInImageView = false;
    private boolean foundImage = false;


    private javafx.scene.image.Image alcoholImage;
    private ImageView alcoholImageView;
    private int imageResultPageNumber;
    private int resultsPerPage = 15;
    private int noOfPagesGenerated;
    private int generatedResultBound;
    private @FXML HBox pageControlsHBox;
    private @FXML ToggleButton previousPageButton;
    private @FXML Button nextPageButton;
    private @FXML Text pageNoText;

    /**
     * Initializes the search menu.
     */
    @FXML
    public void initialize() throws SQLException, NoSuchMethodException, IllegalAccessException, InstantiationException, IOException{
        String searchTemp = "";
        if(Account.getInstance().getSearch().trim().length() > 0){
            searchTemp = Account.getInstance().getSearch();
            Account.getInstance().setSearch("");
        }
        searchTextField.setText(searchTemp);
//        HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(Back);
//        burgerTask2.setRate(-1);
//        Back.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> {
//            burgerTask2.setRate(burgerTask2.getRate() * -1);
//            burgerTask2.play();
//        });
//
//        Back.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
//            screenUtil.switchScene("MainMenu.fxml", "Main Menu");
//        });

        createPageNavigationButtons();

        pageControlsHBox.setVisible(false);

        //enableAutomaticSearch();

        toggleView.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(toggleView.getSelectedToggle() != null){
                    String selectedView = ((JFXRadioButton)toggleView.getSelectedToggle()).getText();
                    if(selectedView.equals("List View") && hasViewChanged){
                        isSearchInImageView = false;
                        Result.setText("Showing " + alcoholDataList.size() + " search results.");
                        resultsMainGridPane.getChildren().remove(imageScrollPane);
                        resultsMainGridPane.getChildren().add(table);
                        //   enableAutomaticSearch();
                    }else if(selectedView.equals("Image View")){
                        hasViewChanged = true;
                        isSearchInImageView = true;
                        resultsMainGridPane.getChildren().remove(table);
                        //    disableAutomaticSearch();
                        displayResultsInThumbnail();
                    }
                }
            }
        });


        // to get information (alcohol data) from the double clicked row in the table
        table.setRowFactory(tv -> {
            TableRow<AlcoholData> row = new TableRow<AlcoholData>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (! row.isEmpty())){
                    AlcoholData rowData = row.getItem();
                    screenUtil.pullUpAlcoholDetails(rowData);
                }
            });
            row.setTooltip(new Tooltip("Double click to see more detail"));
            return row;
        });
        //adds options
        choiceBox.getItems().addAll("All", "Wine", "Beer", "Distilled", "Wine and Beer", "Wine and Distilled", "Beer and Distilled", "ID", "Name", "Brand Name", "Location", "Alcohol Content");
        //sets default vaule
        choiceBox.setValue("All");
    }

    /**
     * Displays results for a search.
     */
    public void displayResults() {
        table.getColumns().clear();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("aid"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        brandNameColumn.setCellValueFactory(new PropertyValueFactory<>("Brand Name"));
        alcoholTypeColumn.setCellValueFactory(new PropertyValueFactory<>("AlcoholType"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("Appellation"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("AlchContent"));
        table.setItems(this.getObservableList());
        table.getColumns().addAll(idColumn, nameColumn, brandNameColumn, alcoholTypeColumn, locationColumn, contentColumn);
    }

    /**
     * Displays results for a search in a thumbnail.
     */
    public void displayResultsInThumbnail(){
        alcoholLabelGridPane = new GridPane();
        alcoholLabelGridPane.setHgap(10);
        alcoholLabelGridPane.setVgap(10);
        alcoholLabelGridPane.setPadding(new Insets(0,0,0,8));
        //alcoholLabelGridPane.setBackground(new Background(new BackgroundFill(Color.web("#e74c3c"), CornerRadii.EMPTY, Insets.EMPTY)));
        imageScrollPane = new ScrollPane(alcoholLabelGridPane);
        imageScrollPane.setFitToWidth(true);
        resultsMainGridPane.getChildren().add(imageScrollPane);

        noOfPagesGenerated = (int)Math.ceil((double)alcoholDataList.size()/(double)resultsPerPage);

        if(noOfPagesGenerated > 1){
            Result.setText("Showing " + resultsPerPage + " results per page. " + noOfPagesGenerated + " pages have been generated.");
        }else{
            Result.setText("Showing " + alcoholDataList.size() + " search results.");
        }

        pageControlsHBox.setVisible(alcoholDataList.size() > resultsPerPage);

        int floor = noOfPagesGenerated-1;

        if(floor*resultsPerPage >= (imageResultPageNumber+1)*resultsPerPage){
            generatedResultBound = (imageResultPageNumber+1)*resultsPerPage;
        }else{
            generatedResultBound = floor*resultsPerPage + (int) Math.round((((double)alcoholDataList.size()/(double)resultsPerPage)-floor)*resultsPerPage);
        }

        pageNoText.setText("Page " + (imageResultPageNumber + 1));

        int imageCol = 0;
        int imageRow = 0;
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(33);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(33);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(33);

        alcoholLabelGridPane.getColumnConstraints().addAll(col1, col2, col3);

        for (int i = imageResultPageNumber*resultsPerPage; i < generatedResultBound; i++) {
            final AlcoholData currentAlcoholData = alcoholDataList.get(i);
            try {
                InputStream inputStream = SearchMenuController.class.getClassLoader().getResourceAsStream("labels/" + String.valueOf(alcoholDataList.get(i).getAid()) + ".jfif");
                if (inputStream == null) {
                    int alcoholType = currentAlcoholData.getAlcoholType();
                    if (alcoholType == 1) {
                        inputStream = SearchMenuController.class.getClassLoader().getResourceAsStream("labels/beer_default.jfif");
                    } else if (alcoholType == 2) {
                        inputStream = SearchMenuController.class.getClassLoader().getResourceAsStream("labels/wine_default.jfif");
                    } else if (alcoholType == 3) {
                        inputStream = SearchMenuController.class.getClassLoader().getResourceAsStream("labels/distilled_default.jfif");
                    }
                }
                alcoholImage = new javafx.scene.image.Image(inputStream);
                alcoholImageView = new ImageView();

                alcoholImageView.setImage(alcoholImage);
                foundImage = true;

            }catch (Exception e){
                foundImage = true;
                try {
                    if (foundImage == false) {
                        alcoholImageView = new ImageView();
                        String path = getPath();
                        File file = new File(path + "/" + alcoholDataList.get(i).getAid() + ".jpg");
                        javafx.scene.image.Image image1 = new javafx.scene.image.Image(file.toURI().toString());
                        alcoholImageView.setImage(image1);
                    }
                }catch (Exception e2){

                }
            }

            alcoholImageView.setFitWidth(260);
            alcoholImageView.setFitHeight(260);

            ImageViewPane imageViewPane = new ImageViewPane(alcoholImageView);

            imageViewPane.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    int depth = 150;

                    DropShadow borderGlow= new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.web("#e74c3c"));
                    borderGlow.setWidth(depth);
                    borderGlow.setHeight(depth);

                    imageViewPane.setEffect(borderGlow);
                }
            });

            imageViewPane.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    imageViewPane.setEffect(null);
                }
            });

            imageViewPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    screenUtil.pullUpAlcoholDetails(currentAlcoholData);
                }
            });

            alcoholLabelGridPane.add(imageViewPane, imageCol, imageRow);

            imageCol++;
            if (imageCol > 2) {
                imageCol = 0;
                imageRow++;
            }
        }

    }

//    /**
//     * Returns a user to the main menu.
//     * @param event Back button is pressed.
//     */
//    public void back (ActionEvent event){
//        Back.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
//            screenUtil.switchScene("MainMenu.fxml", "Main Menu");
//        });
//    }

    public ObservableList<AlcoholData> getObservableList() {
        return observableList;
    }

    /**
     * Executes a search.
     * @param event Search button is pressed.
     * @throws SQLException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws IOException
     */
    public void search(ActionEvent event) throws SQLException, NoSuchMethodException, IllegalAccessException, InstantiationException, IOException{
        search2();
    }

    public void search2() throws SQLException, NoSuchMethodException, IllegalAccessException, InstantiationException, IOException{
        imageResultPageNumber = 0;
        noOfPagesGenerated = 0;
        previousPageButton.setDisable(true);
        nextPageButton.setDisable(false);

        if(normalSearchRadio.isSelected()){
            searchDatabase();
        }
        else if(intersectSearchRadio.isSelected()){
            searchIntersect();
        }
        else if(unionSearchRadio.isSelected()){
            searchUnion();
        }
        observableList = FXCollections.observableList(alcoholDataList);
        Result.setText("Showing " + observableList.size() + " search results.");
        displayResults();
        if(isSearchInImageView){
            displayResultsInThumbnail();
        }
    }




    //TODO add other spirits

    /**
     * Searches the database for info.
     * @throws SQLException
     */
    private void searchDatabase() throws SQLException {
        choiceSearch = choiceBox.getValue();
        if (choiceSearch.equals("All")){
            alcoholDataList = dbUtil.searchAlcoholWithType(BEER);
            alcoholDataList.addAll(dbUtil.searchAlcoholWithType(WINE));
            alcoholDataList.addAll(dbUtil.searchAlcoholWithType(DISTILLED));
            alcoholDataList = intersectAlcoholData(searchByChoice(), alcoholDataList);
        }
        else if (choiceSearch.equals("Wine and Beer")){
            alcoholDataList = dbUtil.searchAlcoholWithType(BEER);
            alcoholDataList.addAll(dbUtil.searchAlcoholWithType(WINE));
            alcoholDataList = intersectAlcoholData(searchByChoice(), alcoholDataList);
        }
        else if (choiceSearch.equals("Beer and Distilled")) {
            alcoholDataList = dbUtil.searchAlcoholWithType(BEER);
            alcoholDataList.addAll(dbUtil.searchAlcoholWithType(DISTILLED));
            alcoholDataList = intersectAlcoholData(searchByChoice(), alcoholDataList);
        }
        else if (choiceSearch.equals("Wine and Distilled")) {
            alcoholDataList = dbUtil.searchAlcoholWithType(WINE);
            alcoholDataList.addAll(dbUtil.searchAlcoholWithType(DISTILLED));
            alcoholDataList = intersectAlcoholData(searchByChoice(), alcoholDataList);
        }
        else if(choiceSearch.equals("Wine") || choiceSearch.equals("Beer") || choiceSearch.equals("Distilled")){
            if (choiceSearch.equals("Wine")){
                alcoholChoice = 2;
            }
            else if (choiceSearch.equals("Beer")){
                alcoholChoice = 1;
            }
            else if (choiceSearch.equals("Distilled")){
                alcoholChoice = 3;
            }
            alcoholDataList = dbUtil.searchAlcoholWithType(alcoholChoice);
            alcoholDataList = intersectAlcoholData(searchByChoice(), alcoholDataList);
        }
        else {
            alcoholDataList = searchByChoice();
        }

        if(startDate.getValue() != null && endDate.getValue() != null){
            alcoholDataList = intersectAlcoholData(dbUtil.searchAlcoholByDate(Date.valueOf(startDate.getValue()), Date.valueOf(endDate.getValue())), alcoholDataList);
        }else if(startDate.getValue() != null){
            alcoholDataList = intersectAlcoholData(dbUtil.searchAlcoholByDate(Date.valueOf(startDate.getValue()), "AFTER"), alcoholDataList);
        }else if(endDate.getValue() != null){
            alcoholDataList = intersectAlcoholData(dbUtil.searchAlcoholByDate(Date.valueOf(endDate.getValue()), "BEFORE"), alcoholDataList);
        }
    }

    /**
     * Searches by different data types.
     * @return Returns a list of alcohol data.
     * @throws SQLException
     */
    public List<AlcoholData> searchByChoice() throws SQLException {
        choiceSearch = choiceBox.getValue();
        List<AlcoholData> adl = new ArrayList<>();
        searchText = searchTextField.getText();
        if(searchText.trim().isEmpty()){
            adl = dbUtil.getAllAlcoholEntries();
        }else{
            if (choiceSearch.equals("ID")) {
                int searchIntegerValue = Integer.parseInt(searchText);
                adl = dbUtil.searchAlcoholWithID(searchIntegerValue);
            } else if (choiceSearch.equals("Name")) {
                adl = dbUtil.searchAlcoholName(searchText);
            } else if (choiceSearch.equals("Brand Name")) {
                adl = dbUtil.searchAlcoholBrand(searchText);
            } else if (choiceSearch.equals("Location Name")) {
                adl = dbUtil.searchAlcoholAppellation(searchText);
            } else if (choiceSearch.equals("Alcohol Content")) {
                double searchDoubleValue = Double.parseDouble(searchText.trim());
                adl = dbUtil.searchAlcoholContent(searchDoubleValue);
            } else {
                adl = searchAllAlcoholFields(searchText);
            }

        }
        return adl;
    }

    /**
     * Does a union search, i.e. the results of one search are added to the results
     * of a previous search.
     * @throws SQLException
     */
    public void searchUnion() throws SQLException
    {
        List<AlcoholData> previousAlcoholDataResults = alcoholDataList;
        searchDatabase();

        alcoholDataList = mergeAlcoholData(previousAlcoholDataResults, alcoholDataList);
    }

    /**
     * Does an intersection search, i.e. the results of one search come only from the
     * result set of a previous search.
     * @throws SQLException
     */
    public void searchIntersect() throws SQLException
    {

        List<AlcoholData> previousAlcoholDataResults = alcoholDataList;
        searchDatabase();

        alcoholDataList = intersectAlcoholData(previousAlcoholDataResults, alcoholDataList);
    }

    public List<AlcoholData> searchAllAlcoholFields(String searchText) throws SQLException{
        List<AlcoholData> finalResultList = new ArrayList<AlcoholData>();

        try{
            int searchAlcTypeInput = Integer.parseInt(searchText);
            List<AlcoholData> alcoholTypeResults = dbUtil.searchAlcoholWithType(searchAlcTypeInput);
            finalResultList = mergeAlcoholData(alcoholTypeResults, finalResultList);
        }catch (Exception e){
            System.out.println("Could not search type field using input");
        }
        List<AlcoholData> alcoholBrandResults = dbUtil.searchAlcoholBrand(searchText);
        finalResultList = mergeAlcoholData(alcoholBrandResults, finalResultList);
        try{
            int searchIDInput = Integer.parseInt(searchText);
            List<AlcoholData> alcoholIDResults = dbUtil.searchAlcoholWithID(searchIDInput);
            finalResultList = mergeAlcoholData(alcoholIDResults, finalResultList);
        }catch (Exception e){
            System.out.println("Could not search id field using input");
        }
        List<AlcoholData> alcoholNameResults = dbUtil.searchAlcoholName(searchText);
        if(alcoholNameResults.isEmpty()) {
            System.out.println("RESULT IS EMPTY");
        }else{
            System.out.println("RESULT IS NOT EMPTY");
        }
        finalResultList = mergeAlcoholData(alcoholNameResults, finalResultList);
        if(finalResultList.isEmpty()) {
            System.out.println("RESULT IS EMPTY");
        }else{
            System.out.println("RESULT IS NOT EMPTY");
        }
        List<AlcoholData> alcoholAppelationResults = dbUtil.searchAlcoholAppellation(searchText);
        finalResultList = mergeAlcoholData(alcoholAppelationResults, finalResultList);
        try {
            double searchAlcContInput = Double.parseDouble(searchText);
            List<AlcoholData> alcoholContentResults = dbUtil.searchAlcoholContent(searchAlcContInput);
            finalResultList = mergeAlcoholData(alcoholContentResults, finalResultList);
        }catch (Exception e){
            System.out.println("Could not search alcohol content field using input");
        }
        return finalResultList;

    }

    /**
     * Merges alcohol data lists.
     * @param listToAdd List to merge.
     * @param originalList Original alcohol data list.
     * @return Returns the merged list.
     */
    public List<AlcoholData> mergeAlcoholData(List<AlcoholData> listToAdd, List<AlcoholData> originalList){
        List<AlcoholData> mergedAlcoholData = originalList;

        if(originalList.size() == 0){
            return listToAdd;
        }

        mergedAlcoholData.addAll(combineAlcoholData(listToAdd, originalList, true));

        return mergedAlcoholData;
    }

    /**
     * Gets the intersection of two lists of alcohol data.
     * @param listToAdd List to intersect with original.
     * @param originalList Original list.
     * @return Returns intersected list.
     */
    public List<AlcoholData> intersectAlcoholData(List<AlcoholData> listToAdd, List<AlcoholData> originalList){
        return combineAlcoholData(listToAdd, originalList, false);
    }

    // combines alcohol data results. to merge assign boolean input to true. to intersect assign boolean input to false.

    /**
     * Combines two lists of alcohol data.
     * @param listToAdd List to add to original.
     * @param originalList Original list.
     * @param isMerge Merges two lists if true, intersects them if false.
     * @return Returns merged list.
     */
    public List<AlcoholData> combineAlcoholData(List<AlcoholData> listToAdd, List<AlcoholData> originalList, boolean isMerge){
        List<AlcoholData> combinedAlcoholData = new ArrayList<>();
        boolean toAdd;


        for(int i=0; i < listToAdd.size(); i++) {
            toAdd = isMerge;

            for(int j=0; j < originalList.size(); j++) {
                if(originalList.get(j).getAid() == listToAdd.get(i).getAid()){
                    toAdd = !toAdd;
                    break;
                }
            }

            if(toAdd) {
                combinedAlcoholData.add(listToAdd.get(i));
            }
        }
        return combinedAlcoholData;
    }

    /**
     * Enables auto search.
     */
    public void enableAutomaticSearch(){
        searchTextField.addEventHandler(KeyEvent.KEY_RELEASED, (e) -> {
            try {
                search(new ActionEvent(searchButton, (Node) searchButton));
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    /**
     * Disables auto search.
     */
    public void disableAutomaticSearch(){
        EventHandler keyHandler = new EventHandler<javafx.scene.input.KeyEvent>(){
            public void handle(javafx.scene.input.KeyEvent event){
                searchTextField.removeEventHandler(KeyEvent.KEY_PRESSED, this);

            }
        };
        searchTextField.addEventFilter(KeyEvent.KEY_PRESSED, keyHandler);
    }

    private static final String TAB_DELIMITER = "\t";
    private static final String COMMA_DELIMITER = ",";
    private String CUSTOM_DELIMITER = "";
    private String DELIMITER = "";
    private String fileType = "";

    private static final String NEW_LINE_SEPARATOR = "\r\n";
    //private String fileName = "CS3733_TeamA";
    private String fileName = /*System.getProperty("user.home") + "/"+*/"CS3733_TeamA";// + ".csv";
    private File F = new File(fileName);
    private String newFileNumber = "0";
    private String fileContents = "";
    private String customDirectoryString = "";
    private String fileDirectoryWithName = "";

    ////////////////////////////////////////////////////////////////////

    /**
     * Downloads search results.
     */
    public void download2(){
        if(csvDownload.isSelected()){
            System.out.println("comma delim");
            DELIMITER = COMMA_DELIMITER;
            fileType = ".csv";
        }else if (tabDownload.isSelected()){
            System.out.println("tab delim");
            DELIMITER = TAB_DELIMITER;
            fileType = ".txt";
        }else if (customDownload.isSelected()){
            System.out.println("custom");
            CUSTOM_DELIMITER = CustomDelimiter.getText();
            System.out.println(CUSTOM_DELIMITER);

            DELIMITER = CUSTOM_DELIMITER;
            fileType = ".txt";
        }
        fileContents = "";
        fileContents = fileContents +"ID";
        fileContents = fileContents + (DELIMITER);
        fileContents = fileContents + ("Name");
        fileContents = fileContents + (DELIMITER);
        fileContents = fileContents + ("Brandname");
        fileContents = fileContents + (DELIMITER);
        fileContents = fileContents + ("App");
        fileContents = fileContents + (DELIMITER);
        fileContents = fileContents + ("Type");
        fileContents = fileContents + (NEW_LINE_SEPARATOR);

        //AlcoholData(ID, name, brandname, app, type)
        for (int i = 0; i< alcoholDataList.size(); i++) {
            fileContents = fileContents + (String.valueOf(alcoholDataList.get(i).getAid()));
            fileContents = fileContents + (DELIMITER);
            fileContents = fileContents + "\"" + (alcoholDataList.get(i).getName()) + "\"";
            fileContents = fileContents + (DELIMITER);
            fileContents = fileContents + "\"" + (alcoholDataList.get(i).getBrandName()) + "\"";
            fileContents = fileContents + (DELIMITER);
            fileContents = fileContents + "\"" + (alcoholDataList.get(i).getAppellation()) + "\"";;
            fileContents = fileContents + (DELIMITER);
            fileContents = fileContents + (String.valueOf(alcoholDataList.get(i).getAlcoholType()));
            //fileWriter.append(data[i].toString());
            //fileWriter.append(COMMA_DELIMITER);
            fileContents = fileContents + (NEW_LINE_SEPARATOR);
        }
        //new Stage() = savePopUp;
        start(new Stage());
    }
    @FXML
    private void back(){

    }

    //////////////////////////////////////////////////////////////////////////
    //@Override
    public void start(Stage primaryStage) {
        //primaryStage.setTitle("java-buddy.blogspot.com");
        // Group root = new Group();

        // TextArea textArea = new TextArea();

        // Button buttonSave = new Button("Save");

        //  buttonSave.setOnAction((ActionEvent event) -> {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(primaryStage);

        if(file != null){
            SaveFile(fileContents, file);
        }
    }//);

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Saves a search as a CSV file.
     * @param content Content to save.
     * @param file File to save search to.
     */
    private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter;

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("IOException");
            //Logger.getLogger(JavaFXSaveText.class
            //        .getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Creates navigation buttons(i.e. scroll)
     */
    public void createPageNavigationButtons(){
        InputStream inputStreamForButtons;
        ImageView buttonImageView;
        pageControlsHBox.setAlignment(Pos.CENTER_LEFT);

        inputStreamForButtons = SearchMenuController.class.getResourceAsStream("/images/previous_button_image.png");
        buttonImageView = new ImageView(new Image(inputStreamForButtons));
        buttonImageView.setFitHeight(31);
        buttonImageView.setPreserveRatio(true);
        previousPageButton.setGraphic(buttonImageView);

        inputStreamForButtons = SearchMenuController.class.getResourceAsStream("/images/next_button_image.png");
        buttonImageView = new ImageView(new Image(inputStreamForButtons));
        buttonImageView.setFitHeight(31);
        buttonImageView.setPreserveRatio(true);
        nextPageButton.setGraphic(buttonImageView);

        previousPageButton.setOnAction((ActionEvent e) -> {
            nextPageButton.setDisable(false);
            imageResultPageNumber--;
            if(imageResultPageNumber > 0){
                displayResultsInThumbnail();
            }else{
                previousPageButton.setDisable(true);
                displayResultsInThumbnail();
            }
        });

        nextPageButton.setOnAction((ActionEvent e) -> {
            previousPageButton.setDisable(false);
            imageResultPageNumber++;
            if(imageResultPageNumber < (noOfPagesGenerated-1)){
                displayResultsInThumbnail();
            }else{
                nextPageButton.setDisable(true);
                displayResultsInThumbnail();
            }
        });
    }

    /**
     * Switches user to the help screen for searches.
     */
    public void needHelp (){
        screenUtil.switchScene("SearchHelp.fxml","Help");
    }

    /**
     * Gets an image path.
     * @return Returns the image path.
     * @throws UnsupportedEncodingException
     */
    public String getPath() throws UnsupportedEncodingException {


        URL url = this.getClass().getProtectionDomain().getCodeSource().getLocation();
        String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
        String parentPath = new File(jarPath).getParentFile().getPath();

        String fileSeparator = System.getProperty("file.separator");
        String newDir = parentPath + fileSeparator + "images" + fileSeparator;

        System.out.println(newDir);

        return newDir;
    }

}
