//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.content;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.util.*;
import mindustry.ctype.*;
import wh.core.*;

public class WHContent extends Content{
    public static TextureRegion arrowRegion;
    public static TextureRegion pointerRegion;
    public static TextureRegion strafeRegion;
    public static TextureRegion missileRegion;
    public static TextureRegion bombRegion;
    @Override
    public ContentType getContentType() {
        return ContentType.error;
    }
    public static void loadPriority() {
        new WHContent().load();
    }

    public void load() {
        arrowRegion = Core.atlas.find(WarHammerMod.name("jump-gate-arrow"));
        pointerRegion = Core.atlas.find(WarHammerMod.name("jump-gate-pointer"));
        strafeRegion = Core.atlas.find(WarHammerMod.name("strafe-mode"));
        missileRegion = Core.atlas.find(WarHammerMod.name("missile-mode"));
        bombRegion = Core.atlas.find(WarHammerMod.name("bomb-mode"));
    }
}
