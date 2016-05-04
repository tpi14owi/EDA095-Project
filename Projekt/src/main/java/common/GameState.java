package main.java.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;


public class GameState implements Serializable {
	private ArrayList<Player> players;

	public GameState() {
		players = new ArrayList<Player>();
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setState(String id, int x, int y) {
		for (Player p : players) {
			if (p.getName().equals(id)) {
				p.setX(x);
				p.setY(y);
				return;
			}
		}
		players.add(new Player(id, x, y));
	}
}



