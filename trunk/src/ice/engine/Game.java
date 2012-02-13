package ice.engine;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import ice.node.Drawable;
import ice.res.Res;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * User: ice
 * Date: 12-1-6
 * Time: 下午3:09
 */
public abstract class Game extends Activity implements App {
    private static final String TAG = Game.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        EngineContext.build(this);
        Res.built(this);
        Drawable.resetId();

        providerStack = new Stack<Class<? extends SceneProvider>>();
        providerCache = new HashMap<Class<? extends SceneProvider>, SoftReference<SceneProvider>>();

        gameView = createRenderer();
        setContentView(gameView);

        Class<? extends SceneProvider> entryProvider = getEntry();//启动时，保证是主界面的入口
        providerStack.push(entryProvider);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");

        new Thread() {
            @Override
            public void run() {

                gameView.getRenderer().waitUntilInited();

                Class<? extends SceneProvider> topProviderClass = providerStack.peek();

                topProvider = findFromCache(topProviderClass);

                if (topProvider == null) {
                    topProvider = buildInstance(topProviderClass);
                    topProvider.onCreate();
                }

                topProvider.onResume();
                gameView.showScene(topProvider.getScene());

            }

        }.start();

    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "onPause");
        topProvider.onPause();
    }

    protected abstract Class<? extends SceneProvider> getEntry();

    @Override
    public AppView getRender() {
        return gameView;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            handBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private synchronized void handBack() {
        Class<? extends SceneProvider> topProviderClass = providerStack.peek();

        SceneProvider topInstance = findFromCache(topProviderClass);

        if (!topInstance.onBackPressed())
            back();

    }


    @Override
    public synchronized void intent(Class<? extends SceneProvider> to) {

        topProvider.onPause();

        SceneProvider toProvider = findFromCache(to);
        if (toProvider == null) {
            toProvider = buildInstance(to);
            toProvider.onCreate();
        }

        toProvider.onResume();
        topProvider = toProvider;

        if (toProvider.isEntry())
            providerStack.clear();

        providerStack.push(to);

        switchToScene(toProvider);
    }

    private void switchToScene(SceneProvider sceneProvider) {
        gameView.switchScene(sceneProvider.getScene());
    }

    @Override
    public void exit() {
        finish();
    }

    protected abstract GameView createRenderer();


    private SceneProvider buildInstance(Class<? extends SceneProvider> providerClass) {

        SceneProvider providerInstance = null;
        try {
            providerInstance = providerClass.newInstance();
            providerCache.put(providerClass, new SoftReference<SceneProvider>(providerInstance));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return providerInstance;
    }

    private SceneProvider findFromCache(Class<? extends SceneProvider> providerClass) {

        SoftReference<SceneProvider> cache = providerCache.get(providerClass);

        if (cache != null) {
            SceneProvider instance = cache.get();

            if (instance != null)
                return instance;

        }

        return null;
    }

    private synchronized void back() {
        Log.i(TAG, "back");

        if (providerStack.size() <= 1) {
            exit();
            return;
        }

        Class<? extends SceneProvider> currentProviderClass = providerStack.pop();

        topProvider = findFromCache(currentProviderClass);

        if (topProvider.isEntry()) {
            providerStack.clear();
            exit();
            return;
        }

        Class<? extends SceneProvider> topProviderClass = providerStack.peek();
        SceneProvider nextProvider = findFromCache(topProviderClass);
        if (nextProvider == null) {
            nextProvider = buildInstance(topProviderClass);
            nextProvider.onCreate();
        }

        topProvider.onPause();
        nextProvider.onResume();
        topProvider = nextProvider;
        switchToScene(nextProvider);
    }

    private GameView gameView;

    private SceneProvider topProvider;
    private Stack<Class<? extends SceneProvider>> providerStack;
    private Map<Class<? extends SceneProvider>, SoftReference<SceneProvider>> providerCache;
}
