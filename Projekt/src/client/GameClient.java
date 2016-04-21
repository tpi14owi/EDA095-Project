package client;

public class GameClient {	
	
	
	
	public static void main(String[] args) {
		ClientMonitor monitor = new ClientMonitor();
		(new ClientOutputThread(monitor)).start();
		(new ClientInputThread(monitor)).start();
		(new ClientUpdaterThread(monitor)).start();
		
		
	}

}
