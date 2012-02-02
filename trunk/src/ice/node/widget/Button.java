package ice.node.widget;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import static android.view.MotionEvent.*;

/**
 * User: ice
 * Date: 11-12-2
 * Time: 下午12:09
 */
public class Button extends TextureGrid {

    public interface OnClickListener {
        void onClick(Button btn);
    }


    public Button(Bitmap tileNormal, Bitmap tilePressed) {
        super(tileNormal);
        this.iconNormal = tileNormal;
        this.iconPressed = tilePressed;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!visible || disabled) return false;

        int action = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();

        boolean hitTest = hitTest(x, y);

        if (hitTest) {
            this.hitTest = true;
        }
        else {
            if (this.hitTest) {
                action = ACTION_CANCEL;
                this.hitTest = false;
            }
            else {
                this.hitTest = false;
                return false;
            }
        }

        if (action == ACTION_UP) {
            if (focusing) {
                focusing = false;
                texture.setBitmap(iconNormal);
                onClick();
                return true;
            }
        }
        else if (action == ACTION_CANCEL) {
            if (focusing) {
                focusing = false;
                texture.setBitmap(iconNormal);
            }
        }
        else {
            if (!focusing) {
                focusing = true;
                texture.setBitmap(iconPressed);
            }
        }

        return false;
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

    private boolean hitTest;
    private boolean focusing;
    private boolean disabled;
    private OnClickListener onClickListener;
    private Bitmap iconNormal, iconPressed;


}
