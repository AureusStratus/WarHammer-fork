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
import wh.content.WHContent;
import wh.gen.WHSounds;
import wh.graphics.MainRenderer;
import wh.graphics.WHShaders;
import wh.ui.dialogs.WHResearchDialog;

public class WarHammerMod extends Mod {
    public static String ModName = "WarHammerMod";

    public WarHammerMod() {
        Log.info("will load WarHammerMod");
        WHClassMap.load();
        Log.info("after load");
        Events.on(EventType.FileTreeInitEvent.class, (e) -> {
            if (!Vars.headless) {
                Log.info("will load WHSounds");
                WHSounds.load();
                Log.info("will post");
                Core.app.post(WHShaders::init);
                Log.info("after post");
            }

        });
        Log.info("after Events.on");
    }

    public static String name(String add) {
        return ModName + "-" + add;
    }

    public void init() {
        Log.info("init1");
        WHContent.loadPriority();
        Log.info("init2");
        MainRenderer.init();
        Log.info("init3");
        WHResearchDialog dialog = new WHResearchDialog();
        Log.info("init4");
        ResearchDialog research = Vars.ui.research;
        research.shown(() -> {
            Log.info("init5");
            dialog.show();
            Log.info("init6");
            Objects.requireNonNull(research);
            Objects.requireNonNull(research);
            Time.runTask(1.0F, research::hide);
            Log.info("init7");
        });
        Log.info("init9");
    }

    @Override
    public void loadContent() {
        Log.info("will loadContent");
        super.loadContent();
        Log.info("after loadContent");
    }
}
