package ice.node;

/**
 * User: ice
 * Date: 11-12-2
 * Time: 下午12:22
 */
public class TouchEvent {
    public static final int ACTION_NOTHING = -1;
    public static final int ACTION_DOWN = 0;
    public static final int ACTION_UP = 1;
    public static final int ACTION_MOVE = 2;
    public static final int ACTION_CANCEL = 3;

    public TouchEvent() {
        reset();
    }

    public void reset() {
        action = ACTION_NOTHING;
        x = y = 0;
    }

    public int getAction() {
        return action;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "TouchEvent{" +
                "action=" + action +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    private int action;
    private float x, y;
}
