package client;

public class ClientInputThread extends Thread {
	private ClientMonitor monitor;

	public ClientInputThread(ClientMonitor monitor) {
		this.monitor = monitor;
	}

}