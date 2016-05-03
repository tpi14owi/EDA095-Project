package main.java.game;

public class ActionWrapper {
	private int x;
	private int y;
	private String id;
	private int command;
	
	public ActionWrapper(String id, int command, int x, int y) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.command = command;
	}	
	
	public String getId() {
		return id;
	}

	public int getCommand() {
		return command;
	}

	public int getX() {		
		return x;
	}
	
	public int getY() {
		return y;
	}
}
