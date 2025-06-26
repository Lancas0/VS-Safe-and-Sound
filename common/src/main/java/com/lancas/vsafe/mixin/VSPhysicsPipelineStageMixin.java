package com.lancas.vsafe.mixin;

import com.lancas.vsafe.EzDebug;
import com.lancas.vsafe.config.VSafeConfig;
import com.lancas.vsafe.mixinfriend.ManualRemapPhysPipelineStage;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import org.joml.Matrix3dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.valkyrienskies.core.api.ships.PhysShip;
import org.valkyrienskies.core.api.ships.ShipForcesInducer;
import org.valkyrienskies.core.api.ships.properties.ShipTransform;
import org.valkyrienskies.core.impl.game.ships.PhysShipImpl;
import org.valkyrienskies.core.impl.game.ships.WingManagerImpl;
import org.valkyrienskies.core.impl.game.ships.WingPhysicsSolver;
import org.valkyrienskies.core.impl.shadow.Aj;
import org.valkyrienskies.core.impl.shadow.Ao;
import org.valkyrienskies.core.impl.shadow.Aq;
import org.valkyrienskies.physics_api.PoseVel;

@Mixin(Aq.class)
public abstract class VSPhysicsPipelineStageMixin implements ManualRemapPhysPipelineStage {
    //@Unique private AtomicBoolean handlingPhysFrame = new AtomicBoolean(false);

    @Shadow(remap = false) @Final public abstract Ao a(Vector3dc vector3dc, double d2, boolean z2);
    @Shadow(remap = false) @Final protected abstract void b(Aj aj);
    @Shadow(remap = false) @Final protected abstract Ao h();
    @Shadow(remap = false) @Final protected abstract Object a(Vector3dc v, double d2, boolean z2, Continuation<? super Unit> continuation);

    @Unique private static boolean isSafePhysThread() {
        return VSafeConfig.get().safePhysThreadOn;
    }

    @Redirect(
        method = "a(Lorg/joml/Vector3dc;DZ)Lorg/valkyrienskies/core/impl/shadow/Ao;",
        at = @At(
            value = "INVOKE",
            target = "Lorg/valkyrienskies/core/impl/game/ships/PhysShipImpl;setPoseVel(Lorg/valkyrienskies/physics_api/PoseVel;)V"
        ),
        remap = false
    )
    private void safeSetPoseVel(PhysShipImpl ship, PoseVel newPoseVel) {
        if (!isSafePhysThread()) {
            ship.setPoseVel(newPoseVel);
        } else {
            try {
                if (newPoseVel.component1().isFinite() && newPoseVel.component2().isFinite() && newPoseVel.component3().isFinite() && newPoseVel.component4().isFinite()) {
                    ship.setPoseVel(newPoseVel);
                } else {
                    EzDebug.warn("Skip assign invalid PoseVel：" + newPoseVel);
                }
            } catch (Exception e) {
                EzDebug.warn("During setPoseVel catch exception:" + e.toString());
            }
        }
    }


    @Redirect(
        method = "a(Lorg/joml/Vector3dc;DZ)Lorg/valkyrienskies/core/impl/shadow/Ao;",
        at = @At(
            value = "INVOKE",
            target = "Lorg/valkyrienskies/core/impl/shadow/Aq;b(Lorg/valkyrienskies/core/impl/shadow/Aj;)V"
        ),
        remap = false
    )
    private void safeInvokeB(Aq instance, Aj remove) {
        if (!isSafePhysThread()) {
            b(remove);
        } else {
            try {
                b(remove);
            } catch (Exception e) {
                EzDebug.warn("During b(remove) catch exception:" + e.toString());
            }
        }
    }


    @Redirect(
        method = "a(Lorg/joml/Vector3dc;DZ)Lorg/valkyrienskies/core/impl/shadow/Ao;",
        at = @At(
            value = "INVOKE",
            target = "Lorg/valkyrienskies/core/impl/game/ships/PhysShipImpl;applyInvariantForce(Lorg/joml/Vector3dc;)V"
        ),
        remap = false
    )
    private void safeApplyForce(PhysShipImpl instance, Vector3dc force) {
        if (!isSafePhysThread())
            instance.applyInvariantForce(force);
        else {
            try {
                instance.applyInvariantForce(force);
            } catch (Exception e) {
                EzDebug.warn("During applying force catch exception:" + e.toString());
            }
        }
    }

    @Redirect(
        method = "a(Lorg/joml/Vector3dc;DZ)Lorg/valkyrienskies/core/impl/shadow/Ao;",
        at = @At(
            value = "INVOKE",
            target = "Lorg/valkyrienskies/core/impl/game/ships/PhysShipImpl;applyInvariantTorque(Lorg/joml/Vector3dc;)V"
        ),
        remap = false
    )
    private void safeApplyTorque(PhysShipImpl instance, Vector3dc torque) {
        if (!isSafePhysThread())
            instance.applyInvariantTorque(torque);
        else {
            try {
                instance.applyInvariantTorque(torque);
            } catch (Exception e) {
                EzDebug.warn("During applying torque catch exception:" + e.toString());
            }
        }
    }

    @Redirect(
        method = "a(Lorg/joml/Vector3dc;DZ)Lorg/valkyrienskies/core/impl/shadow/Ao;",
        at = @At(
            value = "INVOKE",
            target = "Lorg/valkyrienskies/core/impl/game/ships/PhysShipImpl;applyQueuedForces()V"
        ),
        remap = false
    )
    private void safeApplyQueuedForces(PhysShipImpl instance) {
        if (!isSafePhysThread())
            instance.applyQueuedForces();
        else {
            try {
                instance.applyQueuedForces();
            } catch (Exception e) {
                EzDebug.warn("During applying queued forces catch exception:" + e.toString());
            }
        }
    }

    @Redirect(
        method = "a(Lorg/joml/Vector3dc;DZ)Lorg/valkyrienskies/core/impl/shadow/Ao;",
        at = @At(
            value = "INVOKE",
            target = "Lorg/valkyrienskies/core/impl/game/ships/WingPhysicsSolver;applyWingForces(Lorg/valkyrienskies/core/api/ships/properties/ShipTransform;Lorg/valkyrienskies/physics_api/PoseVel;Lorg/valkyrienskies/core/impl/game/ships/WingManagerImpl;Lorg/joml/Matrix3dc;)Lkotlin/Pair;"
        ),
        remap = false
    )
    private Pair<Vector3dc, Vector3dc> safeApplyWingForces(WingPhysicsSolver instance, ShipTransform shipTransform, PoseVel poseVel, WingManagerImpl wingManager, Matrix3dc momentOfInertia) {
        if (!isSafePhysThread())
            return instance.applyWingForces(shipTransform, poseVel, wingManager, momentOfInertia);
        else {
            try {
                return instance.applyWingForces(shipTransform, poseVel, wingManager, momentOfInertia);
            } catch (Exception e) {
                EzDebug.warn("During applying wing forces catch exception:" + e.toString());
                return new Pair<>(new Vector3d(), new Vector3d()); // 返回0向量对，防止物理线程崩
            }
        }
    }

    @Redirect(
        method = "a(Lorg/joml/Vector3dc;DZ)Lorg/valkyrienskies/core/impl/shadow/Ao;",
        at = @At(
            value = "INVOKE",
            target = "Lorg/valkyrienskies/core/api/ships/ShipForcesInducer;applyForces(Lorg/valkyrienskies/core/api/ships/PhysShip;)V"
        ),
        remap = false
    )
    private void safeApplyForces(ShipForcesInducer inducer, PhysShip ship) {
        if (!isSafePhysThread())
            inducer.applyForces(ship);
        else {
            try {
                inducer.applyForces(ship);
            } catch (Exception e) {
                EzDebug.warn("During applying ship forces catch exception:" + e.toString());
            }
        }
    }

    @Redirect(
        method = "a(Lorg/joml/Vector3dc;DZ)Lorg/valkyrienskies/core/impl/shadow/Ao;",
        at = @At(
            value = "INVOKE",
            target = "Lorg/valkyrienskies/core/api/ships/ShipForcesInducer;applyForcesAndLookupPhysShips(Lorg/valkyrienskies/core/api/ships/PhysShip;Lkotlin/jvm/functions/Function1;)V"
        ),
        remap = false
    )
    private void safeApplyForcesAndLookup(ShipForcesInducer inducer, PhysShip ship, Function1<Long, PhysShip> lookup) {
        if (!isSafePhysThread())
            inducer.applyForcesAndLookupPhysShips(ship, lookup);
        else {
            try {
                inducer.applyForcesAndLookupPhysShips(ship, lookup);
            } catch (Exception e) {
                EzDebug.warn("During applying forces and lookup catch exception:" + e.toString());
            }
        }
    }

}
