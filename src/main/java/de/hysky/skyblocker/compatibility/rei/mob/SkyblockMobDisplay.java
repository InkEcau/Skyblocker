package de.hysky.skyblocker.compatibility.rei.mob;

import de.hysky.skyblocker.compatibility.rei.SkyblockerREIClientPlugin;
import de.hysky.skyblocker.skyblock.itemlist.recipe.SkyblockMobRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;

import java.util.List;

public class SkyblockMobDisplay extends BasicDisplay {
    private final SkyblockMobRecipe recipe;
    public SkyblockMobDisplay(SkyblockMobRecipe recipe, List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
        this.recipe = recipe;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return SkyblockerREIClientPlugin.SKYBLOCK_MOB;
    }

    public SkyblockMobRecipe getRecipe() {
        return recipe;
    }
}
