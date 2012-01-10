package ice.node;

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
    }

    @Override
    protected void onDraw(GL11 gl) {

        for (Drawable drawable : children) {

            if (drawable.isRemovable())
                children.remove(drawable);

            drawable.draw(gl);
        }
    }

    public void addChild(T child) {
        if (child == null) throw new NullPointerException();
        children.add(child);
    }


    public void addChildren(T... children) {
        addChildren(Arrays.asList(children));
    }

    public void addChildren(Collection<? extends T> children) {

        for (Drawable child : children) {
            if (child == null) throw new NullPointerException();
        }

        this.children.addAll(children);
    }


    public boolean containsChild(Drawable child) {
        return children.contains(child);
    }

    public void remove(T child) {
        children.remove(child);
    }


    @Override
    public boolean onTouchEvent(TouchEvent event) {
        float originalX = event.getX();
        float originalY = event.getY();

        event.setPos(originalX - posX, originalY - posY);

        try {
            for (Drawable child : children) {

                if (child.onTouchEvent(event)) {
                    return true;
                }

            }
        }
        finally {
            event.setPos(originalX, originalY);
        }

        return super.onTouchEvent(event);
    }

    public T top() {
        if (children.size() == 0)
            return null;

        return children.get(children.size() - 1);
    }

    protected List<T> children;

}
