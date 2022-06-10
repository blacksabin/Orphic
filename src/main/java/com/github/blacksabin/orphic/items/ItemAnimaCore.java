package com.github.blacksabin.orphic.items;

import com.github.blacksabin.orphic.components.AnimaInternal;
import com.github.blacksabin.orphic.common.BaseItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

import static com.github.blacksabin.orphic.common.AnimaUtil.getAnima;

public class ItemAnimaCore extends BaseItem implements AnimaInternal {

    public ItemAnimaCore(Settings settings) {
        super(settings);
    }

    @Override
    public String getComponentType() {
        return "core";
    }

    @Override
    public boolean hasGlint(ItemStack stack){
        return true;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {

        NbtCompound nbt = getAnima(itemStack);

        //tooltip.add( (new TranslatableText("anima.orphic.manaCurrent").append()).formatted(Formatting.DARK_PURPLE) );
        tooltip.add( (Text.translatable("item.orphic.manaMax").append(Integer.toString(nbt.getInt("manaMax"))).formatted(Formatting.DARK_PURPLE)) );
        tooltip.add( (Text.translatable("item.orphic.manaRegen").append(Integer.toString(nbt.getInt("manaRegen"))).formatted(Formatting.DARK_PURPLE)) );

    }

}