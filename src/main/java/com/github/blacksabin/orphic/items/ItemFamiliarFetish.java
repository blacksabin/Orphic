package com.github.blacksabin.orphic.items;

import com.github.blacksabin.orphic.common.BaseItem;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.UUID;

import static com.github.blacksabin.orphic.OrphicInit.LOGGER;


public class ItemFamiliarFetish extends BaseItem {
    private static final String ENTITY_TAG_KEY = "EntityTag";
    private static final String ENTITY_TYPE_KEY = "SummonType";

    public ItemFamiliarFetish(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(!context.getWorld().isClient()){
            ItemStack stack = context.getPlayer().getMainHandStack();
            if(hasFamiliar(stack)){
                this.summonFamiliar((ServerWorld)context.getWorld(),stack,context.getBlockPos());
            }

            return ActionResult.SUCCESS;

        }
        return super.useOnBlock(context);
    }

    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(!user.getWorld().isClient()){
            ItemStack itemStack = user.getStackInHand(hand);
            if((entity instanceof PassiveEntity) || (entity instanceof Tameable)){
                NbtCompound nbt = stack.getOrCreateNbt();
                if(!nbt.contains(ENTITY_TAG_KEY)){
                    if(checkOwner(entity, user.getUuid())){
                        setFamiliar(entity, itemStack);
                    }
                }else{
                    if (checkOwner(entity, user.getUuid())) {
                        storeFamiliar(entity, itemStack);
                    }
                }
            }

            return ActionResult.SUCCESS;

        }
        return ActionResult.PASS;
    }

    public static void setFamiliar(LivingEntity entity, ItemStack fetish){

        LOGGER.info("Setting Familiar");
        storeFamiliar(entity, fetish);
    }

    public static void storeFamiliar(LivingEntity entity, ItemStack fetish){
        LOGGER.info("storeFamiliar called");
        NbtCompound entityTag = new NbtCompound();
        entity.writeNbt(entityTag);
        NbtCompound itemNbt = fetish.getOrCreateNbt();
        itemNbt.put(ENTITY_TAG_KEY,entityTag);
        itemNbt.putUuid("SummonID",entity.getUuid());
        Identifier identifier = EntityType.getId(entity.getType());
        itemNbt.putString(ENTITY_TYPE_KEY,identifier.toString());
        entity.discard();
    }

    public static NbtList toNbtList(double... values) {
        NbtList nbtList = new NbtList();

        for(double d : values) {
            nbtList.add(NbtDouble.of(d));
        }

        return nbtList;
    }

    public static boolean checkOwner(LivingEntity entity, UUID ownerID){
        if(entity instanceof Tameable){
            return ownerID == ((Tameable) entity).getOwnerUuid();
        }else{
            return true;
        }
    }

    public static boolean hasFamiliar(ItemStack stack){
        NbtCompound nbt = stack.getOrCreateNbt();
        return nbt.contains(ENTITY_TYPE_KEY);
    }

    public void summonFamiliar(ServerWorld world, ItemStack stack, BlockPos pos) {
        if (hasFamiliar(stack)) {
            NbtCompound nbt = stack.getNbt();
            NbtCompound entityTag = nbt.getCompound(ENTITY_TAG_KEY);
            entityTag.put("Pos", toNbtList(pos.getX() + 0.5, pos.getY()+1, pos.getZ()+0.5));
            EntityType<?> summonType = EntityType.get(nbt.getString(ENTITY_TYPE_KEY)).orElseThrow();
            Entity entity = summonType.spawnFromItemStack(world, stack, null, pos, SpawnReason.EVENT, true, false);
            UUID summonID = nbt.getUuid("SummonID");
            entity.setUuid(summonID);
            NbtCompound nbtSummonId = new NbtCompound();
            nbtSummonId.putUuid("SummonID",summonID);
            stack.setNbt(nbtSummonId);
        }
    }


    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

    }

}
