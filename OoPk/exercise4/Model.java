

public class Model {

	double x,y,vx,vy,dt;
	final double GRAVITY = 1000;

	public Model(double dt) {
		this.dt = dt;
		this.x = 250;
		this.y = 250;
		this.vx = 250;
		this.vy = -500;
	}

	public void move() {

		if (x < 0 || x > 485) {
			vx = -vx;
		}

		if (y < 0 || y > 485) {
			vy = -vy;
		}

		// kolla kollision med båda kanterna. Vi använde 485 istället
		// för 500 eftersom bollens diameter var 15.

		double newvx = vx;
		double newvy = vy + GRAVITY*dt;

		x += .5*(vx+newvx)*dt;
		y += .5*(vy+newvy)*dt;

		vx = newvx;
		vy = newvy;

		// Runge-Kutta 2 används här för att få en energikonservativ
		// lösning av differentialekvationen.

	}

	public double getX() {
		return x;		
	}

	public double getY() {
		return y;		
	}
}