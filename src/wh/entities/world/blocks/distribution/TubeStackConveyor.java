package wh.entities.world.blocks.distribution;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.distribution.*;
import wh.util.*;

import static mindustry.Vars.*;

public class TubeStackConveyor extends StackConveyor{

    static final byte[][] tileMap = {
    {},
    {0, 2}, {1, 3}, {0, 1},
    {0, 2}, {0, 2}, {1, 2},
    {0, 1, 2}, {1, 3}, {0, 3},
    {1, 3}, {0, 1, 3}, {2, 3},
    {0, 2, 3}, {1, 2, 3}, {0, 1, 2, 3}
    };

    public TextureRegion[][] topRegion;
    public TextureRegion[] capRegion;
    public TextureRegion editorRegion;
    public TextureRegion coverRegion;
    public TextureRegion[][] CoRegions;
    public float coverLength = 12f;
    public boolean drawCover = false;

    public TubeStackConveyor(String name){
        super(name);
    }

    @Override
    public void init(){
        super.init();
        CoRegions = new TextureRegion[5][8];

        topRegion = WHUtils.splitLayers2(name + "-top", 32, 512, 64);
        capRegion = new TextureRegion[]{topRegion[1][0], topRegion[1][1]};
        editorRegion = Core.atlas.find(name + "-full");
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 8; j++){
                CoRegions[i][j] = Core.atlas.find(name + "-" + i + "-" + j);
            }
        }
        coverRegion = Core.atlas.find(name + "-cover");
    }


    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        int[] bits = getTiling(plan, list);

        if(bits == null) return;

        TextureRegion region = CoRegions[bits[0]][0];
        Draw.rect(region, plan.drawx(), plan.drawy(), plan.rotation * 90);

        TextureRegion conveyor = topRegion[0][bits[0]];
        Draw.rect(conveyor, plan.drawx(), plan.drawy(), conveyor.width * bits[1] * conveyor.scl(), conveyor.height * bits[2] * conveyor.scl(), plan.rotation * 90);

        BuildPlan[] directionals = new BuildPlan[4];
        list.each(other -> {
            if(other.breaking || other == plan) return;

            int i = 0;
            for(Point2 point : Geometry.d4){
                int x = plan.x + point.x, y = plan.y + point.y;
                if(x >= other.x - (other.block.size - 1) / 2 && x <= other.x + (other.block.size / 2) && y >= other.y - (other.block.size - 1) / 2 && y <= other.y + (other.block.size / 2)){
                    if((other.block instanceof StackConveyor ? (plan.rotation == i || (other.rotation + 2) % 4 == i) : !noSideBlend && ((plan.rotation == i && other.block.acceptsItems) || (plan.rotation != i && other.block.outputsItems())))){
                        directionals[i] = other;
                    }
                }
                i++;
            }
        });

        int mask = 0;
        for(int i = 0; i < directionals.length; i++){
            if(directionals[i] != null){
                mask += (1 << i);
            }
        }
        mask |= (1 << plan.rotation);
        Draw.rect(topRegion[0][mask], plan.drawx(), plan.drawy(), 0);
        for(byte i : tileMap[mask]){
            if(directionals[i] == null || (directionals[i].block instanceof StackConveyor ? (directionals[i].rotation + 2) % 4 == plan.rotation : ((plan.rotation == i && !directionals[i].block.acceptsItems) || (plan.rotation != i && !directionals[i].block.outputsItems())))){
                int id = i == 0 || i == 3 ? 1 : 0;
                Draw.rect(capRegion[id], plan.drawx(), plan.drawy(), i == 0 || i == 2 ? 0 : -90);
            }
        }

    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{editorRegion};
    }


    @Override
    protected void initBuilding(){
        if(buildType == null) buildType = TubeStackConveyorBuild::new;
    }

    public class TubeStackConveyorBuild extends StackConveyorBuild{
        public int blendbits, blending;
        public boolean capped, backCapped = false;
        public int tiling = 0;
        public boolean shouldDrawCover;

        public int blendsclx = 1, blendscly = 1;

        public float heat;

        public boolean checkBuild(TubeStackConveyorBuild b){
            if(b.link == -1) return false;
            return b.block.name.equals(this.block.name) && b.rotation == rotation
            && b.shouldDrawCover && !b.backCapped && !b.capped && b.blendbits == this.blendbits;
        }

        @Override
        public void created(){
            super.created();
            if(!drawCover) return;
            boolean hasCover = false;
            for(int r = 1; r <= coverLength; r++){
                Tile other = tile.nearby(Geometry.d4(rotation + 2).x * r, Geometry.d4(rotation + 2).y * r);
                if(other != null && other.build != this
                && other.build instanceof TubeStackConveyorBuild b && checkBuild(b)){
                    float dist = Math.max(Math.abs(b.tileX() - tile.x), Math.abs(b.tileY() - tile.y));
                    if(dist >= coverLength){
                        shouldDrawCover = true;
                        return;
                    }
                }
            }
            for(int r = 1; r <= coverLength; r++){
                Tile backCap = tile.nearby(Geometry.d4(rotation + 2).x * r, Geometry.d4(rotation + 2).y * r);
                if(!hasCover && (backCap != null && backCap.build != this
                && backCap.build instanceof TubeStackConveyorBuild a
                && a.block.name.equals(this.block.name) && rotation == a.rotation &&
                ((a.blendbits == 1 || a.blendbits == 3 && a.link != -1) || a.capped || a.backCapped))){
                    float dist = Math.max(Math.abs(a.tileX() - tile.x), Math.abs(a.tileY() - tile.y));
                    if(dist >= coverLength){
                        hasCover = true;
                    }
                }
            }
            shouldDrawCover = hasCover;
        }

        @Override
        public void draw(){
            Draw.z(Layer.block + 0.001f);
            Draw.scl(1.017f, 1.017f);
            Draw.rect(topRegion[0][tiling], x, y, 0);
            byte[] placementId = tileMap[tiling];
            for(byte i : placementId){
                if(isEnd(i)){
                    int id = i == 0 || i == 3 ? 1 : 0;
                    Draw.rect(capRegion[id], x, y, i == 0 || i == 2 ? 0 : -90);
                }
            }
            Draw.scl();
            Draw.z(Layer.block + 0.02f);

            if(drawCover && shouldDrawCover && blendbits != 3 && blendbits != 1){
                for(byte i : placementId){
                    Draw.rect(coverRegion, x, y, i == 0 || i == 2 ? 0 : -90);
                }
            }
            Draw.scl();

            Draw.z(Layer.block - 0.2f);

            int frame = (int)((Time.time * speed * 6f * timeScale * efficiency) % 8f);
            Draw.rect(CoRegions[blendbits][frame], x, y, tilesize * blendsclx, tilesize * blendscly, rotation * 90);

            Tile from = world.tile(link);

            if(link == -1 || from == null || lastItem == null) return;

            int fromRot = from.build == null ? rotation : from.build.rotation;

            //offset
            Tmp.v1.set(from.worldx(), from.worldy());
            Tmp.v2.set(x, y);
            Tmp.v1.interpolate(Tmp.v2, 1f - cooldown, Interp.linear);

            //rotation
            float a = (fromRot % 4) * 90;
            float b = (rotation % 4) * 90;
            if((fromRot % 4) == 3 && (rotation % 4) == 0) a = -1 * 90;
            if((fromRot % 4) == 0 && (rotation % 4) == 3) a = 4 * 90;

            if(glowRegion.found()){
                Draw.z(Layer.blockAdditive + 0.01f);
            }
            Draw.z(Layer.block - 0.01f);
            //stack
            Draw.rect(stackRegion, Tmp.v1.x, Tmp.v1.y, Mathf.lerp(a, b, Interp.smooth.apply(1f - Mathf.clamp(cooldown * 2, 0f, 1f))));

            //item
            float size = itemSize * Mathf.lerp(Math.min((float)items.total() / itemCapacity, 1), 1f, 0.4f);
            Drawf.shadow(Tmp.v1.x, Tmp.v1.y, size * 1.2f);

            Draw.rect(lastItem.fullIcon, Tmp.v1.x, Tmp.v1.y, size, size, 0);
        }


        public boolean valid(int i){
            Building b = nearby(i);
            return b != null && (b instanceof TubeStackConveyorBuild ? (b.front() != null && b.front() == this) : b.block.acceptsItems || b.block.outputsItems());
        }

        public boolean isEnd(int i){
            Building b = nearby(i);
            return (!valid(i) && (b == null ? null : b.block) != block) || (b instanceof StackConveyorBuild && ((b.rotation + 2) % 4 == rotation || (b.front() != this && back() == b)));
        }

        @Override
        public void updateTile(){
            super.updateTile();
        }

        @Override
        public void onProximityUpdate(){
            super.onProximityUpdate();

            int[] bits = buildBlending(tile, rotation, null, true);
            blendbits = bits[0];
            blendsclx = bits[1];
            blendscly = bits[2];
            blending = bits[4];

            tiling = 0;

            for(int i = 0; i < 4; i++){
                Building other = nearby(i);

                if(other == null) continue;
                if(other.block instanceof StackConveyor && rotation == i || (other.rotation + 2) % 4 == i){
                    tiling |= (1 << i);
                }
            }
            tiling |= 1 << rotation;

            Building next = front(), prev = back();
            if(next instanceof TubeStackConveyorBuild a){
                capped = a.state == 2;
            }
            if(prev instanceof TubeStackConveyorBuild a){
                backCapped = a.state == 1;
            }
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            shouldDrawCover = read.bool();
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.bool(shouldDrawCover);
        }
    }
}
