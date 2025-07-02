//
package wh.content;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Interp;
import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.effect.*;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.defense.RegenProjector;
import mindustry.world.blocks.defense.ShieldWall;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.blocks.distribution.ItemBridge;
import mindustry.world.blocks.distribution.MassDriver;
import mindustry.world.blocks.distribution.StackConveyor;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.heat.HeatConductor;
import mindustry.world.blocks.heat.HeatProducer;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.liquid.LiquidBridge;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.blocks.units.RepairTower;
import mindustry.world.consumers.ConsumeItemExplode;
import mindustry.world.consumers.ConsumeItemFlammable;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import wh.entities.bullet.*;
import wh.entities.world.blocks.defense.*;
import wh.entities.world.blocks.distribution.*;
import wh.entities.world.blocks.effect.SelectForceProjector;
import wh.entities.world.blocks.effect.SelectOverdriveProjector;
import wh.entities.world.blocks.production.*;
import wh.entities.world.blocks.storage.FrontlineCoreBlock;
import wh.graphics.WHPal;
import wh.entities.world.blocks.defense.turrets.BulletDefenseTurret;
import wh.entities.world.blocks.effect.BaseForceProjector;
import wh.entities.world.blocks.unit.UnitCallBlock;
import wh.entities.world.drawer.*;

import static mindustry.type.ItemStack.with;
import static wh.graphics.WHPal.CeramiteColor;
import static wh.graphics.WHPal.TiSteelColor;

public final class WHBlocks {
    public static Block promethium;
    public static Block vibraniumOre;
    //factory
    public static Block ADMill, T2TiSteelFurnace, titaniumSteelFurnace,
            ceramiteSteelFoundry, ceramiteRefinery,
            promethiumRefinery, sealedPromethiumMill,
            siliconMixFurnace, atmosphericSeparator, T2CarbideCrucible, largeKiln, T2PlastaniumCompressor, T2Electrolyzer, T2SporePress,
            T2Cultivator, T2CryofluidMixer, T2PhaseSynthesizer, largeSurgeSmelter,
            LiquidNitrogenPlant, slagfurnace, scrapCrusher, scrapFurance, tungstenConverter,
            combustionHeater, decayHeater, promethiumHeater, smallHeatRouter, heatBelt, heatBridge, heatBelt2;
    //drill
    public static Block MechanicalQuarry, heavyCuttingDrill, highEnergyDrill, SpecialCuttingDrill,
            strengthenOilExtractor, slagExtractor, promethiumExtractor, heavyExtractor;
    //liquid
    public static Block gravityPump, tiSteelPump, T2LiquidTank, tiSteelConduit,
            tiSteelBridgeConduit, T2BridgeConduit;
    //effect
    public static Block wrapProjector, wrapOverdrive, armoredVault,
            T2RepairTower, fortlessShield, strongholdCore, T2strongholdCore,selectProjector;
    //distribution
    public static Block steelDust, steelBridge, T2Bridge, ceramiteConveyor, steelUnloader, trackDriver;
    //power
    public static Block T2steamGenerator, T2thermalGenerator, T2impactReactor, plaRector, promethiunmRector,
            Mbattery, Lbattery, MK3battery, TiNode, T2TiNode;
    //turrets
    public static Block flash, collapse, Deflection;
    //TEST
    public static Block sb, sb3, sb4, sb5, sb6, sb7, sb8, sb9, sb10, sb11, sb12, sb13, sb14, sb15, sb16,airDrop;


    private WHBlocks() {
    }

    public static void load() {
        promethium = new Floor("promethium");
        vibraniumOre = new OreBlock("vibranium-ore");

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

     /*   heatBelt = new HeatBelt2("Heat-Belt") {
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
                };*/
        heatBelt2 = new HeatBelt("Heat-Belt") {
            {

                requirements(Category.distribution, with(Items.lead, 10, Items.graphite, 2, WHItems.titaniumSteel, 2));
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
        MechanicalQuarry = new Quarry("mechanical-quarry") {{

            requirements(Category.production, with(Items.graphite, 50, Items.metaglass, 30, Items.silicon, 60));

            health = 300;
            size = 3;
            regionRotated1 = 1;
            itemCapacity = 100;
            acceptsItems = true;

            areaSize = 11;
            liquidBoostIntensity = 1.5f;
            mineTime = 400f;

            tier = 3;

            drawDrill = true;
            deploySpeed = 0.015f;
            deployInterp = new Interp.PowOut(4);
            deployInterpInverse = new Interp.PowIn(4);
            drillMoveSpeed = 0.07f;
            consumePower(2.5f);
            consumeLiquid(Liquids.water, 7.5f / 60f).boost();

        }};
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
                researchCostMultiplier = 0.6f;

            }
        };

        SpecialCuttingDrill = new SDrill("heavy-steel-laser-drill") {
            {
                requirements(Category.production, with(Items.tungsten, 100, Items.silicon, 200, Items.plastanium, 100, WHItems.ceramite, 100));

                health = 3200;
                drillTime = 600;
                size = 4;
                tier = 5;
                itemCapacity = 120;
                liquidCapacity = 80;
                mineOffset = -2;
                mineSize = 6;
                fogRadius = 2;
                drawRim = true;
                hasItems = hasPower = hasLiquids = true;
                consumePower(8);
                consumeLiquid(Liquids.cryofluid, 0.3f);
                allowedItems = Seq.with(
                        Items.thorium,
                        Items.tungsten
                );

                drillTime = 80;
                drillEffect = new MultiEffect(Fx.drillSteam);
                updateEffect = new Effect(180, 100, e -> {
                    float fadeTime = 60f;
                    float fout = Mathf.clamp(e.time > e.lifetime - fadeTime ?
                            1f - (e.time - (e.lifetime - fadeTime)) / fadeTime : 1f, 0, 1);
                    float fade = Interp.pow2Out.apply(fout) * e.fin(Interp.pow5In);
                    Draw.color(WHPal.SkyBlue.cpy());
                    Lines.stroke(fade * 10.0F);
                    Lines.square(e.x, e.y, 32 * e.fin(Interp.pow5In), 90f);

                });

                researchCostMultiplier = 0.6f;

            }
        };
        strengthenOilExtractor = new SolidPump("strengthen-oil-extractor") {
            {
                requirements(Category.production, with(Items.silicon, 80, Items.graphite, 90, WHItems.titaniumSteel, 120, Items.plastanium, 60));
                health = 4000;
                size = 4;
                floating = true;
                hasPower = true;
                hasLiquids = true;
                liquidCapacity = 60;
                requirements(Category.production, with(Items.silicon, 100, Items.graphite, 100, WHItems.titaniumSteel, 100, Items.plastanium, 100));
                health = 400;
                size = 3;
                floating = true;
                hasPower = true;
                hasLiquids = true;
                liquidCapacity = 60;
                pumpAmount = 0.15f;
                rotateSpeed = 1.3f;
                result = Liquids.oil;
                baseEfficiency = 2;
                attribute = Attribute.oil;
                consumePower(3);
                researchCostMultiplier = 0.6f;
            }
        };

        slagExtractor = new AttributeCrafter("slag-extractor") {
            {
                requirements(Category.production, with(Items.thorium, 100, Items.graphite, 100, Items.silicon, 200, WHItems.titaniumSteel, 150));

                health = 450;
                size = 3;
                hasPower = hasLiquids = true;
                liquidCapacity = 180;
                updateEffect = Fx.redgeneratespark;
                drawer = new DrawMulti(new DrawRegion("-bottom"),
                        new DrawLiquidRegion(Liquids.slag),
                        new DrawRegion("-转", 8.6f, true),
                        new DrawDefault());
                consumePower(8);
                outputLiquid = new LiquidStack(Liquids.slag, 0.8f);
                baseEfficiency = 0;
                maxBoost = 6;
                boostScale = 0.334f;
                minEfficiency = -1;
                attribute = Attribute.heat;
                researchCostMultiplier = 0.45f;
            }
        };

        promethiumExtractor = new SolidPump("promethium-extractor") {
            {
                requirements(Category.production, with(Items.silicon, 90, Items.graphite, 120, WHItems.titaniumSteel, 90, Items.thorium, 80));

                health = 550;
                size = 3;
                floating = true;
                hasPower = true;
                hasLiquids = true;
                liquidCapacity = 60;
                pumpAmount = 0.125f;
                rotateSpeed = 1.3f;
                result = WHLiquids.refinePromethium;
                baseEfficiency = 2;
                attribute = Attribute.oil;
                consumePower(1.5f);
                researchCostMultiplier = 0.36f;
                //抽钷机
            }
        };

        heavyExtractor = new SolidPump("heavy-extractor") {

            {
                requirements(Category.production, with(Items.lead, 50, Items.silicon, 40, WHItems.titaniumSteel, 40));
                health = 300;
                size = 2;
                hasPower = true;
                hasLiquids = true;
                liquidCapacity = 30;
                pumpAmount = 0.125f;
                rotateSpeed = 1.3f;
                result = Liquids.water;
                baseEfficiency = 2;
                attribute = Attribute.water;
                consumePower(2f);
                researchCostMultiplier = 0.5f;
                //硬化抽水机
            }
        };

        gravityPump = new Pump("gravity-pump") {
            {
                requirements(Category.liquid, with(Items.metaglass, 300, WHItems.titaniumSteel, 150, Items.plastanium, 130, Items.surgeAlloy, 50, WHItems.ceramite, 50));
                health = 1400;
                size = 4;
                drawer = new DrawMulti(new DrawPumpLiquid(), new DrawDefault());
                squareSprite = false;
                liquidCapacity = 800;
                hasLiquids = hasPower = true;
                pumpAmount = 0.36f;
                consumePower(12);
                researchCostMultiplier = 0.36f;
                //重力泵
            }
        };


        tiSteelPump = new Pump("titanium-steel-pump") {
            {
                requirements(Category.liquid, with(Items.copper, 50, WHItems.titaniumSteel, 30, Items.silicon, 50));
                health = 320;
                size = 2;
                drawer = new DrawMulti(new DrawPumpLiquid(), new DrawDefault());
                squareSprite = false;
                liquidCapacity = 120;
                hasLiquids = hasPower = true;
                pumpAmount = 0.32f;
                consumePower(1);
                researchCostMultiplier = 0.45f;
                //钛钢泵
            }
        };

        T2LiquidTank = new LiquidRouter("t2-titanium-steel-liquid-tank") {
            {
                requirements(Category.liquid, with(Items.lead, 150, WHItems.titaniumSteel, 100, Items.metaglass, 150, Items.plastanium, 100));
                health = 3000;
                size = 3;
                liquidCapacity = 7500;
                absorbLasers = true;
                researchCostMultiplier = 0.36f;
                //钛钢储液罐
            }
        };

        tiSteelBridgeConduit = new LiquidBridge("steel-bridge-conduit") {
            {
                requirements(Category.liquid, with(Items.lead, 20, Items.silicon, 25, WHItems.titaniumSteel, 10));
                health = 130;
                rotate = true;
                range = 8;
                liquidCapacity = 55f;
                arrowSpacing = 7;
                arrowOffset = 3.5f;
                arrowTimeScl = 12;
                bridgeWidth = 8;
                //钛钢导管桥
            }
        };

        tiSteelConduit = new Conduit("steel-conduit") {
            {
                requirements(Category.liquid, with(Items.titanium, 2, Items.metaglass, 3, WHItems.titaniumSteel, 1));
                health = 250;
                liquidCapacity = 55f;
                liquidPressure = 2.5f;
                bridgeReplacement = WHBlocks.tiSteelBridgeConduit;
                //钢导管
            }
        };

        T2BridgeConduit = new LiquidBridge("low-resistance-conduit") {
            {
                requirements(Category.liquid, with(Items.metaglass, 15, Items.plastanium, 10, WHItems.titaniumSteel, 20, Items.phaseFabric, 10));
                health = 230;
                range = 25;
                pulse = true;
                liquidCapacity = 120;
                consumePower(0.5f);
                arrowSpacing = 7;
                arrowOffset = 3.5f;
                arrowTimeScl = 12;
                bridgeWidth = 8;
                //低阻导管桥
            }
        };

        wrapProjector = new RegenProjector("wrap-projector") {
            {
                requirements(Category.effect, with(Items.plastanium, 100, Items.silicon, 200, WHItems.titaniumSteel, 100, Items.carbide, 100, WHItems.sealedPromethium, 50));

                health = 1500;
                size = 3;
                armor = 6;
                canOverdrive = false;
                healPercent = 2 / 60f;
                squareSprite = true;
                baseColor = Pal.sapBullet;
                drawer = new DrawMulti(new DrawLiquidTile(Liquids.nitrogen), new DrawGlowRegion() {{
                    color = Pal.sapBullet;
                }}, new DrawPulseShape() {{
                    square = false;
                    color = Pal.sapBullet.cpy();
                }}, new DrawShape() {{
                    color = Pal.sapBullet.cpy();
                    sides = 4;
                    radius = 4f;
                    useWarmupRadius = true;
                }}, new DrawRegion("-cap"),
                        new DrawDefault());
                effect = new MultiEffect(new ParticleEffect() {
                    {
                        particles = 3;
                        length = 12;
                        lifetime = 96;
                        sizeFrom = 4;
                        sizeTo = 0;
                        colorFrom = Pal.sapBullet.cpy().lerp(Pal.sapBulletBack, 0.5f);
                        colorTo = Pal.sapBullet.cpy();
                    }
                }
                );
                destroyBullet = WHBullets.warpBreak;
                range = 50;
                hasLiquids = true;
                consumePower(15);
                consumeLiquid(Liquids.nitrogen, 0.25f);
                researchCostMultiplier = 0.7f;
                //亚空间修复仪
            }
        };

        wrapOverdrive = new SelectOverdriveProjector("wrap-overdrive") {
            {
                requirements(Category.effect, with(Items.silicon, 250, WHItems.titaniumSteel, 250, Items.surgeAlloy, 150, Items.phaseFabric, 80, WHItems.sealedPromethium, 50));

                health = 100;
                size = 3;
                range = 300;
                phaseRangeBoost = 50;
                speedBoostPhase = 1;
                speedBoost = 1.7f;
                baseColor = Pal.sapBulletBack;
                phaseColor = Pal.sapBullet;
                hasBoost = true;
                useTime = 240;
                status = new StatusEffect[]{StatusEffects.overclock};
                boostStatus = new StatusEffect[]{WHStatusEffects.assault};
                squareSprite = false;
                consumePower(6);
                consumeLiquid(WHLiquids.refinePromethium, 0.1f);
                consumeItems(with(WHItems.sealedPromethium, 1)).boost();
                destroyBullet = WHBullets.warpBreak;
                researchCostMultiplier = 0.7f;
                //亚空间超速仪
                //通过亚空间能量扭曲部分时空，拉近现在与未来的帷幕来实现超速[red]摧毁会发生能量泄露
            }
        };

        armoredVault = new StorageBlock("armored-vault") {
            {
                requirements(Category.effect, with(Items.silicon, 2800, WHItems.titaniumSteel, 1500, Items.plastanium, 1000, WHItems.ceramite, 800));

                health = 2800;
                size = 3;
                itemCapacity = 30000;
                researchCostMultiplier = 0.6f;
                category = Category.effect;
                armor = 12;
                //装甲仓库
            }
        };
        T2RepairTower = new RepairTower("energy-repair-tower") {
            {
                requirements(Category.effect, with(Items.plastanium, 400, WHItems.titaniumSteel, 300, WHItems.ceramite, 300, WHItems.sealedPromethium, 200));

                health = 1500;
                size = 3;
                liquidCapacity = 200;
                range = 220;
                healAmount = 20;
                circleSpeed = 75;
                circleStroke = 8;
                squareRad = 8;
                squareSpinScl = 1.2f;
                glowMag = 0.3f;
                glowScl = 12f;
                consumePower(40);
                consumeLiquid(Liquids.ozone, 0.75f);
                researchCostMultiplier = 0.6f;
                //纳米修复塔
            }
        };

        fortlessShield = new BaseForceProjector("fortless-level-void-shield") {
            {
                requirements(Category.effect, with(Items.tungsten, 1500, WHItems.titaniumSteel, 1500, WHItems.ceramite, 650, WHItems.refineCeramite, 300));
                health = 8500;
                size = 5;
                radius = 300;
                sides = 24;
                canOverdrive = false;
                shieldHealth = 16000;
                phaseRadiusBoost = 0;
                phaseShieldBoost = 10000;
                cooldownNormal = 12;
                cooldownLiquid = 1.5f;
                cooldownBrokenBase = 12;
                coolantConsumption = 20 / 60f;
                liquidCapacity = 150;
                itemCapacity = 20;
                consumePower(40);
                researchCostMultiplier = 0.7f;
                //区块化虚空盾
            }
        };


        strongholdCore = new FrontlineCoreBlock("stronghold-core") {
            {
                requirements(Category.effect, buildVisibility = BuildVisibility.shown, with(Items.copper, 1000, Items.lead, 2000, Items.silicon, 1000, WHItems.titaniumSteel, 500));

                unitType = UnitTypes.evoke;
                armor = 22;
                health = 5000;
                itemCapacity = 2000;
                size = 4;
                unitCapModifier = 10;
                researchCostMultiplier = 0.7f;
                //据点核心

            }
        };

        T2strongholdCore = new FrontlineCoreBlock("large-stronghold-core") {
            {
                requirements(Category.effect, buildVisibility = BuildVisibility.shown, with(Items.thorium, 4300, Items.silicon, 8000, Items.plastanium, 3000, Items.surgeAlloy, 1500, WHItems.titaniumSteel, 5000, WHItems.ceramite, 800));
                unitType = UnitTypes.evoke;
                armor = 35;
                health = 30000;
                itemCapacity = 20000;
                size = 6;
                unitCapModifier = 10;
                researchCostMultiplier = 0.8f;
                //大型据点核心
            }
        };

        steelDust = new CoConveyor("steel-dust") {{

            requirements(Category.distribution, with(WHItems.titaniumSteel, 2, Items.lead, 1, Items.silicon, 2));

            health = 150;
            underBullets = true;
            hasShadow = true;
            placeableLiquid = true;
            size = 1;
            bridgeReplacement = WHBlocks.steelBridge;
            junctionReplacement = Blocks.invertedSorter;
            speed = 0.218f;
            displayedSpeed = 30;
            hasItems = true;
            itemCapacity = 2;
            researchCostMultiplier = 1;
            //钢质导轨
        }};

        steelBridge = new ItemBridge("steel-bridge-conveyor") {{
            {
                requirements(Category.distribution, with(Items.lead, 10, Items.silicon, 15, WHItems.titaniumSteel, 6, Items.metaglass, 10));

                health = 230;
                range = 8;
                transportTime = 2.5f;
                arrowSpacing = 5;
                arrowOffset = 2.5f;
                arrowTimeScl = 12;
                bridgeWidth = 8;
                researchCostMultiplier = 1;
                //钢质带桥
            }
        }};

        steelUnloader = new DirectionalUnloaderF("titanium-steel-unloader") {{

            requirements(Category.distribution, with(Items.lead, 20, Items.silicon, 15, WHItems.titaniumSteel, 30, Items.metaglass, 20));
            size = 1;
            update = true;
            hasItems = true;
            health = 300;
            speed = 1.5f;
            researchCostMultiplier = 1;
            //钢质卸载器
        }};

        T2Bridge = new ItemBridge("low-resistance-bridge-conveyor") {{
            {
                requirements(Category.distribution, with(WHItems.titaniumSteel, 20, WHItems.sealedPromethium, 15, Items.phaseFabric, 15));

                health = 400;
                range = 25;
                transportTime = 1.5f;
                arrowSpacing = 8;
                arrowOffset = 4f;
                arrowTimeScl = 12;
                bridgeWidth = 8;
                consumePower(0.5f);
                researchCostMultiplier = 1;
                //低阻带桥
            }
        }};

        ceramiteConveyor = new StackConveyor("ceramite-conveyor") {{

            requirements(Category.distribution, with(Items.lead, 5, Items.silicon, 4, WHItems.titaniumSteel, 3, WHItems.ceramite, 2));

            health = 550;
            size = 1;
            update = true;
            hasItems = true;
            speed = 0.12f;
            itemCapacity = 35;
            researchCostMultiplier = 1;
            //陶钢打包带
        }};

        trackDriver = new MassDriver("track-driver") {
            {
                requirements(Category.distribution, with(Items.plastanium, 120, WHItems.titaniumSteel, 120, Items.carbide, 80, WHItems.ceramite, 50));

                health = 2800;
                size = 4;
                hasItems = true;
                itemCapacity = 300;
                minDistribute = 60;
                reload = 120;
                rotateSpeed = 2.5f;
                bulletSpeed = 8;
                shootEffect = Fx.instShoot;
                smokeEffect = WHFx.hugeSmokeGray;
                shootSound = Sounds.laser;
                range = 600;
                consumePower(13);
                researchCostMultiplier = 0.6f;
                //轨道驱动器
            }
        };

        T2steamGenerator = new ConsumeGenerator("t2-steam-generator") {
            {
                requirements(Category.power, with(Items.copper, 100, Items.lead, 100, Items.metaglass, 60, Items.silicon, 250, WHItems.titaniumSteel, 150));
                size = 3;
                health = 500;
                hasItems = hasLiquids = true;
                itemDuration = 20f;
                consumeLiquid(Liquids.water, 0.25f);
                itemCapacity = 30;
                liquidCapacity = 150;
                powerProduction = 950 / 60f;
                effectChance = 0.08f;
                drawer = new DrawMulti(
                        new DrawRegion("-bottom"),
                        new DrawLiquidTile(Liquids.water),
                        new DrawBlurSpin("-rotator", 0.6f * 9f),
                        new DrawDefault(),
                        new DrawWarmupRegion());
                generateEffect = Fx.generatespark;
                consume(new ConsumeItemFlammable());
                consume(new ConsumeItemExplode());
                ambientSound = Sounds.smelter;
                ambientSoundVolume = 0.06f;
                researchCostMultiplier = 0.8f;
                //通用发电机
            }
        };

        T2thermalGenerator = new ThermalGenerator("t2-geothermal-generator") {
            {
                requirements(Category.power, with(WHItems.titaniumSteel, 150, Items.silicon, 150, Items.plastanium, 50, Items.tungsten, 50, Items.thorium, 200));

                size = 3;
                health = 660;
                powerProduction = 4.5f;
                generateEffect = Fx.redgeneratespark;
                effectChance = 0.011f;
                drawer = new DrawMulti(
                        new DrawDefault(),
                        new DrawFade()
                );
                researchCostMultiplier = 0.8f;
                //地热发电炉
            }
        };

        T2impactReactor = new ImpactReactor("detonation-reactor") {
            {
                requirements(Category.power, with(Items.lead, 2000, WHItems.titaniumSteel, 1500, Items.tungsten, 800, WHItems.vibranium, 600, WHItems.refineCeramite, 350));

                size = 4;
                health = 10000;
                liquidCapacity = 600;
                itemCapacity = 110;
                hasItems = true;
                hasLiquids = true;
                outputsPower = true;
                powerProduction = 750;
                itemDuration = 1.5f * 60f * 60f;
                drawer = new DrawMulti(
                        new DrawRegion("-bottom"),
                        new DrawPlasma() {{
                            plasma1 = WHPal.SkyBlue;
                            plasma2 = WHPal.SkyBlueF;
                        }},
                        new DrawLiquidRegion(WHLiquids.refinePromethium),
                        new DrawDefault()
                );
                consumePower(15);
                consumeItem(WHItems.sealedPromethium, 50);
                consumeLiquid(WHLiquids.refinePromethium, 10 / 60f);
                ambientSound = Sounds.pulse;
                ambientSoundVolume = 0.1f;
                researchCostMultiplier = 0.8f;
                //爆破放射反应堆
            }
        };


        plaRector = new PlaRector("plasma-reactor") {
            {
                requirements(Category.power, with(Items.silicon, 4000, Items.carbide, 2000, WHItems.titaniumSteel, 4000, WHItems.refineCeramite, 1600, WHItems.adamantium, 800, WHItems.sealedPromethium, 800));

                health = 20000;
                size = 5;
                ambientSound = Sounds.flux;
                ambientSoundVolume = 0.13f;
                effectChance = 0.05f;
                explosionMinWarmup = 0.5f;
                explosionRadius = 100;
                hasItems = true;
                hasLiquids = true;
                itemCapacity = 120;
                liquidCapacity = 1200;
                consumeLiquid(Liquids.cryofluid, 0.5f);
                consumeLiquid(WHLiquids.refinePromethium, 0.5f);
                powerProduction = 2000;
                drawer = new DrawMulti(
                        new DrawRegion("-bottom"),
                        new DrawLiquidTile(WHLiquids.refinePromethium),
                        new DrawSoftParticles() {{
                            alpha = 0.35f;
                            particleRad = 16f;
                            particleSize = 7f;
                            particleLife = 120f;
                            particles = 15;
                            color = WHPal.SkyBlue;
                            color2 = WHPal.SkyBlueF;
                        }},
                        new DrawRegion("-mid"),
                        new DrawDefault(),
                        new DrawHeatInput(),
                        new DrawGlowRegion("-ventglow") {{
                            color = Color.valueOf("32603a");
                        }}
                );
                researchCostMultiplier = 0.6f;
                //等离子反应堆
                //直接利用地核中的等离子体用来发热，不冷却会发生剧烈爆炸
                //[red]地 核 抽 取 机
            }
        };

        promethiunmRector = new NuclearReactor("promethium-reactor") {
            {
                requirements(Category.power, with(Items.lead, 200, Items.silicon, 200, WHItems.titaniumSteel, 110, Items.thorium, 100, Items.tungsten, 50));

                health = 2000;
                size = 3;
                liquidCapacity = 80;
                itemCapacity = 40;
                hasItems = hasLiquids = outputsPower = true;
                powerProduction = 60;
                itemDuration = 600;
                lightColor = WHPal.SkyBlue;
                explosionRadius = 60;
                fuelItem = Items.thorium;
                heating = 0.005f;

                consumeItem(Items.thorium);
                consumeLiquid(Liquids.cryofluid, 6 / 10f).update(false);

                explodeEffect = Fx.reactorExplosion;
                explodeSound = Sounds.explosionbig;
                researchCostMultiplier = 0.8f;
                //钷素反应堆
            }
        };

        Mbattery = new Battery("middle-battery") {

            {
                requirements(Category.power, with(Items.lead, 120, Items.silicon, 110, WHItems.titaniumSteel, 50));
                health = 250;
                size = 2;
                drawer = new DrawMulti(new DrawDefault(), new DrawPower(), new DrawRegion("-top"));
                consumePowerBuffered(40000);
                baseExplosiveness = 2f;
                researchCostMultiplier = 0.8f;
                //小型储能点
                //优化原先大型电池结构，单位面积储存电量更大
            }
        };

        Lbattery = new Battery("large-battery") {
            {
                requirements(Category.power, with(Items.lead, 400, WHItems.titaniumSteel, 400, WHItems.refineCeramite, 50, WHItems.sealedPromethium, 30));
                health = 2900;
                size = 4;
                drawer = new DrawMulti(new DrawDefault(), new DrawPower(), new DrawRegion("-top"));
                consumePowerBuffered(635000);
                baseExplosiveness = 15f;
                researchCostMultiplier = 0.8f;
                //大型储能点
            }
        };

        MK3battery = new ShieldWall("MK3-reinforced-battery") {
            {

                requirements(Category.power, with(Items.silicon, 800, WHItems.titaniumSteel, 400, WHItems.refineCeramite, 400, WHItems.sealedPromethium, 200));

                health = 3800;
                size = 4;
                armor = 18;
                regenSpeed = 15;
                shieldHealth = 2500;
                breakCooldown = 1500;
                conductivePower = true;
                hasPower = true;
                outputsPower = true;
                consumesPower = true;
                canOverdrive = false;
                consumePowerBuffered(900000);
                baseExplosiveness = 10f;
                researchCostMultiplier = 0.8f;
                destroyBullet = WHBullets.warpBreak.copy();
                destroyBullet.hitColor = lightColor = lightningColor = WHPal.pop;
                //MK3强化电池
            }
        };


        TiNode = new PowerNode("titanium-steel-node") {
            {
                requirements(Category.power, with(Items.lead, 40, WHItems.titaniumSteel, 20, Items.silicon, 30));

                health = 400;
                size = 2;
                maxNodes = 20;
                laserRange = 22;
                laserScale = 0.4f;
                laserColor1 = Color.white;
                laserColor2 = WHPal.SkyBlue;
                researchCostMultiplier = 0.8f;
                //钢装甲节点
            }
        };


        T2TiNode = new PowerNode("large-titanium-steel-node") {
            {
                requirements(Category.power, with(WHItems.titaniumSteel, 50, WHItems.ceramite, 20, Items.surgeAlloy, 20));

                health = 850;
                size = 3;
                maxNodes = 5;
                laserRange = 70;
                laserScale = 0.6f;
                laserColor1 = Color.white;
                laserColor2 = WHPal.SkyBlue;


                researchCostMultiplier = 0.8f;
                //装甲电力塔
            }
        };

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
                        drillTime = 700;
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
                        range = 500f;
                        reload = 500f;
                        shootSound = Sounds.laserbig;
                        loopSound = Sounds.beam;
                        loopSoundVolume = 2f;
                        envEnabled |= Env.space;

                        shootType = new DoomLaserBulletType() {{
                            splashDamage = damage = 1000f;
                            splashDamageRadius = 100f;
                            lifetime = 60f * 6f;
                            startOffset = 500f;
                            searchRadius = 300f;
                            circleRadius = 33f;
                            sideLength = 100f;
                            maxMoveRadius = 200;
                            maxSpawnRange = 500;
                            trailLength = 120;
                            width = 30f;
                            oscScl = 2f;
                            oscMag = 0.6f;
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
                            colors = new Color[]{Pal.heal.cpy().a(0.4f), Pal.heal, Color.white};
                            lifetime = 240f;
                            lightColor = lightningColor = Pal.heal;
                            width = hitSize = 25f;
                            length = 300f;
                            hitColor = Pal.heal;
                            drawPositionLighting = false;

                        }};
                        scaledHealth = 200;
                        coolant = consumeCoolant(0.5f);
                        consumePower(17f);
                    }
                };


        sb6 = new

                PowerTurret("laser3") {
                    {
                        requirements(Category.turret, with(Items.copper, 1200, Items.lead, 350, Items.graphite, 300, Items.surgeAlloy, 325, Items.silicon, 325));
                        shootEffect = Fx.shootBigSmoke2;
                        shootCone = 40f;
                        recoil = 4f;
                        size = 4;
                        shake = 2f;
                        range = 350;
                        reload = 180;
                        shootSound = Sounds.laserbig;
                        loopSound = Sounds.beam;
                        loopSoundVolume = 2f;
                        envEnabled |= Env.space;
                        shootType = new BasicBulletType() {
                            {
                                shootEffect = new MultiEffect(Fx.shootTitan, new WaveEffect() {{
                                    colorTo = Pal.sapBulletBack;
                                    sizeTo = 26f;
                                    lifetime = 14f;
                                    strokeFrom = 4f;
                                }});
                                smokeEffect = Fx.shootSmokeTitan;
                                hitColor = Pal.sapBullet;
                                despawnSound = Sounds.spark;

                                sprite = "large-orb";
                                trailEffect = Fx.missileTrail;
                                trailInterval = 3f;
                                trailParam = 4f;
                                speed = 3f;
                                damage = 75f;
                                lifetime = 60f;
                                width = height = 15f;
                                backColor = Pal.sapBulletBack;
                                frontColor = Pal.sapBullet;
                                shrinkX = shrinkY = 0f;
                                trailColor = Pal.sapBulletBack;
                                trailLength = 12;
                                trailWidth = 2.2f;
                                despawnEffect = hitEffect = new ExplosionEffect() {{
                                    waveColor = Pal.sapBullet;
                                    smokeColor = Color.gray;
                                    sparkColor = Pal.sap;
                                    waveStroke = 4f;
                                    waveRad = 40f;
                                }};
                                pierceCap = 3;

                                lightningColor = Pal.sapBullet;
                                lightningDamage = 17;
                                lightning = 8;
                                lightningLength = 2;
                                lightningLengthRand = 8;

                                fragBullets = 1;
                                fragBullet = new LightningChainBulletType() {{
                                    speed = 0f;
                                    damage = 75f;
                                    lifetime = 150f;
                                    width = height = 0;
                                    shrinkX = shrinkY = 0f;
                                    trailColor = Pal.sapBulletBack;
                                    collidesAir = collidesGround = collides = false;
                                    hitColor = chainColor = Pal.sapBullet;

                                    fragBullets = 4;
                                    fragVelocityMax = 1;
                                    fragVelocityMin = 1;
                                    fragBullet = new BasicBulletType(8f, 80) {{
                                        drag = 0.08f;
                                        hitSize = 5;
                                        width = 16f;
                                        lifetime = 150f;
                                        height = 23f;
                                        shootEffect = Fx.shootBig;
                                        pierceCap = 2;
                                        pierceBuilding = true;
                                        knockback = 0.7f;

                                        backColor = hitColor = trailColor = Pal.thoriumAmmoBack;
                                        frontColor = Pal.thoriumAmmoFront;
                                    }};
                                }};
                                scaledHealth = 200;
                                coolant = consumeCoolant(0.5f);
                                consumePower(17f);
                            }
                        };
                    }
                };

        selectProjector = new SelectForceProjector("select-projector") {{
            requirements(Category.effect, with(Items.lead, 100, Items.titanium, 75, Items.silicon, 125));
            size = 4;
            OneTileShieldHealeh = 250f;
            cooldownNormal = 1.5f;
            cooldownLiquid = 1.2f;
            cooldownBrokenBase = 0.5f;
            range = 800f;

            itemConsumer = consumeItem(WHItems.sealedPromethium).boost();
            consumePower(100f);
        }};

        UnitCallBlock airDrop = new UnitCallBlock("air-drop-unit") {
            {
                addSets(
                        new UnitSet(UnitTypes.poly, new byte[]{WHUnitTypes.OTHERS, 2}, 45 * 60f, false,
                                with(Items.lead, 30, Items.copper, 60, Items.graphite, 45, Items.silicon, 30)
                        ),
                        new UnitSet(WHUnitTypes.tank1, new byte[]{WHUnitTypes.AIR_LINE_2, 1}, 15 * 60f, true,
                                with(Items.silicon, 16, Items.copper, 30)
                        ),
                        new UnitSet(WHUnitTypes.tank1s, new byte[]{WHUnitTypes.AIR_LINE_1, 1}, 15 * 60f, true,
                                with(Items.titanium, 30, Items.silicon, 15)
                        ),
                        new UnitSet(WHUnitTypes.air4, new byte[]{WHUnitTypes.AIR_LINE_1, 2}, 30 * 60f, false,
                                with(Items.titanium, 60, Items.silicon, 45, Items.graphite, 30)
                        ),
                        new UnitSet(WHUnitTypes.air5, new byte[]{WHUnitTypes.GROUND_LINE_1, 1}, 20 * 60f, false,
                                with(Items.lead, 15, Items.silicon, 10, Items.copper, 10)
                        ),
                        new UnitSet(UnitTypes.antumbra, new byte[]{WHUnitTypes.GROUND_LINE_1, 2}, 35 * 60f, false,
                                with(Items.lead, 30, Items.titanium, 60, Items.graphite, 45, Items.silicon, 30)
                        ));
            }
        };


        AirRaiderCallBlock test =new AirRaiderCallBlock("tactical-command-center") {{
            requirements(Category.turret, with(WHItems.titaniumSteel, 500, Items.carbide, 200, WHItems.ceramite, 200, WHItems.refineCeramite, 100, WHItems.sealedPromethium, 50));

            size = 4;
        }};


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
    }
};

