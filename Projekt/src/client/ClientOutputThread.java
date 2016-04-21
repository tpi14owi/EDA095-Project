package client;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ClientOutputThread extends Thread {
	private ClientMonitor monitor;
	private BufferedWriter bw;
	
	public ClientOutputThread(ClientMonitor monitor, OutputStream os) {
		this.monitor = monitor;
		bw = new BufferedWriter(new OutputStreamWriter(os));
	}

	public void run() {
		System.out.println("Hej, jag är en OutputThread! Hej då!");
	}
	
}
