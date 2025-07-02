//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.graphics;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.entities.part.DrawPart;
import wh.content.WHContent;

public class DrawArrowSequence extends DrawPart {
    public float x;
    public float y;
    public float rotation;
    public TextureRegion arrowRegion;
    public String arrowName;
    public float layer = 100.0F;
    public float layerOffset = 0.0F;
    public Color color;
    public Color colorTo;
    public float colorToFinScl;
    public Blending blending;
    public float opacity;
    public PartProgress progress;
    public PartProgress colorProgress;
    public float spacing;
    public int arrows;
    public float scl;
    public float maintainScl;

    public DrawArrowSequence() {
        this.color = Color.white;
        this.colorTo = Color.white;
        this.colorToFinScl = 0.7F;
        this.blending = Blending.normal;
        this.opacity = 1.0F;
        this.progress = PartProgress.reload.inv().mul(PartProgress.warmup);
        this.colorProgress = PartProgress.reload.inv().mul(PartProgress.warmup).compress(0.35F, 0.75F);
        this.spacing = 12.0F;
        this.arrows = 8;
        this.scl = 12.0F;
        this.maintainScl = 0.125F;
    }

    public DrawArrowSequence(String arrowName) {
        this.color = Color.white;
        this.colorTo = Color.white;
        this.colorToFinScl = 0.7F;
        this.blending = Blending.normal;
        this.opacity = 1.0F;
        this.progress = PartProgress.reload.inv().mul(PartProgress.warmup);
        this.colorProgress = PartProgress.reload.inv().mul(PartProgress.warmup).compress(0.35F, 0.75F);
        this.spacing = 12.0F;
        this.arrows = 8;
        this.scl = 12.0F;
        this.maintainScl = 0.125F;
        this.arrowName = arrowName;
    }

    public void draw(PartParams params) {
        float z = Draw.z();
        if (this.layer > 0.0F) {
            Draw.z(this.layer);
        }

        if (this.under && this.turretShading) {
            Draw.z(z - 1.0E-4F);
        }

        Draw.z(Draw.z() + this.layerOffset);
        float prevZ = Draw.z();
        float fin = this.progress.getClamp(params);
        float fout = 1.0F - fin;
        Tmp.v5.trns(params.rotation, this.y, this.x).add(params.x, params.y);
        TextureRegion arrowRegion = WHContent.arrowRegion;
        Draw.color(this.color, this.colorTo, this.colorToFinScl * this.colorProgress.get(params));
        Draw.blend(this.blending);
        Draw.alpha(this.opacity);
        float railF = Mathf.curve(Interp.pow2Out.apply(fin), 0.0F, 0.25F) * Mathf.curve(Interp.pow4Out.apply(fout), 0.0F, 0.3F) * fin;

        for(int i = 0; i < this.arrows; ++i) {
            Tmp.v1.trns(params.rotation + this.rotation, (float)i * this.spacing).add(Tmp.v5);
            float f = Interp.pow3Out.apply(Mathf.clamp(fin * (float)this.arrows - (float)i)) * (0.6F + railF * 0.4F);
            Draw.rect(arrowRegion, Tmp.v1.x, Tmp.v1.y, (float)arrowRegion.width * Draw.scl * f, (float)arrowRegion.height * Draw.scl * f, params.rotation - 90.0F);
        }

        Draw.blend();
        Draw.reset();
        Draw.z(prevZ);
    }

    public void load(String name) {
        if (this.arrowName != null) {
            this.arrowRegion = Core.atlas.find(this.arrowName);
        } else {
            this.arrowRegion = WHContent.arrowRegion;
        }

    }
}
