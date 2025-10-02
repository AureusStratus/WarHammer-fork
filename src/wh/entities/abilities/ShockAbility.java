package wh.entities.abilities;

import arc.*;
import arc.audio.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.world.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class ShockAbility extends Ability{
    public float range = 110f;
    public float reload = 60f * 1.5f;
    public float bulletDamage = 160;
    public float falloffCount = 20f;
    public float shake = 2f;

    public Sound shootSound = Sounds.bang;
    public Color waveColor = Pal.accent, heatColor = Pal.turretHeat, shapeColor = Color.valueOf("f29c83");
    public float cooldownMultiplier = 1f;
    public Effect hitEffect = Fx.hitSquaresColor;
    public Effect waveEffect = Fx.pointShockwave;

    //TODO switch to drawers eventually or something
    public float shapeRotateSpeed = 1f, shapeRadius = 6f;
    public int shapeSides = 4;


    public float reloadCounter;
    public float heat = 0f;
    public Seq<Bullet> targets = new Seq<>();

    @Override
    public void addStats(Table t){
        super.addStats(t);
        t.add("[lightgray]" + Stat.damage + ": [white]+" + bulletDamage + StatUnit.none);
        t.row();
        t.add("[lightgray]" + Stat.range + ": [white]+" + (range / tilesize) + StatUnit.blocks);
        t.row();
        t.add("[lightgray]" + Stat.reload + ": [white]+" + (60f / reload) + StatUnit.blocks);
    }


    @Override
    public void update(Unit unit){
        super.update(unit);
        if((reloadCounter += Time.delta*unit.reloadMultiplier) >= reload){
            targets.clear();
            Groups.bullet.intersect(unit.x - range, unit.y - range, range * 2, range * 2, b -> {
                if(b.team != unit.team && b.type.hittable){
                    targets.add(b);
                }
            });

            if(targets.size > 0){
                heat = 1f;
                reloadCounter = 0f;
                waveEffect.at(unit.x, unit.y, range, waveColor);
                shootSound.at(unit);
                Effect.shake(shake, shake, unit);
                float waveDamage = Math.min(bulletDamage, bulletDamage * falloffCount / targets.size);

                for(var target : targets){
                    if(target.damage > waveDamage){
                        target.damage -= waveDamage;
                    }else{
                        target.remove();
                    }
                    hitEffect.at(target.x, target.y, waveColor);
                }
            }
        }

        heat = Mathf.clamp(heat - Time.delta / reload * cooldownMultiplier);
    }

    @Override
    public void draw(Unit unit){
        super.draw(unit);
    }
}

