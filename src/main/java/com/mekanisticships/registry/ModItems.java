package com.mekanisticships.registry;

import com.mekanisticships.MekanisticShips;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MekanisticShips.MOD_ID);

    public static final RegistryObject<Item> THRUSTER = ITEMS.register("thruster",
        () -> new BlockItem(ModBlocks.THRUSTER.get(), new Item.Properties()));

    private ModItems() {
    }
}
