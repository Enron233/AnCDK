package com.github.lileep.ancdk.config;

import com.github.lileep.ancdk.AnCDK;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {
    private static ConfigLoader instance;

    public static ConfigLoader getInstance() {
        if (instance == null) {
            instance = new ConfigLoader();
        }
        return instance;
    }

    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private ConfigurationLoader<CommentedConfigurationNode> langLoader;
    private ConfigurationLoader<CommentedConfigurationNode> cdkLoader;
    private ConfigurationLoader<CommentedConfigurationNode> exportLoader;

    private ConfigurationNode rootNode;
    private ConfigurationNode langNode;
    private ConfigurationNode cdkNode;
    private ConfigurationNode exportNode;

    private boolean useDB = false;
    private File logFile;

    public ConfigurationNode getRootNode() {
        return this.rootNode;
    }

    public ConfigurationNode getLangNode() {
        return langNode;
    }

    public ConfigurationLoader<CommentedConfigurationNode> getCdkLoader() {
        return this.cdkLoader;
    }

    public ConfigurationNode getCdkNode() {
        return this.cdkNode;
    }

    public ConfigurationLoader<CommentedConfigurationNode> getExportLoader() {
        return this.exportLoader;
    }

    public ConfigurationNode getExportNode() {
        return this.exportNode;
    }

    public boolean isUseDB() {
        return useDB;
    }

    public File getLogFile() {
        return logFile;
    }

    public void reload() {
        try {
            rootNode = loader.load();
            loader.save(rootNode);

            langNode = langLoader.load();
            langLoader.save(langNode);

            if (!useDB) {
                cdkNode = cdkLoader.load();
                cdkLoader.save(cdkNode);
            }

            exportNode = exportLoader.load();
            exportLoader.save(exportNode);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ConfigLoader() {
        instance = this;
        File configPath = AnCDK.getInstance().getConfigPath();
        if (!configPath.exists()) {
            if (!configPath.mkdir()) {
                return;
            }
        }
        try {
            //Load blank config
            Sponge.getAssetManager()
                    .getAsset(AnCDK.getInstance().getPluginContainer(), "ancdk.conf")
                    .get()
                    .copyToDirectory(configPath.toPath());

            loader = HoconConfigurationLoader
                    .builder()
                    .setFile(new File(configPath, "ancdk.conf"))
                    .build();
            rootNode = loader.load();
            //Auto add new settings
            rootNode.mergeValuesFrom(
                    HoconConfigurationLoader.builder()
                            .setURL(AnCDK.getInstance().getPluginContainer().getAsset("ancdk.conf").get().getUrl())
                            .build().load()
            );
            loader.save(rootNode);

            Sponge.getAssetManager()
                    .getAsset(AnCDK.getInstance().getPluginContainer(), "lang/language.conf")
                    .get()
                    .copyToDirectory(configPath.toPath());
            langLoader = HoconConfigurationLoader
                    .builder()
                    .setFile(new File(configPath, "language.conf"))
                    .build();
            langNode = langLoader.load();
            langLoader.save(langNode);

            if (rootNode.getNode("Database", "useDatabase").getBoolean()) {
                useDB = true;
            } else {
                cdkLoader = HoconConfigurationLoader
                        .builder()
                        .setFile(new File(configPath, "cdks.conf"))
                        .build();
                cdkNode = cdkLoader.load();
                cdkLoader.save(cdkNode);
            }

            exportLoader = HoconConfigurationLoader
                    .builder()
                    .setFile(new File(configPath, "export.conf"))
                    .build();
            exportNode = exportLoader.load();
            exportLoader.save(exportNode);

            logFile = new File(configPath, "info.log");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
