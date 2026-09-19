package com.frostmatrix.createfix.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;


@Mixin(targets = "com.simibubi.create.foundation.collision.ContinuousOBBCollider", remap = false)
public class ContinuousOBBColliderMixin {

    @WrapOperation(
        method = "collideMany",
        at = @At(
            value = "FIELD",
            target = "Lcom/simibubi/create/foundation/collision/ContinuousOBBCollider$ContinuousSeparationManifold;axis:Lnet/minecraft/world/phys/Vec3;",
            opcode = Opcodes.GETFIELD
        ),
        remap = false
    )
    private static Vec3 createcollisionfix$guardAxis(@Coerce Object manifold, Operation<Vec3> original) {
        Vec3 value = original.call(manifold);
        return value == null ? Vec3.ZERO : value;
    }

    @WrapOperation(
        method = "collideMany",
        at = @At(
            value = "FIELD",
            target = "Lcom/simibubi/create/foundation/collision/ContinuousOBBCollider$ContinuousSeparationManifold;normalAxis:Lnet/minecraft/world/phys/Vec3;",
            opcode = Opcodes.GETFIELD
        ),
        remap = false
    )
    private static Vec3 createcollisionfix$guardNormalAxis(@Coerce Object manifold, Operation<Vec3> original) {
        Vec3 value = original.call(manifold);
        return value == null ? Vec3.ZERO : value;
    }
}