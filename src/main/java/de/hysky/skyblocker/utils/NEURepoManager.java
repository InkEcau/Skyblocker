package de.hysky.skyblocker.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import de.hysky.skyblocker.SkyblockerMod;
import de.hysky.skyblocker.skyblock.itemlist.ItemRepository;
import de.hysky.skyblocker.utils.render.entity.modifier.EntityModifier;
import io.github.moulberry.repo.NEURepository;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * Initializes the NEU repo, which contains item metadata and fairy souls location data. Clones the repo if it does not exist and checks for updates. Use {@link #runAsyncAfterLoad(Runnable)} to run code after the repo is initialized.
 */
public class NEURepoManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(NEURepoManager.class);
    public static final String REMOTE_REPO_URL = "https://github.com/NotEnoughUpdates/NotEnoughUpdates-REPO.git";
    /**
     * Use {@link #NEU_REPO}.
     */
    private static final Path LOCAL_REPO_DIR = SkyblockerMod.CONFIG_DIR.resolve("item-repo"); // TODO rename to NotEnoughUpdates-REPO
    private static final CompletableFuture<Void> REPO_INITIALIZED = loadRepository();
    public static final NEURepository NEU_REPO = NEURepository.of(LOCAL_REPO_DIR);
    public static final Map<String, Map.Entry<String, List<EntityModifier>>> customMobs = new HashMap<>();

    /**
     * Adds command to update repository manually from ingame.
     * <p></p>
     * TODO A button could be added to the settings menu that will trigger this command.
     */
    public static void init() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(ClientCommandManager.literal(SkyblockerMod.NAMESPACE)
                        .then(ClientCommandManager.literal("updaterepository").executes(context -> {
                            deleteAndDownloadRepository();
                            return 1;
                        }))));
    }

    private static CompletableFuture<Void> loadRepository() {
        return CompletableFuture.runAsync(() -> {
            try {
                if (Files.isDirectory(NEURepoManager.LOCAL_REPO_DIR)) {
                    try (Git localRepo = Git.open(NEURepoManager.LOCAL_REPO_DIR.toFile())) {
                        localRepo.pull().setRebase(true).call();
                        LOGGER.info("[Skyblocker] NEU Repository Updated");
                    }
                } else {
                    // TODO branch change
                    Git.cloneRepository().setURI(REMOTE_REPO_URL).setDirectory(NEURepoManager.LOCAL_REPO_DIR.toFile()).setBranchesToClone(List.of("refs/heads/prerelease")).setBranch("refs/heads/prerelease").call().close();
                    LOGGER.info("[Skyblocker] NEU Repository Downloaded");
                }
                NEU_REPO.reload();
                loadMobs();
            } catch (TransportException e) {
                LOGGER.error("[Skyblocker] Transport operation failed. Most likely unable to connect to the remote NEU repo on github", e);
            } catch (RepositoryNotFoundException e) {
                LOGGER.warn("[Skyblocker] Local NEU Repository not found or corrupted, downloading new one", e);
                deleteAndDownloadRepository();
            } catch (Exception e) {
                LOGGER.error("[Skyblocker] Encountered unknown exception while initializing NEU Repository", e);
            }
        });
    }

    private static void deleteAndDownloadRepository() {
        CompletableFuture.runAsync(() -> {
            try {
                ItemRepository.setFilesImported(false);
                recursiveDelete(NEURepoManager.LOCAL_REPO_DIR);
            } catch (Exception ex) {
                if (MinecraftClient.getInstance().player != null)
                    MinecraftClient.getInstance().player.sendMessage(Constants.PREFIX.get().append(Text.translatable("skyblocker.updaterepository.failed")), false);
                return;
            }
            loadRepository();
        });
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void recursiveDelete(Path dir) throws IOException {
        if (Files.isDirectory(dir) && !Files.isSymbolicLink(dir)) {
            Files.list(dir).forEach(child -> {
                try {
                    recursiveDelete(child);
                } catch (Exception e) {
                    LOGGER.error("[Skyblocker] Encountered an exception while deleting a file! Path: {}", child.toAbsolutePath(), e);
                }
            });
        }
        Files.delete(dir);
    }

    /**
     * Runs the given runnable after the NEU repo is initialized.
     *
     * @param runnable the runnable to run
     * @return a completable future of the given runnable
     */
    public static CompletableFuture<Void> runAsyncAfterLoad(Runnable runnable) {
        return REPO_INITIALIZED.thenRunAsync(runnable);
    }

    /**
     * Load mob render data from repo/mobs
     */
    private static void loadMobs() {
        customMobs.clear();
        Path mobPath = Paths.get(LOCAL_REPO_DIR.toString(), "mobs");
        if (Files.isDirectory(mobPath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println(mobPath);
            try (Stream<Path> paths = Files.walk(mobPath)) {
                paths.filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".json"))
                        .forEach(path -> {
                            try {
                                Reader is = new InputStreamReader(new FileInputStream(path.toFile()));
                                JsonObject json = gson.fromJson(is, JsonObject.class);
                                Map.Entry<String, List<EntityModifier>> custom = Map.entry(json.get("entity").getAsString(), EntityModifier.load(json));
                                customMobs.put(path.getFileName().toString(), custom);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }

                        });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            LOGGER.warn("[Skyblocker] Failed to load mobs from neu repo: No such Directory({})", mobPath);
        }

    }
}
