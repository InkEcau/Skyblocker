package de.hysky.skyblocker.compatibility.rei.trade;

import de.hysky.skyblocker.compatibility.rei.SkyblockRecipeDisplayGenerator;
import de.hysky.skyblocker.skyblock.itemlist.recipe.SkyblockTradeRecipe;
import me.shedaniel.rei.api.common.entry.EntryStack;

public class SkyblockTradeDisplayGenerator extends SkyblockRecipeDisplayGenerator<SkyblockTradeDisplay, SkyblockTradeRecipe> {

    public SkyblockTradeDisplayGenerator(EntryStack<?> workstation) {
        super(workstation, SkyblockTradeDisplay.class, SkyblockTradeRecipe.class);
    }
}
