package wh.entities.abilities;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.blocks.environment.*;

import static wh.core.WarHammerMod.name;

public class propeller extends Ability{
    public float px, py, length, speed;
    public String sprite;

    float rot = 0;

    public boolean drawWing = false;

    public static Effect wind = new Effect(30, e -> {
        Draw.z(Layer.debris);
        Draw.color(e.color);
        Fill.circle(e.x, e.y, e.fout() * 6 + 0.3f);
    });

    public propeller(float px, float py, String sprite, float length, float speed){
        this.px = px;
        this.py = py;
        this.length = length;
        this.speed = speed;
        this.sprite = sprite;
    }

    public propeller(float px, float py, float length, float speed){
        this.px = px;
        this.py = py;
        this.length = length;
        this.speed = speed;
    }

    @Override
    public String localized() {
        return Core.bundle.format("ability.wh-utilities-propeller", px, py);
    }

    @Override
    public void update(Unit unit) {
        if(unit.type != null && unit.type.naval && !unit.floorOn().isLiquid){
            unit.elevation = 1;
        }

        float realSpeed = unit.elevation * speed * Time.delta;
        rot += realSpeed;
        float out = unit.elevation * length;
        float x = unit.x + Angles.trnsx(unit.rotation, px, py) + Angles.trnsx(unit.rotation, 0, out);
        float y = unit.y + Angles.trnsy(unit.rotation, px, py) + Angles.trnsy(unit.rotation, 0, out);
        if(!unit.moving() && unit.isFlying()){
            Floor floor = Vars.world.floorWorld(x, y);
            if(floor != null) wind.at(x + Mathf.range(8), y + Mathf.range(8), floor.mapColor);
        }
    }

    @Override
    public void draw(Unit unit) {
        Draw.mixcol(Color.white, unit.hitTime);
        Draw.z(Math.max(Layer.groundUnit - 1, unit.elevation * Layer.flyingUnitLow));
        float e = Math.max(unit.elevation, unit.type.shadowElevation);

        float out = unit.elevation * length;
        float x = unit.x + Angles.trnsx(unit.rotation, px, py) + Angles.trnsx(unit.rotation, 0, out);
        float y = unit.y + Angles.trnsy(unit.rotation, px, py) + Angles.trnsy(unit.rotation, 0, out);
        Draw.rect(Core.atlas.find(name("wing")),x, y, unit.rotation + rot * 2);//why not Time.time ? I Don't Know. ha~~
        if(drawWing)Draw.rect(Core.atlas.find(sprite),x, y, unit.rotation - 90);
        Draw.color(Pal.shadow);
        Draw.rect(Core.atlas.find(name("wing")), x + UnitType.shadowTX * e, y + UnitType.shadowTY * e, unit.rotation + rot * 2);
        Draw.mixcol();
        Draw.z(Math.min(Layer.darkness, Layer.groundUnit - 1));
        if(unit.isFlying()&&drawWing){
            Draw.color(Pal.shadow);
            Draw.rect(Core.atlas.find(sprite), x + UnitType.shadowTX * e, y + UnitType.shadowTY * e, unit.rotation - 90);
            Draw.color();
        }
    }
}