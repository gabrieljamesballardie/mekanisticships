package com.mekanisticships;

import com.mekanisticships.registry.ModBlocks;
import com.mekanisticships.registry.ModBlockEntities;
import com.mekanisticships.registry.ModCreativeTabs;
import com.mekanisticships.registry.ModItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MekanisticShips.MOD_ID)
public class MekanisticShips {
    public static final String MOD_ID = "mekanisticships";

    public MekanisticShips() {
        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlockEntities.BLOCK_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModCreativeTabs.CREATIVE_TABS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
