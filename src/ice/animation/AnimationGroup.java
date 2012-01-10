package ice.animation;

import javax.microedition.khronos.opengles.GL11;
import java.util.ArrayList;
import java.util.List;

public class AnimationGroup extends Animation {

    List<Animation> children;
    List<Long> childrenOffsetTime;

    public AnimationGroup() {
        super(0);
        children = new ArrayList<Animation>(6);
        childrenOffsetTime = new ArrayList<Long>(6);
        interpolator = null;
    }

    public void addAnimation(Animation animation) {
        addAnimation(animation, 0L);
    }

    public void addAnimation(Animation animation, long offsetTime) {
        children.add(animation);
        childrenOffsetTime.add(offsetTime);
        duration = Math.max(duration, offsetTime + animation.duration);
    }


    @Override
    protected void onApply(GL11 gl, float interpolatedTime) {
        for (Animation animation : children) {
            animation.onApply(gl, interpolatedTime);
        }
    }

    @Override
    public void enableLoop(boolean loopEnabled) {
        super.enableLoop(loopEnabled);
        for (Animation child : children) {
            child.enableLoop(loopEnabled);
        }
    }


}
