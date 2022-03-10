package eu.mcprotection.escanorstats.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class StatsCommand extends Command {
  public StatsCommand() {
    super("stats", "escanorstats.stats", "escanorstats");
  }

  @Override
  public void execute(CommandSender commandSender, String[] strings) {
    commandSender.sendMessage("§cEscanorStats is currently under development.");
  }
}
