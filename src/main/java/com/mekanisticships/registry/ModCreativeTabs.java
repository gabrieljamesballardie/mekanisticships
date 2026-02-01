package com.mekanisticships.registry;

import com.mekanisticships.MekanisticShips;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MekanisticShips.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAIN = CREATIVE_TABS.register("mekanisticships", () ->
        CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.mekanisticships"))
            .icon(() -> new ItemStack(ModItems.THRUSTER.get()))
            .displayItems((parameters, output) -> output.accept(ModItems.THRUSTER.get()))
            .build());

    private ModCreativeTabs() {
    }
}
