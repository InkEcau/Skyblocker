package de.hysky.skyblocker.compatibility.rei;

import de.hysky.skyblocker.skyblock.itemlist.ItemRepository;
import de.hysky.skyblocker.skyblock.itemlist.recipe.SkyblockRecipe;
import de.hysky.skyblocker.utils.ItemUtils;
import me.shedaniel.rei.api.client.registry.display.DynamicDisplayGenerator;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class SkyblockRecipeDisplayGenerator<T extends Display, U extends SkyblockRecipe> implements DynamicDisplayGenerator<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkyblockRecipeDisplayGenerator.class);

    private final Class<U> recipeClass;
    private Constructor<T> constructor;
    private final EntryStack<?> workstation;

    public SkyblockRecipeDisplayGenerator(EntryStack<?> workstation, Class<T> displayClass, Class<U> recipeClass) {
        this.workstation = workstation;
        this.recipeClass = recipeClass;
        try {
            this.constructor = displayClass.getConstructor(recipeClass, List.class, List.class);
        } catch (NoSuchMethodException e) {
            LOGGER.error(String.format("[Skyblocker]Error when creating DisplayGenerator: no constructor matched. (%s, %s)", displayClass, recipeClass), e);
            this.constructor = null;
        }
    }

    @Override
    public Optional<List<T>> getRecipeFor(EntryStack<?> entry) {
        if (constructor == null) return Optional.empty();
        if (!(entry.getValue() instanceof ItemStack)) return Optional.empty();
        String itemID = ItemUtils.getItemId((ItemStack) entry.getValue());
        if ("".equals(itemID)) return Optional.empty();

        List<T> displays = ItemRepository.skyblockRecipes.stream()
                .filter(recipe ->
                        recipeClass.isInstance(recipe) &&
                                recipe.getOutput().stream().anyMatch(item -> itemID.equals(ItemUtils.getItemId(item)))
                ).map(recipe -> {
                    try {
                        return constructor.newInstance(
                                recipe,
                                DisplayGeneratorUtil.generateRecipeInputs(recipe),
                                DisplayGeneratorUtil.generateRecipeOutputs(recipe));
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
        List<T> customDisplays = getRecipeFor(itemID);
        List<T> result = new ArrayList<>(displays.size() + customDisplays.size());
        result.addAll(displays);
        result.addAll(customDisplays);
        return Optional.of(result);
    }

    @Override
    public Optional<List<T>> getUsageFor(EntryStack<?> entry) {
        if (constructor == null) return Optional.empty();
        if (!(entry.getValue() instanceof ItemStack)) return Optional.empty();
        boolean ignoreIngredientMatch = EntryStacks.equalsExact(workstation, entry);
        String itemID = ItemUtils.getItemId((ItemStack) entry.getValue());
        if (!ignoreIngredientMatch && "".equals(itemID)) return Optional.empty();

        List<T> displays = ItemRepository.skyblockRecipes.stream()
                .filter(recipe ->
                        recipeClass.isInstance(recipe) &&
                                (ignoreIngredientMatch || recipe.getInput().stream().anyMatch(item -> itemID.equals(ItemUtils.getItemId(item))))
                ).map(recipe -> {
                    try {
                        return constructor.newInstance(
                                recipe,
                                DisplayGeneratorUtil.generateRecipeInputs(recipe),
                                DisplayGeneratorUtil.generateRecipeOutputs(recipe));
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
        List<T> customDisplays = getUsageFor(itemID);
        List<T> result = new ArrayList<>(displays.size() + customDisplays.size());
        result.addAll(displays);
        result.addAll(customDisplays);
        return Optional.of(result);
    }

    /**
     * A customizable method for generating a Display class for a specific itemId.
     *
     * @param itemId NEU item ID
     * @return list of displays
     */
    public List<T> getUsageFor(String itemId) {
        return List.of();
    }

    /**
     * A customizable method for generating a Display class for a specific itemId.
     *
     * @param itemId NEU item ID
     * @return list of displays
     */
    public List<T> getRecipeFor(String itemId) {
        return List.of();
    }


}
