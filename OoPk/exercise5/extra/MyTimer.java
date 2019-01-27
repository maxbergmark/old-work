import java.awt.event.*;
import java.util.*;

public class MyTimer implements Runnable {

	private List<ActionListener> listeners;
	private int delay;
	private int initDelay;
	private Thread thr = null;

	public void run() {
		try {
			Thread.sleep(initDelay);
			while (true) {
				for (ActionListener a : listeners) {
					a.actionPerformed(new ActionEvent(this,
		        		ActionEvent.ACTION_FIRST, "Tick!"));
				}
				Thread.sleep(delay);
			}
		} catch (InterruptedException e) {
			System.out.println("Väcktes!");
		} catch (Exception e) {

		}
	}

	public MyTimer(int delay, ActionListener listener) {
		listeners = new ArrayList<>();
		addActionListener(listener);
		this.delay = delay;
		this.initDelay = 0;
	}

	public void addActionListener(ActionListener a) {
		listeners.add(a);
	}

	public void start() {
		if (thr == null) {
			thr = new Thread(this);
			thr.start();
		}
	}

	public void setInitialDelay(int ms) {
		this.initDelay = ms;
	}


	public void setDelay(int delay) {
		this.delay = delay;
	}


}