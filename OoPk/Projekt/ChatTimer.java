

public class ChatTimer implements Runnable { //Timerklass som anv�nds vid keyrequest och filerequest.
	boolean windowOpen = false;
	public ChatTimer() {

	}

	public void run() { //V�ntar 60 sekunder och byter sedan v�rde p� boolean windowOpen.
		if (!windowOpen) {
			try {
				windowOpen = true;
				Thread.sleep(60*1000);
			} catch (Exception e) {}
			windowOpen = false;
		}
	}

	public void resetTimer() { //�terst�ller timern.
		windowOpen = false;
	}

	public boolean checkTimer() { //Metod som vi kan anropa f�r att kolla om det g�tt 60 sekunder sedan timern startades. 
		return windowOpen;
	}
}