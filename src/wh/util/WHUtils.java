//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.util;

import arc.Core;
import arc.func.Cons;
import arc.func.Intc2;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Geometry;
import arc.math.geom.Intersector;
import arc.math.geom.Point2;
import arc.math.geom.Rect;
import arc.math.geom.Vec2;
import arc.struct.IntSet;
import arc.struct.Seq;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.core.World;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Posc;
import mindustry.gen.Unit;
import mindustry.type.Liquid;
import mindustry.world.Tile;
import org.jetbrains.annotations.Contract;

public final class WHUtils {
    public static final Rand rand = new Rand(0L);
    private static Tile tileParma;
    private static Posc result;
    private static float cdist;
    private static int idx;
    private static final Vec2 tV = new Vec2();
    private static final Vec2 tV2 = new Vec2();
    private static final Vec2 tV3 = new Vec2();
    private static final IntSet collidedBlocks = new IntSet();
    private static final Rect rect = new Rect();
    private static final Rect hitRect = new Rect();
    private static final Seq<Tile> tiles = new Seq();
    private static final Seq<Unit> units = new Seq();
    private static Building tmpBuilding;
    private static Unit tmpUnit;
    private static boolean hit;
    private static boolean hitB;

    private WHUtils() {
    }

    @Contract(
            pure = true
    )
    public static int reverse(int rotation) {
        byte var10000;
        switch (rotation) {
            case 0:
                var10000 = 2;
                break;
            case 1:
                var10000 = 3;
                break;
            case 2:
                var10000 = 0;
                break;
            case 3:
                var10000 = 1;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + rotation);
        }

        return var10000;
    }

    public static TextureRegion[][] splitLayers(String name, int size, int layerCount) {
        TextureRegion[][] layers = new TextureRegion[layerCount][];

        for(int i = 0; i < layerCount; ++i) {
            layers[i] = split(name, size, i);
        }

        return layers;
    }

    public static TextureRegion[] split(String name, int size, int layer) {
        TextureRegion textures = Core.atlas.find(name);
        int margin = 0;
        int countX = textures.width / size;
        TextureRegion[] tiles = new TextureRegion[countX];

        for(int i = 0; i < countX; ++i) {
            tiles[i] = new TextureRegion(textures, i * (margin + size), layer * (margin + size), size, size);
        }

        return tiles;
    }

    public static TextureRegion[] split(String name, int size, int width, int height) {
        TextureRegion textures = Core.atlas.find(name);
        int textureSize = width * height;
        TextureRegion[] regions = new TextureRegion[textureSize];
        float tileWidth = (textures.u2 - textures.u) / (float)width;
        float tileHeight = (textures.v2 - textures.v) / (float)height;

        for(int i = 0; i < textureSize; ++i) {
            float tileX = (float)(i % width) / (float)width;
            float tileY = (float)(i / width) / (float)height;
            TextureRegion region = new TextureRegion(textures);
            region.u = Mathf.map(tileX, 0.0F, 1.0F, region.u, region.u2) + tileWidth * 0.02F;
            region.v = Mathf.map(tileY, 0.0F, 1.0F, region.v, region.v2) + tileHeight * 0.02F;
            region.u2 = region.u + tileWidth * 0.96F;
            region.v2 = region.v + tileHeight * 0.96F;
            region.width = region.height = size;
            regions[i] = region;
        }

        return regions;
    }

    public static int relativeDirection(Building from, Building to) {
        if (from != null && to != null) {
            if (from.x == to.x && from.y > to.y) {
                return (7 - from.rotation) % 4;
            } else if (from.x == to.x && from.y < to.y) {
                return (5 - from.rotation) % 4;
            } else if (from.x > to.x && from.y == to.y) {
                return (6 - from.rotation) % 4;
            } else {
                return from.x < to.x && from.y == to.y ? (4 - from.rotation) % 4 : -1;
            }
        } else {
            return -1;
        }
    }

    public static void drawTiledFramesBar(float w, float h, float x, float y, Liquid liquid, float alpha) {
        TextureRegion region = Vars.renderer.fluidFrames[liquid.gas ? 1 : 0][liquid.getAnimationFrame()];
        Draw.color(liquid.color, liquid.color.a * alpha);
        Draw.rect(region, x + w / 2.0F, y + h / 2.0F, w, h);
        Draw.color();
    }

    public static float findLaserLength(Bullet b, float angle, float length) {
        Tmp.v1.trnsExact(angle, length);
        tileParma = null;
        boolean found = World.raycast(b.tileX(), b.tileY(), World.toTile(b.x + Tmp.v1.x), World.toTile(b.y + Tmp.v1.y), (x, y) -> {
            return (tileParma = Vars.world.tile(x, y)) != null && tileParma.team() != b.team && tileParma.block().absorbLasers;
        });
        return found && tileParma != null ? Math.max(6.0F, b.dst(tileParma.worldx(), tileParma.worldy())) : length;
    }

    public static void collideLine(Bullet hitter, Team team, Effect effect, float x, float y, float angle, float length, boolean large, boolean laser) {
        if (laser) {
            length = findLaserLength(hitter, angle, length);
        }

        collidedBlocks.clear();
        tV.trnsExact(angle, length);
        Intc2 collider = (cx, cy) -> {
            Building tile = Vars.world.build(cx, cy);
            boolean collide = tile != null && collidedBlocks.add(tile.pos());
            if (hitter.damage > 0.0F) {
                float health = !collide ? 0.0F : tile.health;
                if (collide && tile.team != team && tile.collide(hitter)) {
                    tile.collision(hitter);
                    hitter.type.hit(hitter, tile.x, tile.y);
                }

                if (collide && hitter.type.testCollision(hitter, tile)) {
                    hitter.type.hitTile(hitter, tile, (float)(cx * 8), (float)(cy * 8), health, false);
                }
            }

        };
        if (hitter.type.collidesGround) {
            tV2.set(x, y);
            tV3.set(tV2).add(tV);
            World.raycastEachWorld(x, y, tV3.x, tV3.y, (cx, cy) -> {
                collider.get(cx, cy);
                Point2[] var4 = Geometry.d4;
                int var5 = var4.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    Point2 p = var4[var6];
                    Tile other = Vars.world.tile(p.x + cx, p.y + cy);
                    if (other != null && (large || Intersector.intersectSegmentRectangle(tV2, tV3, other.getBounds(Tmp.r1)))) {
                        collider.get(cx + p.x, cy + p.y);
                    }
                }

                return false;
            });
        }

        rect.setPosition(x, y).setSize(tV.x, tV.y);
        float x2 = tV.x + x;
        float y2 = tV.y + y;
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
        Cons<Unit> cons = (unit) -> {
            unit.hitbox(hitRect);
            Vec2 vec = Geometry.raycastRect(x, y, x2, y2, hitRect.grow(expand * 2.0F));
            if (vec != null && hitter.damage > 0.0F) {
                effect.at(vec.x, vec.y);
                unit.collision(hitter, vec.x, vec.y);
                hitter.collision(unit, vec.x, vec.y);
            }

        };
        units.clear();
        Units.nearbyEnemies(team, rect, (unit) -> {
            if (unit.checkTarget(hitter.type.collidesAir, hitter.type.collidesGround)) {
                units.add(unit);
            }

        });
        units.sort((unit) -> {
            return unit.dst2(hitter);
        });
        units.each(cons);
    }

    public static Rand rand(long id) {
        rand.setSeed(id);
        return rand;
    }

    public static <T> void shuffle(Seq<T> seq, Rand rand) {
        T[] items = seq.items;

        for(int i = seq.size - 1; i >= 0; --i) {
            int ii = Mathf.random(i);
            T temp = items[i];
            items[i] = items[ii];
            items[ii] = temp;
        }

    }

    public static class EffectWrapper extends Effect {
        public Effect effect;
        public Color color;
        public float rot;
        public boolean rotModifier;

        public EffectWrapper() {
            this.effect = Fx.none;
            this.color = Color.white.cpy();
            this.rot = -1.0F;
            this.rotModifier = false;
        }

        public EffectWrapper(Effect effect, Color color) {
            this.effect = Fx.none;
            this.color = Color.white.cpy();
            this.rot = -1.0F;
            this.rotModifier = false;
            this.effect = effect;
            this.color = color;
        }

        public EffectWrapper(Effect effect, Color color, float rot) {
            this.effect = Fx.none;
            this.color = Color.white.cpy();
            this.rot = -1.0F;
            this.rotModifier = false;
            this.effect = effect;
            this.color = color;
            this.rot = rot;
        }

        public static EffectWrapper wrap(Effect effect, Color color) {
            return new EffectWrapper(effect, color);
        }

        public static EffectWrapper wrap(Effect effect, Color color, float rot) {
            return new EffectWrapper(effect, color, rot);
        }

        public static EffectWrapper wrap(Effect effect, float rot, boolean rotModifier) {
            return (new EffectWrapper(effect, Color.white, rot)).setRotModifier(rotModifier);
        }

        public EffectWrapper setRotModifier(boolean rotModifier) {
            this.rotModifier = rotModifier;
            return this;
        }

        public void init() {
            this.effect.init();
            this.clip = this.effect.clip;
            this.lifetime = this.effect.lifetime;
        }

        public void render(Effect.EffectContainer e) {
        }

        public void create(float x, float y, float rotation, Color color, Object data) {
            this.effect.create(x, y, this.rot > 0.0F ? (this.rotModifier ? this.rot + rotation : this.rot) : rotation, this.color, data);
        }
    }
}
