package main.java.server;

import java.net.Socket;

public class ServerOutputThread extends Thread {
	private Socket s;
	private ServerMonitor sm;

	public ServerOutputThread(Socket s, ServerMonitor sm) {
		this.s = s;
		this.sm = sm;
	}

	public void run() {
		while (s.isConnected()) {
		}

	}

}
