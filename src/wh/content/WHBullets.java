//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.content;

import arc.func.Boolf;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.Tmp;
import java.util.Iterator;
import java.util.Objects;
import mindustry.Vars;
import mindustry.ai.BlockIndexer;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Lightning;
import mindustry.entities.Sized;
import mindustry.entities.Units;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.entities.bullet.FireBulletType;
import mindustry.entities.bullet.FlakBulletType;
import mindustry.entities.bullet.MissileBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Entityc;
import mindustry.gen.Groups;
import mindustry.gen.Healthc;
import mindustry.gen.Hitboxc;
import mindustry.gen.Sounds;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import wh.entities.bullet.AccelBulletType;
import wh.entities.bullet.BoidBulletType;
import wh.entities.bullet.DOTBulletType;
import wh.entities.bullet.EffectBulletType;
import wh.entities.bullet.LightningLinkerBulletType;
import wh.entities.bullet.ShieldBreakerType;
import wh.entities.bullet.StrafeLaserBulletType;
import wh.entities.bullet.TrailFadeBulletType;
import wh.entities.effect.WrapperEffect;
import wh.gen.UltFire;
import wh.gen.WHSounds;
import wh.graphics.Drawn;
import wh.graphics.PositionLightning;
import wh.graphics.WHPal;
import wh.math.WHInterp;
import wh.util.WHUtils;

public final class WHBullets {
    public static BulletType basicMissile;
    public static BulletType boidMissle;
    public static BulletType sapArtilleryFrag;
    public static BulletType continuousSapLaser;
    public static BulletType ancientArtilleryProjectile;
    public static BulletType hitter;
    public static BulletType ncBlackHole;
    public static BulletType nuBlackHole;
    public static BulletType executor;
    public static BulletType ultFireball;
    public static BulletType basicSkyFrag;
    public static BulletType annMissile;
    public static BulletType vastBulletStrafeLaser;
    public static BulletType vastBulletAccel;
    public static BulletType vastBulletLightningBall;
    public static BulletType hyperBlast;
    public static BulletType hyperBlastLinker;
    public static BulletType arc9000frag;
    public static BulletType arc9000;
    public static BulletType arc9000hyper;
    public static BulletType cMoonExplosion;
    public static BulletType AGFrag;
    public static BulletType tankAG7;
    public static BulletType collaspsePf;
    public static BulletType collapseSp;

    private WHBullets() {
    }

    public static void load() {
        basicMissile = new MissileBulletType(4.2F, 15.0F) {
            {
                this.homingPower = 0.12F;
                this.width = 8.0F;
                this.height = 8.0F;
                this.shrinkX = this.shrinkY = 0.0F;
                this.drag = -0.003F;
                this.homingRange = 80.0F;
                this.keepVelocity = false;
                this.splashDamageRadius = 35.0F;
                this.splashDamage = 30.0F;
                this.lifetime = 62.0F;
                this.trailColor = Pal.missileYellowBack;
                this.hitEffect = Fx.blastExplosion;
                this.despawnEffect = Fx.blastExplosion;
                this.weaveScale = 8.0F;
                this.weaveMag = 2.0F;
            }
        };
        sapArtilleryFrag = new ArtilleryBulletType(2.3F, 30.0F) {
            {
                this.hitEffect = Fx.sapExplosion;
                this.knockback = 0.8F;
                this.lifetime = 70.0F;
                this.width = this.height = 20.0F;
                this.collidesTiles = false;
                this.splashDamageRadius = 70.0F;
                this.splashDamage = 60.0F;
                this.backColor = Pal.sapBulletBack;
                this.frontColor = this.lightningColor = Pal.sapBullet;
                this.lightning = 2;
                this.lightningLength = 5;
                this.smokeEffect = Fx.shootBigSmoke2;
                this.hitShake = 5.0F;
                this.lightRadius = 30.0F;
                this.lightColor = Pal.sap;
                this.lightOpacity = 0.5F;
                this.status = StatusEffects.sapped;
                this.statusDuration = 600.0F;
            }
        };
        boidMissle = new BoidBulletType(2.7F, 30.0F) {
            {
                this.damage = 50.0F;
                this.homingPower = 0.02F;
                this.lifetime = 500.0F;
                this.keepVelocity = false;
                this.shootEffect = Fx.shootHeal;
                this.smokeEffect = Fx.hitLaser;
                this.hitEffect = this.despawnEffect = Fx.hitLaser;
                this.hitSound = Sounds.none;
                this.healPercent = 5.5F;
                this.collidesTeam = true;
                this.trailColor = Pal.heal;
                this.backColor = Pal.heal;
            }
        };
        continuousSapLaser = new ContinuousLaserBulletType(60.0F) {
            {
                this.colors = new Color[]{Pal.sapBulletBack.cpy().a(0.3F), Pal.sapBullet.cpy().a(0.6F), Pal.sapBullet, Color.white};
                this.length = 190.0F;
                this.width = 5.0F;
                this.shootEffect = WHFx.sapPlasmaShoot;
                this.hitColor = this.lightColor = this.lightningColor = Pal.sapBullet;
                this.hitEffect = WHFx.coloredHitSmall;
                this.status = StatusEffects.sapped;
                this.statusDuration = 80.0F;
                this.lifetime = 180.0F;
                this.incendChance = 0.0F;
                this.largeHit = false;
            }

            public void hitTile(Bullet b, Building build, float x, float y, float initialHealth, boolean direct) {
                super.hitTile(b, build, x, y, initialHealth, direct);
                Entityc var8 = b.owner;
                if (var8 instanceof Healthc) {
                    Healthc owner = (Healthc)var8;
                    owner.heal(Math.max(initialHealth - build.health(), 0.0F) * 0.2F);
                }

            }

            public void hitEntity(Bullet b, Hitboxc entity, float health) {
                super.hitEntity(b, entity, health);
                if (entity instanceof Healthc) {
                    Healthc h = (Healthc)entity;
                    Entityc var6 = b.owner;
                    if (var6 instanceof Healthc) {
                        Healthc owner = (Healthc)var6;
                        owner.heal(Math.max(health - h.health(), 0.0F) * 0.2F);
                    }
                }

            }
        };
        ancientArtilleryProjectile = new ShieldBreakerType(7.0F, 6000.0F, "missile-large", 7000.0F) {
            {
                this.backColor = this.trailColor = this.lightColor = this.lightningColor = this.hitColor = WHPal.ancientLightMid;
                this.frontColor = WHPal.ancientLight;
                this.trailEffect = WHFx.hugeTrail;
                this.trailParam = 6.0F;
                this.trailChance = 0.2F;
                this.trailInterval = 3.0F;
                this.lifetime = 200.0F;
                this.scaleLife = true;
                this.trailWidth = 5.0F;
                this.trailLength = 55;
                this.trailInterp = Interp.slope;
                this.lightning = 6;
                this.lightningLength = this.lightningLengthRand = 22;
                this.splashDamage = this.damage;
                this.lightningDamage = this.damage / 15.0F;
                this.splashDamageRadius = 120.0F;
                this.scaledSplashDamage = true;
                this.despawnHit = true;
                this.collides = false;
                this.shrinkY = this.shrinkX = 0.33F;
                this.width = 17.0F;
                this.height = 55.0F;
                this.despawnShake = this.hitShake = 12.0F;
                this.hitEffect = new MultiEffect(new Effect[]{WHFx.square(this.hitColor, 200.0F, 20, this.splashDamageRadius + 80.0F, 10.0F), WHFx.lightningHitLarge, WHFx.hitSpark(this.hitColor, 130.0F, 85, this.splashDamageRadius * 1.5F, 2.2F, 10.0F), WHFx.subEffect(140.0F, this.splashDamageRadius + 12.0F, 33, 34.0F, Interp.pow2Out, (i, x, y, rot, fin) -> {
                    float fout = Interp.pow2Out.apply(1.0F - fin);
                    int[] var7 = Mathf.signs;
                    int var8 = var7.length;

                    for(int var9 = 0; var9 < var8; ++var9) {
                        int s = var7[var9];
                        Drawf.tri(x, y, 12.0F * fout, 45.0F * Mathf.curve(fin, 0.0F, 0.1F) * WHFx.fout(fin, 0.25F), rot + (float)(s * 90));
                    }

                })});
                this.despawnEffect = WHFx.circleOut(145.0F, this.splashDamageRadius + 15.0F, 3.0F);
                this.shootEffect = WrapperEffect.wrap(WHFx.missileShoot, this.hitColor);
                this.smokeEffect = WHFx.instShoot(this.hitColor, this.frontColor);
                this.despawnSound = this.hitSound = Sounds.largeExplosion;
                this.fragBullets = 22;
                this.fragBullet = new BasicBulletType(2.0F, 300.0F, "wh-circle-bolt") {
                    {
                        this.width = this.height = 10.0F;
                        this.shrinkY = this.shrinkX = 0.7F;
                        this.backColor = this.trailColor = this.lightColor = this.lightningColor = this.hitColor = WHPal.ancientLightMid;
                        this.frontColor = WHPal.ancientLight;
                        this.trailEffect = Fx.missileTrail;
                        this.trailParam = 3.5F;
                        this.splashDamage = 80.0F;
                        this.splashDamageRadius = 40.0F;
                        this.lifetime = 18.0F;
                        this.lightning = 2;
                        this.lightningLength = this.lightningLengthRand = 4;
                        this.lightningDamage = 30.0F;
                        this.hitSoundVolume /= 2.2F;
                        this.despawnShake = this.hitShake = 4.0F;
                        this.despawnSound = this.hitSound = Sounds.dullExplosion;
                        this.trailWidth = 5.0F;
                        this.trailLength = 35;
                        this.trailInterp = Interp.slope;
                        this.despawnEffect = WHFx.blast(this.hitColor, 40.0F);
                        this.hitEffect = WHFx.hitSparkHuge;
                    }
                };
                this.fragLifeMax = 5.0F;
                this.fragLifeMin = 1.5F;
                this.fragVelocityMax = 2.0F;
                this.fragVelocityMin = 0.35F;
            }
        };
        hitter = new EffectBulletType(15.0F) {
            {
                this.speed = 0.0F;
                this.hittable = false;
                this.scaledSplashDamage = true;
                this.collidesTiles = this.collidesGround = this.collides = this.collidesAir = true;
                this.lightningDamage = 1000.0F;
                this.lightColor = this.lightningColor = this.trailColor = this.hitColor = WHPal.pop;
                this.lightning = 5;
                this.lightningLength = 12;
                this.lightningLengthRand = 16;
                this.splashDamageRadius = 60.0F;
                this.hitShake = this.despawnShake = 20.0F;
                this.hitSound = this.despawnSound = Sounds.explosionbig;
                this.hitEffect = this.despawnEffect = new MultiEffect(new Effect[]{WHFx.square45_8_45, WHFx.hitSparkHuge, WHFx.crossBlast_45});
            }

            public void despawned(Bullet b) {
                if (this.despawnHit) {
                    this.hit(b);
                } else {
                    this.createUnits(b, b.x, b.y);
                }

                if (!this.fragOnHit) {
                    this.createFrags(b, b.x, b.y);
                }

                this.despawnEffect.at(b.x, b.y, b.rotation(), this.lightColor);
                this.despawnSound.at(b);
                Effect.shake(this.despawnShake, this.despawnShake, b);
            }

            public void hit(Bullet b, float x, float y) {
                this.hitEffect.at(x, y, b.rotation(), this.lightColor);
                this.hitSound.at(x, y, this.hitSoundPitch, this.hitSoundVolume);
                Effect.shake(this.hitShake, this.hitShake, b);
                if (this.fragOnHit) {
                    this.createFrags(b, x, y);
                }

                this.createPuddles(b, x, y);
                this.createIncend(b, x, y);
                this.createUnits(b, x, y);
                if (this.suppressionRange > 0.0F) {
                    Damage.applySuppression(b.team, b.x, b.y, this.suppressionRange, this.suppressionDuration, 0.0F, this.suppressionEffectChance, new Vec2(b.x, b.y));
                }

                this.createSplashDamage(b, x, y);

                for(int i = 0; i < this.lightning; ++i) {
                    Lightning.create(b, this.lightColor, this.lightningDamage < 0.0F ? this.damage : this.lightningDamage, b.x, b.y, b.rotation() + Mathf.range(this.lightningCone / 2.0F) + this.lightningAngle, this.lightningLength + Mathf.random(this.lightningLengthRand));
                }

            }
        };
        ncBlackHole = new EffectBulletType(120.0F) {
            {
                this.despawnHit = true;
                this.splashDamageRadius = 240.0F;
                this.hittable = false;
                this.lightColor = WHPal.pop;
                this.lightningDamage = 1000.0F;
                this.lightning = 2;
                this.lightningLength = 4;
                this.lightningLengthRand = 8;
                this.scaledSplashDamage = true;
                this.collidesAir = this.collidesGround = this.collidesTiles = true;
            }

            public void draw(Bullet b) {
                if (b.data instanceof Seq) {
                    Seq<Sized> data = (Seq)b.data;
                    Draw.color(this.lightColor, Color.white, b.fin() * 0.7F);
                    Draw.alpha(b.fin(Interp.pow3Out) * 1.1F);
                    Lines.stroke(2.0F * b.fout());
                    Iterator var3 = data.iterator();

                    while(var3.hasNext()) {
                        Sized s = (Sized)var3.next();
                        if (s instanceof Building) {
                            Fill.square(s.getX(), s.getY(), s.hitSize() / 2.0F);
                        } else {
                            Lines.spikes(s.getX(), s.getY(), s.hitSize() * (0.5F + b.fout() * 2.0F), s.hitSize() / 2.0F * b.fslope() + 12.0F * b.fin(), 4, 45.0F);
                        }
                    }

                    Drawf.light(b.x, b.y, b.fdata, this.lightColor, 0.3F + b.fin() * 0.8F);
                }
            }

            public void hitT(Sized target, Entityc o, Team team, float x, float y) {
                for(int i = 0; i < this.lightning; ++i) {
                    Lightning.create(team, this.lightColor, this.lightningDamage, x, y, (float)Mathf.random(360), this.lightningLength + Mathf.random(this.lightningLengthRand));
                }

                if (target instanceof Unit) {
                    Unit unit = (Unit)target;
                    if (unit.health > 1000.0F) {
                        WHBullets.hitter.create(o, team, x, y, 0.0F);
                    }
                }

            }

            @Override
            public void update(Bullet b){
                super.update(b);

                if(!(b.data instanceof Seq))return;
                Seq<Sized> data = (Seq<Sized>)b.data;
                data.remove(d -> !((Healthc)d).isValid());
            }


            public void despawned(Bullet b) {
                super.despawned(b);
                float rad = 33.0F;
                Vec2 v = (new Vec2()).set(b);

                for(int i = 0; i < 5; ++i) {
                    Time.run((float)i * 0.35F + (float)Mathf.random(2), () -> {
                        Tmp.v1.rnd(rad / 3.0F).scl(Mathf.random());
                        WHFx.shuttle.at(v.x + Tmp.v1.x, v.y + Tmp.v1.y, Tmp.v1.angle(), this.lightColor, Mathf.random(rad * 3.0F, rad * 12.0F));
                    });
                }

                if (b.data instanceof Seq) {
                    Entityc o = b.owner();
                    Seq<Sized> data = (Seq)b.data;
                    Iterator var6 = data.iterator();

                    while(var6.hasNext()) {
                        Sized s = (Sized)var6.next();
                        float size = Math.min(s.hitSize(), 85.0F);
                        Time.run((float)Mathf.random(44), () -> {
                            if (Mathf.chance(0.2) || data.size < 8) {
                                WHFx.shuttle.at(s.getX(), s.getY(), 45.0F, this.lightColor, Mathf.random(size * 3.0F, size * 12.0F));
                            }

                            this.hitT(s, o, b.team, s.getX(), s.getY());
                        });
                    }

                    this.createSplashDamage(b, b.x, b.y);
                }
            }
            @Override
            public void init(Bullet b){
                super.init(b);
                b.fdata = splashDamageRadius;

                Seq<Sized> data = new Seq<>();

                Vars.indexer.eachBlock(null, b.x, b.y, b.fdata, bu -> bu.team != b.team, data::add);

                Groups.unit.intersect(b.x - b.fdata / 2, b.y - b.fdata / 2, b.fdata, b.fdata, u -> {
                    if(u.team != b.team)data.add(u);
                });

                b.data = data;

            }
            };
        nuBlackHole = new EffectBulletType(20.0F) {
            {
                this.despawnHit = true;
                this.hitColor = WHPal.pop;
                this.splashDamageRadius = 36.0F;
                this.lightningDamage = 600.0F;
                this.lightning = 2;
                this.lightningLength = 6;
                this.lightningLengthRand = 4;
                this.scaledSplashDamage = false;
                this.collidesAir = this.collidesGround = this.collidesTiles = true;
                this.splashDamage = 200.0F;
                this.damage = 1000.0F;
            }

            public void draw(Bullet b) {
                if (b.data instanceof Seq) {
                    Seq<Sized> data = (Seq)b.data;
                    Draw.color(b.team.color, Color.white, b.fin() * 0.7F);
                    Draw.alpha(b.fin(Interp.pow3Out) * 1.1F);
                    Lines.stroke(2.0F * b.fout());
                    Iterator var3 = data.iterator();

                    while(var3.hasNext()) {
                        Sized s = (Sized)var3.next();
                        if (s instanceof Building) {
                            Fill.square(s.getX(), s.getY(), s.hitSize() / 2.0F);
                        } else {
                            Lines.spikes(s.getX(), s.getY(), s.hitSize() * (0.5F + b.fout() * 2.0F), s.hitSize() / 2.0F * b.fslope() + 12.0F * b.fin(), 4, 45.0F);
                        }
                    }

                    Drawf.light(b.x, b.y, b.fdata, this.hitColor, 0.3F + b.fin() * 0.8F);
                }
            }

            public void hitT(Entityc o, Team team, float x, float y) {
                for(int i = 0; i < this.lightning; ++i) {
                    Lightning.create(team, team.color, this.lightningDamage, x, y, (float)Mathf.random(360), this.lightningLength + Mathf.random(this.lightningLengthRand));
                }

                WHBullets.hitter.create(o, team, x, y, 0.0F, 500.0F, 1.0F, 1.0F, (Object)null);
            }

            public void update(Bullet b) {
                super.update(b);
                if (b.data instanceof Seq && !b.timer(0, 10.0F)) {
                    Seq<Sized> data = (Seq)b.data;
                    data.remove((d) -> {
                        return !((Healthc)d).isValid();
                    });
                }
            }

            public void despawned(Bullet b) {
                super.despawned(b);
                float rad = 20.0F;
                if (b.data instanceof Seq) {
                    Entityc o = b.owner();
                    Seq<Sized> data = (Seq)b.data;

                    Sized s;
                    for(Iterator var5 = data.iterator(); var5.hasNext(); this.hitT(o, b.team, s.getX(), s.getY())) {
                        s = (Sized)var5.next();
                        float size = Math.min(s.hitSize(), 75.0F);
                        if (Mathf.chance(0.2) || data.size < 8) {
                            float sd = Mathf.random(size * 3.0F, size * 12.0F);
                            WHFx.shuttleDark.at(s.getX() + Mathf.range(size), s.getY() + Mathf.range(size), 45.0F, b.team.color, sd);
                        }
                    }

                    this.createSplashDamage(b, b.x, b.y);
                }
            }


            @Override
            public void init(Bullet b){
                super.init(b);
                b.fdata = splashDamageRadius;

                Seq<Sized> data = new Seq<>();

                Vars.indexer.eachBlock(null, b.x, b.y, b.fdata, bu -> bu.team != b.team, data::add);

                Groups.unit.intersect(b.x - b.fdata / 2, b.y - b.fdata / 2, b.fdata, b.fdata, u -> {
                    if(u.team != b.team)data.add(u);
                });

                b.data = data;

            }
        };
        ultFireball = new FireBulletType(1.0F, 10.0F) {
            {
                this.colorFrom = this.colorMid = Pal.techBlue;
                this.lifetime = 12.0F;
                this.radius = 4.0F;
                this.trailEffect = WHFx.ultFireBurn;
            }

            public void draw(Bullet b) {
                Draw.color(this.colorFrom, this.colorMid, this.colorTo, b.fin());
                Fill.square(b.x, b.y, this.radius * b.fout(), 45.0F);
                Draw.reset();
            }

            public void update(Bullet b) {
                if (Mathf.chanceDelta((double)this.fireTrailChance)) {
                    UltFire.create(b.tileOn());
                }

                if (Mathf.chanceDelta((double)this.fireEffectChance)) {
                    this.trailEffect.at(b.x, b.y);
                }

                if (Mathf.chanceDelta((double)this.fireEffectChance2)) {
                    this.trailEffect2.at(b.x, b.y);
                }

            }
        };
        executor = new TrailFadeBulletType(28.0F, 1800.0F) {
            {
                this.lifetime = 40.0F;
                this.trailLength = 90;
                this.trailWidth = 3.6F;
                this.tracers = 2;
                this.tracerFadeOffset = 20;
                this.keepVelocity = true;
                this.tracerSpacing = 10.0F;
                this.tracerUpdateSpacing *= 1.25F;
                this.removeAfterPierce = false;
                this.hitColor = this.backColor = this.lightColor = this.lightningColor = WHPal.ancient;
                this.trailColor = WHPal.ancientLightMid;
                this.frontColor = WHPal.ancientLight;
                this.width = 18.0F;
                this.height = 60.0F;
                this.homingPower = 0.01F;
                this.homingRange = 300.0F;
                this.homingDelay = 5.0F;
                this.hitSound = Sounds.plasmaboom;
                this.despawnShake = this.hitShake = 18.0F;
                this.statusDuration = 1200.0F;
                this.pierce = this.pierceArmor = this.pierceBuilding = true;
                this.lightning = 3;
                this.lightningLength = 6;
                this.lightningLengthRand = 18;
                this.lightningDamage = 400.0F;
                this.smokeEffect = WrapperEffect.wrap(WHFx.hitSparkHuge, this.hitColor);
                this.shootEffect = WHFx.instShoot(this.backColor, this.frontColor);
                this.despawnEffect = WHFx.lightningHitLarge;
                this.hitEffect = new MultiEffect(new Effect[]{WHFx.hitSpark(this.backColor, 75.0F, 24, 90.0F, 2.0F, 12.0F), WHFx.square45_6_45, WHFx.lineCircleOut(this.backColor, 18.0F, 20.0F, 2.0F), WHFx.sharpBlast(this.backColor, this.frontColor, 120.0F, 40.0F)});
            }

            public void createFrags(Bullet b, float x, float y) {
                super.createFrags(b, x, y);
                WHBullets.nuBlackHole.create(b, x, y, 0.0F);
            }
        };
        basicSkyFrag = new BasicBulletType(3.8F, 50.0F) {
            {
                this.speed = 6.0F;
                this.trailLength = 12;
                this.trailWidth = 2.0F;
                this.lifetime = 60.0F;
                this.despawnEffect = WHFx.square45_4_45;
                this.knockback = 4.0F;
                this.width = 15.0F;
                this.height = 37.0F;
                this.lightningDamage = this.damage * 0.65F;
                this.backColor = this.lightColor = this.lightningColor = this.trailColor = this.hitColor = this.frontColor = Pal.techBlue;
                this.lightning = 2;
                this.lightningLength = this.lightningLengthRand = 3;
                this.smokeEffect = Fx.shootBigSmoke2;
                this.trailChance = 0.2F;
                this.trailEffect = WHFx.skyTrail;
                this.drag = 0.015F;
                this.hitShake = 2.0F;
                this.hitSound = Sounds.explosion;
                this.hitEffect = new Effect(45.0F, (e) -> {
                    Fx.rand.setSeed((long)e.id);
                    Draw.color(this.lightColor, e.fin());
                    Lines.stroke(1.75F * e.fout());
                    Lines.spikes(e.x, e.y, (float)Fx.rand.random(14, 28) * e.finpow(), (float)Fx.rand.random(1, 5) * e.fout() + (float)Fx.rand.random(5, 8) * e.fin(WHInterp.parabola4Reversed), 4, 45.0F);
                    Lines.square(e.x, e.y, (float)Fx.rand.random(4, 14) * e.fin(Interp.pow3Out), 45.0F);
                });
            }

            public void hit(Bullet b) {
                super.hit(b);
                UltFire.createChance(b, 12.0F, 0.0075F);
            }
        };
        annMissile = new BasicBulletType(5.6F, 80.0F, "wh-strike") {
            {
                this.trailColor = this.lightningColor = this.backColor = this.lightColor = this.frontColor = Pal.techBlue;
                this.lightning = 3;
                this.lightningCone = 360.0F;
                this.lightningLengthRand = this.lightningLength = 9;
                this.splashDamageRadius = 60.0F;
                this.splashDamage = this.lightningDamage = this.damage * 0.7F;
                this.range = 320.0F;
                this.scaleLife = true;
                this.width = 12.0F;
                this.height = 30.0F;
                this.trailLength = 15;
                this.drawSize = 250.0F;
                this.trailParam = 1.4F;
                this.trailChance = 0.35F;
                this.lifetime = 50.0F;
                this.homingDelay = 10.0F;
                this.homingPower = 0.05F;
                this.homingRange = 150.0F;
                this.hitEffect = WHFx.lightningHitLarge(this.lightColor);
                this.shootEffect = WHFx.hugeSmokeGray;
                this.smokeEffect = new Effect(45.0F, (e) -> {
                    Draw.color(this.lightColor, Color.white, e.fout() * 0.7F);
                    Angles.randLenVectors((long)e.id, 8, 5.0F + 55.0F * e.fin(), e.rotation, 45.0F, (x, y) -> {
                        Fill.circle(e.x + x, e.y + y, e.fout() * 3.0F);
                    });
                });
                this.despawnEffect = new Effect(32.0F, (e) -> {
                    Draw.color(Color.gray);
                    Angles.randLenVectors((long)(e.id + 1), 8, 2.0F + 30.0F * e.finpow(), (x, y) -> {
                        Fill.circle(e.x + x, e.y + y, e.fout() * 4.0F + 0.5F);
                    });
                    Draw.color(this.lightColor, Color.white, e.fin());
                    Lines.stroke(e.fout() * 2.0F);
                    Fill.circle(e.x, e.y, e.fout() * e.fout() * 13.0F);
                    Angles.randLenVectors((long)e.id, 4, 7.0F + 40.0F * e.fin(), (x, y) -> {
                        Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 8.0F + 3.0F);
                    });
                });
            }
        };
        vastBulletAccel = new AccelBulletType(2.0F, 180.0F) {
            {
                this.width = 22.0F;
                this.height = 40.0F;
                this.velocityBegin = 1.0F;
                this.velocityIncrease = 11.0F;
                this.accelInterp = WHInterp.inOut;
                this.accelerateBegin = 0.045F;
                this.accelerateEnd = 0.675F;
                this.pierceCap = 3;
                this.splashDamage = this.damage / 4.0F;
                this.splashDamageRadius = 24.0F;
                this.trailLength = 30;
                this.trailWidth = 3.0F;
                this.lifetime = 160.0F;
                this.trailEffect = WHFx.trailFromWhite;
                this.pierceArmor = true;
                this.trailRotation = false;
                this.trailChance = 0.35F;
                this.trailParam = 4.0F;
                this.homingRange = 640.0F;
                this.homingPower = 0.075F;
                this.homingDelay = 5.0F;
                this.lightning = 3;
                this.lightningLengthRand = 10;
                this.lightningLength = 5;
                this.lightningDamage = this.damage / 4.0F;
                this.shootEffect = this.smokeEffect = Fx.none;
                this.hitEffect = this.despawnEffect = new MultiEffect(new Effect[]{new Effect(65.0F, (b) -> {
                    Draw.color(b.color);
                    Fill.circle(b.x, b.y, 6.0F * b.fout(Interp.pow3Out));
                    Angles.randLenVectors((long)b.id, 6, 35.0F * b.fin() + 5.0F, (x, y) -> {
                        Fill.circle(b.x + x, b.y + y, 4.0F * b.fout(Interp.pow2Out));
                    });
                }), WHFx.hitSparkLarge});
                this.despawnHit = false;
                this.rangeOverride = 480.0F;
            }

            public void update(Bullet b) {
                super.update(b);
            }

            public void updateTrailEffects(Bullet b) {
                if (this.trailChance > 0.0F && Mathf.chanceDelta((double)this.trailChance)) {
                    this.trailEffect.at(b.x, b.y, this.trailRotation ? b.rotation() : this.trailParam, b.team.color);
                }

                if (this.trailInterval > 0.0F && b.timer(0, this.trailInterval)) {
                    this.trailEffect.at(b.x, b.y, this.trailRotation ? b.rotation() : this.trailParam, b.team.color);
                }

            }

            public void hit(Bullet b, float x, float y) {
                b.hit = true;
                this.hitEffect.at(x, y, b.rotation(), b.team.color);
                this.hitSound.at(x, y, this.hitSoundPitch, this.hitSoundVolume);
                Effect.shake(this.hitShake, this.hitShake, b);
                if (this.splashDamageRadius > 0.0F && !b.absorbed) {
                    Damage.damage(b.team, x, y, this.splashDamageRadius, this.splashDamage * b.damageMultiplier(), this.collidesAir, this.collidesGround);
                    if (this.status != StatusEffects.none) {
                        Damage.status(b.team, x, y, this.splashDamageRadius, this.status, this.statusDuration, this.collidesAir, this.collidesGround);
                    }
                }

                for(int i = 0; i < this.lightning; ++i) {
                    Lightning.create(b, b.team.color, this.lightningDamage < 0.0F ? this.damage : this.lightningDamage, b.x, b.y, b.rotation() + Mathf.range(this.lightningCone / 2.0F) + this.lightningAngle, this.lightningLength + Mathf.random(this.lightningLengthRand));
                }

                Entityc var5 = b.owner;
                if (var5 instanceof Unit) {
                    Unit from = (Unit)var5;
                    if (!from.dead && from.isAdded() && !(from.healthf() > 0.99F)) {
                        WHFx.chainLightningFade.at(b.x, b.y, (float)Mathf.random(12, 20), b.team.color, from);
                        from.heal(this.damage / 8.0F);
                    }
                }
            }

            public void despawned(Bullet b) {
                this.despawnEffect.at(b.x, b.y, b.rotation(), b.team.color);
                Effect.shake(this.despawnShake, this.despawnShake, b);
            }

            public void removed(Bullet b) {
                if (this.trailLength > 0 && b.trail != null && b.trail.size() > 0) {
                    Fx.trailFade.at(b.x, b.y, this.trailWidth, b.team.color, b.trail.copy());
                }

            }

            public void init(Bullet b) {
                super.init(b);
                b.vel.rotate((float)WHUtils.rand((long)b.id).random(360));
            }

            public void draw(Bullet b) {
                Tmp.c1.set(b.team.color).lerp(Color.white, Mathf.absin(4.0F, 0.3F));
                if (this.trailLength > 0 && b.trail != null) {
                    float z = Draw.z();
                    Draw.z(z - 0.01F);
                    b.trail.draw(Tmp.c1, this.trailWidth);
                    Draw.z(z);
                }

                Draw.color(b.team.color, Color.white, 0.35F);
                Drawn.arrow(b.x, b.y, 5.0F, 35.0F, -6.0F, b.rotation());
                Draw.color(Tmp.c1);
                Drawn.arrow(b.x, b.y, 5.0F, 35.0F, 12.0F, b.rotation());
                Draw.reset();
            }
        };
        vastBulletLightningBall = new LightningLinkerBulletType(3.0F, 120.0F) {
            public final Effect RshootEffect;
            public final Effect RsmokeEffect;

            {
                this.lifetime = 120.0F;
                this.keepVelocity = false;
                this.lightningDamage = this.damage = this.splashDamage = 80.0F;
                this.splashDamageRadius = 50.0F;
                this.homingDelay = 20.0F;
                this.homingRange = 300.0F;
                this.homingPower = 0.025F;
                this.smokeEffect = this.shootEffect = Fx.none;
                this.effectLingtning = 0;
                this.maxHit = 6;
                this.hitShake = this.despawnShake = 5.0F;
                this.hitSound = this.despawnSound = Sounds.plasmaboom;
                this.size = 7.2F;
                this.trailWidth = 3.0F;
                this.trailLength = 16;
                this.linkRange = 80.0F;
                this.scaleLife = false;
                this.despawnHit = false;
                this.collidesAir = this.collidesGround = true;
                this.despawnEffect = this.hitEffect = new MultiEffect(new Effect[]{WHFx.lightningHitLarge, WHFx.hitSparkHuge});
                this.trailEffect = this.slopeEffect = WHFx.trailFromWhite;
                this.spreadEffect = (new Effect(32.0F, (e) -> {
                    Angles.randLenVectors((long)e.id, 2, 6.0F + 45.0F * e.fin(), (x, y) -> {
                        Draw.color(e.color);
                        Fill.circle(e.x + x, e.y + y, e.fout() * this.size / 2.0F);
                        Draw.color(Color.black);
                        Fill.circle(e.x + x, e.y + y, e.fout() * (this.size / 3.0F - 1.0F));
                    });
                })).layer(110.00001F);
                this.RshootEffect = new Effect(24.0F, (e) -> {
                    e.scaled(10.0F, (b) -> {
                        Draw.color(e.color);
                        Lines.stroke(b.fout() * 3.0F + 0.2F);
                        Lines.circle(b.x, b.y, b.fin() * 70.0F);
                    });
                    Draw.color(e.color);
                    int[] var1 = Mathf.signs;
                    int var2 = var1.length;

                    int var3;
                    int i;
                    for(var3 = 0; var3 < var2; ++var3) {
                        i = var1[var3];
                        Drawn.tri(e.x, e.y, 8.0F * e.fout(), 85.0F, e.rotation + 90.0F * (float)i);
                    }

                    Draw.color(Color.black);
                    var1 = Mathf.signs;
                    var2 = var1.length;

                    for(var3 = 0; var3 < var2; ++var3) {
                        i = var1[var3];
                        Drawn.tri(e.x, e.y, 3.0F * e.fout(), 38.0F, e.rotation + 90.0F * (float)i);
                    }

                });
                this.RsmokeEffect = WHFx.hitSparkLarge;
            }

            public Color getColor(Bullet b) {
                return Tmp.c1.set(b.team.color).lerp(Color.white, 0.1F + Mathf.absin(4.0F, 0.15F));
            }

            public void update(Bullet b) {
                this.updateTrail(b);
                this.updateHoming(b);
                this.updateWeaving(b);
                this.updateBulletInterval(b);
                Effect.shake(this.hitShake, this.hitShake, b);
                if (b.timer(5, this.hitSpacing)) {
                    this.slopeEffect.at(b.x + Mathf.range(this.size / 4.0F), b.y + Mathf.range(this.size / 4.0F), Mathf.random(2.0F, 4.0F), b.team.color);
                    this.spreadEffect.at(b.x, b.y, b.team.color);
                    PositionLightning.createRange(b, this.collidesAir, this.collidesGround, b, b.team, this.linkRange, this.maxHit, b.team.color, Mathf.chanceDelta((double)this.randomLightningChance), this.lightningDamage, this.lightningLength, 2.5F, this.boltNum, (p) -> {
                        this.liHitEffect.at(p.getX(), p.getY(), b.team.color);
                    });
                }

                if (Mathf.chanceDelta(0.1)) {
                    this.slopeEffect.at(b.x + Mathf.range(this.size / 4.0F), b.y + Mathf.range(this.size / 4.0F), Mathf.random(2.0F, 4.0F), b.team.color);
                    this.spreadEffect.at(b.x, b.y, b.team.color);
                }

                if (this.randomGenerateRange > 0.0F && Mathf.chance((double)(Time.delta * this.randomGenerateChance)) && b.lifetime - b.time > PositionLightning.lifetime) {
                    PositionLightning.createRandomRange(b, b.team, b, this.randomGenerateRange, this.backColor, Mathf.chanceDelta((double)this.randomLightningChance), 0.0F, 0, this.boltWidth, this.boltNum, this.randomLightningNum, (hitPos) -> {
                        this.randomGenerateSound.at(hitPos, Mathf.random(0.9F, 1.1F));
                        Damage.damage(b.team, hitPos.getX(), hitPos.getY(), this.splashDamageRadius / 8.0F, this.splashDamage * b.damageMultiplier() / 8.0F, this.collidesAir, this.collidesGround);
                        WHFx.lightningHitLarge.at(hitPos.getX(), hitPos.getY(), b.team.color);
                        this.hitModifier.get(hitPos);
                    });
                }

                if (Mathf.chanceDelta((double)this.effectLightningChance) && b.lifetime - b.time > Fx.chainLightning.lifetime) {
                    for(int i = 0; i < this.effectLingtning; ++i) {
                        Vec2 v = randVec.rnd(this.effectLightningLength + Mathf.random(this.effectLightningLengthRand)).add(b).add(Tmp.v1.set(b.vel).scl(Fx.chainLightning.lifetime / 2.0F));
                        Fx.chainLightning.at(b.x, b.y, 12.0F, b.team.color, v.cpy());
                        WHFx.lightningHitSmall.at(v.x, v.y, 20.0F, b.team.color);
                    }
                }

            }

            public void init(Bullet b) {
                super.init(b);
                b.lifetime *= Mathf.randomSeed((long)b.id, 0.875F, 1.125F);
                this.RsmokeEffect.at(b.x, b.y, b.team.color);
                this.RshootEffect.at(b.x, b.y, b.rotation(), b.team.color);
            }

            public void drawTrail(Bullet b) {
                if (this.trailLength > 0 && b.trail != null) {
                    float z = Draw.z();
                    Draw.z(z - 1.0E-4F);
                    b.trail.draw(this.getColor(b), this.trailWidth);
                    Draw.z(z);
                }

            }

            public void draw(Bullet b) {
                this.drawTrail(b);
                Draw.color(Tmp.c1);
                Fill.circle(b.x, b.y, this.size);
                float[] param = new float[]{9.0F, 28.0F, 1.0F, 9.0F, 22.0F, -1.25F, 12.0F, 16.0F, -0.45F};

                for(int i = 0; i < param.length / 3; ++i) {
                    int[] var4 = Mathf.signs;
                    int var5 = var4.length;

                    for(int var6 = 0; var6 < var5; ++var6) {
                        int j = var4[var6];
                        Drawf.tri(b.x, b.y, param[i * 3] * b.fout(), param[i * 3 + 1] * b.fout(), b.rotation() + 90.0F * (float)j + param[i * 3 + 2] * Time.time);
                    }
                }

                Draw.color(Color.black);
                Fill.circle(b.x, b.y, this.size / 6.125F + this.size / 3.0F * Mathf.curve(b.fout(), 0.1F, 0.35F));
                Drawf.light(b.x, b.y, this.size * 6.85F, b.team.color, 0.7F);
            }

            public void despawned(Bullet b) {
                PositionLightning.createRandomRange(b, b.team, b, this.randomGenerateRange, b.team.color, Mathf.chanceDelta((double)this.randomLightningChance), 0.0F, 0, this.boltWidth, this.boltNum, this.randomLightningNum, (hitPos) -> {
                    Damage.damage(b.team, hitPos.getX(), hitPos.getY(), this.splashDamageRadius, this.splashDamage * b.damageMultiplier(), this.collidesAir, this.collidesGround);
                    WHFx.lightningHitLarge.at(hitPos.getX(), hitPos.getY(), b.team.color);
                    this.liHitEffect.at(hitPos);

                    for(int j = 0; j < this.lightning; ++j) {
                        Lightning.create(b, b.team.color, this.lightningDamage < 0.0F ? this.damage : this.lightningDamage, b.x, b.y, b.rotation() + Mathf.range(this.lightningCone / 2.0F) + this.lightningAngle, this.lightningLength + Mathf.random(this.lightningLengthRand));
                    }

                    this.hitSound.at(hitPos, Mathf.random(0.9F, 1.1F));
                    this.hitModifier.get(hitPos);
                });
                if (this.despawnHit) {
                    this.hit(b);
                } else {
                    this.createUnits(b, b.x, b.y);
                }

                if (!this.fragOnHit) {
                    this.createFrags(b, b.x, b.y);
                }

                this.despawnEffect.at(b.x, b.y, b.rotation(), b.team.color);
                this.despawnSound.at(b);
                Effect.shake(this.despawnShake, this.despawnShake, b);
            }

            public void hit(Bullet b, float x, float y) {
                this.hitEffect.at(x, y, b.rotation(), b.team.color);
                this.hitSound.at(x, y, this.hitSoundPitch, this.hitSoundVolume);
                Effect.shake(this.hitShake, this.hitShake, b);
                if (this.fragOnHit) {
                    this.createFrags(b, x, y);
                }

                this.createPuddles(b, x, y);
                this.createIncend(b, x, y);
                this.createUnits(b, x, y);
                if (this.suppressionRange > 0.0F) {
                    Damage.applySuppression(b.team, b.x, b.y, this.suppressionRange, this.suppressionDuration, 0.0F, this.suppressionEffectChance, new Vec2(b.x, b.y));
                }

                this.createSplashDamage(b, x, y);

                for(int i = 0; i < this.lightning; ++i) {
                    Lightning.create(b, b.team.color, this.lightningDamage < 0.0F ? this.damage : this.lightningDamage, b.x, b.y, b.rotation() + Mathf.range(this.lightningCone / 2.0F) + this.lightningAngle, this.lightningLength + Mathf.random(this.lightningLengthRand));
                }

            }

            public void removed(Bullet b) {
                if (this.trailLength > 0 && b.trail != null && b.trail.size() > 0) {
                    Fx.trailFade.at(b.x, b.y, this.trailWidth, b.team.color, b.trail.copy());
                }

            }
        };
        vastBulletStrafeLaser = new StrafeLaserBulletType(0.0F, 300.0F) {
            {
                this.strafeAngle = 0.0F;
            }

            public void init(Bullet b) {
                super.init(b);
                Sounds.laserblast.at(b);
            }

            public void hit(Bullet b, float x, float y) {
                super.hit(b, x, y);
                Entityc var5 = b.owner;
                if (var5 instanceof Unit) {
                    Unit from = (Unit)var5;
                    if (from.dead || !from.isAdded() || from.healthf() > 0.99F) {
                        return;
                    }

                    from.heal(this.damage / 20.0F);
                    if (Vars.headless) {
                        return;
                    }

                    PositionLightning.createEffect(b, from, b.team.color, 2, Mathf.random(1.5F, 3.0F));
                }

            }

            public void draw(Bullet b) {
                Tmp.c1.set(b.team.color).lerp(Color.white, Mathf.absin(4.0F, 0.1F));
                super.draw(b);
                Draw.z(110.0F);
                float fout = b.fout(0.25F) * Mathf.curve(b.fin(), 0.0F, 0.125F);
                Draw.color(Tmp.c1);
                Fill.circle(b.x, b.y, this.width / 1.225F * fout);
                Entityc var4 = b.owner;
                if (var4 instanceof Unit) {
                    Unit unit = (Unit)var4;
                    if (!unit.dead) {
                        Draw.z(100.0F);
                        Lines.stroke((this.width / 3.0F + Mathf.absin(Time.time, 4.0F, 0.8F)) * fout);
                        Lines.line(b.x, b.y, unit.x, unit.y, false);
                    }
                }

                int[] var7 = Mathf.signs;
                int var9 = var7.length;

                for(int var5 = 0; var5 < var9; ++var5) {
                    int i = var7[var5];
                    Drawn.tri(b.x, b.y, 6.0F * fout, 10.0F + 50.0F * fout, Time.time * 1.5F + (float)(90 * i));
                    Drawn.tri(b.x, b.y, 6.0F * fout, 20.0F + 60.0F * fout, Time.time * -1.0F + (float)(90 * i));
                }

                Draw.z(110.001F);
                Draw.color(b.team.color, Color.white, 0.25F);
                Fill.circle(b.x, b.y, this.width / 1.85F * fout);
                Draw.color(Color.black);
                Fill.circle(b.x, b.y, this.width / 2.155F * fout);
                Draw.z(100.0F);
                Draw.reset();
                float rotation = this.dataRot ? b.fdata : b.rotation() + this.getRotation(b);
                float maxRangeFout = this.maxRange * fout;
                float realLength = WHUtils.findLaserLength(b, rotation, maxRangeFout);
                Tmp.v1.trns(rotation, realLength);
                Tmp.v2.trns(rotation, 0.0F, this.width / 2.0F * fout);
                Tmp.v3.setZero();
                if (realLength < maxRangeFout) {
                    Tmp.v3.set(Tmp.v2).scl((maxRangeFout - realLength) / maxRangeFout);
                }

                Draw.color(Tmp.c1);
                Tmp.v2.scl(0.9F);
                Tmp.v3.scl(0.9F);
                Fill.quad(b.x - Tmp.v2.x, b.y - Tmp.v2.y, b.x + Tmp.v2.x, b.y + Tmp.v2.y, b.x + Tmp.v1.x + Tmp.v3.x, b.y + Tmp.v1.y + Tmp.v3.y, b.x + Tmp.v1.x - Tmp.v3.x, b.y + Tmp.v1.y - Tmp.v3.y);
                if (realLength < maxRangeFout) {
                    Fill.circle(b.x + Tmp.v1.x, b.y + Tmp.v1.y, Tmp.v3.len());
                }

                Tmp.v2.scl(1.2F);
                Tmp.v3.scl(1.2F);
                Draw.alpha(0.5F);
                Fill.quad(b.x - Tmp.v2.x, b.y - Tmp.v2.y, b.x + Tmp.v2.x, b.y + Tmp.v2.y, b.x + Tmp.v1.x + Tmp.v3.x, b.y + Tmp.v1.y + Tmp.v3.y, b.x + Tmp.v1.x - Tmp.v3.x, b.y + Tmp.v1.y - Tmp.v3.y);
                if (realLength < maxRangeFout) {
                    Fill.circle(b.x + Tmp.v1.x, b.y + Tmp.v1.y, Tmp.v3.len());
                }

                Draw.alpha(1.0F);
                Draw.color(Color.black);
                Draw.z(Draw.z() + 0.01F);
                Tmp.v2.scl(0.5F);
                Fill.quad(b.x - Tmp.v2.x, b.y - Tmp.v2.y, b.x + Tmp.v2.x, b.y + Tmp.v2.y, b.x + (Tmp.v1.x + Tmp.v3.x) / 3.0F, b.y + (Tmp.v1.y + Tmp.v3.y) / 3.0F, b.x + (Tmp.v1.x - Tmp.v3.x) / 3.0F, b.y + (Tmp.v1.y - Tmp.v3.y) / 3.0F);
                Drawf.light(b.x, b.y, b.x + Tmp.v1.x, b.y + Tmp.v1.y, this.width * 1.5F, this.getColor(b), 0.7F);
                Draw.reset();
                Draw.z(Draw.z() - 0.01F);
            }
        };
        hyperBlast = new BasicBulletType(3.3F, 400.0F) {
            {
                this.lifetime = 60.0F;
                this.trailLength = 15;
                this.drawSize = 250.0F;
                this.drag = 0.0075F;
                this.despawnEffect = this.hitEffect = WHFx.lightningHitLarge(Pal.techBlue);
                this.knockback = 12.0F;
                this.width = 15.0F;
                this.height = 37.0F;
                this.splashDamageRadius = 40.0F;
                this.splashDamage = this.lightningDamage = this.damage * 0.75F;
                this.backColor = this.lightColor = this.lightningColor = this.trailColor = Pal.techBlue;
                this.frontColor = Color.white;
                this.lightning = 3;
                this.lightningLength = 8;
                this.smokeEffect = Fx.shootBigSmoke2;
                this.trailChance = 0.6F;
                this.trailEffect = WHFx.trailToGray;
                this.hitShake = 3.0F;
                this.hitSound = Sounds.plasmaboom;
            }
        };
        hyperBlastLinker = new LightningLinkerBulletType(5.0F, 220.0F) {
            {
                this.effectLightningChance = 0.15F;
                this.backColor = this.trailColor = this.lightColor = this.lightningColor = this.hitColor = Pal.techBlue;
                this.size = 8.0F;
                this.frontColor = Pal.techBlue.cpy().lerp(Color.white, 0.25F);
                this.range = 200.0F;
                this.trailWidth = 8.0F;
                this.trailLength = 20;
                this.linkRange = 280.0F;
                this.maxHit = 8;
                this.drag = 0.085F;
                this.hitSound = Sounds.explosionbig;
                this.splashDamageRadius = 120.0F;
                this.splashDamage = this.lightningDamage = this.damage / 4.0F;
                this.lifetime = 50.0F;
                this.scaleLife = false;
                this.despawnEffect = WHFx.lightningHitLarge(this.hitColor);
                this.hitEffect = new MultiEffect(new Effect[]{WHFx.hitSpark(this.backColor, 65.0F, 22, this.splashDamageRadius, 4.0F, 16.0F), WHFx.blast(this.backColor, this.splashDamageRadius / 2.0F)});
                this.shootEffect = WHFx.hitSpark(this.backColor, 45.0F, 12, 60.0F, 3.0F, 8.0F);
                this.smokeEffect = WHFx.hugeSmokeGray;
            }
        };
        arc9000frag = new FlakBulletType(3.75F, 200.0F) {
            {
                this.trailColor = this.lightColor = this.lightningColor = this.backColor = this.frontColor = Pal.techBlue;
                this.trailLength = 14;
                this.trailWidth = 2.7F;
                this.trailRotation = true;
                this.trailInterval = 3.0F;
                this.trailEffect = WHFx.polyTrail(this.backColor, this.frontColor, 4.65F, 22.0F);
                this.trailChance = 0.0F;
                this.despawnEffect = this.hitEffect = WHFx.techBlueExplosion;
                this.knockback = 12.0F;
                this.lifetime = 90.0F;
                this.width = 17.0F;
                this.height = 42.0F;
                this.hittable = false;
                this.collidesTiles = false;
                this.splashDamageRadius = 60.0F;
                this.splashDamage = this.damage * 0.6F;
                this.lightning = 3;
                this.lightningLength = 8;
                this.smokeEffect = Fx.shootBigSmoke2;
                this.hitShake = 8.0F;
                this.hitSound = Sounds.plasmaboom;
                this.status = StatusEffects.shocked;
            }
        };
        arc9000 = new LightningLinkerBulletType(2.75F, 200.0F) {
            {
                this.trailWidth = 4.5F;
                this.trailLength = 66;
                this.chargeEffect = new MultiEffect(new Effect[]{WHFx.techBlueCharge, WHFx.techBlueChargeBegin});
                this.spreadEffect = this.slopeEffect = Fx.none;
                this.trailEffect = WHFx.hitSparkHuge;
                this.trailInterval = 5.0F;
                this.backColor = this.trailColor = this.hitColor = this.lightColor = this.lightningColor = this.frontColor = Pal.techBlue;
                this.randomGenerateRange = 340.0F;
                this.randomLightningNum = 3;
                this.linkRange = 280.0F;
                this.range = 800.0F;
                this.drawSize = 500.0F;
                this.drag = 0.0035F;
                this.fragLifeMin = 0.3F;
                this.fragLifeMax = 1.0F;
                this.fragVelocityMin = 0.3F;
                this.fragVelocityMax = 1.25F;
                this.fragBullets = 14;
                this.intervalBullets = 2;
                this.intervalBullet = this.fragBullet = WHBullets.arc9000frag;
                this.hitSound = Sounds.explosionbig;
                this.splashDamageRadius = 120.0F;
                this.splashDamage = 1000.0F;
                this.lightningDamage = 375.0F;
                this.hittable = false;
                this.collidesTiles = true;
                this.pierce = false;
                this.collides = false;
                this.ammoMultiplier = 1.0F;
                this.lifetime = 300.0F;
                this.despawnEffect = WHFx.circleOut(this.hitColor, this.splashDamageRadius * 1.5F);
                this.hitEffect = WHFx.largeTechBlueHit;
                this.shootEffect = WHFx.techBlueShootBig;
                this.smokeEffect = WHFx.techBlueSmokeBig;
                this.hitSpacing = 3.0F;
            }

            public void update(Bullet b) {
                super.update(b);
                if (b.timer(1, 6.0F)) {
                    for(int j = 0; j < 2; ++j) {
                        Drawn.randFadeLightningEffect(b.x, b.y, (float)Mathf.random(360), (float)Mathf.random(7, 12), this.backColor, Mathf.chance(0.5));
                    }
                }

            }

            public void draw(Bullet b) {
                Draw.color(this.backColor);
                Drawn.surround((long)b.id, b.x, b.y, this.size * 1.45F, 14, 7.0F, 11.0F, (b.fin(WHInterp.parabola4Reversed) + 1.0F) / 2.0F * b.fout(0.1F));
                this.drawTrail(b);
                Draw.color(this.backColor);
                Fill.circle(b.x, b.y, this.size);
                Draw.z(110.0001F);
                Draw.color(this.frontColor);
                Fill.circle(b.x, b.y, this.size * 0.62F);
                Draw.z(99.89F);
                Draw.color(this.frontColor);
                Fill.circle(b.x, b.y, this.size * 0.66F);
                Draw.z(100.0F);
                Drawf.light(b.x, b.y, this.size * 1.85F, this.backColor, 0.7F);
            }
        };
        arc9000hyper = new AccelBulletType(10.0F, 1000.0F) {
            {
                this.drawSize = 1200.0F;
                this.width = this.height = this.shrinkX = this.shrinkY = 0.0F;
                this.collides = false;
                this.despawnHit = false;
                this.collidesAir = this.collidesGround = this.collidesTiles = true;
                this.splashDamage = 4000.0F;
                this.velocityBegin = 6.0F;
                this.velocityIncrease = -5.9F;
                this.accelerateEnd = 0.75F;
                this.accelerateBegin = 0.1F;
                this.accelInterp = Interp.pow2;
                this.trailInterp = Interp.pow10Out;
                this.despawnSound = Sounds.plasmaboom;
                this.hitSound = Sounds.explosionbig;
                this.hitShake = 60.0F;
                this.despawnShake = 100.0F;
                this.lightning = 12;
                this.lightningDamage = 2000.0F;
                this.lightningLength = 50;
                this.lightningLengthRand = 80;
                this.fragBullets = 1;
                this.fragBullet = WHBullets.arc9000;
                this.fragVelocityMin = 0.4F;
                this.fragVelocityMax = 0.6F;
                this.fragLifeMin = 0.5F;
                this.fragLifeMax = 0.7F;
                this.trailWidth = 12.0F;
                this.trailLength = 120;
                this.ammoMultiplier = 1.0F;
                this.hittable = false;
                this.scaleLife = true;
                this.splashDamageRadius = 400.0F;
                this.hitColor = this.lightColor = this.lightningColor = this.trailColor = Pal.techBlue;
                Effect effect = WHFx.crossBlast(this.hitColor, 420.0F, 45.0F);
                effect.lifetime += 180.0F;
                this.despawnEffect = WHFx.circleOut(this.hitColor, this.splashDamageRadius);
                this.hitEffect = new MultiEffect(new Effect[]{WHFx.blast(this.hitColor, 200.0F), (new Effect(180.0F, 600.0F, (e) -> {
                    float rad = 120.0F;
                    float f = (e.fin(Interp.pow10Out) + 8.0F) / 9.0F * Mathf.curve(Interp.slowFast.apply(e.fout(0.75F)), 0.0F, 0.85F);
                    Draw.alpha(0.9F * e.foutpowdown());
                    Draw.color(Color.white, e.color, e.fin() + 0.6F);
                    Fill.circle(e.x, e.y, rad * f);
                    e.scaled(45.0F, (i) -> {
                        Lines.stroke(7.0F * i.fout());
                        Lines.circle(i.x, i.y, rad * 3.0F * i.finpowdown());
                        Lines.circle(i.x, i.y, rad * 2.0F * i.finpowdown());
                    });
                    Draw.color(Color.white);
                    Fill.circle(e.x, e.y, rad * f * 0.75F);
                    Drawf.light(e.x, e.y, rad * f * 2.0F, Draw.getColor(), 0.7F);
                })).layer(110.001F), effect, new Effect(260.0F, 460.0F, (e) -> {
                    Draw.blend(Blending.additive);
                    Draw.z(114.2F);
                    float radius = e.fin(Interp.pow3Out) * 230.0F;
                    Fill.light(e.x, e.y, Lines.circleVertices(radius), radius, Color.clear, Tmp.c1.set(Pal.techBlue).a(e.fout(Interp.pow10Out)));
                    Draw.blend();
                })});
            }

            public void draw(Bullet b) {
                super.draw(b);
                Draw.color(Pal.techBlue, Color.white, b.fout() * 0.25F);
                float extend = Mathf.curve(b.fin(Interp.pow10Out), 0.075F, 1.0F);
                float chargeCircleFrontRad = 20.0F;
                float width = chargeCircleFrontRad * 1.2F;
                Fill.circle(b.x, b.y, width * (b.fout() + 4.0F) / 3.5F);
                float rotAngle = b.fdata;
                int[] var6 = Mathf.signs;
                int var7 = var6.length;

                int var8;
                int ix;
                for(var8 = 0; var8 < var7; ++var8) {
                    ix = var6[var8];
                    Drawn.tri(b.x, b.y, width * b.foutpowdown(), 200.0F + 570.0F * extend, rotAngle + (float)(90 * ix) - 45.0F);
                }

                var6 = Mathf.signs;
                var7 = var6.length;

                for(var8 = 0; var8 < var7; ++var8) {
                    ix = var6[var8];
                    Drawn.tri(b.x, b.y, width * b.foutpowdown(), 200.0F + 570.0F * extend, rotAngle + (float)(90 * ix) + 45.0F);
                }

                float cameraFin = (1.0F + 2.0F * Drawn.cameraDstScl(b.x, b.y, Vars.mobile ? 200.0F : 320.0F)) / 3.0F;
                float triWidth = b.fout() * chargeCircleFrontRad * cameraFin;
                int[] var14 = Mathf.signs;
                ix = var14.length;

                for(int var10 = 0; var10 < ix; ++var10) {
                    int i = var14[var10];
                    Fill.tri(b.x, b.y + triWidth, b.x, b.y - triWidth, b.x + (float)i * cameraFin * chargeCircleFrontRad * (23.0F + Mathf.absin(10.0F, 0.75F)) * (b.fout() * 1.25F + 1.0F), b.y);
                }

                float rad = this.splashDamageRadius * b.fin(Interp.pow5Out) * Interp.circleOut.apply(b.fout(0.15F));
                Lines.stroke(8.0F * b.fin(Interp.pow2Out));
                Lines.circle(b.x, b.y, rad);
                Draw.color(Color.white);
                Fill.circle(b.x, b.y, width * (b.fout() + 4.0F) / 5.5F);
                Drawf.light(b.x, b.y, rad, this.hitColor, 0.5F);
            }

            public void init(Bullet b) {
                super.init(b);
                b.fdata = Mathf.randomSeed((long)b.id, 90.0F);
            }

            public void update(Bullet b) {
                super.update(b);
                b.fdata += b.vel.len() / 3.0F;
            }

            public void despawned(Bullet b) {
                super.despawned(b);
                Angles.randLenVectors((long)b.id, 8, this.splashDamageRadius / 1.25F, (x, y) -> {
                    float nowX = b.x + x;
                    float nowY = b.y + y;
                    Vec2 vec2 = new Vec2(nowX, nowY);
                    Team team = b.team;
                    float mul = b.damageMultiplier();
                    Time.run(Mathf.random(6.0F, 24.0F) + Mathf.sqrt(x * x + y * y) / this.splashDamageRadius * 3.0F, () -> {
                        if (Mathf.chanceDelta(0.4000000059604645)) {
                            this.hitSound.at(vec2.x, vec2.y, this.hitSoundPitch, this.hitSoundVolume);
                        }

                        this.despawnSound.at(vec2);
                        Effect.shake(this.hitShake, this.hitShake, vec2);

                        int i;
                        for(i = 0; i < this.lightning / 2; ++i) {
                            Lightning.create(team, this.lightningColor, this.lightningDamage, vec2.x, vec2.y, Mathf.random(360.0F), this.lightningLength + Mathf.random(this.lightningLengthRand));
                        }

                        this.hitEffect.at(vec2.x, vec2.y, 0.0F, this.hitColor);
                        this.hitSound.at(vec2.x, vec2.y, this.hitSoundPitch, this.hitSoundVolume);
                        if (this.fragBullet != null) {
                            for(i = 0; i < this.fragBullets; ++i) {
                                this.fragBullet.create((Entityc)team.cores().firstOpt(), team, vec2.x, vec2.y, (float)Mathf.random(360), Mathf.random(this.fragVelocityMin, this.fragVelocityMax), Mathf.random(this.fragLifeMin, this.fragLifeMax));
                            }
                        }

                        if (this.splashDamageRadius > 0.0F && !b.absorbed) {
                            Damage.damage(team, vec2.x, vec2.y, this.splashDamageRadius, this.splashDamage * mul, this.collidesAir, this.collidesGround);
                            if (this.status != StatusEffects.none) {
                                Damage.status(team, vec2.x, vec2.y, this.splashDamageRadius, this.status, this.statusDuration, this.collidesAir, this.collidesGround);
                            }
                        }

                    });
                });
            }
        };
        AGFrag = new LightningLinkerBulletType() {
            {
                this.effectLightningChance = 0.15F;
                this.damage = 200.0F;
                this.backColor = this.trailColor = this.lightColor = this.lightningColor = this.hitColor = Color.valueOf("FFC397FF");
                this.size = 10.0F;
                this.frontColor = Color.valueOf("000000ff");
                this.range = 600.0F;
                this.spreadEffect = Fx.none;
                this.trailWidth = 8.0F;
                this.trailLength = 20;
                this.speed = 6.0F;
                this.linkRange = 280.0F;
                this.maxHit = 12;
                this.drag = 0.0065F;
                this.hitSound = Sounds.explosionbig;
                this.splashDamageRadius = 60.0F;
                this.splashDamage = this.lightningDamage = this.damage / 3.0F;
                this.lifetime = 130.0F;
                this.despawnEffect = WHFx.lightningHitLarge(this.hitColor);
                this.hitEffect = WHFx.sharpBlast(this.hitColor, this.frontColor, 35.0F, this.splashDamageRadius * 1.25F);
                this.shootEffect = WHFx.hitSpark(this.backColor, 45.0F, 12, 60.0F, 3.0F, 8.0F);
                this.smokeEffect = WHFx.hugeSmoke;
            }
        };
        tankAG7 = new EffectBulletType(480.0F) {
            {
                this.hittable = false;
                this.collides = false;
                this.collidesTiles = this.collidesAir = this.collidesGround = true;
                this.speed = 0.1F;
                this.despawnHit = true;
                this.keepVelocity = false;
                this.splashDamageRadius = 480.0F;
                this.splashDamage = 800.0F;
                this.lightningDamage = 200.0F;
                this.lightning = 36;
                this.lightningLength = 60;
                this.lightningLengthRand = 60;
                this.hitShake = this.despawnShake = 40.0F;
                this.drawSize = 800.0F;
                this.hitColor = this.lightColor = this.trailColor = this.lightningColor = Color.valueOf("FFC397FF");
                this.buildingDamageMultiplier = 1.1F;
                this.fragBullets = 22;
                this.fragBullet = WHBullets.AGFrag;
                this.hitSound = WHSounds.hugeBlast;
                this.hitSoundVolume = 4.0F;
                this.fragLifeMax = 1.1F;
                this.fragLifeMin = 0.7F;
                this.fragVelocityMax = 0.6F;
                this.fragVelocityMin = 0.2F;
                this.status = StatusEffects.shocked;
                this.shootEffect = WHFx.lightningHitLarge(this.hitColor);
                this.hitEffect = WHFx.hitSpark(this.hitColor, 240.0F, 220, 900.0F, 8.0F, 27.0F);
                this.despawnEffect = WHFx.collapserBulletExplode;
            }

            public void despawned(Bullet b) {
                super.despawned(b);
                Vec2 vec = (new Vec2()).set(b);
                float damageMulti = b.damageMultiplier();
                Team team = b.team;

                for(int i = 0; (float)i < this.splashDamageRadius / 28.0F; ++i) {
                    int finalI = i;
                    Time.run((float)i * this.despawnEffect.lifetime / (this.splashDamageRadius / 16.0F), () -> {
                        Damage.damage(team, vec.x, vec.y, (float)(8 * (finalI + 6)), this.splashDamage * damageMulti, true);
                    });
                }

                float rad = 120.0F;
                float spacing = 2.5F;

                for(int k = 0; (float)k < (this.despawnEffect.lifetime - WHFx.chainLightningFadeReversed.lifetime) / spacing; ++k) {
                    Time.run((float)k * spacing, () -> {
                        int[] var3 = Mathf.signs;
                        int var4 = var3.length;

                        for(int var5 = 0; var5 < var4; ++var5) {
                            int j = var3[var5];
                            Vec2 v = Tmp.v6.rnd(rad * 2.0F + Mathf.random(rad * 2.0F)).add(vec);
                            (j > 0 ? WHFx.chainLightningFade : WHFx.chainLightningFadeReversed).at(v.x, v.y, 12.0F, this.hitColor, vec);
                        }

                    });
                }

            }

            public void update(Bullet b) {
                float rad = 120.0F;
                Effect.shake(8.0F * b.fin(), 6.0F, b);
                if (b.timer(1, 12.0F)) {
                    Seq<Teamc> entites = new Seq();
                    Team var10000 = b.team;
                    float var10001 = b.x;
                    float var10002 = b.y;
                    float var10003 = rad * 2.5F * (1.0F + b.fin()) / 2.0F;
                    Objects.requireNonNull(entites);
                    Units.nearbyEnemies(var10000, var10001, var10002, var10003, entites::add);
                    Units.nearbyBuildings(b.x, b.y, rad * 2.5F * (1.0F + b.fin()) / 2.0F, (ex) -> {
                        if (ex.team != b.team) {
                            entites.add(ex);
                        }

                    });
                    entites.shuffle();
                    entites.truncate(15);
                    Iterator var4 = entites.iterator();

                    while(var4.hasNext()) {
                        Teamc e = (Teamc)var4.next();
                        PositionLightning.create(b, b.team, b, e, this.lightningColor, false, this.lightningDamage, 5 + Mathf.random(5), 2.5F, 1, (p) -> {
                            WHFx.lightningHitSmall.at(p.getX(), p.getY(), 0.0F, this.lightningColor);
                        });
                    }
                }

                if (b.lifetime() - b.time() > WHFx.chainLightningFadeReversed.lifetime) {
                    for(int i = 0; i < 2; ++i) {
                        if (Mathf.chanceDelta(0.2 * (double)Mathf.curve(b.fin(), 0.0F, 0.8F))) {
                            int[] var10 = Mathf.signs;
                            int var11 = var10.length;

                            for(int var6 = 0; var6 < var11; ++var6) {
                                int j = var10[var6];
                                Sounds.spark.at(b.x, b.y, 1.0F, 0.3F);
                                Vec2 v = Tmp.v6.rnd(rad / 2.0F + Mathf.random(rad * 2.0F) * (1.0F + Mathf.curve(b.fin(), 0.0F, 0.9F)) / 1.5F).add(b);
                                (j > 0 ? WHFx.chainLightningFade : WHFx.chainLightningFadeReversed).at(v.x, v.y, 12.0F, this.hitColor, b);
                            }
                        }
                    }
                }

                if (b.fin() > 0.05F && Mathf.chanceDelta((double)(b.fin() * 0.3F + 0.02F))) {
                    WHSounds.blaster.at(b.x, b.y, 1.0F, 0.3F);
                    Tmp.v1.rnd(rad / 4.0F * b.fin());
                    WHFx.shuttleLerp.at(b.x + Tmp.v1.x, b.y + Tmp.v1.y, Tmp.v1.angle(), this.hitColor, Mathf.random(rad, rad * 3.0F) * (Mathf.curve(b.fin(Interp.pow2In), 0.0F, 0.7F) + 2.0F) / 3.0F);
                }

            }

            public void draw(Bullet b) {
                float fin = Mathf.curve(b.fin(), 0.0F, 0.02F);
                float f = fin * Mathf.curve(b.fout(), 0.0F, 0.1F);
                float rad = 120.0F;
                float z = Draw.z();
                float circleF = (b.fout(Interp.pow2In) + 1.0F) / 2.0F;
                Draw.color(this.hitColor);
                Lines.stroke(rad / 20.0F * b.fin());
                Lines.circle(b.x, b.y, rad * b.fout(Interp.pow3In));
                Lines.circle(b.x, b.y, b.fin(Interp.circleOut) * rad * 3.0F * Mathf.curve(b.fout(), 0.0F, 0.05F));
                Rand rand = WHFx.rand;
                rand.setSeed((long)b.id);

                for(int i = 0; i < (int)(rad / 3.0F); ++i) {
                    Tmp.v1.trns(rand.random(360.0F) + rand.range(1.0F) * rad / 5.0F * b.fin(Interp.pow2Out), rad / 2.05F * circleF + rand.random(rad * (1.0F + b.fin(Interp.circleOut)) / 1.8F));
                    float angle = Tmp.v1.angle();
                    Drawn.tri(b.x + Tmp.v1.x, b.y + Tmp.v1.y, (b.fin() + 1.0F) / 2.0F * 28.0F + (float)rand.random(0, 8), rad / 16.0F * (b.fin(Interp.exp5In) + 0.25F), angle);
                    Drawn.tri(b.x + Tmp.v1.x, b.y + Tmp.v1.y, (b.fin() + 1.0F) / 2.0F * 12.0F + (float)rand.random(0, 2), rad / 12.0F * (b.fin(Interp.exp5In) + 0.5F) / 1.2F, angle - 180.0F);
                }

                Angles.randLenVectors((long)(b.id + 1), (int)(rad / 3.0F), rad / 4.0F * circleF, rad * (1.0F + b.fin(Interp.pow3Out)) / 3.0F, (x, y) -> {
                    float angle = Mathf.angle(x, y);
                    Drawn.tri(b.x + x, b.y + y, rad / 8.0F * (1.0F + b.fout()) / 2.2F, (b.fout() * 3.0F + 1.0F) / 3.0F * 25.0F + (float)rand.random(4, 12) * (b.fout(Interp.circleOut) + 1.0F) / 2.0F, angle);
                    Drawn.tri(b.x + x, b.y + y, rad / 8.0F * (1.0F + b.fout()) / 2.2F, (b.fout() * 3.0F + 1.0F) / 3.0F * 9.0F + (float)rand.random(0, 2) * (b.fin() + 1.0F) / 2.0F, angle - 180.0F);
                });
                Drawf.light(b.x, b.y, rad * f * (b.fin() + 1.0F) * 2.0F, Draw.getColor(), 0.7F);
                Draw.z(110.001F);
                Draw.color(this.hitColor);
                Fill.circle(b.x, b.y, rad * fin * circleF / 2.0F);
                Draw.color(Color.valueOf("000000ff"));
                Fill.circle(b.x, b.y, rad * fin * circleF * 0.75F / 2.0F);
                Draw.z(99.9F);
                Draw.color(Color.valueOf("FFC397FF"));
                Fill.circle(b.x, b.y, rad * fin * circleF * 0.8F / 2.0F);
                Draw.z(z);
            }
        };
        collaspsePf = new LightningLinkerBulletType() {
            {
                this.frontColor = Color.valueOf("000000ff");
                this.backColor = Color.valueOf("788AD7FF");
                this.hittable = false;
                this.size = 13.0F;
                this.damage = 100.0F;
                this.speed = 8.0F;
                this.lifetime = 56.25F;
                this.splashDamageRadius = 80.0F;
                this.splashDamage = 100.0F;
                this.lightningColor = Color.valueOf("788AD7FF");
                this.buildingDamageMultiplier = 0.2F;
                this.status = WHStatusEffects.palsy;
                this.statusDuration = 300.0F;
                this.lightningDamage = 65.0F;
                this.lightning = 1;
                this.lightningLength = 10;
                this.lightningLengthRand = 10;
                this.trailColor = Color.valueOf("788AD7FF");
                this.despawnEffect = new MultiEffect(new Effect[]{WHFx.circleOut(130.0F, this.splashDamageRadius, 3.0F), WHFx.smoothColorCircle(Color.valueOf("788AD7FF"), this.splashDamageRadius, 130.0F)});
                this.fragBullet = new DOTBulletType() {
                    {
                        this.DOTDamage = this.damage = 40.0F;
                        this.DOTRadius = 12.0F;
                        this.radIncrease = 0.25F;
                        this.fx = WHFx.squSpark1;
                        this.lightningColor = Color.valueOf("788AD7FF");
                    }
                };
                this.fragBullets = 1;
            }
        };
        collapseSp = new LightningLinkerBulletType() {
            {
                this.frontColor = Color.valueOf("000000ff");
                this.backColor = Color.valueOf("DBD58C");
                this.trailColor = Color.valueOf("DBD58C");
                this.hittable = false;
                this.speed = 8.0F;
                this.lifetime = 62.5F;
                this.size = 13.0F;
                this.rangeChange = 50.0F;
                this.damage = 100.0F;
                this.splashDamageRadius = 80.0F;
                this.splashDamage = 100.0F;
                this.lightningLength = 10;
                this.lightningLengthRand = 3;
                this.effectLightningChance = 0.25F;
                this.lightningCone = 360.0F;
                this.lightningDamage = 200.0F;
                this.linkRange = 200.0F;
                this.lightningColor = Color.valueOf("DBD58C");
                this.buildingDamageMultiplier = 0.2F;
                this.status = WHStatusEffects.palsy;
                this.statusDuration = 480.0F;
                this.lightningDamage = 65.0F;
                this.lightning = 1;
                this.lightningLength = 10;
                this.lightningLengthRand = 10;
                this.ammoMultiplier = 2.0F;
                this.fragBullet = new DOTBulletType() {
                    {
                        this.DOTDamage = this.damage = 70.0F;
                        this.DOTRadius = 12.0F;
                        this.radIncrease = 0.25F;
                        this.fx = WHFx.triSpark2;
                        this.lightningColor = Color.valueOf("DBD58C");
                    }
                };
                this.fragBullets = 1;
                this.despawnEffect = new MultiEffect(new Effect[]{WHFx.circleOut(130.0F, this.splashDamageRadius, 3.0F), WHFx.smoothColorCircle(Color.valueOf("DBD58C"), this.splashDamageRadius, 130.0F), WHFx.ScrossBlastArrow45, WHFx.subEffect(130.0F, 85.0F, 12, 30.0F, Interp.pow2Out, (i, x, y, rot, fin) -> {
                    float fout = Interp.pow2Out.apply(1.0F - fin);
                    float finpow = Interp.pow3Out.apply(fin);
                    Tmp.v1.trns(rot, 25.0F * finpow);
                    Draw.color(Color.valueOf("DBD58C"));
                    int[] var8 = Mathf.signs;
                    int var9 = var8.length;

                    for(int var10 = 0; var10 < var9; ++var10) {
                        int s = var8[var10];
                        Drawf.tri(x, y, 14.0F * fout, 30.0F * Mathf.curve(finpow, 0.0F, 0.3F) * WHFx.fout(fin, 0.15F), rot + (float)(s * 90));
                    }

                })});
            }
        };
    }
}
