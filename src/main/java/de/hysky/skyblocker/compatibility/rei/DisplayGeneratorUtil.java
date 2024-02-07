package de.hysky.skyblocker.compatibility.rei;

import de.hysky.skyblocker.skyblock.itemlist.recipe.SkyblockRecipe;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;

import java.util.List;

public class DisplayGeneratorUtil {

    public static List<EntryIngredient> generateRecipeInputs(SkyblockRecipe recipe) {
        return recipe.getInput().stream().map(item -> EntryIngredient.of(EntryStacks.of(item))).toList();
    }

    public static List<EntryIngredient> generateRecipeOutputs(SkyblockRecipe recipe) {
        return recipe.getOutput().stream().map(item -> EntryIngredient.of(EntryStacks.of(item))).toList();
    }

}
