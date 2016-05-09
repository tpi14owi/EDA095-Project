package main.java.game;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

import main.java.client.ClientMonitor;

public class PlayerControl extends AbstractControl {
	private int screenWidth, screenHeight;
	// is the player currently moving?
	public boolean space, left, right, lcontrol;
	// speed of the player
	private float speed = 400f;
	private long timer;
	private int lastMovement;
	private SnueMain sm;
	private boolean lastMoveWasRight, isJumping, goingDown;
    private ClientMonitor m;
    private int ground;
    private float speedFactor;
    private static float GRAVITY = (float) 6E-4;
    private float currentJSpeed;


	public PlayerControl(int width, int height, SnueMain sm, ClientMonitor m) {
		this.screenWidth = width;
		this.screenHeight = height;
		timer = 0;
		this.sm = sm;
        this.m = m;
		lastMovement = 0;
		lastMoveWasRight = true;
		isJumping = false;
		ground = (int) (screenHeight / 2f);
		speedFactor = (float) 1.4E-4;
		goingDown = false;
		currentJSpeed = 0;
	}

	private void changePicture(int i) {
		sm.updateSpatial(i);
	}

	@Override
	protected void controlUpdate(float tpf) {	
		speedFactor = tpf;
		if (left) {
			if (spatial.getLocalTranslation().x > (Float) spatial.getUserData("radius")) {
				spatial.move(tpf * -speed, 0, 0);				
				m.move((int) spatial.getLocalTranslation().x, (int) spatial.getLocalTranslation().y);				
			}
			lastMoveWasRight = false;
			if (System.currentTimeMillis() - timer > 100) {
				changePicture(lastMovement + 3);
				timer = System.currentTimeMillis();
				lastMovement++;
				if (lastMovement > 2)
					lastMovement = 0;
			}
		} else if (right) {
			if (spatial.getLocalTranslation().x < screenWidth - (Float) spatial.getUserData("radius")) {
				spatial.move(tpf * speed, 0, 0);
				m.move((int) spatial.getLocalTranslation().x, (int) spatial.getLocalTranslation().y);
			}
			lastMoveWasRight = true;
			if (System.currentTimeMillis() - timer > 100) {
				changePicture(lastMovement);
				timer = System.currentTimeMillis();
				lastMovement++;
				if (lastMovement > 2)
					lastMovement = 0;
			}
		} else if (lastMoveWasRight) {
			changePicture(0);
		} else if (!lastMoveWasRight) {
			changePicture(3);
		} 
		if (lcontrol) {			
			if (!isJumping) {
				currentJSpeed = speedFactor;
				isJumping = true;
			}
		}
	}
	
	public void jump() {
		if(isJumping) {
			currentJSpeed -= GRAVITY;
			spatial.move(0, currentJSpeed * speed, 0);
			m.move((int) spatial.getLocalTranslation().x, (int) spatial.getLocalTranslation().y);
		}
		if (spatial.getLocalTranslation().y <= ground) {
			isJumping = false;
			goingDown = false;
			currentJSpeed = 0;
		}
	}

	@Override
	protected void controlRender(RenderManager rm, ViewPort vp) {
	}

	// reset the moving values (i.e. for spawning)
	public void reset() {
		space = false;
		left = false;
		right = false;
	}
}
