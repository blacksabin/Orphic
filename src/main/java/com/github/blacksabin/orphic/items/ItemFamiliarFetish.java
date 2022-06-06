package com.github.blacksabin.orphic.items;

import com.github.blacksabin.orphic.common.BaseItem;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
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
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {

        if(!world.isClient()){
            ItemStack mainHandItem = playerEntity.getMainHandStack();
            if(hasFamiliar(mainHandItem)){
                this.summonFamiliar((ServerWorld)world,mainHandItem,playerEntity,playerEntity.getBlockPos());
            }

            return TypedActionResult.success(playerEntity.getStackInHand(hand));

        }
        return TypedActionResult.pass(playerEntity.getStackInHand(hand));
    }

    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(!user.getWorld().isClient()){
            LOGGER.info("useOnEntity called");
            ItemStack itemStack = user.getStackInHand(hand);
            if(entity instanceof PassiveEntity){
                LOGGER.info("PassiveEntity check confirmed");
                NbtCompound nbt = (NbtCompound)stack.getOrCreateNbt();
                if(!nbt.contains(ENTITY_TAG_KEY)){
                    LOGGER.info("Nbt is empty");
                    if(checkOwner(entity, user.getUuid())){
                        setFamiliar(entity, itemStack);
                    }
                }else {
                    LOGGER.info("Checking for owner");
                    if (checkOwner(entity, user.getUuid())) {
                        LOGGER.info("Player is Owner");
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

    public void summonFamiliar(ServerWorld world, ItemStack stack, PlayerEntity player, BlockPos pos) {
        if (hasFamiliar(stack)) {
            EntityType<?> summonType = EntityType.get(stack.getOrCreateNbt().getString(ENTITY_TYPE_KEY)).orElseThrow();
            Entity entity = summonType.spawnFromItemStack(world, stack, null, pos, SpawnReason.EVENT, true, false);
        }
    }


    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

    }

}
