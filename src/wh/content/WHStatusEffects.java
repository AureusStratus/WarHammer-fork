package wh.content;

import mindustry.graphics.*;
import mindustry.type.*;
import wh.gen.PlasmaFire;
import wh.graphics.WHPal;

public final class WHStatusEffects {
    public static StatusEffect ultFireBurn,PlasmaFireBurn, assault, bless, distort, energyAmplification, forcesOfChaos, melta, palsy, plasma;

    private WHStatusEffects() {}

    public static void load() {
        ultFireBurn = new StatusEffect("ult-fire-burn") {{
            color = Pal.techBlue;
            damage = 15f;
            speedMultiplier = 1.2f;
            effect = WHFx.ultFireBurn;
        }};
        PlasmaFireBurn = new StatusEffect("plasma") {{
            color = WHPal.SkyBlue;
            effect = WHFx.PlasmaFireBurn;
            }};


        assault = new StatusEffect("assault");
        bless = new StatusEffect("bless");
        distort = new StatusEffect("distort");
        energyAmplification = new StatusEffect("energy-amplification");
        forcesOfChaos = new StatusEffect("forces-of-chaos");
        melta = new StatusEffect("melta");
        palsy = new StatusEffect("palsy");
        //plasma = new StatusEffect("plasma");
    }
}
