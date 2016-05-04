package main.java.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import main.java.game.SnueMain;

public class GameClient {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println("Usage: java -jar GameClient host port name");
			System.exit(1);
		}
		String name = args[2];
		ClientMonitor m = new ClientMonitor(name);
		int port = Integer.parseInt(args[1]);
		String host = args[0];

		// Change "snue" in SnueMain constructor to be fetched from args!??
		SnueMain app = new SnueMain(m, "snue");
		m.setAssetManager(app);

		Socket socket = null;
		try {
			socket = new Socket(host, port);
		} catch (Exception e) {
			System.out.println("Couldn't connect.");
			e.printStackTrace();
		}

		OutputStream os = null;

		try {
			os = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		(new ClientInputThread(socket, m)).start();
		(new ClientOutputThread(socket, m, name)).start();
		(new ClientUpdaterThread(m)).start();

		(new TestingThread(m)).start();
	}
}
