package de.hysky.skyblocker.utils.render.entity;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class GuiPlayerEntity extends AbstractClientPlayerEntity {

    private SkinTextures skinTextures;

    public GuiPlayerEntity(ClientWorld world) {
        super(world, new GameProfile(UUID.randomUUID(), "GuiPlayerEntity"));
        this.skinTextures = new SkinTextures(new Identifier("textures/entity/player/wide/steve.png"), null, null, null, SkinTextures.Model.WIDE, true);
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return null;
            }

            @Override
            public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
                return null;
            }
        });
    }

    @Override
    public SkinTextures getSkinTextures() {
        return skinTextures;
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }

    public GuiPlayerEntity setSkin(Identifier identifier) {
        this.skinTextures = new SkinTextures(identifier, skinTextures.textureUrl(), skinTextures.capeTexture(), skinTextures.elytraTexture(), skinTextures.model(), skinTextures.secure());
        return this;
    }

    public GuiPlayerEntity setModel(SkinTextures.Model model) {
        this.skinTextures = new SkinTextures(skinTextures.texture(), skinTextures.textureUrl(), skinTextures.capeTexture(), skinTextures.elytraTexture(), model, skinTextures.secure());
        return this;
    }
    public GuiPlayerEntity setCape(Identifier identifier) {
        this.skinTextures = new SkinTextures(skinTextures.texture(), skinTextures.textureUrl(), identifier, skinTextures.elytraTexture(), skinTextures.model(), skinTextures.secure());
        return this;
    }

}
