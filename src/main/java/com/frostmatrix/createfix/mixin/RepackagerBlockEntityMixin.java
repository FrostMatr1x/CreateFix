package com.frostmatrix.createfix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.packager.repackager.RepackagerBlockEntity;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

@Mixin(value = RepackagerBlockEntity.class, remap = false)
public class RepackagerBlockEntityMixin {

    @SuppressWarnings("unchecked")
    @Redirect(
        method = "attemptToRepackage(Lnet/neoforged/neoforge/items/IItemHandler;)V",
        at = @At(
            value = "INVOKE",
            target = "Lcom/simibubi/create/content/logistics/box/PackageItem;isPackage(Lnet/minecraft/world/item/ItemStack;)Z"
        )
    )
    private boolean redirectIsPackage(ItemStack stack) {
        if (!PackageItem.isPackage(stack)) {
            return false;
        }

        DataComponentType<?> packageOrderDataType = BuiltInRegistries.DATA_COMPONENT_TYPE.get(
            ResourceLocation.parse("create:package_order_data")
        );

        if (packageOrderDataType != null && stack.has(packageOrderDataType)) {
            Object value = stack.get(packageOrderDataType);
            if (value != null) {
                try {
                    BlockEntity be = (BlockEntity) (Object) this;
                    if (be.getLevel() != null) {
                        RegistryOps<Tag> ops = be.getLevel().registryAccess().createSerializationContext(NbtOps.INSTANCE);
                        
                        Tag serialized = ((DataComponentType<Object>) packageOrderDataType).codecOrThrow()
                            .encodeStart(ops, value)
                            .getOrThrow(IllegalStateException::new);

                        if (serialized instanceof CompoundTag tag) {
                            if (tag.contains("order_context", Tag.TAG_COMPOUND)) {
                                CompoundTag orderContext = tag.getCompound("order_context");
                                if (orderContext.contains("ordered_crafts", Tag.TAG_LIST)) {
                                    if (!orderContext.getList("ordered_crafts", Tag.TAG_COMPOUND).isEmpty()) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }
        }

        return true;
    }
}