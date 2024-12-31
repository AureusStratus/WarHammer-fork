//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.content;

import arc.Core;
import arc.graphics.g2d.TextureRegion;

public class WHContent {
    public static TextureRegion arrowRegion;

    public WHContent() {
    }

    public static void loadPriority() {
        (new WHContent()).load();
    }

    public void load() {
        arrowRegion = Core.atlas.find("wh-jump-gate-arrow");
    }
}
