package sample;

/**
 * Created by Adonay on 4/10/2017.
 */
public class ProxyImage implements  Image {
     private RealImage realImage = null;
     private String fileName = null;

     public ProxyImage(String fileName){
         this.fileName = fileName;

     }

     @Override
    public void display(){
         if(realImage == null){
             realImage = new RealImage(fileName);
         }
         realImage.display();
     }
}
