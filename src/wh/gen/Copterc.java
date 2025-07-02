package wh.gen;

import mindustry.gen.*;
import wh.entities.world.unit.CopterUnitType.*;

public interface Copterc extends Unitc {
    RotorMount[] rotors();

    float rotorSpeedScl();

    void rotors(RotorMount[] value);

    void rotorSpeedScl(float value);
}
