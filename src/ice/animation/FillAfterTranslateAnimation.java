package ice.animation;

import ice.node.Drawable;

/**
 * User: Mike.Hu
 * Date: 11-11-30
 * Time: 上午10:50
 */
public class FillAfterTranslateAnimation extends TranslateAnimation {


    public FillAfterTranslateAnimation(Drawable tile, long duration, float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
        super(duration, fromXDelta, toXDelta, fromYDelta, toYDelta);
        this.tile = tile;
        bindFillAfterListener(toXDelta, toYDelta);
    }

    private void bindFillAfterListener(final float toXDelta, final float toYDelta) {
        fillAfterListener = new AnimationListener() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                tile.setPos(
                        tile.getPosX() + toXDelta,
                        tile.getPosY() + toYDelta,
                        tile.getPosZ()
                );

                if (logicListener != null) {
                    logicListener.onAnimationEnd(drawable);
                }

            }

        };
        setListener(fillAfterListener);
    }

    @Override
    public void setListener(AnimationListener listener) {
        if (listener == fillAfterListener) {
            super.setListener(listener);
        }
        else {
            logicListener = listener;
        }
    }

    private AnimationListener fillAfterListener;
    private AnimationListener logicListener;
    private Drawable tile;
}
