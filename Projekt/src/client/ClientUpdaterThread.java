package client;

public class ClientUpdaterThread extends Thread {
	private ClientMonitor monitor;

	public ClientUpdaterThread(ClientMonitor monitor) {
		this.monitor = monitor;
	}

}