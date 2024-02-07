package de.hysky.skyblocker.skyblock.itemlist.recipe;

import de.hysky.skyblocker.skyblock.itemlist.ItemRepository;
import de.hysky.skyblocker.utils.ItemUtils;
import de.hysky.skyblocker.utils.NEURepoManager;
import io.github.moulberry.repo.data.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class RecipeUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeUtil.class);

    /**
     * Generate ItemStack from a NEUIngredient.
     * @param input NEUIngredient
     * @return ItemStack
     */
    public static ItemStack getItemStack(NEUIngredient input) {
        if (input != NEUIngredient.SENTINEL_EMPTY) {
            String itemId = input.getItemId();
            if (itemId.equals(NEUIngredient.NEU_SENTINEL_COINS))
                return ItemUtils.getCoinItemStack((long) input.getAmount());
            ItemStack stack = ItemRepository.getItemStack(itemId);
            if (stack != null) {
                return stack.copyWithCount((int) input.getAmount());
            } else {
                LOGGER.warn("[Skyblocker Recipe] Unable to find item {}", itemId);
            }
        }
        return Items.AIR.getDefaultStack();
    }

    /**
     * Get all directly associated recipes.
     * @param itemId internal ID of the item
     * @return recipes
     */
    public static List<SkyblockRecipe> getRecipes(String itemId) {
        return NEURepoManager.NEU_REPO.getItems().getItemBySkyblockId(itemId)
                .getRecipes().stream()
                .map(RecipeUtil::loadFromNeuRecipe)
                .filter(Objects::nonNull)
                .toList();
    }

    /**
     * Load recipe from neu recipe.
     * @param recipe neu recipe
     * @return loaded recipe
     */
    public static SkyblockRecipe loadFromNeuRecipe(NEURecipe recipe) {
        if (recipe instanceof NEUCraftingRecipe neuCraftingRecipe) {
            return SkyblockCraftingRecipe.fromNEURecipe(neuCraftingRecipe);
        } else if (recipe instanceof NEUNpcShopRecipe neuNpcShopRecipe) {
            return SkyblockNpcShopRecipe.fromNEURecipe(neuNpcShopRecipe);
        } else if (recipe instanceof NEUForgeRecipe neuForgeRecipe) {
            return SkyblockForgeRecipe.fromNEURecipe(neuForgeRecipe);
        } else if (recipe instanceof NEUKatUpgradeRecipe neuKatUpgradeRecipe) {
            return SkyblockKatRecipe.fromNEURecipe(neuKatUpgradeRecipe);
        } else if (recipe instanceof NEUTradeRecipe neuTradeRecipe) {
            return SkyblockTradeRecipe.fromNEURecipe(neuTradeRecipe);
        } else if (recipe instanceof NEUMobDropRecipe neuMobDropRecipe) {
            return SkyblockMobRecipe.fromNEURecipe(neuMobDropRecipe);
        }
        return null;
    }
}
