package johnsUtil.model;

//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
//import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
//import org.apache.pdfbox.pdmodel.interactive.form.PDField;
//import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by John on 4/16/2017.
 */
//public class PdfModel implements Exporter{
//
//    private File source;
//    private File target;
//    private PDDocument pdfDocument;
//    private PDAcroForm acroForm;
//    private boolean open;
//
//    Form form;
//    /** Creates a pdf form of the form data sent, to the directed target path
//     * @param  form
//     * @throws IOException
//     */
//    public PdfModel(File source, File target, Form form) throws IOException {
//        this.source = source;
//        this.target= target;
//        this.open = false;
//        this.form = form;
//    }
//
//    public PdfModel(){
//        this.source = null;
//        this.target = null;
//        this.open = false;
//        this.form = null;
//    }
//
//    public void setSource(File source){
//        this.target = source;
//    }
//
//    public void setTarget(File target){
//        this.target = target;
//    }
//
//    public void setForm(Form form){
//        this.form = form;
//    }
//
//
//
////    public static void main(String args[]){
////        String template = "C:\\Users\\John\\Desktop\\Coding Misc\\emptyFormFinalWATER.pdf";
////        String target = "C:\\Users\\John\\Desktop\\Coding Misc\\filledForm.pdf";
////
////        try {
////            //PdfModel model = new PdfModel(new File(template), new File(target));
////            PdfModel model = new PdfModel(new File(template), new File(target),"JOhn A", "TTBID122",123,"123456",  "Fancyname",  "Formula1231",  "Mailindaddres 123",  "BrandName123",  "401162135","email@email.com",   "Varietals!", "Africa",  "Testing123", true, "WINE", "4-19-17");
////            model.open();
////            model.fill();
////            //model.printFields();
////            model.save();
////            model.close();
////        } catch (IOException e) {
////            e.printStackTrace();
////            System.out.println("There was a problem trying to fetch source file");
////        }
////
////    }
//
//    /**
//     * Fill the new form out with the specified text
//     * @Param Form Data required to fill out alcohol form
//     * @throws  NullPointerException - If not form is set
//     */
//    public void fill() throws IOException{
//        if(form == null){
//            throw new NullPointerException("No Form set");
//        }
//
//        if(acroForm != null){
//            PDTextField pdTtbid = (PDTextField) acroForm.getField("TTBID");
//            pdTtbid.setValue(form.getTtbid());
//
//            PDTextField pdRepid = (PDTextField) acroForm.getField("REPID");
//            pdRepid.setValue(form.getRepid());
//
//            if(form.isDomestic()){
//                PDField domestic = acroForm.getField("DOMESTIC");
//                ((PDCheckBox) domestic).check();
//            }
//            else{
//                PDField imported = acroForm.getField("IMPORTED");
//                ((PDCheckBox) imported).check();
//            }
//
//            if(form.getSerial().length() >= 6) {
//                String serial = form.getSerial();
//                PDTextField pdYear1 = (PDTextField) acroForm.getField("YEAR1");
//                pdYear1.setValue(serial.charAt(0) + "");
//
//                PDTextField pdYear2 = (PDTextField) acroForm.getField("YEAR2");
//                pdYear2.setValue(serial.charAt(1) + "");
//
//                PDTextField pdSerial1 = (PDTextField) acroForm.getField("SERIAL1");
//                pdSerial1.setValue(serial.charAt(2) + "");
//
//                PDTextField pdSerial2 = (PDTextField) acroForm.getField("SERIAL2");
//                pdSerial2.setValue(serial.charAt(3) + "");
//
//                PDTextField pdSerial3 = (PDTextField) acroForm.getField("SERIAL3");
//                pdSerial3.setValue(serial.charAt(4) + "");
//
//                PDTextField pdSerial4 = (PDTextField) acroForm.getField("SERIAL4");
//                pdSerial3.setValue(serial.charAt(5) + "");
//
//            }
//
//            int top = form.getType_of_product();
//            if(top == 1){
//                PDField wine = acroForm.getField("WINE");
//                ((PDCheckBox) wine).check();
//            }
//            else if(top == 2){
//                PDField malt = acroForm.getField("MALT");
//                ((PDCheckBox) malt).check();
//            }
//            else if(top == 3){
//                PDField spirits = acroForm.getField("DISTILLED");
//                ((PDCheckBox) spirits).check();
//            }
//
//            PDTextField pdBrandName = (PDTextField) acroForm.getField("BRANDNAME");
//            pdBrandName.setValue(form.getBrand_name());
//
//            PDTextField pdFancyName = (PDTextField) acroForm.getField("FANCIFULNAME");
//            pdFancyName.setValue(form.getFancyName());
//
//            PDTextField pdAppInfo = (PDTextField) acroForm.getField("APPLICANTINFO");
//            pdFancyName.setValue(form.getApplicantInfo());
//
//            PDTextField pdMailingAd = (PDTextField) acroForm.getField("MAILING");
//            pdMailingAd.setValue(form.getMailingAddress());
//
//            PDTextField pdFormula = (PDTextField) acroForm.getField("FORMULA");
//            pdFormula.setValue(form.getFormula());
//
//            PDTextField pdPlant = (PDTextField) acroForm.getField("");
//            pdPlant.setValue(form.getPermit_no());
//
//            PDTextField pdAddress = (PDTextField) acroForm.getField("");
//            pdAddress.setValue("");
//
//            PDTextField pdVarietal = (PDTextField) acroForm.getField("VARIETALS");
//            pdVarietal.setValue(form.getVarietal());
//
//            PDTextField pdAppellation = (PDTextField) acroForm.getField("APPELLATION");
//            pdAppellation.setValue(form.getAppellation());
//
//            PDTextField pdPhoneNum = (PDTextField) acroForm.getField("PHONE");
//            pdPhoneNum.setValue(form.getPhone_number());
//
//            PDTextField pdEmail = (PDTextField) acroForm.getField("EMAIL");
//            pdEmail.setValue(form.getEmail());
//
//            //TODO  Add checkboxes and fill out their info
//            int toa = form.getTypeOfApp();
//            if(1 == toa){
//
//            }
//            else if(2 == toa){
//
//            }
//            else if(3 == toa){
//
//            }
//            else if(4 == toa){
//
//            }
//
//            PDTextField pdAddInfo = (PDTextField) acroForm.getField("INFO");
//            pdAddInfo.setValue(form.getInfoOnBottle());
//
//            PDTextField pdDate =(PDTextField) acroForm.getField("APPDATE");
//            pdDate.setValue(form.getDate());
//
//            PDTextField pdApplicantName = (PDTextField) acroForm.getField("APPLICANT");
//            pdApplicantName.setValue(form.getApplicantName());
//
//            PDTextField pdIssDate =(PDTextField) acroForm.getField("ISSDATE");
//            pdDate.setValue(form.getIssuedDate());
//
//            PDTextField pdPh = (PDTextField) acroForm.getField("QUAL");
//            pdPh.setValue(form.getQualifications());
//
//            PDTextField pdExpDate =(PDTextField) acroForm.getField("ExpDATE");
//            pdDate.setValue(form.getExpDate());
//
//        }
//    }
//
//    /**
//     * Saves the new form to the desired target location
//     * @throws IOException
//     * @throws NullPointerException if target file was not set
//     */
//    public File save() throws IOException {
//        if(target == null){
//            throw new NullPointerException("No target file set");
//        }
//
//        pdfDocument.save(this.target);
//        return this.target;
//    }
//
//    private void printFields(){
//        Iterator<PDField> iter = acroForm.getFieldIterator();
//        System.out.println("Start");
//        while(iter.hasNext()){
//            PDField field = iter.next();
//
//
//            System.out.println("\n Field: |" + field.getFullyQualifiedName()+"|");
//            System.out.println("type:    " + field.getFieldType());
//            System.out.println("mapping:    " + field.getMappingName());
//            System.out.println("altName:    " + field.getAlternateFieldName());
//
//            if(field != null && field.getFieldType() != null && field.getFieldType().equals("Tx")){
//                try{
//                    PDTextField txtfield = (PDTextField) field;
//                    txtfield.setValue("*" + txtfield.getFullyQualifiedName()+ "*");
//                    System.out.println("**Set**");
//                }catch(IOException e){
//                    System.out.println("Failed: " + field.getFullyQualifiedName());
//                }
//
//            }
//        }
//        System.out.println("Ending");
//    }
//
//    /**
//     * Opens file
//     * @throws IOException If file can not be found, already open, or could not be opened
//     * @throws NullPointerException If source file was not set
//     */
//    public void open() throws IOException {
//        this.pdfDocument = PDDocument.load(source);
//        acroForm = pdfDocument.getDocumentCatalog().getAcroForm();
//        if(pdfDocument.isEncrypted()){
//            pdfDocument.setAllSecurityToBeRemoved(true);
//        }
//        open = true;
//    }
//
//    /**
//     * Ends connection with file
//     * @throws IOException Couldn't close pdf
//     */
//    public void close() throws IOException {
//        pdfDocument.close();
//        open = false;
//    }
//
//    /**
//     * Checks if there is a current connection with the file
//     * @return boolean indicating whether there is still an open connection
//     */
//    public boolean isOpen(){
//        return open;
//    }
//}
