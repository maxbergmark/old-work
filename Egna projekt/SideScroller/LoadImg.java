import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.geom.*;

public class LoadImg implements Runnable {

	private BufferedImage img;
	private String fN;
	private int xd, yd;
	private boolean loaded;

	public LoadImg(String fN, int xd, int yd) {
		this.fN = fN;
		this.xd = xd;
		this.yd = yd;
		this.loaded = false;
	}

	public boolean ready() {
		return loaded;
	}

	public void run() {
		try {
			img = ImageIO.read(new File("res/" + fN));
			
			Image toolkitImage = img.getScaledInstance(xd, yd, 
				Image.SCALE_SMOOTH);
			int width = toolkitImage.getWidth(null);
			int height = toolkitImage.getHeight(null);
			BufferedImage newImage = new BufferedImage(xd, yd, 
			BufferedImage.TYPE_INT_ARGB);
			Graphics g = newImage.getGraphics();
			g.drawImage(toolkitImage, 0, 0, null);
			g.dispose();
			img = newImage;
			loaded = true;
		} catch (Exception e) {
			System.err.println(e);
			System.err.println("File not found.");
		}
	}

	public int getGround() {
		for (int i = 0; i < img.getHeight(); i++) {
			int a = img.getRGB(0,i);
			if (a != 0) {
				return i;
			}
		}
		return -1;
	}

	public BufferedImage get() {
		return img;
	}
}