package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    @FXML CheckBox IsWine, IsBeer, IsOther;
    @FXML TextField Tags;
    @FXML TableColumn IDno, Name, BrandName, Type, Location;
    @FXML TableView table;

    ScreenUtil screenUtil = new ScreenUtil();

    Connection conn = connect();
    int wob = 0;
    final int BEER = 1;
    final int WINE = 2;

    List<AlcoholData> AlcoholDataList = new ArrayList<AlcoholData>();
    static ObservableList<AlcoholData> observableList;



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
        screenUtil.pullUpScreen("MainMenu.fxml", "Main Menu", event);
    }

    public static ObservableList<AlcoholData> getObservableList() {
        return observableList;
    }


    public void search(ActionEvent event) throws SQLException, NoSuchMethodException, IllegalAccessException, InstantiationException, IOException{
        AlcoholDataList.clear();
        if (IsBeer.isSelected()){
            wob = 1;
        }
        else if (IsWine.isSelected()){
            wob = 2;
        }
        else if (Tags.getText() == null || Tags.getText().trim().isEmpty()) {
            System.out.println("ERROR");
            screenUtil.pullUpScreen("ErrorState.fxml","Error", event);
            System.out.println("ERROR");
        }

        searchDatabase(conn);
        observableList = FXCollections.observableList(AlcoholDataList);
        getResults();
    }


    private void searchDatabase(Connection conn) throws SQLException {
        Scanner input = new Scanner(System.in);
        ResultSet rset;
        Statement stmt;
        String brand;

        String qry = "SELECT * FROM ALCOHOL WHERE ALCOHOL.ALCOHOL_TYPE = ";

        stmt = conn.createStatement();

        if (IsWine.isSelected() && IsBeer.isSelected()){
            rset = stmt.executeQuery(qry + BEER);
            while(rset.next()){ //TODO Make this clean so that the while loop does not come up twice
                String ID = String.format("%1$"+3+ "s", rset.getString("AID"));
                String name = String.format("%1$"+25+ "s", rset.getString("NAME"));
                String brandname = String.format("%1$"+25+ "s", rset.getString("BRAND_NAME"));
                String app = String.format("%1$"+22+ "s", rset.getString("APPELLATION"));
                String type = String.format("%1$"+10+ "s", rset.getString("ALCOHOL_TYPE"));
                // AlcoholData Constructor
                AlcoholData a = new AlcoholData(ID, name, brandname, app, type);
                AlcoholDataList.add(a);
            }
            rset = stmt.executeQuery(qry + WINE);//TODO MAKE IT NOT SET TWICE PLSPLSPLS
        }
        else if(IsWine.isSelected() || IsBeer.isSelected()){
            System.out.println("while loop executd");
            rset = stmt.executeQuery(qry + wob);
        }
        else {
            brand = Tags.getText().trim();
            System.out.println("Hello");
            System.out.println(brand);
            rset = stmt.executeQuery("SELECT * FROM ALCOHOL WHERE ALCOHOL.BRAND_NAME LIKE '"+brand+"%'");
        }

        while(rset.next()){
            String ID = String.format("%1$"+3+ "s", rset.getString("AID"));
            String name = String.format("%1$"+25+ "s", rset.getString("NAME"));
            String brandname = String.format("%1$"+25+ "s", rset.getString("BRAND_NAME"));
            String app = String.format("%1$"+22+ "s", rset.getString("APPELLATION"));
            String type = String.format("%1$"+10+ "s", rset.getString("ALCOHOL_TYPE"));
            // AlcoholData Constructor
            AlcoholData a = new AlcoholData(ID, name, brandname, app, type);
            AlcoholDataList.add(a);
            System.out.println("while loop executd");
        }

        rset.close();
        stmt.close();
        input.close();
    }



    public static Connection connect(){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Java DB Driver not found. Add the classpath to your module.");
            e.printStackTrace();
            return null;
        }

        System.out.println("Java DB driver registered!");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:derby:DATABASE\\ProjectC;create=true");
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return connection;
        }
        System.out.println("Java DB connection established!");

        return connection;
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

