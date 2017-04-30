package johnsUtil.model.Export;


import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by John on 4/20/2017.
 */
enum FileType { PDF, CSV, DELIMITED}

public class FileFactory {
    private Exporter pdfModel;
    private Exporter csvModel;
    private Exporter csvDelimited;

    private char delimiter;

    private File sourcePdf;  //TODO hard code

    /**
     * Creates a file of a certain type
     * - Pdf will only create a document for the first element in the form list
     * - If using delimiter, make sure to set the delimiter before hand
     * @param type
     * @param forms
     * @param target
     * @return
     * @throws Exception
     *
     */
    public void createFile(FileType type,List<Form> forms, File target) throws IOException {
        if(type == FileType.PDF ){
            Exporter pdfModel = null; //TODO //new PdfModel(this.sourcePdf,target,forms.get(0));
            pdfModel.open();
            pdfModel.fill();
            pdfModel.save();
            pdfModel.close();
        }
        else if(type == FileType.CSV){

        }
        else{//Delimited

        }
    }

    public void setDelimiter(char delimiter){
        this.delimiter = delimiter;
    }
}
