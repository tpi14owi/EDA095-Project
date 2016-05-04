package main.java.client;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import main.java.common.MessageHandler;
import main.java.game.ActionWrapper;

public class ClientOutputThread extends Thread {
	private ClientMonitor monitor;
	private BufferedWriter bw;
	private Socket s;
	private DataOutputStream os;
	private String name;

	public ClientOutputThread(Socket s, ClientMonitor monitor, String name) {
		this.monitor = monitor;
		this.s = s;
		this.name = name;
	}

	public void run() {
		try {
			os = new DataOutputStream(s.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (s.isConnected()) {
			ActionWrapper aw = monitor.getOutput();
			int command = aw.getCommand();
			int x = aw.getX();
			int y = aw.getY();
			long timestamp = System.currentTimeMillis();
			MessageHandler mh = new MessageHandler(timestamp, aw.getId(), command, x, y);
			mh.send(os);
		}
	}
}
