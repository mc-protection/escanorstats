package eu.mcprotection.escanorstats.utils;

import eu.mcprotection.escanorstats.EscanorStats;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@UtilityClass
public class ConfigUtil {
  public static String getString(@NotNull final String path) {
    return EscanorStats.PLUGIN.getConfiguration().getString(path);
  }

  public static int getInteger(@NotNull final String path) {
    return EscanorStats.PLUGIN.getConfiguration().getInt(path);
  }

  public static boolean getBoolean(@NotNull final String path) {
    return EscanorStats.PLUGIN.getConfiguration().getBoolean(path);
  }

  public static List<String> getStringList(@NotNull final String path) {
    return EscanorStats.PLUGIN.getConfiguration().getStringList(path);
  }
}