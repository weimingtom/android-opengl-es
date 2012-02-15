package ice.engine;


import ice.node.Overlay;
import ice.node.OverlayParent;

/**
 * User: ice
 * Date: 11-11-14
 * Time: 上午10:41
 */
public class Scene extends OverlayParent<Overlay> {



    @Override
    public float getWidth() {
        return EngineContext.getAppWidth();
    }

    @Override
    public float getHeight() {
        return EngineContext.getAppHeight();
    }
}
