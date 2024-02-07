package de.hysky.skyblocker.compatibility.rei.mob;

import de.hysky.skyblocker.compatibility.rei.SkyblockRecipeDisplayGenerator;
import de.hysky.skyblocker.skyblock.itemlist.recipe.SkyblockMobRecipe;
import me.shedaniel.rei.api.common.entry.EntryStack;

public class SkyblockMobDisplayGenerator extends SkyblockRecipeDisplayGenerator<SkyblockMobDisplay, SkyblockMobRecipe> {
    public SkyblockMobDisplayGenerator(EntryStack<?> workstation) {
        super(workstation, SkyblockMobDisplay.class, SkyblockMobRecipe.class);
    }
}
