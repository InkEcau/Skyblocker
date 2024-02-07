package de.hysky.skyblocker.compatibility.rei.forge;

import de.hysky.skyblocker.compatibility.rei.SkyblockRecipeDisplayGenerator;
import de.hysky.skyblocker.skyblock.itemlist.recipe.SkyblockForgeRecipe;
import me.shedaniel.rei.api.common.entry.EntryStack;

public class SkyblockForgeDisplayGenerator extends SkyblockRecipeDisplayGenerator<SkyblockForgeDisplay, SkyblockForgeRecipe> {
    public SkyblockForgeDisplayGenerator(EntryStack<?> workstation) {
        super(workstation, SkyblockForgeDisplay.class, SkyblockForgeRecipe.class);
    }
}
