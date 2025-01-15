//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.entities;

import arc.func.Boolf;
import arc.func.Cons;
import arc.func.Intc2;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Intersector;
import arc.math.geom.Point2;
import arc.math.geom.Rect;
import arc.math.geom.Vec2;
import arc.struct.FloatSeq;
import arc.struct.IntFloatMap;
import arc.struct.IntSet;
import arc.struct.Seq;
import arc.util.Tmp;
import arc.util.pooling.Pool;
import arc.util.pooling.Pools;
import mindustry.Vars;
import mindustry.ai.types.MissileAI;
import mindustry.core.World;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Healthc;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import mindustry.world.Tile;

public final class WHDamage {
    private static final EventType.UnitDamageEvent bulletDamageEvent = new EventType.UnitDamageEvent();
    private static final Rect rect = new Rect();
    private static final Rect hitrect = new Rect();
    private static final Vec2 tr = new Vec2();
    private static final Vec2 seg1 = new Vec2();
    private static final Vec2 seg2 = new Vec2();
    private static final Seq<Building> builds = new Seq();
    private static final Seq<Unit> units = new Seq();
    private static final IntSet collidedBlocks = new IntSet();
    private static final IntFloatMap damages = new IntFloatMap();
    private static final Seq<Damage.Collided> collided = new Seq();
    private static final Pool<Damage.Collided> collidePool = Pools.get(Damage.Collided.class, Damage.Collided::new);
    private static final FloatSeq distances = new FloatSeq();
    private static Tile furthest;
    private static Building tmpBuilding;
    private static Unit tmpUnit;
    private static float tmpFloat;
    private static boolean check;

    private WHDamage() {
    }

    public static void trueEachBlock(float wx, float wy, float range, Cons<Building> cons) {
        collidedBlocks.clear();
        int tx = World.toTile(wx);
        int ty = World.toTile(wy);
        int tileRange = Mathf.floorPositive(range / 8.0F);

        for(int x = tx - tileRange - 2; x <= tx + tileRange + 2; ++x) {
            for(int y = ty - tileRange - 2; y <= ty + tileRange + 2; ++y) {
                if (Mathf.within((float)(x * 8), (float)(y * 8), wx, wy, range)) {
                    Building other = Vars.world.build(x, y);
                    if (other != null && !collidedBlocks.contains(other.pos())) {
                        cons.get(other);
                        collidedBlocks.add(other.pos());
                    }
                }
            }
        }

    }


    public static void allNearbyEnemies(Team team, float x, float y, float radius, Cons<Healthc> cons) {
        Units.nearbyEnemies(team, x - radius, y - radius, radius * 2.0F, radius * 2.0F, (unit) -> {
            if (unit.within(x, y, radius + unit.hitSize / 2.0F) && !unit.dead) {
                cons.get(unit);
            }

        });
        trueEachBlock(x, y, radius, (build) -> {
            if (build.team != team && !build.dead && build.block != null) {
                cons.get(build);
            }

        });
    }

    public static boolean checkForTargets(Team team, float x, float y, float radius) {
        check = false;
        Units.nearbyEnemies(team, x - radius, y - radius, radius * 2.0F, radius * 2.0F, (unit) -> {
            if (unit.within(x, y, radius + unit.hitSize / 2.0F) && !unit.dead) {
                check = true;
            }

        });
        trueEachBlock(x, y, radius, (build) -> {
            if (build.team != team && !build.dead && build.block != null) {
                check = true;
            }

        });
        return check;
    }

    public static Teamc bestTarget(Team team, float cx, float cy, float x, float y, float range, Boolf<Unit> unitPred, Boolf<Building> tilePred, Units.Sortf sort) {
        if (team == Team.derelict) {
            return null;
        } else {
            Unit unit = findEnemyUnit(team, cx, cy, x, y, range, unitPred, sort);
            return (Teamc)(unit != null ? unit : findEnemyTile(team, cx, cy, x, y, range, tilePred));
        }
    }

    public static Unit findEnemyUnit(Team team, float cx, float cy, float x, float y, float range, Boolf<Unit> pred, Units.Sortf unitSort) {
        tmpUnit = null;
        tmpFloat = Float.NEGATIVE_INFINITY;
        Units.nearbyEnemies(team, cx - range, cy - range, range * 2.0F, range * 2.0F, (unit) -> {
            float cost = unitSort.cost(unit, x, y);
            if (!unit.dead && tmpFloat < cost && unit.within(cx, cy, range + unit.hitSize / 2.0F) && pred.get(unit)) {
                tmpUnit = unit;
                tmpFloat = cost;
            }

        });
        return tmpUnit;
    }

    public static Building findEnemyTile(Team team, float cx, float cy, float x, float y, float range, Boolf<Building> pred) {
        tmpBuilding = null;
        tmpFloat = 0.0F;
        trueEachBlock(cx, cy, range, (b) -> {
            if (b.team() != team && (b.team() != Team.derelict || Vars.state.rules.coreCapture) && pred.get(b)) {
                float dist = b.dst(x, y) - b.hitSize() / 2.0F;
                if (tmpBuilding == null || dist < tmpFloat && b.block.priority >= tmpBuilding.block.priority || b.block.priority > tmpBuilding.block.priority) {
                    tmpBuilding = b;
                    tmpFloat = dist;
                }
            }

        });
        return tmpBuilding;
    }

    public static boolean collideLine(float damage, Team team, Effect effect, StatusEffect status, float statusDuration, float x, float y, float angle, float length, boolean ground, boolean air) {
        return collideLine(damage, team, effect, status, statusDuration, x, y, angle, length, ground, air, false);
    }

    public static boolean collideLine(float damage, Team team, Effect effect, StatusEffect status, float statusDuration, float x, float y, float angle, float length, boolean ground, boolean air, boolean buildings) {
        tr.trnsExact(angle, length);
        rect.setPosition(x, y).setSize(tr.x, tr.y);
        float x2 = x + tr.x;
        float y2 = y + tr.y;
        Rect var10000;
        if (rect.width < 0.0F) {
            var10000 = rect;
            var10000.x += rect.width;
            var10000 = rect;
            var10000.width *= -1.0F;
        }

        if (rect.height < 0.0F) {
            var10000 = rect;
            var10000.y += rect.height;
            var10000 = rect;
            var10000.height *= -1.0F;
        }

        float expand = 3.0F;
        var10000 = rect;
        var10000.y -= expand;
        var10000 = rect;
        var10000.x -= expand;
        var10000 = rect;
        var10000.width += expand * 2.0F;
        var10000 = rect;
        var10000.height += expand * 2.0F;
        check = false;
        Cons<Unit> cons = (e) -> {
            e.hitbox(hitrect);
            Vec2 vec = Geometry.raycastRect(x, y, x2, y2, hitrect.grow(expand * 2.0F));
            if (vec != null && damage > 0.0F) {
                effect.at(vec.x, vec.y, angle, team.color);
                e.damage(damage);
                e.apply(status, statusDuration);
                check = true;
            }

        };
        units.clear();
        Units.nearbyEnemies(team, rect, (u) -> {
            if (u.checkTarget(air, ground)) {
                units.add(u);
            }

        });
        units.sort((u) -> {
            return u.dst2(x, y);
        });
        units.each(cons);
        if (buildings) {
            collidedBlocks.clear();
            Intc2 collider = (cx, cy) -> {
                Building tile = Vars.world.build(cx, cy);
                boolean collide = tile != null && collidedBlocks.add(tile.pos());
                if (collide && damage > 0.0F && tile.team != team) {
                    effect.at(tile.x, tile.y, angle, team.color);
                    tile.damage(damage);
                    check = true;
                }

            };
            seg1.set(x, y);
            seg2.set(seg1).add(tr);
            World.raycastEachWorld(x, y, seg2.x, seg2.y, (cx, cy) -> {
                collider.get(cx, cy);
                Point2[] var3 = Geometry.d4;
                int var4 = var3.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    Point2 p = var3[var5];
                    Tile other = Vars.world.tile(p.x + cx, p.y + cy);
                    if (other != null && Intersector.intersectSegmentRectangle(seg1, seg2, other.getBounds(Tmp.r1))) {
                        collider.get(cx + p.x, cy + p.y);
                    }
                }

                return false;
            });
        }

        return check;
    }

    public static void missileCollideLine(Bullet hitter, Team team, Effect effect, float x, float y, float angle, float length, boolean large, boolean laser, int pierceCap) {
        if (pierceCap > 0) {
            length = findPierceLength(hitter, pierceCap, length);
        } else if (laser) {
            length = Damage.findLaserLength(hitter, length);
        }

        collidedBlocks.clear();
        tr.trnsExact(angle, length);
        float expand = 3.0F;
        rect.setPosition(x, y).setSize(tr.x, tr.y).normalize().grow(expand * 2.0F);
        float x2 = tr.x + x;
        float y2 = tr.y + y;
        Units.nearbyEnemies(team, rect, (u) -> {
            if (u.checkTarget(hitter.type.collidesAir, hitter.type.collidesGround) && u.hittable() && u.controller() instanceof MissileAI) {
                u.hitbox(hitrect);
                Vec2 vec = Geometry.raycastRect(x, y, x2, y2, hitrect.grow(expand * 2.0F));
                if (vec != null) {
                    collided.add(((Damage.Collided)collidePool.obtain()).set(vec.x, vec.y, u));
                }
            }

        });
        int[] collideCount = new int[]{0};
        collided.sort((c) -> {
            return hitter.dst2(c.x, c.y);
        });
        collided.each((c) -> {
            if (hitter.damage > 0.0F && (pierceCap <= 0 || collideCount[0] < pierceCap)) {
                Teamc patt10696$temp = c.target;
                if (patt10696$temp instanceof Unit) {
                    Unit u = (Unit)patt10696$temp;
                    effect.at(c.x, c.y);
                    u.collision(hitter, c.x, c.y);
                    hitter.collision(u, c.x, c.y);
                    int var10002 = collideCount[0]++;
                }
            }

        });
        collidePool.freeAll(collided);
        collided.clear();
    }

    public static float findLaserLength(float x, float y, float angle, Team team, float length) {
        Tmp.v1.trns(angle, length);
        furthest = null;
        boolean found = World.raycast(World.toTile(x), World.toTile(y), World.toTile(x + Tmp.v1.x), World.toTile(y + Tmp.v1.y), (tx, ty) -> {
            return (furthest = Vars.world.tile(tx, ty)) != null && furthest.team() != team && furthest.block().absorbLasers;
        });
        return found && furthest != null ? Math.max(6.0F, Mathf.dst(x, y, furthest.worldx(), furthest.worldy())) : length;
    }

    public static float findPierceLength(Bullet b, int pierceCap, float length) {
        tr.trnsExact(b.rotation(), length);
        rect.setPosition(b.x, b.y).setSize(tr.x, tr.y).normalize().grow(3.0F);
        tmpFloat = Float.POSITIVE_INFINITY;
        distances.clear();
        World.raycast(b.tileX(), b.tileY(), World.toTile(b.x + tr.x), World.toTile(b.y + tr.y), (x, y) -> {
            Building build = Vars.world.build(x, y);
            if (build != null && build.team != b.team && build.collide(b) && b.checkUnderBuild(build, (float)(x * 8), (float)(y * 8))) {
                float dst = b.dst((float)(x * 8), (float)(y * 8)) - 8.0F;
                distances.add(dst);
                if (b.type.laserAbsorb && build.absorbLasers()) {
                    tmpFloat = Math.min(tmpFloat, dst);
                    return true;
                }
            }

            return false;
        });
        Units.nearbyEnemies(b.team, rect, (u) -> {
            u.hitbox(hitrect);
            if (u.checkTarget(b.type.collidesAir, b.type.collidesGround) && u.hittable() && Intersector.intersectSegmentRectangle(b.x, b.y, b.x + tr.x, b.y + tr.y, hitrect)) {
                distances.add(b.dst(u) - u.hitSize());
            }

        });
        distances.sort();
        return Math.min(distances.size >= pierceCap && pierceCap >= 0 ? Math.max(6.0F, distances.get(pierceCap - 1)) : length, tmpFloat);
    }
}
