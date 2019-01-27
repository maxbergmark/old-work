import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;


public class Mandelbrot {

	double[][] array;
	Complex[][] complexarray;
	int xres = 2000, yres = 2000;
	static double cx = 0*-.10044328862809998;
	static double cy = 0*.8380577884663519;
	static double dx = 2;
	static double dy = 2;
	double xmin = -2.5,ymin = -1.5,xmax = 1,ymax = 1.5;
	double r = 1e50;
	int N = 555;
	int num;

	private BufferedImage output;

	public Mandelbrot(int num, double xmin, double xmax, double ymin, double ymax) {
		this.num = num;
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
		output = new BufferedImage(xres, yres, BufferedImage.TYPE_INT_RGB);

		initArrays();
		calcMandel();
		drawImage();
	}

	public void initArrays() {
		array = new double[xres][yres];
		complexarray = new Complex[xres][yres];
		for (int i = 0; i < xres; i++) {
			for (int j = 0; j < yres ; j++) {
				complexarray[i][j] = new Complex(xmin + (xmax-xmin)*i/xres, ymin + (ymax-ymin)*j/yres);
			}
		}
	}

	public void calcMandel() {
		for (int i = 0; i < xres; i++) {
			for (int j = 0; j < yres ; j++) {
				Complex c = complexarray[i][j];
				Complex z = c;
				int count = 0;
				while(z.abs()<r && count+1 < N) {
					count++;
					z = Complex.add(Complex.mult(Complex.mult(Complex.mult(z,z),z),z), c);
				}
				double res = 0;
				if (z.abs() > r) {
					res = Math.log(Math.log(z.abs())/Math.log(r))/Math.log(2);
				}
				double v = count - res;
				array[i][j] = v/(double)N;
			}
		}	
	}


	public int getRGB(double v) {
		double r, g, b;
		double[] c0 = {0,0,0};
		double[] c1 = {0,0,0.6};
		double[] c2 = {.999,.999,.999};
		double[] c3 = {0.6,0.4,0};
		if(v < .25) {
			r = v/.25 * c1[0] + (1 - v/.25) * c0[0];
			g = v/.25 * c1[1] + (1 - v/.25) * c0[1];
			b = v/.25 * c1[2] + (1 - v/.25) * c0[2];
		} else if( v < .5) {
			r = (v-.25)/.25 * c2[0] + (1 - (v-.25)/.25) * c1[0];
			g = (v-.25)/.25 * c2[1] + (1 - (v-.25)/.25) * c1[1];
			b = (v-.25)/.25 * c2[2] + (1 - (v-.25)/.25) * c1[2];
		} else if( v < .75) {
			r = (v-.5)/.25 * c3[0] + (1 - (v-.5)/.25) * c2[0];
			g = (v-.5)/.25 * c3[1] + (1 - (v-.5)/.25) * c2[1];
			b = (v-.5)/.25 * c3[2] + (1 - (v-.5)/.25) * c2[2];
		} else {
			r = (v-.75)/.25 * c0[0] + (1 - (v-.75)/.25) * c3[0];
			g = (v-.75)/.25 * c0[1] + (1 - (v-.75)/.25) * c3[1];
			b = (v-.75)/.25 * c0[2] + (1 - (v-.75)/.25) * c3[2];
		}
		return (int)(r*256)<<16 | (int)(g*256)<<8 | (int)(b*256);

	}

	public void drawImage() {


		for (int i = 0; i < xres; i++) {
			for (int j = 0; j < yres; j++) {


				// 
				int rgb = getRGB(array[i][j]*10%1);

				output.setRGB(i, j, rgb);
			}
		}

		System.out.println("Image generated, saving...");
		try {
			ImageIO.write(output, "png", new File("gustaf/gustaf" + num + ".png"));
		} catch (Exception e) {
			System.out.println("error");
			e.printStackTrace();
		}

	}

	public static class Complex {
		double a,b;

		public Complex(double a, double b) {
			this.a = a;
			this.b = b;
		}

		public static Complex add(Complex first, Complex second) {
			return new Complex((first.a + second.a), (first.b + second.b));
		}

		public static Complex mult(Complex c1, Complex c2) {
			return new Complex(c1.a*c2.a - c1.b*c2.b, c1.b*c2.a +c1.a*c2.b);
		}

		public double abs() {
			return Math.sqrt(a*a+b*b);
		}

	}


	public static void main(String[] args) {
		for (int i = 0; i < 1; i++) {
			new Mandelbrot(i, cx-dx, cx+dx, cy-dy, cy+dy);
			dx *= .9;
			dy *= .9;
		}
	}
}