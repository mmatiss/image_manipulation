import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.imageio.ImageIO;

public class main {

    public static void main(String[] args) {
        BufferedImage image = null;

        image = open("tree");

        pixelArr pix = new pixelArr(image);
        int c = 0;

        int max = 0;
        int contrast;
        int total = 0;
        int totalCount = 1;
        int[] contrastArr = new int[pix.height * pix.width];
        for (int i = 0; i < pix.height; i++) {
            for (int j = 0; j < pix.width; j++) {
                contrast = pix.getContrast(j, i);
                contrastArr[i * pix.width + j] = contrast;
            }
        }

        pix.setBuffer(image);
        BufferedImage con = pix.createContrastImage();
        save(con);
    }


    private static BufferedImage open(String imageName) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/images/" + imageName + ".jpg"));
            System.out.println("Reading complete.");

        }
        catch(IOException e)
        {
            System.out.println("Error: "+e);
        }
        return image;
    }

    private static void save(BufferedImage image) {
        try
        {
            // Output file path
            File output_file = new File("src/images/out.jpg");

            // Writing to file taking type and path as
            ImageIO.write(image, "jpg", output_file);

            System.out.println("Writing complete.");
        }
        catch(IOException e)
        {
            System.out.println("Error: "+e);
        }
    }
}


