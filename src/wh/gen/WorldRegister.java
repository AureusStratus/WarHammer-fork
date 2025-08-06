//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.gen;

import arc.Core;
import arc.Events;
import arc.struct.*;
import mindustry.core.GameState.State;
import mindustry.game.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import wh.entities.world.blocks.defense.*;
import wh.entities.world.blocks.defense.AirRaiderCallBlock.*;
import wh.entities.world.blocks.defense.CommandableBlock.*;
import wh.graphics.*;

import static wh.graphics.MainRenderer.renderer;

public class WorldRegister {
    public static final Seq<Runnable> afterLoad = new Seq<>();
    public static final Seq<CommandableBlockBuild> commandableBuilds = new Seq<>();
    public static final Seq<AirRaiderUnitBuild> ARBuilds = new Seq<>();
    public static final ObjectMap<Team, ObjectMap<Class<?>, ObjectSet<Building>>> placedBuildings = new ObjectMap<>();
    static {
        for (Team team : Team.all) {
            placedBuildings.put(team, new ObjectMap<>());
        }
    }
    public static boolean worldLoaded = false;

    private WorldRegister() {
    }

    // 获取某队某类型的建筑的总数
    public static <T extends Building> ObjectSet<T> getPlaced(Team team, Class<T> type) {
        ObjectMap<Class<?>, ObjectSet<Building>> teamMap = placedBuildings.get(team);

        if (teamMap == null) {
            teamMap = new ObjectMap<>();
            placedBuildings.put(team, teamMap);
        }

        ObjectSet<Building> set = teamMap.get(type);
        if (set == null) {
            set = new ObjectSet<>();
            teamMap.put(type, set);
        }
        return (ObjectSet<T>) set;
    }

    public static <T extends Building> void addPlaced(Team team, T building) {
        getPlaced(team, (Class<T>) building.getClass()).add(building);
    }

    public static <T extends Building> void removePlaced(Team team, T building) {
        getPlaced(team, (Class<T>) building.getClass()).remove(building);
    }

    public static void clear() {
        commandableBuilds.clear();
        ARBuilds.clear();
        AirRaiderCallBlock.clear();
    }

    public static void load() {
        Events.on(EventType.ResetEvent.class, (e) -> {
            WorldRegister.clear();
            worldLoaded = true;
        });
    }
}
