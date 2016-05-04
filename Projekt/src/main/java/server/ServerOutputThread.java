package main.java.server;

import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import main.java.common.MessageHandler;
import main.java.common.GameState;


public class ServerOutputThread extends Thread {
	private Socket s;
	private ServerMonitor sm;

	public ServerOutputThread(Socket s, ServerMonitor sm) {
		this.s = s;
		this.sm = sm;
	}

	public void run() {
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {
			//e.printStackTrace();
		}

		while (s.isConnected()) {
			try {
				os.writeObject(sm.getState());
				/*
				ArrayList<MessageHandler> messages = sm.getMessages(s);
				for (MessageHandler ms : messages) {
				//	System.out.println("Sending: " + ms.toString());
					ms.send(os);
				}
				*/
				sleep(20);
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}

	}

}
