package de.hysky.skyblocker.skyblock.itemlist.recipe;

import io.github.moulberry.repo.data.NEUNpcShopRecipe;
import net.minecraft.item.ItemStack;

import java.util.List;

public class SkyblockNpcShopRecipe implements SkyblockRecipe {

    private List<ItemStack> costs;
    private ItemStack result;
    private String npcName;
//    private String island;
//    private BlockPos pos;

    private SkyblockNpcShopRecipe() {
    }

    public static SkyblockNpcShopRecipe fromNEURecipe(NEUNpcShopRecipe neuRecipe) {
        SkyblockNpcShopRecipe recipe = new SkyblockNpcShopRecipe();
        recipe.costs = neuRecipe.getAllInputs().stream().map(RecipeUtil::getItemStack).toList();
        recipe.result = RecipeUtil.getItemStack(neuRecipe.getResult());
        recipe.npcName = neuRecipe.getIsSoldBy().getDisplayName();
//        recipe.island = "island";
//        recipe.pos = new BlockPos(1,2,3);
        return recipe;
    }

    @Override
    public List<ItemStack> getInput() {
        return costs;
    }

    @Override
    public List<ItemStack> getOutput() {
        return List.of(result);
    }

    public String getNpcName() {
        return npcName;
    }

//    public String getIsland() {
//        return island;
//    }
//
//    public BlockPos getPos() {
//        return pos;
//    }
}
