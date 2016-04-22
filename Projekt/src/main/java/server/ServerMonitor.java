package main.java.server;

import java.net.Socket;
import java.util.ArrayList;

public class ServerMonitor {
	private ArrayList<Socket> connections;

	public ServerMonitor() {
		connections = new ArrayList<Socket>();
	}

	public synchronized void addConnection(Socket s) {
		connections.add(s);
	}

	public synchronized boolean newConnectionPossible() {
		return connections.size() < 4;
	}

	public synchronized void removeConnection(Socket s) {
		connections.remove(s);

	}

}
