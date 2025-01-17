package wh.world.blocks.production;

import arc.math.Mathf;
import mindustry.content.Items;
import mindustry.entities.Effect;
import mindustry.type.Item;
import mindustry.world.blocks.production.BurstDrill;

public class SpecificMineralDrill extends BurstDrill {
    private final Item targetThuriom;
    private final Item targetTungsten;

    public SpecificMineralDrill(String name) {
        super(name);
        this.targetThuriom = Items.thorium;
        this.targetTungsten = Items.tungsten;
    }

    public class SpecificMineralDrillBuild extends DrillBuild {
        public float digAmount;
        public float reFindTimer;
        public float smoothProgress = 0f;
        public float invertTime = 0f;

        @Override
        public void updateTile() {
            if (dominantItem == null) {
                return;
            }

            timeDrilled += warmup * delta();

            float delay = getDrillTime(dominantItem);

            if (dominantItem == targetThuriom || dominantItem == targetTungsten) {
                if (items.total() < itemCapacity && dominantItems > 0 && efficiency > 0) {
                    float speed = Mathf.lerp(1f, liquidBoostIntensity, optionalEfficiency) * efficiency;

                    lastDrillSpeed = (speed * dominantItems * warmup) / delay;
                    warmup = Mathf.approachDelta(warmup, speed, warmupSpeed);
                    digAmount += delta() * dominantItems * speed * warmup;
                    reFindTimer += delta();

                    if (invertTime > 0f) invertTime -= delta() / invertedTime;

                    if (timer(timerDump, dumpTime)) {
                        dump(items.has(dominantItem) ? dominantItem : null);
                    }

                    float drillTime = getDrillTime(dominantItem);

                    smoothProgress = Mathf.lerpDelta(smoothProgress, progress / (drillTime - 20f), 0.1f);

                    if (items.total() <= itemCapacity - dominantItems && dominantItems > 0 && efficiency > 0) {
                        warmup = Mathf.approachDelta(warmup, progress / drillTime, 0.01f);

                        timeDrilled += speedCurve.apply(progress / drillTime) * speed;

                        lastDrillSpeed = 1f / drillTime * speed * dominantItems;
                        progress += delta() * speed;
                    } else {
                        warmup = Mathf.approachDelta(warmup, 0f, 0.01f);
                        lastDrillSpeed = 0f;
                        return;
                    }

                    if (dominantItems > 0 && progress >= drillTime && items.total() < itemCapacity) {
                        for (int i = 0; i < dominantItems; i++) {
                            offload(dominantItem);
                        }

                        invertTime = 1f;
                        progress %= drillTime;

                        if (wasVisible) {
                            Effect.shake(shake, shake, this);
                            drillSound.at(x, y, 1f + Mathf.range(drillSoundPitchRand), drillSoundVolume);
                            drillEffect.at(x + Mathf.range(drillEffectRnd), y + Mathf.range(drillEffectRnd), dominantItem.color);
                        } else {
                            warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
                        }
                    }
                }
            } else {
                warmup = Mathf.approachDelta(warmup, 0f, 0.01f);
                lastDrillSpeed = 0f;
            }
        }}}
