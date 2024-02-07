package de.hysky.skyblocker.skyblock.itemlist.recipe;

import io.github.moulberry.repo.data.NEUIngredient;
import io.github.moulberry.repo.data.NEUKatUpgradeRecipe;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkyblockKatRecipe implements SkyblockRecipe {

    private ItemStack oldPet;
    private ItemStack result;
    private List<ItemStack> items;
    private double coins;
    private long duration;

    private SkyblockKatRecipe() {
    }

    public static SkyblockKatRecipe fromNEURecipe(NEUKatUpgradeRecipe neuRecipe) {
        SkyblockKatRecipe recipe = new SkyblockKatRecipe();
        recipe.oldPet = RecipeUtil.getItemStack(neuRecipe.getInput());
        recipe.result = RecipeUtil.getItemStack(neuRecipe.getOutput());
        recipe.items = neuRecipe.getItems().stream().map(RecipeUtil::getItemStack).toList();
        recipe.coins = neuRecipe.getCoins();
        recipe.duration = neuRecipe.getSeconds();
        return recipe;
    }

    /**
     * Get all materials required to upgrade the pet(pet not included).
     *
     * @return materials
     */
    @Override
    public List<ItemStack> getInput() {
        List<ItemStack> inputs = new ArrayList<>();
        inputs.add(RecipeUtil.getItemStack(NEUIngredient.ofCoins(coins)));
        inputs.addAll(items);
        return inputs;
    }

    @Override
    public List<ItemStack> getOutput() {
        return List.of(result);
    }

    public double getCoins() {
        return coins;
    }

    public ItemStack getOldPet() {
        return oldPet;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public long getDuration() {
        return duration;
    }
}
