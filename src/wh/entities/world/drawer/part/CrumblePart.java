package wh.entities.world.drawer.part;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.entities.part.*;
import mindustry.graphics.*;
import wh.graphics.*;

public class CrumblePart extends DrawPart{
    public float x, y;
    public Color color =WHPal.WHYellow;
    public float layer = -1f, layerOffset = 0.001f;
    public PartProgress progress = PartProgress.warmup;
    public float lineWidth = 2f;
    public float rotation = 0f, rad = 12f;
    public float triLengthTo = 20,triLength = 0;
    public float haloRotateSpeed = 0.3f, haloRotation = 0f;
    public float rotateSpeed=-1f;
    public boolean mirror = false;
    @Override
    public void draw(PartParams params){
        float z = Draw.z();
        if(layer > 0) Draw.z(layer);
        if(under && turretShading) Draw.z(z - 0.0001f);

        Draw.z(Draw.z() + layerOffset);
        Draw.color(color);

        float
        p = progress.getClamp(params),
        triLen = triLengthTo < 0 ? triLength : Mathf.lerp(triLength, triLengthTo, p);


        if(p < 0.001f) return;
        int len = mirror && params.sideOverride == -1 ? 2 : 1;

        for(int s = 0; s < len; s++) {
            int i = params.sideOverride == -1 ? s : params.sideOverride;

            float sign = (i == 0 ? 1 : -1) * params.sideMultiplier;
            Tmp.v1.set(x * sign, y ).rotate(params.rotation - 90);

            float
            rx = params.x + Tmp.v1.x,
            ry = params.y + Tmp.v1.y;

            float baseRot = Time.time * rotateSpeed;

            Lines.stroke(lineWidth*p);
            Lines.poly(rx, ry, 3, rad,  baseRot * sign);
            Lines.circle(rx, ry, rad);

            float haloRot = (haloRotation + haloRotateSpeed * Time.time) * sign;

            float amount=2,stroke = 0f,strokeTo = 7f;
            for(int v = 0; v < amount; v++){
                float str = Mathf.lerp(stroke, strokeTo, p);
                float rot = v * 360f / amount + params.rotation-90;
                float shapeX = Angles.trnsx(rot, rad) + rx, shapeY = Angles.trnsy(rot, rad) + ry;
                float pointRot = rot * sign;

                if(str > 0.001 && triLen > 0.001){
                    Drawf.tri(shapeX, shapeY, str, triLen, pointRot);
                    Drawf.tri(shapeX, shapeY, str, triLen, pointRot-180);
                }
            }

            float amount2=3,stroke2 = 0f,strokeTo2 = 4f;
            for(int v = 0; v < amount2; v++){
                float str = Mathf.lerp(stroke2, strokeTo2, p);
                float rot = haloRot+v * 360f / amount2 + params.rotation;
                float shapeX = Angles.trnsx(rot, rad) + rx, shapeY = Angles.trnsy(rot, rad) + ry;
                float pointRot = rot - baseRot * sign;

                if(str > 0.001 && triLen > 0.001){
                    Drawf.tri(shapeX, shapeY, str, triLen/2, pointRot);
                    Drawf.tri(shapeX, shapeY, str, triLen/2, pointRot-180);
                }
            }
        }

        Draw.reset();
        Draw.z(z);
    }
}
