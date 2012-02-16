package ice.node.widget;

import ice.engine.Scene;


public abstract class ConfirmDialog extends Scene {

    public ConfirmDialog() {
        onSetupComponent();
    }

    protected abstract void onSetupComponent();

    public ButtonOverlay getConfirmButtonOverlay() {
        return confirmButtonOverlay;
    }

    public ButtonOverlay getCancelButtonOverlay() {
        return cancelButtonOverlay;
    }

    protected ButtonOverlay confirmButtonOverlay;
    protected ButtonOverlay cancelButtonOverlay;
}
