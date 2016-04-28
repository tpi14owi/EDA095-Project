package main.java.game;

import java.util.ArrayList;

import com.jme3.app.SimpleApplication;
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

	public SnueMain(ClientMonitor m, String name) {
		textures = new ArrayList<String>();
		textures.add(0, name + "_right.png");
		textures.add(1, name + "_right_step1.png");
		textures.add(2, name + "_right_step2.png");
		textures.add(3, name + "_left.png");
		textures.add(4, name + "_left_step1.png");
		textures.add(5, name + "_left_step2.png");
		this.start();
		this.m = m;

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
		inputManager.addMapping("mousePick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addListener(this, "mousePick");

		inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_LEFT));
		inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_RIGHT));

		inputManager.addListener(this, "left");
		inputManager.addListener(this, "right");

		pc = new PlayerControl(settings.getWidth(), settings.getHeight(), this, m);
		player.addControl(pc);
	}

	@Override
	public void simpleUpdate(float tpf) {

	}

	@Override
	public void simpleRender(RenderManager rm) {

	}

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

	private Node getSpatial(String name) {
		Node node = new Node(name);
		// load picture
		pic = new Picture(name);
//		pc.setPic(pic);
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

	@Override
	/**
	 * PROBLEMET: Räknar bara ett knapptryck, samt när detta släpps. Behöver
	 * kontinuerlig kontroll på nedtryckt knapp
	 */
	public void onAction(String name, boolean isPressed, float tpf) {
		if ((Boolean) player.getUserData("alive")) {
			if (name.equals("left")) {
				player.getControl(PlayerControl.class).left = isPressed;
			} else if (name.equals("right")) {
				player.getControl(PlayerControl.class).right = isPressed;
			}
		}
	}
}
