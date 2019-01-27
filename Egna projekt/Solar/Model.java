import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

import nature.*;

public class Model{
	private static double L=0;
	private double xval;
	private double yval;
    private static int xsize;
    private static int ysize;
    private int particles;
    private ArrayList<SunParticle> particleArray;
    private static int dt = 10;
    private int timepassed = 0;

    public static class SunParticle{
        private double x;
        private double y;
        private double yvel;
        private Boolean frozen = false;
        private Color color;
        private int id;

        public SunParticle(double xvalue, double yvelin, int idin){
            x = xvalue;
            y = 0;
            yvel = yvelin;
            id = idin;
            color = new Color(255, 150, 0);
        }

        public static SunParticle randomParticle(int idin){
                return new SunParticle(xsize*Math.random(), 5, idin);
        }


        public void updatePosition2(){
            if (!frozen) {
                y += yvel;
                checkEdges();
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

            if (y > ysize) {
                frozen = true;
            }

        }
        
    }

    public class Grid {
        private ArrayList<ArrayList<SunParticle>> partList;
        private int xsize;
        private int ysize;
        public Grid (int xs, int ys) {
            xsize = xs;
            ysize = ys;
            partList = new ArrayList<ArrayList<SunParticle>>(xsize*ysize);
            while (partList.size() < xsize*ysize) {
                partList.add(new ArrayList<SunParticle>());
            }
        }
        public void put(int x, int y, SunParticle p) {
            partList.get(xsize*y + x).add(p);
        }
        public ArrayList<SunParticle> get(int x, int y) {
            return partList.get(xsize*y + x);
        }
    }

    public Model(int p, int xs, int ys){
        particles = p;
        xsize = xs;
        ysize = ys;
        particleArray = new ArrayList<SunParticle>(particles); 
        for (int i=0; i<particles; i++){
            particleArray.add(SunParticle.randomParticle(i));
        }

    }

    public void updateParticles() {
        for (SunParticle p: particleArray) {
            p.updatePosition2();
        }
        for (int i = particles-1; i >= 0; i--) {
            SunParticle temp = particleArray.get(i);
            if (temp.getFrozen()) {
                particleArray.remove(temp);
                particles--;
            }
        }
    }

    public void addParticles(int n) {
        for (int i = 0; i < n; i++) {
            particleArray.add(SunParticle.randomParticle(0));
            particles++;
        }
    }

    public int getTime() {
        return timepassed;
    }

    public SunParticle getParticle(int i) {
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
