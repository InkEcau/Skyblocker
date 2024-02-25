package de.hysky.skyblocker.utils.render.entity.modifier;

import com.google.gson.JsonObject;
import de.hysky.skyblocker.skyblock.itemlist.ItemStackBuilder;
import de.hysky.skyblocker.utils.NEURepoManager;
import io.github.moulberry.repo.data.NEUItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class EquipmentModifier implements EntityModifier{


    private ItemStack hand;
    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack feet;

    protected EquipmentModifier() {
    }

    @Override
    public LivingEntity applyModifier(LivingEntity entity) {

        if (hand != null) entity.equipStack(EquipmentSlot.MAINHAND, hand);
        if (helmet != null) entity.equipStack(EquipmentSlot.HEAD, helmet);
        if (chestplate != null) entity.equipStack(EquipmentSlot.CHEST, chestplate);
        if (leggings != null) entity.equipStack(EquipmentSlot.LEGS, leggings);
        if (feet != null) entity.equipStack(EquipmentSlot.FEET, feet);
        return null;
    }

    @Override
    public EntityModifier loadFromNeuRepo(JsonObject json) {
        if (json.has("hand")) {
            String hand = json.get("hand").getAsString();
            NEUItem neuItem = NEURepoManager.NEU_REPO.getItems().getItemBySkyblockId(hand);
            if (neuItem != null) this.hand = ItemStackBuilder.fromNEUItem(neuItem);
        }
        if (json.has("helmet")) {
            String helmet = json.get("helmet").getAsString();
            NEUItem neuHelmet = NEURepoManager.NEU_REPO.getItems().getItemBySkyblockId(helmet);
            if (neuHelmet != null) this.helmet = ItemStackBuilder.fromNEUItem(neuHelmet);
        }
        if (json.has("chestplate")) {
            String chestplate = json.get("chestplate").getAsString();
            NEUItem neuChestplate = NEURepoManager.NEU_REPO.getItems().getItemBySkyblockId(chestplate);
            if (neuChestplate != null) this.chestplate = ItemStackBuilder.fromNEUItem(neuChestplate);
        }
        if (json.has("leggings")) {
            String leggings = json.get("leggings").getAsString();
            NEUItem neuLeggings = NEURepoManager.NEU_REPO.getItems().getItemBySkyblockId(leggings);
            if (neuLeggings != null) this.leggings = ItemStackBuilder.fromNEUItem(neuLeggings);
        }
        if (json.has("feet")) {
            String feet = json.get("feet").getAsString();
            NEUItem neuFeet = NEURepoManager.NEU_REPO.getItems().getItemBySkyblockId(feet);
            if (neuFeet != null) this.feet = ItemStackBuilder.fromNEUItem(neuFeet);
        }
        return this;
    }
}
