import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.event.*;
import java.io.*;


public class Controller {
	private Tetris tetrisGame;
	private PlayTetris5 tetrisBot;
	private MakeSound tetrisSound;
	private View tetrisView;
	private Thread viewThread;
	private Thread soundThread;
	private int[] direction = {0, 1};
	private boolean firstDrop = true;
	private int gameSlider = 1;
	private int frameTime = 10;
	private volatile long gameSpeed = (long) (Math.pow(10, (double) gameSlider/10.0)-1);
	private long totTime = 0;
	private long totDecisionTime = 0;
	private long loopTime = 0;
	private long gamescore = 0;
	private int i = 0;
	private JSlider[] sliders = new JSlider[2];

	private boolean enableView = false;

	public Controller(int seed) {
		setupGame(seed);
		playTetris();
		exitGame();

	}

	private void setupGame(int seed) {
		tetrisGame = new Tetris(seed);
		tetrisBot = new PlayTetris5(tetrisGame);

		if (enableView) {
			tetrisView = new View(tetrisGame, frameTime);
			addSliders();
			viewThread = new Thread(tetrisView);
			viewThread.start();
		}
		tetrisSound = new MakeSound("game1.wav");
		// soundThread = new Thread(tetrisSound);
		// soundThread.start();		
	}

	private void playTetris() {
		while (!tetrisGame.gameLost) {
			loopTime = System.nanoTime();
			while (loopTime+gameSpeed > System.nanoTime()) {}
			updateGame();
		}		
	}

	private void exitGame() {
		//tetrisBot.stopThreads();
		//tetrisSound.terminate();
		if (enableView) {
			tetrisView.terminate();
			try {
				viewThread.interrupt();
				//soundThread.join();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		gamescore = tetrisGame.getScore();

		tetrisGame = null;
		tetrisBot = null;
		tetrisView = null;
		viewThread = null;
		sliders = null;

	}

	public synchronized void setDelay(int value) {
		gameSpeed = (long) (Math.pow(10, (double) value/10.0)-1);
		//gameSpeed = (1L<<value/3)/16-1L;
		//System.out.println(gameSpeed);
		
	}

	public void updateGame() {
		boolean temp = tetrisGame.movePiece(direction);
		if (!temp || firstDrop) {
			firstDrop = false;
			//tetrisGame.setGrid(tetrisGame.checkRows(tetrisGame.deepCopy(tetrisGame.getGrid())));
			tetrisGame.updateRows();
			tetrisGame.spawnPiece(0);
			long startTime = System.nanoTime();
			tetrisBot.makeChoice();
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
		long totScore = 0;
		int iterations = 0;
		for (int i = 1; i < 21; i++) {
			Controller hej = new Controller(i);//Integer.parseInt(args[0]));
			totScore += hej.gamescore;
			System.out.println("Average: " + (double)(totScore)/(double)(++iterations));
		}
		
	}
}
