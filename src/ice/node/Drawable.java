package ice.node;


import android.view.MotionEvent;
import ice.animation.Animation;
import ice.graphic.Camera;

import javax.microedition.khronos.opengles.GL11;

/**
 * User: ice
 * Date: 11-11-14
 * Time: 上午10:40
 */
public abstract class Drawable {
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

        ensureSelfPos(gl);

        applyAnimation(gl);

        onDraw(gl);

        gl.glPopMatrix();
    }

    private void applyAnimation(GL11 gl) {
        Animation theAnimation = animation;

        if (theAnimation != null) {
            theAnimation.apply(gl, System.currentTimeMillis());

            if (theAnimation.isCanceled()) {
                animation = null;
            }
            else {
                if (theAnimation.isCompleted()) {
                    onAnimationComplete();
                    animation = null;
                }
            }

        }
    }

    protected void ensureSelfPos(GL11 gl) {

        if (posX != 0 || posY != 0 || posZ != 0)
            gl.glTranslatef(posX, posY, posZ);
    }

    protected abstract void onDraw(GL11 gl);

    public void startAnimation(Animation animation) {
        if (animation == null) throw new IllegalArgumentException("animation null !");

        this.animation = animation;
        this.animation.start();
    }

    public void cancelAnimation() {

    }

    private void onAnimationComplete() {
        final Animation temp = animation;

        if (temp != null) {

            new Thread() {

                @Override
                public void run() {
                    Animation.AnimationListener listener = temp.getListener();
                    if (listener != null) listener.onAnimationEnd(Drawable.this);
                }

            }.start();

        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
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

    private Camera camera;   //TODO
    protected float posX, posY, posZ;
    //private int width, height;
    protected boolean visible;
    protected boolean removable;
    //private DrawableParent parent;
    private Animation animation;

    private long id;
}
