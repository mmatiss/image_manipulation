public class Pixel {

    int red;
    int green;
    int blue;

    public Pixel(int i) {
        blue = i % 256;
        i = i / 256;
        green = i % 256;
        i = i / 256;
        red = i % 256;
    }

    public Pixel(int b, int g, int r) {
        blue = b;
        green = g;
        red = r;
    }

    public int getRed() {
        return red;
    }

    public int getBlue() {
        return blue;
    }

    public int getGreen() {
        return green;
    }

    public int getDif(Pixel p) {
        return java.lang.Math.abs(p.red - red) + java.lang.Math.abs(p.green - green) + java.lang.Math.abs(p.blue - blue);
    }

    public String toString() {
        return "r: " + red + " green: " + green + " blue: " + blue;
    }

    public int toInt() {
        return 256 * ((256 * red) + blue) + green;
    }
}
