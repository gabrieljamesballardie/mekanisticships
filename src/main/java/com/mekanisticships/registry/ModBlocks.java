package com.mekanisticships.registry;

import com.mekanisticships.MekanisticShips;
import com.mekanisticships.block.ThrusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MekanisticShips.MOD_ID);

    public static final RegistryObject<Block> THRUSTER = BLOCKS.register("thruster",
        () -> new ThrusterBlock(BlockBehaviour.Properties.of()
            .strength(3.0f, 6.0f)
            .sound(SoundType.METAL)
            .requiresCorrectToolForDrops()));

    private ModBlocks() {
    }
}
