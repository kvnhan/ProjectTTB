package sample;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class for containing image information.
 */
public class RealImage implements Image{
    private String fileName = null;
    private BufferedImage img = null;

    private ScreenUtil screenUtil = new ScreenUtil();

    /**
     * Creates an image.
     * @param fileName Name of the image file.
     */
    public RealImage(String fileName){
        this.fileName = fileName;
        loadImage(fileName);
    }

    @Override
    /**
     * Displays an image in the system.
     */
    public void display(){
        System.out.println("Displaying " + fileName);
    }

    /**
     * Loads an image into the system.
     * @param fileName Name of image to load.
     */
    private void loadImage(String fileName){
        try {
            img = ImageIO.read(new File("/labels/"+fileName));
        } catch (IOException e) {
        }
    }
}
