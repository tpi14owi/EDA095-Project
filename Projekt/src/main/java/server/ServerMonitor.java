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

	/**
	 * Adds socket connection to the server monitor. All messages will prpagate to this added socket.
	 * @param s
	 */
	public synchronized void addConnection(Socket s) {
		connections.add(s);
	}

	/**
	 * No more than MAX_CONNECTIONS allowed.
	 */
	public synchronized boolean newConnectionPossible() {
		return connections.size() < MAX_CONNECTIONS;
	}

	public synchronized void removeConnection(Socket s) {
		connections.remove(s);
	}

	/**
	 * Adds message to monitor.
	 * @param s Socket which the messages is affiliated with 
	 * @param mh message
	 */
	public synchronized void addMessage(Socket s, MessageHandler mh) {
		messages.put(s, mh);
		notifyAll();
	}

	/**
	 * Returns messages that a specific socket should send out. Messages that comes from s will not be included.
	 * @param s
	 * @return
	 */
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
