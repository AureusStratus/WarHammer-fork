package wh.entities.bullet;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.entities.Damage;
import mindustry.entities.Lightning;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Drawf;

public class CircleEndLaserType  extends LaserBulletType {
    @Override
    public void draw(Bullet b){
        float realLength = b.fdata;

        float f = Mathf.curve(b.fin(), 0f, 0.2f);
        float baseLen = realLength * f;
        float cwidth = width;
        float compound = 1f;

        Tmp.v1.trns(b.rotation(), baseLen);

        for(Color color : colors){
            Draw.color(color);

            Lines.stroke((cwidth *= lengthFalloff) * b.fout());
            Lines.lineAngle(b.x, b.y, b.rotation(), baseLen, false);

            Fill.circle(Tmp.v1.x + b.x, Tmp.v1.y + b.y, Lines.getStroke() );

            Fill.circle(b.x, b.y, 1.2f * cwidth * b.fout());
            compound *= lengthFalloff;
        }
        Draw.reset();
        Drawf.light(b.x, b.y, b.x + Tmp.v1.x, b.y + Tmp.v1.y, width * 1.4f * b.fout(), colors[0], 0.6f);
    }

}
