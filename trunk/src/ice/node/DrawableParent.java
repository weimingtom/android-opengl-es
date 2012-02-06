package ice.node;

import android.view.MotionEvent;

import javax.microedition.khronos.opengles.GL11;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * User: ice
 * Date: 11-11-14
 * Time: 下午12:00
 */
public class DrawableParent<T extends Drawable> extends Drawable {

    public DrawableParent() {
        this(false);
    }

    public DrawableParent(boolean simpleMode) {
        if (simpleMode) {
            children = new ArrayList<T>();
        }
        else {
            children = new CopyOnWriteArrayList<T>();
        }

        setupTouchDispatcher();
    }

    @Override
    protected void onDraw(GL11 gl) {

        for (Drawable drawable : children) {

            if (drawable.isRemovable()) {
                children.remove(drawable);
            }
            else {
                drawable.draw(gl);
            }
        }
    }

    public void addChild(T child) {
        addChildren(child);
    }

    public void addChildren(T... children) {
        addChildren(Arrays.asList(children));
    }


    public void addChildren(Collection<? extends T> children) {

        for (Drawable child : children) {
            if (child == null) throw new NullPointerException();
            child.setParent(this);
        }

        this.children.addAll(children);
    }

    public boolean containsChild(Drawable child) {
        return children.contains(child);
    }


    public void remove(T child) {
        children.remove(child);
    }

    public T top() {
        if (children.size() == 0)
            return null;

        return children.get(children.size() - 1);
    }

    private void setupTouchDispatcher() {
        OnTouchListener TouchEventDispatcher = new OnTouchListener() {
            @Override
            public boolean onTouch(Drawable drawable, MotionEvent event) {

                for (Drawable child : children) {
                    if (child.onTouch(event)) return true;
                }

                return false;
            }
        };
        setOnTouchListener(TouchEventDispatcher);
    }

    protected List<T> children;
}
