package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import javafx.collections.ObservableList;

public class SearchMenuController {

    private String brandName;
    private @FXML CheckBox isWineBox, isBeerBox, isOtherBox;
    private @FXML TextField brandField;
    private @FXML TableColumn idColumn, nameColumn, brandNameColumn, alcoholTypeColumn, locationColumn;
    private @FXML TableView table;
    private @FXML RadioButton normalSearch, intersectSearch, unionSearch;
    private @FXML Button helpSearchButton;

    private ScreenUtil screenUtil = new ScreenUtil();
    private int alcoholChoice = 0;
    private final int BEER = 1;
    private final int WINE = 2;
    private final int DISTILLED = 3;

    private List<AlcoholData> alcoholDataList = new ArrayList<AlcoholData>();
    private static ObservableList<AlcoholData> observableList;

    private DatabaseUtil dbUtil = new DatabaseUtil();

    @FXML
    public void initialize(){
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
    }


    public void displayResults(){
        table.getColumns().clear();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("aid"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        brandNameColumn.setCellValueFactory(new PropertyValueFactory<>("BrandName"));
        alcoholTypeColumn.setCellValueFactory(new PropertyValueFactory<>("AlcoholType"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("Appellation"));
        table.setItems(SearchMenuController.getObservableList());
        table.getColumns().addAll(idColumn, nameColumn, brandNameColumn, alcoholTypeColumn, locationColumn);
    }

    public void back (ActionEvent event){
        screenUtil.switchScene("MainMenu.fxml", "Main Menu");
    }

    public static ObservableList<AlcoholData> getObservableList() {
        return observableList;
    }

    public void searchIntersect() throws SQLException
    {
        List<AlcoholData> alcoholDataListTemp = new ArrayList<AlcoholData>();
        for(int i = 0; i< alcoholDataList.size(); i++){
            alcoholDataListTemp.add(alcoholDataList.get(i));
        }
        alcoholDataList.clear();
        if (isBeerBox.isSelected()){
            isWineBox.setSelected(false);
            alcoholChoice = 1;
        }
        else if (isWineBox.isSelected()){
            alcoholChoice = 2;
        }
        else if (brandField.getText() == null || brandField.getText().trim().isEmpty()) {
            brandName = " ";
           // System.out.println("BRAND NAME EMPTY");
           // screenUtil.switchScene("ErrorState.fxml","Error");
           // System.out.println("CHOOSE ALCOHOL TYPE OR BRANDNAME");
        }
        else
            brandName = brandField.getText();
        searchDatabase();
        boolean to_remove;
        for(int i=0; i < alcoholDataList.size(); i++) {
            to_remove = true;
            for (int j = 0; j < alcoholDataListTemp.size(); j++) {
                if (alcoholDataListTemp.get(j).myEquals(alcoholDataList.get(i))) {
                    to_remove = false;
                    break;
                }
            }
            if(to_remove==true) {
                alcoholDataList.remove(alcoholDataList.get(i));
                i--;
            }
        }
    }
    public void searchUnion() throws SQLException
    {
        List<AlcoholData> alcoholDataListTemp = new ArrayList<AlcoholData>();
        for(int i = 0; i< alcoholDataList.size(); i++){
            alcoholDataListTemp.add(alcoholDataList.get(i));
        }
        alcoholDataList.clear();
        if (isBeerBox.isSelected()){
            alcoholChoice = 1;
        }
        else if (isWineBox.isSelected()){
            alcoholChoice = 2;
        }
        else if (brandField.getText() == null || brandField.getText().trim().isEmpty()) {
            brandName = " ";
            //System.out.println("BRAND NAME EMPTY");
           // screenUtil.switchScene("ErrorState.fxml","Error");
           // System.out.println("CHOOSE ALCOHOL TYPE OR BRANDNAME");
        }
        else
            brandName = brandField.getText();
        searchDatabase();
        boolean to_add;
        int temp_int = alcoholDataListTemp.size();
        System.out.println((temp_int));
        for(int i=0; i < alcoholDataListTemp.size(); i++) {
            to_add = true;
            System.out.println("Checking if item is in list");
            for (int j = 0; j < alcoholDataList.size(); j++) {
                if (alcoholDataList.get(j).myEquals(alcoholDataListTemp.get(i))) {
                    to_add = false;
                    break;
                }
            }
            if(to_add==true) {
                alcoholDataList.add(alcoholDataListTemp.get(i));
            }
        }
    }
    public void searchNormal() throws SQLException
    {
        alcoholDataList.clear();
        if (isBeerBox.isSelected()){
            alcoholChoice = 1;
        }
        else if (isWineBox.isSelected()){
            alcoholChoice = 2;
        }
        else if (brandField.getText() == null || brandField.getText().trim().isEmpty()) {
            brandName = " ";
        //    System.out.println("BRAND NAME EMPTY");
        //    screenUtil.switchScene("ErrorState.fxml","Error");
         //   System.out.println("CHOOSE ALCOHOL TYPE OR BRANDNAME");
        }
        else
            brandName = brandField.getText();
        searchDatabase();
    }


    public void search(ActionEvent event) throws SQLException, NoSuchMethodException, IllegalAccessException, InstantiationException, IOException{
        if(normalSearch.isSelected()){
            searchNormal();
        }
        else if(intersectSearch.isSelected()){
            searchIntersect();
        }
        else {
            System.out.println("Entering Union");
            searchUnion();
        }
        observableList = FXCollections.observableList(alcoholDataList);
        displayResults();
        System.out.println("FLAG 3");
    }

    public void displayAll(ActionEvent event) throws SQLException, NoSuchMethodException, IllegalAccessException, InstantiationException, IOException{
        alcoholDataList.clear();
        alcoholDataList = dbUtil.searchAlcoholWithType(BEER);
        alcoholDataList.addAll(dbUtil.searchAlcoholWithType(WINE));
        alcoholDataList.addAll(dbUtil.searchAlcoholWithType(DISTILLED));
        observableList = FXCollections.observableList(alcoholDataList);
        displayResults();
    }

    //TODO add other spirits
    private void searchDatabase() throws SQLException {

        if (isWineBox.isSelected() && isBeerBox.isSelected()){
            alcoholDataList = dbUtil.searchAlcoholWithType(BEER);
            alcoholDataList.addAll(dbUtil.searchAlcoholWithType(WINE));
        }
        else if(isWineBox.isSelected() || isBeerBox.isSelected()){
            alcoholDataList = dbUtil.searchAlcoholWithType(alcoholChoice);
        }
        else {
            alcoholDataList = dbUtil.searchAlcoholBrand(brandName);
        }
    }

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\r\n";
    //private String fileName = "CS3733_TeamA";
    private String fileName = System.getProperty("user.home") + "/"+"CS3733_TeamA" + ".csv";
    private File F = new File(fileName);
    private int j = 1;


    public void download(){


        if(F.exists()) {
            for (j = 1; F.exists(); j++) {
                int fileNameLength = fileName.length();
                fileName =  fileName.substring(0,fileNameLength-4) + String.valueOf(j) + ".csv";
                F = new File(fileName);
            }
        }
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName);

            //Write the CSV file header

            fileWriter.append("ID");
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append("Name");
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append("Brandname");
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append("App");
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append("Type");
            fileWriter.append(NEW_LINE_SEPARATOR);

            //AlcoholData(ID, name, brandname, app, type)
            for (int i = 0; i< alcoholDataList.size(); i++) {
                fileWriter.append(String.valueOf(alcoholDataList.get(i).getAid()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(alcoholDataList.get(i).getName());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(alcoholDataList.get(i).getBrandName());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(alcoholDataList.get(i).getAppellation());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(alcoholDataList.get(i).getAlcoholType()));
                //fileWriter.append(data[i].toString());
                //fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(NEW_LINE_SEPARATOR);
            }

            System.out.println("CSV file was created successfully !!!");
            System.out.println("CSV file name:"+fileName);
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }

        }
    }

    public void buttonClicked (){
            screenUtil.switchScene("SearchHelp.fxml","Help");
    }

}

