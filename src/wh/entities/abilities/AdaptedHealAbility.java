//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.entities.abilities;

import arc.func.Cons;
import arc.graphics.Color;
import arc.util.Time;
import mindustry.entities.Units;
import mindustry.entities.abilities.RepairFieldAbility;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import wh.content.WHFx;

public class AdaptedHealAbility extends RepairFieldAbility {
    public Color applyColor;
    public boolean ignoreHealthMultiplier;
    public float selfHealAmount;
    public float selfHealReloadTime;
    protected float lastHealth;
    protected float selfHealReload;

    public AdaptedHealAbility() {
        this(1.0F, 1.0F, 1.0F);
    }

    public AdaptedHealAbility(float amount, float reload, float range, Color applyColor) {
        this(amount, reload, range);
        this.applyColor = applyColor;
    }

    public AdaptedHealAbility(float amount, float reload, float range) {
        super(amount, reload, range);
        this.applyColor = Pal.heal;
        this.ignoreHealthMultiplier = true;
        this.selfHealAmount = 5.0E-4F;
        this.selfHealReloadTime = -1.0F;
        this.lastHealth = 0.0F;
        this.selfHealReload = 0.0F;
        this.healEffect = WHFx.healReceiveCircle;
        this.activeEffect = WHFx.healSendCircle;
    }

    public AdaptedHealAbility modify(Cons<AdaptedHealAbility> modifier) {
        modifier.get(this);
        return this;
    }

    public void update(Unit unit) {
        this.timer += Time.delta;
        if (this.timer >= this.reload) {
            this.wasHealed = false;
            Units.nearby(unit.team, unit.x, unit.y, this.range, (other) -> {
                if (other.damaged()) {
                    this.healEffect.at(other.x, other.y, 0.0F, this.applyColor, this.parentizeEffects ? other : null);
                    this.wasHealed = true;
                }

                other.heal(this.amount);
            });
            if (this.wasHealed) {
                this.activeEffect.at(unit.x, unit.y, this.range, this.applyColor);
            }

            this.timer = 0.0F;
        }

        if (!(this.selfHealReloadTime < 0.0F)) {
            if (this.lastHealth <= unit.health && unit.damaged()) {
                this.selfHealReload += Time.delta;
                if (this.selfHealReload > this.selfHealReloadTime) {
                    unit.healFract(this.selfHealAmount * (this.ignoreHealthMultiplier ? 1.0F : 1.0F / unit.healthMultiplier));
                }
            } else {
                this.selfHealReload = 0.0F;
            }

            this.lastHealth = unit.health;
        }
    }
}
