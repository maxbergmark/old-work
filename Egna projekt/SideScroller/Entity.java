import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.geom.*;

public class Entity implements Comparable<Entity> {


	BufferedImage img;
	double z, x, y;
	LoadImg fN;
	double driftx, drifty;
	boolean inMiddle = true;
	boolean repeatable = false;
	boolean collide = false;
	int xd, yd;
	double lDamp, tDamp, rDamp;

	public Entity(LoadImg i, double z, int xd, int yd) {
		this.img = i.get();
		this.z = z;
		this.fN = i;
		this.xd = xd;
		this.yd = yd;
	}

	public void setCollide(boolean b) {
		this.collide = b;
	}

	public boolean getCollide() {
		return this.collide;
	}

	public void setDamp(double l, double t, double r) {
		this.lDamp = l;
		this.tDamp = t;
		this.rDamp = r;
	}

	public double getLDamp() {
		return this.lDamp;
	}

	public double getTDamp() {
		return this.tDamp;
	}

	public double getRDamp() {
		return this.rDamp;
	}

	public void setRepeatable(boolean b) {
		this.repeatable = b;
	}

	public boolean getRepeatable() {
		return this.repeatable;
	}

	public void setMiddle(boolean b) {
		this.inMiddle = b;
	}

	public boolean getMiddle() {
		return this.inMiddle;
	}

	public String toString() {
		return null;
	}

	public void setDrift(double x, double y) {
		this.driftx = x;
		this.drifty = y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public int getXDim() {
		return this.xd;
	}

	public int getYDim() {
		return this.yd;
	}

	public void advance(int f) {

	}

	public void drift(double x, int xdim) {
		this.x += driftx;
		this.y += drifty;
		if ((this.x-x+xd)*z > 2*xdim) {
			this.x -= (4-0.5*Math.random())*xdim/z;
		}
		if ((this.x-x+xd)*z < -2*xdim) {
			this.x += (4-0.5*Math.random())*xdim/z;
		}
	}

	public BufferedImage getImg() {
		return img;
	}

	public int compareTo(Entity e) {
		return z>e.getZ()?1:(z<e.getZ()?-1:0);
	}

}