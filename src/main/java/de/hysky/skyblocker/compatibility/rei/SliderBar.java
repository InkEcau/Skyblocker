package de.hysky.skyblocker.compatibility.rei;

import de.hysky.skyblocker.SkyblockerMod;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.BaseWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.util.Identifier;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Objects;

/**
 * Simple implementation of a rei widget for slider bar.
 * The range of "current" is [0,max].
 */
public class SliderBar extends BaseWidget<SliderBar> {

    private static final Identifier SLIDER_LOCATION = new Identifier(SkyblockerMod.NAMESPACE, "textures/gui/slider.png");
    private final Rectangle bounds;
    private final int max;
    private int current;
    private final PropertyChangeSupport currentChangeSupport;

    public SliderBar(Rectangle bounds, int max) {
        this.bounds = Objects.requireNonNull(bounds);
        this.max = max > 0 ? max : 10;
        this.current = 0;
        this.currentChangeSupport = new PropertyChangeSupport(this);
        addCurrentChangeListener(evt -> {
            int newValue = (int) evt.getNewValue();
            System.out.println("change: " + newValue);
            if (newValue > max) {
                setCurrent(0);
                throw new RuntimeException("Current value larger than max value: " + current + " > " + max);
            } else if(newValue < 0) {
                setCurrent(0);
                throw new RuntimeException("Current value lower than max value: " + current + "< 0");
            }
        });
    }

    public int getMax() {
        return max;
    }

    public int getCurrent() {
        return current;
    }

    public SliderBar setCurrent(int current) {
        int old = this.current;
        this.current = current;
        currentChangeSupport.firePropertyChange("current", old, current);
        return this;
    }

    public SliderBar addCurrentChangeListener(PropertyChangeListener listener) {
        currentChangeSupport.addPropertyChangeListener(listener);
        return this;
    }

    public SliderBar removeCurrentChangeListener(PropertyChangeListener listener) {
        currentChangeSupport.removePropertyChangeListener(listener);
        return this;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int x = bounds.x, y = bounds.y, width = bounds.width;
        // bar
        context.drawTexture(SLIDER_LOCATION, x, y, 1, 10, 0, 0, 1, 10, 10, 10);
        context.drawTexture(SLIDER_LOCATION, x + 1, y, width - 2, 10, 1, 0, 1, 10, 10, 10);
        context.drawTexture(SLIDER_LOCATION, x + width - 1, y, 1, 10, 2, 0, 1, 10, 10, 10);
        // slider
        context.drawTexture(SLIDER_LOCATION, (int) Math.round(x + (double) current / max * width) - 3, y, 6, 10, 4, 0, 6, 10, 10, 10);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        if (containsMouse(mouseX, mouseY) && button == 0) {
            setCurrent((int) Math.round((mouseX - bounds.x + 3) / bounds.width * max));
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        int newValue = current - (int) verticalAmount;
        if (containsMouse(mouseX, mouseY) && newValue >= 0 && newValue <= max) {
            setCurrent(newValue);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        super.keyPressed(keyCode, scanCode, modifiers);
        if (keyCode == 263 && current >= 1) {
            setCurrent(current-1);
        } else if (keyCode == 262 && current + 1 <= max) {
            setCurrent(current+1);
        }
        return true;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public List<? extends Element> children() {
        return List.of();
    }
}
