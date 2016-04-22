package main.java.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClient {

	public static void main(String[] args) {
		ClientMonitor m = new ClientMonitor();

		int port = Integer.parseInt("1337");
		String host = "lo-10";

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
