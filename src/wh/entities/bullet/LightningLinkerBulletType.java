//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.entities.bullet;

import arc.audio.Sound;
import arc.func.Cons;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Lightning;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import wh.content.WHFx;
import wh.graphics.PositionLightning;

public class LightningLinkerBulletType extends BasicBulletType {
    public static final Vec2 randVec = new Vec2();
    public float hitSpacing;
    public float size;
    public float linkRange;
    public float boltWidth;
    public float randomGenerateRange;
    public float randomGenerateChance;
    public float randomLightningChance;
    public int randomLightningNum;
    public Sound randomGenerateSound;
    public Cons<Position> hitModifier;
    public float range;
    public int maxHit;
    public int boltNum;
    public int effectLingtning;
    public float effectLightningChance;
    public float effectLightningLength;
    public float effectLightningLengthRand;
    public float trueHitChance;
    public boolean drawCircle;
    public Effect slopeEffect;
    public Effect liHitEffect;
    public Effect spreadEffect;

    public LightningLinkerBulletType(float speed, float damage) {
        super(speed, damage);
        this.hitSpacing = 10.0F;
        this.size = 30.0F;
        this.linkRange = 240.0F;
        this.boltWidth = 2.5F;
        this.randomGenerateRange = -1.0F;
        this.randomGenerateChance = 0.03F;
        this.randomLightningChance = 0.1F;
        this.randomLightningNum = 4;
        this.randomGenerateSound = Sounds.plasmaboom;
        this.hitModifier = (p) -> {
        };
        this.range = -1.0F;
        this.maxHit = 20;
        this.boltNum = 1;
        this.effectLingtning = 2;
        this.effectLightningChance = 0.35F;
        this.effectLightningLength = -1.0F;
        this.effectLightningLengthRand = -1.0F;
        this.trueHitChance = 0.66F;
        this.drawCircle = true;
        this.slopeEffect = Fx.none;
        this.liHitEffect = WHFx.lightningHitSmall;
        this.spreadEffect = Fx.none;
        this.collidesGround = this.collidesAir = true;
        this.collides = false;
        this.scaleLife = this.despawnHit = true;
        this.hitShake = 3.0F;
        this.hitSound = Sounds.explosion;
        this.shootEffect = Fx.shootBig;
        this.lightning = 4;
        this.lightningLength = 3;
        this.lightningLengthRand = 12;
        this.lightningCone = 360.0F;
        this.trailWidth = -1.0F;
    }

    public LightningLinkerBulletType() {
        this(1.0F, 1.0F);
    }

    public boolean testCollision(Bullet bullet, Building tile) {
        return super.testCollision(bullet, tile);
    }

    public float estimateDPS() {
        return this.lightningDamage * (float)this.maxHit * 0.75F * 60.0F / this.hitSpacing;
    }

    public void init() {
        super.init();
        if (this.slopeEffect == Fx.none) {
            this.slopeEffect = new Effect(25.0F, (e) -> {
                Object patt2256$temp = e.data;
                if (patt2256$temp instanceof Integer) {
                    Integer in = (Integer)patt2256$temp;
                    Draw.color(this.backColor);
                    Angles.randLenVectors((long)e.id, (int)(this.size / 8.0F), this.size / 4.0F + this.size * 2.0F * e.fin(), (x, y) -> {
                        Fill.circle(e.x + x, e.y + y, e.fout() * this.size / 1.65F);
                    });
                    Lines.stroke((in < 0 ? e.fin() : e.fout()) * 3.0F);
                    Lines.circle(e.x, e.y, (in > 0 ? e.fin() : e.fout()) * this.size * 1.1F);
                }
            });
        }

        if (this.spreadEffect == Fx.none) {
            this.spreadEffect = (new Effect(32.0F, (e) -> {
                Angles.randLenVectors((long)e.id, 2, 6.0F + 45.0F * e.fin(), (x, y) -> {
                    Draw.color(this.backColor);
                    Fill.circle(e.x + x, e.y + y, e.fout() * this.size / 2.0F);
                    Draw.color(this.frontColor);
                    Fill.circle(e.x + x, e.y + y, e.fout() * (this.size / 3.0F - 1.0F));
                });
            })).layer(110.00001F);
        }

        if (this.trailWidth < 0.0F) {
            this.trailWidth = this.size * 0.75F;
        }

        if (this.trailLength < 0) {
            this.trailLength = 12;
        }

        this.drawSize = Math.max(this.drawSize, this.size * 2.0F);
        if (this.effectLightningLength < 0.0F) {
            this.effectLightningLength = this.size * 1.5F;
        }

        if (this.effectLightningLengthRand < 0.0F) {
            this.effectLightningLengthRand = this.size * 2.0F;
        }

    }

    public void update(Bullet b) {
        super.update(b);
        Effect.shake(this.hitShake, this.hitShake, b);
        if (b.timer(4, this.hitSpacing)) {
            int[] var2 = Mathf.signs;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                int i = var2[var4];
                this.slopeEffect.at(b.x + Mathf.range(this.size / 4.0F), b.y + Mathf.range(this.size / 4.0F), b.rotation(), i);
            }

            this.spreadEffect.at(b);
            PositionLightning.setHitChance(this.trueHitChance);
            PositionLightning.createRange(b, this.collidesAir, this.collidesGround, b, b.team, this.linkRange, this.maxHit, this.backColor, Mathf.chanceDelta((double)this.randomLightningChance), this.lightningDamage, this.lightningLength, 2.5F, this.boltNum, (p) -> {
                this.liHitEffect.at(p.getX(), p.getY(), this.hitColor);
            });
            PositionLightning.setHitChanceDef();
        }

        if (this.randomGenerateRange > 0.0F && Mathf.chance((double)(Time.delta * this.randomGenerateChance)) && b.lifetime - b.time > PositionLightning.lifetime) {
            PositionLightning.createRandomRange(b, b.team, b, this.randomGenerateRange, this.backColor, Mathf.chanceDelta((double)this.randomLightningChance), 0.0F, 0, this.boltWidth, this.boltNum, this.randomLightningNum, (hitPos) -> {
                this.randomGenerateSound.at(hitPos, Mathf.random(0.9F, 1.1F));
                Damage.damage(b.team, hitPos.getX(), hitPos.getY(), this.splashDamageRadius / 8.0F, this.splashDamage * b.damageMultiplier() / 8.0F, this.collidesAir, this.collidesGround);
                WHFx.lightningHitLarge.at(hitPos.getX(), hitPos.getY(), this.lightningColor);
                this.hitModifier.get(hitPos);
            });
        }

        if (Mathf.chanceDelta((double)this.effectLightningChance) && b.lifetime - b.time > Fx.chainLightning.lifetime) {
            for(int i = 0; i < this.effectLingtning; ++i) {
                Vec2 v = randVec.rnd(this.effectLightningLength + Mathf.random(this.effectLightningLengthRand)).add(b).add(Tmp.v1.set(b.vel).scl(Fx.chainLightning.lifetime / 2.0F));
                Fx.chainLightning.at(b.x, b.y, 12.0F, this.backColor, v.cpy());
                WHFx.lightningHitSmall.at(v.x, v.y, 20.0F, this.backColor);
            }
        }

    }

    public void init(Bullet b) {
        super.init(b);
        b.vel.scl(1.0F + b.lifetime * this.drag * 28.0F / this.lifetime);
    }

    public void draw(Bullet b) {
        this.drawTrail(b);
        if (this.drawCircle) {
            Draw.color(this.backColor);
            Fill.circle(b.x, b.y, this.size);
            Draw.color(this.frontColor);
            Fill.circle(b.x, b.y, this.size / 7.0F + this.size / 3.0F * Mathf.curve(b.fout(), 0.1F, 0.35F));
        } else {
            super.draw(b);
        }

        Drawf.light(b.x, b.y, this.size * 1.85F, this.backColor, 0.7F);
    }

    public void despawned(Bullet b) {
        PositionLightning.createRandomRange(b, b.team, b, this.randomGenerateRange, this.backColor, Mathf.chanceDelta((double)this.randomLightningChance), 0.0F, 0, this.boltWidth, this.boltNum, this.randomLightningNum, (hitPos) -> {
            Damage.damage(b.team, hitPos.getX(), hitPos.getY(), this.splashDamageRadius, this.splashDamage * b.damageMultiplier(), this.collidesAir, this.collidesGround);
            WHFx.lightningHitLarge.at(hitPos.getX(), hitPos.getY(), this.lightningColor);
            this.liHitEffect.at(hitPos);

            for(int j = 0; j < this.lightning; ++j) {
                Lightning.create(b, this.lightningColor, this.lightningDamage < 0.0F ? this.damage : this.lightningDamage, b.x, b.y, b.rotation() + Mathf.range(this.lightningCone / 2.0F) + this.lightningAngle, this.lightningLength + Mathf.random(this.lightningLengthRand));
            }

            this.hitSound.at(hitPos, Mathf.random(0.9F, 1.1F));
            this.hitModifier.get(hitPos);
        });
        super.despawned(b);
    }
}
