package ice.node.widget;

import android.graphics.Bitmap;
import android.graphics.Point;

/**
 * User: jason
 * Date: 12-1-12
 * Time: 下午4:33
 */
public class RadioButton extends Button {

    public interface OnToggledListener {
        void onToggled(RadioButton radioButton);
    }

    public RadioButton(Bitmap normal, Bitmap pressed, Bitmap disable) {
        super(normal, pressed, disable);
    }


//    @Override
//    protected void onGetTouchFocus() {
//        super.onGetTouchFocus();
//
//        RadioButton toggled = parent.getToggled();
//
//        if (toggled == null) {
//
//            if (!lock) {
//                parent.setToggled(this);
//
//                if (onToggledListener != null) {
//                    onToggledListener.onToggled(this);
//                }
//
//            }
//
//            return;
//        }
//
//        if (toggled != this) {
//
//            toggled.setBitmap(toggled.normal);
//
//            if (lock) {
//                parent.setToggled(null);
//            }
//            else {
//                parent.setToggled(this);
//
//                if (onToggledListener != null) {
//                    onToggledListener.onToggled(this);
//                }
//            }
//
//
//        }
//    }

//    @Override
//    protected void onLostTouchFocus() {
//        if (lock) {
//            super.onLostTouchFocus();
//        }
//
//    }
//
//    @Override
//    protected void onGetHover() {
//        if (parent.getToggled() == this)
//            return;
//
//        super.onGetHover();
//    }
//
//    @Override
//    protected void onLoseHover() {
//        if (parent.getToggled() == this)
//            return;
//
//        super.onLoseHover();
//    }

    public void setOnToggledListener(OnToggledListener onToggledListener) {
        this.onToggledListener = onToggledListener;
    }

    void setParent(RadioGroup parent) {
        this.parent = parent;
    }

    private RadioGroup parent;
    private OnToggledListener onToggledListener;
}
