package de.hysky.skyblocker.utils.render.entity.modifier;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public interface EntityModifier {
    LivingEntity applyModifier(LivingEntity entity);

    EntityModifier loadFromNeuRepo(JsonObject json);

    static List<EntityModifier> load(JsonObject json) {
        JsonElement modifiers = json.get("modifiers");
        if (modifiers == null) return List.of();
        List<EntityModifier> list = new ArrayList<>(modifiers.getAsJsonArray().size());
        modifiers.getAsJsonArray().forEach(jsonElement -> {
            if (!(jsonElement instanceof JsonObject modifierJson)) return;
            switch (modifierJson.get("type").getAsString()) {
                case "playerdata" -> list.add(new SkinModifier().loadFromNeuRepo(modifierJson));
                case "equipment" -> list.add(new EquipmentModifier().loadFromNeuRepo(modifierJson));
                case "riding" -> list.add(new RidingModifier().loadFromNeuRepo(modifierJson));
                case "charged" -> list.add(new ChargedModifier().loadFromNeuRepo(modifierJson));
                case "invisible" -> list.add(new InvisibleModifier().loadFromNeuRepo(modifierJson));
                case "age" -> list.add(new AgeModifier().loadFromNeuRepo(modifierJson));
                case "horse" -> list.add(new HorseModifier().loadFromNeuRepo(modifierJson));
            }
        });
        return list;
    }
}
