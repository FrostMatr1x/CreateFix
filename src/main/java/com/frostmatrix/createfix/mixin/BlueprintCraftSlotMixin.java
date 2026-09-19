package com.frostmatrix.createfix.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.simibubi.create.content.logistics.filter.FilterItem;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

@Mixin(targets = "com.simibubi.create.content.equipment.blueprint.BlueprintMenu$BlueprintCraftSlot", remap = false)
public abstract class BlueprintCraftSlotMixin extends SlotItemHandler {

    public BlueprintCraftSlotMixin(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if (stack.getItem() instanceof FilterItem) {
            return false;
        }
        return super.mayPlace(stack);
    }

    @Override
    public void set(ItemStack stack) {
        if (stack.getItem() instanceof FilterItem) {
            return;
        }
        super.set(stack);
    }
}