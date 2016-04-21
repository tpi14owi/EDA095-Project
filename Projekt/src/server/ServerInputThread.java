package server;

import java.net.Socket;

public class ServerInputThread extends Thread {
	private Socket s;
	private ServerMonitor sm;

	public ServerInputThread(Socket s, ServerMonitor sm) {
		this.s = s;
		this.sm = sm;
	}

	public void run() {
		while (s.isConnected()) {
		}
		sm.removeConnection(s);
	}
	

	// Dö

}
