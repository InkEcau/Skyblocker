package de.hysky.skyblocker.skyblock.itemlist.recipe;

import io.github.moulberry.repo.data.NEUCraftingRecipe;
import io.github.moulberry.repo.data.NEUIngredient;
import net.minecraft.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SkyblockCraftingRecipe implements SkyblockRecipe {
    private static final Logger LOGGER = LoggerFactory.getLogger(SkyblockCraftingRecipe.class);
    private final String craftText;
    private final List<ItemStack> grid = new ArrayList<>(9);
    private ItemStack result;

    public SkyblockCraftingRecipe(String craftText) {
        this.craftText = craftText;
    }

    public static SkyblockCraftingRecipe fromNEURecipe(NEUCraftingRecipe neuCraftingRecipe) {
        SkyblockCraftingRecipe recipe = new SkyblockCraftingRecipe(neuCraftingRecipe.getExtraText() != null ? neuCraftingRecipe.getExtraText() : "");
        for (NEUIngredient input : neuCraftingRecipe.getInputs()) {
            recipe.grid.add(RecipeUtil.getItemStack(input));
        }
        recipe.result = RecipeUtil.getItemStack(neuCraftingRecipe.getOutput());
        return recipe;
    }

    public List<ItemStack> getGrid() {
        return grid;
    }

    public ItemStack getResult() {
        return result;
    }

    public String getCraftText() {
        return craftText;
    }

    @Override
    public List<ItemStack> getInput() {
        return grid;
    }

    @Override
    public List<ItemStack> getOutput() {
        return List.of(result);
    }
}
