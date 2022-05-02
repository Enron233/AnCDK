package com.github.lileep.ancdk.util;

import com.github.lileep.ancdk.config.ConfigLoader;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CDKUtil {

    /**
     * 运行CDK主逻辑
     *
     * @param cdk    待处理的CDK
     * @param player 执行者
     */
    private static void runCDK(String cdk, Player player) {
        String command = ConfigLoader.getInstance().getRootNode().getNode(cdk, "command").getString().replace("{player}", player.getName());
        CommandSource source;
        if (ConfigLoader.getInstance().getRootNode().getNode(cdk, "console").getBoolean()) {
            source = Sponge.getServer().getConsole();
        } else {
            source = player;
        }
        Sponge.getCommandManager().process(source, command);
    }

    /**
     * 日志记录主逻辑
     *
     * @param cdk      待记录的CDK
     * @param executor 此条记录的操作来源
     * @throws IOException 需处理的读写异常
     */
    private static void setLog(String cdk, String executor, String operation) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        ConfigurationNode node = ConfigLoader.getInstance().getLoggerNode();
        node.getNode(formatter.format(new Date(System.currentTimeMillis())) + executor + " " + cdk).setValue(operation);
        ConfigLoader.getInstance().getLoggerLoader().save(node);
    }

    /**
     * 运行CDK并记录log
     *
     * @param cdkey  待运行的CDK
     * @param player 执行者
     * @return 是否运行成功
     */
    public static boolean runCDKandLog(String cdkey, Player player) {
        ConfigurationNode node = ConfigLoader.getInstance().getRootNode();
        if (Optional.ofNullable(node.getNode(cdkey).getString()).isPresent()) {
            runCDK(cdkey, player);
            try {
                if (node.getNode(cdkey, "once").getBoolean()) {
                    node.getNode(cdkey).setValue(null);
                    ConfigLoader.getInstance().getLoader().save(node);
                }
                setLog(cdkey, player.getName(), "use");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    private static final Random RANDOM = new Random();

    /**
     * 生成九位及以上CDK
     *
     * @return 生成的CDK
     */
    public static String genCDK() {
        int a, b, c;
        a = RANDOM.nextInt(360000) + 2000;
        b = RANDOM.nextInt(300000) + 2000;
        c = RANDOM.nextInt(66000) + 2000;
        if (RANDOM.nextInt(10) % 2 == 0) {
            return (Integer.toString(a, 16) + Integer.toString(b, 16) + Integer.toString(c, 16));
        }
        return (Integer.toString(a, 36) + Integer.toString(b, 36) + Integer.toString(c, 36));
    }

    /**
     * 创建CDK
     *
     * @param command CDK对应的命令
     * @param count   创建条数
     * @return 是否创建成功
     * @throws IOException 需处理的读写异常
     */
    public static boolean createCDK(String command, int count, String executor) throws IOException {
        ConfigurationNode node = ConfigLoader.getInstance().getRootNode();
        for (int i = 0; i < count; i++) {
            String key = genCDK();
            node.getNode(key, "command").setValue(command);
            node.getNode(key, "console").setValue(true);
            node.getNode(key, "once").setValue(true);
            setLog(key, executor, "create");
        }
        ConfigLoader.getInstance().getLoader().save(node);
        return true;
    }

    /**
     * 导出
     *
     * @return 是否导出成功
     * @throws IOException 需处理的读写异常
     */
    public static boolean exportCDK() throws IOException {
//        AnCDK.getInstance().getLogger().info("key set:"+cdkList.keySet()+", values:"+cdkList.values());
//        AnCDK.getInstance().getLogger().info("values:"+cdkList.values());
//        for (Map<String, String> sets : cdkList.values()) {
//            AnCDK.getInstance().getLogger().info(sets.get("command"));
//        }
        if (!Optional.ofNullable(ConfigLoader.getInstance().getRootNode().getValue()).isPresent()) {
            return false;
        }
        Map<String, Map<String, String>> cdkList = (LinkedHashMap<String, Map<String, String>>) ConfigLoader.getInstance().getRootNode().getValue(LinkedHashMap.class);
        ConfigurationNode exportNode = ConfigLoader.getInstance().getExportNode();

        Map<String, Integer> counter = new LinkedHashMap<>();
        for (String key : cdkList.keySet()) {
            String command = cdkList.get(key).get("command");
            counter.merge(command, 1, Integer::sum);
            exportNode.getNode(command, counter.get(command).toString()).setValue(key);
        }
        ConfigLoader.getInstance().getExportLoader().save(exportNode);
        return true;
    }

}
