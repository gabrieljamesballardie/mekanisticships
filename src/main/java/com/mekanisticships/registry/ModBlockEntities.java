package com.mekanisticships.registry;

import com.mekanisticships.MekanisticShips;
import com.mekanisticships.blockentity.ThrusterBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
        DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MekanisticShips.MOD_ID);

    public static final RegistryObject<BlockEntityType<ThrusterBlockEntity>> THRUSTER = BLOCK_ENTITIES.register(
        "thruster",
        () -> BlockEntityType.Builder.of(ThrusterBlockEntity::new, ModBlocks.THRUSTER.get()).build(null)
    );

    private ModBlockEntities() {
    }
}
