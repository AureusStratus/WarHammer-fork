package wh.entities.world.drawer.part;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.entities.part.*;
import mindustry.graphics.*;

public class BarrelPart extends RegionPart{
    public Color heatColor = Pal.turretHeat.cpy();
    public float intervalWidth = 12 / 4f;
    public int barrelCount = 5;
    public float layerOffset = -0.001f;
    public float rotateSpeed = 1f;
    public PartProgress progress = PartProgress.warmup;
    public PartProgress reloadProgress = PartProgress.recoil;
    public PartProgress recoilProgress = PartProgress.recoil;

    public TextureRegion barrel, barrelOutline, barrelHeat;

    @Override
    public void load(String name){
        super.load(name);
        String realName = this.name == null ? name + suffix : this.name;
        barrel = Core.atlas.find(realName);
        barrelOutline = Core.atlas.find(realName + "-outline");
        barrelHeat = Core.atlas.find(realName + "-heat");
    }

    public BarrelPart(String suffix){
        this.suffix = suffix;
    }

    @Override
    public void getOutlines(Seq<TextureRegion> out){
        super.getOutlines(out);
        out.add(barrel);
    }

    public BarrelPart(){
    }

    @Override
    public void draw(PartParams params){
        float prog = progress.getClamp(params, clampProgress);
        float reload = reloadProgress.getClamp(params, false);
        float recoil = recoilProgress.getClamp(params, clampProgress);
        float time = (360f * 1.5f / barrelCount) * (prog * reload);

        float mx = moveX * prog, my = moveY * prog, mr = moveRot * prog + rotation;

        int len = mirror && params.sideOverride == -1 ? 2 : 1;
        float z = Draw.z() + layerOffset;
        for(int s = 0; s < len; s++){
            int m = params.sideOverride == -1 ? s : params.sideOverride;

            Vec2 v = Tmp.v1;
            float sign = (m == 0 ? 1 : -1) * params.sideMultiplier;
            Tmp.v1.set((x + mx) * sign, y + my).rotateRadExact((params.rotation - 90) * Mathf.degRad);

            Draw.xscl *= sign;
            float
            rx = params.x + Tmp.v1.x,
            ry = params.y + Tmp.v1.y,
            rot = mr + params.rotation - 90;

            Draw.z(z);
            float angle = 360f / barrelCount;
            for(int i = 0; i < barrelCount; i++){
                v.trns(params.rotation - 90f, intervalWidth * Mathf.cosDeg(time - angle * i), 3 * recoil * Mathf.sinDeg(time - angle * i));
                float z1 = z - 0.003f - Mathf.sinDeg(time - angle * i) * 0.001f;
                Draw.z(z1);
                if(barrelOutline != null){
                    rect(barrelOutline, rx + v.x, ry + v.y, rot);  // 先绘制轮廓线
                }
                if(barrel != null){
                    rect(barrel, rx + v.x, ry + v.y, rot);  // 再绘制主体
                }
                if(heat.found() && z1 >= z - 0.002f){
                    float hprog = heatProgress.getClamp(params, clampProgress);
                    heatColor.write(Tmp.c1).a(hprog * heatColor.a);
                    Drawf.additive(barrelHeat, Tmp.c1, 1f, rx + v.x, ry + v.y, rot, turretShading ? turretHeatLayer : Draw.z() + heatLayerOffset, originX, originY);
                }
            }
        }
        Draw.z(z);
    }

    void rect(TextureRegion region, float x, float y, float rotation){
        float w = region.width * region.scl() * Draw.xscl, h = region.height * region.scl() * Draw.yscl;
        Draw.rect(region, x, y, w, h, w / 2f + originX * Draw.xscl, h / 2f + originY * Draw.yscl, rotation);
    }
}