package com.lancas.vsafe.valkyrien;


import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.valkyrienskies.core.api.ships.PhysShip;
import org.valkyrienskies.core.api.ships.ShipForcesInducer;

public class ExceptionalForceInducer implements ShipForcesInducer {
    public ExceptionalForceInducer() {}

    @Override
    public void applyForces(@NotNull PhysShip physShip) {
        physShip.applyInvariantForce(new Vector3d(Double.NaN, Double.NaN, Double.NaN));
    }
}