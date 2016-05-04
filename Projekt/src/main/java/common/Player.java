package main.java.common;

import java.io.Serializable;

public class Player implements Serializable {
	private int x;
	private int y;
	private String name;
	private int command;

	public Player(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public void setCommand(int command) {
		this.command = command;
	}

	public int getCommand() {
		return command;
	}

	public String getName() {
		return name;
	}
}
