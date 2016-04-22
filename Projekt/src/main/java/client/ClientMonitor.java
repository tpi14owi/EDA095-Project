package main.java.client;

public class ClientMonitor {
	
	
	
	public ClientMonitor() {
		
	}

	/**
	 * Lets the OutputThread fetch work from output correalted worklist to send towards the server
	 * @param readLine
	 */
	synchronized public char[] getOutput() {
		char[] ret = {'H', 'e', 'l','l','o'};
		return ret;
	}
	
	/**
	 * Lets the UpdaterThread put work into the output-worklist for the OutputThread to send to server.
	 * @param readLine
	 */
	synchronized public void putOutput(String readLine) {
		// TODO Auto-generated method stub		
	}
	
	/**
	 * Lets the Game fetch work from input correalted worklist to propagate to the game
	 * @param readLine
	 */
	synchronized public char[] getInput() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Lets the InputThread put work into the worklist for the updater to propagate to the game/GUI
	 * @param readLine
	 */
	synchronized public void putInput(String readLine) {
		// TODO Auto-generated method stub		
	}
	
	
	
}
