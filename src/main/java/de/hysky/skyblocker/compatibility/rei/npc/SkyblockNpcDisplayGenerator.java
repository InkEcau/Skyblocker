package de.hysky.skyblocker.compatibility.rei.npc;

import de.hysky.skyblocker.compatibility.rei.DisplayGeneratorUtil;
import de.hysky.skyblocker.compatibility.rei.SkyblockRecipeDisplayGenerator;
import de.hysky.skyblocker.skyblock.itemlist.recipe.RecipeUtil;
import de.hysky.skyblocker.skyblock.itemlist.recipe.SkyblockNpcShopRecipe;
import me.shedaniel.rei.api.common.entry.EntryStack;

import java.util.List;

public class SkyblockNpcDisplayGenerator extends SkyblockRecipeDisplayGenerator<SkyblockNpcDisplay, SkyblockNpcShopRecipe> {

    public SkyblockNpcDisplayGenerator(EntryStack<?> workstation) {
        super(workstation, SkyblockNpcDisplay.class, SkyblockNpcShopRecipe.class);
    }

    @Override
    public List<SkyblockNpcDisplay> getUsageFor(String itemId) {
        if (itemId.endsWith("NPC")) {
            return RecipeUtil.getRecipes(itemId).stream().filter(recipe ->
                    recipe instanceof SkyblockNpcShopRecipe
            ).map(recipe ->
                    new SkyblockNpcDisplay(
                            (SkyblockNpcShopRecipe) recipe,
                            DisplayGeneratorUtil.generateRecipeInputs(recipe),
                            DisplayGeneratorUtil.generateRecipeOutputs(recipe))
            ).toList();
        } else {
            return super.getUsageFor(itemId);
        }
    }
}
