//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.content;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.UnitSorts;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.entities.bullet.PointBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.part.ShapePart;
import mindustry.entities.part.DrawPart.PartProgress;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.entities.pattern.ShootBarrel;
import mindustry.entities.pattern.ShootSpread;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.weapons.PointDefenseWeapon;
import wh.entities.abilities.AdaptedHealAbility;
import wh.entities.abilities.ShockWaveAbility;
import wh.entities.bullet.BlackHoleBulletType;
import wh.entities.bullet.ChainLightingBulletType;
import wh.entities.bullet.LightningLinkerBulletType;
import wh.entities.bullet.PositionLightningBulletType;
import wh.entities.bullet.TrailFadeBulletType;
import wh.gen.NucleoidUnit;
import wh.gen.WHSounds;
import wh.graphics.WHPal;
import wh.type.unit.NucleoidUnitType;
import wh.util.WHUtils.EffectWrapper;

public final class WHUnitTypes {
    public static UnitType cMoon;
    public static UnitType tankAG;

    private WHUnitTypes() {
    }

    public static void load() {
        cMoon = new NucleoidUnitType("c-moon") {
            {
                this.constructor = NucleoidUnit::create;
                this.addEngine(-58.0F, -175.0F, 0.0F, 5.0F, true);
                this.addEngine(-53.0F, -175.0F, 0.0F, 5.0F, true);
                this.addEngine(-8.0F, -151.0F, 0.0F, 5.0F, true);
                this.addEngine(-4.0F, -151.0F, 0.0F, 5.0F, true);
                this.addEngine(-1.0F, -151.0F, 0.0F, 5.0F, true);
                this.abilities.add(new Ability() {
                    {
                        this.display = false;
                    }

                    public void death(Unit unit) {
                        Effect.shake(unit.hitSize / 10.0F, unit.hitSize / 8.0F, unit.x, unit.y);
                        WHFx.circleOut.at(unit.x, unit.y, unit.hitSize, unit.team.color);
                        WHFx.jumpTrailOut.at(unit.x, unit.y, unit.rotation, unit.team.color, unit.type);
                        WHSounds.jump.at(unit.x, unit.y, 1.0F, 3.0F);
                    }
                });
                this.abilities.add((new AdaptedHealAbility(3000.0F, 180.0F, 220.0F, this.healColor)).modify((a) -> {
                    a.selfHealReloadTime = 300.0F;
                }));
                this.abilities.add((new ShockWaveAbility(180.0F, 320.0F, 2000.0F, WHPal.pop)).status(new Object[]{StatusEffects.unmoving, 300.0F, StatusEffects.disarmed, 300.0F}).modify((a) -> {
                    a.knockback = 400.0F;
                    a.shootEffect = new MultiEffect(new Effect[]{WHFx.circleOut, WHFx.hitSpark(a.hitColor, 55.0F, 40, a.range + 30.0F, 3.0F, 8.0F), WHFx.crossBlastArrow45, WHFx.smoothColorCircle(WHPal.pop.cpy().a(0.3F), a.range, 60.0F)});
                }));
                this.weapons.add(new Weapon("wh-c-moon-weapon3") {
                    {
                        this.x = 49.0F;
                        this.y = -61.5F;
                        this.mirror = true;
                        this.layerOffset = 0.15F;
                        this.rotate = true;
                        this.rotateSpeed = 1.2F;
                        this.reload = 300.0F;
                        this.shootY = 46.0F;
                        this.inaccuracy = 1.5F;
                        this.velocityRnd = 0.075F;
                        this.shootSound = Sounds.mediumCannon;
                        this.shoot = new ShootAlternate() {
                            {
                                this.shots = 2;
                                this.spread = 25.0F;
                                this.shotDelay = 15.0F;
                            }
                        };
                        this.bullet = new TrailFadeBulletType(20.0F, 1800.0F) {
                            {
                                this.lifetime = 55.0F;
                                this.trailLength = 90;
                                this.trailWidth = 3.6F;
                                this.tracers = 2;
                                this.tracerFadeOffset = 20;
                                this.keepVelocity = false;
                                this.tracerSpacing = 10.0F;
                                this.tracerUpdateSpacing *= 1.25F;
                                this.removeAfterPierce = false;
                                this.hitColor = this.backColor = this.lightColor = this.lightningColor = WHPal.pop;
                                this.trailColor = WHPal.pop;
                                this.frontColor = WHPal.pop2;
                                this.width = 18.0F;
                                this.height = 60.0F;
                                this.hitSound = Sounds.plasmaboom;
                                this.despawnShake = this.hitShake = 18.0F;
                                this.pierce = this.pierceArmor = true;
                                this.pierceCap = 3;
                                this.pierceBuilding = false;
                                this.lightning = 3;
                                this.lightningLength = 8;
                                this.lightningLengthRand = 8;
                                this.lightningDamage = 300.0F;
                                this.smokeEffect = EffectWrapper.wrap(WHFx.hitSparkHuge, this.hitColor);
                                this.shootEffect = WHFx.instShoot(this.backColor, this.frontColor);
                                this.despawnEffect = WHFx.lightningHitLarge;
                            }

                            public void createFrags(Bullet b, float x, float y) {
                                super.createFrags(b, x, y);
                                WHBullets.nuBlackHole.create(b, x, y, 0.0F);
                            }
                        };
                        this.parts.add(new RegionPart("-管l") {
                            {
                                this.outline = true;
                                this.mirror = false;
                                this.moveY = -8.0F;
                                this.under = true;
                                this.recoilIndex = 0;
                                this.heatProgress = PartProgress.recoil;
                                this.progress = PartProgress.recoil;
                            }
                        });
                        this.parts.add(new RegionPart("-管r") {
                            {
                                this.outline = true;
                                this.mirror = false;
                                this.moveY = -8.0F;
                                this.under = true;
                                this.recoilIndex = 1;
                                this.heatProgress = PartProgress.recoil;
                                this.progress = PartProgress.recoil;
                            }
                        });
                    }

                    protected Teamc findTarget(Unit unit, float x, float y, float range, boolean air, boolean ground) {
                        return Units.bestTarget(unit.team, x, y, range, (u) -> {
                            return u.checkTarget(air, ground);
                        }, (t) -> {
                            return ground;
                        }, UnitSorts.strongest);
                    }
                });
                this.weapons.add(new Weapon("wh-c-moon-weapon1") {
                    {
                        this.x = 39.0F;
                        this.y = 34.0F;
                        this.shootY = 16.0F;
                        this.reload = 15.0F;
                        this.rotate = true;
                        this.alternate = false;
                        this.rotateSpeed = 4.0F;
                        this.shake = 1.0F;
                        this.shootSound = Sounds.railgun;
                        this.soundPitchMin = 1.5F;
                        this.soundPitchMax = 1.5F;
                        this.heatColor = Color.valueOf("FF9A79FF");
                        this.shoot = new ShootAlternate() {
                            {
                                this.barrels = 2;
                                this.spread = 7.5F;
                            }
                        };
                        this.bullet = new ChainLightingBulletType(300) {
                            {
                                this.length = 600.0F;
                                this.hitColor = this.lightColor = this.lightningColor = WHPal.pop;
                                this.shootEffect = WHFx.hitSparkLarge;
                                this.hitEffect = WHFx.lightningHitSmall;
                                this.smokeEffect = WHFx.hugeSmokeGray;
                            }
                        };
                    }
                });
                this.weapons.add(new PointDefenseWeapon("wh-c-moon-weapon1") {
                    {
                        this.color = WHPal.pop;
                        this.display = false;
                        this.layerOffset = 0.01F;
                        this.rotate = true;
                        this.rotateSpeed = 3.0F;
                        this.mirror = true;
                        this.alternate = false;
                        this.x = -54.5F;
                        this.y = -118.5F;
                        this.reload = 6.0F;
                        this.targetInterval = 6.0F;
                        this.targetSwitchInterval = 6.0F;
                        this.beamEffect = Fx.chainLightning;
                        this.bullet = new BulletType() {
                            {
                                this.shootEffect = WHFx.shootLineSmall(color);
                                this.hitEffect = WHFx.lightningHitSmall;
                                this.hitColor = color;
                                this.maxRange = 600.0F;
                                this.damage = 150.0F;
                            }
                        };
                    }
                });
                this.weapons.add(new Weapon("wh-c-moon-weapon1") {
                    {
                        this.reload = 20.0F;
                        this.rotate = true;
                        this.rotateSpeed = 3.0F;
                        this.alternate = false;
                        this.x = -97.5F;
                        this.y = -73.25F;
                        this.shootY = 20.0F;
                        this.shoot = new ShootAlternate() {
                            {
                                this.barrels = 2;
                                this.spread = 7.5F;
                            }
                        };
                        this.bullet = new PositionLightningBulletType(200.0F) {
                            {
                                this.maxRange = 600.0F;
                                this.rangeOverride = 600.0F;
                                this.hitColor = this.lightColor = this.lightningColor = WHPal.pop;
                                this.shootEffect = WHFx.hitSparkLarge;
                                this.hitEffect = WHFx.lightningHitSmall;
                                this.smokeEffect = WHFx.hugeSmokeGray;
                                this.spawnBullets.add(new LaserBulletType(800.0F) {
                                    {
                                        this.status = WHStatusEffects.plasma;
                                        this.statusDuration = 80.0F;
                                        this.shootEffect = Fx.bigShockwave;
                                        this.sideAngle = 25.0F;
                                        this.sideWidth = 1.35F;
                                        this.sideLength = 90.0F;
                                        this.colors = new Color[]{Color.valueOf("FBE5BFFF"), Color.valueOf("FFFBBDE1"), Color.valueOf("FFFBBDE1")};
                                        this.width = 11.0F;
                                        this.length = 600.0F;
                                    }
                                });
                            }
                        };
                    }
                });
                this.weapons.add(new Weapon("wh-c-moon-weapon2") {
                    {
                        this.layerOffset = 0.01F;
                        this.reload = 100.0F;
                        this.recoil = 1.5F;
                        this.x = -63.75F;
                        this.y = 17.75F;
                        this.shootY = 16.75F;
                        this.rotate = true;
                        this.mirror = true;
                        this.rotateSpeed = 3.0F;
                        this.inaccuracy = 1.8F;
                        this.shootCone = 31.8F;
                        this.shootSound = Sounds.bolt;
                        this.alternate = false;
                        this.shake = 0.3F;
                        this.ejectEffect = Fx.casing3Double;
                        this.parts.add(new RegionPart("-管") {
                            {
                                this.progress = PartProgress.recoil;
                                this.mirror = false;
                                this.under = true;
                                this.x = 0.0F;
                                this.y = 0.0F;
                                this.moveY = -8.0F;
                            }
                        });
                        this.shoot = new ShootAlternate() {
                            {
                                this.barrels = 2;
                                this.spread = 10.5F;
                                this.shots = 6;
                                this.shotDelay = 15.0F;
                            }
                        };
                        this.bullet = new TrailFadeBulletType() {
                            {
                                this.damage = 800.0F;
                                this.keepVelocity = false;
                                this.tracerSpacing = 10.0F;
                                this.tracerUpdateSpacing *= 1.25F;
                                this.removeAfterPierce = false;
                                this.pierce = true;
                                this.pierceCap = 3;
                                this.speed = 20.0F;
                                this.status = WHStatusEffects.palsy;
                                this.statusDuration = 400.0F;
                                this.buildingDamageMultiplier = 0.3F;
                                this.lightningDamage = 200.0F;
                                this.lightning = 2;
                                this.lightningLength = 9;
                                this.lightningLengthRand = 9;
                                this.lightningColor = WHPal.pop;
                                this.lifetime = 47.0F;
                                this.width = 15.0F;
                                this.height = 30.0F;
                                this.frontColor = WHPal.pop;
                                this.backColor = WHPal.pop;
                                this.trailLength = 13;
                                this.trailWidth = 3.0F;
                                this.trailColor = Color.valueOf("FEEBB3");
                                this.shootEffect = Fx.shootBig2;
                                this.smokeEffect = new ParticleEffect() {
                                    {
                                        this.particles = 8;
                                        this.line = true;
                                        this.interp = Interp.fastSlow;
                                        this.sizeInterp = Interp.pow3In;
                                        this.lenFrom = 6.0F;
                                        this.lenTo = 0.0F;
                                        this.strokeFrom = 2.0F;
                                        this.strokeTo = 0.0F;
                                        this.length = 35.0F;
                                        this.baseLength = 0.0F;
                                        this.lifetime = 23.0F;
                                        this.colorFrom = this.colorTo = WHPal.pop;
                                        this.cone = 15.0F;
                                    }
                                };
                                this.hitEffect = new MultiEffect(new Effect[]{new ParticleEffect() {
                                    {
                                        this.particles = 4;
                                        this.region = "wh-菱形";
                                        this.sizeFrom = 12.0F;
                                        this.sizeTo = 0.0F;
                                        this.length = 30.0F;
                                        this.baseLength = 0.0F;
                                        this.lifetime = 45.0F;
                                        this.colorFrom = this.colorTo = WHPal.pop;
                                    }
                                }});
                                this.despawnEffect = new MultiEffect() {
                                    {
                                        ParticleEffect var10001 = new ParticleEffect() {
                                            {
                                                this.particles = 4;
                                                this.region = "wh-菱形";
                                                this.sizeFrom = 10.0F;
                                                this.sizeTo = 0.0F;
                                                this.length = 50.0F;
                                                this.baseLength = 0.0F;
                                                this.lifetime = 60.0F;
                                                this.colorFrom = this.colorTo = WHPal.pop;
                                            }
                                        };
                                        var10001 = new ParticleEffect() {
                                            {
                                                this.particles = 12;
                                                this.strokeFrom = 2.0F;
                                                this.strokeTo = 0.0F;
                                                this.line = true;
                                                this.lenFrom = 20.0F;
                                                this.lenTo = 0.0F;
                                                this.length = 80.0F;
                                                this.baseLength = 10.0F;
                                                this.lifetime = 60.0F;
                                                this.colorFrom = this.colorTo = WHPal.pop;
                                            }
                                        };
                                        WaveEffect var2 = new WaveEffect() {
                                            {
                                                this.lifetime = 70.0F;
                                                this.sizeFrom = 0.0F;
                                                this.sizeTo = 70.0F;
                                                this.strokeFrom = 3.0F;
                                                this.strokeTo = 0.0F;
                                                this.colorFrom = this.colorTo = WHPal.pop;
                                            }
                                        };
                                        var2 = new WaveEffect() {
                                            {
                                                this.lifetime = 70.0F;
                                                this.sizeFrom = 40.0F;
                                                this.sizeTo = 70.0F;
                                                this.strokeFrom = 2.5F;
                                                this.strokeTo = 0.0F;
                                                this.colorFrom = this.colorTo = WHPal.pop;
                                            }
                                        };
                                    }
                                };
                            }
                        };
                    }
                });
                this.weapons.add(new Weapon("wh-c-moon-weapon2") {
                    {
                        this.layerOffset = 0.01F;
                        this.reload = 100.0F;
                        this.recoil = 1.5F;
                        this.x = -43.5F;
                        this.y = 91.0F;
                        this.shootY = 16.75F;
                        this.rotate = true;
                        this.mirror = true;
                        this.rotateSpeed = 3.0F;
                        this.inaccuracy = 1.8F;
                        this.shootCone = 31.8F;
                        this.shootSound = Sounds.bolt;
                        this.alternate = false;
                        this.shake = 0.3F;
                        this.ejectEffect = Fx.casing3Double;
                        this.parts.add(new RegionPart("-管") {
                            {
                                this.progress = PartProgress.recoil;
                                this.mirror = false;
                                this.under = true;
                                this.x = 0.0F;
                                this.y = 0.0F;
                                this.moveY = -8.0F;
                            }
                        });
                        this.shoot = new ShootAlternate() {
                            {
                                this.barrels = 2;
                                this.spread = 10.5F;
                                this.shots = 6;
                                this.shotDelay = 15.0F;
                            }
                        };
                        range = 280.0F;
                        this.reload = 150.0F;
                        this.shootY = 0.25F;
                        this.rotateSpeed = 3.5F;
                        this.recoil = 4.0F;
                        this.bullet = new TrailFadeBulletType() {
                            {
                                this.damage = 800.0F;
                                this.keepVelocity = false;
                                this.tracerSpacing = 10.0F;
                                this.tracerUpdateSpacing *= 1.25F;
                                this.removeAfterPierce = false;
                                this.pierce = true;
                                this.pierceCap = 3;
                                this.speed = 20.0F;
                                this.status = WHStatusEffects.palsy;
                                this.statusDuration = 400.0F;
                                this.buildingDamageMultiplier = 0.3F;
                                this.lightningDamage = 200.0F;
                                this.lightning = 2;
                                this.lightningLength = 9;
                                this.lightningLengthRand = 9;
                                this.lightningColor = WHPal.pop;
                                this.lifetime = 47.0F;
                                this.width = 15.0F;
                                this.height = 30.0F;
                                this.frontColor = WHPal.pop;
                                this.backColor = WHPal.pop;
                                this.trailLength = 13;
                                this.trailWidth = 3.0F;
                                this.trailColor = Color.valueOf("FEEBB3");
                                this.shootEffect = Fx.shootBig2;
                                this.smokeEffect = new ParticleEffect() {
                                    {
                                        this.particles = 8;
                                        this.line = true;
                                        this.interp = Interp.fastSlow;
                                        this.sizeInterp = Interp.pow3In;
                                        this.lenFrom = 6.0F;
                                        this.lenTo = 0.0F;
                                        this.strokeFrom = 2.0F;
                                        this.strokeTo = 0.0F;
                                        this.length = 35.0F;
                                        this.baseLength = 0.0F;
                                        this.lifetime = 23.0F;
                                        this.colorFrom = this.colorTo = WHPal.pop;
                                        this.cone = 15.0F;
                                    }
                                };
                                this.hitEffect = new MultiEffect(new Effect[]{new ParticleEffect() {
                                    {
                                        this.particles = 4;
                                        this.region = "wh-菱形";
                                        this.sizeFrom = 12.0F;
                                        this.sizeTo = 0.0F;
                                        this.length = 30.0F;
                                        this.baseLength = 0.0F;
                                        this.lifetime = 45.0F;
                                        this.colorFrom = this.colorTo = WHPal.pop;
                                    }
                                }});
                                this.despawnEffect = new MultiEffect() {
                                    {
                                        ParticleEffect var10001 = new ParticleEffect() {
                                            {
                                                this.particles = 4;
                                                this.region = "wh-菱形";
                                                this.sizeFrom = 10.0F;
                                                this.sizeTo = 0.0F;
                                                this.length = 50.0F;
                                                this.baseLength = 0.0F;
                                                this.lifetime = 60.0F;
                                                this.colorFrom = this.colorTo = WHPal.pop;
                                            }
                                        };
                                        var10001 = new ParticleEffect() {
                                            {
                                                this.particles = 12;
                                                this.strokeFrom = 2.0F;
                                                this.strokeTo = 0.0F;
                                                this.line = true;
                                                this.lenFrom = 20.0F;
                                                this.lenTo = 0.0F;
                                                this.length = 80.0F;
                                                this.baseLength = 10.0F;
                                                this.lifetime = 60.0F;
                                                this.colorFrom = this.colorTo = WHPal.pop;
                                            }
                                        };
                                        WaveEffect var2 = new WaveEffect() {
                                            {
                                                this.lifetime = 70.0F;
                                                this.sizeFrom = 0.0F;
                                                this.sizeTo = 70.0F;
                                                this.strokeFrom = 3.0F;
                                                this.strokeTo = 0.0F;
                                                this.colorFrom = this.colorTo = WHPal.pop;
                                            }
                                        };
                                        var2 = new WaveEffect() {
                                            {
                                                this.lifetime = 70.0F;
                                                this.sizeFrom = 40.0F;
                                                this.sizeTo = 70.0F;
                                                this.strokeFrom = 2.5F;
                                                this.strokeTo = 0.0F;
                                                this.colorFrom = this.colorTo = WHPal.pop;
                                            }
                                        };
                                    }
                                };
                            }
                        };
                    }
                });
                this.weapons.add(new Weapon("wh-c-moon-weapon4") {
                    {
                        this.reload = 600.0F;
                        this.recoil = 1.5F;
                        this.x = 0.0F;
                        this.y = 0.0F;
                        range = 800.0F;
                        this.shootY = 16.75F;
                        this.rotate = false;
                        this.mirror = false;
                        this.rotateSpeed = 3.0F;
                        this.inaccuracy = 1.8F;
                        this.alternate = false;
                        this.shake = 0.3F;
                        this.ejectEffect = Fx.none;
                        this.shootCone = 30.0F;
                        this.shoot.firstShotDelay = 180.0F;
                        this.shootSound = WHSounds.hugeShoot;
                        this.shootStatus = StatusEffects.unmoving;
                        this.shootStatusDuration = 180.0F;
                        this.bullet = new BasicBulletType() {
                            {
                                this.parts.add(new ShapePart() {
                                    {
                                        this.color = Color.valueOf("000000ff");
                                        this.circle = true;
                                        this.radius = 23.0F;
                                        this.radiusTo = 30.0F;
                                        this.layer = 114.0F;
                                    }
                                }, new ShapePart() {
                                    {
                                        this.color = Color.valueOf("FFFBBDE1");
                                        this.circle = true;
                                        this.radius = 30.0F;
                                        this.radiusTo = 40.0F;
                                        this.layer = 110.0F;
                                    }
                                });
                                this.spin = -11.0F;
                                this.homingPower = 0.03F;
                                this.homingRange = 50.0F;
                                this.damage = 400.0F;
                                this.speed = 5.0F;
                                this.lifetime = 200.0F;
                                this.trailLength = 45;
                                this.trailWidth = 9.0F;
                                this.hittable = false;
                                this.absorbable = false;
                                this.trailColor = WHPal.pop;
                                this.trailInterval = 1.0F;
                                this.trailRotation = true;
                                this.trailEffect = new ParticleEffect() {
                                    {
                                        this.interp = Interp.pow10Out;
                                        this.line = true;
                                        this.lenFrom = 60.0F;
                                        this.lenTo = 0.0F;
                                        this.strokeFrom = 5.0F;
                                        this.strokeTo = 0.0F;
                                        this.length = -100.0F;
                                        this.baseLength = 0.0F;
                                        this.lifetime = 60.0F;
                                        this.colorFrom = Color.valueOf("FFFBBDE1");
                                        this.colorTo = Color.valueOf("FFFBBDE1");
                                        this.cone = 18.0F;
                                    }
                                };
                                this.hitEffect = this.despawnEffect = Fx.none;
                                this.shootEffect = Fx.none;
                                this.chargeEffect = new MultiEffect(new Effect[]{new WaveEffect() {
                                    {
                                        this.lifetime = 99.0F;
                                        this.sizeFrom = 190.0F;
                                        this.sizeTo = 0.0F;
                                        this.strokeFrom = 0.0F;
                                        this.strokeTo = 10.0F;
                                        this.colorFrom = this.colorTo = WHPal.pop;
                                    }
                                }, new WaveEffect() {
                                    {
                                        this.startDelay = 49.0F;
                                        this.interp = Interp.circleIn;
                                        this.lifetime = 50.0F;
                                        this.sizeFrom = 190.0F;
                                        this.sizeTo = 0.0F;
                                        this.strokeFrom = 0.0F;
                                        this.strokeTo = 10.0F;
                                        this.colorFrom = this.colorTo = WHPal.pop;
                                    }
                                }, new Effect(180.0F, (e) -> {
                                    Draw.color(Tmp.c1);
                                    Fill.circle(e.x, e.y, 23.0F * e.finpow());
                                    float z = Draw.z();
                                    Draw.z(212.0F);
                                    Draw.color(Color.black);
                                    Fill.circle(e.x, e.y, 20.0F * e.finpow());
                                    Draw.z(z);
                                    Angles.randLenVectors((long)e.id, 16, 60.0F * e.foutpow(), Mathf.randomSeed((long)e.id, 360.0F), 360.0F, (x, y) -> {
                                        Draw.color(Tmp.c1);
                                        Fill.circle(e.x + x, e.y + y, 12.0F * e.foutpow());
                                        float zs = Draw.z();
                                        Draw.z(210.0F);
                                        Draw.color(Color.black);
                                        Fill.circle(e.x + x, e.y + y, 12.0F * e.foutpow());
                                        Draw.z(zs);
                                    });
                                })});
                                chargeSound = Sounds.lasercharge;
                                this.fragBullets = 1;
                                this.fragRandomSpread = 0.0F;
                                this.fragBullet = new BlackHoleBulletType() {
                                    {
                                        this.keepVelocity = false;
                                        this.inRad = 32.0F;
                                        this.outRad = this.drawSize = 88.0F;
                                        this.accColor = WHPal.pop;
                                        this.speed = 0.0F;
                                        this.lifetime = 150.0F;
                                        Color c1 = WHPal.pop;
                                        this.splashDamageRadius = 180.0F;
                                        this.splashDamage = 1500.0F;
                                        this.despawnEffect = this.hitEffect = new MultiEffect(new Effect[]{WHFx.smoothColorCircle(WHPal.pop, this.splashDamageRadius * 1.25F, 95.0F), WHFx.circleOut(WHPal.pop, this.splashDamageRadius * 1.25F), WHFx.hitSparkLarge});
                                        this.fragBullets = 15;
                                        this.fragVelocityMax = 1.3F;
                                        this.fragVelocityMin = 0.5F;
                                        this.fragLifeMax = 1.3F;
                                        this.fragLifeMin = 0.4F;
                                        this.fragBullet = new LightningLinkerBulletType(3.5F, 800.0F) {
                                            {
                                                this.size = 12.0F;
                                                this.randomLightningNum = 2;
                                                this.lightning = 2;
                                                this.lightningLength = 10;
                                                this.lightningLengthRand = 3;
                                                this.effectLightningChance = 0.25F;
                                                this.lightningCone = 360.0F;
                                                this.lightningDamage = 200.0F;
                                                this.linkRange = 150.0F;
                                                this.lightningColor = WHPal.pop;
                                                this.drag = 0.04F;
                                                this.knockback = 0.8F;
                                                this.lifetime = 90.0F;
                                                this.splashDamageRadius = 64.0F;
                                                this.splashDamage = 800.0F;
                                                this.backColor = this.trailColor = this.hitColor = WHPal.pop;
                                                this.frontColor = WHPal.pop;
                                                this.despawnShake = 7.0F;
                                                this.lightRadius = 30.0F;
                                                this.lightColor = WHPal.pop;
                                                this.lightOpacity = 0.5F;
                                                this.trailLength = 20;
                                                this.trailWidth = 3.5F;
                                            }
                                        };
                                    }
                                };
                            }
                        };
                    }
                });
            }
        };
        tankAG = new UnitType("tankAG") {
            {
                this.constructor = UnitEntity::create;
                this.weapons.add(new Weapon("tankAG-weapon7") {
                    float rangeWeapon = 650.0F;

                    {
                        this.reload = 1100.0F;
                        this.x = 0.0F;
                        this.shake = 0.0F;
                        this.rotate = true;
                        this.rotateSpeed = 10.0F;
                        this.mirror = false;
                        this.inaccuracy = 0.0F;
                        this.shootSound = Sounds.plasmadrop;
                        this.recoil = 0.0F;
                        this.bullet = new ArtilleryBulletType(10.0F, 150.0F) {
                            {
                                this.sprite = "wh-jump-arrow";
                                this.height = 65.0F;
                                this.width = 55.0F;
                                this.lifetime = 65.0F;
                                this.shrinkY = 0.0F;
                                this.frontColor = Color.valueOf("FFC397FF");
                                this.backColor = Color.valueOf("FFC397FF");
                                this.absorbable = false;
                                this.hittable = false;
                                this.despawnEffect = this.smokeEffect = Fx.none;
                                this.hitEffect = new MultiEffect(new Effect[]{new ParticleEffect() {
                                    {
                                        this.particles = 1;
                                        this.region = "wh-jump";
                                        this.sizeFrom = 15.0F;
                                        this.sizeTo = 0.0F;
                                        this.length = 0.0F;
                                        this.baseLength = 0.0F;
                                        this.lifetime = 180.0F;
                                        this.colorFrom = Color.valueOf("FFC397FF");
                                        this.colorTo = Color.valueOf("FFC397FF00");
                                    }
                                }, new WaveEffect() {
                                    {
                                        this.lifetime = 60.0F;
                                        this.sizeFrom = 100.0F;
                                        this.sizeTo = 0.0F;
                                        this.strokeFrom = 4.0F;
                                        this.strokeTo = 1.0F;
                                        this.colorFrom = Color.valueOf("FFC397FF");
                                        this.colorTo = Color.valueOf("FFC397FF");
                                    }
                                }});
                                this.trailColor = Color.valueOf("FFC397FF");
                                this.trailRotation = true;
                                this.trailWidth = 8.0F;
                                this.trailLength = 12;
                                targetInterval = 0.3F;
                                this.trailChance = 0.7F;
                                this.trailEffect = new MultiEffect(new Effect[]{new Effect(60.0F, (e) -> {
                                    Draw.color(e.color);
                                    Fx.rand.setSeed((long)e.id);
                                    float fin = 1.0F - Mathf.curve(e.fout(), 0.0F, 0.85F);
                                    Tmp.v1.set((float)(Fx.rand.chance(0.5) ? 5 : -5) * (Fx.rand.chance(0.20000000298023224) ? 0.0F : fin), 0.0F).rotate(e.rotation - 90.0F);
                                    float exx = e.x + Tmp.v1.x;
                                    float ey = e.y + Tmp.v1.y;
                                    Draw.rect("wh-jump-arrow", exx, ey, 64.0F * e.fout(), 64.0F * e.fout(), e.rotation - 90.0F);
                                })});
                                this.shootEffect = Fx.bigShockwave;
                                this.fragRandomSpread = 1.0F;
                                this.fragVelocityMax = 1.0F;
                                this.fragVelocityMin = 1.0F;
                                this.fragLifeMin = 1.0F;
                                this.fragLifeMax = 1.0F;
                                this.fragAngle = 180.0F;
                                this.fragBullets = 1;
                                this.fragBullet = new PointBulletType() {
                                    {
                                        this.damage = 20.0F;
                                        this.lifetime = 125.0F;
                                        this.speed = 10.0F;
                                        this.trailEffect = Fx.none;
                                        this.hitEffect = Fx.none;
                                        this.despawnEffect = new MultiEffect(new Effect[]{new ParticleEffect() {
                                            {
                                                this.particles = 1;
                                                this.sizeFrom = 0.0F;
                                                this.sizeTo = 30.0F;
                                                this.length = 0.0F;
                                                this.baseLength = 0.0F;
                                                this.lifetime = 20.0F;
                                                this.colorFrom = Color.valueOf("FFC397FF");
                                                this.colorTo = Color.valueOf("FFC397FF6E");
                                            }
                                        }, new WaveEffect() {
                                            {
                                                this.lifetime = 20.0F;
                                                this.sizeFrom = 60.0F;
                                                this.sizeTo = 0.0F;
                                                this.strokeFrom = 4.0F;
                                                this.strokeTo = 0.0F;
                                                this.colorFrom = Color.valueOf("FFC397FF");
                                                this.colorTo = Color.valueOf("FFC397FF");
                                            }
                                        }});
                                        this.fragBullets = 1;
                                        this.fragAngle = 180.0F;
                                        this.fragRandomSpread = 1.0F;
                                        this.fragVelocityMax = 1.0F;
                                        this.fragVelocityMin = 1.0F;
                                        this.fragLifeMin = 1.0F;
                                        this.fragLifeMax = 1.0F;
                                        this.fragBullet = new BasicBulletType(15.0F, 1000.0F, "wh-大导弹") {
                                            {
                                                this.lifetime = 83.3F;
                                                this.splashDamageRadius = 150.0F;
                                                this.splashDamage = 200.0F;
                                                this.scaledSplashDamage = true;
                                                this.buildingDamageMultiplier = 1.25F;
                                                this.shrinkX = 0.5F;
                                                this.shrinkY = 0.5F;
                                                this.width = 55.0F;
                                                this.height = 200.0F;
                                                this.hitSize = 20.0F;
                                                this.trailLength = 40;
                                                this.trailWidth = 5.0F;
                                                this.trailColor = Color.valueOf("FFC397FF");
                                                this.trailChance = 0.8F;
                                                this.trailEffect = new ParticleEffect() {
                                                    {
                                                        this.particles = 4;
                                                        this.region = "wh-菱形";
                                                        this.sizeFrom = 7.0F;
                                                        this.sizeTo = 16.0F;
                                                        this.length = 42.0F;
                                                        this.baseLength = 0.0F;
                                                        this.lifetime = 33.0F;
                                                        this.colorFrom = Color.valueOf("FFC397FF");
                                                        this.colorTo = Color.valueOf("FFC397FF");
                                                    }
                                                };
                                                this.hitShake = 20.0F;
                                                this.collides = false;
                                                this.pierce = true;
                                                this.absorbable = false;
                                                this.hittable = false;
                                                this.pierceBuilding = true;
                                                this.backColor = Color.valueOf("FF5B5B");
                                                this.frontColor = Color.valueOf("EEC591");
                                                heatColor = Color.valueOf("EEC591");
                                                this.hitSound = Sounds.plasmaboom;
                                                this.hitEffect = new MultiEffect(new Effect[]{new ParticleEffect() {
                                                    {
                                                        this.particles = 60;
                                                        this.line = true;
                                                        this.strokeFrom = 4.0F;
                                                        this.strokeTo = 0.0F;
                                                        this.lenFrom = 85.0F;
                                                        this.lenTo = 23.0F;
                                                        this.length = 203.0F;
                                                        this.baseLength = 20.0F;
                                                        this.lifetime = 30.0F;
                                                        this.colorFrom = Color.valueOf("FFC397FF");
                                                        this.colorTo = Color.valueOf("FFC397FF");
                                                    }
                                                }, new WaveEffect() {
                                                    {
                                                        this.lifetime = 15.0F;
                                                        this.sizeFrom = 0.0F;
                                                        this.sizeTo = 230.0F;
                                                        this.strokeFrom = 19.0F;
                                                        this.strokeTo = 0.0F;
                                                        this.colorFrom = Color.valueOf("FFC397FF");
                                                        this.colorTo = Color.valueOf("FFC397FF");
                                                    }
                                                }});
                                                this.despawnEffect = Fx.none;
                                                this.fragBullets = 1;
                                                this.fragLifeMin = 1.0F;
                                                this.fragLifeMax = 1.0F;
                                                this.fragVelocityMin = 0.0F;
                                                this.fragVelocityMax = 0.0F;
                                                this.fragBullet = WHBullets.tankAG7;
                                            }
                                        };
                                    }
                                };
                            }
                        };
                    }

                    public void draw(Unit unit, WeaponMount mount) {
                        float z = Draw.z();
                        Tmp.v1.trns(unit.rotation, this.y);
                        float f = 1.0F - mount.reload / this.reload;
                        float rad = 12.0F;
                        float f1 = Mathf.curve(f, 0.4F, 1.0F);
                        Draw.z(100.0F);
                        Draw.color(this.heatColor);
                        TextureRegion arrowRegion = WHContent.arrowRegion;
                        Tmp.v6.set(mount.aimX, mount.aimY).sub(unit);
                        Tmp.v2.set(mount.aimX, mount.aimY).sub(unit).nor().scl(Math.min(Tmp.v6.len(), this.rangeWeapon)).add(unit);

                        for(int l = 0; l < 4; ++l) {
                            float angle = (float)(45 + 90 * l);

                            for(int i = 0; i < 3; ++i) {
                                Tmp.v3.trns(angle, (float)((i - 4) * 8 + 8)).add(Tmp.v2);
                                float fS = (100.0F - (Time.time + (float)(25 * i)) % 100.0F) / 100.0F * f1 / 4.0F;
                                Draw.rect(arrowRegion, Tmp.v3.x, Tmp.v3.y, (float)arrowRegion.width * fS, (float)arrowRegion.height * fS, angle + 90.0F);
                            }
                        }

                        Lines.stroke((1.0F + Mathf.absin(Time.time + 4.0F, 8.0F, 1.5F)) * f1, this.heatColor);
                        Lines.square(Tmp.v2.x, Tmp.v2.y, 4.0F + Mathf.absin(8.0F, 4.0F), 45.0F);
                        Lines.stroke(rad / 2.5F * mount.heat, this.heatColor);
                        Lines.circle(unit.x + Tmp.v1.x, unit.y + Tmp.v1.y, rad * 2.0F * (1.0F - mount.heat));
                        Draw.color(this.heatColor);
                        Draw.z(z);
                    }
                });
                this.weapons.add(new Weapon("wh-tankAG-weapon1") {
                    {
                        this.x = -40.0F;
                        this.y = 33.5F;
                        this.shootY = 16.0F;
                        this.reload = 60.0F;
                        this.rotate = true;
                        this.alternate = true;
                        this.mirror = true;
                        this.rotateSpeed = 1.5F;
                        this.shootSound = Sounds.missile;
                        this.recoil = 2.0F;
                        this.shoot = new ShootSpread() {
                            {
                                this.spread = 1.2F;
                                this.shots = 2;
                                this.firstShotDelay = 30.0F;
                            }
                        };
                        this.bullet = new PositionLightningBulletType() {
                            {
                                this.maxRange = 550.0F;
                                this.rangeOverride = 550.0F;
                                this.lightningColor = Color.valueOf("FFA05C");
                                this.lightningDamage = 65.0F;
                                this.lightning = 2;
                                this.lightningLength = 4;
                                this.lightningLengthRand = 6;
                                this.hitEffect = WHFx.lightningSpark;
                                this.spawnBullets.add(new LaserBulletType() {
                                    {
                                        this.damage = 500.0F;
                                        this.length = 550.0F;
                                        this.width = 12.0F;
                                        this.sideAngle = 0.0F;
                                        this.sideWidth = 0.0F;
                                        this.sideLength = 0.0F;
                                        this.laserAbsorb = true;
                                        this.pierce = true;
                                        this.pierceBuilding = false;
                                        this.colors = new Color[]{Color.valueOf("FFA05C"), Color.valueOf("FFA05C"), Color.valueOf("FFA05C")};
                                        this.shootEffect = new ParticleEffect() {
                                            {
                                                this.particles = 1;
                                                this.sizeFrom = 12.0F;
                                                this.sizeTo = 0.0F;
                                                this.length = 0.0F;
                                                this.baseLength = 0.0F;
                                                this.lifetime = 15.0F;
                                                this.colorFrom = Color.valueOf("FFC397FF");
                                                this.colorTo = Color.valueOf("FFC397FF");
                                            }
                                        };
                                        this.smokeEffect = Fx.none;
                                    }
                                });
                            }
                        };
                    }
                });
                this.weapons.add(new Weapon("wh-tankAG-weapon2") {
                    {
                        this.shoot = new ShootAlternate() {
                            {
                                this.barrels = 2;
                                this.spread = 12.5F;
                                this.shots = 6;
                                this.shotDelay = 6.0F;
                            }
                        };
                        this.reload = 60.0F;
                        this.recoil = 3.0F;
                        this.x = 0.0F;
                        this.y = 19.0F;
                        this.shootY = 21.0F;
                        this.mirror = false;
                        this.rotateSpeed = 1.5F;
                        this.rotate = true;
                        this.inaccuracy = 8.0F;
                        this.shootSound = Sounds.shootBig;
                        this.alternate = false;
                        this.ejectEffect = Fx.casing4;
                        this.shootCone = 15.0F;
                        this.bullet = new BasicBulletType() {
                            {
                                this.damage = 140.0F;
                                this.pierce = true;
                                this.pierceBuilding = false;
                                this.pierceArmor = true;
                                this.pierceCap = 5;
                                this.speed = 25.0F;
                                this.lifetime = 22.0F;
                                this.hitSound = Sounds.explosion;
                                this.shootEffect = Fx.shootBig2;
                                this.smokeEffect = Fx.shootBigSmoke;
                                this.trailLength = 4;
                                this.trailWidth = 3.3F;
                                this.trailColor = Color.valueOf("FFC397FF");
                                this.backColor = Color.valueOf("FFC397FF");
                                this.frontColor = Color.valueOf("FFFFFF");
                                this.width = 14.0F;
                                this.height = 33.0F;
                                this.hitEffect = new ParticleEffect() {
                                    {
                                        this.particles = 15;
                                        this.line = true;
                                        this.strokeFrom = 3.0F;
                                        this.strokeTo = 0.0F;
                                        this.lenFrom = 0.0F;
                                        this.lenTo = 11.0F;
                                        this.length = 60.0F;
                                        this.baseLength = 0.0F;
                                        this.lifetime = 10.0F;
                                        this.colorFrom = Color.valueOf("FFC397FF");
                                        this.colorTo = Color.valueOf("FFFFFF");
                                        this.cone = 60.0F;
                                    }
                                };
                                this.despawnEffect = Fx.flakExplosionBig;
                            }
                        };
                    }
                });
                this.weapons.add(new Weapon("wh-tankAG-weapon3") {
                    {
                        this.reload = 15.0F;
                        this.recoil = 2.0F;
                        this.recoilTime = 20.0F;
                        this.mirror = false;
                        this.x = 0.0F;
                        this.y = -21.0F;
                        this.rotate = true;
                        this.rotateSpeed = 0.8F;
                        this.shootCone = 20.0F;
                        this.xRand = 1.0F;
                        this.inaccuracy = 5.0F;
                        this.shootSound = Sounds.laser;
                        this.shake = 1.0F;
                        this.layerOffset = 0.02F;
                        this.recoils = 2;
                        this.cooldownTime = 120.0F;
                        this.heatColor = Color.valueOf("FF4040");
                        this.shoot = new ShootBarrel() {
                            {
                                this.shots = 1;
                                this.barrels = new float[]{-24.0F, 62.0F, 0.0F, 24.0F, 62.0F, 0.0F};
                            }
                        };
                        this.ejectEffect = Fx.none;
                        this.velocityRnd = 0.06F;
                        this.parts.add(new RegionPart("-管l") {
                            {
                                this.y = -2.0F;
                                this.outline = true;
                                this.mirror = false;
                                this.moveY = -20.0F;
                                this.under = true;
                                this.recoilIndex = 0;
                                this.heatColor = Color.valueOf("FF4040");
                                this.heatProgress = PartProgress.recoil;
                                this.progress = PartProgress.recoil;
                            }
                        }, new RegionPart("-管r") {
                            {
                                this.y = -2.0F;
                                this.outline = true;
                                this.mirror = false;
                                this.moveY = -20.0F;
                                this.under = true;
                                this.recoilIndex = 1;
                                this.heatColor = Color.valueOf("FF4040");
                                this.heatProgress = PartProgress.recoil;
                                this.progress = PartProgress.recoil;
                            }
                        });
                        this.bullet = new BasicBulletType(10.3F, 900.0F) {
                            public final float hitChance;

                            {
                                this.keepVelocity = false;
                                this.sprite = "wh-透彻";
                                this.absorbable = false;
                                this.hittable = true;
                                this.pierce = true;
                                this.lightningColor = Color.valueOf("FFC397FF");
                                this.pierceArmor = true;
                                this.pierceBuilding = true;
                                this.pierceCap = 8;
                                this.splashDamageRadius = 48.0F;
                                this.splashDamage = 300.0F;
                                this.hitEffect = WHFx.sharpBlast(Color.valueOf("FFC397FF"), Color.valueOf("FFC397FF"), 35.0F, this.splashDamageRadius * 1.25F);
                                this.despawnEffect = WHFx.lightningHitLarge(Color.valueOf("FFC397FF"));
                                this.trailRotation = true;
                                this.hitSound = Sounds.plasmaboom;
                                this.trailLength = 12;
                                this.trailWidth = 5.0F;
                                this.trailColor = Color.valueOf("FFC397FF");
                                this.backColor = Color.valueOf("FFC397FF");
                                this.frontColor = Color.valueOf("FFFFFF");
                                this.drag = -0.07F;
                                this.width = 23.0F;
                                this.height = 75.0F;
                                this.hitSize = 25.0F;
                                this.lifetime = 25.5F;
                                this.hitChance = 0.2F;
                            }

                            public void despawned(Bullet b) {
                                Units.nearbyEnemies(b.team, b.x, b.y, this.splashDamageRadius, (u) -> {
                                    if (u.checkTarget(this.collidesAir, this.collidesGround) && u.type != null && u.targetable(b.team) && Mathf.chance(0.20000000298023224)) {
                                        float pDamage = this.splashDamage * 3.0F;
                                        if (u.health <= pDamage) {
                                            u.kill();
                                        } else {
                                            u.health -= pDamage;
                                        }
                                    }

                                });
                                super.despawned(b);
                            }
                        };
                    }
                });
            }
        };
    }
}
