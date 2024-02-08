package de.hysky.skyblocker.compatibility.rei.kat;

import de.hysky.skyblocker.compatibility.rei.RoundedIngredientCategory;
import de.hysky.skyblocker.compatibility.rei.SkyblockerREIClientPlugin;
import de.hysky.skyblocker.compatibility.rei.SliderBar;
import de.hysky.skyblocker.skyblock.item.Pet;
import de.hysky.skyblocker.utils.FormatterUtil;
import de.hysky.skyblocker.utils.ItemUtils;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.*;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SkyblockKatCategory extends RoundedIngredientCategory<SkyblockKatDisplay> {
    @Override
    public CategoryIdentifier<SkyblockKatDisplay> getCategoryIdentifier() {
        return SkyblockerREIClientPlugin.SKYBLOCK_KAT;
    }

    @Override
    public Text getTitle() {
        return Text.of("Skyblock Kat Upgrade");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ItemUtils.getKatStack());
    }

    @Override
    public List<Widget> setupInputDisplay(SkyblockKatDisplay display, Rectangle inputBounds, Rectangle bounds) {
        List<Widget> widgets = super.setupInputDisplay(display, inputBounds, bounds, false);
        widgets.add(Widgets
                .createSlot(new Point(inputBounds.getCenterX() - 9, inputBounds.getCenterY() - 9))
                .entry(EntryStacks.of(display.getRecipe().getOldPet()))
                .markInput()
        );
        return widgets;
    }

    @Override
    public List<Widget> setupDisplay(SkyblockKatDisplay display, Rectangle bounds) {
        List<Widget> widgets = super.setupDisplay(display, bounds);

        AtomicInteger sourceLevel = new AtomicInteger(0);
        AtomicInteger upgradedLevel = new AtomicInteger(0);
        AtomicInteger coinCost = new AtomicInteger((int) display.getRecipe().getCoins());
        AtomicReference<String> timeCost = new AtomicReference<>(FormatterUtil.formatDuration(display.getRecipe().getDuration()));

        Pet sourcePet = Pet.fromItemId(ItemUtils.getItemId(display.getRecipe().getOldPet()));
        Pet upgradedPet = Pet.fromItemId(ItemUtils.getItemId(display.getRecipe().getOutput().get(0)));
        widgets.add(new SliderBar(new Rectangle(bounds.getCenterX() - 50, bounds.getMaxY() - 16, 100, 10), 100).addCurrentChangeListener(evt -> {
            sourceLevel.set((int) evt.getNewValue());
            sourcePet.setPetLevel(sourceLevel.get());
            upgradedPet.setPetExp(sourcePet.getPetExp());
            upgradedLevel.set(upgradedPet.getPetLevel());
            coinCost.set((int) (display.getRecipe().getCoins() * (1-0.003*sourceLevel.get())));
        }));

        Button button = Widgets.createButton(new Rectangle(bounds.getMaxX() - 16, bounds.getMaxY() - 16, 12, 12), Text.literal("I"));
        button.setTooltip(b -> {
            Text[] texts = new Text[7];
            texts[0] = Text.of("The recipe shows coin cost for a level \"0\" pet.");
            texts[1] = Text.of("This cost is reduced for each level,");
            texts[2] = Text.of("so even a level 1 pet costs less to upgrade than this.");
            texts[3] = Text.of("You can adjust the level of the source pet by sliding the slider.");

            texts[4] = Text.of(String.format("Level: %d -> %d", sourceLevel.get(), upgradedLevel.get()));
            texts[5] = Text.of("Coin cost: " + coinCost.get());
            texts[6] = Text.of("Time cost: " + timeCost.get());

            return texts;
        });
        widgets.add(button);

        long duration = display.getRecipe().getDuration();
        String durationFormatted = FormatterUtil.formatDuration(duration);
        Point labelPoint = new Point(bounds.getMaxX() - durationFormatted.length() * 3 - 14, bounds.getMaxY() - 12);
        Label craftTextLabel = Widgets.createLabel(labelPoint, Text.of(durationFormatted));
        widgets.add(craftTextLabel);

        return widgets;
    }
}
