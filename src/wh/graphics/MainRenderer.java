//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.graphics;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.gl.FrameBuffer;
import arc.struct.Seq;
import arc.util.Tmp;
import arc.util.pooling.Pools;
import mindustry.Vars;
import mindustry.game.EventType.Trigger;

public class MainRenderer {
    private final Seq<BlackHole> holes = new Seq();
    private static MainRenderer renderer;
    private FrameBuffer buffer;
    private static final float[][] initFloat = new float[510][];

    protected MainRenderer() {
        if (!Vars.headless) {
            MainShader.createShader();
            this.buffer = new FrameBuffer();
            Events.run(Trigger.draw, this::advancedDraw);
        }

    }

    public static void init() {
        if (renderer == null) {
            renderer = new MainRenderer();
        }

        for(int i = 0; i < 510; ++i) {
            initFloat[i] = new float[i * 4];
        }

    }

    public static void addBlackHole(float x, float y, float inRadius, float outRadius, float alpha) {
        if (!Vars.headless) {
            renderer.addHole(x, y, inRadius, outRadius, alpha);
        }

    }

    public static void addBlackHole(float x, float y, float inRadius, float outRadius) {
        if (!Vars.headless) {
            renderer.addHole(x, y, inRadius, outRadius, 1.0F);
        }

    }

    private void advancedDraw() {
        Draw.draw(-11.0F, () -> {
            this.buffer.resize(Core.graphics.getWidth(), Core.graphics.getHeight());
            this.buffer.begin();
        });
        Draw.draw(219.0F, () -> {
            this.buffer.end();
            if (this.holes.size < 510) {
                if (this.holes.size >= MainShader.MaxCont) {
                    MainShader.createShader();
                }

                float[] blackholes = initFloat[this.holes.size];

                for(int i = 0; i < this.holes.size; ++i) {
                    BlackHole hole = (BlackHole)this.holes.get(i);
                    blackholes[i * 4] = hole.x;
                    blackholes[i * 4 + 1] = hole.y;
                    blackholes[i * 4 + 2] = hole.inRadius;
                    blackholes[i * 4 + 3] = hole.outRadius;
                    Draw.color(Tmp.c2.set(Color.black).a(hole.alpha));
                    Fill.circle(hole.x, hole.y, hole.inRadius * 1.5F);
                    Draw.color();
                }

                MainShader.holeShader.blackHoles = blackholes;
                this.buffer.blit(MainShader.holeShader);
                this.buffer.begin();
                Draw.rect();
                this.buffer.end();
                this.holes.clear();
            }
        });
    }

    private void addHole(float x, float y, float inRadius, float outRadius, float alpha) {
        if (!(inRadius > outRadius) && !(outRadius <= 0.0F)) {
            this.holes.add(((BlackHole)Pools.obtain(BlackHole.class, BlackHole::new)).set(x, y, inRadius, outRadius, alpha));
        }
    }

    public static class BlackHole {
        float x;
        float y;
        float inRadius;
        float outRadius;
        float alpha;

        public BlackHole set(float x, float y, float inRadius, float outRadius, float alpha) {
            this.x = x;
            this.y = y;
            this.inRadius = inRadius;
            this.outRadius = outRadius;
            this.alpha = alpha;
            return this;
        }

        public BlackHole() {
        }
    }
}
