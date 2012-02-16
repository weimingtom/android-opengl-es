package ice.node.widget;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import ice.node.Overlay;
import ice.res.Res;

/**
 * User: ice
 * Date: 11-12-2
 * Time: 下午12:09
 */
public class ButtonOverlay extends BitmapOverlay {

    public interface OnClickListener {
        void onClick(ButtonOverlay btn);
    }

    public ButtonOverlay(int tileNormalId, int tilePressedId) {
        this(tileNormalId, tilePressedId, 0);
    }

    public ButtonOverlay(int tileNormalId, int tilePressedId, int lockedId) {
        this(
                Res.getBitmap(tileNormalId),
                Res.getBitmap(tilePressedId),
                lockedId == 0 ? null : Res.getBitmap(lockedId)
        );
    }

    public ButtonOverlay(Bitmap tileNormal, Bitmap tilePressed) {
        this(tileNormal, tilePressed, null);
    }

    public ButtonOverlay(float width, float height) {
        super(width, height);
        setOnTouchListener(new ClickHandler());
    }

    public ButtonOverlay(Bitmap tileNormal, Bitmap tilePressed, Bitmap locked) {
        super(tileNormal.getWidth(), tileNormal.getHeight());

        setBitmaps(tileNormal, tilePressed, locked);
        setOnTouchListener(new ClickHandler());
    }

    public void setBitmaps(int iconNormal, int iconPressed, int locked) {
        setBitmaps(
                Res.getBitmap(iconNormal),
                Res.getBitmap(iconPressed),
                locked == 0 ? null : Res.getBitmap(locked)
        );
    }

    public void setBitmaps(Bitmap iconNormal, Bitmap iconPressed, Bitmap locked) {

        this.iconNormal = setBitmap(iconNormal);

        this.iconPressed = getTexture().tryAdjust(iconPressed);

        if (locked != null)
            this.locked = getTexture().tryAdjust(locked);
    }

    protected void onClick() {
        if (onClickListener != null) {
            onClickListener.onClick(this);
        }
    }


    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    protected void onGetTouchFocus() {
        setBitmap(iconPressed);
    }

    protected void onLostTouchFocus() {
        setBitmap(lock ? locked : iconNormal);
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {

        if (this.lock != lock) {
            this.lock = lock;
            texture.setBitmap(lock ? locked : iconNormal);
        }
    }

    private boolean focusing;
    private boolean disabled;
    protected boolean lock;
    private OnClickListener onClickListener;
    protected Bitmap iconNormal, iconPressed, locked;

    public class ClickHandler implements OnTouchListener {

        @Override
        public boolean onTouch(Overlay overlay, MotionEvent event) {
            if (!isVisible() || disabled) return false;

            int action = event.getAction();

            int x = (int) event.getX();
            int y = (int) event.getY();

            boolean hitTest = hitTest(x, y);

            if (hitTest) {
                this.hitTest = true;
            }
            else {
                if (this.hitTest) {
                    action = MotionEvent.ACTION_CANCEL;
                    this.hitTest = false;
                }
                else {
                    this.hitTest = false;
                    return false;
                }
            }

            if (action == MotionEvent.ACTION_UP) {
                if (focusing) {
                    focusing = false;
                    onLostTouchFocus();
                    onClick();
                    return true;
                }
            }
            else if (action == MotionEvent.ACTION_CANCEL) {
                if (focusing) {
                    focusing = false;
                    onLostTouchFocus();
                }
            }
            else {
                if (!focusing) {
                    focusing = true;
                    onGetTouchFocus();
                }
            }

            return false;
        }

        private boolean hitTest;
    }
}
