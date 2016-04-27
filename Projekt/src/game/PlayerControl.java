import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

public class PlayerControl extends AbstractControl {
    private int screenWidth, screenHeight;
 
//    is the player currently moving?
    public boolean up,left,right;
//    speed of the player
    private float speed = 400f;
//    lastRotation of the player
    private float lastRotation;    
 
 
    public PlayerControl(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
       
    }
 
    @Override
    protected void controlUpdate(float tpf) {
        if (left) {
            if (spatial.getLocalTranslation().x  > (Float)spatial.getUserData("radius")) {
                spatial.move(tpf*-speed,0,0);
            }
           
//            spatial.rotate(0,0, -lastRotation + FastMath.PI);
//            lastRotation=FastMath.PI;
        } else if (right) {
            if (spatial.getLocalTranslation().x < screenWidth - (Float)spatial.getUserData("radius")) {
                spatial.move(tpf*speed,0,0);               
            }
//            spatial.rotate(0,0,-lastRotation + 0);
//            lastRotation=0;
        }
    }
 
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}
 
//    reset the moving values (i.e. for spawning)
    public void reset() {
        up = false;       
        left = false;
        right = false;
    }
}