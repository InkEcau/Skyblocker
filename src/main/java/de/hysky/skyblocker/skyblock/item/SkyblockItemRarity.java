package de.hysky.skyblocker.skyblock.item;

import io.github.moulberry.repo.data.Rarity;
import net.minecraft.util.Formatting;

public enum SkyblockItemRarity {
	ADMIN(Formatting.DARK_RED),
	ULTIMATE(Formatting.DARK_RED),
	VERY_SPECIAL(Formatting.RED),
	SPECIAL(Formatting.RED),
	DIVINE(Formatting.AQUA),
	MYTHIC(Formatting.LIGHT_PURPLE),
	LEGENDARY(Formatting.GOLD),
	EPIC(Formatting.DARK_PURPLE),
	RARE(Formatting.BLUE),
	UNCOMMON(Formatting.GREEN),
	COMMON(Formatting.WHITE);

	public final float r;
	public final float g;
	public final float b;

	SkyblockItemRarity(Formatting formatting) {
		@SuppressWarnings("DataFlowIssue")
		int rgb = formatting.getColorValue();

		this.r = ((rgb >> 16) & 0xFF) / 255f;
		this.g = ((rgb >> 8) & 0xFF) / 255f;
		this.b = (rgb & 0xFF) / 255f;
	}

	public static SkyblockItemRarity fromNEURarity(Rarity rarity) {
		return switch (rarity) {
			case COMMON -> COMMON;
            case UNCOMMON -> UNCOMMON;
            case RARE -> RARE;
            case EPIC -> EPIC;
            case LEGENDARY -> LEGENDARY;
            case MYTHIC -> MYTHIC;
            case DIVINE -> DIVINE;
            case SPECIAL -> SPECIAL;
            case VERY_SPECIAL -> VERY_SPECIAL;
			case SUPREME, UNKNOWN -> null;
        };
	}

	/**
	 * Get rarity object by rarity ID;
	 * Note: This is for pet, the code was written with no rarity IDs found elsewhere for the time being.
	 * e.g. ENDERMAN;0 -> COMMON
	 * @param id rarity id [0,5]
	 * @return rarity object
	 */
	public static SkyblockItemRarity fromId(int id) {
		return switch (id) {
			case 0 -> COMMON;
			case 1 -> UNCOMMON;
			case 2 -> RARE;
			case 3 -> EPIC;
			case 4 -> LEGENDARY;
			case 5 -> MYTHIC;
            default -> throw new IllegalStateException("Unexpected value: " + id);
        };
	}
}
