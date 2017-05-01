package sample;

/**
 * Created by Jacob Remz on 4/25/2017.
 */


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/** Assumes UTF-8 encoding. JDK 7+. */
public class ReadWithScanner {
    dataUtilUpload dataUtil= new dataUtilUpload();
    public static void main(String... aArgs) throws Exception {
        ReadWithScanner parser = new ReadWithScanner("D:\\samur\\Downloads\\Apr17\\ttbsample (1).txt");
        parser.processLineByLine();
        log("Done.");
    }

    /**
     Constructor.
     @param aFileName full name of an existing, readable file.
     */
    public ReadWithScanner(String aFileName){
        fFilePath = Paths.get(aFileName);
    }


    /** Template method that calls {@link #processLine(String)}.  */
    public final void processLineByLine() throws Exception  {
        int counter = 0;
        try (Scanner scanner =  new Scanner(fFilePath, ENCODING.name())){
            while (scanner.hasNextLine()){
                counter++;
                String string = scanner.nextLine();
                System.out.println(string);
                processLine(string);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    // helps parse bad text
    private String stringHelper(String s){
        if(s.contains(".")) {
            System.out.println("I found a null");
            return "0";
        }
        else if(s.equals(". .")) {
            System.out.println("I found a bad value");
            return "0";
        }
        return s;
    }

    //helps parse bad text
    private int integerHelper(String s)throws NumberFormatException{
        try{
            return Integer.parseInt(s);
        }
        catch(NumberFormatException e){
            return 4; //TODO: remove 4 and make it a proper parse
        }
    }

    /**
     Overridable method for processing lines in different ways.

     <P>This simple default implementation expects simple name-value pairs, separated by an
     '=' sign. Examples of valid input:
     <tt>height = 167cm</tt>
     <tt>mass =  65kg</tt>
     <tt>disposition =  "grumpy"</tt>
     <tt>this is the name = this is the value</tt>
     */
    protected void processLine(String aLine)throws Exception {
        //use a second Scanner to parse the content of each line
        System.out.println(aLine);
        Scanner scanner = new Scanner(aLine);
        scanner.useDelimiter(",");

        int pType = 0;
        String CFM_APPL_ID = "";
        String REP_ID = "";
        String CLASS_TYPE_CODE  = "";
        String ORIGIN_CODE = "";
        String PERMIT_ID = "";
        String SOURCE_OF_PRODUCT = "";
        String SERIAL_NUM = "";
        String PRODUCT_TYPE = "";
        String PRODUCT_NAME = "";
        String FANCIFUL_NAME = "";
        String PERMIT_NAME = "";
        String ADDRESS = "";
        String VARTL_NAME = "";
        String NET_CONTENTS = "";
        String ALCOHOL_PCT = "";
        String APPELLATION_DESC = "";
        String VINTAGE = "";
        String APPL_TYPE = "";
        String SPCL_WORDING_DESC = "";
        String COMPLETED_DATE = "";
        String ISSUED_DATE = "";
        String RECEIVED_DATE = "";
        String STATUS = "";
        String EXPIRY_DATE = "";
        String SURRENDERED_DATE = "";
        String QUALIFICATION = "";
        String RECEIVED_CODE = "";

        java.sql.Date SQLrecDate = new java.sql.Date(00-00-0000);
        java.sql.Date SQLissDate = new java.sql.Date(00-00-0000);


        //CFM_APPL_ID,REP_ID,CLASS_TYPE_CODE,ORIGIN_CODE,PERMIT_ID,SOURCE_OF_PRODUCT,SERIAL_NUM,PRODUCT_TYPE,
        if (scanner.hasNext()){//assumes the line has a certain structure //each
             CFM_APPL_ID = scanner.next();}
        if (scanner.hasNext()){
            REP_ID = scanner.next();}
        if (scanner.hasNext()){
            CLASS_TYPE_CODE = scanner.next();}
        if (scanner.hasNext()){
            ORIGIN_CODE = scanner.next();}
        if (scanner.hasNext()){
            PERMIT_ID = scanner.next();}
        if (scanner.hasNext()){
            SOURCE_OF_PRODUCT = scanner.next();}
        if (scanner.hasNext()){
            SERIAL_NUM = scanner.next();}
        if (scanner.hasNext()){
            PRODUCT_TYPE = scanner.next();
            if(PRODUCT_TYPE.equals("Wine")){
                pType = 2;
            }else if(PRODUCT_TYPE.equals("Malt Beverage")){
                pType = 1;
            }else {
                pType = 3;
            }}

            // PRODUCT_NAME,FANCIFUL_NAME,PERMIT_NAME,ADDRESS,VARTL_NAME,NET_CONTENTS,
        if (scanner.hasNext()){
            PRODUCT_NAME = scanner.next();}
        if (scanner.hasNext()){
            FANCIFUL_NAME = scanner.next();}
        if (scanner.hasNext()){
            PERMIT_NAME = scanner.next();}
        if (scanner.hasNext()){
            ADDRESS = scanner.next();}
        if (scanner.hasNext()){
            VARTL_NAME = scanner.next();}
        if (scanner.hasNext()){
            NET_CONTENTS = scanner.next();}

            // ALCOHOL_PCT,APPELLATION_DESC,VINTAGE,APPL_TYPE,SPCL_WORDING_DESC,COMPLETED_DATE,ISSUED_DATE,
        if (scanner.hasNext()){
            ALCOHOL_PCT = scanner.next();}
        if (scanner.hasNext()){
            APPELLATION_DESC = scanner.next();}
        if (scanner.hasNext()){
            VINTAGE = scanner.next();}
        if (scanner.hasNext()){
            APPL_TYPE = scanner.next();}
        if (scanner.hasNext()){
            SPCL_WORDING_DESC = scanner.next();
        System.out.println(SPCL_WORDING_DESC);}
        if (scanner.hasNext()){
            COMPLETED_DATE = scanner.next();
        System.out.println(COMPLETED_DATE);}
        if (scanner.hasNext()) {

            ISSUED_DATE = scanner.next();
            System.out.println(ISSUED_DATE);
            if(ISSUED_DATE.equals(null)){
                ISSUED_DATE = "00/00/0000";
            }
            if(ISSUED_DATE.equals(".")){
                ISSUED_DATE = "00/00/0000";
            }
            SimpleDateFormat issDate = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date2 = issDate.parse(ISSUED_DATE);
            SQLissDate = new java.sql.Date(date2.getTime());
        }
//tested

            // RECEIVED_DATE,STATUS,EXPIRY_DATE,SURRENDERED_DATE,QUALIFICATION,RECEIVED_CODE
        if (scanner.hasNext()){
            RECEIVED_DATE = scanner.next();
            if(RECEIVED_DATE.equals(null)){
                RECEIVED_DATE = "00/00/0000";
            }
            SimpleDateFormat recDate = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = recDate.parse(RECEIVED_DATE);
            SQLrecDate = new java.sql.Date(date.getTime());
            }
        if (scanner.hasNext()){
            STATUS = scanner.next();}
        if (scanner.hasNext()){
            EXPIRY_DATE = scanner.next();}
        if (scanner.hasNext()){
            SURRENDERED_DATE = scanner.next();}
        if (scanner.hasNext()){
            QUALIFICATION = scanner.next();}
        if (scanner.hasNext()){
            RECEIVED_CODE = scanner.next();}
            String sanatizer[] = {"", "."};

        for(int i =0; i<2; i++) {
            if (CFM_APPL_ID.equals(sanatizer[i])) {
                CFM_APPL_ID = "n/a";
            }
            if (REP_ID.equals(sanatizer[i])) {
                REP_ID = "0";
            }
            if (CLASS_TYPE_CODE.equals(sanatizer[i])) {
                CLASS_TYPE_CODE = "0";
            }
            if (ORIGIN_CODE.equals(sanatizer[i])) {
                ORIGIN_CODE = "0";
            }
            if (PERMIT_ID.equals(sanatizer[i])) {
                PERMIT_ID = "n/a";
            }
            if (SOURCE_OF_PRODUCT.equals(sanatizer[i])) {
                SOURCE_OF_PRODUCT = "n/a";
            }
            if (SERIAL_NUM.equals(sanatizer[i])) {
                SERIAL_NUM = "n/a";
            }
            if (PRODUCT_TYPE.equals(sanatizer[i])) {
                PRODUCT_TYPE = "0";
            }
            if (PRODUCT_NAME.equals(sanatizer[i])) {
                PRODUCT_NAME = "n/a";
            }
            if (FANCIFUL_NAME.equals(sanatizer[i])) {
                FANCIFUL_NAME = "n/a";
            }
            if (PERMIT_NAME.equals(sanatizer[i])) {
                PERMIT_NAME = "n/a";
            }
            if (ADDRESS.equals(sanatizer[i])) {
                ADDRESS = "n/a";
            }
            if (VARTL_NAME.equals(sanatizer[i])) {
                VARTL_NAME = "n/a";
            }
            if (NET_CONTENTS.equals(sanatizer[i])) {
                NET_CONTENTS = "n/a";
            }
            if (ALCOHOL_PCT.equals(sanatizer[i])) {
                ALCOHOL_PCT = "0";
            }
            if (APPELLATION_DESC.equals(sanatizer[i])) {
                APPELLATION_DESC = "n/a";
            }
            if (VINTAGE.equals(sanatizer[i])) {
                VINTAGE = "0";
            }
            if (APPL_TYPE.equals(sanatizer[i])) {
                APPL_TYPE = "n/a";
            }
            if (SPCL_WORDING_DESC.equals(sanatizer[i])) {
                SPCL_WORDING_DESC = "n/a";
            }
            if (COMPLETED_DATE.equals(sanatizer[i])) {
                COMPLETED_DATE = "";
            }

            if (STATUS.equals(sanatizer[i])) {
                STATUS = "n/a";
            }
            if (EXPIRY_DATE.equals(sanatizer[i])) {
                EXPIRY_DATE = "";
            }
            if (SURRENDERED_DATE.equals(sanatizer[i])) {
                SURRENDERED_DATE = "";
            }
            if (QUALIFICATION.equals(sanatizer[i])) {
                QUALIFICATION = "n/a";
            }
            if (RECEIVED_CODE.equals(sanatizer[i])) {
                RECEIVED_CODE = "n/a";
            }
            if (SQLrecDate.equals(null)){
                SQLrecDate = new java.sql.Date(00-00-0000);
            }
            if (SQLissDate.equals(null)){
                SQLissDate = new java.sql.Date(00-00-0000);
            }
        }

/*
            //CFM_APPL_ID,REP_ID,CLASS_TYPE_CODE,ORIGIN_CODE,PERMIT_ID,SOURCE_OF_PRODUCT,SERIAL_NUM,PRODUCT_TYPE,
            System.out.println("Hello1");
            System.out.println(CFM_APPL_ID);
            System.out.println(REP_ID);
            System.out.println(CLASS_TYPE_CODE);
            System.out.println(ORIGIN_CODE);
            System.out.println(PERMIT_ID);
            System.out.println(SOURCE_OF_PRODUCT);
            System.out.println(SERIAL_NUM);
            System.out.println(PRODUCT_TYPE);

            // PRODUCT_NAME,FANCIFUL_NAME,PERMIT_NAME,ADDRESS,VARTL_NAME,NET_CONTENTS,
            System.out.println("Hello2");
            System.out.println(PRODUCT_NAME);
            System.out.println(FANCIFUL_NAME);
            System.out.println(PERMIT_NAME);
            System.out.println(ADDRESS);
            System.out.println(VARTL_NAME);
             System.out.println(NET_CONTENTS);

            // ALCOHOL_PCT,APPELLATION_DESC,VINTAGE,APPL_TYPE,SPCL_WORDING_DESC,COMPLETED_DATE,ISSUED_DATE,
            System.out.println("Hello3");
            System.out.println(ALCOHOL_PCT);
            System.out.println(APPELLATION_DESC);
            System.out.println("vintage" + VINTAGE);
            System.out.println(APPL_TYPE);
            System.out.println(SPCL_WORDING_DESC);
            System.out.println(COMPLETED_DATE);
            System.out.println("issued date");
            System.out.println(ISSUED_DATE);
            // RECEIVED_DATE,STATUS,EXPIRY_DATE,SURRENDERED_DATE,QUALIFICATION,RECEIVED_CODE
            System.out.println("Hello4");
            System.out.println("RECEIVED DATE");
            System.out.println(RECEIVED_DATE);
            System.out.println(STATUS);
            System.out.println(EXPIRY_DATE);
            System.out.println(SURRENDERED_DATE);
            Sytem.out.println(QUALIFICATION);
            System.out.println(RECEIVED_CODE);

*/

        System.out.println(PRODUCT_NAME);
        System.out.println(RECEIVED_CODE);


        String ttb = dataUtil.addForm(CFM_APPL_ID, Integer.parseInt(REP_ID),stringHelper(SERIAL_NUM), stringHelper(ADDRESS), "", "", "", stringHelper(STATUS), 0, "", 0, SQLrecDate, stringHelper(PERMIT_NAME), stringHelper(ADDRESS), stringHelper(QUALIFICATION), stringHelper(RECEIVED_CODE),stringHelper(SPCL_WORDING_DESC),stringHelper(PERMIT_ID));
       // dataUtil.addAlcohol("","", " ",0.0, "", " ", 0, 0, " ", " ", " ", 1, " ","", "", 0000, 0.0, "", " ", "",  new java.sql.Date(00-00-0000), 0);


/*            String ttb = dataUtil.addForm(CFM_APPL_ID, Integer.parseInt(REP_ID),stringHelper(SERIAL_NUM),  stringHelper(ADDRESS), stringHelper(PERMIT_ID),
                    " ", " ", stringHelper(STATUS), 0, " ",
                    -1, SQLrecDate, stringHelper(PERMIT_NAME), stringHelper(ADDRESS), stringHelper(QUALIFICATION),
                    stringHelper(RECEIVED_CODE), stringHelper(SPCL_WORDING_DESC), stringHelper(PERMIT_ID));
  */          int aid = dataUtil.addAlcohol(FANCIFUL_NAME,APPELLATION_DESC, "", Double.parseDouble(ALCOHOL_PCT),
                    NET_CONTENTS, " ", 0, Integer.parseInt(CLASS_TYPE_CODE), " ",
                    " ", " ", pType, " ",PRODUCT_NAME,
                    STATUS, Integer.parseInt(VINTAGE), 0.0, "", " ",
                    SOURCE_OF_PRODUCT, SQLissDate, integerHelper(ORIGIN_CODE));
            System.out.println(aid);
            System.out.println(ttb);
            dataUtil.updateAlcoholIDForForm(aid, ttb);

                //log("Name is : " + quote(name.trim()) + ", and Value is : " + quote(value.trim()) + "&" + quote(value2.trim()));
        }



    // PRIVATE
    private final Path fFilePath;
    private final static Charset ENCODING = StandardCharsets.UTF_8;

    private static void log(Object aObject){
        System.out.println(String.valueOf(aObject));
    }

    private String quote(String aText){
        String QUOTE = "'";
        return QUOTE + aText + QUOTE;
    }
}
