package main.java.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import main.java.common.MessageHandler;

public class ServerOutputThread extends Thread {
	private Socket s;
	private ServerMonitor sm;

	public ServerOutputThread(Socket s, ServerMonitor sm) {
		this.s = s;
		this.sm = sm;
	}

	public void run() {
		DataOutputStream os = null;
		try {
			os = new DataOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		while (s.isConnected()) {
			try {
				ArrayList<MessageHandler> messages = sm.getMessages(s);
				for (MessageHandler ms : messages) {
					ms.send(os);
				}
				sleep(200);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
