package de.hysky.skyblocker.compatibility.rei.kat;

import de.hysky.skyblocker.compatibility.rei.DisplayGeneratorUtil;
import de.hysky.skyblocker.compatibility.rei.SkyblockRecipeDisplayGenerator;
import de.hysky.skyblocker.skyblock.itemlist.ItemRepository;
import de.hysky.skyblocker.skyblock.itemlist.recipe.SkyblockKatRecipe;
import me.shedaniel.rei.api.common.entry.EntryStack;

import java.util.List;

public class SkyblockKatDisplayGenerator extends SkyblockRecipeDisplayGenerator<SkyblockKatDisplay, SkyblockKatRecipe> {
    public SkyblockKatDisplayGenerator(EntryStack<?> workstation) {
        super(workstation, SkyblockKatDisplay.class, SkyblockKatRecipe.class);
    }

    @Override
    public List<SkyblockKatDisplay> getUsageFor(String itemId) {
        if ("KAT_NPC".equals(itemId)) {
            return ItemRepository.skyblockRecipes.stream()
                    .filter(recipe -> recipe instanceof SkyblockKatRecipe)
                    .map(recipe ->
                            new SkyblockKatDisplay(
                                    (SkyblockKatRecipe) recipe,
                                    DisplayGeneratorUtil.generateRecipeInputs(recipe),
                                    DisplayGeneratorUtil.generateRecipeOutputs(recipe))
                    ).toList();
        } else {
            return super.getUsageFor(itemId);
        }
    }
}
