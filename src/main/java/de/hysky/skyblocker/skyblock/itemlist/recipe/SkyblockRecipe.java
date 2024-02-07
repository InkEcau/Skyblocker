package de.hysky.skyblocker.skyblock.itemlist.recipe;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface SkyblockRecipe {
    List<ItemStack> getInput();
    List<ItemStack> getOutput();
}
