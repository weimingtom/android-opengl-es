package ice.node.widget;

import ice.graphic.texture.Texture;
import ice.node.OverlayParent;

import java.util.ArrayList;
import java.util.List;

public class AtlasSequence extends OverlayParent {

    public AtlasSequence(int eachWidth, int eachHeight, float margin) {
        this.eachWidth = eachWidth;
        this.eachHeight = eachHeight;
        this.margin = margin;
    }

    public void setAtlas(Texture atlasTexture, int splitU, int splitV) {
        this.atlasTexture = atlasTexture;
        this.splitU = splitU;
        this.splitV = splitV;
    }


    public void setSequence(int[] sequence) {
        int size = size();

        float totalWidth = sequence.length * eachWidth + (sequence.length - 1) * margin;

        float mostRight = totalWidth / 2 - eachWidth / 2;


        if (size == 0) {

            List<AtlasOverlay> overlays = new ArrayList<AtlasOverlay>(sequence.length);

            for (int i = 0; i < sequence.length; i++) {

                AtlasOverlay atlasOverlay = new AtlasOverlay(eachWidth, eachHeight, splitU, splitV);

                atlasOverlay.setAtlasIndex(sequence[i], atlasTexture);

                atlasOverlay.setPos(mostRight - i * (eachWidth / 2 + margin), 0);

                overlays.add(atlasOverlay);
            }

            addChildren(overlays);
        }
        else if (size == sequence.length) {
            for (int i = 0; i < sequence.length; i++) {
                AtlasOverlay atlasOverlay = (AtlasOverlay) get(i);
                atlasOverlay.setAtlasIndex(sequence[i], atlasTexture);
                atlasOverlay.setVisible(true);
            }
        }
        else {

        }
        this.sequence = sequence;
    }

    public void resetSequence() {
        for (int i = 0; i < size(); i++) {
            get(i).setVisible(false);
        }
    }

    private Texture atlasTexture;
    private int splitU, splitV;

    private int[] sequence;

    private int eachWidth, eachHeight;
    private float margin;
}
