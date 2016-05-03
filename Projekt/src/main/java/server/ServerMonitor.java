package main.java.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import main.java.common.MessageHandler;

public class ServerMonitor {
	private static final int MAX_CONNECTIONS = 4;
	private ArrayList<Socket> connections;
	private HashMap<Socket, MessageHandler> messages;

	public ServerMonitor() {
		connections = new ArrayList<Socket>();
		messages = new HashMap<Socket, MessageHandler>();
	}

	public synchronized void addConnection(Socket s) {
		connections.add(s);
	}

	public synchronized boolean newConnectionPossible() {
		return connections.size() < MAX_CONNECTIONS;
	}

	public synchronized void removeConnection(Socket s) {
		connections.remove(s);
	}
	
	public synchronized void addMessage(Socket s, MessageHandler mh) {
		messages.put(s, mh);
		notifyAll();
	}

	public synchronized boolean hasConnections() {
		return connections.size() > 0;
	}

	public synchronized ArrayList<MessageHandler> getMessages(Socket s) {
		ArrayList<MessageHandler> ret = new ArrayList<MessageHandler>();
		
		while (messages.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (Socket socket : connections) {
			if (socket != s) {
				ret.add(messages.get(socket));
			}
		}
		return ret;
	}

}
