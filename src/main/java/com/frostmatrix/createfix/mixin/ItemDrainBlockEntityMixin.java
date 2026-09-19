package com.frostmatrix.createfix.mixin;

import com.simibubi.create.content.fluids.drain.ItemDrainBlockEntity;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import net.minecraft.world.Clearable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ItemDrainBlockEntity.class, remap = false)
public abstract class ItemDrainBlockEntityMixin implements Clearable {

    @Shadow
    public TransportedItemStack heldItem;

    @Override
    public void clearContent() {
        this.heldItem = null;
    }
}