package ice.node.widget;

import java.util.ArrayList;

/**
 * User: jason
 * Date: 12-1-13
 * Time: 上午9:33
 */
public class RadioGroup extends ArrayList<RadioButton> {

    public RadioGroup(RadioButton... radioButtons) {

        for (RadioButton radioButton : radioButtons) {
            radioButton.setParent(this);
            add(radioButton);
        }
    }

    public void setToggled(RadioButton toggled) {
        this.toggled = toggled;
    }

    public RadioButton getToggled() {
        return toggled;
    }

    public void setOnToggledListener(RadioButton.OnToggledListener onToggledListener) {
        for (RadioButton radioButton : this) {
            radioButton.setOnToggledListener(onToggledListener);
        }
    }

    private RadioButton toggled;
}
