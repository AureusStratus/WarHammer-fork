package wh.entities.bullet;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.util.Tmp;
import mindustry.content.StatusEffects;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Trail;
import wh.entities.world.entities.PlasmaFire;

import static mindustry.Vars.*;

public class TextureMissileType extends BasicBulletType{
    public String sprite;
	public float plaFireChance = 0.35f;
	public TextureMissileType(float damage, String bulletSprite){
        this.sprite = bulletSprite;
		this.damage = damage;
		absorbable = false;
	}
	@Override
	public void load(){
		backRegion = frontRegion = Core.atlas.find(sprite);
	}

	
	@Override
	public void drawTrail(Bullet b){
		if(trailLength > 0 && b.trail != null){
			b.trail.draw(trailColor, trailWidth);
			b.trail.drawCap(trailColor, trailWidth);
		}
	}
	
	@Override
	public void updateTrail(Bullet b){
		if(!headless && trailLength > 0 && b.time > 5f){
			if(b.trail == null){
				b.trail = new Trail(trailLength);
			}
			b.trail.length = trailLength;
			b.trail.update(b.x, b.y, trailInterp.apply(b.fin()));
		}
	}
	
	@Override
	public void draw(Bullet b){
		drawTrail(b);
		
		float z = Draw.z();
		Draw.z(Layer.flyingUnitLow - 0.2f);
		Tmp.v1.trns(b.rotation(), height / 1.75f).add(b);
		Drawf.shadow(Tmp.v1.x, Tmp.v1.y, height / 1.25f);
		Draw.rect(backRegion, Tmp.v1.x, Tmp.v1.y, b.rotation() - 90);
		Draw.z(z);
	}

    public void createSplashDamage(Bullet b, float x, float y){
        if(splashDamageRadius > 0 && !b.absorbed){
            Damage.damage(b.team, x, y, splashDamageRadius, splashDamage * b.damageMultiplier(), splashDamagePierce, collidesAir, collidesGround, scaledSplashDamage, b);

            if(status != StatusEffects.none){
                Damage.status(b.team, x, y, splashDamageRadius, status, statusDuration, collidesAir, collidesGround);
            }

            if(heals()){
                indexer.eachBlock(b.team, x, y, splashDamageRadius, Building::damaged, other -> {
                    healEffect.at(other.x, other.y, 0f, healColor, other.block);
                    other.heal(healPercent / 100f * other.maxHealth() + healAmount);
                });
            }

            if(makeFire&&plaFireChance > 0){
                PlasmaFire.createChance(x, y, splashDamageRadius, plaFireChance, b.team);
            }

            if(makeFire){
                indexer.eachBlock(null, x, y, splashDamageRadius, other -> other.team != b.team, other -> PlasmaFire.create(other.tile));
            }
        }
    }
	
	public void hitTile(Bullet b, Building build, float initialHealth, boolean direct){
		PlasmaFire.create(build.tile);
		
		if(build.team != b.team && direct){
			hit(b);
		}
	}
	
}
