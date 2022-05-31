package com.github.blacksabin.orphic.blocks;

import com.github.blacksabin.orphic.OrphicInit;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class BlockEntityDeepSentinel extends BlockEntity {
    private int timer = 0;
    private float power = 4.0f;
    private int tickRate = 20;
    private boolean active = true;
    @Nullable
    private LivingEntity targetEntity;
    @Nullable
    private UUID targetUuid;
    private long nextAmbientSoundTime;

    public BlockEntityDeepSentinel(BlockPos pos, BlockState state) {
        super(OrphicInit.BLOCK_ENTITY_DEEP_SENTINEL, pos, state);
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.containsUuid("Target")) {
            this.targetUuid = nbt.getUuid("Target");
        } else {
            this.targetUuid = null;
        }

    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (this.targetEntity != null) {
            nbt.putUuid("Target", this.targetEntity.getUuid());
        }

    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, BlockEntityDeepSentinel blockEntity) {
        blockEntity.timer++;
        if (blockEntity.isActive() && blockEntity.timer >= blockEntity.tickRate) {
            //givePlayersEffects(world, pos, blockEntity);
            updateTargetEntity(world, pos, blockEntity);
            attackHostileEntity(world, pos, state, blockEntity);
        }

    }

    private float getPower(){
        return this.power;
    }

    private static void givePlayersEffects(World world, BlockPos pos, BlockEntityDeepSentinel blockEntity) {
        int i = 42; // Radius? How many blocks it needs for full power?
        int j = i / 7 * 16;
        int k = pos.getX();
        int l = pos.getY();
        int m = pos.getZ();
        Box box = (new Box((double)k, (double)l, (double)m, (double)(k + 1), (double)(l + 1), (double)(m + 1))).expand((double)j).stretch(0.0, (double)world.getHeight(), 0.0);
        List<PlayerEntity> list = world.getNonSpectatingEntities(PlayerEntity.class, box);
        if (!list.isEmpty()) {
            Iterator var10 = list.iterator();

            while(var10.hasNext()) {
                PlayerEntity playerEntity = (PlayerEntity)var10.next();
                if (pos.isWithinDistance(playerEntity.getBlockPos(), (double)j) && playerEntity.isTouchingWaterOrRain()) {
                    playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 260, 0, true, true));
                }
            }

        }
    }

    private static void attackHostileEntity(World world, BlockPos pos, BlockState state, BlockEntityDeepSentinel blockEntity) {
        LivingEntity livingEntity = blockEntity.targetEntity;
        int i = 42; // Radius? How many blocks it needs for full power?
        if (i < 42) {
            blockEntity.targetEntity = null;
        } else if (blockEntity.targetEntity == null && blockEntity.targetUuid != null) {
            blockEntity.targetEntity = findTargetEntity(world, pos, blockEntity.targetUuid);
            blockEntity.targetUuid = null;
        } else if (blockEntity.targetEntity == null) {
            List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, getAttackZone(pos), (entity) -> {
                return entity instanceof Monster;
            });
            if (!list.isEmpty()) {
                blockEntity.targetEntity = (LivingEntity)list.get(world.random.nextInt(list.size()));
            }
        } else if (!blockEntity.targetEntity.isAlive() || !pos.isWithinDistance(blockEntity.targetEntity.getBlockPos(), 8.0)) {
            blockEntity.targetEntity = null;
        }

        if (blockEntity.targetEntity != null) {
            world.playSound((PlayerEntity)null, blockEntity.targetEntity.getX(), blockEntity.targetEntity.getY(), blockEntity.targetEntity.getZ(), SoundEvents.BLOCK_CONDUIT_ATTACK_TARGET, SoundCategory.BLOCKS, 1.0F, 1.0F);
            blockEntity.targetEntity.damage(DamageSource.MAGIC, blockEntity.getPower());
        }

        if (livingEntity != blockEntity.targetEntity) {
            world.updateListeners(pos, state, state, 2);
        }

    }

    private static void updateTargetEntity(World world, BlockPos pos, BlockEntityDeepSentinel blockEntity) {
        if (blockEntity.targetUuid == null) {
            blockEntity.targetEntity = null;
        } else if (blockEntity.targetEntity == null || !blockEntity.targetEntity.getUuid().equals(blockEntity.targetUuid)) {
            blockEntity.targetEntity = findTargetEntity(world, pos, blockEntity.targetUuid);
            if (blockEntity.targetEntity == null) {
                blockEntity.targetUuid = null;
            }
        }

    }

    private static Box getAttackZone(BlockPos pos) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        return (new Box((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1))).expand(8.0);
    }

    @Nullable
    private static LivingEntity findTargetEntity(World world, BlockPos pos, UUID uuid) {
        List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, getAttackZone(pos), (entity) -> {
            return entity.getUuid().equals(uuid);
        });
        return list.size() == 1 ? (LivingEntity)list.get(0) : null;
    }

    public boolean isActive() {
        return this.active;
    }

}

