package com.mekanisticships.registry;

import com.mekanisticships.MekanisticShips;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MekanisticShips.MOD_ID);

    public static final RegistryObject<Item> FE_ENGINE_ITEM = ITEMS.register("fe_engine", () ->
        new BlockItem(ModBlocks.FE_ENGINE_BLOCK.get(), new Item.Properties())
    );
}
