package de.hysky.skyblocker.compatibility.rei.forge;

import de.hysky.skyblocker.compatibility.rei.RoundedIngredientCategory;
import de.hysky.skyblocker.compatibility.rei.SkyblockerREIClientPlugin;
import de.hysky.skyblocker.utils.FormatterUtil;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Label;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

import java.util.List;

public class SkyblockForgeCategory extends RoundedIngredientCategory<SkyblockForgeDisplay> {
    @Override
    public CategoryIdentifier<SkyblockForgeDisplay> getCategoryIdentifier() {
        return SkyblockerREIClientPlugin.SKYBLOCK_FORGE;
    }

    @Override
    public Text getTitle() {
        return Text.literal("Skyblock Forge");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Items.ANVIL);
    }

    @Override
    public List<Widget> setupDisplay(SkyblockForgeDisplay display, Rectangle bounds) {
        List<Widget> widgets = super.setupDisplay(display, bounds);

        int duration = display.getRecipe().getDuration();
        String durationFormatted = FormatterUtil.formatDuration(duration);
        Point labelPoint = new Point(bounds.getMaxX() - durationFormatted.length() * 3 - 4, bounds.getMaxY() - 12);
        Label craftTextLabel = Widgets.createLabel(labelPoint, Text.of(durationFormatted));
        widgets.add(craftTextLabel);

        return widgets;
    }


}
