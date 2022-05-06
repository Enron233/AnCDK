package com.github.lileep.ancdk.handler;

import com.github.lileep.ancdk.AnCDK;
import com.github.lileep.ancdk.config.ConfigLoader;
import com.github.lileep.ancdk.handler.api.AbstractCDKHandler;
import com.github.lileep.ancdk.util.CSVUtil;
import javafx.util.Pair;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CDKDbHandler extends AbstractCDKHandler {

    @Override
    public boolean runCDKandLog(String cdkey, Player player) {
        Pair<String, Boolean> getRes = DatabaseHandler.getInstance().getCommandByCDK(cdkey);
        String command = getRes.getKey();
        boolean once = getRes.getValue();
        if ("".equals(command)) {
            return false;
        }
        if (once) {
            if (DatabaseHandler.getInstance().del(cdkey) <= 0) {
                return false;
            }
        } else {
            //check
            if (DatabaseHandler.getInstance().checkPlayerNameAndCDK(cdkey, player.getName()) > 0) {
                return false;
            }

            //insert
            if (DatabaseHandler.getInstance().insert(cdkey, player.getName()) <= 0) {
                return false;
            }
        }
        String[] commands = command.replace("{player}", player.getName()).split(";");
        for (String s : commands) {
            runCDKCmd(s, player);
        }
        return true;
    }

    @Override
    public boolean createCDK(String command, int count, boolean once, String executor) {
        List<String> keyList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            keyList.add(genCDK());
        }
        if (ConfigLoader.getInstance().getRootNode().getNode("General", "useLog").getBoolean()) {
            setLog(executor, "create", keyList);
        }
        return DatabaseHandler.getInstance().create(command, once, keyList) > 0;
    }

    @Override
    public boolean exportCDK() {
        Set<Pair<String, String>> getRes = DatabaseHandler.getInstance().getAllCDKsAndCommands();
        ConfigurationNode exportNode = ConfigLoader.getInstance().getExportNode();

        Map<String, Integer> counter = new LinkedHashMap<>();
        for (Pair<String, String> res : getRes) {
            String cdk = res.getKey();
            String command = res.getValue();
            counter.merge(command, 1, Integer::sum);
            exportNode.getNode(command, counter.get(command).toString()).setValue(cdk);
        }
        try {
            ConfigLoader.getInstance().getExportLoader().save(exportNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean exportCSV() {
        Set<String> cdkSet = DatabaseHandler.getInstance().getAllCDKs();
        return CSVUtil.writeCSVFileData(new File(AnCDK.getInstance().getConfigPath(), "export.csv"), cdkSet);
    }
}
