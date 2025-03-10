//
package wh.content;

import arc.graphics.Blending;
import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.content.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.RadialEffect;
import mindustry.entities.effect.WrapEffect;
import mindustry.entities.part.RegionPart;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.heat.HeatConductor;
import mindustry.world.blocks.heat.HeatProducer;
import mindustry.world.blocks.production.*;
import mindustry.world.consumers.ConsumeItemFlammable;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Env;
import wh.entities.bullet.LaserBeamBulletType;
import wh.entities.bullet.PositionLightningBulletType;
import wh.entities.bullet.SlowLaserBulletType;
import wh.graphics.WHPal;
import wh.world.blocks.defense.turrets.BulletDefenseTurret;
import wh.world.blocks.distribution.CoveredConveyor;
import wh.world.blocks.distribution.HeatBelt;
import wh.world.blocks.distribution.HeatDirectionBridge;
import wh.world.blocks.production.FlammabilityHeatProducer;
import wh.world.blocks.production.MultiCrafter;
import wh.world.blocks.production.SpecificMineralDrill;
import wh.world.drawer.*;


import static mindustry.type.ItemStack.with;
import static wh.graphics.WHPal.CeramiteColor;
import static wh.graphics.WHPal.TiSteelColor;

public final class WHBlocks {
    public static Block promethium;
    public static Block vibraniumOre;
    public static Block steelDust;
    //factory
    public static Block ADMill, T2TiSteelFurnace, titaniumSteelFurnace,
            ceramiteSteelFoundry, ceramiteRefinery,
            promethiumRefinery, sealedPromethiumMill,
            siliconMixFurnace, atmosphericSeparator, T2CarbideCrucible, largeKiln, T2PlastaniumCompressor, T2Electrolyzer, T2SporePress,
            T2Cultivator, T2CryofluidMixer, T2PhaseSynthesizer, largeSurgeSmelter,
            LiquidNitrogenPlant, slagfurnace, scrapCrusher, scrapFurance, tungstenConverter,
            combustionHeater, decayHeater, promethiumHeater, smallHeatRouter, heatBelt, heatBridge;
    //drill
    public static Block heavyCuttingDrill, highEnergyDrill, SpecialCuttingDrill,
            strengthenOilExtractor, slagExtractor, promethiumExtractor, heavyExtractor;
    //liquid
    public static Block gravityPump, tiSteelPump, T2LiquidTank, tiSteelConduit,
            tiSteelBridgeConduit, ceramiteBridgeConduit;
    //effect
    public static Block wrapProjector, wrapOverdrive, armoredVault,
            T2RepairTower, fortlessShield, strongholdCore, T2strongholdCore;
    //turret
    public static Block flash, collapse, sb3, sb4, Deflection;
    //Drill
    public static Block sb;


    private WHBlocks() {
    }

    public static void load() {
        promethium = new Floor("promethium");
        vibraniumOre = new OreBlock("vibranium-ore");
        steelDust = new CoveredConveyor("steel-dust");


        atmosphericSeparator = new HeatCrafter("atmospheric-separator") {
            {

                requirements(Category.crafting, with(Items.lead, 80, Items.metaglass, 80, Items.silicon, 150, WHItems.titaniumSteel, 70));

                hasItems = false;
                hasPower = hasLiquids = true;
                size = 3;
                health = 900;
                craftTime = 60;
                liquidCapacity = 100;
                updateEffect = Fx.none;
                ambientSound = Sounds.extractLoop;
                ambientSoundVolume = 0.06f;
                consumePower(1.5f);
                heatRequirement = 9;
                outputLiquid = new LiquidStack(Liquids.nitrogen, 18f / 60f);
                drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.nitrogen, 4.1f),
                        new DrawParticles() {{
                            color = Color.valueOf("d4f0ff");
                            alpha = 0.6f;
                            particleSize = 4f;
                            particles = 10;
                            particleRad = 12f;
                            particleLife = 140f;
                        }},
                        new DrawDefault(), new DrawHeatInput());
            }
        };

        siliconMixFurnace = new GenericCrafter("silicon-mix-furnace") {
            {
                requirements(Category.crafting, with(Items.silicon, 80, Items.graphite, 100, Items.silicon, 50, WHItems.titaniumSteel, 50));

                size = 3;
                health = 900;
                craftTime = 120;
                itemCapacity = 60;
                hasPower = hasItems = true;
                consumePower(8f);
                outputItem = new ItemStack(Items.silicon, 10);
                consumeItems(with(Items.sand, 10, Items.graphite, 8));
                consumeLiquid(WHLiquids.orePromethium, 7.5f / 60f);
                drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawArcSmelt() {{
                    circleStroke = 1.5f;
                    flameRadiusScl = 2.5f;
                    flameRadiusMag = 0.2f;
                }}, new DrawDefault());
                ambientSound = Sounds.smelter;
                ambientSoundVolume = 0.11f;
                researchCostMultiplier = 0.5f;

            }
        };

        T2CarbideCrucible = new HeatCrafter("t2-carbide-crucible") {{

            requirements(Category.crafting, with(Items.copper, 120, Items.thorium, 80, Items.silicon, 80, WHItems.titaniumSteel, 80));
            size = 3;
            health = 800;
            itemCapacity = 24;
            outputItem = new ItemStack(Items.carbide, 3);
            craftTime = 180;
            heatRequirement = 12;
            hasItems = hasPower = true;
            consumePower(4.5f);
            consumeItems(with(Items.graphite, 3, Items.tungsten, 5));
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawCrucibleFlame(), new DrawDefault(), new DrawHeatInput());
            researchCostMultiplier = 0.45f;
        }};

        largeKiln = new GenericCrafter("large-kiln") {
            {
                requirements(Category.crafting, with(Items.graphite, 60, WHItems.titaniumSteel, 50, Items.plastanium, 100));

                size = 3;
                health = 360;
                itemCapacity = 40;
                hasPower = hasItems = true;
                consumePower(180f / 60f);
                consumeItems(with(Items.sand, 5, Items.lead, 5));
                outputItem = new ItemStack(Items.metaglass, 8);
                drawer = new DrawMulti(new DrawDefault(), new LargekilnDrawer(Color.valueOf("f58349")), new DrawFlame());

                ambientSound = Sounds.smelter;
                ambientSoundVolume = 0.11f;
                researchCostMultiplier = 0.8f;
            }
        };
        T2PlastaniumCompressor = new GenericCrafter("t2-plastanium-compressor") {
            {

                requirements(Category.crafting, with(Items.lead, 300, Items.titanium, 200, Items.graphite, 150, WHItems.titaniumSteel, 200));
                size = 3;
                health = 650;
                hasItems = hasPower = hasLiquids = true;
                craftTime = 60;
                liquidCapacity = 120;
                itemCapacity = 30;
                consumePower(10f);
                consumeLiquid(Liquids.oil, 1);
                consumeItems(with(Items.titanium, 8));
                outputItem = new ItemStack(Items.plastanium, 5);
                drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawDefault(), new T2PlastaniumCompresserDrawer(WHPal.ORL), new DrawFade());
                craftEffect = Fx.formsmoke;
                updateEffect = Fx.plasticburn;
                researchCostMultiplier = 0.6f;
            }
        };
        T2Electrolyzer = new GenericCrafter("t2-electrolyzer") {
            {
                requirements(Category.crafting, with(Items.silicon, 100, Items.graphite, 80, Items.tungsten, 40, WHItems.titaniumSteel, 40));

                size = 3;
                health = 750;
                hasPower = hasLiquids = true;
                craftTime = 60;
                liquidCapacity = 60;
                itemCapacity = 0;

                rotate = true;
                invertFlip = true;
                consumePower(3f);
                consumeLiquid(Liquids.water, 0.5f);
                outputLiquids = LiquidStack.with(Liquids.ozone, 15f / 60, Liquids.hydrogen, 30f / 60);
                drawer = new DrawMulti(
                        new DrawRegion("-bottom"),
                        new DrawLiquidTile(Liquids.water, 2f),
                        new DrawBubbles(Color.valueOf("7693e3")) {{
                            sides = 10;
                            recurrence = 3f;
                            spread = 6;
                            radius = 1.5f;
                            amount = 20;
                        }},
                        new DrawRegion(),
                        new DrawLiquidOutputs(),
                        new DrawGlowRegion() {{
                            alpha = 0.7f;
                            color = Color.valueOf("c4bdf3");
                            glowIntensity = 0.3f;
                            glowScale = 6f;
                        }}
                );
                regionRotated1 = 3;
                liquidOutputDirections = new int[]{2, 4};
            }
        };

        slagfurnace = new Separator("slag-furnace") {{

            requirements(Category.crafting, with(Items.graphite, 150, WHItems.titaniumSteel, 400, Items.surgeAlloy, 200, WHItems.ceramite, 200, WHItems.refineCeramite, 80));
            size = 3;
            health = 850;
            hasPower = hasLiquids = hasItems = true;
            craftTime = 60;
            liquidCapacity = 120;
            itemCapacity = 30;
            consumePower(15f);
            consumeLiquid(Liquids.slag, 120 / 60f);
            results = with(
                    WHItems.ceramite, 3,
                    WHItems.titaniumSteel, 5,
                    WHItems.refineCeramite, 2,
                    Items.surgeAlloy, 4,
                    Items.phaseFabric, 4
            );
            drawer = new DrawMulti(new DrawRegion("-bottom"),
                    new DrawLiquidTile(Liquids.slag),
                    new DrawDefault(),
                    new DrawFlame(Color.valueOf("FF8C7AFF")));
            ambientSound = Sounds.pulse;
            ambientSoundVolume = 0.3f;
            researchCostMultiplier = 0.6f;

        }};

        //钛钢
        titaniumSteelFurnace = new GenericCrafter("titanium-steel-furnace") {
            {
                Color color = WHPal.TiSteelColor;
                requirements(Category.crafting, with(Items.lead, 30, Items.copper, 50, Items.silicon, 30));

                health = 150;
                hasItems = hasPower = true;
                craftTime = 60;
                itemCapacity = 10;
                size = 2;
                drawer = new DrawMulti(new DrawDefault(), new DrawFlame(color));
                consumePower(1);
                consumeItems(with(Items.titanium, 1, Items.metaglass, 2));
                craftEffect = WHFx.square(TiSteelColor, 35f, 4, 16f, 4f);
                outputItem = new ItemStack(WHItems.titaniumSteel, 1);
                researchCostMultiplier = 0.2f;
            }
        };

        T2TiSteelFurnace = new GenericCrafter("t2-ti-steel-furnace") {
            {

                Color color = WHPal.TiSteelColor;
                requirements(Category.crafting, with(Items.silicon, 100, WHItems.ceramite, 50, WHItems.titaniumSteel, 70));
                health = 700;
                hasItems = hasPower = true;
                craftTime = 60;
                itemCapacity = 40;
                size = 3;
                consumePower(5);
                consumeItems(with(Items.titanium, 6, Items.metaglass, 9));
                consumeLiquid(Liquids.water, 15 / 60f);
                outputItem = new ItemStack(WHItems.titaniumSteel, 6);
                drawer = new DrawMulti(new DrawDefault(), new DrawFlame(color));
                craftEffect = WHFx.square(TiSteelColor, 35f, 6, 26f, 5f);
                researchCostMultiplier = 0.45f;
            }
        };

        T2SporePress = new GenericCrafter("t2-spore-press") {{

            requirements(Category.crafting, with(Items.silicon, 60, Items.plastanium, 50, WHItems.titaniumSteel, 70));
            health = 700;
            hasItems = hasPower = hasLiquids = true;
            craftTime = 60;
            itemCapacity = 40;
            liquidCapacity = 120;
            size = 3;
            consumePower(2);
            consumeItems(with(Items.sporePod, 9));
            outputLiquid = new LiquidStack(Liquids.oil, 1);
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawPistons() {{
                        sinMag = 1f;
                    }},
                    new DrawDefault(),
                    new DrawLiquidRegion(Liquids.oil),
                    new DrawRegion("-top")
            );

            researchCostMultiplier = 0.45f;
        }};

        T2Cultivator = new AttributeCrafter("t2-cultivator") {{
            requirements(Category.crafting, with(Items.copper, 90, Items.silicon, 60, WHItems.titaniumSteel, 50));
            health = 400;
            hasItems = hasPower = hasLiquids = true;
            craftTime = 120;
            itemCapacity = 20;
            liquidCapacity = 120;
            size = 3;
            consumePower(5);
            consumeLiquid(Liquids.water, 30f / 60f);
            outputItem = new ItemStack(Items.sporePod, 6);

            craftEffect = Fx.none;
            envRequired |= Env.spores;
            attribute = Attribute.spores;
            legacyReadWarmup = true;
            drawer = new DrawMulti(
                    new DrawDefault(),
                    new DrawCultivator() {{
                        radius = 4f;
                    }},
                    new DrawRegion("-top")
            );
            maxBoost = 2f;
            researchCostMultiplier = 0.45f;
        }};

        T2CryofluidMixer = new GenericCrafter("t2-cryofluid-mixer") {
            {
                requirements(Category.crafting, with(Items.silicon, 80, WHItems.ceramite, 20, WHItems.titaniumSteel, 50));
                health = 600;
                hasItems = hasPower = hasLiquids = true;
                craftTime = 60;
                itemCapacity = 20;
                liquidCapacity = 120;
                size = 3;
                consumePower(4);
                consumeLiquid(Liquids.water, 0.5f);
                outputLiquid = new LiquidStack(Liquids.cryofluid, 0.65f);
                drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.water),
                        new DrawLiquidTile(Liquids.cryofluid) {{
                            drawLiquidLight = true;
                        }}, new DrawDefault());
                craftEffect = WHFx.square(Liquids.cryofluid.color, 35f, 4, 16f, 5f);
                researchCostMultiplier = 0.45f;
            }
        };

        T2PhaseSynthesizer = new GenericCrafter("t2-phase-synthesizer") {{

            requirements(Category.crafting, with(Items.silicon, 120, Items.carbide, 20, WHItems.ceramite, 50, WHItems.ceramite, 20, WHItems.titaniumSteel, 100));
            health = 1000;
            hasItems = hasPower = hasLiquids = true;
            craftTime = 120;
            itemCapacity = 20;
            liquidCapacity = 120;
            size = 3;
            consumePower(5);
            consumeLiquid(Liquids.ozone, 30f / 60f);
            consumeItems(with(Items.thorium, 4, Items.sand, 10));
            outputItem = new ItemStack(Items.phaseFabric, 5);
            drawer = new DrawMulti(new DrawRegion("-bottom"),
                    new DrawLiquidTile(Liquids.ozone) {{
                        drawLiquidLight = true;
                        alpha = 0.5f;
                    }},
                    new PhaseWeave(),
                    new DrawDefault());

            craftEffect = WHFx.square(Items.phaseFabric.color, 35f, 4, 16f, 5f);
            researchCostMultiplier = 0.45f;
        }};
        //钷素
        promethiumRefinery = new GenericCrafter("promethium-refinery") {
            {

                requirements(Category.crafting, with(Items.phaseFabric, 20, Items.tungsten, 50, WHItems.ceramite, 20, WHItems.titaniumSteel, 50));

                health = 600;
                hasItems = hasPower = hasLiquids = true;
                craftTime = 120;
                itemCapacity = 20;
                liquidCapacity = 180;
                size = 3;
                consumePower(5);
                consumeItems(with(Items.phaseFabric, 2));
                consumeLiquid(Liquids.oil, 30f / 60f);
                consumeLiquid(WHLiquids.orePromethium, 30 / 60f);
                outputLiquid = new LiquidStack(WHLiquids.refinePromethium, 31f / 60f);
                drawer = new DrawMulti(new DrawRegion("-bottom"),
                        new DrawLiquidRegion(WHLiquids.orePromethium) {{
                            alpha = 0.7f;
                        }},
                        new DrawLiquidTile(WHLiquids.refinePromethium) {{
                            alpha = 0.7f;
                        }},
                        new DrawGlowRegion() {{
                            color = WHPal.RefinePromethiumColor;
                            rotateSpeed = 1.6f;
                            glowIntensity = 0.5f;
                            alpha = 0.4f;
                            layer = -1;
                        }},
                        new DrawDefault());
                craftEffect = WHFx.square(WHLiquids.refinePromethium.color, 35f, 4, 16f, 5f);
                researchCostMultiplier = 0.6f;
            }
        };

        sealedPromethiumMill = new GenericCrafter("sealed-promethium-mill") {{

            requirements(Category.crafting, with(Items.tungsten, 150, Items.plastanium, 100, WHItems.ceramite, 60, Items.phaseFabric, 80));

            health = 2000;
            hasItems = hasPower = hasLiquids = true;
            craftTime = 120;
            itemCapacity = 40;
            liquidCapacity = 120;
            size = 4;
            consumePower(15);
            consumeLiquid(WHLiquids.refinePromethium, 30f / 60f);
            consumeItems(with(Items.phaseFabric, 3, WHItems.ceramite, 2));
            outputItems = with(WHItems.sealedPromethium, 3);
            drawer = new DrawMulti(new DrawRegion("-bottom"),
                    new DrawLiquidTile(WHLiquids.refinePromethium),
                    new SealedPromethiumMillDrawer(),
                    new DrawDefault(),
                    new DrawArcSmelt() {{
                        midColor = flameColor = Pal.sapBullet;
                        particleRad = 40f;
                        particleLen = 7f;

                    }},
                    new DrawGlowRegion() {{
                        color = Pal.sapBullet;
                        glowIntensity = 0.3f;
                        glowScale = 6f;
                    }}
            );
            craftEffect = new MultiEffect(new WrapEffect(WHFx.circleOut(40, 40f, 5), Pal.sapBullet),
                    new WrapEffect(WHFx.circleOut(30, 40f, 5), Pal.sapBullet).startDelay(40));

            destroyBullet = WHBullets.sealedPromethiumMillBreak;
            researchCostMultiplier = 0.6f;
        }};

        scrapCrusher = new MultiCrafter("scrap-crusher") {{

            requirements(Category.crafting, with(Items.silicon, 50, Items.lead, 50, WHItems.titaniumSteel, 30, Items.graphite, 80));

            health = 400;
            hasItems = hasPower = true;
            hasLiquids = false;
            itemCapacity = 20;
            outputsLiquid = false;
            size = 2;
            useBlockDrawer = true;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawDefault());
            craftPlans.add(
                    new CraftPlan() {{
                        craftTime = 60f;
                        consumeItem(Items.scrap, 3);
                        outputItems = with(Items.sand, 4);
                        consumePower(100 / 60f);
                        craftEffect = new MultiEffect(WHFx.square(Items.scrap.color, 35f, 4, 16f, 5f),
                                WHFx.arcSmelt(Items.copper.color, 13f, 6, 60f));
                    }}
                    , new CraftPlan() {{
                        craftTime = 20f;
                        consumeItem(Items.copper, 1);
                        outputItems = with(Items.scrap, 2);
                        consumePower(100 / 60f);
                        craftEffect = new MultiEffect(WHFx.square(Items.copper.color, 35f, 4, 16f, 5f),
                                WHFx.arcSmelt(Items.copper.color, 13f, 6, 60f));
                    }}
                    , new CraftPlan() {{
                        craftTime = 30f;
                        consumeItem(Items.lead, 1);
                        outputItems = with(Items.sand, 2);
                        consumePower(100 / 60f);
                        craftEffect = new MultiEffect(WHFx.square(Items.lead.color, 35f, 4, 16f, 5f),
                                WHFx.arcSmelt(Items.lead.color, 13f, 6, 60f));
                    }}, new CraftPlan() {{
                        craftTime = 30f;
                        consumeItem(Items.titanium, 1);
                        outputItems = with(Items.sand, 3);
                        consumePower(100 / 60f);
                        craftEffect = new MultiEffect(WHFx.square(Items.titanium.color, 35f, 4, 16f, 5f),
                                WHFx.arcSmelt(Items.titanium.color, 13f, 6, 60f));
                    }});
        }};

        scrapFurance = new GenericCrafter("scrap-furance") {{
            requirements(Category.crafting, with(WHItems.titaniumSteel, 100, Items.lead, 120, Items.graphite, 120));
            health = 800;
            outputLiquid = new LiquidStack(Liquids.slag, 1);
            size = 2;
            craftTime = 10f;
            hasLiquids = hasPower = true;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.slag), new DrawDefault());

            consumePower(3f);
            consumeItem(Items.scrap, 4);
            researchCostMultiplier = 0.6f;
        }};

        largeSurgeSmelter = new GenericCrafter("large-surge-smelter") {{
            requirements(Category.crafting, with(WHItems.titaniumSteel, 120, Items.thorium, 180, Items.carbide, 80, WHItems.ceramite, 50));

            outputItem = new ItemStack(Items.surgeAlloy, 3);
            size = 4;
            craftTime = 60f;
            health = 1200;
            hasItems = hasPower = hasLiquids = true;
            liquidCapacity = 50;
            itemCapacity = 50;
            consumePower(8f);
            consumeItems(with(Items.copper, 10, Items.silicon, 10, WHItems.titaniumSteel, 8));
            consumeLiquid(WHLiquids.refinePromethium, 10 / 60f);
            drawer = new DrawMulti(new DrawDefault(),
                   /* new DrawFlame() {{
                        flameRadius = 0;
                        flameRadiusIn = 0;
                        flameRadiusMag = 0;
                        flameRadiusInMag = 0;
                        flameColor = Color.valueOf("F2E770");
                    }},*/
                    new DrawGlowRegion() {{
                        color = Color.valueOf("FFDEB5FF");
                        layer = Layer.effect;
                        glowIntensity = 0.5f;
                    }});
            updateEffect = WHFx.hexagonSpread(Items.surgeAlloy.color, 10f, 20f);
            craftEffect = WHFx.hexagonSmoke(Items.surgeAlloy.color, 30f, 1.2f, 10, 20f);
            researchCostMultiplier = 0.6f;
        }};

        LiquidNitrogenPlant = new GenericCrafter("Liquid-nitrogen-plant") {{

            requirements(Category.crafting, with(WHItems.titaniumSteel, 80, Items.plastanium, 60, Items.carbide, 40, WHItems.ceramite, 40));

            size = 2;
            craftTime = 60f;
            health = 550;
            hasPower = hasLiquids = true;
            liquidCapacity = 80;
            consumePower(4f);
            consumeLiquid(Liquids.nitrogen, 36 / 60f);
            consumeLiquid(Liquids.cryofluid, 18 / 60f);
            outputLiquid = new LiquidStack(WHLiquids.liquidNitrogen, 0.6f);
            drawer = new DrawMulti(new DrawRegion() {{
                suffix = "-bottom";
            }}, new DrawLiquidTile() {{
                drawLiquid = Liquids.cryofluid;
            }}, new DrawLiquidTile() {{
                drawLiquid = WHLiquids.liquidNitrogen;
            }},
                    new DrawDefault());
            updateEffect = WHFx.square(Liquids.nitrogen.color, 20f, 4, 12, 5);
            researchCostMultiplier = 0.6f;
        }};

        tungstenConverter = new HeatCrafter("tungsten-converter") {{
            requirements(Category.crafting, with(WHItems.titaniumSteel, 50, Items.plastanium, 60, Items.phaseFabric, 20));

            size = 2;
            craftTime = 90f;
            health = 400;
            hasPower = hasItems = true;
            itemCapacity = 20;
            consumePower(2f);
            consumeItem(Items.thorium, 1);
            outputItem = new ItemStack(Items.tungsten, 1);
            drawer = new DrawMulti(new DrawDefault(),
                    new DrawGlowRegion() {{
                        alpha = 0.7f;
                        color = Color.valueOf("F89661FF");
                        glowIntensity = 0.3f;
                        glowScale = 6f;
                        rotateSpeed = 1.5f;

                    }},
                    new DrawHeatInput());
            heatRequirement = 4f;
            ambientSound = Sounds.smelter;
            updateEffect = WHFx.square(Items.tungsten.color, 20f, 4, 12, 5);
            researchCostMultiplier = 0.6f;
        }};
        //热量
        combustionHeater = new FlammabilityHeatProducer("combustion-heater") {
            {
                requirements(Category.crafting, with(WHItems.titaniumSteel, 50, Items.lead, 60, Items.silicon, 40));
                size = 2;
                craftTime = 90f;
                health = 200;
                hasItems = true;
                itemCapacity = 20;
                consume(new ConsumeItemFlammable());
                heatOutput = 1.8f;
                drawer = new DrawMulti(new DrawDefault(),
                        new DrawHeatOutput(),
                        new DrawHeatInput() {{
                            suffix = "-heat";
                        }});
                ;
                drawer = new DrawMulti(new DrawDefault(),
                        new DrawHeatOutput(),
                        new DrawHeatInput() {{
                            suffix = "-heat";
                        }});
                ambientSound = Sounds.hum;
                ambientSoundVolume = 0.002f;
                researchCostMultiplier = 0.6f;
            }
        };

        decayHeater = new HeatProducer("decay-heater") {
            {
                requirements(Category.crafting, with(WHItems.titaniumSteel, 50, Items.plastanium, 60, Items.carbide, 50));
                size = 3;
                craftTime = 240f;
                health = 1000;
                hasItems = true;
                itemCapacity = 20;
                consumeItem(Items.thorium, 2);
                heatOutput = 6f;
                drawer = new DrawMulti(new DrawDefault(),
                        new DrawHeatOutput(),
                        new DrawHeatInput() {{
                            suffix = "-heat";
                        }});
                ambientSound = Sounds.smelter;
                ambientSoundVolume = 0.002f;
                researchCostMultiplier = 0.7f;
            }
        };

        promethiumHeater = new HeatProducer("promethium-heater") {{
            requirements(Category.crafting, with(Items.thorium, 220, Items.silicon, 150, Items.carbide, 100, WHItems.ceramite, 100, WHItems.titaniumSteel, 100));

            size = 3;
            craftTime = 4f;
            health = 1000;
            hasItems = false;
            hasLiquids = hasPower = true;
            liquidCapacity = 80;
            consumeLiquid(WHLiquids.refinePromethium, 15 / 60f);
            heatOutput = 40f;
            drawer = new DrawMulti(new DrawRegion("-bottom"),
                    new DrawLiquidTile(WHLiquids.refinePromethium),
                    new DrawDefault(),
                    new DrawHeatOutput(),
                    new DrawHeatInput() {{
                        suffix = "-heat";
                    }});
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.002f;
            researchCostMultiplier = 0.8f;
        }};

        smallHeatRouter = new HeatConductor("small-heat-router") {
            {

                requirements(Category.crafting, with(Items.copper, 15, Items.graphite, 10, Items.titanium, 10));

                researchCostMultiplier = 2f;

                group = BlockGroup.heat;
                size = 1;
                drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput(-1, false), new DrawHeatOutput(), new DrawHeatOutput(1, false), new DrawHeatInput("-heat"));
                regionRotated1 = 1;
                splitHeat = true;
            }
        };

        heatBelt = new

                HeatBelt("Heat-Belt") {
                    {

                        requirements(Category.crafting, with(Items.lead, 10, Items.graphite, 2, WHItems.titaniumSteel, 2));
                        speed = 0;
                        drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput(), new DrawHeatInput("-heat"));
                        researchCostMultiplier = 10f;
                        rotate = true;
                        group = BlockGroup.heat;
                        size = 1;
                        regionRotated1 = 1;
                    }
                };

        heatBridge = new HeatDirectionBridge("heat-bridge") {

            {

                requirements(Category.distribution, with(Items.phaseFabric, 5, Items.silicon, 7, Items.lead, 10, Items.graphite, 10));
                size = 1;
                range = 6;
                health = 90;
                researchCostMultiplier = 2f;
                regionRotated1 = 1;
                hasPower = true;
                pulse = true;
                envEnabled |= Env.space;

                consumePower(1f);
            }
        };
//陶钢
        ceramiteSteelFoundry = new GenericCrafter("ceramite-steel-foundry") {
            {
                Color color = CeramiteColor;
                requirements(Category.crafting, with(Items.lead, 50, Items.silicon, 50, Items.tungsten, 25, Items.plastanium, 25));
                health = 300;
                hasItems = hasPower = true;
                craftTime = 60;
                itemCapacity = 20;
                size = 2;
                consumePower(4);
                consumeItems(with(Items.tungsten, 4, Items.plastanium, 4));
                outputItem = new ItemStack(WHItems.ceramite, 2);
                drawer = new DrawMulti(new DrawDefault(), new DrawFlame(color));
                craftEffect = WHFx.square(CeramiteColor, 35f, 4, 16f, 4f);
            }
        };

        ceramiteRefinery = new

                GenericCrafter("ceramite-refinery") {
                    {
                        Color color = WHPal.RefineCeramiteColor;
                        requirements(Category.crafting, with(Items.surgeAlloy, 50, Items.carbide, 60, WHItems.ceramite, 50, Items.phaseFabric, 50));
                        health = 1000;
                        hasItems = hasPower = hasLiquids = true;
                        craftTime = 120;
                        itemCapacity = 30;
                        size = 3;
                        consumePower(6);
                        consumeItems(with(WHItems.ceramite, 3, Items.tungsten, 3, Items.surgeAlloy, 3));
                        consumeLiquid(WHLiquids.refinePromethium, 0.3f);
                        outputItem = new ItemStack(WHItems.refineCeramite, 3);
                        drawer = new DrawMulti(new DrawDefault(), new DrawFlame(color));
                        craftEffect = new RadialEffect(Fx.surgeCruciSmoke, 4, 90f, 40 / 4f) {{
                            rotationOffset = 45F;
                        }};
                        researchCostMultiplier = 0.6f;
                    }
                };

        ADMill = new
                GenericCrafter("admantium-mill") {
                    {
                        requirements(Category.crafting, with(Items.silicon, 200, WHItems.titaniumSteel, 50, WHItems.ceramite, 50, WHItems.refineCeramite, 30));

                        hasItems = true;
                        health = 600;
                        size = 3;
                        hasPower = true;
                        hasLiquids = true;
                        liquidCapacity = 40;
                        itemCapacity = 20;
                        craftTime = 120;
                        consumePower(6f);
                        consumeItems(with(WHItems.vibranium, 4, WHItems.refineCeramite, 3));
                        consumeLiquid(WHLiquids.liquidNitrogen, 0.3f);
                        outputItem = new ItemStack(WHItems.adamantium, 2);
                        drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("FFEA96FF")), new AdmantiumMillDrawer(Items.surgeAlloy.color.cpy(), 7.5f));
                        craftEffect = WHFx.hexagonSmoke(Color.valueOf("FFEA96FF"), 35, 1f, 7.5f, 20f);
                        researchCostMultiplier = 0.6f;
                    }
                };
        //Drill
        heavyCuttingDrill = new BurstDrill("heavy-cutting-drill") {
            {
                requirements(Category.production, with(Items.copper, 100, Items.lead, 100, Items.graphite, 100, Items.silicon, 200, WHItems.titaniumSteel, 100));

                health = 2900;
                size = 4;
                tier = 3;
                arrowOffset = 0;
                arrowSpacing = 0;
                arrows = 1;
                itemCapacity = 100;
                liquidCapacity = 50;
                glowColor = Color.valueOf("FEE984FF");
                fogRadius = 1;
                drawRim = true;
                hasItems = hasPower = hasLiquids = true;
                consumePower(7);
                consumeLiquid(Liquids.water, 15f / 60f);
                drillTime = 45;
                drillEffect = new MultiEffect(Fx.mineImpact, Fx.drillSteam, Fx.mineImpactWave.wrap(Pal.redLight, 40f));
                researchCostMultiplier = 0.6f;

            }
        };

        highEnergyDrill = new BurstDrill("high-energy-drill") {
            {
                requirements(Category.production, with(Items.tungsten, 300, Items.graphite, 300, Items.silicon, 500, WHItems.ceramite, 150, WHItems.refineCeramite, 100));

                health = 4000;
                size = 5;
                tier = 15;
                arrowOffset = 2;
                arrowSpacing = 5;
                arrows = 2;
                itemCapacity = 120;
                liquidCapacity = 80;
                glowColor = Items.surgeAlloy.color;
                fogRadius = 2;
                drawRim = true;
                hasItems = hasPower = hasLiquids = true;
                consumePower(15);
                consumeLiquid(Liquids.cryofluid, 0.3f);
                drillTime = 45;
                drillEffect = new MultiEffect(Fx.mineImpact, Fx.drillSteam,
                        new WrapEffect(Fx.dynamicSpikes, Items.surgeAlloy.color, 30f),
                        new WrapEffect(Fx.mineImpactWave, Items.surgeAlloy.color, 45f));

            }
        };

            /*
{
	"name": "高能钻井",
	"type": "BurstDrill",
	"health": 3550,
	"size": 5,
	"tier": 15,
	"arrowOffset": 2,
	"arrowSpacing": 5,
	"arrows": 2,
	"itemCapacity": 50,
	"liquidCapacity": 20,
	"glowColor": "FEE984FF",
	"fogRadius": 0.5,
	"drawRim": true,
	"hasItems": true,
	"drillTime": 45,
	"drillEffect": {
		"type": "MultiEffect",
		"effects": [
			"mineImpact",
			"drillSteam",
			{
				"type": "WrapEffect",
				"effect": "dynamicSpikes",
				"color": "FEE984FF",
				"rotation": 30
			},
			{
				"type": "WrapEffect",
				"effect": "mineImpactWave",
				"color": "FEE984FF",
				"rotation": 45
			}
		]
	},
	"consumes": {
		"power": 15,
		"liquid": {
			"liquid": "cryofluid",
			"amount": 0.3
		}
	},
	"requirements": [
		"tungsten/300",
		"graphite/250",
		"silicon/400",
		"ceramite/100",
		"refine-ceramite/50"
	],
	"category": "production",
	"research": {
		"parent": "slag-extractor"
	},
	"researchCostMultiplier": 0.25
}*/


        flash = new

                PowerTurret("Flash") {
                    {
                        shootType = new PositionLightningBulletType(50.0F) {
                            {
                                maxRange = 300.0F;
                                rangeOverride = 300.0F;
                                lightningColor = WHPal.rim3;
                                lightningDamage = 50.0F;
                                lightning = 3;
                                lightningLength = 6;
                                lightningLengthRand = 6;
                                hitEffect = WHFx.lightningSpark;
                            }
                        };
                    }
                }

        ;
        collapse = new

                ItemTurret("Collapse") {
                    {
                        ammo(WHItems.sealedPromethium, WHBullets.collapseSp, Items.phaseFabric, WHBullets.collaspsePf);
                    }
                }
        ;


        sb = new

                SpecificMineralDrill("傻逼") {
                    {
                        requirements(Category.production, with(Items.silicon, 1));
                        targetItems = new Item[]{Items.thorium, Items.tungsten};
                        drillTime = 600;
                        size = 4;
                        hasPower = true;
                        tier = 1145;
                        drillEffect = new MultiEffect(Fx.drillSteam);
                        itemCapacity = 40;
                        researchCostMultiplier = 0.5f;
                        fogRadius = 4;

                        consumePower(160f / 60f);
                    }
                };
        sb3 = new

                PowerTurret("激光") {
                    {
                        requirements(Category.turret, with(Items.copper, 1200, Items.lead, 350, Items.graphite, 300, Items.surgeAlloy, 325, Items.silicon, 325));
                        shootEffect = Fx.shootBigSmoke2;
                        shootCone = 40f;
                        recoil = 4f;
                        size = 4;
                        shake = 2f;
                        range = 195f;
                        reload = 500f;
                        shootSound = Sounds.laserbig;
                        loopSound = Sounds.beam;
                        loopSoundVolume = 2f;
                        envEnabled |= Env.space;

                        shootType = new SlowLaserBulletType(100) {{
                            maxLength = 400f;
                            maxRange = 400f;
                            hitEffect = Fx.hitMeltdown;
                            hitColor = Pal.meltdownHit;
                            status = StatusEffects.melting;
                            drawSize = 420f;
                            lifetime = 180f;
                            pierceCap = 5;
                            width = collisionWidth = 15f;
                            incendChance = 0.4f;
                            incendSpread = 5f;
                            incendAmount = 1;
                            ammoMultiplier = 1f;

                        }};

                        scaledHealth = 200;
                        coolant = consumeCoolant(0.5f);
                        consumePower(17f);
                    }
                }

        ;
        sb4 = new

                PowerTurret("laser") {
                    {
                        requirements(Category.turret, with(Items.copper, 1200, Items.lead, 350, Items.graphite, 300, Items.surgeAlloy, 325, Items.silicon, 325));
                        shootEffect = Fx.shootBigSmoke2;
                        shootCone = 40f;
                        recoil = 4f;
                        size = 4;
                        shake = 2f;
                        range = 195f;
                        reload = 500f;
                        shootSound = Sounds.laserbig;
                        loopSound = Sounds.beam;
                        loopSoundVolume = 2f;
                        envEnabled |= Env.space;

                        shootType = new LaserBeamBulletType(100) {{
                            colors = new Color[]{Pal.lancerLaser.cpy().a(0.4f), Pal.lancerLaser, Color.white};
                            lifetime = 30f;
                            pierceCap = 5;
                            width = 28f;
                            length = 300f;

                            lightningColor = Pal.lancerLaser;
                            lightningSpacing = 20f;
                            lightningLength = 10;
                            lightningDelay = 1.1f;
                            lightningDamage = 50;
                            largeHit = true;
                            drawPositionLighting = true;

                        }};
                        scaledHealth = 200;
                        coolant = consumeCoolant(0.5f);
                        consumePower(17f);
                    }
                };
        Deflection = new BulletDefenseTurret("Deflection") {{

            requirements(Category.turret, with(WHItems.titaniumSteel, 500, Items.carbide, 200, WHItems.ceramite, 200, WHItems.refineCeramite, 100, WHItems.sealedPromethium, 50));

            size = 4;
            health = 5300;
            outlineColor = Color.valueOf("36363CFF");
            outlineRadius = 3;
            shootWarmupSpeed = 0.1f;
            minWarmup = 0.8f;
            warmupMaintainTime = 120f;
            researchCostMultiplier = 0.6f;
            }};
        }};

