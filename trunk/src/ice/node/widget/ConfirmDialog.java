package ice.node.widget;

import ice.engine.Scene;


public abstract class ConfirmDialog extends Scene {

    public ConfirmDialog() {
        onSetupComponent();
    }

    protected abstract void onSetupComponent();

    public Button getConfirmButton() {
        return confirmButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    protected Button confirmButton;
    protected Button cancelButton;
}
