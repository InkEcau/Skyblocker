package de.hysky.skyblocker.compatibility.rei.npc;

import de.hysky.skyblocker.compatibility.rei.RoundedIngredientCategory;
import de.hysky.skyblocker.compatibility.rei.SkyblockerREIClientPlugin;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.*;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

import java.util.List;

public class SkyblockNpcCategory extends RoundedIngredientCategory<SkyblockNpcDisplay> {

    @Override
    public CategoryIdentifier<SkyblockNpcDisplay> getCategoryIdentifier() {
        return SkyblockerREIClientPlugin.SKYBLOCK_NPC;
    }

    @Override
    public Text getTitle() {
        return Text.literal("Skyblock NPC Shop");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Items.WHEAT_SEEDS);
    }

    @Override
    public List<Widget> setupDisplay(SkyblockNpcDisplay display, Rectangle bounds) {
        List<Widget> widgets = super.setupDisplay(display, bounds);
        Button button = Widgets.createButton(new Rectangle(bounds.getMaxX() - 18, bounds.getMaxY() - 18, 12, 12), Text.literal("I"));
        button.setTooltip(b -> {
//            Text[] texts = new Text[5];
            Text[] texts = new Text[1];
            texts[0] = Text.literal("NPC: " + display.getRecipe().getNpcName());
//            texts[1] = Text.literal("Island: " + display.getRecipe().getIsland());
//            texts[2] = Text.literal("Position: " + String.format("X:%s Y:%s Z:%s", display.getRecipe().getPos().getX(), display.getRecipe().getPos().getY(),display.getRecipe().getPos().getZ()));
//            texts[3] = Text.literal("");
//            texts[4] = Text.literal("Click to highlight waypoint!").withColor(0xffff00);
            return texts;
        });
        widgets.add(button);
        return widgets;
    }
}
