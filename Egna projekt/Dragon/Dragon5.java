import java.util.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;


public class Dragon5 {
	private byte[] dragon2 = {1};
	private short[] xpos;
	private short[] ypos;

	public Dragon5(int n) {
		System.out.println("Iterations: " + n);
		final long startTime = System.nanoTime();
		for (int i = 0; i < n; i++) {
			dragon2 = iterateArray(dragon2, (i == n-1));
		}
		if (n == 0) {
			dragon2 = new byte[] {1, 0};
		}
		double dragonTime = ((double)(System.nanoTime()-startTime)/1000000);
		final long startTime2 = System.nanoTime();
		createDragonFast(dragon2);
		double coordTime = ((double)(System.nanoTime()-startTime2)/1000000);
		final long startTime3 = System.nanoTime();
		drawImage();
		double drawTime = ((double)(System.nanoTime()-startTime3)/1000000);
		double totTime = ((double)(System.nanoTime()-startTime)/1000000000);
		double perSec = (long)(3)*dragon2.length/totTime;
		System.out.println("Dragon array created in: " + dragonTime + " ms.");
		System.out.println("Coordinate array created in: " + coordTime + " ms.");
		System.out.println("Image drawn in: " + drawTime + " ms.");
		System.out.println("Total execution time: " + totTime*1000 + " ms.");
		System.out.println("Array length: " + dragon2.length);
		System.out.println("Created " + perSec + " elements per second.");
		//System.out.println(Arrays.toString(dragon2));
		//System.out.println(Arrays.toString(xpos));		
		System.out.println((12+1*(dragon2.length+1))/1024/1024);
	}

	public void drawImage() {
		BufferedImage image = new BufferedImage(2048, 2048, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();
		for (int i = 0;i < xpos.length; i++) {
			try {
				image.setRGB(xpos[i]+1024, ypos[i]+1024, 0x00ff00);
			} catch (Exception e) {}
		}
		try {
			File outputFile = new File ("testdragon.png");
			ImageIO.write(image, "png", outputFile);
		} catch (Exception e) {
			System.out.println("Error");
		}
	}

	public byte[] iterateArray(byte[] drag, boolean addExtra) {
		int dragLength = drag.length;
		byte[] retdrag = new byte[dragLength*2+1+(addExtra ? 1 : 0)];
		System.arraycopy(drag, 0, retdrag, 0, dragLength);
		retdrag[dragLength] = drag[dragLength/2];
		for (int i = 1; i < dragLength+1; i++) {
			retdrag[dragLength+i] = (byte)(-1*drag[dragLength-i]);
		}
		return retdrag;
	}

	public void createDragonFast(byte[] drag) {
		short[] ew = {1, 0, -1, 0};
		short[] ns = {0, 1, 0, -1};
		xpos = new short[drag.length+1];
		ypos = new short[drag.length+1];
		byte direction = 0;
		for(int i = 0; i < drag.length; i++) {
			xpos[i+1] = (short)(xpos[i]+ew[direction%4]);
			ypos[i+1] = (short)(ypos[i]+ns[direction%4]);
			direction += drag[i];
		}
	}

	public static void main(String[] args) {
		Dragon5 instance = new Dragon5(Integer.valueOf(args[0]));

	}
}