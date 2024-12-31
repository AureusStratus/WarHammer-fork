//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.gen;

import arc.Core;
import arc.Events;
import arc.struct.Seq;
import mindustry.core.GameState.State;
import mindustry.game.EventType;
import wh.world.blocks.defense.CommandableBlock;

public final class WorldRegister {
    public static final Seq<Runnable> afterLoad = new Seq();
    public static final Seq<CommandableBlock.CommandableBlockBuild> commandableBuilds = new Seq();
    public static boolean worldLoaded = false;

    private WorldRegister() {
    }

    public static void postAfterLoad(Runnable runnable) {
        if (worldLoaded) {
            afterLoad.add(runnable);
        }

    }

    public static void load() {
        Events.on(EventType.ResetEvent.class, (e) -> {
            commandableBuilds.clear();
            worldLoaded = true;
        });
        Events.on(EventType.WorldLoadEvent.class, (e) -> {
            Core.app.post(() -> {
                worldLoaded = false;
            });
        });
        Events.on(EventType.StateChangeEvent.class, (e) -> {
            if (e.to == State.menu) {
                worldLoaded = true;
            }

        });
    }
}
