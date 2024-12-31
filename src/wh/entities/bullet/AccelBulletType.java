//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.entities.bullet;

import arc.math.Interp;
import arc.math.Mathf;
import arc.struct.FloatSeq;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;

public class AccelBulletType extends BasicBulletType {
    public float velocityBegin;
    public float velocityIncrease;
    public float accelerateBegin;
    public float accelerateEnd;
    public Interp accelInterp;

    public AccelBulletType() {
        this.velocityBegin = -1.0F;
        this.velocityIncrease = 0.0F;
        this.accelerateBegin = 0.1F;
        this.accelerateEnd = 0.6F;
        this.accelInterp = Interp.linear;
    }

    public AccelBulletType(float velocityBegin, float velocityIncrease, Interp accelInterp, float damage, String bulletSprite) {
        super(1.0F, damage, bulletSprite);
        this.velocityBegin = -1.0F;
        this.velocityIncrease = 0.0F;
        this.accelerateBegin = 0.1F;
        this.accelerateEnd = 0.6F;
        this.accelInterp = Interp.linear;
        this.velocityBegin = velocityBegin;
        this.velocityIncrease = velocityIncrease;
        this.accelInterp = accelInterp;
    }

    public AccelBulletType(float speed, float damage, String bulletSprite) {
        super(speed, damage, bulletSprite);
        this.velocityBegin = -1.0F;
        this.velocityIncrease = 0.0F;
        this.accelerateBegin = 0.1F;
        this.accelerateEnd = 0.6F;
        this.accelInterp = Interp.linear;
    }

    public AccelBulletType(float speed, float damage) {
        this(speed, damage, "bullet");
    }

    public AccelBulletType(float damage, String bulletSprite) {
        super(1.0F, damage, bulletSprite);
        this.velocityBegin = -1.0F;
        this.velocityIncrease = 0.0F;
        this.accelerateBegin = 0.1F;
        this.accelerateEnd = 0.6F;
        this.accelInterp = Interp.linear;
    }

    public void disableAccel() {
        this.accelerateBegin = 10.0F;
    }

    protected float calculateRange() {
        if (this.velocityBegin < 0.0F) {
            this.velocityBegin = this.speed;
        }

        boolean computeRange = this.rangeOverride < 0.0F;
        float cal = 0.0F;
        FloatSeq speeds = new FloatSeq();

        for(float i = 0.0F; i <= 1.0F; i += 0.05F) {
            float s = this.velocityBegin + this.accelInterp.apply(Mathf.curve(i, this.accelerateBegin, this.accelerateEnd)) * this.velocityIncrease;
            speeds.add(s);
            if (computeRange) {
                cal += s * this.lifetime * 0.05F;
            }
        }

        this.speed = speeds.sum() / (float)speeds.size;
        if (computeRange) {
            ++cal;
        }

        return cal;
    }

    public void init() {
        super.init();
    }

    public void update(Bullet b) {
        if (this.accelerateBegin < 1.0F) {
            b.vel.setLength((this.velocityBegin + this.accelInterp.apply(Mathf.curve(b.fin(), this.accelerateBegin, this.accelerateEnd)) * this.velocityIncrease) * (this.drag != 0.0F ? 1.0F * Mathf.pow(b.drag, b.fin() * b.lifetime() / 6.0F) : 1.0F));
        }

        super.update(b);
    }
}
