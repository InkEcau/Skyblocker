package de.hysky.skyblocker.utils.render.entity.modifier;

import com.google.gson.JsonObject;
import de.hysky.skyblocker.utils.render.entity.GuiPlayerEntity;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class SkinModifier implements EntityModifier {
    private Identifier skin;
    private Identifier cape;
    private SkinTextures.Model model;

    protected SkinModifier() {
    }

    private SkinModifier(Identifier skin, Identifier cape, SkinTextures.Model model) {
        this.skin = skin;
        this.cape = cape;
        this.model = model;
    }

    @Override
    public LivingEntity applyModifier(LivingEntity entity) {
        if (entity instanceof GuiPlayerEntity player) {
            if (skin != null) player.setSkin(skin);
            if (cape != null) player.setCape(cape);
            if (model != null) player.setModel(model);
        }
        return null;
    }

    @Override
    public EntityModifier loadFromNeuRepo(JsonObject json) {
        return null;
    }
}
