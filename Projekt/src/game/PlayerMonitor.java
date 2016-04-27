package game;

public class PlayerMonitor {
	private boolean leftPressed;
	private boolean rightPressed;
	
	public PlayerMonitor() {
		leftPressed = false;
		rightPressed = false;
	}

	public synchronized void setLeftPressed(boolean pressed) {
		leftPressed = pressed;
	}
	
	public synchronized void setRightPressed(boolean pressed) {
		rightPressed = pressed;
	}
	
	public synchronized boolean rightPressed() {
		return rightPressed;
	}
	
	public synchronized boolean leftPressed() {
		return leftPressed;
	}

}
