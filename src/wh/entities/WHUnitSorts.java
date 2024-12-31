//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.entities;

import arc.math.Mathf;
import mindustry.Vars;
import mindustry.entities.Units;
import mindustry.world.meta.BlockGroup;

public final class WHUnitSorts {
    private static float dcr;
    public static final Units.Sortf slowest = (u, x, y) -> {
        return u.speed() + Mathf.dst2(u.x, u.y, x, y) / 6400.0F;
    };
    public static final Units.Sortf fastest = (u, x, y) -> {
        return -u.speed() + Mathf.dst2(u.x, u.y, x, y) / 6400.0F;
    };
    public static final Units.Sortf regionalHPMaximumUnit = (u, x, y) -> {
        dcr = 0.0F;
        Vars.state.teams.get(u.team).tree().intersect(u.x - 68.0F, u.y - 68.0F, 128.0F, 128.0F, (t) -> {
            dcr -= t.health + t.shield;
        });
        return dcr;
    };
    public static final Units.Sortf regionalHPMaximumBuilding = (u, x, y) -> {
        dcr = 0.0F;
        Vars.indexer.eachBlock(u, 128.0F, (bi) -> {
            return bi.block.group != BlockGroup.walls;
        }, (b) -> {
            dcr -= b.health;
        });
        return dcr;
    };
    public static final Units.Sortf regionalHPMaximumAll = (u, x, y) -> {
        dcr = 0.0F;
        Vars.state.teams.get(u.team).tree().intersect(u.x - 68.0F, u.y - 68.0F, 128.0F, 128.0F, (t) -> {
            dcr -= t.health + t.shield;
        });
        Vars.indexer.eachBlock(u, 128.0F, (bi) -> {
            return bi.block.group != BlockGroup.walls;
        }, (b) -> {
            dcr -= b.health;
        });
        return dcr;
    };

    private WHUnitSorts() {
    }
}
