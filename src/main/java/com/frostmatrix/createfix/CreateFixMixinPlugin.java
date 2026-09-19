package com.frostmatrix.createfix;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import net.neoforged.fml.loading.FMLLoader;

import java.util.List;
import java.util.Set;

public class CreateFixMixinPlugin implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {}

    @Override
    public String getRefMapperConfig() { return null; }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        String[] path = mixinClassName.split("\\.");
        String name = path[path.length - 1];

        if (name.equals("BrewstationBlockEntityMixin")) {
            return FMLLoader.getLoadingModList().getModFileById("brewery") != null &&
                FMLLoader.getLoadingModList().getModFileById("sable") != null;
        }
        if (isSableMixin(name)) {
            return FMLLoader.getLoadingModList().getModFileById("sable") != null;
        }
        return true;
    }

    private boolean isSableMixin(String name)
    {
        return switch (name) {
            case "SubLevelAssemblyHelperMixin",
                "MassTrackerMixin",
                "BlockDropMixin",
                "ItemDrainBlockEntityMixin" -> true;
        
            default -> false;
        };
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() { return null; }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}