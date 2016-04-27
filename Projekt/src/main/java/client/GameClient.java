package main.java.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import game.SnueMain;

public class GameClient {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println("Usage: java -jar GameClient host port");
			System.exit(1);
		}
		ClientMonitor m = new ClientMonitor();

		int port = Integer.parseInt(args[1]);
		String host = args[0];

		// Change "snue" in SnueMain constructor to be fetched from args!??
		SnueMain app = new SnueMain(m, "snue");

		Socket socket = null;
		try {
			socket = new Socket(host, port);
		} catch (Exception e) {
			System.out.println("Couldn't connect.");
		}

		OutputStream os = null;
		InputStream is = null;

		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		(new ClientInputThread(m, is)).start();
		(new ClientOutputThread(m, os)).start();
		(new ClientUpdaterThread(m)).start();
	}
}
