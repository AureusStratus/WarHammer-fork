package wh.entities.world.blocks;

import arc.graphics.g2d.*;
import arc.math.geom.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.blocks.*;

import java.util.*;

public class Road extends Block{
    protected TextureRegion[] autotileRegions;

    public Road(String name){
        super(name);
        size = 4;
        solid = false;
        underBullets = true;
        squareSprite = false;
        canPickup = false;
    }

    @Override
    public boolean hasBuilding(){
        return true;
    }

    @Override
    public void load(){
        super.load();
        autotileRegions = TileBitmask.load(name);
    }

    public class RoadBuild extends Building{
        @Override
        public void draw(){
            int bits = 0;

            for(int i = 0; i < 8; i++){
                Building other = nearby(Geometry.d8[i].x * size, Geometry.d8[i].y * size);
                if(other != null && Objects.equals(other.block.name, this.block.name) && other != this
               /* &&(other.x == this.x + Geometry.d8[i].x * size ||other.y == this.y + Geometry.d8[i].y * size)*/){
                    bits |= (1 << i);
                }
            }
            int bit = TileBitmask.values[bits];

            Draw.rect(autotileRegions[bit], x, y);
        }

        @Override
        public void damage(float damage){
        }
    }
}
