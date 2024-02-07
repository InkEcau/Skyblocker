package de.hysky.skyblocker.compatibility.rei;

import de.hysky.skyblocker.SkyblockerMod;
import de.hysky.skyblocker.compatibility.rei.crafting.SkyblockCraftingCategory;
import de.hysky.skyblocker.compatibility.rei.crafting.SkyblockCraftingDisplay;
import de.hysky.skyblocker.compatibility.rei.crafting.SkyblockCraftingDisplayGenerator;
import de.hysky.skyblocker.compatibility.rei.forge.SkyblockForgeCategory;
import de.hysky.skyblocker.compatibility.rei.forge.SkyblockForgeDisplay;
import de.hysky.skyblocker.compatibility.rei.forge.SkyblockForgeDisplayGenerator;
import de.hysky.skyblocker.compatibility.rei.kat.SkyblockKatCategory;
import de.hysky.skyblocker.compatibility.rei.kat.SkyblockKatDisplay;
import de.hysky.skyblocker.compatibility.rei.kat.SkyblockKatDisplayGenerator;
import de.hysky.skyblocker.compatibility.rei.mob.SkyblockMobCategory;
import de.hysky.skyblocker.compatibility.rei.mob.SkyblockMobDisplay;
import de.hysky.skyblocker.compatibility.rei.mob.SkyblockMobDisplayGenerator;
import de.hysky.skyblocker.compatibility.rei.npc.SkyblockNpcCategory;
import de.hysky.skyblocker.compatibility.rei.npc.SkyblockNpcDisplay;
import de.hysky.skyblocker.compatibility.rei.npc.SkyblockNpcDisplayGenerator;
import de.hysky.skyblocker.compatibility.rei.trade.SkyblockTradeCategory;
import de.hysky.skyblocker.compatibility.rei.trade.SkyblockTradeDisplay;
import de.hysky.skyblocker.compatibility.rei.trade.SkyblockTradeDisplayGenerator;
import de.hysky.skyblocker.skyblock.itemlist.ItemRepository;
import de.hysky.skyblocker.utils.NEURepoManager;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.plugins.PluginManager;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.Items;

/**
 * REI integration
 */
public class SkyblockerREIClientPlugin implements REIClientPlugin {
    public static final CategoryIdentifier<SkyblockCraftingDisplay> SKYBLOCK_CRAFTING = CategoryIdentifier.of(SkyblockerMod.NAMESPACE, "skyblock_crafting");
    public static final CategoryIdentifier<SkyblockNpcDisplay> SKYBLOCK_NPC = CategoryIdentifier.of(SkyblockerMod.NAMESPACE, "skyblock_npc");
    public static final CategoryIdentifier<SkyblockForgeDisplay> SKYBLOCK_FORGE = CategoryIdentifier.of(SkyblockerMod.NAMESPACE, "skyblock_forge");
    public static final CategoryIdentifier<SkyblockKatDisplay> SKYBLOCK_KAT = CategoryIdentifier.of(SkyblockerMod.NAMESPACE, "skyblock_kat");
    public static final CategoryIdentifier<SkyblockMobDisplay> SKYBLOCK_MOB = CategoryIdentifier.of(SkyblockerMod.NAMESPACE, "skyblock_mob");
    public static final CategoryIdentifier<SkyblockTradeDisplay> SKYBLOCK_TRADE = CategoryIdentifier.of(SkyblockerMod.NAMESPACE, "skyblock_trade");

    private static boolean isRegisteredNeuReload = false;

    @Override
    public void registerCategories(CategoryRegistry categoryRegistry) {
        categoryRegistry.addWorkstations(SKYBLOCK_CRAFTING, EntryStacks.of(Items.CRAFTING_TABLE));
        categoryRegistry.add(new SkyblockCraftingCategory());
        categoryRegistry.addWorkstations(SKYBLOCK_NPC, EntryStacks.of(Items.WHEAT_SEEDS));
        categoryRegistry.add(new SkyblockNpcCategory());
        categoryRegistry.addWorkstations(SKYBLOCK_FORGE, EntryStacks.of(Items.ANVIL));
        categoryRegistry.add(new SkyblockForgeCategory());
        categoryRegistry.addWorkstations(SKYBLOCK_KAT, EntryStacks.of(Items.POPPY));
        categoryRegistry.add(new SkyblockKatCategory());
        categoryRegistry.addWorkstations(SKYBLOCK_MOB, EntryStacks.of(Items.DIAMOND_SWORD));
        categoryRegistry.add(new SkyblockMobCategory());
        // TODO NEU repo parser is not fully support this
        categoryRegistry.addWorkstations(SKYBLOCK_TRADE, EntryStacks.of(Items.EMERALD));
        categoryRegistry.add(new SkyblockTradeCategory());
    }

    @Override
    public void registerDisplays(DisplayRegistry displayRegistry) {
        displayRegistry.registerDisplayGenerator(SKYBLOCK_CRAFTING, new SkyblockCraftingDisplayGenerator());
        displayRegistry.registerDisplayGenerator(SKYBLOCK_NPC, new SkyblockNpcDisplayGenerator(EntryStacks.of(Items.WHEAT_SEEDS)));
        displayRegistry.registerDisplayGenerator(SKYBLOCK_FORGE, new SkyblockForgeDisplayGenerator(EntryStacks.of(Items.ANVIL)));
        displayRegistry.registerDisplayGenerator(SKYBLOCK_KAT, new SkyblockKatDisplayGenerator(EntryStacks.of(Items.POPPY)));
        displayRegistry.registerDisplayGenerator(SKYBLOCK_MOB, new SkyblockMobDisplayGenerator(EntryStacks.of(Items.DIAMOND_SWORD)));
        displayRegistry.registerDisplayGenerator(SKYBLOCK_TRADE, new SkyblockTradeDisplayGenerator(EntryStacks.of(Items.EMERALD)));
    }

    @Override
    public void registerEntries(EntryRegistry entryRegistry) {
        entryRegistry.addEntries(ItemRepository.getItemsStream().map(EntryStacks::of).toList());
        if (!isRegisteredNeuReload) {
            // neu repo reload
            NEURepoManager.runAsyncAfterLoad(() -> {
                isRegisteredNeuReload = true;
                if (!PluginManager.getClientInstance().isReloading()) PluginManager.getClientInstance().startReload();
            });
        }
    }
}
