package de.hysky.skyblocker.skyblock.item;

import de.hysky.skyblocker.utils.NEURepoManager;
import io.github.moulberry.repo.constants.PetLevelingBehaviourOverride;
import io.github.moulberry.repo.constants.PetLevelingData;

import java.util.*;

public class Pet {
    private final String petId;
    private final SkyblockItemRarity rarity;
    private long petExp;

    public static final Map<SkyblockItemRarity, Integer> levelStartOffset = new HashMap<>();
    public static final List<Integer> petExpCostForLevel = new ArrayList<>(119);
    public static final Map<String, CustomPetLeveling> customPetLeveling = new HashMap<>();
    public static final Map<String, String> petType = new HashMap<>();

    public Pet(String petId, SkyblockItemRarity rarity, long petExp) {
        this.petId = petId;
        this.rarity = rarity;
        this.petExp = petExp;
    }

    public String getPetId() {
        return petId;
    }

    public SkyblockItemRarity getRarity() {
        return rarity;
    }

    public long getPetExp() {
        return petExp;
    }

    public Pet setPetExp(long petExp) {
        this.petExp = petExp;
        return this;
    }

    public int getPetLevel() {
        return getPetLevel(rarity, petExp, petId);
    }

    public Pet setPetLevel(int level) {
        this.petExp = getPetExp(rarity, level, petId);
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pet pet)) return false;
        return this.petId.equals(pet.petId) && this.petExp == pet.petExp && this.rarity.equals(((Pet) obj).rarity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.petId, this.petExp, this.rarity);
    }

    /**
     * Calculates pet experience based on specified rarity and level.
     *
     * @param rarity pet rarity
     * @param level  pet level
     * @param petId  pet id(without rarity)
     * @return pet experience
     */
    private static long getPetExp(SkyblockItemRarity rarity, int level, String petId) {
        int offset;
        List<Integer> expCostForLevel;
        int maxLevel;
        CustomPetLeveling custom = customPetLeveling.get(petId);
        if (custom == null) {
            offset = levelStartOffset.get(rarity);
            expCostForLevel = petExpCostForLevel;
            maxLevel = 100;
        } else {
            offset = custom.levelStartOffset().get(rarity);
            expCostForLevel = custom.petExpCostForLevel();
            maxLevel = custom.maxLevel();
        }
        long result = 0;
        for (int i = offset; i < maxLevel - 1 + offset && i < level + offset-1; i++) result += expCostForLevel.get(i);
        return result;
    }

    /**
     * Calculates pet level based on specified rarity and experience.
     *
     * @param rarity     pet rarity
     * @param experience pet experience
     * @param petId      pet id(without rarity)
     * @return pet level
     */
    private static int getPetLevel(SkyblockItemRarity rarity, long experience, String petId) {
        int offset;
        List<Integer> expCostForLevel;
        int maxLevel;
        CustomPetLeveling custom = customPetLeveling.get(petId);
        if (custom == null) {
            offset = levelStartOffset.get(rarity);
            expCostForLevel = petExpCostForLevel;
            maxLevel = 100;
        } else {
            offset = custom.levelStartOffset().get(rarity);
            expCostForLevel = custom.petExpCostForLevel();
            maxLevel = custom.maxLevel();
        }
        int result = 1;
        for (int i = offset; i < maxLevel - 1 + offset; result++, i++) {
            experience -= expCostForLevel.get(i);
            if (experience < 0) break;
        }
        return result;
    }

    public static Pet fromItemId(String itemId) {
        return fromItemId(itemId, 0);
    }

    /**
     * Create pet object from item ID.
     * If provided itemId doesn't contain item rarity number, then find an item that matches item ID.
     *
     * @param itemId item ID
     * @param petExp pet experience(not level)
     * @return pet object
     * @throws RuntimeException petExp < 0 or itemId is null or no item ID matched or unknown rarity ID.
     */
    public static Pet fromItemId(String itemId, long petExp) {
        if (petExp < 0) {
            throw new RuntimeException("Too low petExp: " + petExp);
        }
        if (itemId == null) {
            throw new RuntimeException("Item ID can't be null.");
        }
        String[] split = itemId.split(";");
        if (split.length < 2) {
            String[] finalSplit = split;
            Optional<String> fullItemId = NEURepoManager.NEU_REPO.getItems().getItems().keySet().stream().filter(id -> {
                String[] s = id.split(";");
                return s.length > 1 && s[0].equals(finalSplit[0]);
            }).findAny();
            if (fullItemId.isEmpty()) throw new RuntimeException("Can't find item: " + itemId);
            split = fullItemId.get().split(";");
        } else if (NEURepoManager.NEU_REPO.getItems().getItems().keySet().stream().noneMatch(s -> s.equals(itemId))) {
            throw new RuntimeException("Can't find item: " + itemId);
        }
        int rarityId;
        try {
            rarityId = Integer.parseInt(split[1]);
            if (rarityId < 0 || rarityId > 5) {
                throw new RuntimeException(String.format("Can't find rarity: %s (%s)", split[1], itemId));
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(String.format("Can't find rarity: %s (%s)", split[1], itemId));
        }
        return new Pet(split[0], SkyblockItemRarity.fromId(rarityId), petExp);
    }

    public static void init() {
        NEURepoManager.runAsyncAfterLoad(() -> {
            PetLevelingData petLevelingData = NEURepoManager.NEU_REPO.getConstants().getPetLevelingData();
            // offset
            petLevelingData.getPetLevelStartOffset().forEach((rarity, integer) -> levelStartOffset.put(SkyblockItemRarity.fromNEURarity(rarity), integer));
            // exp cost
            petExpCostForLevel.addAll(petLevelingData.getPetExpCostForLevel());
            // custom
            petLevelingData.getPetLevelingBehaviourOverrides().forEach((petId, custom) -> {
                // offset
                Map<SkyblockItemRarity, Integer> levelStartOffset = new HashMap<>();
                if (custom.getPetLevelStartOffset() == null) {
                    levelStartOffset.putAll(Pet.levelStartOffset);
                } else {
                    custom.getPetLevelStartOffset().forEach((rarity, offset) -> levelStartOffset.put(SkyblockItemRarity.fromNEURarity(rarity), offset));
                }
                // exp cost
                List<Integer> petExpCostModifier = custom.getPetExpCostModifier();
                List<Integer> petExpCostForLevel = new ArrayList<>(Pet.petExpCostForLevel.size() + petExpCostModifier.size());
                PetLevelingBehaviourOverride.PetExpModifierType petExpCostModifierType = custom.getPetExpCostModifierType();
                if (Objects.requireNonNull(petExpCostModifierType) == PetLevelingBehaviourOverride.PetExpModifierType.APPEND) {
                    petExpCostForLevel.addAll(Pet.petExpCostForLevel);
                    petExpCostForLevel.addAll(petExpCostModifier);
                } else if (petExpCostModifierType == PetLevelingBehaviourOverride.PetExpModifierType.REPLACE) {
                    petExpCostForLevel.addAll(petExpCostModifier);
                } else {
                    petExpCostForLevel.addAll(Pet.petExpCostForLevel);
                }
                customPetLeveling.put(petId, new CustomPetLeveling(
                        custom.getXpMultiplier() == null ? 1 : custom.getXpMultiplier(),
                        levelStartOffset,
                        custom.getMaxLevel() == null ? 100 : custom.getMaxLevel(),
                        petExpCostForLevel
                ));
            });
            // pet type
            petType.putAll(petLevelingData.getPetExpTypes());
        });
    }

    public record CustomPetLeveling(double xpMultiplier,
                                    Map<SkyblockItemRarity, Integer> levelStartOffset,
                                    int maxLevel,
                                    List<Integer> petExpCostForLevel) {
    }
}
