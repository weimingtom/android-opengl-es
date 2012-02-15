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

        OverlayParent<Scene> drawableRoot = renderer.getDrawDispatcher();

        drawableRoot.addChild(scene);
    }

    @Override
    public void switchScene(Scene newScene) {

        OverlayParent<Scene> root = renderer.getDrawDispatcher();

        Scene oldScene = root.top();

        root.addChild(newScene);
        root.remove(oldScene);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (renderer != null) {
            OverlayParent<Scene> drawDispatcher = renderer.getDrawDispatcher();
            Scene topScene = drawDispatcher.top();

            if (topScene != null)
                topScene.onTouchEvent(event);

        }
        return true;
    }

    private GlRenderer renderer;
}
