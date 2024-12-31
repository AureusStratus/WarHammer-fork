//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.entities.abilities;

import arc.graphics.Color;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;

public class PointDefenseAbility extends Ability {
    public float px;
    public float py;
    public float reloadTime = 60.0F;
    public float range = 180.0F;
    public float bulletDamage = 10.0F;
    public String suffix = "-full";
    public Color color;
    public Bullet target;
    public float rotation;
    public float timer;
    public float reload;

    public PointDefenseAbility() {
        this.color = Color.white;
        this.rotation = 90.0F;
        this.timer = 90.0F;
        this.reload = 60.0F;
    }

    public PointDefenseAbility(float px, float py, float reloadTime, float range, float bulletDamage, String sprite) {
        this.color = Color.white;
        this.rotation = 90.0F;
        this.timer = 90.0F;
        this.reload = 60.0F;
        this.px = px;
        this.py = py;
        this.reloadTime = reloadTime;
        this.range = range;
        this.bulletDamage = bulletDamage;
        this.suffix = sprite;
    }

    public void update(Unit unit) {
        float x = unit.x + Angles.trnsx(unit.rotation, this.py, this.px);
        float y = unit.y + Angles.trnsy(unit.rotation, this.py, this.px);
        this.target = (Bullet)Groups.bullet.intersect(unit.x - this.range, unit.y - this.range, this.range * 2.0F, this.range * 2.0F).min((b) -> {
            return b.team != unit.team && b.type.hittable;
        }, (b) -> {
            return b.dst2(unit);
        });
        if (this.target != null && !this.target.isAdded()) {
            this.target = null;
        }

        if (this.target == null) {
            if (this.timer >= 90.0F) {
                this.rotation = Angles.moveToward(this.rotation, unit.rotation, 3.0F);
            } else {
                this.timer += Time.delta;
            }
        }

        if (this.target != null && this.target.within(unit, this.range) && this.target.team != unit.team && this.target.type != null && this.target.type.hittable) {
            this.timer = 0.0F;
            this.reload += Time.delta;
            float dest = this.target.angleTo(x, y) - 180.0F;
            this.rotation = Angles.moveToward(this.rotation, dest, 20.0F);
            if (Angles.within(this.rotation, dest, 3.0F) && this.reload >= this.reloadTime) {
                if (this.target.damage > this.bulletDamage) {
                    Bullet var10000 = this.target;
                    var10000.damage -= this.bulletDamage;
                } else {
                    this.target.remove();
                }

                Tmp.v1.trns(this.rotation, 6.0F);
                Fx.pointBeam.at(x + Tmp.v1.x, y + Tmp.v1.y, this.rotation, this.color, this.target);
                Fx.sparkShoot.at(x + Tmp.v1.x, y + Tmp.v1.y, this.rotation, this.color);
                Fx.pointHit.at(this.target.x, this.target.y, this.color);
                Sounds.lasershoot.at(x, y, Mathf.random(0.9F, 1.1F));
                this.reload = 0.0F;
            }
        }

    }
}
