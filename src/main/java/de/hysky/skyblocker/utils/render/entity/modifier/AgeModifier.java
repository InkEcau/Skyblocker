package de.hysky.skyblocker.utils.render.entity.modifier;

import com.google.gson.JsonObject;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.nbt.NbtByte;
import net.minecraft.nbt.NbtCompound;

public class AgeModifier implements EntityModifier{

    private boolean isBaby;

    protected AgeModifier() {
    }

    @Override
    public LivingEntity applyModifier(LivingEntity entity) {
        if (entity instanceof ZombieEntity zombieEntity) zombieEntity.setBaby(isBaby);
        else if (entity instanceof PiglinEntity piglinEntity) piglinEntity.setBaby(isBaby);
        else if (entity instanceof ArmorStandEntity armorStand){
            NbtCompound nbt = new NbtCompound();
            nbt.put("Small", NbtByte.of(true));
            armorStand.readCustomDataFromNbt(nbt);
        }
        return entity;
    }

    @Override
    public EntityModifier loadFromNeuRepo(JsonObject json) {
        isBaby = "baby".equals(json.get("baby").getAsString());
        return this;   }
}