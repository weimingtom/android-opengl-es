package ice.engine;

import android.content.Context;
import android.opengl.GLSurfaceView;
import ice.node.DrawableParent;

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

    @Override
    public void setRenderer(Renderer renderer) {
        if (renderer instanceof GlRenderer)
            super.setRenderer(renderer);
    }

    @Override
    public void showScene(Scene scene) {

        DrawableParent drawableRoot = renderer.getDrawDispatcher();

        drawableRoot.addChild(scene);
    }

    @Override
    public void switchScene(Scene newScene) {

        DrawableParent<Scene> root = renderer.getDrawDispatcher();

        Scene oldScene = root.top();

        root.addChild(newScene);
        root.remove(oldScene);
    }

    private GlRenderer renderer;
}
