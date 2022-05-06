package com.github.lileep.ancdk.handler;

import com.github.lileep.ancdk.AnCDK;
import com.github.lileep.ancdk.config.ConfigLoader;
import com.github.lileep.ancdk.handler.api.AbstractCDKHandler;
import com.github.lileep.ancdk.util.CSVUtil;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CDKCfgHandler extends AbstractCDKHandler {

    private static TypeToken setToken = new TypeToken<Set<String>>() {
    };

    private static Set<String> emptySet = new HashSet<>();

    @Override
    public boolean runCDKandLog(String cdkey, Player player) {
        ConfigurationNode node = ConfigLoader.getInstance().getCdkNode();
        if (Optional.ofNullable(node.getNode(cdkey).getString()).isPresent()) {
            try {
                if (Optional.ofNullable(node.getNode(cdkey, "usedPlayer").getValue(setToken)).isPresent()) {
                    Set<String> tempSet = (HashSet<String>) node.getNode(cdkey, "usedPlayer").getValue(setToken);
                    if (tempSet.contains(player.getName())) {
                        return false;
                    }
                    tempSet.add(player.getName());
                    node.getNode(cdkey, "usedPlayer").setValue(tempSet);
                } else {
                    node.getNode(cdkey).setValue(null);
                }
                runCDKCmd(ConfigLoader.getInstance().getCdkNode().getNode(cdkey, "command").getString().replace("{player}", player.getName()), player);
                ConfigLoader.getInstance().getCdkLoader().save(node);
                if (ConfigLoader.getInstance().getRootNode().getNode("General", "useLog").getBoolean()) {
                    setLog(player.getName(), "use", cdkey);
                }
            } catch (IOException | ObjectMappingException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean createCDK(String command, int count, boolean once, String executor) {
        ConfigurationNode node = ConfigLoader.getInstance().getCdkNode();
        try {
            if (ConfigLoader.getInstance().getRootNode().getNode("General", "useLog").getBoolean()) {
                List<String> keyList = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    String key = genCDK();
                    node.getNode(key, "command").setValue(command);
                    if (!once) {
                        node.getNode(key, "usedPlayer").setValue(setToken, emptySet);
                    }
                    keyList.add(key);
                }
                setLog(executor, "create", keyList);
            } else {
                for (int i = 0; i < count; i++) {
                    String key = genCDK();
                    node.getNode(key, "command").setValue(command);
                    if (!once) {
                        node.getNode(key, "usedPlayer").setValue(setToken, emptySet);
                    }
                }
            }
            ConfigLoader.getInstance().getCdkLoader().save(node);
        } catch (IOException | ObjectMappingException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean exportCDK() {
//        AnCDK.getInstance().getLogger().info("key set:"+cdkList.keySet()+", values:"+cdkList.values());
//        AnCDK.getInstance().getLogger().info("values:"+cdkList.values());
//        for (Map<String, String> sets : cdkList.values()) {
//            AnCDK.getInstance().getLogger().info(sets.get("command"));
//        }
        if (!Optional.ofNullable(ConfigLoader.getInstance().getCdkNode().getValue()).isPresent()) {
            return false;
        }
        Map<String, Map<String, String>> cdkList = (LinkedHashMap<String, Map<String, String>>) ConfigLoader.getInstance().getCdkNode().getValue(LinkedHashMap.class);
        ConfigurationNode exportNode = ConfigLoader.getInstance().getExportNode();

        Map<String, Integer> counter = new LinkedHashMap<>();
        for (String key : cdkList.keySet()) {
            String command = cdkList.get(key).get("command");
            counter.merge(command, 1, Integer::sum);
            exportNode.getNode(command, counter.get(command).toString()).setValue(key);
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
        if (!Optional.ofNullable(ConfigLoader.getInstance().getCdkNode().getValue()).isPresent()) {
            return false;
        }
        Set<String> cdkSet = ((LinkedHashMap<String, Map<String, String>>) ConfigLoader.getInstance().getCdkNode().getValue(LinkedHashMap.class)).keySet();
        return CSVUtil.writeCSVFileData(new File(AnCDK.getInstance().getConfigPath(), "export.csv"), cdkSet);
    }

}
