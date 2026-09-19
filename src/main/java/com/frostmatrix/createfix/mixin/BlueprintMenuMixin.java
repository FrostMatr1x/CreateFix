package com.frostmatrix.createfix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.content.equipment.blueprint.BlueprintMenu;
import com.simibubi.create.content.logistics.filter.FilterItem;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

@Mixin(value = BlueprintMenu.class, remap = false)
public abstract class BlueprintMenuMixin extends AbstractContainerMenu {

    protected BlueprintMenuMixin(MenuType<?> type, int id) {
        super(type, id);
    }

    @Redirect(
        method = "addSlots",
        at = @At(
            value = "NEW",
            target = "net/neoforged/neoforge/items/SlotItemHandler"
        ),
        remap = false
    )
    private SlotItemHandler create$redirectSlotItemHandler(IItemHandler itemHandler, int index, int x, int y) {
        return new SlotItemHandler(itemHandler, index, x, y) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return !(stack.getItem() instanceof FilterItem) && super.mayPlace(stack);
            }

            @Override
            public void set(ItemStack stack) {
                if (stack.getItem() instanceof FilterItem) {
                    return;
                }
                super.set(stack);
            }
        };
    }

    @Inject(method = "addSlots", at = @At("TAIL"), remap = false)
    private void create$clearFiltersOnInit(CallbackInfo ci) {
        ItemStackHandler ghostInventory = ((GhostItemMenuAccessor) this).getGhostInventory();
        if (ghostInventory != null) {
            for (int i = 0; i < ghostInventory.getSlots(); i++) {
                if (ghostInventory.getStackInSlot(i).getItem() instanceof FilterItem) {
                    ghostInventory.setStackInSlot(i, ItemStack.EMPTY);
                }
            }
        }
    }

    @Inject(method = "onCraftMatrixChanged", at = @At("HEAD"), remap = false)
    private void create$clearFiltersOnMatrixChanged(CallbackInfo ci) {
        ItemStackHandler ghostInventory = ((GhostItemMenuAccessor) this).getGhostInventory();
        if (ghostInventory != null) {
            for (int i = 0; i < ghostInventory.getSlots(); i++) {
                if (ghostInventory.getStackInSlot(i).getItem() instanceof FilterItem) {
                    ghostInventory.setStackInSlot(i, ItemStack.EMPTY);
                }
            }
        }
    }
}