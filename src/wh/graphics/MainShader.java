//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.graphics;

import arc.Core;
import arc.graphics.gl.Shader;
import mindustry.Vars;

public class MainShader {
    public static int MaxCont = 4;
    public static HoleShader holeShader;

    public MainShader() {
    }

    public static void createShader() {
        if (MaxCont < 510) {
            MaxCont = Math.min(MaxCont * 2, 510);
            if (holeShader != null) {
                holeShader.dispose();
            }

            Shader.prependFragmentCode = "#define MAX_COUNT " + MaxCont + "\n";
            holeShader = new HoleShader();
            Shader.prependFragmentCode = "";
        }
    }

    public static class HoleShader extends Shader {
        public float[] blackHoles;

        public HoleShader() {
            super(Core.files.internal("shaders/screenspace.vert"), Vars.tree.get("shaders/TearingSpace.frag"));
        }

        public void apply() {
            this.setUniformf("u_campos", Core.camera.position.x - Core.camera.width / 2.0F, Core.camera.position.y - Core.camera.height / 2.0F);
            this.setUniformf("u_resolution", Core.camera.width, Core.camera.height);
            this.setUniformi("u_blackholecount", this.blackHoles.length / 4);
            this.setUniform4fv("u_blackholes", this.blackHoles, 0, this.blackHoles.length);
        }
    }
}
