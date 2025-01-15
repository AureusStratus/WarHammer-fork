//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.entities.bullet;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.bullet.ContinuousBulletType;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.type.StatusEffect;
import wh.content.WHStatusEffects;
import wh.graphics.PositionLightning;

public class DOTBulletType extends ContinuousBulletType {
    public float DOTRadius = 12.0F;
    public float DOTDamage = 150.0F;
    public float radIncrease = 0.25F;
    public StatusEffect effect;
    public Effect fx;

    public DOTBulletType() {
        this.effect = WHStatusEffects.palsy;
        this.fx = Fx.none;
        this.speed = 0.0F;
        this.lifetime = 120.0F;
        this.collides = false;
        this.hittable = false;
        this.absorbable = false;
        this.damage = this.DOTDamage;
        this.buildingDamageMultiplier = 0.0F;
        this.despawnEffect = this.hitEffect = Fx.none;
    }

    public void draw(Bullet b) {
        float rad = (Float)b.data;

        for(int i = 0; i < 2; ++i) {
            float chance = Mathf.lerp(0.5F, 0.2F, b.time / b.lifetime);
            if (Mathf.chance((double)chance) && Vars.state.isPlaying() && b.timer(1, 1.0F)) {
                float a0 = (float)Mathf.random(360) + b.rotation();
                float r0 = Mathf.random(-rad / 5.0F, rad / 2.0F) + rad;
                float a1 = (float)Mathf.random(360) + b.rotation();
                float r1 = Mathf.random(-rad / 5.0F, rad / 2.0F) + rad;
                Vec2 pos0 = new Vec2(b.x + r0 * Mathf.sinDeg(a0), b.y + r0 * Mathf.cosDeg(a0));
                Vec2 pos1 = new Vec2(b.x + r1 * Mathf.sinDeg(a1), b.y + r1 * Mathf.cosDeg(a1));
                PositionLightning.createEffect(pos0, pos1, this.lightningColor, 1, 2.5F);
                this.fx.at(pos0);
                this.fx.at(pos1);
            }
        }

    }

    public void init(Bullet b) {
        super.init(b);
        b.data = this.DOTRadius;
    }

    public void update(Bullet b) {
        super.update(b);
        float rad = (Float)b.data;
        rad += this.radIncrease;
        b.data = rad;
        if (b.timer(2, this.damageInterval)) {
            Damage.damage(b.team, b.x, b.y, rad * 1.2F, this.DOTDamage * b.damageMultiplier());
            Units.nearby((Team)null, b.x, b.y, rad, (unit) -> {
                if (unit.team != b.team) {
                    unit.apply(this.effect, 30.0F);
                }

            });
        }
    }
}
