//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.entities.bullet;

import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Healthc;
import mindustry.gen.Hitboxc;
import mindustry.gen.Sounds;
import wh.content.WHFx;
import wh.graphics.PositionLightning;

public class PositionLightningBulletType extends BulletType {
    public int boltNum;
    public float hitEffectRotation;

    public PositionLightningBulletType() {
        this(1.0F);
    }

    public PositionLightningBulletType(float damage) {
        super(1.0E-4F, damage);
        this.boltNum = 2;
        this.hitEffectRotation = 12.0F;
        this.scaleLife = true;
        this.hitShake = 2.0F;
        this.hitSound = Sounds.spark;
        this.absorbable = this.keepVelocity = false;
        this.instantDisappear = true;
        this.collides = false;
        this.collidesAir = this.collidesGround = true;
        this.lightning = 3;
        this.lightningDamage = damage;
        this.lightningLength = this.lightningLengthRand = 6;
        this.hitEffect = this.shootEffect = this.smokeEffect = WHFx.boolSelector;
        this.despawnEffect = Fx.none;
    }

    public void init() {
        super.init();
        this.drawSize = Math.max(this.drawSize, this.maxRange * 2.0F);
        if (this.hitEffect == WHFx.boolSelector) {
            this.hitEffect = WHFx.lightningHitLarge(this.lightningColor);
        }

        if (this.smokeEffect == WHFx.boolSelector) {
            this.smokeEffect = Fx.shootBigSmoke;
        }

        if (this.shootEffect == WHFx.boolSelector) {
            this.shootEffect = WHFx.shootLineSmall(this.lightningColor);
        }

    }

    public float range() {
        return this.maxRange;
    }

    public void init(Bullet b) {
        float length = b.lifetime * this.range() / this.lifetime;
        Healthc target = Damage.linecast(b, b.x, b.y, b.rotation(), length + 4.0F);
        b.data = target;
        if (target instanceof Hitboxc) {
            Hitboxc hit = (Hitboxc)target;
            hit.collision(b, hit.x(), hit.y());
            b.collision(hit, hit.x(), hit.y());
        } else if (target instanceof Building) {
            Building tile = (Building)target;
            if (tile.collide(b)) {
                tile.collision(b);
                this.hit(b, tile.x, tile.y);
            }
        }

        PositionLightning.createLength(b, b.team, b, length, b.rotation(), this.lightningColor, true, 0.0F, 0, 2.5F, this.boltNum, (p) -> {
            this.hitEffect.at(p.getX(), p.getY(), this.hitEffectRotation, this.hitColor);
            Effect.shake(this.hitShake, this.hitShake, p);
        });
        super.init(b);
    }

    public void despawned(Bullet b) {
        this.despawnEffect.at(b.x, b.y, b.rotation(), this.lightningColor);
    }

    public void hit(Bullet b) {
    }

    public void hit(Bullet b, float x, float y) {
    }

    public void draw(Bullet b) {
    }

    public void drawLight(Bullet b) {
    }
}
