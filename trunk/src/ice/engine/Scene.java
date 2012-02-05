package ice.engine;


import ice.node.Drawable;
import ice.node.DrawableParent;

/**
 * User: ice
 * Date: 11-11-14
 * Time: 上午10:41
 */
public class Scene extends DrawableParent<Drawable> {



    @Override
    public float getWidth() {
        return EngineContext.getAppWidth();
    }

    @Override
    public float getHeight() {
        return EngineContext.getAppHeight();
    }
}
