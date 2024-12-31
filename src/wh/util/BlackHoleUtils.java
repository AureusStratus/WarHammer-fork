//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.util;

import arc.func.Cons;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.IntSet;
import arc.struct.ObjectSet;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.UnitTypes;
import mindustry.core.World;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.ContinuousBulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.entities.bullet.SapBulletType;
import mindustry.entities.bullet.ShrapnelBulletType;
import mindustry.game.Team;
import mindustry.gen.BlockUnitUnit;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.gen.Posc;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import wh.entities.bullet.BlackHoleBulletType;

public class BlackHoleUtils {
    private static final IntSet collidedBlocks = new IntSet();
    public static final ObjectSet<Class<?>> immuneBulletTypes = ObjectSet.with(new Class[]{ContinuousBulletType.class, LaserBulletType.class, SapBulletType.class, ShrapnelBulletType.class, BlackHoleBulletType.class});
    public static final ObjectSet<BulletType> immuneBullets = new ObjectSet();
    public static final ObjectSet<Class<?>> immuneUnitTypes = new ObjectSet();
    public static final ObjectSet<Class<?>> immuneUnitComps = ObjectSet.with(new Class[]{BlockUnitUnit.class});
    public static final ObjectSet<UnitType> immuneUnits;

    public BlackHoleUtils() {
    }

    public static void blackHoleUpdate(Team team, Posc source, float offsetX, float offsetY, float damageRadius, float suctionRadius, float damage, boolean pierceArmor, float buildingDamageMultiplier, float bulletDamage, boolean repel, float force, float scaledForce, float bulletForce, float scaledBulletForce) {
        float x = source.x() + offsetX;
        float y = source.y() + offsetY;
        if (damage > 0.0F) {
            completeDamage(team, x, y, damageRadius, damage, buildingDamageMultiplier, pierceArmor);
        }

        Units.nearbyEnemies(team, x - suctionRadius, y - suctionRadius, suctionRadius * 2.0F, suctionRadius * 2.0F, (unit) -> {
            float rad = suctionRadius + unit.hitSize / 2.0F;
            if (!unit.type.internal && !isUnitImmune(unit) && unit.hittable() && unit.within(x, y, rad) && source != unit) {
                Vec2 impulse = Tmp.v1.trns(unit.angleTo(x, y), force + (1.0F - unit.dst(x, y) / rad) * scaledForce);
                if (repel) {
                    impulse.rotate(180.0F);
                }

                unit.impulseNet(impulse);
            }

        });
        Groups.bullet.intersect(x - suctionRadius, y - suctionRadius, suctionRadius * 2.0F, suctionRadius * 2.0F, (other) -> {
            if (other != null && !isBulletImmune(other.type) && Mathf.within(x, y, other.x, other.y, suctionRadius) && source != other && team != other.team && other.type.speed > 0.01F) {
                Vec2 impulse = Tmp.v1.trns(other.angleTo(x, y), bulletForce + (1.0F - other.dst(x, y) / suctionRadius) * scaledBulletForce);
                if (repel) {
                    impulse.rotate(180.0F);
                }

                other.vel().add(impulse);
                if (other.isRemote()) {
                    other.move(impulse.x, impulse.y);
                }

                if (bulletDamage > 0.0F && other.type.hittable && Mathf.within(x, y, other.x, other.y, damageRadius)) {
                    if (other.damage > bulletDamage) {
                        other.damage(other.damage - bulletDamage);
                    } else {
                        other.remove();
                    }
                }
            }

        });
    }

    public static void blackHoleUpdate(Team team, Posc source, float offsetX, float offsetY, float damageRadius, float suctionRadius, float damage, float bulletDamage, boolean repel, float force, float scaledForce, float bulletForce, float scaledBulletForce) {
        blackHoleUpdate(team, source, offsetX, offsetY, damageRadius, suctionRadius, damage, false, 1.0F, bulletDamage, repel, force, scaledForce, bulletForce, scaledBulletForce);
    }

    public static void blackHoleUpdate(Team team, Posc source, float damageRadius, float suctionRadius, float damage, boolean pierceArmor, float buildingDamageMultiplier, float bulletDamage, boolean repel, float force, float scaledForce, float bulletForce, float scaledBulletForce) {
        blackHoleUpdate(team, source, 0.0F, 0.0F, damageRadius, suctionRadius, damage, pierceArmor, buildingDamageMultiplier, bulletDamage, repel, force, scaledForce, bulletForce, scaledBulletForce);
    }

    public static void blackHoleUpdate(Team team, Posc source, float damageRadius, float suctionRadius, float damage, float bulletDamage, boolean repel, float force, float scaledForce, float bulletForce, float scaledBulletForce) {
        blackHoleUpdate(team, source, 0.0F, 0.0F, damageRadius, suctionRadius, damage, bulletDamage, repel, force, scaledForce, bulletForce, scaledBulletForce);
    }

    public static boolean isBulletImmune(BulletType type) {
        return immuneBulletTypes.toSeq().contains((c) -> {
            return c.isAssignableFrom(type.getClass());
        }) || immuneBullets.contains(type);
    }

    public static boolean isUnitImmune(Unit unit) {
        return immuneUnitTypes.toSeq().contains((c) -> {
            return c.isAssignableFrom(unit.type.getClass());
        }) || immuneUnitComps.toSeq().contains((c) -> {
            return c.isAssignableFrom(unit.getClass());
        }) || immuneUnits.contains(unit.type);
    }

    public static void completeDamage(Team team, float x, float y, float radius, float damage) {
        completeDamage(team, x, y, radius, damage, 1.0F, false);
    }

    public static void completeDamage(Team team, float x, float y, float radius, float damage, float buildingDamageMultiplier, boolean pierceArmor) {
        Units.nearbyEnemies(team, x - radius, y - radius, radius * 2.0F, radius * 2.0F, (unit) -> {
            if (!unit.dead && unit.hittable() && unit.within(x, y, radius + unit.hitSize / 2.0F)) {
                if (pierceArmor) {
                    unit.damagePierce(damage);
                } else {
                    unit.damage(damage);
                }
            }

        });
        trueEachBlock(x, y, radius, (build) -> {
            if (build.team != team && !build.dead && build.block != null) {
                if (pierceArmor) {
                    build.damagePierce(damage * buildingDamageMultiplier);
                } else {
                    build.damage(damage * buildingDamageMultiplier);
                }
            }

        });
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

    static {
        immuneUnits = ObjectSet.with(new UnitType[]{UnitTypes.block});
    }
}
