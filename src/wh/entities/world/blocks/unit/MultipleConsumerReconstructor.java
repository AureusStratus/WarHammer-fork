package wh.entities.world.blocks.unit;

import arc.*;
import arc.math.*;
import arc.struct.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.game.EventType.*;
import mindustry.type.*;
import mindustry.world.blocks.units.*;

import static mindustry.Vars.state;

public class MultipleConsumerReconstructor extends Reconstructor{
    public ObjectMap<UnitType, ItemStack[]> upgradeCosts = new ObjectMap<>();

    public MultipleConsumerReconstructor(String name){
        super(name);
    }
    public void addUpgrade(UnitType from, UnitType to, ItemStack... costs){
        upgrades.add(new UnitType[]{from, to});
        upgradeCosts.put(from, costs);

        for(ItemStack stack : costs){
            capacities[stack.item.id] = Math.max(capacities[stack.item.id], stack.amount * 2);
            itemCapacity = Math.max(itemCapacity, stack.amount * 2);
        }
    }

    public class MultipleConsumerReconstructorBuild extends ReconstructorBuild{
        boolean constructing;

        @Override
        public boolean constructing(){
            return payload != null && hasUpgrade(payload.unit.type);
        }
        @Override
        public boolean shouldConsume(){
            if(!constructing || !enabled) return false;

            ItemStack[] costs = upgradeCosts.get(payload.unit.type);
            if(costs == null) return false;

            for(ItemStack stack : costs){
                if(items.get(stack.item) < stack.amount * edelta()){
                    return false;
                }
            }
            return true;
        }

        @Override
        public void updateTile(){
            //cache value to prevent repeated calls and multithreading issues
            constructing = constructing();
            boolean valid = false;

            if(payload != null){
                //check if offloading
                if(!hasUpgrade(payload.unit.type)){
                    moveOutPayload();
                }else{ //update progress
                    if(moveInPayload()){
                        if(efficiency > 0){
                            valid = true;
                            progress += edelta() * state.rules.unitBuildSpeed(team);
                        }

                        //upgrade the unit
                        if(progress >= constructTime){
                            payload.unit = upgrade(payload.unit.type).create(payload.unit.team());

                            if(payload.unit.isCommandable()){
                                if(commandPos != null){
                                    payload.unit.command().commandPosition(commandPos);
                                }
                                //this already checks if it is a valid command for the unit type
                                payload.unit.command().command(command == null && payload.unit.type.defaultCommand != null ? payload.unit.type.defaultCommand : command);
                            }

                            progress %= 1f;
                            Effect.shake(2f, 3f, this);
                            Fx.producesmoke.at(this);
                            consume();
                            Events.fire(new UnitCreateEvent(payload.unit, this));
                        }
                    }
                }
            }

            speedScl = Mathf.lerpDelta(speedScl, Mathf.num(valid), 0.05f);
            time += edelta() * speedScl * state.rules.unitBuildSpeed(team);
        }

        @Override
        public boolean hasUpgrade(UnitType type){
            return super.hasUpgrade(type);
        }
    }
}
