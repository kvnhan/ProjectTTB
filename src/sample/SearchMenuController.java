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
import java.util.Scanner;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.List;
import javafx.collections.ObservableList;

public class SearchMenuController {

    private String brandName;
    private @FXML CheckBox isWineBox, isBeerBox, isOtherBox;
    private @FXML TextField brandField;
    private @FXML TableColumn IDno, Name, BrandName, Type, Location;
    private @FXML TableView table;

    private ScreenUtil screenUtil = new ScreenUtil();
    private int alcoholChoice = 0;
    private final int BEER = 1;
    private final int WINE = 2;

    private List<AlcoholData> AlcoholDataList = new ArrayList<AlcoholData>();
    private static ObservableList<AlcoholData> observableList;

    private DatabaseUtil dbUtil = new DatabaseUtil();


    public void getResults(){
        table.getColumns().clear();
        IDno.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        BrandName.setCellValueFactory(new PropertyValueFactory<>("BrandName"));
        Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        Location.setCellValueFactory(new PropertyValueFactory<>("Appellation"));
        table.setItems(SearchMenuController.getObservableList());
        table.getColumns().addAll(IDno, Name, BrandName, Type, Location);
    }

    public void back (ActionEvent event){
        screenUtil.switchScene("MainMenu.fxml", "Main Menu");
    }

    public static ObservableList<AlcoholData> getObservableList() {
        return observableList;
    }


    public void search(ActionEvent event) throws SQLException, NoSuchMethodException, IllegalAccessException, InstantiationException, IOException{
        AlcoholDataList.clear();
        if (isBeerBox.isSelected()){
            alcoholChoice = 1;
        }
        else if (isWineBox.isSelected()){
            alcoholChoice = 2;
        }
        else if (brandField.getText() == null || brandField.getText().trim().isEmpty()) {
            System.out.println("BRAND NAME EMPTY");
            screenUtil.switchScene("ErrorState.fxml","Error");
            System.out.println("CHOOSE ALCOHOL TYPE OR BRANDNAME");
        }

        brandName = brandField.getText();

        searchDatabase();

        observableList = FXCollections.observableList(AlcoholDataList);
        getResults();
    }


    private void searchDatabase() throws SQLException {

        if (isWineBox.isSelected() && isBeerBox.isSelected()){
            AlcoholDataList = dbUtil.searchAlcoholWithType(BEER);
            AlcoholDataList.addAll(dbUtil.searchAlcoholWithType(WINE));
        }
        else if(isWineBox.isSelected() || isBeerBox.isSelected()){
            AlcoholDataList = dbUtil.searchAlcoholWithType(alcoholChoice);
        }
        else {
            AlcoholDataList = dbUtil.searchAlcoholBrand(brandName);
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
            for (int i=0;i< AlcoholDataList.size(); i++) {
                fileWriter.append(AlcoholDataList.get(i).getID());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(AlcoholDataList.get(i).getName());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(AlcoholDataList.get(i).getBrandName());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(AlcoholDataList.get(i).getAppellation());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(AlcoholDataList.get(i).getType());
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
}

