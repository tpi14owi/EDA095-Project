package main.java.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClientInputThread extends Thread {
	private ClientMonitor monitor;
	private BufferedReader br;

	public ClientInputThread(ClientMonitor monitor, InputStream is) {
		this.monitor = monitor;
		br = new BufferedReader(new InputStreamReader(is));
	}

	public void run() {
		System.out.println("Hej, jag är en InputThread! Hej då!");
		while (true) {
			try {
				monitor.putInput(br.readLine());
			} catch (IOException e) {
				System.out.println("Couldn't read data from ServerOutput");
				e.printStackTrace();
			}
		}
	}

}
