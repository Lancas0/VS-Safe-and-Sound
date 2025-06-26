package com.lancas.vsafe.mixinfriend;

import com.lancas.vsafe.mixin.PhysItemAccessor;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import org.valkyrienskies.core.impl.game.ships.PhysShipImpl;
import org.valkyrienskies.core.impl.shadow.Aj;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface ManualRemapPhysPipelineStage {
    public default PhysItemAccessor getAccessor() {
        return (PhysItemAccessor)this;
    }

    //NOT SURE
    public default ConcurrentLinkedQueue<Aj> getPhysFrames() { return getAccessor().getC(); }

    public default Map<String, Long2ObjectMap<PhysShipImpl>> getMaybeEachLevelShips() { return getAccessor().getH(); }
}
