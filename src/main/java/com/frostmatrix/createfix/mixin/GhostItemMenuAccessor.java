package com.frostmatrix.createfix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.simibubi.create.foundation.gui.menu.GhostItemMenu;

import net.neoforged.neoforge.items.ItemStackHandler;

@Mixin(value = GhostItemMenu.class, remap = false)
public interface GhostItemMenuAccessor {
    
    @Accessor("ghostInventory")
    ItemStackHandler getGhostInventory();
}