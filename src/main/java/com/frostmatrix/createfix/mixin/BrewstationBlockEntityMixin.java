package com.frostmatrix.createfix.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.brewery.core.block.entity.BrewstationBlockEntity;
import net.satisfy.brewery.core.event.brew_event.BrewHelper;
import net.satisfy.brewery.core.registry.BlockStateRegistry;
import net.satisfy.brewery.core.registry.ObjectRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo 
@Mixin(targets = "net.satisfy.brewery.core.block.entity.BrewstationBlockEntity", remap = false)
public abstract class BrewstationBlockEntityMixin {

    @Inject(
        method = "canBrew", 
        at = @At("HEAD"), 
        cancellable = true, 
        remap = false
    )
    private void createfix$safeguardCanBrew(Recipe<?> recipe, CallbackInfoReturnable<Boolean> cir) {
        BrewstationBlockEntity self = (BrewstationBlockEntity) (Object) this;

        if (self.getLevel() == null || self.getBlockPos() == null) {
            cir.setReturnValue(false);
            return;
        }

        BlockState state = self.getLevel().getBlockState(self.getBlockPos());
        if (!state.hasProperty(BlockStateRegistry.MATERIAL) || !state.hasProperty(BlockStateRegistry.LIQUID)) {
            cir.setReturnValue(false);
            return;
        }

        BlockPos ovenPos = BrewHelper.getBlock(ObjectRegistry.BREW_OVEN.get(), self.getComponents(), self.getLevel());
        if (ovenPos == null) {
            cir.setReturnValue(false);
            return;
        }

        BlockState ovenState = self.getLevel().getBlockState(ovenPos);
        if (!ovenState.hasProperty(BlockStateRegistry.HEAT)) {
            cir.setReturnValue(false);
        }
    }
}