package de.hysky.skyblocker.compatibility.rei.trade;

import de.hysky.skyblocker.compatibility.rei.SkyblockerREIClientPlugin;
import de.hysky.skyblocker.skyblock.itemlist.recipe.SkyblockTradeRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;

import java.util.List;

public class SkyblockTradeDisplay extends BasicDisplay {

    private final SkyblockTradeRecipe recipe;

    public SkyblockTradeDisplay(SkyblockTradeRecipe recipe, List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
        this.recipe = recipe;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return SkyblockerREIClientPlugin.SKYBLOCK_TRADE;
    }

    public SkyblockTradeRecipe getRecipe() {
        return recipe;
    }
}
