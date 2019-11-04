import java.awt.image.*;
import java.io.*;
import java.util.*;

public class pixelArr {

    int width;
    int height;
    int[] imgArr;
    Pixel[] pixArr;
    int diffs;
    int type;


    public pixelArr(BufferedImage image) {
        width = image.getWidth();
        height = image.getHeight();
        type = image.getType();
        imgArr = new int[width * height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                imgArr[i * width + j] = image.getRGB(j, i);
            }
        }
        //imgArr = image.getRGB(0, 0, width, height, null, 0, 1);
        pixArr = new Pixel[imgArr.length];
        for (int i = 0; i < imgArr.length; i++) {
            pixArr[i] = new Pixel(imgArr[i]);
        }
    }

    public Pixel getPixel(int x, int y) {
        return pixArr[y * width + x];
    }

    public void setPixel(int x, int y, Pixel value) {
        pixArr[y * width + x] = value;
    }

    public int getContrast(int x, int y) {
        int diff = 0;
        Pixel p = getPixel(x, y);
        Pixel neighbor;
        int neighbors = 0;
        for (int yPos = y - 1; yPos <= y + 1 && yPos < height; yPos++) {
            if (yPos < 0) {
                continue;
            }
            for (int xPos = x - 1; xPos <= x + 1 && xPos < width; xPos++) {
                if (xPos < 0) {
                    continue;
                }
                 neighbor = getPixel(xPos, yPos);
                 diff += p.getDif(neighbor);
                 neighbors++;
            }
        }
        if (diff > 0) {
            diffs++;
        }
        return diff / neighbors;
    }

    public int getContrast2(int x, int y) {
        int diff = 0;
        Pixel p = getPixel(x, y);
        Pixel neighbor;
        int neighbors = 0;
        if (x - 1 >= 0) {
            neighbor = getPixel(x - 1, y);
            diff += p.getDif(neighbor);
            neighbors++;
        }
        if (x + 1 < width) {
            neighbor = getPixel(x + 1, y);
            diff += p.getDif(neighbor);
            neighbors++;
        }
        if (y - 1 >= 0) {
            neighbor = getPixel(x, y - 1);
            diff += p.getDif(neighbor);
            neighbors++;
        }
        if (y + 1 < height) {
            neighbor = getPixel(x, y + 1);
            diff += p.getDif(neighbor);
            neighbors++;
        }
        if (diff > 0) {
            diffs++;
        }
        return diff / neighbors;
    }

    public BufferedImage createContrastImage() {
        BufferedImage image = new BufferedImage(width, height, type);
        /**
        int[] contrast = createContrastArr();
        int total = 0;
        int totalcount = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (contrast[i * width + j] > 0) {
                    total += contrast[i * width + j];
                    totalcount++;
                }
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (contrast[i * width +  j] < total/totalcount) {
                    image.setRGB(j, i, 16777215);
                }
            }
        }*/
        int[] contrast = greyScale();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                image.setRGB(j, i, contrast[i * width +  j]);
            }
        }
        return image;
    }

    public Pixel grayPixel(int i) {
        return new Pixel(i, i, i);
    }

    public int[] createContrastArr() {

        int contrast;
        int[] contrastArr = new int[height * width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                contrast = getContrast(j, i);
                contrastArr[i * width + j] = contrast;
            }
        }
        return contrastArr;
    }


    public void setBuffer(BufferedImage image) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                image.setRGB(j, i,imgArr[i * width + j]);
            }
        }
    }

    public int[] greyScale() {
        int[] contrast = createContrastArr();
        int[] arr2 = new int[contrast.length];
        System.arraycopy(contrast, 0, arr2, 0, contrast.length);
        Arrays.sort(contrast);
        int[] buckets = new int[256];
        int chunk = contrast.length/256;
        for (int i = 0; i < 256; i++) {
            buckets[i] = contrast[chunk * i];
        }

        int[] ret = new int[contrast.length];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int c = 0; c < 256; c++) {
                    if (buckets[c] > arr2[i * width + j]){
                        ret[i * width + j] = grayPixel(255 - buckets[c - 1]).toInt();
                        break;
                    }
                }
            }
        }
        return ret;
    }
}
