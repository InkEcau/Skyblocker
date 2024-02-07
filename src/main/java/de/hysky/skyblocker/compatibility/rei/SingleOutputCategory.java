package de.hysky.skyblocker.compatibility.rei;

import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.display.Display;

import java.util.List;

/**
 * DisplayCategory that draws an arrow and an output slot.
 */
public abstract class SingleOutputCategory<T extends Display> implements DisplayCategory<T> {
    @Override
    public List<Widget> setupDisplay(T display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 58, bounds.getCenterY() - 27);

        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        // arrow
        Point arrowPoint = new Point(startPoint.x + 60, startPoint.y + 18);
        widgets.add(Widgets.createArrow(arrowPoint));
        // result slot
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 95, startPoint.y + 19)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 95, startPoint.y + 19)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        // input
        widgets.addAll(setupInputDisplay(display, new Rectangle(
                bounds.getMinX(),
                bounds.getMinY(),
                arrowPoint.getX() - bounds.getMinX(),
                bounds.getHeight()), bounds
        ));

        return widgets;
    }

    protected abstract List<Widget> setupInputDisplay(T display, Rectangle inputBounds, Rectangle bounds);
}