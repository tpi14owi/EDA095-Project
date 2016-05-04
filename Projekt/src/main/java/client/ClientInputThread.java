package main.java.client;

import java.lang.ClassNotFoundException;
import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import main.java.common.MessageHandler;
import main.java.common.GameState;

public class ClientInputThread extends Thread {
	private ClientMonitor monitor;
	private BufferedReader br;
	private ObjectInputStream is;
	private Socket s;

	public ClientInputThread(Socket s, ClientMonitor monitor) {
		this.monitor = monitor;
		this.s = s;
	}


	public void run() {
		try {
			is = new ObjectInputStream(s.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (s.isConnected()) {
			try {
				monitor.setState((GameState) is.readObject());



				/*

				is.readLong();	// Throw away time stamp
				String playerid = MessageHandler.readString(is);
				int command = is.readInt();
				int x = is.readInt();
				int y = is.readInt();
				//System.out.println("ClientInput: " + playerid + " Cmd: " + command);

				monitor.putWork(playerid, command, x, y);
				*/
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException ex) {

			}
		}
	}
}
