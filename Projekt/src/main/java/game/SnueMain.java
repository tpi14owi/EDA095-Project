package main.java.game;

import java.util.ArrayList;
import java.util.LinkedList;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;

import main.java.client.ClientMonitor;

public class SnueMain extends SimpleApplication implements ActionListener {
	private long bulletCooldown;
	public Node player;
	private Node bulletNode;
	private ClientMonitor m;
	private PlayerControl pc;
	private ArrayList<String> textures;
	private Picture pic;
	private ArrayList<PlayerWrapper> players;




	public SnueMain(ClientMonitor m, String name) {
		textures = new ArrayList<String>();
		textures.add(0, name + "_right.png");
		textures.add(1, name + "_right_step1.png");
		textures.add(2, name + "_right_step2.png");
		textures.add(3, name + "_left.png");
		textures.add(4, name + "_left_step1.png");
		textures.add(5, name + "_left_step2.png");
		players = new ArrayList<PlayerWrapper>();
		this.start();
		this.m = m;
	}

	/**
	 * Uppdaterar GUI för Client kontinuerligt.
	 * Hämtar arbete ifrån ClientMonitor för att få en konsekvent
	 * bild av andra spelare.
	 * @param tpf
	 */
	@Override
	public void simpleUpdate(float tpf) {
		ActionWrapper pw = m.getWork();
		if (pw != null) {
			int command = pw.getCommand();
			String name = pw.getId();
			int x = pw.getX();
			int y = pw.getY();
			switch (command) {
			case 0:
				createPlayer(name);
				break;
			case 1:
				updatePlayer(name, x, y);
				break;
			}
		}
	}


	@Override
	public void simpleInitApp() {
		// setup camera for 2D games
		cam.setParallelProjection(true);
		cam.setLocation(new Vector3f(0, 0, 0.5f));
		getFlyByCamera().setEnabled(false);

		// turn off stats view (you can leave it on, if you want)
		setDisplayStatView(false);
		setDisplayFps(false);

		// setup the player
		player = getSpatial("snue_right");
		player.setUserData("alive", true);
		player.move(settings.getWidth() / 2, settings.getHeight() / 2, 0);
		guiNode.attachChild(player);

		// setup input handling
		setUpKeys();

		pc = new PlayerControl(settings.getWidth(), settings.getHeight(), this, m);
		player.addControl(pc);
	}

	/**
	 * Uppdaterar en spelare men motion i, ser till att
	 * spelaren animeras.
	 * @param i
	 */
	public void updateSpatial(int i) {
		player.detachChild(pic);
		pic = new Picture(textures.get(i));
		Texture2D tex = (Texture2D) assetManager.loadTexture(textures.get(i));
		pic.setTexture(assetManager, tex, true);
		// adjust picture
		float width = tex.getImage().getWidth();
		float height = tex.getImage().getHeight();
		pic.setWidth(width);
		pic.setHeight(height);
		pic.move(-width / 2f, -height / 2f, 0);
		// add a material to the picture
		Material picMat = new Material(assetManager, "Common/MatDefs/Gui/Gui.j3md");
		picMat.getAdditionalRenderState().setBlendMode(BlendMode.AlphaAdditive);
		player.setMaterial(picMat);
		player.attachChild(pic);
	}

	/**
	 * Skapar en motståndare som anslutat sig till spelet
	 * @param id
	 */
	private void createPlayer(String id) {
		Node tmp = getSpatial(id);
		tmp.setUserData("alive", true);
		tmp.move(settings.getWidth() / 4, settings.getHeight() / 2, 0);
		guiNode.attachChild(tmp);
		players.add(new PlayerWrapper(id, tmp));
	}

	/**
	 * Skapar en spatial som kopplas till GUIet.
	 * @param name
	 * @return
	 */
	private Node getSpatial(String name) {
		Node node = new Node(name);
		// load picture
		pic = new Picture(name);
		// pc.setPic(pic);
		Texture2D tex = (Texture2D) assetManager.loadTexture(textures.get(0));
		pic.setTexture(assetManager, tex, true);
		// adjust picture
		float width = tex.getImage().getWidth();
		float height = tex.getImage().getHeight();
		pic.setWidth(width);
		pic.setHeight(height);
		pic.move(-width / 2f, -height / 2f, 0);
		// add a material to the picture
		Material picMat = new Material(assetManager, "Common/MatDefs/Gui/Gui.j3md");
		picMat.getAdditionalRenderState().setBlendMode(BlendMode.AlphaAdditive);
		node.setMaterial(picMat);
		// set the radius of the spatial
		// (using width only as a simple approximation)
		node.setUserData("radius", width / 2);
		// attach the picture to the node and return it
		node.attachChild(pic);
		return node;
	}

	/**
	 * Actionlistener för clienten.
	 * @param name
	 * @param isPressed
	 * @param tpf
	 */
	@Override
	public void onAction(String name, boolean isPressed, float tpf) {
		if ((Boolean) player.getUserData("alive")) {
			if (name.equals("left")) {
				player.getControl(PlayerControl.class).left = isPressed;
			} else if (name.equals("right")) {
				player.getControl(PlayerControl.class).right = isPressed;
			}
		}
	}


	private void setUpKeys() {
		inputManager.addMapping("mousePick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addListener(this, "mousePick");

		inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_LEFT));
		inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_RIGHT));

		inputManager.addListener(this, "left");
		inputManager.addListener(this, "right");
	}

	/**
	 * Hjälpmetod så GUIet kan uppdatera en motståndare som rört sig.
	 * @param id
	 * @param x
	 * @param y
	 */
	private void updatePlayer(String id, int x, int y) {
		Node tmp = null;
		System.out.println("Trying to move: " + id);
		for (PlayerWrapper p : players) {
			if (p.getId() == id) {
				tmp = p.getNode();
				break;
			}
		}
		if (tmp != null)
			tmp.move(20, 0, 0);
	}

}
