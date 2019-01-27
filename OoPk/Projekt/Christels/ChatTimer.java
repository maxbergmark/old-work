

public class ChatTimer implements Runnable {
	boolean windowOpen = false;
	public ChatTimer() {

	}

	public void run() {
		if (!windowOpen) {
			System.out.println("starting timer");
			try {
				windowOpen = true;
				Thread.sleep(60*1000);
			} catch (Exception e) {}
			windowOpen = false;
		}
	}

	public void resetTimer() {
		windowOpen = false;
	}

	public boolean checkTimer() {
		return windowOpen;
	}
}