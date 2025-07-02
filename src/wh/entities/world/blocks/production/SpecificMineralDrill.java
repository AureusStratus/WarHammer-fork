
package wh.entities.world.blocks.production;

import arc.Core;
import arc.math.Mathf;
import arc.scene.ui.Image;
import arc.struct.Seq;
import arc.util.Nullable;
import arc.util.Strings;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.Drill;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import java.util.Arrays;

//删除
public class SpecificMineralDrill extends Drill {
    public Item[] targetItems = new Item[]{};
    public @Nullable Seq<Item> allowedItems;
    public SpecificMineralDrill(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        stats.add(Stat.output, table -> {
            table.row();
            table.add(Core.bundle.format("stat-designated-minerals")).left().top();
            for (Item item : targetItems) {
                table.add(item.localizedName).color(item.color).padLeft(5).padRight(5);
                Image itemImage = new Image(item.fullIcon);
                table.add(itemImage).size(32);
                table.draw();

            }
        });
        float finaldrillSpeed = Math.round(60f / drillTime * Math.pow(size, 4));
        stats.add(Stat.drillSpeed, finaldrillSpeed, StatUnit.itemsSecond);
        super.setStats();
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("drillspeed", (DrillBuild e) ->
                new Bar(() -> Core.bundle.format("bar.drillspeed", Strings.fixed(e.lastDrillSpeed * 60 * e.dominantItems * e.timeScale(), 2)), () -> Pal.ammo, () -> e.warmup));
    }

    public class SpecificMineralDrillBuild extends DrillBuild {

        @Override
        public void updateTile() {

            if (dominantItem == null) {
                return;
            }

            timeDrilled += warmup * delta();

            float delay = getDrillTime(dominantItem);
            if (!Arrays.asList(targetItems).contains(dominantItem)) {
                return;
            }
            if (items.total() < itemCapacity && dominantItems > 0 && efficiency > 0) {
                float speed = efficiency;
                lastDrillSpeed = 1f / drillTime * speed * dominantItems;
                warmup = Mathf.approachDelta(warmup, speed, warmupSpeed);
                progress += delta() * dominantItems * speed * warmup;

                if (Mathf.chanceDelta(updateEffectChance * warmup))
                    updateEffect.at(x + Mathf.range(size * 2f), y + Mathf.range(size * 2f));
            } else {
                lastDrillSpeed = 0f;
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
                return;
            }

            if (dominantItems > 0 && progress >= delay && items.total() < itemCapacity) {
                for (int i = 0; i < dominantItems; i++) {
                    offload(dominantItem);
                }

                progress %= delay;

                if (wasVisible && Mathf.chanceDelta(drillEffectChance * warmup))
                    drillEffect.at(x + Mathf.range(drillEffectRnd), y + Mathf.range(drillEffectRnd), dominantItem.color);
            }

        }

        @Override
        public float progress() {
            return dominantItem == null ? 0f : Mathf.clamp(progress / getDrillTime(dominantItem));
        }
    }
}
