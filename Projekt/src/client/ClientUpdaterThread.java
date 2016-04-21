package client;

public class ClientUpdaterThread extends Thread {
	private ClientMonitor monitor;

	public ClientUpdaterThread(ClientMonitor monitor) {
		this.monitor = monitor;
	}

	public void run() {
		System.out.println("Hej, jag är en UpdaterThread! Hej då!");
		while (true) {
			// Fetch work from monitor and send to game
		}
	}

}
