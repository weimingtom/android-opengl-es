package ice.animation;

/**
 * User: jason
 * Date: 12-2-10
 * Time: 下午4:46
 */
public class AlphaAnimation extends ColorAnimation {

    public AlphaAnimation(long duration, float fromAlpha, float toAlpha) {
        super(duration, new float[]{1, 1, 1, fromAlpha}, new float[]{1, 1, 1, toAlpha});
    }

    public static AlphaAnimation createFadeIn(long duration) {
        return new AlphaAnimation(duration, 0, 1);
    }

    public static AlphaAnimation createFadeOut(long duration) {
        return new AlphaAnimation(duration, 1, 0);
    }

}
