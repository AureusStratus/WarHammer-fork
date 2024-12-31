//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.graphics;

import arc.Core;
import arc.files.Fi;
import arc.graphics.Texture;
import arc.graphics.g3d.Camera3D;
import arc.graphics.gl.Shader;
import arc.math.geom.Mat3D;
import arc.util.Tmp;
import mindustry.Vars;
import wh.WHVars;
import wh.type.BetterPlanet;

public class WHShaders {
    public static DepthShader depth;
    public static DepthAtmosphereShader depthAtmosphere;

    private WHShaders() {
    }

    public static void init() {
        depth = new DepthShader();
        depthAtmosphere = new DepthAtmosphereShader();
    }

    public static Fi df(String name) {
        return Vars.tree.get("shaders/" + name + ".frag");
    }

    public static Fi dv(String name) {
        return Vars.tree.get("shaders/" + name + ".vert");
    }

    public static Fi mf(String name) {
        return WHVars.internalTree.child("shaders/" + name + ".frag");
    }

    public static Fi mv(String name) {
        return WHVars.internalTree.child("shaders/" + name + ".vert");
    }

    public static final class DepthShader extends Shader {
        public Camera3D camera;

        private DepthShader() {
            super(WHShaders.mv("depth"), WHShaders.mf("depth"));
        }

        public void apply() {
            this.setUniformf("u_camPos", this.camera.position);
            this.setUniformf("u_camRange", this.camera.near, this.camera.far - this.camera.near);
        }
    }

    public static final class DepthAtmosphereShader extends Shader {
        private static final Mat3D mat = new Mat3D();
        public Camera3D camera;
        public BetterPlanet planet;

        private DepthAtmosphereShader() {
            super(WHShaders.mv("depth-atmosphere"), WHShaders.mf("depth-atmosphere"));
        }

        public void apply() {
            this.setUniformMatrix4("u_proj", this.camera.combined.val);
            this.setUniformMatrix4("u_trans", this.planet.getTransform(mat).val);
            this.setUniformf("u_camPos", this.camera.position);
            this.setUniformf("u_relCamPos", Tmp.v31.set(this.camera.position).sub(this.planet.position));
            this.setUniformf("u_camRange", this.camera.near, this.camera.far - this.camera.near);
            this.setUniformf("u_center", this.planet.position);
            this.setUniformf("u_light", this.planet.getLightNormal());
            this.setUniformf("u_color", this.planet.atmosphereColor.r, this.planet.atmosphereColor.g, this.planet.atmosphereColor.b);
            this.setUniformf("u_innerRadius", this.planet.radius + this.planet.atmosphereRadIn);
            this.setUniformf("u_outerRadius", this.planet.radius + this.planet.atmosphereRadOut);
            ((Texture)this.planet.depthBuffer.getTexture()).bind(0);
            this.setUniformi("u_topology", 0);
            this.setUniformf("u_viewport", (float)Core.graphics.getWidth(), (float)Core.graphics.getHeight());
        }
    }
}
