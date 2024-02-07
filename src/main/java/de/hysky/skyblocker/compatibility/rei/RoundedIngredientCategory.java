package de.hysky.skyblocker.compatibility.rei;

import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;

import java.util.List;

/**
 * An abstract DisplayCategory class that implements a style where ingredients are arranged in a circular shape.
 * Looks like <a href="https://imgur.com/a/x9TXrj7">this</a>.
 * <p>
 * Note: When the input is greater than 10 need to adjust the height or choose another layout.
 */
public abstract class RoundedIngredientCategory<T extends Display> extends SingleOutputCategory<T> {

    @Override
    public List<Widget> setupInputDisplay(T display, Rectangle inputBounds, Rectangle bounds) {
        return setupInputDisplay(display, inputBounds, bounds, true);
    }

    /**
     * @param center whether to determine when there is only one item to generate slots at the center point
     */
    public List<Widget> setupInputDisplay(T display, Rectangle inputBounds, Rectangle bounds, boolean center) {

        List<EntryIngredient> input = display.getInputEntries();
        int size = input.size();
        List<Widget> widgets = Lists.newArrayList();

        if (center && size == 1) {
            // If there is only one item needed, the input slot is generated at the center point.
            Slot slot = Widgets.createSlot(new Point(inputBounds.getCenterX() - 9, inputBounds.getCenterY() - 9)).entries(input.get(0)).markInput();
            widgets.add(slot);
        } else if (size > 1) {
            // If more than 2 items are needed, generate input slots evenly spaced on the circle.
            int centerX = inputBounds.getCenterX();
            int centerY = inputBounds.getCenterY();
            int radius = Math.min(inputBounds.getHeight(), inputBounds.getWidth()) / 2 - 12;
            for (int i = 0; i < input.size(); i++) {
                double angle = 2 * Math.PI * i / input.size();
                int slotX = (int) (centerX + radius * Math.cos(angle));
                int slotY = (int) (centerY + radius * Math.sin(angle));

                Slot slot = Widgets.createSlot(new Point(slotX - 9, slotY - 9)).entries(input.get(i)).markInput();
                widgets.add(slot);
            }
        }


        return widgets;
    }
}
