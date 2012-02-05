package ice.animation;

/**
 * User: Jason
 * Date: 12-2-5
 * Time: 下午12:08
 */
public class FadeInAnimation extends AlphaAnimation {

    public FadeInAnimation() {
        this(600);
    }

    public FadeInAnimation(long duration) {
        super(duration, 0, 1);
    }

}
