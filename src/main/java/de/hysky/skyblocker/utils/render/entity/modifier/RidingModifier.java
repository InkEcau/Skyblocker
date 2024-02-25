package de.hysky.skyblocker.utils.render.entity.modifier;

import com.google.gson.JsonObject;
import de.hysky.skyblocker.utils.render.entity.NeuEntityConstructor;
import net.minecraft.entity.LivingEntity;

import java.util.List;

/**
 * TODO Fails to render correctly, only renders itself (not passengers/vehicles), may need to adjust rendering to render correctly
 */
public class RidingModifier implements EntityModifier {

    private String render;
    private List<EntityModifier> modifiers;

    protected RidingModifier() {
    }

    @Override
    public LivingEntity applyModifier(LivingEntity entity) {
        if (render == null) return entity;
        LivingEntity livingEntity = NeuEntityConstructor.createLivingEntity(render);
        modifiers.forEach(entityModifier -> entityModifier.applyModifier(livingEntity));
        livingEntity.startRiding(entity);
        return entity;
    }

    @Override
    public EntityModifier loadFromNeuRepo(JsonObject json) {
        this.render = json.get("entity").getAsString();
        this.modifiers = EntityModifier.load(json);
        return this;
    }
}