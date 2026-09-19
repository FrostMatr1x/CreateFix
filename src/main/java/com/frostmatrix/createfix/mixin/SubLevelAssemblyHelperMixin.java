package com.frostmatrix.createfix.mixin;

import com.frostmatrix.createfix.AssemblyState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "dev.ryanhcode.sable.api.SubLevelAssemblyHelper", remap = false)
public class SubLevelAssemblyHelperMixin {

    @Inject(method = "moveBlocks", at = @At("HEAD"))
    private static void onMoveBlocksStart(CallbackInfo ci) {
        AssemblyState.setAssembling(true);
    }

    @Inject(method = "moveBlocks", at = @At("RETURN"))
    private static void onMoveBlocksEnd(CallbackInfo ci) {
        AssemblyState.setAssembling(false);
    }
}