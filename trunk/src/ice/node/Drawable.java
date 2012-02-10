package ice.node;


import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import ice.animation.Animation;
import ice.engine.EngineContext;
import ice.graphic.Camera;
import ice.model.Point3F;
import ice.util.GlUtil;

import javax.microedition.khronos.opengles.GL11;

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

        boolean colorChanged = ensureSelfStatus(gl);

        Animation theAnimation = animation;

        attachAnimation(gl, theAnimation);

        onDraw(gl);

        detachAnimation(gl, theAnimation);

        if (blendState) gl.glDisable(GL_BLEND);

        if (switchDepthTest && (depthTestStates != storedDepthTest))
            restoreDepthTest(gl, storedDepthTest);

        if (colorChanged)
            restoreColor(gl);

        gl.glPopMatrix();
    }

    private void restoreColor(GL11 gl) {
        gl.glColor4f(1, 1, 1, 1); //TODO 先这样吧
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }


    private boolean ensureDepthTestSwitch(GL11 gl, boolean depthTestStates) {

        boolean storedDepthTest = GlUtil.query(gl, GL_DEPTH_TEST);

        if (depthTestStates) {
            gl.glEnable(GL_DEPTH_TEST);
        }
        else {
            gl.glDisable(GL_DEPTH_TEST);
        }

        return storedDepthTest;
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
        setVisible(true);
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
            theAnimation.attach(gl, AnimationUtils.currentAnimationTimeMillis());

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

    private boolean ensureSelfStatus(GL11 gl) {

        if (posX != 0 || posY != 0 || posZ != 0)
            gl.glTranslatef(posX, posY, posZ);

        if (scaleX != 1 || scaleY != 1 || scaleZ != 1)
            gl.glScalef(scaleX, scaleY, scaleZ);

        if (rotate != 0)
            gl.glRotatef(rotate, axleX, axleY, axleZ);


        if (colors != null) {
            gl.glColor4f(colors[0], colors[1], colors[2], colors[3]);
            return true;
        }

        return false;
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

    public Point3F getAbsolutePos() {
        Point3F absolute = new Point3F(posX, posY, posZ);

        if (parent != null) {
            Point3F parentAbsolutePos = parent.getAbsolutePos();
            absolute.x += parentAbsolutePos.x;
            absolute.y += parentAbsolutePos.y;
            absolute.z += parentAbsolutePos.z;
        }

        return absolute;
    }

    protected void setParent(DrawableParent parent) {
        this.parent = parent;
    }

    public void setScale(float scaleX, float scaleY, float scaleZ) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
    }

    public void setRotate(float rotate, float axleX, float axleY, float axleZ) {
        this.rotate = rotate;
        this.axleX = axleX;
        this.axleY = axleY;
        this.axleZ = axleZ;
    }

    public void setColors(float[] colors) {
        this.colors = colors;
    }

    private boolean depthTest;
    private boolean switchDepthTestStates;

    private int blendFactor_S, blendFactor_D;
    private boolean blend;

    private OnTouchListener onTouchListener;

    private Camera camera;   //TODO

    protected float posX, posY, posZ;
    private float scaleX = 1, scaleY = 1, scaleZ = 1;
    private float rotate;
    private float axleX = 1, axleY = 1, axleZ = 1;
    private float[] colors;

    //private int width, height;
    protected boolean visible;
    protected boolean removable;
    protected float width, height;

    /**
     * 用于获取绝对位置
     */
    private DrawableParent parent;

    private Animation animation;

    private long id;
}
