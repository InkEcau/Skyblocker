package de.hysky.skyblocker.utils.render.entity.modifier;

import com.google.gson.JsonObject;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.mob.ZombieHorseEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.DonkeyEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.MuleEntity;

public class HorseModifier implements EntityModifier {

    private String kind;
    private String armor;

    protected HorseModifier() {
    }

    @Override
    public LivingEntity applyModifier(LivingEntity entity) {
        if (entity instanceof HorseEntity horse) {
            AbstractHorseEntity newHorse = null;
            if (kind.equals("skeleton")) newHorse = new SkeletonHorseEntity(EntityType.SKELETON_HORSE, horse.getWorld());
            else if (kind.equals("zombie")) newHorse = new ZombieHorseEntity(EntityType.ZOMBIE_HORSE, horse.getWorld());
            else if (kind.equals("mule")) newHorse = new MuleEntity(EntityType.MULE, horse.getWorld());
            else if (kind.equals("donkey")) newHorse = new DonkeyEntity(EntityType.DONKEY, horse.getWorld());
            else if (kind.equals("horse")) newHorse = new HorseEntity(EntityType.HORSE, horse.getWorld());
            if (newHorse == null) return horse;
//            switch (armor) {
////                case "iron" -> newHorse.equipHorseArmor();
//            }
            return newHorse;
        } else return entity;
    }

    @Override
    public EntityModifier loadFromNeuRepo(JsonObject json) {
        if (json.has("kind")) kind = json.get("kind").getAsString();
        if (json.has("armor")) armor = json.get("armor").getAsString();
        return this;
    }

}