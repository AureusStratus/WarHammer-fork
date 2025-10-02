package wh.entities.abilities;

import arc.math.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;
import mindustry.world.meta.*;
import wh.content.*;

public class AccumulateAccelerate extends Ability{
    public float reloadMultiplier = 1f;
    public float maxMultiplier = 5f;
    public float maintainTime = 120;

    public float increasePerTick = 0.2f / 60f;
    public float decreasePerTick = 2f / 60f;

    protected float timer;

    @Override
    public void update(Unit unit){
        super.update(unit);

        float increment = increasePerTick * Time.delta;
        if(reloadMultiplier < maxMultiplier && !unit.isShooting){
            increment = -decreasePerTick * Time.delta;
        }else if(reloadMultiplier >= maxMultiplier && !unit.isShooting){
            timer += Time.delta;
        }

        if(timer >= maintainTime){
            reloadMultiplier = Mathf.approachDelta(reloadMultiplier, 1, 0.08f);
            if(reloadMultiplier==1) timer = 0;
        }

        reloadMultiplier = Mathf.clamp(reloadMultiplier + increment, 1, maxMultiplier);
        unit.reloadMultiplier *= reloadMultiplier;
    }

    @Override
    public void addStats(Table t){
        t.add("[lightgray]" + WHStats.increaseWhenShooting.localized() + ": [white]+" + Strings.autoFixed(increasePerTick * 60 * 100, 0) + "%" + StatUnit.perSecond.localized());
        t.row();
        t.add("[lightgray]" + WHStats.decreaseNotShooting.localized() + ": [white]-" + Strings.autoFixed(decreasePerTick * 60 * 100, 0) + "%" + StatUnit.perSecond.localized());
        t.row();
        t.add("[lightgray]" + WHStats.maxBoostPercent.localized() + ": [white]" + Strings.autoFixed(maxMultiplier * 100, 0) + "%");
        t.row();
        t.add("[lightgray]" + new Stat("wh-maintain-time", StatCat.function).localized() + ": [white]" + Strings.autoFixed(maintainTime/60f, 0) + "%");
    }
}
