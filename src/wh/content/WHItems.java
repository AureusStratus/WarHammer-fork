//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.content;

import mindustry.type.Item;

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
        titaniumSteel = new Item("titanium-steel");
        imperium = new Item("imperium");
        vibranium = new Item("vibranium");
        ceramite = new Item("ceramite");
        refineCeramite = new Item("refine-ceramite");
        adamantium = new Item("adamantium");
        sealedPromethium = new Item("sealed-promethium");
    }
}
