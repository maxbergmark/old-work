import java.util.ArrayList;


public class Model {

	double px, py, vx, vy, fx;
	double dt = 0.016;
	boolean jumping;
	int walk;
	boolean[] keyState;
	int xdim, ydim, xs, ys;
	int ground;
	final int OFFSET = 2;
	ArrayList<Entity> collides;
	private boolean started = false;
	private boolean stopped = false;
	private long startTime = 0;

	public Model(int xdim, int ydim, int xs, int ys) {
		py = ydim-ys;
		this.xdim = xdim;
		this.ydim = ydim;
		this.xs = xs;
		this.ys = ys;

	}

	public long getStartTime() {
		return startTime;
	}

	public void sendEntities(ArrayList<Entity> e) {
		this.collides = e;
	}

	public void setGround(int ground) {
		this.ground = ground;
	}

	public int getMove() {
		return walk;
	}

	public double getVx() {
		return vx;
	}

	public void setDT(double dt) {
		this.dt = dt;
	}
	public boolean getStopped() {
		return this.stopped;
	}

	public void move() {
		if (!started) {
			for (boolean b : keyState) {
				if (b) {
					started = true;
					startTime = System.nanoTime();
				}
			}
		} else {
			double elapsed = (System.nanoTime()-startTime)/1e9;
			if (elapsed > 20) {
				dt = 0;
				stopped = true;
			}
		}
		fx = 0;
		if (keyState[0]) {
			fx = -15*ydim;
			walk = -2;
		} else {
			walk = (walk==1)?1:(walk == 0 | walk == 2) ? 0 : -1;
		}
		if (keyState[1]) {
			fx = 15*ydim;
			walk = (walk>=0)?1:0;
		} else {
			walk = (walk==-2)?-2 : (walk==-1) ? -1 : 0;
		}
		if (keyState[0] & keyState[1]) {
			fx = 0;
		}
		if (keyState[2]) {
			if (!jumping) {
				vy = -ydim*0.7;
				jumping = true;
			}
		}
		if (keyState[3]) {
			vy = Math.max(vy, Math.max(ydim,0.8*Math.abs(vx)));
		}
		if (jumping) {
			if (vx > 0) {
				walk = 2;
			} else {
				walk = -3;
			}
		}
		double bx = 0.75*vx*(py+ydim)/(2.0*ydim);
		if (!jumping) {
			if (vx > 0) {
				bx = Math.max(10*Math.atan(vx),vx);
			} else {
				bx = Math.min(10*Math.atan(vx),vx);
			}
		}
		vx += fx*dt-10.0*bx*dt;
		px += vx*dt;
		if (jumping) {
			vy += ydim*dt;
		}
		py += vy*dt;
		if (py > ground+OFFSET-ys) {
			vy = 0;
			py = ground+OFFSET-ys;
			jumping = false;
		}
		checkCollide();
	}

	public void checkCollide() {
		if (py != ground+OFFSET-ys) {
			jumping = true;
		}
		double bound = Math.max(20,Math.abs(vy)*dt*2);
		for (Entity e : collides) {
			int exs = e.getXDim();
			int eys = e.getYDim();
			if (py > e.getY() - ys+bound) {
				if (px + xs > e.getX() && px + xs < e.getX() + exs) {
					px = e.getX()-xs;
					vx = e.getLDamp();
				}
				if (px < e.getX()+exs && px > e.getX()) {
					px = e.getX() + exs;
					vx = e.getRDamp();
				}
			} 
			if (py > e.getY() - ys && py < e.getY()-ys+bound) {
				if (px + xs > e.getX() && px < e.getX()+exs && vy > 0) {
					vy = e.getTDamp();
					py = e.getY() - ys + OFFSET;
					if (vy > 0) {
						jumping = false;
					}
				}

			}
		}

	}

	public double getX() {
		return px;
	}

	public void setX(double x) {
		this.px = x;
	}

	public double getY() {
		return py;
	}

	public void updateKeys(boolean[] keys) {
		keyState = keys;
	}
}