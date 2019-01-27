import java.util.*;
import java.awt.event.*;

public class MyTimer implements Runnable {
	
	volatile int delay;
	int initDelay;
	List<ActionListener> listeners;

	Thread thr = null;

	public void run() {
		try {
			Thread.sleep(initDelay);
			while (true) {
				for (ActionListener a : listeners) {
					a.actionPerformed(new ActionEvent(
						this, ActionEvent.ACTION_FIRST, "Tick!"));
				}
				Thread.sleep(delay);
			}
		} catch (InterruptedException e) {
			System.out.println("Interrpted");
			return;
		}
	}

	public MyTimer(int delay, ActionListener a) {
		this.delay = delay;
		listeners = new ArrayList<>();
		addActionListener(a);
	}

	public void addActionListener(ActionListener a) {
		listeners.add(a);
	}

	public void setInitialDelay(int ms) {
		this.initDelay = ms;
	}

	public void setDelay(int ms) {
		this.delay = ms;
	}

	public void start() {
		if (thr == null) {
			thr = new Thread(this);
			thr.start();
		}
	}
}