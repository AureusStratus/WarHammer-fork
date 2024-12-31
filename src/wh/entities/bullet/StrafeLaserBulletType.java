//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.entities.bullet;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.UnitSorts;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Entityc;
import mindustry.gen.Player;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import wh.graphics.Drawn;
import wh.util.WHUtils;

public class StrafeLaserBulletType extends BulletType {
    public float strafeAngle;
    public float width;
    public float computeTick;
    public float fallScl;
    public boolean dataRot;
    public float lerpWhiteSine;
    public float sideTriRootLength;
    public float sideTriLength;
    public float sideTriWidth;
    public float sideLengthDifference;

    public StrafeLaserBulletType() {
        this(1.0F, 1.0F);
    }

    public StrafeLaserBulletType(float speed, float damage) {
        this.strafeAngle = 70.0F;
        this.width = 18.0F;
        this.computeTick = 5.0F;
        this.fallScl = 0.125F;
        this.dataRot = true;
        this.lerpWhiteSine = 0.3F;
        this.sideTriRootLength = 20.0F;
        this.sideTriLength = 50.0F;
        this.sideTriWidth = 6.0F;
        this.sideLengthDifference = 10.0F;
        this.damage = damage;
        this.speed = speed;
        this.lifetime = 160.0F;
        this.hitEffect = new Effect(20.0F, (e) -> {
            Draw.color(e.color, Color.white, e.fout() * 0.6F + 0.1F);
            Lines.stroke(e.fout() * 2.0F);
            Angles.randLenVectors((long)e.id, 3, e.finpow() * 48.0F, e.rotation, 35.0F, (x, y) -> {
                float ang = Mathf.angle(x, y);
                Lines.lineAngle(e.x + x, e.y + y, ang, e.fout() * 8.0F + 2.0F);
            });
        });
        this.shootEffect = Fx.none;
        this.smokeEffect = Fx.none;
        this.maxRange = 600.0F;
        this.hitShake = 4.0F;
        this.reflectable = false;
        this.despawnEffect = Fx.none;
        this.impact = true;
        this.keepVelocity = false;
        this.collides = false;
        this.pierce = true;
        this.hittable = false;
        this.absorbable = false;
        this.hitColor = null;
    }

    public void init() {
        super.init();
        this.drawSize = Math.max(this.drawSize, this.maxRange * 2.0F);
    }

    public void init(Bullet b) {
        super.init(b);
        if (this.dataRot) {
            Entityc var3 = b.owner;
            if (var3 instanceof Unit) {
                Unit u = (Unit)var3;
                b.fdata = b.angleTo(u.aimX, u.aimY());
            }
        }

    }

    public float getRotation(Bullet b) {
        return -this.strafeAngle / 2.0F + this.strafeAngle * b.fin(Interp.pow3);
    }

    public float estimateDPS() {
        return this.continuousDamage();
    }

    public Color getColor(Bullet b) {
        return this.hitColor == null ? b.team.color : this.hitColor;
    }

    public void draw(Bullet b) {
        float rotation = this.dataRot ? b.fdata : b.rotation() + this.getRotation(b);
        float fout = b.fout(this.fallScl) * Mathf.curve(b.fin(), 0.0F, this.fallScl);
        float maxRange = this.maxRange * fout;
        float realLength = WHUtils.findLaserLength(b, rotation, maxRange);
        Tmp.v1.trns(rotation, realLength);
        Tmp.v2.trns(rotation, 0.0F, this.width / 2.0F * fout);
        Tmp.v3.setZero();
        if (realLength < maxRange) {
            Tmp.v3.set(Tmp.v2).scl((maxRange - realLength) / maxRange);
        }

        if (this.lerpWhiteSine != 0.0F) {
            Tmp.c1.set(this.getColor(b)).lerp(Color.white, Mathf.absin(4.0F, this.lerpWhiteSine));
        } else {
            Tmp.c1.set(this.getColor(b));
        }

        Draw.z(100.01F);
        Draw.color(Tmp.c1);
        Fill.circle(b.x, b.y, this.width / 1.225F * fout);
        int[] var6 = Mathf.signs;
        int var7 = var6.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            int i = var6[var8];
            Drawn.tri(b.x, b.y, this.sideTriWidth * fout, this.sideTriRootLength - this.sideLengthDifference + (this.sideTriLength - this.sideLengthDifference) * fout, Time.time * 1.5F + (float)(90 * i));
            Drawn.tri(b.x, b.y, this.sideTriWidth * fout, this.sideTriRootLength + this.sideTriLength * fout, Time.time * -1.0F + (float)(90 * i));
        }

        Draw.color(Tmp.c1, Color.white, 0.55F);
        Fill.circle(b.x, b.y, this.width / 1.85F * fout);
        Draw.color(Color.white);
        Fill.circle(b.x, b.y, this.width / 2.125F * fout);
        Draw.z(100.0F);
        Draw.reset();
        Tmp.v2.scl(1.1F);
        Tmp.v3.scl(1.1F);
        Draw.alpha(0.5F);
        Fill.quad(b.x - Tmp.v2.x, b.y - Tmp.v2.y, b.x + Tmp.v2.x, b.y + Tmp.v2.y, b.x + Tmp.v1.x + Tmp.v3.x, b.y + Tmp.v1.y + Tmp.v3.y, b.x + Tmp.v1.x - Tmp.v3.x, b.y + Tmp.v1.y - Tmp.v3.y);
        if (realLength < maxRange) {
            Fill.circle(b.x + Tmp.v1.x, b.y + Tmp.v1.y, Tmp.v3.len());
        }

        Draw.color(Tmp.c1);
        Tmp.v2.scl(0.85F);
        Tmp.v3.scl(0.85F);
        Fill.quad(b.x - Tmp.v2.x, b.y - Tmp.v2.y, b.x + Tmp.v2.x, b.y + Tmp.v2.y, b.x + Tmp.v1.x + Tmp.v3.x, b.y + Tmp.v1.y + Tmp.v3.y, b.x + Tmp.v1.x - Tmp.v3.x, b.y + Tmp.v1.y - Tmp.v3.y);
        if (realLength < maxRange) {
            Fill.circle(b.x + Tmp.v1.x, b.y + Tmp.v1.y, Tmp.v3.len());
        }

        Draw.alpha(1.0F);
        Draw.color(Tmp.c2.set(Tmp.c1).lerp(Color.white, 0.3F));
        Tmp.v2.scl(0.5F);
        Fill.quad(b.x - Tmp.v2.x, b.y - Tmp.v2.y, b.x + Tmp.v2.x, b.y + Tmp.v2.y, b.x + (Tmp.v1.x + Tmp.v3.x) / 3.0F, b.y + (Tmp.v1.y + Tmp.v3.y) / 3.0F, b.x + (Tmp.v1.x - Tmp.v3.x) / 3.0F, b.y + (Tmp.v1.y - Tmp.v3.y) / 3.0F);
        Drawf.light(b.x, b.y, b.x + Tmp.v1.x, b.y + Tmp.v1.y, this.width * 1.5F, this.getColor(b), 0.7F);
        Draw.reset();
    }

    public float continuousDamage() {
        return this.damage / this.computeTick * 60.0F;
    }

    public void update(Bullet b) {
        if (b.timer.get(1, this.computeTick)) {
            float maxRange = this.maxRange * b.fout(this.fallScl) * Mathf.curve(b.fin(), 0.0F, this.fallScl);
            WHUtils.collideLine(b, b.team, Fx.none, b.x, b.y, this.dataRot ? b.fdata : b.rotation() + this.getRotation(b), maxRange, true, true);
        }

        if (this.dataRot) {
            Entityc var3 = b.owner;
            if (var3 instanceof Unit) {
                Unit u = (Unit)var3;
                if (Vars.net.active()) {
                    if (u.isPlayer()) {
                        Player player = (Player)u.controller();
                        b.fdata = Angles.moveToward(b.fdata, b.angleTo(player.mouseX, player.mouseY), 1.0F);
                    } else {
                        Teamc target = Units.bestTarget(b.team, b.x, b.y, this.range, (un) -> {
                            return un.type.canAttack;
                        }, Building::isValid, UnitSorts.strongest);
                        if (target != null) {
                            b.fdata = Angles.moveToward(b.fdata, b.angleTo(target), 1.0F);
                        }
                    }
                } else {
                    b.fdata = Angles.moveToward(b.fdata, b.angleTo(u.aimX, u.aimY), 1.0F);
                }
            }
        }

        if (this.hitShake > 0.0F) {
            Effect.shake(this.hitShake, this.hitShake, b);
        }

    }

    public void hit(Bullet b, float x, float y) {
        this.hitEffect.at(x, y, b.rotation() + this.getRotation(b), this.getColor(b));
    }
}
