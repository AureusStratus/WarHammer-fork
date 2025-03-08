package wh.content;

import arc.graphics.Color;
import mindustry.type.*;
import wh.graphics.WHPal;

public final class WHLiquids {
    public static Liquid orePromethium, refinePromethium, liquidNitrogen;

    private WHLiquids() {}

    public static void load() {

        orePromethium = new Liquid("ore-promethium", WHPal.OrePromethiumColor){{
            lightColor=WHPal.OrePromethiumColor;
            flammability=0.6f;
            temperature=0.6f;
            heatCapacity=0.2f;
            explosiveness=0.4f;
            viscosity=1f;
        }};

        refinePromethium = new Liquid("refine-promethium", WHPal.RefinePromethiumColor){{
            lightColor = WHPal.RefinePromethiumColor;
            flammability = 0.5f;
            temperature = 0.6f;
            heatCapacity = 0.2f;
            viscosity = 0.5f;
            explosiveness = 0.5f;
        }};

        liquidNitrogen = new Liquid("liquid-nitrogen", Color.valueOf("97C9FFD0")){
            {
                lightColor = Color.valueOf("97C9FFD0");
                flammability = 0f;
                temperature = 0.1f;
                heatCapacity = 1.6f;
                explosiveness = 0f;
                viscosity = 0.2f;
            }};
    }
}
