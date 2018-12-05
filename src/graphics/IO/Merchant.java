package graphics.IO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Merchant {

    public static BufferedImage load(String path){
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void save(BufferedImage img, String path, String ext)
    {
        try {
            ImageIO.write(img,"bmp", new File(path + ext));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
