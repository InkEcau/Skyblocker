package de.hysky.skyblocker.utils.render.entity.modifier;

import com.google.gson.JsonObject;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.nbt.NbtByte;
import net.minecraft.nbt.NbtCompound;

public class ChargedModifier implements EntityModifier{
    
    protected ChargedModifier() {
    }

    @Override
    public LivingEntity applyModifier(LivingEntity entity) {
        if (entity instanceof CreeperEntity creeper) {
            NbtCompound nbt = new NbtCompound();
            nbt.put("powered", NbtByte.of(true));
            creeper.readCustomDataFromNbt(nbt);
        }
        return entity;
    }

    @Override
    public EntityModifier loadFromNeuRepo(JsonObject json) {
        return this;
    }

}