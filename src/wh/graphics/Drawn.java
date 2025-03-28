//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.graphics;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Font;
import arc.graphics.g2d.GlyphLayout;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Mat3D;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.math.geom.Vec3;
import arc.scene.ui.layout.Scl;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.pooling.Pools;
import mindustry.Vars;
import mindustry.gen.Player;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.ui.Fonts;
import wh.content.WHFx;
import wh.math.WHInterp;
import wh.util.WHUtils;

public final class Drawn {
    public static final int[] oneArr = new int[]{1};
    public static final float sinScl = 1.0F;
    public static final float[] v = new float[6];
    public static final Rand rand = new Rand(0L);
    public static final Color bottomColor = Pal.gray;
    static final Vec3[] tmpV = new Vec3[4];
    static final Mat3D matT = new Mat3D();
    static final Vec3 tAxis = new Vec3();
    static final Vec3 tmpV2 = new Vec3();
    static final TextureRegion t1 = new TextureRegion();
    static final TextureRegion t2 = new TextureRegion();
    static final TextureRegion nRegion = new TextureRegion();
    static final Vec2 v1 = new Vec2();
    static final Vec2 v2 = new Vec2();
    static final Vec2 v3 = new Vec2();
    static final Vec2 v4 = new Vec2();
    static final Vec2 v5 = new Vec2();
    static final Vec2 v6 = new Vec2();
    static final Vec2 v7 = new Vec2();
    static final Vec2 v8 = new Vec2();
    static final Vec2 v9 = new Vec2();
    static final Vec2 v10 = new Vec2();
    static final Vec2 v11 = new Vec2();
    static final Vec2 v12 = new Vec2();
    static final Vec2 v13 = new Vec2();
    static final Vec2 rv = new Vec2();
    static final Vec3 v31 = new Vec3();
    static final Vec3 v32 = new Vec3();
    static final Vec3 v33 = new Vec3();
    static final Vec3 v34 = new Vec3();
    static final Vec3 v35 = new Vec3();
    static final Vec3 v36 = new Vec3();
    static final Vec3 v37 = new Vec3();
    static final Vec3 v38 = new Vec3();
    static final Vec3 v39 = new Vec3();
    static final Vec3 v310 = new Vec3();
    static final Color c1 = new Color();
    static final Color c2 = new Color();
    static final Color c3 = new Color();
    static final Color c4 = new Color();
    static final Color c5 = new Color();
    static final Color c6 = new Color();
    static final Color c7 = new Color();
    static final Color c8 = new Color();
    static final Color c9 = new Color();
    static final Color c10 = new Color();

    private Drawn() {
    }

    public static void teleportUnitNet(Unit before, float x, float y, float angle, Player player) {
        if (!Vars.net.active() && !Vars.headless) {
            before.set(x, y);
        } else {
            if (player != null) {
                player.set(x, y);
                player.snapInterpolation();
                player.snapSync();
                player.lastUpdated = player.updateSpacing = 0L;
            }

            before.set(x, y);
            before.snapInterpolation();
            before.snapSync();
            before.updateSpacing = 0L;
            before.lastUpdated = 0L;
        }

        before.rotation = angle;
    }

    public static void circlePercent(float x, float y, float rad, float percent, float angle) {
//        Lines.swirl(x, y, rad, 360 * percent, angle);
        float p = Mathf.clamp(percent);

        int sides = Lines.circleVertices(rad);

        float space = 360.0F / (float)sides;
        float len = 2 * rad * Mathf.sinDeg(space / 2);
        float hstep = Lines.getStroke() / 2.0F / Mathf.cosDeg(space / 2.0F);
        float r1 = rad - hstep;
        float r2 = rad + hstep;

        int i;

        for(i = 0; i < sides * p - 1; ++i){
            float a = space * (float)i + angle;
            float cos = Mathf.cosDeg(a);
            float sin = Mathf.sinDeg(a);
            float cos2 = Mathf.cosDeg(a + space);
            float sin2 = Mathf.sinDeg(a + space);
            Fill.quad(x + r1 * cos, y + r1 * sin, x + r1 * cos2, y + r1 * sin2, x + r2 * cos2, y + r2 * sin2, x + r2 * cos, y + r2 * sin);
        }

        float a = space * i + angle;
        float cos = Mathf.cosDeg(a);
        float sin = Mathf.sinDeg(a);
        float cos2 = Mathf.cosDeg(a + space);
        float sin2 = Mathf.sinDeg(a + space);
        float f = sides * p - i;
        v1.trns(a, 0, len * (f - 1));
        Fill.quad(x + r1 * cos, y + r1 * sin, x + r1 * cos2 + v1.x, y + r1 * sin2 + v1.y, x + r2 * cos2 + v1.x, y + r2 * sin2 + v1.y, x + r2 * cos, y + r2 * sin);
    }

    public static void posSquareLink(Color color, float stroke, float size, boolean drawBottom, float x, float y, float x2, float y2) {
        posSquareLink(color, stroke, size, drawBottom, v6.set(x, y), v6.set(x2, y2));
    }

    public static void posSquareLink(Color color, float stroke, float size, boolean drawBottom, Position from, Position to) {
        posSquareLinkArr(color, stroke, size, drawBottom, false, from, to);
    }

    public static void posSquareLinkArr(Color color, float stroke, float size, boolean drawBottom, boolean linkLine, Position... pos){
        if(pos.length < 2 || (!linkLine && pos[0] == null))return;

        for (int c : drawBottom ? Mathf.signs : oneArr) {
            for (int i = 1; i < pos.length; i++) {
                if (pos[i] == null)continue;
                Position p1 = pos[i - 1], p2 = pos[i];
                Lines.stroke(stroke + 1 - c, c == 1 ? color : bottomColor);
                if(linkLine) {
                    if(p1 == null)continue;
                    Lines.line(p2.getX(), p2.getY(), p1.getX(), p1.getY());
                }else{
                    Lines.line(p2.getX(), p2.getY(), pos[0].getX(), pos[0].getY());
                }
                Draw.reset();
            }

            for (Position p : pos) {
                if (p == null)continue;
                Draw.color(c == 1 ? color : bottomColor);
                Fill.square(p.getX(), p.getY(), size + 1 -c / 1.5f, 45);
                Draw.reset();
            }
        }
    }


    public static void randFadeLightningEffect(float x, float y, float range, float lightningPieceLength, Color color, boolean in) {
        randFadeLightningEffectScl(x, y, range, 0.55F, 1.1F, lightningPieceLength, color, in);
    }

    public static void randFadeLightningEffectScl(float x, float y, float range, float sclMin, float sclMax, float lightningPieceLength, Color color, boolean in) {
        v6.rnd(range).scl(Mathf.random(sclMin, sclMax)).add(x, y);
        (in ? WHFx.chainLightningFadeReversed : WHFx.chainLightningFade).at(x, y, lightningPieceLength, color, v6.cpy());
    }

    public static float cameraDstScl(float x, float y, float norDst) {
        v6.set(Core.camera.position);
        float dst = Mathf.dst(x, y, v6.x, v6.y);
        return 1.0F - Mathf.clamp(dst / norDst);
    }

    public static float rotator_90(float in, float margin) {
        return 90.0F * WHInterp.pow10.apply(Mathf.curve(in, margin, 1.0F - margin));
    }

    public static float rotator_90() {
        return 90.0F * Interp.pow5.apply(Mathf.curve(cycle_100(), 0.15F, 0.85F));
    }

    public static float rotator_120(float in, float margin) {
        return 120.0F * WHInterp.pow10.apply(Mathf.curve(in, margin, 1.0F - margin));
    }

    public static float cycle(float phaseOffset, float T) {
        return (Time.time + phaseOffset) % T / T;
    }

    public static float cycle(float in, float phaseOffset, float T) {
        return (in + phaseOffset) % T / T;
    }

    public static float cycle_100() {
        return Time.time % 100.0F / 100.0F;
    }

    public static void surround(long id, float x, float y, float rad, int num, float innerSize, float outerSize, float interp) {
        Rand rand = WHUtils.rand;
        rand.setSeed(id);

        for(int i = 0; i < num; ++i) {
            float len = rad * rand.random(0.75F, 1.5F);
            v6.trns(rand.random(360.0F) + rand.range(2.0F) * (1.5F - Mathf.curve(len, rad * 0.75F, rad * 1.5F)) * Time.time, len);
            float angle = v6.angle();
            v6.add(x, y);
            tri(v6.x, v6.y, (interp + 1.0F) * outerSize + rand.random(0.0F, outerSize / 8.0F), outerSize * (Interp.exp5In.apply(interp) + 0.25F) / 2.0F, angle);
            tri(v6.x, v6.y, (interp + 1.0F) / 2.0F * innerSize + rand.random(0.0F, innerSize / 8.0F), innerSize * (Interp.exp5In.apply(interp) + 0.5F), angle - 180.0F);
        }

    }

    public static void drawConnected(float x, float y, float size, Color color) {
        Draw.reset();
        float sin = Mathf.absin(Time.time * 1.0F, 8.0F, 1.25F);

        for(int i = 0; i < 4; ++i) {
            float length = size / 2.0F + 3.0F + sin;
            Tmp.v1.trns((float)(i * 90), -length);
            Draw.color(Pal.gray);
            Draw.rect(Core.atlas.find("wh-linked-arrow-back"), x + Tmp.v1.x, y + Tmp.v1.y, (float)(i * 90));
            Draw.color(color);
            Draw.rect(Core.atlas.find("wh-linked-arrow"), x + Tmp.v1.x, y + Tmp.v1.y, (float)(i * 90));
        }

        Draw.reset();
    }

    public static void overlayText(String text, float x, float y, float offset, Color color, boolean underline) {
        overlayText(Fonts.outline, text, x, y, offset, 1.0F, 0.25F, color, underline, false);
    }

    public static void overlayText(Font font, String text, float x, float y, float offset, float offsetScl, float size, Color color, boolean underline, boolean align){
        GlyphLayout layout = Pools.obtain(GlyphLayout.class, GlyphLayout::new);
        boolean ints = font.usesIntegerPositions();
        font.setUseIntegerPositions(false);
        font.getData().setScale(size / Scl.scl(1.0f));
        layout.setText(font, text);
        font.setColor(color);

        float dy = offset + 3.0F;
        font.draw(text, x, y + layout.height / (align ? 2 : 1) + (dy + 1.0F) * offsetScl, 1);
        --dy;

        if(underline){
            Lines.stroke(2.0F, Color.darkGray);
            Lines.line(x - layout.width / 2.0F - 2.0F, dy + y, x + layout.width / 2.0F + 1.5F, dy + y);
            Lines.stroke(1.0F, color);
            Lines.line(x - layout.width / 2.0F - 2.0F, dy + y, x + layout.width / 2.0F + 1.5F, dy + y);
            Draw.color();
        }

        font.setUseIntegerPositions(ints);
        font.setColor(Color.white);
        font.getData().setScale(1.0F);
        Draw.reset();
        Pools.free(layout);
    }

    public static void arrow(float x, float y, float width, float length, float backLength, float angle) {
        float wx = Angles.trnsx(angle + 90.0F, width);
        float wy = Angles.trnsy(angle + 90.0F, width);
        float ox = Angles.trnsx(angle, backLength);
        float oy = Angles.trnsy(angle, backLength);
        float cx = Angles.trnsx(angle, length) + x;
        float cy = Angles.trnsy(angle, length) + y;
        Fill.tri(x + ox, y + oy, x - wx, y - wy, cx, cy);
        Fill.tri(x + wx, y + wy, x + ox, y + oy, cx, cy);
    }

    public static void tri(float x, float y, float width, float length, float angle) {
        float wx = Angles.trnsx(angle + 90.0F, width);
        float wy = Angles.trnsy(angle + 90.0F, width);
        Fill.tri(x + wx, y + wy, x - wx, y - wy, Angles.trnsx(angle, length) + x, Angles.trnsy(angle, length) + y);
    }

    public static void circlePercentFlip(float x, float y, float rad, float in, float scl) {
        boolean var10000 = in % (scl * 4.0F) < scl * 2.0F;
        float f = Mathf.cos(in % (scl * 3.0F), scl, 1.1F);
        circlePercent(x, y, rad, f > 0.0F ? f : -f, in + (float)(-90 * Mathf.sign(f)));
    }

    static {
        for(int i = 0; i < tmpV.length; ++i) {
            tmpV[i] = new Vec3();
        }

    }
}
