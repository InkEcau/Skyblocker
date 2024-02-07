package de.hysky.skyblocker.skyblock.itemlist.recipe;

import io.github.moulberry.repo.data.NEUForgeRecipe;
import net.minecraft.item.ItemStack;

import java.util.List;

public class SkyblockForgeRecipe implements SkyblockRecipe{

    private List<ItemStack> costs;
    private ItemStack result;
    private int duration;

    private SkyblockForgeRecipe(){};

    public static SkyblockForgeRecipe fromNEURecipe(NEUForgeRecipe neuRecipe){
        SkyblockForgeRecipe recipe = new SkyblockForgeRecipe();
        recipe.costs = neuRecipe.getAllInputs().stream().map(RecipeUtil::getItemStack).toList();
        recipe.result = RecipeUtil.getItemStack(neuRecipe.getOutputStack());
        recipe.duration = neuRecipe.getDuration();
        return recipe;
    }

    @Override
    public List<ItemStack> getInput() {
        return costs;
    }

    @Override
    public List<ItemStack> getOutput() {
        return List.of(result);
    }

    public int getDuration() {
        return duration;
    }
}
