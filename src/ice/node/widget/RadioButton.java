package ice.node.widget;

import android.graphics.Bitmap;

/**
 * User: jason
 * Date: 12-1-12
 * Time: 下午4:33
 */
public class RadioButton extends Button {

    public interface OnToggledListener {
        void onToggled(RadioButton radioButton);
    }

    public RadioButton(int tileNormalId, int tilePressedId, int lockedId) {
        super(tileNormalId, tilePressedId, lockedId);
    }

    public RadioButton(Bitmap normal, Bitmap pressed, Bitmap disable) {
        super(normal, pressed, disable);
    }


    @Override
    protected void onGetTouchFocus() {
        super.onGetTouchFocus();

        RadioButton toggled = parent.getToggled();

        if (toggled == null) {

            if (!lock) {
                parent.setToggled(this);

                if (onToggledListener != null) {
                    onToggledListener.onToggled(this);
                }

            }

            return;
        }

        if (toggled != this) {

            toggled.texture.setBitmap(toggled.iconNormal);

            if (lock) {
                parent.setToggled(null);
            }
            else {
                parent.setToggled(this);

                if (onToggledListener != null) {
                    onToggledListener.onToggled(this);
                }
            }


        }
    }

    @Override
    protected void onLostTouchFocus() {
        if (lock) {
            super.onLostTouchFocus();
        }

    }


    public void setOnToggledListener(OnToggledListener onToggledListener) {
        this.onToggledListener = onToggledListener;
    }

    void setParent(RadioGroup parent) {
        this.parent = parent;
    }

    private RadioGroup parent;
    private OnToggledListener onToggledListener;
}
