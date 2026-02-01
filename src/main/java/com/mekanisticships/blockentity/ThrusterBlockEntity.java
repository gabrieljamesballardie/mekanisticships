package com.mekanisticships.blockentity;

import com.mekanisticships.block.ThrusterBlock;
import com.mekanisticships.registry.ModBlockEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.valkyrienskies.core.api.ships.LoadedServerShip;
import org.valkyrienskies.eureka.EurekaConfig;
import org.valkyrienskies.eureka.ship.EurekaShipControl;
import org.valkyrienskies.mod.common.VSGameUtilsKt;
import org.jetbrains.annotations.Nullable;

public class ThrusterBlockEntity extends BlockEntity {
    public static final int MAX_FE = 100_000;
    public static final int FE_INPUT_PER_TICK = 1_000;
    public static final int FE_PER_TICK_REQUIRED = 100;
    public static final float THRUST_MULTIPLIER = 5.0f;

    private final ThrusterEnergyStorage energy = new ThrusterEnergyStorage(MAX_FE, FE_INPUT_PER_TICK, 0);
    private final LazyOptional<IEnergyStorage> energyCap = LazyOptional.of(() -> energy);
    private boolean active = false;
    private float currentThrust = 0f;

    public ThrusterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.THRUSTER.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ThrusterBlockEntity blockEntity) {
        if (blockEntity.energy.getEnergyStored() >= FE_PER_TICK_REQUIRED) {
            int consumed = blockEntity.energy.extractEnergy(FE_PER_TICK_REQUIRED, false);
            blockEntity.updateThrust(level, consumed);
            blockEntity.setActive(level, pos, state, true);
            blockEntity.showConsumptionMessage(level);
        } else {
            blockEntity.currentThrust = 0f;
            blockEntity.setActive(level, pos, state, false);
        }
        blockEntity.setChanged();
    }

    private void updateThrust(Level level, int consumed) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        LoadedServerShip ship = VSGameUtilsKt.getLoadedShipManagingPos(serverLevel, worldPosition);
        if (ship == null) {
            currentThrust = 0f;
            return;
        }
        EurekaShipControl control = ship.getAttachment(EurekaShipControl.class);
        if (control == null) {
            currentThrust = 0f;
            return;
        }

        float fraction = Mth.clamp(consumed / (float) FE_PER_TICK_REQUIRED, 0f, 1f);
        float minLinear = EurekaConfig.SERVER.getEnginePowerLinearMin();
        float maxLinear = EurekaConfig.SERVER.getEnginePowerLinear();
        float minAngular = EurekaConfig.SERVER.getEnginePowerAngularMin();
        float maxAngular = EurekaConfig.SERVER.getEnginePowerAngular();

        float linear = Mth.lerp(fraction, minLinear, maxLinear) * THRUST_MULTIPLIER;
        float angular = Mth.lerp(fraction, minAngular, maxAngular) * THRUST_MULTIPLIER;

        currentThrust = linear;
        control.setPowerLinear(control.getPowerLinear() + linear);
        control.setPowerAngular(control.getPowerAngular() + angular);
    }

    private void showConsumptionMessage(Level level) {
        if (level.getGameTime() % 20L != 0L) {
            return;
        }
        Component message = Component.literal("Thruster Consuming " + energy.getEnergyStored() + " FE")
            .withStyle(ChatFormatting.GREEN);
        level.players().stream()
            .filter(player -> player.distanceToSqr(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D) <= 64)
            .forEach(player -> player.displayClientMessage(message, true));
    }

    private void setActive(Level level, BlockPos pos, BlockState state, boolean newActive) {
        if (active == newActive) {
            return;
        }
        active = newActive;
        if (state.getValue(ThrusterBlock.ACTIVE) != newActive) {
            level.setBlock(pos, state.setValue(ThrusterBlock.ACTIVE, newActive), 3);
        }
    }

    public boolean isActive() {
        return active;
    }

    public float getCurrentThrust() {
        return currentThrust;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Energy", energy.getEnergyStored());
        tag.putBoolean("Active", active);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        energy.setEnergy(tag.getInt("Energy"));
        active = tag.getBoolean("Active");
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyCap.invalidate();
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return energyCap.cast();
        }
        return super.getCapability(cap, side);
    }

    private static class ThrusterEnergyStorage extends EnergyStorage {
        public ThrusterEnergyStorage(int capacity, int maxReceive, int maxExtract) {
            super(capacity, maxReceive, maxExtract);
        }

        public void setEnergy(int amount) {
            this.energy = Math.min(amount, getMaxEnergyStored());
        }
    }
}
