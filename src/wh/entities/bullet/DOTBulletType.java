package wh.entities.bullet;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import wh.content.*;
import wh.graphics.*;

import static mindustry.Vars.state;

public class DOTBulletType extends ContinuousBulletType{

    public float DOTRadius = 12f;
    public float DOTDamage = 100f;
    public float radIncrease = 1.6f;
    public float effectTimer = 1f;
    public StatusEffect effect = WHStatusEffects.palsy;
    public Effect fx = Fx.none;

    public DOTBulletType(){
        speed = 0f;
        lifetime = 120f;
        collides = collidesTiles = false;
        hittable = false;
        absorbable = false;
        scaledSplashDamage = false;
        splashDamagePierce = true;

        damage = DOTDamage;
        buildingDamageMultiplier = 0f;
        despawnEffect = hitEffect = Fx.none;
    }

    public void drawBullet(Bullet b){
        float rad = b.fdata;
        for(int i = 0; i < 2; i++){
            float chance = Mathf.lerp(0.5f, 0.2f, b.fin());
            if(Mathf.chance(chance) && state.isPlaying() && b.timer(0, effectTimer)){
                float a0 = Mathf.random(360) + b.rotation();
                float r0 = Mathf.random(-rad / 5, rad / 2) + rad / 2;
                float a1 = Mathf.random(360) + b.rotation();
                float r1 = Mathf.random(-rad / 5, rad / 2) + rad / 2;

                Vec2 pos0 = new Vec2(b.x + r0 * Mathf.sinDeg(a0), b.y + r0 * Mathf.cosDeg(a0));
                Vec2 pos1 = new Vec2(b.x + r1 * Mathf.sinDeg(a1), b.y + r1 * Mathf.cosDeg(a1));

                PositionLightning.createEffect(pos0, pos1, lightningColor, 2, 2.5f);
                fx.at(pos0);
                fx.at(pos1);
            }
        }
    }

    @Override
    public void update(Bullet b){
        super.update(b);
        b.fdata += radIncrease * Time.delta;
        b.fdata = Mathf.clamp(b.fdata, 0, DOTRadius);
        if(b.timer(2, damageInterval)){
            Damage.damage(b.team, b.x, b.y, b.fdata, DOTDamage * b.damageMultiplier(), splashDamagePierce, collidesAir, collidesGround, scaledSplashDamage, b);
            Damage.status(b.team, b.x, b.y, b.fdata, effect, statusDuration, collidesAir, collidesGround);
           /* Units.nearby(b.team, b.x, b.y, b.fdata, unit -> {
                if(unit.team != b.team && unit.hasEffect(effect)){
                    unit.apply(effect, statusDuration);
                }
            });*/
        }
        drawBullet(b);
    }

    public void applyDamage(Bullet b){
    }

}