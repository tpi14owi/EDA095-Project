package main.java.game;

import com.jme3.scene.Node;
import com.jme3.ui.Picture;

public class PlayerWrapper {
	private Node node;
	private String id;
	private Picture pic;

	public PlayerWrapper(String id, Node node, Picture pic) {
		this.id = id;
		this.node = node;
		this.pic = pic;
	}

	public Picture getPic() {
		return pic;
	}
	
	public void setPic(Picture pic) {
		this.pic = pic;
	}
	
	public String getId() {
		return id;
	}

	public Node getNode() {
		return node;
	}

}
