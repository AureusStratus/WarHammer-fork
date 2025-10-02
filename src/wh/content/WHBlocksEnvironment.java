package wh.content;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.environment.*;
import wh.entities.world.blocks.*;

import static mindustry.Vars.*;

public class WHBlocksEnvironment{
    public static Block road;

    public static void load(){
        road = new Road("road-autotile");
    }
}
