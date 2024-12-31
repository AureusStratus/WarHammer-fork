//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.entities.bullet;

import arc.Core;
import arc.graphics.Color;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.pooling.Pools;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.type.unit.MissileUnitType;
import wh.content.WHFx;
import wh.graphics.MainRenderer;

public class BlackHoleBulletType extends BulletType {
    public float inRad;
    public float outRad;
    public float rotateSpeed;
    public int minLength = 13;
    public int midLength = 18;
    public int maxLength = 25;
    public float minWidth = 0.9F;
    public float maxWidth = 2.2F;
    public float minSpeed = 0.3F;
    public float midSpeed = 0.5F;
    public float maxSpeed = 1.4F;
    public float impulse = 0.667F;
    public Color accColor = Color.valueOf("B778FF");
    public int amount = 4;

    public BlackHoleBulletType() {
        this.speed = 0.0F;
        this.damage = 0.0F;
        this.lifetime = 300.0F;
        this.keepVelocity = this.collides = this.collidesGround = this.collidesAir = this.absorbable = this.hittable = false;
        this.despawnEffect = this.healEffect = Fx.none;
    }

    public void draw(Bullet b) {
        float in = b.time <= b.lifetime - 72.0F ? Math.min(b.time / 60.0F, 1.0F) : (b.lifetime - b.time) / 72.0F;
        in = Interp.fastSlow.apply(in);
        MainRenderer.addBlackHole(b.x, b.y, this.inRad * in, this.outRad * in, Math.min(1.0F, in + 0.1F));
        super.draw(b);
    }

    public void drawLight(Bullet b) {
    }

    public void update(Bullet b) {
        float in = b.time <= b.lifetime - 72.0F ? Math.min(b.time / 60.0F, 1.0F) : (b.lifetime - b.time) / 72.0F;
        in = Interp.fastSlow.apply(in);
        float finalIn = in;
        Units.nearbyEnemies(b.team, b.x, b.y, this.outRad * 2.0F, (e) -> {
            if (e != null && e.targetable(b.team)) {
                float p = this.F(b.x, b.y, e.x, e.y, this.impulse * Math.abs(e.mass() + 1.0F), this.outRad * 2.0F);
                if (e.type instanceof MissileUnitType) {
                    p = this.F(b.x, b.y, e.x, e.y, this.impulse * 2000.0F, this.outRad * 2.0F);
                }

                e.impulseNet(Tmp.v3.set(e).sub(b).nor().scl(-p * Time.delta * finalIn));
                e.vel.limit(5.0F);
            }

        });
        Groups.bullet.intersect(b.x - this.outRad * 2.0F, b.y - this.outRad * 2.0F, this.outRad * 4.0F, this.outRad * 4.0F, (bullet) -> {
            if (bullet != null && bullet.within(b, this.outRad * 2.0F) && bullet.team != b.team && bullet.type != null && (bullet.type.absorbable || bullet.type.hittable)) {
                float p = this.F(b.x, b.y, bullet.x, bullet.y, this.impulse * 10.0F, this.outRad * 2.0F);
                Vec2 v = Tmp.v4.set(bullet).sub(b).nor().scl(-p * Time.delta * finalIn);
                bullet.vel.add(v.x, v.y);
                bullet.vel.limit(5.0F);
                if (bullet.within(b, this.inRad)) {
                    bullet.damage = 0.0F;
                    bullet.remove();
                }
            }

        });
        if (!Vars.headless && Core.settings != null && b.time <= b.lifetime - 72.0F) {
            for(int i = 0; i < this.amount; ++i) {
                WHFx.ateData data = (WHFx.ateData)Pools.obtain(WHFx.ateData.class, WHFx.ateData::new);
                float outRDI = i % 2 == 0 ? this.outRad * 1.3F : this.outRad;
                data.width = Mathf.random(this.minWidth, this.maxWidth) * in;
                data.inRad = this.inRad * 0.9F * in;
                data.outRad = Math.max(data.inRad, Mathf.random(this.inRad * 2.0F, outRDI) * in);
                data.speed = data.outRad > this.inRad * 1.5F ? Mathf.random(this.minSpeed, this.midSpeed) : Mathf.random(this.midSpeed * 2.0F, this.maxSpeed);
                data.length = data.speed < this.midSpeed ? Mathf.random(this.midLength, this.maxLength) : Mathf.random(this.minLength, this.midLength);
                data.owner = b;
                if (i % 6 == 0) {
                    data.out = true;
                }

                WHFx.AccretionDiskEffect.at(b.x, b.y, 0.0F, this.accColor, data);
            }
        }

    }

    public float F(float x, float y, float tx, float ty, float G, float r) {
        float dst = Mathf.dst(x, y, tx, ty);
        float ptr = 1.0F - dst / r;
        return G * ptr;
    }
}
