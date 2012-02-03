package ice.node;


import android.view.MotionEvent;
import ice.animation.Animation;
import ice.engine.EngineContext;
import ice.graphic.Camera;

import javax.microedition.khronos.opengles.GL11;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import static ice.model.Constants.SIZE_OF_FLOAT;
import static javax.microedition.khronos.opengles.GL10.GL_BLEND;
import static javax.microedition.khronos.opengles.GL10.GL_DEPTH_TEST;

/**
 * User: ice
 * Date: 11-11-14
 * Time: 上午10:40
 */
public abstract class Drawable {

    public interface OnTouchListener {
        boolean onTouch(Drawable drawable, MotionEvent event);
    }

    private static long maxId;

    public synchronized static long requestId() {
        maxId++;

        if (maxId == Long.MAX_VALUE) {
            throw new IllegalStateException("ID reuse !");
        }

        return maxId;
    }

    public synchronized static void resetId() {
        maxId = 0;
    }

    public Drawable() {
        this(0, 0, 0);
    }

    protected Drawable(float posX, float posY, float posZ) {
        setPos(posX, posY, posZ);
        visible = true;
        id = requestId();
    }

    public void draw(GL11 gl) {
        if (!visible) return;

        gl.glPushMatrix();

        boolean switchDepthTest = switchDepthTestStates;
        boolean depthTestStates = depthTest;
        boolean storedDepthTest = false;

        if (switchDepthTest)
            storedDepthTest = ensureDepthTestSwitch(gl, depthTestStates);

        boolean blendState = blend;

        if (blendState) {
            gl.glEnable(GL_BLEND);
            gl.glBlendFunc(blendFactor_S, blendFactor_D);
        }

        ensureSelfPos(gl);

        Animation theAnimation = animation;

        attachAnimation(gl, theAnimation);

        onDraw(gl);

        detachAnimation(gl, theAnimation);

        if (blendState) gl.glDisable(GL_BLEND);

        if (switchDepthTest && (depthTestStates != storedDepthTest))
            restoreDepthTest(gl, storedDepthTest);

        gl.glPopMatrix();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }


    private boolean ensureDepthTestSwitch(GL11 gl, boolean depthTestStates) {
        ByteBuffer vfb = ByteBuffer.allocateDirect(SIZE_OF_FLOAT);
        vfb.order(ByteOrder.nativeOrder());
        IntBuffer currentDepthTest = vfb.asIntBuffer();

        gl.glGetBooleanv(GL_DEPTH_TEST, currentDepthTest);

        if (depthTestStates) {
            gl.glEnable(GL_DEPTH_TEST);
        }
        else {
            gl.glDisable(GL_DEPTH_TEST);
        }

        return currentDepthTest.get(0) != 0;
    }

    private void restoreDepthTest(GL11 gl, boolean storedDepthTest) {
        if (storedDepthTest) {
            gl.glEnable(GL_DEPTH_TEST);
        }
        else {
            gl.glDisable(GL_DEPTH_TEST);
        }
    }

    protected abstract void onDraw(GL11 gl);

    public synchronized void startAnimation(Animation animation) {
        this.animation = animation;
        this.animation.start();
    }

    public synchronized void cancelAnimation() {
        animation = null;
    }

    public void enableBlend(int sFactor, int dFactor) {
        blendFactor_S = sFactor;
        blendFactor_D = dFactor;
        blend = true;
    }

    public void disableBlend() {
        blend = false;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    public boolean isRemovable() {
        return removable;
    }

    public boolean hitTest(int x, int y) {
        return x >= posX
                && x <= posX + width
                && y <= posY
                && y >= posY - height;
    }

    public final boolean onTouchEvent(MotionEvent event) {
        float originalX = event.getX();
        float originalY = event.getY();

        boolean handled = false;

        event.setLocation(originalX - posX, EngineContext.getAppHeight() - originalY - posY);
        try {
            handled = onTouch(event);
        }
        finally {
            event.setLocation(originalX, originalY);
        }

        return handled;
    }

    protected boolean onTouch(MotionEvent event) {
        return onTouchListener == null
                ? false
                : onTouchListener.onTouch(this, event);
    }

    public void setPos(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void setPos(float posX, float posY, float posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getPosZ() {
        return posZ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Drawable drawable = (Drawable) o;

        if (id != drawable.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    private void attachAnimation(GL11 gl, Animation theAnimation) {

        if (theAnimation != null)
            theAnimation.attach(gl, System.currentTimeMillis());

    }

    private void detachAnimation(GL11 gl, Animation theAnimation) {
        if (theAnimation == null) return;

        theAnimation.detach(gl);

        if (theAnimation.isCanceled()) {
            animation = null;
        }
        else {
            if (theAnimation.isCompleted()) {
                theAnimation.onComplete(this, gl);
                animation = null;
            }
        }
    }

    private void ensureSelfPos(GL11 gl) {

        if (posX != 0 || posY != 0 || posZ != 0)
            gl.glTranslatef(posX, posY, posZ);
    }

    public void enableDepthTestSwitch(boolean depthTest) {
        this.depthTest = depthTest;
        switchDepthTestStates = true;
    }

    public void disableDepthTestSwitch() {
        switchDepthTestStates = false;
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    private boolean depthTest;
    private boolean switchDepthTestStates;

    private int blendFactor_S, blendFactor_D;
    private boolean blend;

    private OnTouchListener onTouchListener;

    private Camera camera;   //TODO
    protected float posX, posY, posZ;
    //private int width, height;
    protected boolean visible;
    protected boolean removable;
    protected int width, height;

    //private DrawableParent parent;
    private Animation animation;

    private long id;
}
