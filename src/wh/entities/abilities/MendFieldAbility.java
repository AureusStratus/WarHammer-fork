//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.entities.abilities;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;

public class MendFieldAbility extends Ability {
    public Color baseColor = Color.valueOf("84f491");
    public Color phaseColor = Color.valueOf("ffd59e");
    public float range = 180.0F;
    public float reload = 60.0F;
    public float healP = 10.0F;
    public float timer;

    public MendFieldAbility() {
    }

    public MendFieldAbility(float range, float reload, float healP) {
        this.range = range;
        this.reload = reload;
        this.healP = healP;
    }

    public void update(Unit unit) {
        Vars.indexer.eachBlock(unit, this.range, Building::damaged, (other) -> {
            this.timer += Time.delta;
            if (this.timer >= this.reload) {
                this.timer = 0.0F;
                other.heal(this.healP / 100.0F * (float)other.block.health);
                Fx.healBlockFull.at(other.x, other.y, (float)other.block.size, Tmp.c1.set(this.baseColor).lerp(this.phaseColor, 0.3F));
            }

        });
    }

    public Ability copy() {
        return new MendFieldAbility(this.range, this.reload, this.healP);
    }

    public void draw(Unit unit) {
        Vars.indexer.eachBlock(unit, this.range, Building::damaged, (other) -> {
            Color tmp = Tmp.c1.set(this.baseColor);
            tmp.a = Mathf.absin(4.0F, 1.0F);
            Drawf.selected(other, tmp);
        });
    }
}
