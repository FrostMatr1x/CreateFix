package com.frostmatrix.createfix;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static final TagKey<Item> DEPLOYER_BLACKLIST =
        TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("createfix", "deployer_blacklist"));
}
