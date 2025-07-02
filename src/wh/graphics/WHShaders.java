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
import wh.entities.world.type.BetterPlanet;

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

    public static class DepthAtmosphereShader extends Shader {
        private static final Mat3D mat = new Mat3D();

        public Camera3D camera;
        public BetterPlanet planet;
        /**
         * The only instance of this class: {@link #depthAtmosphere}.
         */
        private DepthAtmosphereShader() {
            super(WHShaders.mv("depth-atmosphere"), WHShaders.mf("depth-atmosphere"));
        }


        @Override
        public void apply() {
            setUniformMatrix4("u_proj", camera.combined.val);
            setUniformMatrix4("u_trans", planet.getTransform(mat).val);

            setUniformf("u_camPos", camera.position);
            setUniformf("u_relCamPos", Tmp.v31.set(camera.position).sub(planet.position));
            setUniformf("u_camRange", camera.near, camera.far - camera.near);
            setUniformf("u_center", planet.position);
            setUniformf("u_light", planet.getLightNormal());
            setUniformf("u_color", planet.atmosphereColor.r, planet.atmosphereColor.g, planet.atmosphereColor.b);

            setUniformf("u_innerRadius", planet.radius + planet.atmosphereRadIn);
            setUniformf("u_outerRadius", planet.radius + planet.atmosphereRadOut);

            planet.buffer.getTexture().bind(0);
            setUniformi("u_topology", 0);
            setUniformf("u_viewport", Core.graphics.getWidth(), Core.graphics.getHeight());
        }
    }
}
