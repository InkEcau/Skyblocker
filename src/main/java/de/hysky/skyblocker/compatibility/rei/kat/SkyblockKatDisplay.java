package de.hysky.skyblocker.compatibility.rei.kat;

import de.hysky.skyblocker.compatibility.rei.SkyblockerREIClientPlugin;
import de.hysky.skyblocker.skyblock.itemlist.recipe.SkyblockKatRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;

import java.util.List;

public class SkyblockKatDisplay extends BasicDisplay {

    private final SkyblockKatRecipe recipe;

    public SkyblockKatDisplay(SkyblockKatRecipe recipe, List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
        this.recipe = recipe;
    }

    public SkyblockKatRecipe getRecipe() {
        return recipe;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return SkyblockerREIClientPlugin.SKYBLOCK_KAT;
    }
}
