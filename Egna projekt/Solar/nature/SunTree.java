package nature;


public class SunTree {
	private long dna;
	private double base = 0;
	private double top = 30;
	private double xpos;
	public SunTree(long dnaIn, double xposIn) {
		dna = dnaIn;
		xpos = xposIn;
	}
	public double getPos() {
		return xpos;
	}
	public double getBase() {
		return base;
	}
	public double getTop() {
		return top;
	}
}