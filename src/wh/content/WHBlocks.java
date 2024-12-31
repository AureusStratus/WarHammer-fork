//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.content;

import mindustry.content.Items;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import wh.entities.bullet.PositionLightningBulletType;
import wh.graphics.WHPal;
import wh.world.blocks.distribution.CoveredConveyor;

public final class WHBlocks {
    public static Block promethium;
    public static Block vibraniumOre;
    public static Block steelDust;
    public static Block flash;
    public static Block collapse;

    private WHBlocks() {
    }

    public static void load() {
        promethium = new Floor("promethium");
        vibraniumOre = new OreBlock("vibranium-ore");
        steelDust = new CoveredConveyor("steel-dust");
        flash = new PowerTurret("Flash") {
            {
                this.shootType = new PositionLightningBulletType(50.0F) {
                    {
                        this.maxRange = 300.0F;
                        this.rangeOverride = 300.0F;
                        this.lightningColor = WHPal.rim3;
                        this.lightningDamage = 50.0F;
                        this.lightning = 3;
                        this.lightningLength = 6;
                        this.lightningLengthRand = 6;
                        this.hitEffect = WHFx.lightningSpark;
                    }
                };
            }
        };
        collapse = new ItemTurret("Collapse") {
            {
                this.ammo(new Object[]{WHItems.sealedPromethium, WHBullets.collapseSp, Items.phaseFabric, WHBullets.collaspsePf});
            }
        };
    }
}
