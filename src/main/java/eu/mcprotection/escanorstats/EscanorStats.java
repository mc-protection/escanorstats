package eu.mcprotection.escanorstats;

import com.google.common.io.ByteStreams;
import eu.mcprotection.escanorstats.commands.StatsCommand;
import lombok.Getter;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import xyz.yooniks.escanorproxy.EscanorProxyStatistics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public enum EscanorStats {
  PLUGIN;

  @Getter
  private EscanorStatsPlugin plugin;
  @Getter
  private BungeeAudiences adventure;

  @Getter
  private Configuration configuration;
  @Getter
  private EscanorProxyStatistics statistics;
  @Getter
  private ProxyServer proxyServer;
  private PluginManager pluginManager;

  @Getter
  private ScheduledExecutorService scheduledService;

  /**
   * Set the plugin instance.
   *
   * @param plugin the plugin - {@link EscanorStatsPlugin} - instance
   */
  public void load(@NotNull final EscanorStatsPlugin plugin) {
    this.plugin = plugin;
  }

  /**
   * Register all commands and listeners when the plugin is enabled.
   */
  public void start() {
    this.init();
    this.proxyServer.getLogger().info("EscanorStats plugin has been started!");
  }

  /**
   * Send a message to the console when the plugin is stopped.
   */
  public void stop() {
    if (this.adventure != null) {
      this.adventure.close();
      this.adventure = null;
    }
    this.proxyServer.getLogger().info("EscanorStats plugin has been stopped!");
  }

  /**
   * Initialize everything.
   */
  private void init() {
    try {
      this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.loadResource("config.yml"));
    } catch (IOException exception) {
      System.out.println("[EscanorStats]: Could not load configuration file!");
      throw new RuntimeException(exception);
    }
    this.registerCommands();

    this.adventure = BungeeAudiences.create(this.getPlugin());
    this.statistics = new EscanorProxyStatistics();
    this.proxyServer = this.plugin.getProxy();
    this.pluginManager = this.proxyServer.getPluginManager();

    this.scheduledService = Executors.newSingleThreadScheduledExecutor();
  }

  /**
   * Register all commands.
   */
  private void registerCommands() {
    this.pluginManager.registerCommand(this.getPlugin(), new StatsCommand());
  }

  private File loadResource(@NotNull final String resource) {
    final File folder = this.plugin.getDataFolder();
    if (!folder.exists()) {
      folder.mkdir();
    }

    final File resourceFile = new File(folder, resource);
    try {
      if (!resourceFile.exists()) {
        resourceFile.createNewFile();
        try (InputStream in = this.plugin.getResourceAsStream(resource);
             OutputStream out = new FileOutputStream(resourceFile)) {
          ByteStreams.copy(in, out);
        }
      }
    } catch (Exception exception) {
      this.plugin.getProxy().getLogger().warning("Could not load resource file: " + resource);
      throw new RuntimeException(exception);
    }
    return resourceFile;
  }
}