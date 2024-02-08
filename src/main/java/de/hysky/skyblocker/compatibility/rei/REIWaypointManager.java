package de.hysky.skyblocker.compatibility.rei;

import de.hysky.skyblocker.config.SkyblockerConfigManager;
import de.hysky.skyblocker.utils.NEURepoManager;
import de.hysky.skyblocker.utils.waypoint.Waypoint;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

import java.util.function.Supplier;

// TODO npc waypoint https://github.com/NotEnoughUpdates/RepoParser/issues/1
public class REIWaypointManager {
    private static final Supplier<Waypoint.Type> TYPE_SUPPLIER = () -> SkyblockerConfigManager.get().general.waypoints.waypointType;
    private static Waypoint currentWaypoint;

    public static void init() {
        WorldRenderEvents.AFTER_TRANSLUCENT.register(REIWaypointManager::render);
        NEURepoManager.runAsyncAfterLoad(() -> {
        });
    }

    public static void pos(BlockPos pos) {
        if (currentWaypoint != null) {
            currentWaypoint.setFound();
        }
        currentWaypoint = new Waypoint(pos, TYPE_SUPPLIER, DyeColor.YELLOW.getColorComponents());
    }

    public static void reset() {
        if (currentWaypoint != null) {
            currentWaypoint.setFound();
        }
    }

    private static void render(WorldRenderContext context) {
        if (currentWaypoint != null) currentWaypoint.render(context);
    }
}
