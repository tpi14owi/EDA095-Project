package main.java.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import main.java.common.MessageHandler;

public class ServerInputThread extends Thread {
	private Socket s;
	private ServerMonitor sm;
	private DataInputStream is;

	public ServerInputThread(Socket s, ServerMonitor sm) {
		this.s = s;
		this.sm = sm;
	}

	public void run() {
		try {
			is = new DataInputStream(s.getInputStream());
		} catch (IOException e1) {
			//e1.printStackTrace();
		}
		while (s.isConnected()) {
			try {
				long timestamp = is.readLong();
				String playerid = MessageHandler.readString(is);
				int command = is.readInt();
				int xcord = is.readInt();
				int ycord = is.readInt();
				MessageHandler mh = new MessageHandler(timestamp, playerid, command, xcord, ycord);
				sm.addMessage(s, mh);
				//System.out.println("ServerInput: " + mh.toString());
			} catch (IOException e) {
				//e.printStackTrace();
			}

		}
		sm.removeConnection(s);
	}
}
