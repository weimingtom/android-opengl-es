package ice.engine;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import ice.graphic.GlRenderer;
import ice.node.OverlayParent;

/**
 * User: ice
 * Date: 12-1-6
 * Time: 下午3:23
 */
public class GameView extends GLSurfaceView implements AppView {

    public GameView(Context context, GlRenderer glRenderer) {
        super(context);

        this.renderer = glRenderer;

        setRenderer(renderer);
    }

    public GlRenderer getRenderer() {
        return renderer;
    }

    @Override
    public void setRenderer(Renderer renderer) {
        if (renderer instanceof GlRenderer)
            super.setRenderer(renderer);
    }

    @Override
    public void showScene(Scene scene) {

        OverlayParent drawableRoot = renderer.getDrawDispatcher();

        drawableRoot.addChild(scene);
    }

    @Override
    public void switchScene(Scene newScene) {

        OverlayParent root = renderer.getDrawDispatcher();

        Scene oldScene = (Scene) root.top();

        root.addChild(newScene);
        root.remove(oldScene);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (renderer != null) {
            OverlayParent drawDispatcher = renderer.getDrawDispatcher();
            Scene topScene = (Scene) drawDispatcher.top();

            if (topScene != null)
                topScene.onTouchEvent(event);

        }
        return true;
    }

    private GlRenderer renderer;
}
