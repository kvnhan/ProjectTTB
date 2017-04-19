package sample;

/**
 * Displays image proxys in the system.
 */
public class ProxyImage implements  Image {
     private RealImage realImage = null;
     private String fileName = null;

     public ProxyImage(String fileName){
         this.fileName = fileName;
     }

     @Override
     /**
      * Displays an image in the system.
      */
    public void display(){
         if(realImage == null){
             realImage = new RealImage(fileName);
         }
         realImage.display();
     }
}
