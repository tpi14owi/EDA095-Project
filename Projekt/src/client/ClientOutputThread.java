package client;

public class ClientOutputThread extends Thread {
	private ClientMonitor monitor;
	
	public ClientOutputThread(ClientMonitor monitor) {
		this.monitor = monitor;
	}
	
	public void run() {
		System.out.println("Hej, jag är en ClientOutputThread. Hejdå!");
	}

}
