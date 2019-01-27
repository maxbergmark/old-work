

public class BollModelDemo {

	double x, y;
	double vx, vy;
	double dt;
	final double GRAVITY = 982;
	BollViewDemo v;


	public BollModelDemo(double dt) {
		v = new BollViewDemo();
		vx = 750;
		vy = -750;
		x = 250;
		y = 250;
		this.dt = dt;
	}

	public void move() {

		if (x < 0 || x > 475) {
			vx = -vx;
		}
		if (y < 0 || y > 475) {
			vy = -vy;
		}
		double newvx = vx;
		double newvy = vy + GRAVITY*dt;



		x += .5*(vx+newvx)*dt;
		y += .5*(vy+newvy)*dt;

		vy = newvy;
		vx = newvx;
		v.newEvent(x,y);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}