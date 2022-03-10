package eu.mcprotection.escanorstats;

import net.md_5.bungee.api.plugin.Plugin;

public class EscanorStatsPlugin extends Plugin {
  @Override
  public void onLoad() {
    EscanorStats.PLUGIN.load(this);
  }

  @Override
  public void onEnable() {
    EscanorStats.PLUGIN.start();
  }

  @Override
  public void onDisable() {
    EscanorStats.PLUGIN.stop();
  }
}