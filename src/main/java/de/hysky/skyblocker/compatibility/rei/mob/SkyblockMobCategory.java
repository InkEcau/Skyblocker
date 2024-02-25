package de.hysky.skyblocker.compatibility.rei.mob;

import de.hysky.skyblocker.compatibility.rei.SkyblockerREIClientPlugin;
import de.hysky.skyblocker.skyblock.itemlist.recipe.SkyblockMobRecipe;
import de.hysky.skyblocker.utils.FormatterUtil;
import de.hysky.skyblocker.utils.ItemUtils;
import de.hysky.skyblocker.utils.render.RenderHelper;
import de.hysky.skyblocker.utils.render.entity.NeuEntityConstructor;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SkyblockMobCategory implements DisplayCategory<SkyblockMobDisplay> {

    private static final int RESULT_BOX_COLUMN_COUNT = 6;

    private static final int MOB_RENDER_HEIGHT = 60;
    private static final int MOB_DETAIL_BOX_WIDTH = 60;
    private static final int RESULT_BOX_WIDTH = RESULT_BOX_COLUMN_COUNT * 18;

    @Override
    public CategoryIdentifier<? extends SkyblockMobDisplay> getCategoryIdentifier() {
        return SkyblockerREIClientPlugin.SKYBLOCK_MOB;
    }

    @Override
    public Text getTitle() {
        return Text.of("Skyblock Mob Drops");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Items.DIAMOND_SWORD);
    }


    @Override
    public int getDisplayWidth(SkyblockMobDisplay display) {
        return MOB_DETAIL_BOX_WIDTH + RESULT_BOX_WIDTH + 12;
    }

    @Override
    public int getDisplayHeight() {
        return 120;
    }

    @Override
    public List<Widget> setupDisplay(SkyblockMobDisplay display, Rectangle bounds) {
        Rectangle mobRenderBox = new Rectangle(bounds.getMinX() + 4, bounds.getMinY() + 16, MOB_DETAIL_BOX_WIDTH, bounds.getHeight() - 20);
        Rectangle resultBox = new Rectangle(mobRenderBox.getMaxX() + 4, mobRenderBox.getMinY(), RESULT_BOX_WIDTH, mobRenderBox.getHeight());

        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        // render entity name
        widgets.add(Widgets.createLabel(
                new Point(bounds.getCenterX(), bounds.getMinY() + 5),
                Text.of(String.format("[Lv %s] %s", display.getRecipe().getLevel(), display.getRecipe().getName()))
        ));
        // render entity
        LivingEntity livingEntity = NeuEntityConstructor.createLivingEntity(display.getRecipe().getRender());
        widgets.add(Widgets.withTranslate(Widgets.createDrawableWidget((graphics, mouseX, mouseY, delta) -> {
            if (livingEntity != null) RenderHelper.drawEntity(graphics, 0, 0, mobRenderBox.getWidth(), MOB_RENDER_HEIGHT, RenderHelper.getFittingScale(livingEntity), 0.0625F, mouseX, mouseY, livingEntity);
        }), mobRenderBox.getMinX(), mobRenderBox.getMinY(), 0));
        // render info
        widgets.add(Widgets.createLabel(
                new Point(mobRenderBox.getCenterX(), mobRenderBox.getMinY() + MOB_RENDER_HEIGHT + 6),
                Text.literal("Coins: " + FormatterUtil.formatNumber(display.getRecipe().getCoins())).withColor(0xFFAA00)
        ));
        widgets.add(Widgets.createLabel(
                new Point(mobRenderBox.getCenterX(), mobRenderBox.getMinY() + MOB_RENDER_HEIGHT + 16),
                Text.literal("Exp: " + FormatterUtil.formatNumber(display.getRecipe().getEnchantingExperience())).withColor(0x55FF55)
        ));
        widgets.add(Widgets.createLabel(
                new Point(mobRenderBox.getCenterX(), mobRenderBox.getMinY() + MOB_RENDER_HEIGHT + 26),
                Text.literal("CmbExp: " + FormatterUtil.formatNumber(display.getRecipe().getCombatExperience())).withColor(0x55FFFF)
        ));
        // render drops
        widgets.add(Widgets.createSlotBase(resultBox));
        for (int i = 0; i < display.getRecipe().getDrops().size(); i++) {
            // add drop rate and extra info to item tooltip
            SkyblockMobRecipe.Drop drop = display.getRecipe().getDrops().get(i);
            ItemStack item = ItemUtils.appendLore(drop.item().copy(), Text.of(""));
            ItemUtils.appendLore(item, Text.literal("Chance: " + drop.chance()).withColor(0xFFAA00));
            drop.extra().forEach(s -> ItemUtils.appendLore(item, Text.literal(s)));
            ItemUtils.appendLore(item, Text.literal(""));
            widgets.add(Widgets.createSlot(new Point(resultBox.getMinX() + i % RESULT_BOX_COLUMN_COUNT * 18, resultBox.getMinY() + i / RESULT_BOX_COLUMN_COUNT * 18))
                    .entry(EntryStacks.of(item)).disableBackground());
        }

        return widgets;
    }
}
