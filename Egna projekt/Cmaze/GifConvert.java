import java.io.*;
import java.util.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.Color;

public class GifConvert {

	static int n;
	static byte[] data;

	public static void main(String[] args) {

		try {
			File file = new File("img2.txt");
			n = (int)Math.sqrt(file.length())*2;
			data = new byte[(int) file.length()];
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			dis.readFully(data);
			dis.close();
			drawImage();
		} catch (Exception e) {
			System.out.println("Error");
			//System.out.println(e.getStackTrace()[2].getLineNumber());


		}

	}



	public static void drawImage() {
		BufferedImage image;
		String fileName = "output";
		System.out.println(n);
		int div = 8;
		int[] ims = new int[div];
		for (int i = 0; i < div; i++) {
			ims[i] = 2*n/div;
		}
		ims[0] = 2*n/div+1;
		try {

			int count = 0;
			int elem = 0;

			for (int l = 0; l < div; l++) {

				BufferedImage[] images = new BufferedImage[div];

				for (int k = 0; k < div; k++) {
					images[k] = new BufferedImage(ims[k], ims[l], BufferedImage.TYPE_BYTE_BINARY);
				}
					// System.out.println(image.getWidth());
					// System.out.println(image.getHeight());

				int offset = 0;
				if (l == 0) {
					offset = 1;
				}

				for (int j = 0; j < n/div; j++) {
					
					for (int i = 0; i < n; i++) {

						boolean c1 = (data[elem] & 1<<count) > 0;
						count++;
						boolean c0 = (data[elem] & 1<<count) > 0;
						count++;
						// System.out.println((c0 ? "true" : "false") + "\t" + (c1 ? "true" : "false"));
						if (count == 8) {
							// String s1 = String.format("%8s", Integer.toBinaryString(data[elem] & 0xFF)).replace(' ', '0');
							// System.out.println(s1); // 10000001
							elem++;
							count = 0;
							if (elem % 1000 == 0) {
								printProgress(elem/1000, data.length/1000, elem);
							}
						}

						int x = 2*i;
						int currImage = 0;
						while (x >= 2*n/div) {
							x -= 2*n/div; 
							currImage++;
						}
						int offsetx = 0;
						if (currImage == 0) {
							offsetx = 1;
						}
						/*
						if (true || 2*x+offsetx >= images[currImage].getWidth()-5) {
							System.out.println("width: " + (x+offsetx) + "\t" + images[currImage].getWidth());
						}
						if (true || 2*j*offset+1 >= images[currImage].getHeight()-5) {
							System.out.println("height: " + (j+offset+1) + "\t" + images[currImage].getHeight() + "\t" + currImage);
						}
						*/
						images[currImage].setRGB(x+offsetx, 2*j+offset, Color.WHITE.getRGB());
						if (c1) {
							images[currImage].setRGB(x+offsetx+1, 2*j+offset, Color.WHITE.getRGB());
						}
						if (c0) {

							images[currImage].setRGB(x+offsetx, 2*j+offset+1, Color.WHITE.getRGB());
						}

					}
				}
				//printProgress(1, 1, elem);
				System.out.println();


				for (int k = 0; k < div; k++) {
					File outputFile = new File ("merged/" + fileName + l + k + ".gif");
					ImageIO.write(images[k], "gif", outputFile);
				}
			}



		} catch (Exception e) {
			System.out.println("Error creating file");
			System.out.println(e);
			System.out.println(e.getStackTrace()[2].getLineNumber());
		}
	}

	public static void printProgress(int curr, int max, int number) {
		int width = 40;
		String s = "|";
		double p = width*curr/max;
		for (int i = 0; i < p; i++) {
			s += "=";
		}
		for (int i = 0; i < width-p; i++) {
			s += " ";
		}
		s += "|";
//		System.out.println(100*curr);
//		System.out.print("\r                                                                                ");
		System.out.print("\r   ");
		System.out.print(String.format("%s%.1f%% (%d)          ", 
			s, 100*curr/(double)max, number));

	}
}