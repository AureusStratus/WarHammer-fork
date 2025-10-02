package wh.entities.world.drawer.part;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.entities.part.*;
import mindustry.entities.part.DrawPart.*;
import mindustry.graphics.*;
import wh.graphics.*;

public class MeltaPart extends DrawPart{
    public float x, y=-16;
    public Color color = Pal.meltdownHit;
    public float layer =Layer.effect, layerOffset = 0;
    public PartProgress progress = PartProgress.warmup;
    public float rotation = 0f, rad = 20f;
    public float haloRotateSpeed = 0.3f, haloRotation = 0f;
    public float rotateSpeed=-1f;
    public boolean mirror = true;
    @Override
    public void draw(PartParams params){
        float z = Draw.z();
        if(layer > 0) Draw.z(layer);
        if(under && turretShading) Draw.z(z - 0.0001f);

        Draw.z(Draw.z() + layerOffset);
        Draw.color(color);

        float
        p = progress.getClamp(params);

        if(p < 0.001f) return;
        int len = mirror && params.sideOverride == -1 ? 2 : 1;

        for(int s = 0; s < len; s++) {
            int i = params.sideOverride == -1 ? s : params.sideOverride;

            float sign = (i == 0 ? 1 : -1) * params.sideMultiplier;
            Tmp.v1.set(x * sign,y ).rotate(params.rotation - 90);

            float
            rx = params.x + Tmp.v1.x,
            ry = params.y + Tmp.v1.y;

            float baseRot = Time.time * rotateSpeed;

            float haloRot = (haloRotation + haloRotateSpeed * Time.time) * sign;
            float triLen = Mathf.lerp(0, 35, p);
            float progressRot=Mathf.lerp(45, 0, p);

            float amount=2,stroke = 0f,strokeTo = 10f;
            for(int v = 0; v < amount; v++){
                for(float a:Mathf.signs){
                    float str = Mathf.lerp(stroke, strokeTo, p);
                    float rot =/* v * 360f / amount */+ params.rotation +(90 + progressRot)*a;
                    float shapeX = Angles.trnsx(rot, rad) + rx, shapeY = Angles.trnsy(rot, rad) + ry;

                    if(str > 0.001 && triLen > 0.001){
                        Drawf.tri(shapeX, shapeY, str, triLen, rot);
                        Drawf.tri(shapeX, shapeY, str, 5, rot - 180);
                    }
                }
            }

            float amount2=2,stroke2 = 0f,strokeTo2 = 4f;
            float triLen1 = Mathf.lerp(0, 21, p);
            float progressRot2=Mathf.lerp(90, 0, p);
            float progressRot3=Mathf.lerp(0, 35, p);

                for(float a:Mathf.signs){
                    float str = Mathf.lerp(stroke2, strokeTo2, p);
                    float rot = (progressRot2 + progressRot3)*a + /*v * 360f / amount2 */+180+ params.rotation;
                    float shapeX = Angles.trnsx(rot, rad) + rx, shapeY = Angles.trnsy(rot, rad) + ry;

                    if(str > 0.001 && triLen1 > 0.001){
                        Drawf.tri(shapeX, shapeY, str, triLen1, rot);
                        Drawf.tri(shapeX, shapeY, str, 7, rot - 180);
                    }
                }

        }

        Draw.reset();
        Draw.z(z);
    }
}

