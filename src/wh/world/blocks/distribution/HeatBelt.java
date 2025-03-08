package wh.world.blocks.distribution;


import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Position;
import arc.struct.IntSet;
import arc.struct.Seq;
import arc.util.Nullable;
import arc.util.Tmp;
import arc.util.pooling.Pool;
import arc.util.pooling.Pools;
import mindustry.Vars;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.world.Tile;
import mindustry.world.blocks.distribution.Duct;
import mindustry.world.blocks.heat.HeatBlock;
import mindustry.world.blocks.heat.HeatConsumer;
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawDefault;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.itemSize;
import static mindustry.Vars.tilesize;


public class HeatBelt extends Duct {

    public HeatBelt(String name) {
        super(name);
        update = solid = rotate = true;
        size = 1;
    }

    public DrawBlock drawer = new DrawDefault();
    public float visualMaxHeat = 150f;
    public boolean splitHeat = false;

    @Override
    public void load(){
        super.load();

        drawer.load(this);
    }
    @Override
    public void setBars() {
        super.setBars();

        addBar("heat", (
                HeatBletBuild entity) -> new Bar(() -> Core.bundle.format("bar.heatamount", (int) (entity.heat + 0.001f)), () -> Pal.lightOrange, () -> entity.heat / visualMaxHeat));
    }


    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{Core.atlas.find("duct-bottom"), topRegions[0]};
    }

    public class HeatBletBuild extends Building implements HeatBlock, HeatConsumer {
        public float heat = 0f;
        public float[] sideHeat = new float[4];
        public IntSet cameFrom = new IntSet();
        public long lastHeatUpdate = -1;
        public float progress;
        public int blendbits, xscl, yscl, blending;
        public @Nullable Building next;
        public @Nullable DuctBuild nextc;

        @Override
        public void updateTile(){
            updateHeat();
        }

        public void updateHeat(){
            if(lastHeatUpdate == Vars.state.updateId) return;

            lastHeatUpdate = Vars.state.updateId;
            heat = calculateHeat(sideHeat, cameFrom);
        }

        @Override
        public float warmup(){
            return heat;
        }

        @Override
        public float heat(){
            return heat;
        }

        @Override
        public float heatFrac(){
            return (heat / visualMaxHeat) / (splitHeat ? 3f : 1);
        }

        @Override
        public void draw(){
            float rotation = rotdeg();
            int r =  this.rotation;
            //draw extra ducts facing this one for tiling purposes
            for(int i = 0; i < 4; i++){
                if((blending & (1 << i)) != 0){
                    int dir = r - i;
                    float rot = i == 0 ? rotation : (dir)*90;
                    drawAt(x + Geometry.d4x(dir) * tilesize*0.75f, y + Geometry.d4y(dir) * tilesize*0.75f, 0, rot, i != 0 ? SliceMode.bottom : SliceMode.top);
                }
            }
            Draw.scl(xscl, yscl);
            drawAt(x, y, blendbits, rotation, SliceMode.none);
            Draw.reset();
            drawer.draw(this);
        }

        public void onProximityUpdate(){
            super.onProximityUpdate();

            int[] bits = buildBlending(tile,  rotation, null, true);
            blendbits = bits[0];
            xscl = bits[1];
            yscl = bits[2];
            blending = bits[4];
            next = front();
            nextc = next instanceof DuctBuild d ? d : null;
        }

        public void payloadDraw(){
            Draw.rect(fullIcon, x, y);
        }

        protected void drawAt(float x, float y, int bits, float rotation, SliceMode slice){
            Draw.rect(sliced(botRegions[bits], slice), x, y, rotation);
            Draw.color(transparentColor);
            Draw.rect(sliced(botRegions[bits], slice), x, y, rotation);
            Draw.color();
            Draw.rect(sliced(topRegions[bits], slice), x, y, rotation);
        }

        @Override
        public void drawLight(){
            super.drawLight();
            drawer.drawLight(this);
        }

        @Override
        public float[] sideHeat(){
            return sideHeat;
        }

        @Override
        public float heatRequirement(){
            return visualMaxHeat;

            }
    }}


