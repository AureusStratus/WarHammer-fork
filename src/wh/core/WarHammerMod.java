//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.core;

import arc.Core;
import arc.Events;
import arc.util.Log;
import arc.util.Time;
import java.util.Objects;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import mindustry.ui.dialogs.ResearchDialog;
import wh.content.*;
import wh.gen.EntityRegister;
import wh.gen.WHSounds;
import wh.gen.WorldRegister;
import wh.graphics.MainRenderer;
import wh.graphics.WHShaders;
import wh.ui.dialogs.WHResearchDialog;

public class WarHammerMod extends Mod {
    public static String ModName = "WarHammerMod";

    public WarHammerMod() {
        WHClassMap.load();
        Events.on(EventType.FileTreeInitEvent.class, (e) -> {
            if (!Vars.headless) {
                WHSounds.load();
                Core.app.post(WHShaders::init);
            }

        });
    }

    public static String name(String add) {
        return ModName + "-" + add;
    }

    public void init() {
        WHContent.loadPriority();
        MainRenderer.init();
        WHResearchDialog dialog = new WHResearchDialog();
        ResearchDialog research = Vars.ui.research;
        research.shown(() -> {
            dialog.show();
            Objects.requireNonNull(research);
            Objects.requireNonNull(research);
            Time.runTask(1.0F, research::hide);
        });
    }

    @Override
    public void loadContent() {
        super.loadContent();
        WHOverride.load();


      /* WorldRegister.load();
        EntityRegister.load();
        WHStatusEffects.load();
        WHItems.load();
        WHLiquids.load();
        WHBullets.load();
        WHUnitTypes.load();
        WHBlocks.load();
        WHPlanets.load();
        WHTechTree.load();*/
    }
}
