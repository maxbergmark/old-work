import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.util.*;

public class Sprite extends Entity {


	BufferedImage img;
	double x, y;
	int xd, yd;
	int frame;
	int total;
	BufferedImage[] sprites;
	int[][] states;
	int[] speeds;
	int state;


	public Sprite(LoadImg image, int xd, int yd, int total, int speed, int xs, int ys, int numStates) {
		super(image, 1, xs*xd, ys*yd);
		sprites = new BufferedImage[total];
		try {
			this.img = image.get();
//			img =  ImageIO.read(new File(fileName));
			int width = img.getWidth();
			int height = img.getHeight();
			int dx = width/xd;
			int dy = height/yd;
			for (int i = 0; i < total; i++) {
				sprites[i] = img.getSubimage(dx*(i%xd), dy*(i/xd), dx, dy);

			}

		} catch (Exception e) {
			System.err.println(e);
			System.err.println("File not found.");
		}
		this.xd = xd;
		this.yd = yd;
		this.total = total;
		this.speeds = new int[numStates];
		for (int i = 0; i < numStates; i++) {
			this.speeds[i] = speed;
		}
		this.states = new int[numStates][];
	}

	public void setSpeed(int num, int speed) {
		speeds[num] = speed;
	}

	public void setState(int state, int min, int max) {
		states[state] = new int[Math.abs(max-min)];

		if (min > max) {
			for (int i = min-1; i >= max; i--) {
				states[state][min-i-1] = i;
			}
		} else {
			for (int i = min; i < max; i++) {
				states[state][i-min] = i;
			}
		}
		/*
		for (int[] i : states) {
			System.out.println(Arrays.toString(i));
		}
		*/
	}

	public void changeState(int state) {
		this.state = state;
	}


	public void advance(int f) {
		frame = (f/speeds[state])%(states[state].length);
	}

	public BufferedImage getSprite(int i) {
		return sprites[i];
	}


	public BufferedImage getImg() {
		//System.out.println("\n\n\n\n" + sprites.length + "\n\n\n\n");
		int f = states[state][frame];
		return sprites[f];
	}

	public int compareTo(Sprite e) {
		return z>e.getZ()?1:(z<e.getZ()?-1:0);
	}

}