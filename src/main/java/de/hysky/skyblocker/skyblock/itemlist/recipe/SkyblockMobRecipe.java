package de.hysky.skyblocker.skyblock.itemlist.recipe;

import io.github.moulberry.repo.data.NEUMobDropRecipe;
import net.minecraft.item.ItemStack;

import java.util.List;

public class SkyblockMobRecipe implements SkyblockRecipe {

    private int combatExperience;
    private int coins;
    private int enchantingExperience;
    private String name;
    private String render;
    private String panorama;
    private int level;
    private List<String> extra;
    private List<Drop> drops;
    private ItemStack dropsFrom;

    public static SkyblockMobRecipe fromNEURecipe(NEUMobDropRecipe neuRecipe) {
        SkyblockMobRecipe recipe = new SkyblockMobRecipe();
        recipe.combatExperience = neuRecipe.getCombatExperience();
        recipe.coins = neuRecipe.getCoins();
        recipe.enchantingExperience = neuRecipe.getEnchantingExperience();
        recipe.name = neuRecipe.getName();
        recipe.render = neuRecipe.getRender();
        recipe.panorama = neuRecipe.getPanorama();
        recipe.level = neuRecipe.getLevel();
        recipe.extra = neuRecipe.getExtra();
        recipe.drops = neuRecipe.getDrops().stream()
                .map(drop -> new Drop(
                        RecipeUtil.getItemStack(drop.getDropItem()), drop.getChance(), drop.getExtra()
                )).toList();
        recipe.dropsFrom = RecipeUtil.getItemStack(neuRecipe.getAllInputs().iterator().next());
        return recipe;
    }

    @Override
    public List<ItemStack> getInput() {
        return List.of(dropsFrom);
    }

    @Override
    public List<ItemStack> getOutput() {
        return drops.stream().map(item -> item.item).toList();
    }


    public record Drop(ItemStack item, String chance, List<String> extra) {
    }

    public int getCombatExperience() {
        return combatExperience;
    }

    public int getCoins() {
        return coins;
    }

    public int getEnchantingExperience() {
        return enchantingExperience;
    }

    public String getName() {
        return name;
    }

    public String getRender() {
        return render;
    }

    public String getPanorama() {
        return panorama;
    }

    public int getLevel() {
        return level;
    }

    public List<String> getExtra() {
        return extra;
    }

    public List<Drop> getDrops() {
        return drops;
    }

    public ItemStack getDropsFrom() {
        return dropsFrom;
    }
}
