import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

public class Model{
    private Grid grid;
	private static double L=1;
	private double xval;
	private double yval;
    private double vx;
    private double vy;
    private static int xsize;
    private static int ysize;
    private int particles;
    private Particle[] particleArray;
    private static int dt = 10;
    private int timepassed = 0;
    private int frames = 0;
    
    private double theta = (Math.random());

    public static class Particle{
        private double x;
        private double y;
        private double xvel;
        private double yvel;
        private Boolean frozen = false; 
        private Color color;
        private int id;
        private Random testrandom = new Random();


        public Particle(double xvalue, double yvalue, double xvelin, double yvelin, int idin){
            x = xvalue;
            y = yvalue;
            xvel = xvelin;
            yvel = yvelin;
            id = idin;
            
            color = new Color(0, 0, 255);
        }

        public static Particle randomParticle(int idin){
        	return new Particle(xsize*Math.random(),ysize*Math.random(),0,0,idin);
//            return new Particle(xsize/2, ysize-20, 1600*Math.random()-800, -700-200*Math.random(), idin);
                
        }



        public void updatePosition2(double L, double theta){
            if (!frozen) {
                x = x + L*Math.cos(theta);
                y = y + L*Math.sin(theta);
                checkEdges();
            }
        }

        public void updatePosition() {
            color = new Color((int)(256*Math.random()),(int)(256*Math.random()),(int)(256*Math.random()));
            
            x += 0.001*dt*xvel;
            double newyvel = yvel + 1000*dt*.001;
            y += (yvel+newyvel)*dt*.001;
            yvel = newyvel;
            if (y > ysize-10) {
                yvel = -Math.abs(yvel);
            }
            if (x < 10) {
                xvel = Math.abs(xvel);
            }
            if (x > xsize-10) {
                xvel = -Math.abs(xvel);
            }
        }

        public double getX() {
            return x;
        }
        public double getY() {
            return y;
        }
        public Color getColor() {
            return color;
        }
        public Boolean getFrozen() {
            return frozen;
        }
        public void freeze() {
            frozen = true;
            color = new Color(255, 0, 0);
        }
        public int getID() {
            return id;
        }

        public void checkEdges() {
            if (x < 1 || x > xsize-1 || y < 1 || y > ysize-1) {
                frozen = true;
                color = new Color(255, 0, 0);
            }
            if (Math.sqrt((xsize/2-x)*(xsize/2-x)+(ysize/2-y)*(ysize/2-y)) < Math.min(xsize, ysize)/3 && Math.sqrt((xsize/2-x)*(xsize/2-x)+(ysize/2-y)*(ysize/2-y)) > Math.min(xsize, ysize)/3-1) {
                frozen = true;
                color = new Color(255, 0, 0);
            }
        }
        
    }

    public class Grid {
        private ArrayList<ArrayList<Particle>> partList;
        private int xsize;
        private int ysize;
        public Grid (int xs, int ys) {
            xsize = xs;
            ysize = ys;
            partList = new ArrayList<ArrayList<Particle>>(xsize*ysize); 
            while (partList.size() < xsize*ysize) {
                partList.add(new ArrayList<Particle>()); 
            }
        }
        public void clearGrid() {
            for (int i = 0; i < xsize*ysize; i++) {
                partList.get(i).clear();
            }   
        }
        public void put(int x, int y, Particle p) {
            partList.get(xsize*y + x).add(p); 
        }
        public ArrayList<Particle> get(int x, int y) {
            return partList.get(xsize*y + x); 
        }
    }

    public Model(int p, int xs, int ys){
        particles = p;
        xsize = xs;
        ysize = ys;
        grid = new Grid(xsize, ysize); 
        particleArray = new Particle[particles]; 
        for (int i=0; i<particles; i++){
            particleArray[i] = Particle.randomParticle(i);
            
            

            
        }

    }

    public void updateParticles(){
        timepassed += dt;
        frames++;
        grid.clearGrid(); 
        for (int i = 0; i < particles; i++){ 
            //particleArray[i].updatePosition();
            
            particleArray[i].updatePosition2(L, 2*Math.PI*Math.random()); 
            if (0 < particleArray[i].getX() && particleArray[i].getX() < xsize && 0 < particleArray[i].getY() && particleArray[i].getY() < ysize) { 
                grid.put((int)particleArray[i].getX(), (int)particleArray[i].getY(), particleArray[i]); 
            }
            
        }
            
            
        
        ArrayList<Integer> indexes = new ArrayList<Integer>(); 
        for (int x = 0; x < xsize; x++) {
            for (int y = 0; y < ysize; y++) { 
                ArrayList<Particle> tempList = grid.get(x, y);
                for (Particle p : tempList) { 
                    if (p.getFrozen()) { 
                        for (int i = -1; i < 2; i++) { 
                            for (int j = -1; j < 2; j++) { 
                                if (0 <= x+i && x+i < xsize && 0 <= y+j && y+j < ysize) {
                                    ArrayList<Particle> cellList = grid.get(x+i, y+j);
                                    for (Particle pc : cellList) { 
                                        if (!pc.getFrozen()) {
                                            indexes.add(pc.getID());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for (int i : indexes) {
            particleArray[i].freeze();
        }
        
        
    }

    public int getTime() {
        return timepassed;
    }

    public int getFrame() {
        return frames;
    }

    public Particle getParticle(int i) {
        return particleArray[i];
    }

    
    
	public void setL(double lvalue){
		L=lvalue;
	}
	public double getL(){
		return L;
	}
    public int getPN() {
        return particles;
    }
    public void setDelta(int n) {
        dt = n;
    }
    public int getDelta() {
        return dt;
    }

    public static void main(String[] args) {
        Model hej = new Model(5, 800, 800);
    } 

}
