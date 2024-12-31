//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.entities.abilities;

import arc.audio.Sound;
import arc.func.Cons;
import arc.func.Cons2;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Position;
import arc.struct.ObjectFloatMap;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.Tmp;
import java.util.Iterator;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;
import wh.content.WHFx;
import wh.gen.WHSounds;
import wh.graphics.PositionLightning;
import wh.graphics.WHPal;

public class ShockWaveAbility extends Ability {
    protected static final Seq<Unit> all = new Seq();
    public ObjectFloatMap<StatusEffect> status = new ObjectFloatMap();
    public boolean targetGround = true;
    public boolean targetAir = true;
    public float x;
    public float y;
    public float reload = 500.0F;
    public float range = 400.0F;
    public float damage = 400.0F;
    public float knockback = 20.0F;
    public float rotKnock = 10.0F;
    public Color hitColor;
    public Sound shootSound;
    public Effect shootEffect;
    public Effect hitEffect;
    public float maxSpeed;
    public int boltNum;
    public float boltWidth;
    public Cons2<Position, Position> effect;
    protected float timer;

    public ShockWaveAbility() {
        this.hitColor = WHPal.ancientLightMid;
        this.shootSound = WHSounds.shock;
        this.shootEffect = WHFx.circleOut;
        this.hitEffect = WHFx.hitSparkLarge;
        this.maxSpeed = -1.0F;
        this.boltNum = 2;
        this.boltWidth = 2.0F;
        this.effect = (from, to) -> {
            PositionLightning.createEffect(from, to, this.hitColor, this.boltNum, this.boltWidth);
        };
        this.timer = 0.0F;
    }

    public ShockWaveAbility(float reload, float range, float damage, Color hitColor) {
        this.hitColor = WHPal.ancientLightMid;
        this.shootSound = WHSounds.shock;
        this.shootEffect = WHFx.circleOut;
        this.hitEffect = WHFx.hitSparkLarge;
        this.maxSpeed = -1.0F;
        this.boltNum = 2;
        this.boltWidth = 2.0F;
        this.effect = (from, to) -> {
            PositionLightning.createEffect(from, to, this.hitColor, this.boltNum, this.boltWidth);
        };
        this.timer = 0.0F;
        this.reload = reload;
        this.range = range;
        this.damage = damage;
        this.hitColor = hitColor;
    }

    public ShockWaveAbility modify(Cons<ShockWaveAbility> m) {
        m.get(this);
        return this;
    }

    public ShockWaveAbility status(Object... values) {
        for(int i = 0; i < values.length / 2; ++i) {
            this.status.put((StatusEffect)values[i * 2], (Float)values[i * 2 + 1]);
        }

        return this;
    }

    public void init(UnitType type) {
        super.init(type);
        if (this.maxSpeed > 0.0F) {
            this.maxSpeed *= this.maxSpeed;
        }

    }

    public void update(Unit unit) {
        if (!unit.disarmed) {
            this.timer += Time.delta * unit.reloadMultiplier;
            if (this.maxSpeed > 0.0F && unit.vel().len2() > this.maxSpeed) {
                this.timer = 0.0F;
            } else if (this.timer > this.reload) {
                all.clear();
                Tmp.v1.trns(unit.rotation - 90.0F, this.x, this.y).add(unit.x, unit.y);
                float rx = Tmp.v1.x;
                float ry = Tmp.v1.y;
                Units.nearby((Team)null, rx, ry, this.range, (other) -> {
                    if (other.team != unit.team && other.checkTarget(this.targetAir, this.targetGround) && other.targetable(unit.team)) {
                        all.add(other);
                    }

                });
                if (all.any()) {
                    this.timer = 0.0F;
                    this.shootSound.at(rx, ry, 1.0F + Mathf.range(0.15F), 3.0F);
                    this.shootEffect.at(rx, ry, this.range, this.hitColor);
                    Iterator var4 = all.iterator();

                    while(var4.hasNext()) {
                        Unit u = (Unit)var4.next();
                        ObjectFloatMap.Entries var6 = this.status.entries().iterator();

                        while(var6.hasNext()) {
                            ObjectFloatMap.Entry<StatusEffect> s = (ObjectFloatMap.Entry)var6.next();
                            u.apply((StatusEffect)s.key, s.value);
                        }

                        Tmp.v3.set(unit).sub(Tmp.v1).nor().scl(this.knockback * 80.0F);
                        u.impulse(Tmp.v3);
                        u.damage(this.damage);
                        this.hitEffect.at(u.x, u.y, this.hitColor);
                        this.effect.get(Tmp.v1, u);
                    }
                }
            }

        }
    }

    public void draw(Unit unit) {
        super.draw(unit);
    }

    public String localized() {
        return super.localized();
    }
}
