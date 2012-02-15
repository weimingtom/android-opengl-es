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
public class OverlayParent<T extends Overlay> extends Overlay {

    public OverlayParent() {
        this(false);
    }

    public OverlayParent(boolean simpleMode) {
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

        for (Overlay overlay : children) {

            if (overlay.isRemovable()) {
                children.remove(overlay);
            }
            else {
                overlay.draw(gl);
            }
        }
    }

    public void addChild(T child) {
        addChildren(child);
    }

    public void addChild(int index, T child) {
        children.add(index, child);
    }

    public void addChildren(T... children) {
        addChildren(Arrays.asList(children));
    }


    public void addChildren(Collection<? extends T> children) {

        for (Overlay child : children) {
            if (child == null) throw new NullPointerException();
            child.setParent(this);
        }

        this.children.addAll(children);
    }

    public boolean containsChild(Overlay child) {
        return children.contains(child);
    }

    public int indexOf(Overlay overlay) {
        return children.indexOf(overlay);
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
            public boolean onTouch(Overlay drawable, MotionEvent event) {

                for (Overlay child : children) {
                    if (onDispatchTouch(child, event))
                        return true;
                }

                return false;
            }
        };
        setOnTouchListener(TouchEventDispatcher);
    }

    protected boolean onDispatchTouch(Overlay child, MotionEvent event) {
        return child.onTouch(event);
    }

    protected List<T> children;
}
