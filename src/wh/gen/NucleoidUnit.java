//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.gen;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Fx;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;
import wh.graphics.Drawn;
import wh.world.unit.NucleoidUnitType;

public class NucleoidUnit extends UnitEntity implements Nucleoidc {
    public float recentDamage = 0.0F;
    public float reinforcementsReload = 0.0F;

    protected NucleoidUnit() {
    }

    public int classId() {
        return EntityRegister.getId(NucleoidUnit.class);
    }

    public void setType(UnitType type) {
        super.setType(type);
        if (type instanceof NucleoidUnitType) {
            NucleoidUnitType nType = (NucleoidUnitType)type;
            this.recentDamage = nType.maxDamagedPerSec;
            this.reinforcementsReload = nType.reinforcementsSpacing;
        }

    }

    public float mass() {
        UnitType var2 = this.type;
        if (var2 instanceof NucleoidUnitType) {
            NucleoidUnitType nType = (NucleoidUnitType)var2;
            return nType.mass;
        } else {
            return 8000000.0F;
        }
    }

    public void update() {
        super.update();
        UnitType var2 = this.type;
        if (var2 instanceof NucleoidUnitType) {
            NucleoidUnitType nType = (NucleoidUnitType)var2;
            this.recentDamage += nType.recentDamageResume * Time.delta;
            if (this.recentDamage >= nType.maxDamagedPerSec) {
                this.recentDamage = nType.maxDamagedPerSec;
            }

            this.reinforcementsReload += Time.delta;
            if (this.healthf() < 0.3F && this.reinforcementsReload >= nType.reinforcementsSpacing) {
                this.reinforcementsReload = 0.0F;
                int[] var6 = Mathf.signs;
                int var3 = var6.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    int i = var6[var4];
                    Tmp.v1.trns(this.rotation + (float)(60 * i), -this.hitSize * 1.85F).add(this.x, this.y);
                }
            }
        }

    }

    public void draw() {
        super.draw();
        UnitType var2 = this.type;
        if (var2 instanceof NucleoidUnitType) {
            NucleoidUnitType nType = (NucleoidUnitType)var2;
            float z = Draw.z();
            Draw.z(100.0F);
            Tmp.c1.set(this.team.color).lerp(Color.white, Mathf.absin(4.0F, 0.15F));
            Draw.color(Tmp.c1);
            Lines.stroke(3.0F);
            Drawn.circlePercent(this.x, this.y, this.hitSize * 1.15F, this.reinforcementsReload / nType.reinforcementsSpacing, 0.0F);
            float scl = Interp.pow3Out.apply(Mathf.curve(this.reinforcementsReload / nType.reinforcementsSpacing, 0.96F, 1.0F));
            TextureRegion arrowRegion = Core.atlas.find("wh-jump-gate-arrow");
            int[] var5 = Mathf.signs;
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                int l = var5[var7];
                float angle = (float)(90 + 90 * l);

                for(int i = 0; i < 4; ++i) {
                    Tmp.v1.trns(angle, (float)(i * 50) + this.hitSize * 1.32F);
                    float f = (100.0F - (Time.time + (float)(25 * i)) % 100.0F) / 100.0F;
                    Draw.rect(arrowRegion, this.x + Tmp.v1.x, this.y + Tmp.v1.y, (float)arrowRegion.width * f * scl, (float)arrowRegion.height * f * scl, angle + 90.0F);
                }
            }

            Draw.z(z);
        }

    }

    public void rawDamage(float amount) {
        UnitType var3 = this.type;
        if (var3 instanceof NucleoidUnitType) {
            NucleoidUnitType nType = (NucleoidUnitType)var3;
            float a = amount * nType.damageMultiplier;
            boolean hadShields = this.shield > 1.0E-4F;
            if (hadShields) {
                this.shieldAlpha = 1.0F;
            }

            a = Math.min(a, nType.maxOnceDamage);
            float shieldDamage = Math.min(Math.max(this.shield, 0.0F), a);
            this.shield -= shieldDamage;
            this.hitTime = 1.0F;
            a -= shieldDamage;
            a = Math.min(this.recentDamage / this.healthMultiplier, a);
            this.recentDamage -= a * 1.5F * this.healthMultiplier;
            if (a > 0.0F && this.type.killable) {
                this.health -= a;
                if (this.health <= 0.0F && !this.dead) {
                    this.kill();
                }

                if (hadShields && this.shield <= 1.0E-4F) {
                    Fx.unitShieldBreak.at(this.x, this.y, 0.0F, this.team.color, this);
                }
            }
        }

    }

    public void read(Reads read) {
        this.reinforcementsReload = read.f();
        super.read(read);
    }

    public void write(Writes write) {
        write.f(this.reinforcementsReload);
        super.write(write);
    }

    public void readSync(Reads read) {
        super.readSync(read);
        if (!this.isLocal()) {
            this.reinforcementsReload = read.f();
        } else {
            read.f();
        }

    }

    public void writeSync(Writes write) {
        super.writeSync(write);
        write.f(this.reinforcementsReload);
    }

    public float recentDamage() {
        return this.recentDamage;
    }

    public float reinforcementsReload() {
        return this.reinforcementsReload;
    }

    public void recentDamage(float value) {
        this.recentDamage = value;
    }

    public void reinforcementsReload(float value) {
        this.reinforcementsReload = value;
    }

    public static NucleoidUnit create() {
        return new NucleoidUnit();
    }
}
