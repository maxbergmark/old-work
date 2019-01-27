import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class TestDraw {
	BufferedImage test;

	public TestDraw() {
		test = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
		test.createGraphics();
		test.setRGB(128, 128, 0x00ff00);
		File out = new File("testimage.png");
		try {
			ImageIO.write(test, "png", out);
		} catch (Exception e) {

		}		
	}


	public static void main(String[] args) {
		TestDraw hej = new TestDraw();
	}
}