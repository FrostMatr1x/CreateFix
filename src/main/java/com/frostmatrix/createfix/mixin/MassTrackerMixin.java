package com.frostmatrix.createfix.mixin;

import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.ryanhcode.sable.api.physics.mass.MassTracker;

@Mixin(value = MassTracker.class, remap = false)
public class MassTrackerMixin {

    @Inject(method = "getCenterOfMass", at = @At("RETURN"), cancellable = true)
    private void createfix$fixNullCenterOfMass(CallbackInfoReturnable<Vector3dc> cir) {
        if (cir.getReturnValue() == null) {
            boolean shouldPatch = false;

            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            
            for (StackTraceElement element : stackTrace) {
                String methodName = element.getMethodName();

                if (methodName.equals("updateContraptionPose") || methodName.contains("buildProperties")) {
                    shouldPatch = true;
                    break;
                }
            }

            if (shouldPatch) {
                cir.setReturnValue(new Vector3d(0, 0, 0));
            }
        }
    }
}