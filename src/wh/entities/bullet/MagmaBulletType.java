//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.entities.bullet;

import arc.struct.IntSeq;
import arc.util.Tmp;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Fires;
import mindustry.entities.Puddles;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import wh.content.WHFx;
import wh.entities.WHDamage;
import wh.graphics.WHPal;

public class MagmaBulletType extends BulletType {
    public float radius;
    public float shake;
    public float crackRadius = -1.0F;
    public float groundRise = 4.0F;

    public MagmaBulletType(float damage, float radius) {
        super(0.001F, damage);
        this.radius = radius;
        this.hitEffect = Fx.fireballsmoke;
        this.despawnEffect = this.shootEffect = this.smokeEffect = Fx.none;
        this.lifetime = 16.0F;
        this.hitColor = WHPal.pop;
        this.makeFire = true;
        this.keepVelocity = this.backMove = false;
        this.hittable = this.absorbable = false;
        this.collides = this.collidesTiles = false;
        this.collidesGround = true;
        this.collidesAir = false;
        this.displayAmmoMultiplier = false;
        this.status = StatusEffects.melting;
    }

    public float continuousDamage() {
        return this.damage / 5.0F * 60.0F;
    }

    public float estimateDPS() {
        return this.damage * 100.0F / 5.0F * 3.0F;
    }

    public void init() {
        super.init();
        if (this.crackRadius < 0.0F) {
            this.crackRadius = this.radius * 2.0F;
        }

    }

    public void init(Bullet b) {
        super.init(b);
        b.data = new IntSeq();
    }

    public void update(Bullet b) {
        if (b.timer(1, 5.0F)) {
            Object var3 = b.data;
            if (var3 instanceof IntSeq) {
                IntSeq tiles = (IntSeq)var3;
                Damage.damage(b.team, b.x, b.y, this.radius * b.fout(), this.damage * b.damageMultiplier(), true, this.collidesAir, this.collidesGround);
                if (this.status != StatusEffects.none) {
                    Damage.status(b.team, b.x, b.y, this.radius * b.fout(), this.status, this.statusDuration, this.collidesAir, this.collidesGround);
                }

                Tmp.r1.setSize(this.radius * 2.0F * b.fout()).setCenter(b.x, b.y);
                Units.nearbyEnemies(b.team, Tmp.r1, (u) -> {
                    if (u.within(b, this.radius * b.fout())) {
                        if (this.puddleLiquid != null) {
                            Puddles.deposit(u.tileOn(), this.puddleLiquid, this.puddleAmount);
                        }

                        if (this.makeFire) {
                            Fires.create(u.tileOn());
                        }

                        tiles.add(u.tileOn().pos());
                    }

                });
                WHDamage.trueEachBlock(b.x, b.y, this.radius * b.fout(), (build) -> {
                    if (build.team != b.team) {
                        if (this.puddleLiquid != null) {
                            Puddles.deposit(build.tileOn(), this.puddleLiquid, this.puddleAmount);
                        }

                        if (this.makeFire) {
                            Fires.create(build.tileOn());
                        }

                    }
                });
                if (this.groundRise > 0.0F) {
                    WHDamage.trueEachTile(b.x, b.y, this.radius * b.fout(), (tile) -> {
                        if (tile != null && tiles.addUnique(tile.pos()) && tile.block() == Blocks.air && tile.overlay() == Blocks.air) {
                            WHFx.groundRise.at(tile, this.groundRise);
                        }

                    });
                }
            }
        }

        if (this.shake > 0.0F) {
            Effect.shake(this.shake, this.shake, b);
        }

    }

    public void draw(Bullet b) {
    }
}
