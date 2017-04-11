package sample;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Adonay on 4/10/2017.
 */
public class RealImage implements Image{
    private String fileName = null;
    private BufferedImage img = null;

    private ScreenUtil screenUtil = new ScreenUtil();

    public RealImage(String fileName){
        this.fileName = fileName;
        loadImage(fileName);
    }

    @Override
    public void display(){
        System.out.println("Displaying " + fileName);
    }

    private void loadImage(String fileName){
        try {
            img = ImageIO.read(new File("/label_images/"+fileName));
        } catch (IOException e) {
        }
    }
}
