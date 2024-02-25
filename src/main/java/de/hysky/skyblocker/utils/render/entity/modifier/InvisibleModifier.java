package de.hysky.skyblocker.utils.render.entity.modifier;

import com.google.gson.JsonObject;
import net.minecraft.entity.LivingEntity;

public class InvisibleModifier implements EntityModifier{

    protected InvisibleModifier() {
    }

    @Override
    public LivingEntity applyModifier(LivingEntity entity) {
        entity.setInvisible(true);
        return entity;
    }

    @Override
    public EntityModifier loadFromNeuRepo(JsonObject json) {
        return this;
    }

}