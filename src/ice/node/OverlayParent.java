package ice.node;

import android.view.MotionEvent;

import javax.microedition.khronos.opengles.GL11;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * User: ice
 * Date: 11-11-14
 * Time: 下午12:00
 */
public class OverlayParent extends Overlay {

    public OverlayParent() {
        children = new ArrayList<Overlay>();
        addBuffer = new ArrayList<Overlay>();
        removeBuffer = new ArrayList<Overlay>();

        setupTouchDispatcher();
    }

    @Override
    protected void onDraw(GL11 gl) {

        if (addBuffer.size() > 0) {
            synchronized (this) {
                for (Overlay newOverlay : addBuffer)
                    newOverlay.onComeIntoUse(gl);

                children.addAll(addBuffer);
                addBuffer.clear();
            }
        }


        for (Overlay overlay : children) {

            if (overlay.isRemovable()) {
                removeBuffer.add(overlay);
                continue;
            }

            overlay.draw(gl);
        }

        if (removeBuffer.size() > 0) {
            synchronized (this) {
                for (Overlay newOverlay : removeBuffer)
                    newOverlay.onOutdated(gl);

                children.removeAll(removeBuffer);
                removeBuffer.clear();
            }
        }
    }

    public void addChild(Overlay child) {
        addChildren(child);
    }

    public void addChildren(Overlay... children) {
        addChildren(Arrays.asList(children));
    }

    public void addChildren(Collection<? extends Overlay> children) {

        for (Overlay child : children) {
            if (child == null) throw new NullPointerException();
            child.setParent(this);
        }

        synchronized (this) {
            addBuffer.addAll(children);
        }
    }

    public boolean containsChild(Overlay child) {
        return children.contains(child) || addBuffer.contains(child);
    }

    public Overlay get(int index) {
        return children.get(index);
    }

    public synchronized void remove(Overlay child) {
        removeBuffer.add(child);
    }

    public synchronized void remove(Collection<Overlay> children) {
        removeBuffer.addAll(children);
    }

    public void clear() {
        children.clear();
    }

    public Overlay top() {
        if (children.size() == 0)
            return null;

        return children.get(children.size() - 1);
    }

    @Override
    protected void onComeIntoUse(GL11 gl) {
        for (Overlay child : children)
            child.onComeIntoUse(gl);
    }

    @Override
    protected void onOutdated(GL11 gl) {
        for (Overlay child : children)
            child.onOutdated(gl);
    }

    public int size() {
        return children.size();
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
        return child.onTouchEvent(event);
    }

    private List<Overlay> addBuffer;
    private List<Overlay> removeBuffer;

    protected List<Overlay> children;
}
