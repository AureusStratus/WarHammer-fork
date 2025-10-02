package wh.entities.world.drawer.part;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.entities.part.*;
import mindustry.graphics.*;
import wh.graphics.*;

import static wh.util.WHUtils.rand;

public class CollapsePart extends DrawPart{
    public float x, y;
    public float radius = 8f;
    public Color particleColor = WHPal.WHYellow;
    public int particles = 15;
    public float particleLife = 100f;
    public float particleSize = 4f;
    public float particleLen = 7f;
    public float layer = -1f, layerOffset = 0f;
    public PartProgress progress = PartProgress.recoil.curve(Interp.smooth);
    public float orbSinScl = 8f, orbSinMag = 1f,rotateScl=3f;
    public Interp particleInterp = f -> Interp.circleOut.apply(Interp.slope.apply(f));

    @Override
    public void draw(PartParams params){
        float z = Draw.z();
        if(layer > 0) Draw.z(layer);
        if(under) Draw.z(z - 0.0001f);

        Draw.z(Draw.z() + layerOffset);

        float pro= progress.getClamp(params);

        float rad = radius + Mathf.absin(orbSinScl, orbSinMag);
        Tmp.v1.set(x, y).rotate(params.rotation - 90);
        float ax = params.x + Tmp.v1.x, ay = params.y + Tmp.v1.y;

        float base = (Time.time / particleLife);
        rand.setSeed((long)(x+y+114514+hashCode()));
        for(int i = 0; i < particles; i++){
            float fin = (rand.random(1f) + base) % 1f, fout = 1f - fin;
            float angle = rand.random(360f) + (Time.time / rotateScl) % 360f;
            float len = particleLen * particleInterp.apply(fout);
            Draw.color(particleColor);
            Fill.circle(
            ax + Angles.trnsx(angle, len*pro),
            ay + Angles.trnsy(angle, len*pro),
            particleSize * Mathf.slope(fin)*pro
            );
          /*  Draw.color(Pal.coalBlack);
            Fill.circle(
            ax + Angles.trnsx(angle, len*pro),
            ay + Angles.trnsy(angle, len*pro),
            particleSize*0.5f * Mathf.slope(fin)*pro
            );*/
        }
        Draw.reset();
        Draw.color(particleColor);
        Fill.circle(ax, ay, rad*pro);
        Draw.color(Pal.coalBlack);
        Fill.circle(ax, ay, rad*pro*0.5f);
    }
}
