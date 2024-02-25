package de.hysky.skyblocker.utils.render.entity;

import de.hysky.skyblocker.utils.NEURepoManager;
import de.hysky.skyblocker.utils.render.entity.modifier.EntityModifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class NeuEntityConstructor {

    public static LivingEntity createLivingEntity(String render) {
        if (render == null) return null;
        if (render.startsWith("@neurepo")) {
            String location = render.split(":")[1];
            if (!location.startsWith("mobs")) return null;
            String key = location.split("mobs/")[1];
            Map.Entry<String, List<EntityModifier>> entityModifiersEntry = NEURepoManager.customMobs.get(key);
            if (entityModifiersEntry == null) return null;
            AtomicReference<LivingEntity> livingEntity = new AtomicReference<>(getLivingEntityByName(entityModifiersEntry.getKey(), MinecraftClient.getInstance().world));
             if (livingEntity.get() == null) return null;
            entityModifiersEntry.getValue().forEach(entityModifier -> {
                if (entityModifier == null) return;
                livingEntity.set(entityModifier.applyModifier(livingEntity.get()));
            });
            return livingEntity.get();
        } else {
            return getLivingEntityByName(render, MinecraftClient.getInstance().world);
        }
    }

    /**
     * Get living entity by entity name supplied by neu.
     *
     * @param name  entity name
     * @param world world
     * @return a new living entity object
     */
    private static LivingEntity getLivingEntityByName(String name, ClientWorld world) {
        return switch (name) {
            case "Zombie" -> new ZombieEntity(EntityType.ZOMBIE, world);
            case "Chicken" -> new ChickenEntity(EntityType.CHICKEN, world);
            case "Slime" -> new SlimeEntity(EntityType.SLIME, world);
            case "Wolf" -> new WolfEntity(EntityType.WOLF, world);
            case "Skeleton" -> new SkeletonEntity(EntityType.SKELETON, world);
            case "Creeper" -> new CreeperEntity(EntityType.CREEPER, world);
            case "Ocelot" -> new OcelotEntity(EntityType.OCELOT, world);
            case "Blaze" -> new BlazeEntity(EntityType.BLAZE, world);
            case "Rabbit" -> new RabbitEntity(EntityType.RABBIT, world);
            case "Sheep" -> new SheepEntity(EntityType.SHEEP, world);
            case "Horse" -> new HorseEntity(EntityType.HORSE, world);
            case "Eisengolem" -> new IronGolemEntity(EntityType.IRON_GOLEM, world);
            case "Silverfish" -> new SilverfishEntity(EntityType.SILVERFISH, world);
            case "Witch" -> new WitchEntity(EntityType.WITCH, world);
            case "Endermite" -> new EndermiteEntity(EntityType.ENDERMITE, world);
            case "Snowman" -> new SnowGolemEntity(EntityType.SNOW_GOLEM, world);
            case "Villager" -> new VillagerEntity(EntityType.VILLAGER, world);
            case "Guardian" -> new GuardianEntity(EntityType.GUARDIAN, world);
            case "ArmorStand" -> new ArmorStandEntity(EntityType.ARMOR_STAND, world);
            case "Squid" -> new SquidEntity(EntityType.SQUID, world);
            case "Bat" -> new BatEntity(EntityType.BAT, world);
            case "Spider" -> new SpiderEntity(EntityType.SPIDER, world);
            case "CaveSpider" -> new CaveSpiderEntity(EntityType.CAVE_SPIDER, world);
            case "Pigman" -> new PiglinEntity(EntityType.PIGLIN, world);
            case "Ghast" -> new GhastEntity(EntityType.GHAST, world);
            case "MagmaCube" -> new MagmaCubeEntity(EntityType.MAGMA_CUBE, world);
            case "Wither" -> new WitherEntity(EntityType.WITHER, world);
            case "Enderman" -> new EndermanEntity(EntityType.ENDERMAN, world);
            case "Mooshroom" -> new MooshroomEntity(EntityType.MOOSHROOM, world);
            case "WitherSkeleton" -> new WitherSkeletonEntity(EntityType.WITHER_SKELETON, world);
            case "Cow" -> new CowEntity(EntityType.COW, world);
            case "Dragon" -> new EnderDragonEntity(EntityType.ENDER_DRAGON, world);
            case "Player" -> new GuiPlayerEntity(world);
            case "Pig" -> new PigEntity(EntityType.PIG, world);
            case "Giant" -> new GiantEntity(EntityType.GIANT, world);
            default -> null;
        };
    }
}
