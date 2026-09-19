package com.frostmatrix.createfix.mixin;

import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import net.minecraft.world.Clearable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TableClothBlockEntity.class, remap = false)
public abstract class TableClothBlockEntityMixin implements Clearable {

    @Unique
    private boolean createfix$cleared = false;

    @Override
    public void clearContent() {
        this.createfix$cleared = true;
    }

    @Inject(method = "destroy", at = @At("HEAD"), cancellable = true)
    private void onDestroy(CallbackInfo ci) {
        if (this.createfix$cleared) {
            ci.cancel();
        }
    }
}