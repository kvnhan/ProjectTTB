package sample;

/**
 * Created by Adonay on 4/10/2017.
 */
public class RealImage implements Image{

    private String fileName;

    public RealImage(String fileName){
        this.fileName = fileName;
        loadImage(fileName);
    }

    @Override
    public void display(){
        System.out.println("Displaying " + fileName);
    }

    private void loadImage(String fileName){

    }
}
