package client;

import java.io.BufferedReader;
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
	}
	
}
