package com.xiaoan.ancdk;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public final class AnCDK extends JavaPlugin {
    public File file = new File(getDataFolder(), "Export.yml");
    public File used = new File(getDataFolder(), "Log.yml");
    public static YamlConfiguration filec,uselog;
    @Override
    public void onEnable() {
        ins = this;
        saveDefaultConfig();
        if (!file.exists()){
            try {
                Boolean resp = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!used.exists()){
            try {
                Boolean usedNewFile = used.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        filec = YamlConfiguration.loadConfiguration(file);
        uselog = YamlConfiguration.loadConfiguration(used);
        Bukkit.getPluginCommand("ancdk").setExecutor(new Acommand());
        int pluginId = 14378; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));
        this.getLogger().info("§a我是小安，感谢你使用这款§4CDK§a插件");
        this.getLogger().info("§a插件已经成功运行！");
        this.getLogger().info(" \n" +
                "§c.----------------.  .-----------------. .----------------.  .----------------.  .----------------. \n" +
                "§6| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |\n" +
                "§e| |      __      | || | ____  _____  | || |     ______   | || |  ________    | || |  ___  ____   | |\n" +
                "§a| |     /  \\     | || ||_   \\|_   _| | || |   .' ___  |  | || | |_   ___ `.  | || | |_  ||_  _|  | |\n" +
                "§9| |    / /\\ \\    | || |  |   \\ | |   | || |  / .'   \\_|  | || |   | |   `. \\ | || |   | |_/ /    | |\n" +
                "§5| |   / ____ \\   | || |  | |\\ \\| |   | || |  | |         | || |   | |    | | | || |   |  __'.    | |\n" +
                "§c| | _/ /    \\ \\_ | || | _| |_\\   |_  | || |  \\ `.___.'\\  | || |  _| |___.' / | || |  _| |  \\ \\_  | |\n" +
                "§6| ||____|  |____|| || ||_____|\\____| | || |   `._____.'  | || | |________.'  | || | |____||____| | |\n" +
                "§e| |              | || |              | || |              | || |              | || |              | |\n" +
                "§a| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |\n" +
                "§9 '----------------'  '----------------'  '----------------'  '----------------'  '----------------' ");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    private static AnCDK ins;
    public static AnCDK getIns(){
        return ins;
    }
}
