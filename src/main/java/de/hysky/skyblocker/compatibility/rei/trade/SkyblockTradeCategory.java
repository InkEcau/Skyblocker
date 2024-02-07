package de.hysky.skyblocker.compatibility.rei.trade;

import de.hysky.skyblocker.compatibility.rei.RoundedIngredientCategory;
import de.hysky.skyblocker.compatibility.rei.SkyblockerREIClientPlugin;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

import java.util.List;

public class SkyblockTradeCategory extends RoundedIngredientCategory<SkyblockTradeDisplay> {

    @Override
    public CategoryIdentifier<SkyblockTradeDisplay> getCategoryIdentifier() {
        return SkyblockerREIClientPlugin.SKYBLOCK_TRADE;
    }

    @Override
    public Text getTitle() {
        return Text.literal("Skyblock Trade(Stranded)");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Items.EMERALD);
    }

    @Override
    public List<Widget> setupInputDisplay(SkyblockTradeDisplay display, Rectangle inputBounds, Rectangle bounds) {
        List<Widget> widgets = super.setupInputDisplay(display, inputBounds, bounds);
        if (display.getRecipe().getMin() == 0 || display.getRecipe().getMax() == 0) {
            return widgets;
        }
        // min
        widgets.add(Widgets.createLabel(new Point(inputBounds.getCenterX(), inputBounds.getCenterY()+9), Text.of("min:" + display.getRecipe().getMin())));
        // max
        widgets.add(Widgets.createLabel(new Point(inputBounds.getCenterX(), inputBounds.getCenterY()+18), Text.of("max:" + display.getRecipe().getMax())));
        return widgets;
    }

}
