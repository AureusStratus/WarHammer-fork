//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.content;

import arc.graphics.Color;
import mindustry.type.Item;
import wh.graphics.WHPal;

public final class WHItems {
    public static Item titaniumSteel;
    public static Item imperium;
    public static Item vibranium;
    public static Item ceramite;
    public static Item refineCeramite;
    public static Item adamantium;
    public static Item sealedPromethium;

    private WHItems() {
    }

    public static void load() {
        imperium = new Item("imperium", Color.valueOf("FFFFFF")){{
            hidden = true;
            alwaysUnlocked = true;
        }};
        titaniumSteel = new Item("titanium-steel", WHPal.TiSteelColor){{
            cost = 0.75f;
            radioactivity = 0f;
            flammability = 0f;
            explosiveness = 0f;

        }};
        vibranium = new Item("vibranium",Color.valueOf("85CBFFFF")){{
            cost=5f;
            hardness=7;
            radioactivity = 0f;
            flammability = 0f;
            explosiveness = 0f;
        }};

        ceramite = new Item("ceramite",WHPal.CeramiteColor){{
            cost = 1.8f;
            radioactivity = 0f;
            flammability = 0f;
            explosiveness = 0f;

        }};

        refineCeramite = new Item("refine-ceramite",WHPal.RefineCeramiteColor){{
            cost= 3.75f;
            radioactivity = 0f;
            flammability = 0f;
            explosiveness = 0f;
            charge = 0.8f;
        }};

        adamantium = new Item("adamantium", Color.valueOf("E3AE6FFF")){{
            cost = 6f;
            radioactivity = 0f;
            flammability = 0f;
            explosiveness = 0f;

        }};

        sealedPromethium = new Item("sealed-promethium",Color.valueOf("68FFFFFF")){{
            cost = 1f;
            radioactivity = 1.5f;
            flammability = 0.8f;
            explosiveness = 0.8f;
            charge = 0.8f;
            frames=12;
            transitionFrames=2;
            frameTime=1;
        }};
    }

}