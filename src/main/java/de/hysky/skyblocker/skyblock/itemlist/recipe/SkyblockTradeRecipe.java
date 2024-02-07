package de.hysky.skyblocker.skyblock.itemlist.recipe;

import io.github.moulberry.repo.data.NEUTradeRecipe;
import net.minecraft.item.ItemStack;

import java.util.List;

public class SkyblockTradeRecipe implements SkyblockRecipe {

    private ItemStack cost;
    private int min;
    private int max;
    private ItemStack result;

    private SkyblockTradeRecipe() {
    }

    public static SkyblockTradeRecipe fromNEURecipe(NEUTradeRecipe neuRecipe) {
        SkyblockTradeRecipe recipe = new SkyblockTradeRecipe();
        recipe.cost = RecipeUtil.getItemStack(neuRecipe.getCost());
        recipe.min = neuRecipe.getMin();
        recipe.max = neuRecipe.getMax();
        recipe.result = RecipeUtil.getItemStack(neuRecipe.getResult());
        return recipe;
    }

    @Override
    public List<ItemStack> getInput() {
        return List.of(cost);
    }

    @Override
    public List<ItemStack> getOutput() {
        return List.of(result);
    }

    public ItemStack getCost() {
        return cost;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
