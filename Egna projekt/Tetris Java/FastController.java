import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.event.*;
import java.io.*;


public class FastController {
	private FastTetris tetrisGame;
	private PlayTetris5 tetrisBot;
	private MakeSound tetrisSound;
	private FastView tetrisView;  
	private Thread viewThread;
	private Thread soundThread;
	private Timer test;
	private int[] direction = {0, 1};
	private boolean firstDrop = true;
	private int gameSlider = 95;
	private int frameTime = 10;
	private volatile long gameSpeed = (long) (Math.pow(10, (double) gameSlider/10.0)-1);
	private long totTime = 0;
	private long totDecisionTime = 0;
	private long loopTime = 0;
	private int i = 0;
	private JSlider[] sliders = new JSlider[2];

	public FastController() {
		tetrisGame = new FastTetris();
		//tetrisBot = new PlayTetris5(tetrisGame);
		
		tetrisView = new FastView(tetrisGame, frameTime);
		addSliders();
		viewThread = new Thread(tetrisView);
		viewThread.start();
		
		
		tetrisSound = new MakeSound("game1.wav");
		soundThread = new Thread(tetrisSound);
		soundThread.start();
		
		while (!tetrisGame.gameLost) {
			//try {
			//	Thread.sleep(gameSpeed);
			//} catch (Exception e) {}
			loopTime = System.nanoTime();
			while (loopTime+gameSpeed > System.nanoTime()) {}
			//long testTime1 = System.nanoTime();
			updateGame();
			//long testTime2 = System.nanoTime();
			//totTime += testTime2-testTime1;
			//System.out.println((double)(totDecisionTime)/(double)(totTime));
		}

		tetrisSound.terminate();
		tetrisView.terminate();
		try {
			viewThread.join();
			soundThread.join();
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		
	}



	public synchronized void setDelay(int value) {
		gameSpeed = (long) (Math.pow(10, (double) value/10.0)-1);
		//gameSpeed = (1L<<value/3)/16-1L;
		//System.out.println(gameSpeed);
		
	}

	public void updateGame() {
		System.out.println("new frame");
		tetrisGame.moveMainDown(1);
		boolean temp = true;
		if (!temp || firstDrop) {
			firstDrop = false;
			//tetrisGame.setGrid(tetrisGame.checkRows(tetrisGame.deepCopy(tetrisGame.getGrid())));
			//tetrisGame.updateRows();
			tetrisGame.spawnPiece();
			long startTime = System.nanoTime();
			//tetrisBot.makeChoice();
			long endTime = System.nanoTime();
			totDecisionTime += endTime-startTime;
			//tetrisGame.clearGrid();
			//System.out.println((totTime += (endTime-startTime))/(++i));
		}
	}

	public void addSliders() {
		for (int i = 0; i < 2; i++) {
			sliders[i] = new JSlider(JSlider.HORIZONTAL, 0, 100, ((i==0) ? gameSlider : frameTime));
			sliders[i].addChangeListener(new SliderClass());
			sliders[i].setMajorTickSpacing(10);
	        sliders[i].setMinorTickSpacing(1);
	        sliders[i].setPaintTicks(false);
	        sliders[i].setPaintLabels(false);
	        sliders[i].setFocusable(false);
	    	tetrisView.addSlider(sliders[i]);
	    }
	}

	class SliderClass implements ChangeListener {

		public void stateChanged(ChangeEvent e){
			JSlider source = (JSlider)e.getSource();
			if(!source.getValueIsAdjusting() || true){
				if (source == sliders[0]) {
					setDelay(source.getValue());
				}
				else {
					tetrisView.setDelay(source.getValue());
				}
			}
		}
	}

	public static void main(String[] args) {
		new FastController();
	}
}