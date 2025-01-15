//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.entities.bullet;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Vec2;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.gen.Bullet;
import mindustry.gen.Hitboxc;
import wh.content.WHFx;
import wh.graphics.PositionLightning;
import wh.struct.Vec2Seq;

public class TrailFadeBulletType extends AccelBulletType {
    protected static final Vec2 v1 = new Vec2();
    protected static final Vec2 v2 = new Vec2();
    protected static final Vec2 v3 = new Vec2();
    protected static final Rand rand = new Rand();
    public int tracers;
    public int tracerFadeOffset;
    public int tracerStrokeOffset;
    public float tracerStroke;
    public float tracerSpacing;
    public float tracerRandX;
    public float tracerUpdateSpacing;
    public boolean addBeginPoint;
    public boolean hitBlinkTrail;
    public boolean despawnBlinkTrail;

    public TrailFadeBulletType() {
        this.tracers = 2;
        this.tracerFadeOffset = 10;
        this.tracerStrokeOffset = 15;
        this.tracerStroke = 3.0F;
        this.tracerSpacing = 8.0F;
        this.tracerRandX = 6.0F;
        this.tracerUpdateSpacing = 0.3F;
        this.addBeginPoint = false;
        this.hitBlinkTrail = true;
        this.despawnBlinkTrail = false;
    }

    public TrailFadeBulletType(float speed, float damage, String bulletSprite) {
        super(speed, damage, bulletSprite);
        this.tracers = 2;
        this.tracerFadeOffset = 10;
        this.tracerStrokeOffset = 15;
        this.tracerStroke = 3.0F;
        this.tracerSpacing = 8.0F;
        this.tracerRandX = 6.0F;
        this.tracerUpdateSpacing = 0.3F;
        this.addBeginPoint = false;
        this.hitBlinkTrail = true;
        this.despawnBlinkTrail = false;
        this.impact = true;
    }

    public TrailFadeBulletType(float speed, float damage) {
        this(speed, damage, "bullet");
    }

    public void despawned(Bullet b) {
        if (!Vars.headless) {
            Object var3 = b.data;
            if (var3 instanceof Vec2Seq[]) {
                Vec2Seq[] pointsArr = (Vec2Seq[])var3;
                Vec2Seq[] var8 = pointsArr;
                int var4 = pointsArr.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    Vec2Seq points = var8[var5];
                    points.add(b.x, b.y);
                    if (!this.despawnBlinkTrail && (!b.absorbed || !this.hitBlinkTrail)) {
                        points.add(this.tracerStroke, (float)this.tracerFadeOffset);
                        WHFx.lightningFade.at(b.x, b.y, (float)this.tracerStrokeOffset, this.hitColor, points);
                    } else {
                        PositionLightning.createBoltEffect(this.hitColor, this.tracerStroke * 2.0F, points);
                        Vec2 v = points.firstTmp();
                        WHFx.lightningHitSmall.at(v.x, v.y, this.hitColor);
                    }
                }

                b.data = null;
            }
        }

        super.despawned(b);
    }

    public void hitEntity(Bullet b, Hitboxc entity, float health) {
        super.hitEntity(b, entity, health);
        this.hit(b);
    }

    public void hit(Bullet b) {
        super.hit(b);
        if (!Vars.headless && b.data instanceof Vec2Seq[]) {
            Vec2Seq[] pointsArr = (Vec2Seq[])b.data();
            Vec2Seq[] var3 = pointsArr;
            int var4 = pointsArr.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Vec2Seq points = var3[var5];
                points.add(b.x, b.y);
                if (this.hitBlinkTrail) {
                    PositionLightning.createBoltEffect(this.hitColor, this.tracerStroke * 2.0F, points);
                    Vec2 v = points.firstTmp();
                    WHFx.lightningHitSmall.at(v.x, v.y, this.hitColor);
                } else {
                    points.add(this.tracerStroke, (float)this.tracerFadeOffset);
                    WHFx.lightningFade.at(b.x, b.y, (float)this.tracerStrokeOffset, this.hitColor, points);
                }
            }

            b.data = null;
        }
    }

    public void init(Bullet b) {
        super.init(b);
        if (!Vars.headless && this.trailLength > 0) {
            Vec2Seq[] points = new Vec2Seq[this.tracers];

            for(int i = 0; i < this.tracers; ++i) {
                Vec2Seq p = new Vec2Seq();
                if (this.addBeginPoint) {
                    p.add(b.x, b.y);
                }

                points[i] = p;
            }

            b.data = points;
        }
    }

    public void update(Bullet b) {
        super.update(b);
        if (!Vars.headless && b.timer(2, this.tracerUpdateSpacing)) {
            Object var3 = b.data;
            if (!(var3 instanceof Vec2Seq[])) {

            }

            Vec2Seq[] vecs = (Vec2Seq[])var3;
            Vec2Seq[] var7 = vecs;
            int var4 = vecs.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Vec2Seq seq = var7[var5];
                v2.trns(b.rotation(), 0.0F, rand.range(this.tracerRandX));
                v1.setToRandomDirection(rand).scl(this.tracerSpacing);
                seq.add(v3.set(b.x, b.y).add(v1).add(v2));
            }
        }


    }

    public void drawTrail(Bullet b) {
        super.drawTrail(b);
        Object var3 = b.data;
        if (var3 instanceof Vec2Seq[]) {
            Vec2Seq[] vecs = (Vec2Seq[])var3;
            Vec2Seq[] var10 = vecs;
            int var4 = vecs.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Vec2Seq points = var10[var5];
                if (points.size() < 2) {
                    return;
                }

                Draw.color(this.hitColor);

                for(int i = 1; i < points.size(); ++i) {
                    Lines.stroke(Mathf.clamp(((float)i + (float)this.tracerFadeOffset / 2.0F) / (float)points.size() * (float)(this.tracerStrokeOffset - (points.size() - i)) / (float)this.tracerStrokeOffset) * this.tracerStroke);
                    Vec2 from = points.setVec2(i - 1, Tmp.v1);
                    Vec2 to = points.setVec2(i, Tmp.v2);
                    Lines.line(from.x, from.y, to.x, to.y, false);
                    Fill.circle(from.x, from.y, Lines.getStroke() / 2.0F);
                }

                Fill.circle(points.peekTmp().x, points.peekTmp().y, this.tracerStroke);
            }
        }

    }
}
