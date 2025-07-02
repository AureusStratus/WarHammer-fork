//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package wh.core;

import mindustry.mod.ClassMap;
import wh.entities.abilities.AdaptedHealAbility;
import wh.entities.abilities.MendFieldAbility;
import wh.entities.abilities.PointDefenseAbility;
import wh.entities.abilities.ShockWaveAbility;
import wh.entities.bullet.AccelBulletType;
import wh.entities.bullet.BlackHoleBulletType;
import wh.entities.bullet.BoidBulletType;
import wh.entities.bullet.ChainLightingBulletType;
import wh.entities.bullet.EffectBulletType;
import wh.entities.bullet.LightningLinkerBulletType;
import wh.entities.bullet.PositionLightningBulletType;
import wh.entities.bullet.ShieldBreakerType;
import wh.entities.bullet.StrafeLaserBulletType;
import wh.entities.bullet.TrailFadeBulletType;
import wh.entities.effect.WrapperEffect;
import wh.entities.world.type.ExtraSectorPreset;
import wh.entities.world.unit.AncientUnitType;
import wh.entities.world.unit.NucleoidUnitType;
import wh.entities.world.unit.PesterUnitType;
import wh.entities.world.blocks.defense.AirRaider;
import wh.entities.world.blocks.defense.BombLauncher;
import wh.entities.world.blocks.defense.turrets.SpeedupTurret;
import wh.entities.world.blocks.distribution.DirectionalUnloaderF;
import wh.entities.world.blocks.distribution.TubeConveyor;
import wh.entities.world.blocks.storage.FrontlineCoreBlock;
import wh.entities.world.blocks.storage.UnloaderF;

final class WHClassMap {

    private WHClassMap() {
    }

    static void load() {
        ClassMap.classes.put("AdaptedHealAbility", AdaptedHealAbility.class);
        ClassMap.classes.put("MendFieldAbility", MendFieldAbility.class);
        ClassMap.classes.put("PointDefenseAbility", PointDefenseAbility.class);
        ClassMap.classes.put("ShockWaveAbility", ShockWaveAbility.class);
        ClassMap.classes.put("WrapperEffect", WrapperEffect.class);
        ClassMap.classes.put("AccelBulletType", AccelBulletType.class);
        ClassMap.classes.put("BoidBulletType", BoidBulletType.class);
        ClassMap.classes.put("EffectBulletType", EffectBulletType.class);
        ClassMap.classes.put("BlackHoleBulletType", BlackHoleBulletType.class);
        ClassMap.classes.put("LightningLinkerBulletType", LightningLinkerBulletType.class);
        ClassMap.classes.put("ChainLightingBulletType", ChainLightingBulletType.class);
        ClassMap.classes.put("PositionLightningBulletType", PositionLightningBulletType.class);
        ClassMap.classes.put("ShieldBreakerType", ShieldBreakerType.class);
        ClassMap.classes.put("StrafeLaserBulletType", StrafeLaserBulletType.class);
        ClassMap.classes.put("TrailFadeBulletType", TrailFadeBulletType.class);
        ClassMap.classes.put("ExtraSectorPreset", ExtraSectorPreset.class);
        ClassMap.classes.put("AncientUnitType", AncientUnitType.class);
        ClassMap.classes.put("AncientEngine", AncientUnitType.AncientEngine.class);
        ClassMap.classes.put("NucleoidUnitType", NucleoidUnitType.class);
        ClassMap.classes.put("PesterUnitType", PesterUnitType.class);
        ClassMap.classes.put("AirRaider", AirRaider.class);
        ClassMap.classes.put("AirRaiderBuild", AirRaider.AirRaiderBuild.class);
        ClassMap.classes.put("BombLauncher", BombLauncher.class);
        ClassMap.classes.put("BombLauncherBuild", BombLauncher.BombLauncherBuild.class);
        ClassMap.classes.put("SpeedupTurret", SpeedupTurret.class);
        ClassMap.classes.put("SpeedupTurretBuild", SpeedupTurret.SpeedupTurretBuild.class);
        ClassMap.classes.put("TubeConveyor", TubeConveyor.class);
        ClassMap.classes.put("TubeConveyorBuild", TubeConveyor.TubeConveyorBuild.class);
        ClassMap.classes.put("UnloaderF", UnloaderF.class);
        ClassMap.classes.put("UnloaderBuildF", UnloaderF.UnloaderBuildF.class);

        ClassMap.classes.put("DirectionalUnloaderF", DirectionalUnloaderF.class);
        ClassMap.classes.put("DirectionalUnloaderBuildF", DirectionalUnloaderF.DirectionalUnloaderBuildF.class);
        ClassMap.classes.put("FrontlineCoreBlock", FrontlineCoreBlock.class);
        ClassMap.classes.put("FrontlineCoreBuild", FrontlineCoreBlock.FrontlineCoreBuild.class);
    }
}
