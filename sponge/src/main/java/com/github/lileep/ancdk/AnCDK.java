package com.github.lileep.ancdk;

import com.github.lileep.ancdk.command.ACommand;
import com.github.lileep.ancdk.config.ConfigLoader;
import com.github.lileep.ancdk.lib.Reference;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;

@Plugin(
        id = Reference.PLUGIN_ID,
        name = Reference.PLUGIN_NAME,
        description = "test",
        version = Reference.VERSION,
        authors = {"Enron233", "Lileep"}
)
public class AnCDK {

    private static AnCDK instance;

    public static AnCDK getInstance() {
        if (instance == null) {
            instance = new AnCDK();
        }
        return instance;
    }

    @Inject
    private Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private File dir;

    public Logger getLogger() {
        return logger;
    }

    public File getConfigPath() {
        return dir;
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        instance = this;

        new ConfigLoader();

        Sponge.getCommandManager().register(getInstance(), ACommand.BASE, "ancdk", "ancdkey", "cdk", "cdkey");

        logger.info("§a我是小安，感谢你使用这款§4CDK§a插件");
        logger.info("§a适用于Sponge的§4CDK§a, 由触手百合(Lileep)重构");
        logger.info("§a插件已成功运行！");
        logger.info(" \n" +
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

    @Listener
    public void reloadListener(GameReloadEvent event) {
        ConfigLoader.getInstance().reload();
        logger.info(Reference.PREFIX + "§a插件已成功重载！");
    }
}
