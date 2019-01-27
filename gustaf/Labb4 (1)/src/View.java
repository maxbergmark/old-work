import java.awt.Color;
import static java.awt.Color.*;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;


public class View extends JPanel {
    Model model;
    int x, y;

    View(int x, int y, Model model) {
        this.model = model;
        this.x = x;
        this.y = y;
        this.setPreferredSize(new Dimension (x,y));
    }

    @Override
    public void paint (Graphics g) {
        g.clearRect(0,0,x,y);
        Model.Particle[] particles = model.returnAll();
        for(int i = 0; i < particles.length; i++) {
            if(particles[i].stuck){
                g.setColor(new Color((int)particles[i].x*255/x, (int)particles[i].y*255/y,0));
                g.drawOval((int)particles[i].x, (int)particles[i].y, 1,1);
            } else {
                g.setColor(black);
                g.drawOval((int)particles[i].x, (int)particles[i].y, 1,1);
            }
        }
    }
}
