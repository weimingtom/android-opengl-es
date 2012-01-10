package ice.engine;

import android.content.Context;

public interface App {

    Context getContext();

    AppView getRender();

    int getWidth();

    int getHeight();

    float getZNear();

    float getZFar();

    void exit();

    void intent(Class<? extends SceneProvider> to);
}
