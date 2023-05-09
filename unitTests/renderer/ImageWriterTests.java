package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import static org.junit.jupiter.api.Assertions.*;

/** Testing ImageWriter */
class ImageWriterTests {

    /**
     * Test method for {@link renderer.ImageWriterTests#writeImageTest()}.
     */
    @Test
    void writeImageTest() {
        int nX = 801;
        int nY = 501;
        int interval = 50; // Amount of pixels in each square (50x50)
        Color yellow = new Color(255d, 255d, 0);
        Color red = new Color(255d, 0, 0);
        ImageWriter imageWriter = new ImageWriter("yellowWithRedGridTest", nX, nY);

        // Lopping to fill the matrix with yellow colors and a red grid
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                // Check for the inside of the squares
                if (i % interval != 0 && j % interval != 0) {
                    imageWriter.writePixel(j, i, yellow);
                }
                // The boundaries
                else
                    imageWriter.writePixel(j, i, red);
            }
        }
    }
}