package game;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;

import client.ClientMonitor;

public class PlayerControl extends AbstractControl {

	private int screenWidth, screenHeight;

	// is the player currently moving?
	public boolean up, left, right;
	// speed of the player
	private float speed = 400f;

	private long timer;
	private Node player;
	private Picture pic;
	private AssetManager assetManager;
	private int lastMovement;
	private SnueMain sm;
    private ClientMonitor m;

	public PlayerControl(int width, int height, SnueMain sm, ClientMonitor m) {
		this.screenWidth = width;
		this.screenHeight = height;
		timer = 0;
		this.sm = sm;
        this.m = m;
		lastMovement = 0;
	}

	private void changePicture(String name) {
		sm.updateSpatial(name);
	}

	@Override
	protected void controlUpdate(float tpf) {
		if (left) {
			if (spatial.getLocalTranslation().x > (Float) spatial.getUserData("radius")) {
				spatial.move(tpf * -speed, 0, 0);
				m.moveLeft();
			}
			switch (lastMovement) {
			case 0:
				if (System.currentTimeMillis() - timer > 100) {
					changePicture("snue_left_step1");
					lastMovement = 1;
					timer = System.currentTimeMillis();
				}
				break;
			case 1:
				if (System.currentTimeMillis() - timer > 100) {
					changePicture("snue_left");
					lastMovement = 2;
					timer = System.currentTimeMillis();
				}
				break;
			case 2:
				if (System.currentTimeMillis() - timer > 100) {
					changePicture("snue_left_step2");
					lastMovement = 3;
					timer = System.currentTimeMillis();
				}
				break;
			case 3:
				if (System.currentTimeMillis() - timer > 100) {
					changePicture("snue_left");
					lastMovement = 0;
					timer = System.currentTimeMillis();
				}
				break;
			}

			// spatial.rotate(0,0, -lastRotation + FastMath.PI);
			// lastRotation=FastMath.PI;
		} else if (right) {
			if (spatial.getLocalTranslation().x < screenWidth - (Float) spatial.getUserData("radius")) {
				spatial.move(tpf * speed, 0, 0);
                m.moveRight();
			}
			switch (lastMovement) {
			case 0:
				if (System.currentTimeMillis() - timer > 100) {
					changePicture("snue_right_step1");
					lastMovement = 1;
					timer = System.currentTimeMillis();
				}
				break;
			case 1:
				if (System.currentTimeMillis() - timer > 100) {
					changePicture("snue_right");
					lastMovement = 2;
					timer = System.currentTimeMillis();
				}
				break;
			case 2:
				if (System.currentTimeMillis() - timer > 100) {
					changePicture("snue_right_step2");
					lastMovement = 3;
					timer = System.currentTimeMillis();
				}
				break;
			case 3:
				if (System.currentTimeMillis() - timer > 100) {
					changePicture("snue_right");
					lastMovement = 0;
					timer = System.currentTimeMillis();
				}
				break;
			}
		}
	}

	@Override
	protected void controlRender(RenderManager rm, ViewPort vp) {
	}

	// reset the moving values (i.e. for spawning)
	public void reset() {
		up = false;
		left = false;
		right = false;
	}

	public void setPic(Picture pic) {
		this.pic = pic;

	}
}