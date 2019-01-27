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
    private static int dt = 1;
    private int timepassed = 0;
    private int frames = 0;
    //private double theta = Math.random(); //Minns inte sökvägen eller importen
    private double theta = (Math.random());
    KeyboardFocusManager m;
    boolean[] keyStates = {false, false, false, false};
    private double r0;
    private double x0;
    private double y0;
    private double scale;

    public class Particle{
        private double x;
        private double y;
        private double r;
        private double xvel;
        private double yvel;
        private Boolean frozen = false; //när den krockar, sätt till true, och då flyttas den ej mer. Sortera listan m.a.p. x, kolla grannar mot varandra, om nära, kolla avstånd m.h.a. pythagoras.
        private Color color;
        private int id;
        private long t = System.nanoTime();
        private Random testrandom = new Random();
        



        public Particle(double xvalue, double yvalue, double xvelin, double yvelin, int idin){
            x = xvalue;
            y = yvalue;
            xvel = xvelin;
            yvel = yvelin;
            id = idin;
            r = 2;
            //color = new Color(testrandom.nextInt(256), testrandom.nextInt(256), testrandom.nextInt(256));
            color = new Color(0, 0, 255);
        }

        public Particle(int idin){
            x = (xsize-40)*Math.random()-20;
            y = (ysize-40)*Math.random()-20;
            r = 4+(Math.random()+1)*r0/2.0;
            xvel = 160*Math.random()-80;
            yvel = 160-80*Math.random();
            //color = new Color(0, (int)(255*(r-5)/10), (int)(255*(15-r)/10));
            id = idin;
            if (id == 0) {
                color = new Color(255, 0, 0);
                r = 20;
                r0 = 20;
            }
            t = System.nanoTime();
        }

        public Particle(){
            x = xsize/2;
            y = ysize/2;
            r = 20;
            r0 = 20;
            xvel = 0;//1600*Math.random()-800;
            yvel = 0;//-700-200*Math.random();
            id = 0;
            color = new Color(255, 0, 0);
            t = System.nanoTime();
        }



        public void updatePosition2(double L, double theta){
            if (!frozen) {
                x = x + L*Math.cos(theta);
                y = y + L*Math.sin(theta);
                checkEdges();
            }
        }

        public void updatePosition() {
            //color = new Color((int)(256*Math.random()),(int)(256*Math.random()),(int)(256*Math.random()));
            //color = new Color(0, 0, 255);
            if (!getFrozen()) {
                double yg = keyStates[2] ? 5000 : 0;
                double xg = keyStates[1] ? -5000 : 0;
                yg = keyStates[0] ? -5000 : yg;
                xg = keyStates[3] ? 5000 : xg;
                yg = keyStates[0] & keyStates[2] ? 0 : yg;
                xg = keyStates[1] & keyStates[3] ? 0 : xg;
                xg = id == 0 ? xg : 0;
                yg = id == 0 ? yg : 0;

                double totalFx = 0;
                double totalFy = 0;

                for (Particle p: particleArray) {
                    if (p.getID() != id && !p.getFrozen() && !getFrozen()) {
                        double tempx = p.getX();
                        double tempy = p.getY();
                        double tempr = p.getR();
                        double tempm = p.getM();
                        double dist = Math.max((x-tempx)*(x-tempx)+(y-tempy)*(y-tempy), Math.min(getR(), p.getR()));
                        if (Math.sqrt(dist) < Math.max(getR(), p.getR())) {
                            if (getR() < p.getR()) {
                                p.addMass(r*r*r);
                                p.setVX((p.getM()*p.getVX() + getM()*getVX())/(p.getM()+getM()));
                                p.setVY((p.getM()*p.getVY() + getM()*getVY())/(p.getM()+getM()));
                                freeze();
                            } else {
                                addMass(p.getM());
                                setVX((p.getM()*p.getVX() + getM()*getVX())/(p.getM()+getM()));
                                setVY((p.getM()*p.getVY() + getM()*getVY())/(p.getM()+getM()));
                                p.freeze();
                            }
                        }
                        if (id == 0 && p.getR() < getR()) {
                            p.setColor(new Color(0, 255, 0));
                        } else if (id == 0 && p.getR() > getR()) {
                            p.setColor(new Color(0, 0, 255));
                        }
                        double ang = Math.atan2(tempy-y, tempx-x);
                        double f = 300000.0*getM()*tempm/dist;
                        double fy = f*Math.sin(ang);
                        double fx = f*Math.cos(ang);
                        totalFx += fx;
                        totalFy += fy;
                    }
                }


                //x += 0.001*dt*xvel;
                double dt = (System.nanoTime()-t)/5000000.0;
                t = System.nanoTime();
                double newyvel = yvel + (yg+totalFy/getM()/r0/r0/r0)*dt*.001;
                double newxvel = xvel + (xg+totalFx/getM()/r0/r0/r0)*dt*.001;
                y += (yvel+newyvel)*dt*.001;
                x += (xvel+newxvel)*dt*.001;
                if (id == 0) {
                    x0 = x;
                    y0 = y;
                }

                yvel = newyvel;
                xvel = newxvel;
                if (y > ysize-getR()) {
                    yvel = -Math.abs(yvel);
                }
                if (y < getR()) {
                    yvel = Math.abs(yvel);
                }

                if (x < getR()) {
                    xvel = Math.abs(xvel);
                }
                if (x > xsize-getR()) {
                    xvel = -Math.abs(xvel);
                }
            }
        }

        public double getX() {
            return x;
        }
        public double getY() {
            return y;
        }
        public double getXRel() {
            double xrel = (1/scale*x + ((scale-1)/scale)*(x-x0));
            double xscr = 1/5.0*(xsize/scale/2) + 4/5.0*xrel;
            return xscr;
        }
        public double getYRel() {
            double yrel = (1/scale*y + ((scale-1)/scale)*(y-y0));
            double yscr = 1/5.0*(ysize/scale/2) + 4/5.0*yrel;
            return yscr;
        }
        public double getR() {
            return 20*r/r0;
        }
        public double getR0() {
            return r;
        }
        public double getM() {
            return r*r*r;
        }
        public double getVX() {
            return xvel;
        }
        public double getVY() {
            return yvel;
        }
        public void setVX(double xv) {
            xvel = xv;
        }
        public void setVY(double yv) {
            yvel = yv;
        }
        public void addMass(double m) {
            r = Math.cbrt(getM()+m);
        }
        public Color getColor() {
            return color;
        }
        public void setColor(Color c) {
            color = c;
        }
        public Boolean getFrozen() {
            return frozen;
        }
        public void freeze() {
            frozen = true;
            xvel = 0;
            yvel = 0;
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
            partList = new ArrayList<ArrayList<Particle>>(xsize*ysize); // stor lista med inre listor
            while (partList.size() < xsize*ysize) {
                partList.add(new ArrayList<Particle>()); //skapar bara tomma listelement tills vi nått rätt storlek
            }
        }
        public void clearGrid() {
            for (int i = 0; i < xsize*ysize; i++) {
                partList.get(i).clear();
            }   
        }
        public void put(int x, int y, Particle p) {
            partList.get(xsize*y + x).add(p); //lägger till vår partikel
        }
        public ArrayList<Particle> get(int x, int y) {
            return partList.get(xsize*y + x); //kan här få flera partiklar som ligger på samma pixel men även noll partiklar
        }
    }

    private class YourDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_TYPED) {
                System.out.println("hej");
            } else if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    keyStates[0] = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    keyStates[1] = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    keyStates[2] = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    keyStates[3] = true;
                }
            } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    keyStates[0] = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    keyStates[1] = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    keyStates[2] = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    keyStates[3] = false;
                }
            }
            return false;
        }
    }


    public Model(int p, int xs, int ys, double sf){
        particles = p;
        xsize = (int)(xs*sf);
        ysize = (int)(ys*sf);
        scale = sf;
        //grid = new Grid(xsize, ysize); //rutnätsform
        KeyboardFocusManager m = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        m.addKeyEventDispatcher(new YourDispatcher());
        particleArray = new Particle[particles];
        particleArray[0] = new Particle(); 
        for (int i=1; i<particles; i++){
            particleArray[i] = new Particle(i);
            //particleArray[i] = new Particle(100 + 5*i, 600 + i, 0, 0);
            

            
        }

    }

    public void updateParticles(){
        //System.out.println(Arrays.toString(keyStates));
        timepassed += dt;
        frames++;
        //grid.clearGrid(); //rutnätsform
        for (int i = 0; i < particles; i++){ //loopar över alla partiklar

            particleArray[i].updatePosition();
            /*
            particleArray[i].updatePosition2(L, 2*Math.PI*Math.random()); //uppdaterar positionen
            if (0 < particleArray[i].getX() && particleArray[i].getX() < xsize && 0 < particleArray[i].getY() && particleArray[i].getY() < ysize) { //om partikeln är i rutan, är den utanför har vi redan frusit den
                grid.put((int)particleArray[i].getX(), (int)particleArray[i].getY(), particleArray[i]); //sätter in partikeln i vårt grid
            }
            */
        }
        //System.out.println(r0);
        r0 = particleArray[0].getR0();
        for (int i = 0; i < particles; i++) {
            if (particleArray[i].getFrozen()) {
                particleArray[i] = new Particle(i);
            }
        }

            //particleArray[i].updatePosition();
            
        /*
        ArrayList<Integer> indexes = new ArrayList<Integer>(); //skapar indexlista för de partiklar som ska frysas
        for (int x = 0; x < xsize; x++) {
            for (int y = 0; y < ysize; y++) { //loopar igenom alla pixlar!
                ArrayList<Particle> tempList = grid.get(x, y);
                for (Particle p : tempList) { //loopar igenom partiklar p ur tempList, sker oftast inte
                    if (p.getFrozen()) { //körs om den är frusen
                        for (int i = -1; i < 2; i++) { //kollar igenom tre på x-nivå
                            for (int j = -1; j < 2; j++) { //kollar igenom tre på y-nivå -> 9 st
                                if (0 <= x+i && x+i < xsize && 0 <= y+j && y+j < ysize) {
                                    ArrayList<Particle> cellList = grid.get(x+i, y+j);
                                    for (Particle pc : cellList) { //för varje partikel vi har hittat ser vi till att frysa den
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
        */
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
    public int getXSize() {
        return xsize;
    }
    public int getYSize() {
        return ysize;
    }

    public static void main(String[] args) {
        Model hej = new Model(5, 800, 800, 4.0);
    } 

}

