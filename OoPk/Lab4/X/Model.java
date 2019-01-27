import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

public class Model{
	private static double L=0;
	private double xval;
	private double yval;
    private double vx;
    private double vy;
    private static int xsize;
    private static int ysize;
    private int particles;
    private ArrayList<ArrayList<Particle>> particleArray;
    private static int dt = 10;
    private int timepassed = 0;
    //private double theta = Math.random(); //Minns inte sökvägen eller importen
    private double theta = (Math.random());

    public static class Particle implements Comparable<Particle>{
        private double x;
        private double y;
        private double xvel;
        private double yvel;
        private Boolean frozen = false; //när den krockar, sätt till true, och då flyttas den ej mer. Sortera listan m.a.p. x, kolla grannar mot varandra, om nära, kolla avstånd m.h.a. pythagoras.
        private Color color;
        private int id;
        private Random testrandom = new Random();


        public Particle(double xvalue, double yvalue, double xvelin, double yvelin, int idin){
            x = xvalue;
            y = yvalue;
            xvel = xvelin;
            yvel = yvelin;
            id = idin;
            //color = new Color(testrandom.nextInt(256), testrandom.nextInt(256), testrandom.nextInt(256));
            color = new Color(0, 0, 255);
        }

        public static Particle randomParticle(int idin){
                //return new Particle(300,300, 0, 0, idin);
                return new Particle(xsize*Math.random(),ysize*Math.random(), 1600*Math.random()-800, 1600*Math.random()-800, idin); // Ska vara random koord. dock.
        }

        public int compareTo(Particle p) {
            double px = p.getX();
            if (x < px) {
                return -1;
            }
            if (x > px) {
                return 1;
            }
            return 0;
        }

        public void updatePosition2(double L, double theta){
            if (!frozen) {
                x = x + L*Math.cos(theta);
                y = y + L*Math.sin(theta);
                checkEdges();
            }
        }

        public void updatePosition() {

            x += xvel*dt*.001;
            double yvelo = yvel;
            yvel += 9.82*470*L*dt*.001;
            y += .5*(yvelo+yvel)*dt*.001;
            //y += yvelo*dt*.001; //konservera energin, n00b
            
            if(x>760){
                xvel = -xvel;
                //yvel = yvel*.5 - 1000;
            }
            if(y>790 && yvel > 0){
                yvel = -yvel;
                //xvel += 100;
            }
            if(x<0){
                xvel = -xvel;
            }
            if(y<0 && yvel < 0){
                yvel = -yvel;
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
        public void setID(int idin) {
            id = idin;
        }

        public void checkEdges() {
            if (x < 1 || x > xsize-1 || y < 1 || y > ysize-1) {
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
        particleArray = new ArrayList<ArrayList<Particle>>(particles); 
        for (int i=0; i<particles; i++){
            particleArray.add(new ArrayList<Particle>());
            particleArray.get(i).add(Particle.randomParticle(i));
            //particleArray[i] = new Particle(100 + 5*i, 600 + i, 0, 0);
            

            
        }

    }

    public void updateParticles(){
        timepassed += dt;
        Grid grid = new Grid(xsize, ysize);
        for (int i = 0; i < particles; i++){
            Boolean willfreeze = false;
            double theta = 2*Math.PI*Math.random();
            for (Particle p : particleArray.get(i)) {
                p.updatePosition2(L, theta);
                if (p.getFrozen()) {
                    willfreeze = true;
                }
                if (0 < p.getX() && p.getX() < xsize && 0 < p.getY() && p.getY() < ysize) {
                    grid.put((int)p.getX(), (int)p.getY(), p);
                }
            }
            if (willfreeze) {
                for (Particle p : particleArray.get(i)) {
                    p.freeze();
                }
            }
        }
            //particleArray[i].updatePosition();
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for (int x = 0; x < xsize; x++) {
            for (int y = 0; y < ysize; y++) {
                
                ArrayList<Particle> tempList = grid.get(x, y);
                for (Particle p : tempList) {

                        for (int i = -1; i < 2; i++) {
                            for (int j = -1; j < 2; j++) {
                                if (0 <= x+i && x+i < xsize && 0 <= y+j && y+j < ysize) {
                                    ArrayList<Particle> cellList = grid.get(x+i, y+j);
                                    for (Particle pc : cellList) {
                                        if (!(p.getID() == pc.getID()) && p != pc) {
                                            int tempid = pc.getID();
                                            for (Particle pi : particleArray.get(pc.getID())) {

                                                pi.x = (int) pi.x + (p.x%1);
                                                pi.y = (int) pi.y + (p.y%1);
                                                particleArray.get(p.getID()).add(pi);
                                                pi.setID(p.getID());
                                            }
                                            particleArray.get(tempid).clear();
                                            

                                        }
                                    }
                                }
                            }
                        }

                }
            }
        }

        
        
        /*
        Arrays.sort(particleArray);
        //System.out.println("hej");
        for (int i = 0; i < particles-1; i++) {
            if (particleArray[i].getFrozen() ^ particleArray[i+1].getFrozen()) {
                if (particleArray[i+1].getX() - particleArray[i].getX() < 5) {
                    if (Math.abs(particleArray[i+1].getY() - particleArray[i].getY()) < 5) {
                        System.out.println("Freezing" + i);
                        particleArray[i].freeze();
                        particleArray[i+1].freeze();
                    }
                }
            }
        }
        */
    }

    public int getTime() {
        return timepassed;
    }

    public ArrayList<Particle> getParticleGroup(int i) {
        return particleArray.get(i);
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
