import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Model {
    Particle[] particles;
    ArrayList<ParticleGroup> groups;
    double l;
    int xsize, ysize;
    boolean[][] stucklist;
    long time;
    StringBuilder file;
    int counter = 0;

    public Model (int p, double l, int x, int y) {
        particles = new Particle[p];
        groups = new ArrayList<>();
        this.l = l;
        this.xsize = x;
        this.ysize = y;
        for (int i = 0; i < particles.length; i++) {
            particles[i] = new Particle(x, y);
        }
        stucklist = new boolean[x][y];
        time = System.nanoTime();
        file = new StringBuilder();
    }
    class Particle {
        double x, y;
        boolean stuck;
        boolean group;
        ParticleGroup papa;

        public Particle (int x, int y) {
            this.x = 1 + Math.random() * (x - 3);
            this.y = 1 + Math.random()* (y - 3);
            stuck = false;
            group = false;

        }
        public void updateParticle() {
            if(!group) {
                double theta = Math.random() * 2 * Math.PI;
                x += Math.cos(theta)*l;
                y += Math.sin(theta)*l;
            }
            if(x<1) { stuck = true; x = 1; stucked(); }
            if(x>xsize-3) { stuck = true; x = xsize-3; stucked(); }
            if(y<1) { stuck = true; y = 1; stucked(); }
            if(y>ysize-3) { stuck = true; y = ysize-3; stucked(); }
        }

        public void isStuck() {
            if(stucklist[(int)x][(int)y]) {
                stuck = true;
                stucked();
                if(group) {
                    papa.stuckGroup();
                }
            }
        }
        public void stucked() {
            int x = (int)this.x;
            int y = (int)this.y;
            for (int i = x-1; i <= x+1; i++) {
                for (int j = y-1; j <= y+1; j++) {
                    stucklist[i][j] = true;
                }
            }
        }
    }
    class ParticleGroup extends ArrayList<Particle> {
        boolean isGroupStuck;
        public ParticleGroup (Particle a, Particle b) {
            groups.add(this);
            add(a);
            add(b);
            a.group = true;
            a.papa = this;
            b.group = true;
            b.papa = this;
            isGroupStuck = false;
        }
        public void addToGroup (Particle a) {
            add(a);
            a.group = true;
            a.papa = this;
        }
        public void mergeGroups (ParticleGroup a) {
            for(int i = 0; i < a.size(); i++) {
                this.addToGroup(a.get(i));
                a.get(i).papa = this;
            }
            a.clear();
        }
        public void updateGroup () {
            if(!isGroupStuck) {
                double theta = Math.random() * 2 * Math.PI;
                for (int i = 0; i < size(); i++) {
                    get(i).x += Math.cos(theta)*l;
                    get(i).y += Math.sin(theta)*l;
                    if(get(i).x<1) { get(i).x = 1; stuckGroup(); }
                    if(get(i).x>xsize-3) { get(i).x = xsize-3; stuckGroup(); }
                    if(get(i).y<1) { get(i).y = 1; stuckGroup(); }
                    if(get(i).y>ysize-3) { get(i).y = ysize-3; stuckGroup(); }
                }
            }
        }
        public void stuckGroup () {
            isGroupStuck = true;
            for (int i = 0; i < size(); i++) {
                get(i).stuck = true;
                get(i).stucked();
            }
        }
        public ParticleGroup returnGroup () {
            return this;
        }
    }
    public void updateAll() {
        Particle[][] array = particleArray();
        for (int i = 0; i < particles.length; i++) {
            if(!particles[i].stuck){
                particles[i].updateParticle();
                particles[i].isStuck();
                addToParticleGroup(array, particles[i]);
            }
        }
        for(int i = 0; i < groups.size(); i++) {
            groups.get(i).updateGroup();
        }
        /*if(counter == 0){
            time = System.nanoTime();
        }
        if(counter <= 100) {
            makeFile();
        }
        if(counter == 100) {
            writeToFile();
        }*/
    }
    public void makeFile () {
        file.append((System.nanoTime() - time)/1e9);
        file.append(", ");
        for (int i = 0; i < particles.length; i++) {
            file.append(particles[i].x);
            file.append(", ");
            file.append(particles[i].y);
            file.append(", ");
        }
        file.append("\r\n");
        System.out.println(counter);
        counter++;
    }
    public void writeToFile () {
        try(PrintWriter out = new PrintWriter("Particles.txt")){
            out.print(file);
        } catch (IOException e) {}
    }
    public Particle[][] particleArray () {
        Particle[][] ParticlesForGroups = new Particle[xsize][ysize];
        for(int i = 0; i < particles.length; i++) {
            if(!particles[i].stuck) {
                ParticlesForGroups[(int)particles[i].x][(int)particles[i].y] = particles[i];
            }
        }
        return ParticlesForGroups;
    }
    public void addToParticleGroup (Particle[][] array, Particle p) {
        for (int u = (int)p.x-1; u <= (int)p.x+1; u++) {
            for (int j = (int)p.y-1; j <= (int)p.y+1; j++) {
                if(array[u][j]!=null && array[u][j] != p) {
                    if(!p.group && !array[u][j].group) {
                        groups.add(new ParticleGroup(p, array[u][j]));
                    } else if (p.group && !array[u][j].group) {
                        p.papa.addToGroup(array[u][j]);
                    } else if (!p.group && array[u][j].group) {
                        array[u][j].papa.addToGroup(p);
                    } else if (p.group && array[u][j].group && array[u][j].papa != p.papa) {
                        p.papa.mergeGroups(array[u][j].papa);
                    }
                }
            }
        }

    }

    public Particle[] returnAll() {
        return particles;
    }
}

