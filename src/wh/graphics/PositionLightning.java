//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.graphics;

import arc.func.Cons;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Geometry;
import arc.math.geom.Position;
import arc.math.geom.Rect;
import arc.math.geom.Vec2;
import arc.struct.FloatSeq;
import arc.struct.Seq;
import java.util.Iterator;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.core.World;
import mindustry.entities.Lightning;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Entityc;
import mindustry.gen.Healthc;
import wh.content.WHFx;
import wh.entities.bullet.EffectBulletType;
import wh.struct.Vec2Seq;

public final class PositionLightning {
    public static final BulletType hitter = new EffectBulletType(5.0F) {
        {
            this.absorbable = true;
            this.collides = this.collidesAir = this.collidesGround = this.collidesTiles = true;
            this.status = StatusEffects.shocked;
            this.statusDuration = 10.0F;
            this.hittable = false;
        }
    };
    public static final Cons<Position> none = (p) -> {
    };
    public static final float lifetime;
    public static final float WIDTH = 2.5F;
    public static final float RANGE_RAND = 5.0F;
    public static final float ROT_DST = 4.8F;
    public static float trueHitChance;
    private static Building furthest;
    private static final Rect rect;
    private static final Rand rand;
    private static final FloatSeq floatSeq;
    private static final Vec2 tmp1;
    private static final Vec2 tmp2;
    private static final Vec2 tmp3;

    private PositionLightning() {
    }

    public static void setHitChance(float f) {
        trueHitChance = f;
    }

    public static void setHitChanceDef() {
        trueHitChance = 1.0F;
    }

    private static float getBoltRandomRange() {
        return Mathf.random(1.0F, 7.0F);
    }

    public static void createRange(Bullet owner, float range, int maxHit, Color color, boolean createSubLightning, float width, int lightningNum, Cons<Position> hitPointMovement) {
        createRange(owner, owner, owner.team, range, maxHit, color, createSubLightning, 0.0F, 0, width, lightningNum, hitPointMovement);
    }

    public static void createRange(Bullet owner, boolean hitAir, boolean hitGround, Position from, Team team, float range, int maxHit, Color color, boolean createSubLightning, float damage, int subLightningLength, float width, int lightningNum, Cons<Position> hitPointMovement) {
        Seq<Healthc> entities = new Seq();
        whetherAdd(entities, team, rect.setSize(range * 2.0F).setCenter(from.getX(), from.getY()), maxHit, hitGround, hitAir);
        Iterator var15 = entities.iterator();

        while(var15.hasNext()) {
            Healthc p = (Healthc)var15.next();
            create(owner, team, from, p, color, createSubLightning, damage, subLightningLength, width, lightningNum, hitPointMovement);
        }

    }

    public static void createRange(Bullet owner, Position from, Team team, float range, int maxHit, Color color, boolean createSubLightning, float damage, int subLightningLength, float width, int lightningNum, Cons<Position> hitPointMovement) {
        createRange(owner, owner == null || owner.type.collidesAir, owner == null || owner.type.collidesGround, from, team, range, maxHit, color, createSubLightning, damage, subLightningLength, width, lightningNum, hitPointMovement);
    }

    public static void createLength(Bullet owner, Team team, Position from, float length, float angle, Color color, boolean createSubLightning, float damage, int subLightningLength, float width, int lightningNum, Cons<Position> hitPointMovement) {
        create(owner, team, from, tmp2.trns(angle, length).add(from), color, createSubLightning, damage, subLightningLength, width, lightningNum, hitPointMovement);
    }

    public static void create(Entityc owner, Team team, Position from, Position target, Color color, boolean createSubLightning, float damage, int subLightningLength, float lightningWidth, int lightningNum, Cons<Position> hitPointMovement) {
        if (Mathf.chance((double)trueHitChance)) {
            Position sureTarget = findInterceptedPoint(from, target, team);
            hitPointMovement.get(sureTarget);
            if (createSubLightning) {
                int i;
                if (owner instanceof Bullet) {
                    Bullet b = (Bullet)owner;

                    for(i = 0; i < b.type.lightning; ++i) {
                        Lightning.create(b, color, b.type.lightningDamage < 0.0F ? b.damage : b.type.lightningDamage, sureTarget.getX(), sureTarget.getY(), b.rotation() + Mathf.range(b.type.lightningCone / 2.0F) + b.type.lightningAngle, b.type.lightningLength + Mathf.random(b.type.lightningLengthRand));
                    }
                } else {
                    for(i = 0; i < 3; ++i) {
                        Lightning.create(team, color, damage <= 0.0F ? 1.0F : damage, sureTarget.getX(), sureTarget.getY(), Mathf.random(360.0F), subLightningLength);
                    }
                }
            }

            float realDamage = damage;
            if (damage <= 0.0F) {
                if (owner instanceof Bullet) {
                    Bullet b = (Bullet)owner;
                    realDamage = b.damage > 0.0F ? b.damage : 1.0F;
                } else {
                    realDamage = 1.0F;
                }
            }

            hitter.create(owner, team, sureTarget.getX(), sureTarget.getY(), 1.0F).damage(realDamage);
            createEffect(from, sureTarget, color, lightningNum, lightningWidth);
        }
    }

    public static void createRandom(Bullet owner, Team team, Position from, float rand, Color color, boolean createSubLightning, float damage, int subLightningLength, float width, int lightningNum, Cons<Position> hitPointMovement) {
        create(owner, team, from, tmp2.rnd(rand).scl(Mathf.random(1.0F)).add(from), color, createSubLightning, damage, subLightningLength, width, lightningNum, hitPointMovement);
    }

    public static void createRandom(Team team, Position from, float rand, Color color, boolean createSubLightning, float damage, int subLightningLength, float width, int lightningNum, Cons<Position> hitPointMovement) {
        createRandom((Bullet)null, team, from, rand, color, createSubLightning, damage, subLightningLength, width, lightningNum, hitPointMovement);
    }

    public static void createRandomRange(Team team, Position from, float rand, Color color, boolean createSubLightning, float damage, int subLightningLength, float width, int lightningNum, int generateNum, Cons<Position> hitPointMovement) {
        createRandomRange((Bullet)null, team, from, rand, color, createSubLightning, damage, subLightningLength, width, lightningNum, generateNum, hitPointMovement);
    }

    public static void createRandomRange(Bullet owner, float rand, Color color, boolean createSubLightning, float damage, float width, int lightningNum, int generateNum, Cons<Position> hitPointMovement) {
        createRandomRange(owner, owner.team, owner, rand, color, createSubLightning, damage, owner.type.lightningLength + Mathf.random(owner.type.lightningLengthRand), width, lightningNum, generateNum, hitPointMovement);
    }

    public static void createRandomRange(Bullet owner, Team team, Position from, float rand, Color color, boolean createSubLightning, float damage, int subLightningLength, float width, int lightningNum, int generateNum, Cons<Position> hitPointMovement) {
        for(int i = 0; i < generateNum; ++i) {
            createRandom(owner, team, from, rand, color, createSubLightning, damage, subLightningLength, width, lightningNum, hitPointMovement);
        }

    }

    public static void createEffect(Position from, float length, float angle, Color color, int lightningNum, float width) {
        if (!Vars.headless) {
            createEffect(from, tmp2.trns(angle, length).add(from), color, lightningNum, width);
        }
    }

    public static void createEffect(Position from, Position to, Color color, int lightningNum, float width) {
        if (!Vars.headless) {
            if (lightningNum < 1) {
                Fx.chainLightning.at(from.getX(), from.getY(), 0.0F, color, (new Vec2()).set(to));
            } else {
                float dst = from.dst(to);

                for(int i = 0; i < lightningNum; ++i) {
                    float len = getBoltRandomRange();
                    float randRange = len * 5.0F;
                    floatSeq.clear();
                    FloatSeq randomArray = floatSeq;

                    for(int num = 0; (float)num < dst / (4.8F * len) + 1.0F; ++num) {
                        randomArray.add(Mathf.range(randRange) / ((float)num * 0.025F + 1.0F));
                    }

                    createBoltEffect(color, width, computeVectors(randomArray, from, to));
                }
            }

        }
    }

    public static Position findInterceptedPoint(Position from, Position target, Team fromTeam) {
        furthest = null;
        return (Position)(Geometry.raycast(World.toTile(from.getX()), World.toTile(from.getY()), World.toTile(target.getX()), World.toTile(target.getY()), (x, y) -> {
            return (furthest = Vars.world.build(x, y)) != null && furthest.team() != fromTeam && furthest.block().insulated;
        }) && furthest != null ? furthest : target);
    }

    private static void whetherAdd(Seq<Healthc> points, Team team, Rect selectRect, int maxHit, boolean targetGround, boolean targetAir) {
        Units.nearbyEnemies(team, selectRect, (unit) -> {
            if (unit.checkTarget(targetAir, targetGround)) {
                points.add(unit);
            }

        });
        if (targetGround) {
            selectRect.getCenter(tmp3);
            Units.nearbyBuildings(tmp3.x, tmp3.y, selectRect.getHeight() / 2.0F, (b) -> {
                if (b.team != team && b.isValid()) {
                    points.add(b);
                }

            });
        }

        points.shuffle();
        points.truncate(maxHit);
    }

    public static void createBoltEffect(Color color, float width, Vec2Seq vets) {
        vets.each((x, y) -> {
            if (Mathf.chance(0.0855)) {
                WHFx.lightningSpark.at(x, y, rand.random(2.0F + width, 4.0F + width), color);
            }

        });
        WHFx.posLightning.at((vets.firstTmp().x + vets.peekTmp().x) / 2.0F, (vets.firstTmp().y + vets.peekTmp().y) / 2.0F, width, color, vets);
    }

    private static Vec2Seq computeVectors(FloatSeq randomVec, Position from, Position to) {
        int param = randomVec.size;
        float angle = from.angleTo(to);
        Vec2Seq lines = new Vec2Seq(param);
        tmp1.trns(angle, from.dst(to) / (float)(param - 1));
        lines.add(from);

        for(int i = 1; i < param - 2; ++i) {
            lines.add(tmp3.trns(angle - 90.0F, randomVec.get(i)).add(tmp1, (float)i).add(from.getX(), from.getY()));
        }

        lines.add(to);
        return lines;
    }

    static {
        lifetime = Fx.chainLightning.lifetime;
        trueHitChance = 1.0F;
        rect = new Rect();
        rand = new Rand();
        floatSeq = new FloatSeq();
        tmp1 = new Vec2();
        tmp2 = new Vec2();
        tmp3 = new Vec2();
    }
}
