package main.java.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import main.java.common.MessageHandler;

public class ClientInputThread extends Thread {
	private ClientMonitor monitor;
	private BufferedReader br;
	private DataInputStream is;
	private Socket s;

	public ClientInputThread(Socket s, ClientMonitor monitor) {
		this.monitor = monitor;
		this.s = s;
	}


	public void run() {
		try {
			is = new DataInputStream(s.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (s.isConnected()) {
			try {
				is.readLong();	// Throw away time stamp
				String playerid = MessageHandler.readString(is);
				int command = is.readInt();
				int x = is.readInt();
				int y = is.readInt();
//				System.out.println("ClientInput: " + playerid + " Cmd: " + command);
				monitor.putWork(playerid, command, x, y);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
