//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.entities.bullet;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import java.util.Iterator;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;

public class BoidBulletType extends BasicBulletType {
    public float alignrate = 3.0F;
    public float cohesion = 1.7F;
    public float sep = 10.0F;
    protected final Seq<Bullet> close = new Seq();

    public BoidBulletType() {
    }

    public BoidBulletType(float speed, float damage) {
        super(speed, damage, "missile");
    }

    public void update(Bullet b) {
        super.update(b);
        float radius = this.sep * 8.0F;
        Vec2 vel = b.vel().cpy();
        Vec2 CoM = new Vec2();
        this.close.clear();
        Groups.bullet.intersect(b.x - radius, b.y - radius, radius * 2.0F, radius * 2.0F, (otherx) -> {
            if (otherx.type instanceof BoidBulletType && otherx != b) {
                this.close.add(otherx);
            }

        });
        if (!this.close.isEmpty()) {
            Iterator var5 = this.close.iterator();

            while(var5.hasNext()) {
                Bullet other = (Bullet)var5.next();
                vel.add((other.vel.x - vel.x) / ((float)this.close.size * 100.0F / this.alignrate), (other.vel.y - vel.y) / ((float)this.close.size * 100.0F / this.alignrate));
                Vec2 seperation = new Vec2(other.x - b.x, other.y - b.y);
                float dist2 = Mathf.dst2(0.0F, 0.0F, seperation.x, seperation.y);
                seperation.scl(1.0F / Mathf.sqrt(dist2));
                vel.add(-this.sep * seperation.x / dist2, -this.sep * seperation.y / dist2);
                CoM.add(other.x, other.y);
            }

            CoM = CoM.scl(1.0F / (float)this.close.size);
            Vec2 f3 = new Vec2(CoM.x - b.x, CoM.y - b.y);
            f3 = f3.nor();
            vel.add(f3.x / (100.0F / this.cohesion), f3.y / (100.0F / this.cohesion));
            b.vel.set(vel.x, vel.y);
            b.vel.setLength(this.speed);
        }
    }
}
