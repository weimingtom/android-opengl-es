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
    protected void onAttach(GL11 gl, float interpolatedTime) {
        for (Animation animation : children) {
            animation.onAttach(gl, interpolatedTime);
        }
    }

    @Override
    protected void onDetach(GL11 gl) {
        for (Animation animation : children) {
            animation.onDetach(gl);
        }
    }

    @Override
    public void setLoop(boolean loopEnabled) {
        super.setLoop(loopEnabled);
        for (Animation child : children) {
            child.setLoop(loopEnabled);
        }
    }


}
