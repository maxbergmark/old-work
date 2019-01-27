

public class ChatTimer implements Runnable { //Timerklass som används vid keyrequest och filerequest.
	boolean windowOpen = false;
	public ChatTimer() {

	}

	public void run() { //Väntar 60 sekunder och byter sedan värde på boolean windowOpen.
		if (!windowOpen) {
			try {
				windowOpen = true;
				Thread.sleep(60*1000);
			} catch (Exception e) {}
			windowOpen = false;
		}
	}

	public void resetTimer() { //Återställer timern.
		windowOpen = false;
	}

	public boolean checkTimer() { //Metod som vi kan anropa för att kolla om det gått 60 sekunder sedan timern startades. 
		return windowOpen;
	}
}