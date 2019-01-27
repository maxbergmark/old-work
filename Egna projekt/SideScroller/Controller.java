import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller implements ActionListener {

	View v;
	Model m;
	ArrayList<Entity> entities;
	HashMap<String,LoadImg> images;
	int frame;
	Sprite player;
	int cameraOffset;
	int cameraDirection = 0;
	long lastTime = 0;
	double dt;
	int xdim, ydim;
	int playerx, playery;
	int ground;
	SoundMixer mixer;

	public Controller(int xdim, int ydim) {
		
		this.xdim = xdim;
		this.ydim = ydim;
		playerx = ydim/7;
		playery = ydim/7;

		long t0 = System.nanoTime();
		loadFiles();
		long t1 = System.nanoTime();
		System.out.println(String.format("\n   Images loaded in %.3f ms.", (t1-t0)/1000000.0));
		
		createEntities();


		long t2 = System.nanoTime();
		System.out.println(String.format("   %d enities created in %.3f ms.", entities.size(), (t2-t1)/1000000.0));

		m = new Model(xdim, ydim, playerx, playery);
		sendCollides();
		v = new View(m, xdim, ydim);
		Timer t = new Timer(16, this);
		v.sendEntities(entities);
		m.setGround(ground);
		System.out.println();
		mixer = new SoundMixer("res/game2.wav");
		//new Thread(mixer).start();
		t.start();
	}

	public void loadFiles() {
		images = new HashMap<>();
		LoadImg temp1 = new LoadImg("sonic2.png", playerx*10, playery*6);
		LoadImg temp2 = new LoadImg("cloud.png", ydim/2, ydim/4);
		LoadImg temp3 = new LoadImg("cloud.png", ydim, ydim/2);
		LoadImg temp4 = new LoadImg("box.jpg", ydim/6, ydim/6);
		LoadImg temp5 = new LoadImg("mushroom.png", ydim/8, ydim/8);
		images.put("sonic", temp1);
		images.put("cloud", temp2);
		images.put("bigcloud", temp3);
		images.put("box", temp4);
		images.put("mushroom", temp5);
		new Thread(temp1).start();
		new Thread(temp2).start();
		new Thread(temp3).start();
		new Thread(temp4).start();
		new Thread(temp5).start();
		loadScenery2();
	}

	public void createEntities() {
		entities = new ArrayList<>();
		player = new Sprite(images.get("sonic"), 10, 6, 60, 4, playerx, playery, 6);
		setPlayerStats();
		entities.add(player);
		createScenery2();

		for (int i = 0; i < 3; i++) {
			Entity e1 = getRandomEntity("cloud", 0.1, 0.5, 0, 200, -ydim/200, ydim/200, false);
			Entity e2 = getRandomEntity("bigcloud", 0.1, 0.5, 0, 200, -ydim/100, ydim/100, false);
			Entity e3 = getRandomEntity("box", 1, 1, ground-ydim/6, ground-ydim/6, 0, 0, true);
			Entity e4 = getRandomEntity("mushroom", 1, 1, ground-ydim/8, ground-ydim/8, 0, 0, true);
			e3.setDamp(1.0, 1.0, -1.0);
			e4.setDamp(1.0, -ydim, -1.0);
			entities.add(e1);
			entities.add(e2);
			entities.add(e3);
			entities.add(e4);
		}

	}

	public void createScenery1() {
		addBackgroundEntity("layer1");
		addBackgroundEntity("layer2");

		addRepeatableEntity("layer3", 0.2, ydim*16/9, ydim);
		addRepeatableEntity("layer4", 0.25, ydim*16/9, ydim);
		addRepeatableEntity("layer5", 0.33333, ydim*16/9, ydim);
		addRepeatableEntity("layer6", 0.5, ydim*16/9, ydim);
		addRepeatableEntity("layer7", 1, ydim*16/9, ydim);
		addRepeatableEntity("layer8", 1, ydim*16/9, ydim);	
	}

	public void createScenery2() {
		addBackgroundEntity("layer1");

		addRepeatableEntity("layer2", 0.2, ydim*16/9, ydim);
		addRepeatableEntity("layer3", 0.3333, ydim*16/9, ydim);
		addRepeatableEntity("layer4", 0.5, ydim*16/9, ydim);
		addRepeatableEntity("layer5", 1, ydim*16/9, ydim);
	}

	public void createScenery3() {
		addBackgroundEntity("layer1");
		addBackgroundEntity("layer2");

		addRepeatableEntity("layer3", 0.2, ydim*16/9, ydim);
		addRepeatableEntity("layer4", 0.3333, ydim*16/9, ydim);
		addRepeatableEntity("layer5", 0.5, ydim*16/9, ydim);
		addRepeatableEntity("layer6", 1, ydim*16/9, ydim);
		addRepeatableEntity("layer7", 1.2, ydim*16/9, ydim);
	}

	public void loadScenery1() {
		int layers = 8;
		for (int i = 1; i <= layers; i++) {
			int x = ydim*16/9;
			if (i <= 2 && xdim > x) {x = xdim;}
			LoadImg temp = new LoadImg("layer_0" + i + "_2.png", x, ydim);
			images.put("layer" + i, temp);
			new Thread(temp).start();
		}

		checkMap();
		ground = images.get("layer8").getGround();	
	}

	public void loadScenery2() {
		int layers = 5;
		for (int i = 1; i <= layers; i++) {
			int x = ydim*16/9;
			if (i <= 1 && xdim > x) {x = xdim;}
			LoadImg temp = new LoadImg("layer_0" + i + "_1.png", x, ydim);
			images.put("layer" + i, temp);
			new Thread(temp).start();
		}
		checkMap();
		ground = images.get("layer5").getGround();	
	}

	public void loadScenery3() {
		int layers = 7;
		for (int i = 1; i <= layers; i++) {
			int x = ydim*16/9;
			if (i <= 2 && xdim > x) {x = xdim;}
			LoadImg temp = new LoadImg("layer_0" + i + "_3.png", x, ydim);
			images.put("layer" + i, temp);
			new Thread(temp).start();
		}

		checkMap();
		ground = images.get("layer6").getGround();	
	}

	public void checkMap() {
		int c;
		do {
			c = 0;
			for (String key : images.keySet()) {
				c += images.get(key).ready()?1:0;
				if (!images.get(key).ready()) {
					//System.out.println(key + " not ready.");
				}
			}
			try {
				Thread.sleep(100);
			} catch (Exception e) {}
		} while (c < images.size());
	}

	public void setPlayerStats() {
		player.setSpeed(4,1);
		player.setSpeed(5,1);
		player.setState(0,10,14);
		player.setState(1,50,46);
		player.setState(2,0,1);
		player.setState(3,39,40);
		player.setState(4,21,29);
		player.setState(5,59,51);
		player.changeState(2);
		player.setZ(1.00001);
	}

	public Entity getRandomEntity(String name, double zmin, double zmax, 
		double ymin, double ymax, double driftmin, double driftmax, boolean collide) {
		LoadImg i = images.get(name);
		Entity e = new Entity(i, zmin+Math.random()*(zmax-zmin), 
			i.get().getWidth(), i.get().getHeight());
		e.setY(ymin+Math.random()*(ymax-ymin));
		e.setX(3*xdim*Math.random()-xdim);
		e.setDrift(driftmin + Math.random()*(driftmax-driftmin), 0);
		e.setCollide(collide);
		return e;
	}

	public void addBackgroundEntity(String name) {
		Entity e = new Entity(images.get(name), 0, xdim, ydim);
		e.setMiddle(false);
		entities.add(e);
	}

	public void addRepeatableEntity(String name, double z, int xdim, int ydim) {
			Entity e = new Entity(images.get(name), z, xdim, ydim);
			e.setY(0);
			e.setRepeatable(true);
			entities.add(e);

	}
	public void updateEntities() {
		for (Entity e : entities) {
			e.advance(frame);
			e.drift(m.getX(), xdim);
		}
	}

	public void playerState() {
		int move = m.getMove();
		int[] states = {5, 1, 3, 2, 0, 4};
		int state = states[move+3];
		player.changeState(state);
	}

	public void moveCamera() {
		double vx = m.getVx();
		if (vx < -5) {
			cameraDirection = 1;
		}
		if (vx > 5) {
			cameraDirection = -1;
		}
		cameraOffset += cameraDirection*1000*dt;
		if (cameraOffset > xdim/3) {
			cameraOffset = xdim/3;
		}
		if (cameraOffset < -xdim/3-playerx) {
			cameraOffset = -xdim/3-playerx;
		}
	}


	public void sendCollides() {
		ArrayList<Entity> temp = new ArrayList<>();
		for (Entity e : entities) {
			if (e.getCollide()) {
				temp.add(e);
			}
		}
		m.sendEntities(temp);
	}



	public void actionPerformed(ActionEvent e) {
		long t0 = System.nanoTime();
		if (lastTime > 0) {
			dt = (t0-lastTime)/1e9;
			m.setDT(dt);
		}
		lastTime = t0;
		v.action();
		v.sendKeys();
		m.move();
		moveCamera();
		playerState();
		player.setX(m.getX());
		player.setY(m.getY());
		frame++;
		updateEntities();
		v.setCamera(cameraOffset);
		v.repaint();
	}


	public static void main(String[] args) {
		int x = 500;
		int y = 500;
		try {
			x = Integer.parseInt(args[0]);
			y = Integer.parseInt(args[1]);
		} catch (Exception e) {

		}
		new Controller(x, y);
	}
}