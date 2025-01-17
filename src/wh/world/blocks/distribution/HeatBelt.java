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

        //TODO show number
        addBar("heat", (
                HeatBelt.HeatBletBuild entity) -> new Bar(() -> Core.bundle.format("bar.heatamount", (int) (entity.heat + 0.001f)), () -> Pal.lightOrange, () -> entity.heat / visualMaxHeat));
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
        public @Nullable Item current;
        public int recDir = 0;
        public int blendbits, xscl, yscl, blending;
        public @Nullable Building next;
        public @Nullable DuctBuild nextc;
        public float realRange = 0;
        private boolean can, show, change1_2 = false;
        public float lp1 = 0, lp2 = 1;
        private final Seq<Float[]> pos = new Seq<>();
        private final Pool<EPos> posPool = Pools.get(EPos.class, EPos::new);

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

            //draw item
            if(current != null){
                Draw.z(Layer.blockUnder + 0.1f);
                Tmp.v1.set(Geometry.d4x(recDir) * tilesize / 2f, Geometry.d4y(recDir) * tilesize / 2f)
                        .lerp(Geometry.d4x(r) * tilesize / 2f, Geometry.d4y(r) * tilesize / 2f,
                                Mathf.clamp((progress + 1f) / 2f));

                Draw.rect(current.fullIcon, x + Tmp.v1.x, y + Tmp.v1.y, itemSize, itemSize);
            }
            //draw item
            if(current != null){
                Draw.z(Layer.blockUnder + 0.1f);
                Tmp.v1.set(Geometry.d4x(recDir) * tilesize / 2f, Geometry.d4y(recDir) * tilesize / 2f)
                        .lerp(Geometry.d4x(r) * tilesize / 2f, Geometry.d4y(r) * tilesize / 2f,
                                Mathf.clamp((progress + 1f) / 2f));

                Draw.rect(current.fullIcon, x + Tmp.v1.x, y + Tmp.v1.y, itemSize, itemSize);
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


        /*@Override
        public void drawSelect() {
            super.drawSelect();
            if(!change1_2) lp1 = Mathf.lerpDelta(lp1, 1, 0.06f);
            if(change1_2) lp2 = Mathf.lerpDelta(lp2, 0, 0.06f);
            if(lp1 > 0.99f){
                change1_2 = true;
                lp1 = 0;
            }
            if(lp2 < 0.01f){
                change1_2 = false;
                lp2 = 1;
            }

            Lines.stroke(2.4f, team.color);
            pos.clear();
            Building input = back();
            Building output = front();

            if (input != null && output != null) {
                float inputX = input.x + Geometry.d4x(input.rotation) * tilesize / 2f;
                float inputY = input.y + Geometry.d4y(input.rotation) * tilesize / 2f;
                float outputX = output.x + Geometry.d4x(output.rotation) * tilesize / 2f;
                float outputY = output.y + Geometry.d4y(output.rotation) * tilesize / 2f;

                Float[] inputPos = {inputX, inputY};
                Float[] outputPos = {outputX, outputY};
                pos.add(inputPos);
                pos.add(outputPos);

                for(int i = 0; i < pos.size; i++){
                    float ox = pos.get(i)[0], oy = pos.get(i)[1];
                    float ex = pos.get((i + 1) % pos.size)[0], ey = pos.get((i + 1) % pos.size)[1];

                    EPos og = posPool.obtain().set(ox, oy);
                    float dst = og.dst(ex, ey);
                    float angle = og.angleTo(ex, ey);

                    if(!change1_2) {
                        Lines.lineAngle(ox, oy, angle, dst * lp1);
                    } else {
                        Lines.lineAngle(ex, ey, angle - 180, dst * lp2);
                    }

                    posPool.free(og);
                }
            }
        }*/



    }

    public static class EPos implements Position {
        public float x, y;

        public EPos set(float x, float y){
            this.x = x;
            this.y = y;
            return this;
        }

        @Override
        public float getX() {
            return x;
        }

        @Override
        public float getY() {
            return y;
        }
    }}


