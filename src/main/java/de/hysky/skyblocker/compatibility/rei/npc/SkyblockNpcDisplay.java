package de.hysky.skyblocker.compatibility.rei.npc;

import de.hysky.skyblocker.compatibility.rei.SkyblockerREIClientPlugin;
import de.hysky.skyblocker.skyblock.itemlist.recipe.SkyblockNpcShopRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;

import java.util.List;

public class SkyblockNpcDisplay extends BasicDisplay {

    private final SkyblockNpcShopRecipe recipe;

    public SkyblockNpcDisplay(SkyblockNpcShopRecipe recipe, List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
        this.recipe = recipe;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return SkyblockerREIClientPlugin.SKYBLOCK_NPC;
    }

    public SkyblockNpcShopRecipe getRecipe() {
        return recipe;
    }
}
