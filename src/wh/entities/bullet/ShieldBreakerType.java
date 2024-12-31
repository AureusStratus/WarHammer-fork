//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.entities.bullet;

import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import wh.content.WHFx;

public class ShieldBreakerType extends BasicBulletType {
    protected static BulletType breakType = new EffectBulletType(3.0F) {
        {
            this.absorbable = true;
            this.collides = false;
            this.lifetime = 8.0F;
            this.drawSize = 0.0F;
            this.damage = 1.0F;
        }

        public void despawned(Bullet b) {
            if (b.absorbed) {
                Object var3 = b.data;
                if (var3 instanceof Color) {
                    Color color = (Color)var3;
                    WHFx.shuttle.at(b.x, b.y, Mathf.random(360.0F), color, b.damage / 8.0F / 2.0F);
                    Effect.shake(b.damage / 100.0F, b.damage / 100.0F, b);
                    Sounds.plasmaboom.at(b);
                }
            }

        }
    };
    public float fragSpawnSpacing;
    public float maxShieldDamage;

    public ShieldBreakerType(float speed, float damage, String bulletSprite, float shieldDamage) {
        super(speed, damage, bulletSprite);
        this.fragSpawnSpacing = 5.0F;
        this.splashDamage = this.splashDamageRadius = -1.0F;
        this.maxShieldDamage = shieldDamage;
        this.absorbable = false;
    }

    public ShieldBreakerType(float speed, float damage, float shieldDamage) {
        this(speed, damage, "bullet", shieldDamage);
    }

    public ShieldBreakerType() {
        this(1.0F, 1.0F, "bullet", 500.0F);
    }

    public void init() {
        super.init();
    }

    public void update(Bullet b) {
        super.update(b);
        if (b.timer(5, this.fragSpawnSpacing)) {
            breakType.create(b, b.team, b.x, b.y, 0.0F, this.maxShieldDamage, 0.0F, 1.0F, this.backColor);
        }

    }
}
