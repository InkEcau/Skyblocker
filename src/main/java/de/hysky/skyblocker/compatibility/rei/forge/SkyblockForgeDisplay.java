package de.hysky.skyblocker.compatibility.rei.forge;

import de.hysky.skyblocker.compatibility.rei.SkyblockerREIClientPlugin;
import de.hysky.skyblocker.skyblock.itemlist.recipe.SkyblockForgeRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;

import java.util.List;

public class SkyblockForgeDisplay extends BasicDisplay {

    private final SkyblockForgeRecipe recipe;

    public SkyblockForgeDisplay(SkyblockForgeRecipe recipe, List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
        this.recipe = recipe;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return SkyblockerREIClientPlugin.SKYBLOCK_FORGE;
    }

    public SkyblockForgeRecipe getRecipe() {
        return recipe;
    }
}
