package com.github.lileep.ancdk.handler.api;

import com.github.lileep.ancdk.config.ConfigLoader;
import com.github.lileep.ancdk.lib.Reference;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public abstract class AbstractCDKHandler {

    /**
     * 运行CDK主逻辑
     * 对于控制台指令剪掉其"console:"部分
     *
     * @param command 待执行的命令
     * @param player 执行者
     */
    protected void runCDKCmd(String command, Player player) {
        CommandSource source;
        if (command.startsWith(Reference.CONSOLE_PREFIX)) {
            source = Sponge.getServer().getConsole();
            command = command.substring(Reference.CONSOLE_PREFIX.length());
        } else {
            source = player;
        }
        Sponge.getCommandManager().process(source, command);
    }

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");

    /**
     * 日志记录主逻辑
     *
     * @param executor  此条记录的操作来源
     * @param operation 操作
     * @param cdk       待记录的CDK
     */
    protected void setLog(String executor, String operation, String cdk) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ConfigLoader.getInstance().getLogFile(), true), StandardCharsets.UTF_8))) {
            bw.write(FORMATTER.format(new Date(System.currentTimeMillis())) + executor + " " + operation + " " + cdk);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 日志记录主逻辑
     *
     * @param executor  此条记录的操作来源
     * @param operation 操作
     * @param cdks      待记录的CDK
     */
    protected void setLog(String executor, String operation, List<String> cdks) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ConfigLoader.getInstance().getLogFile(), true), StandardCharsets.UTF_8))) {
            for (String cdk : cdks) {
                bw.write(FORMATTER.format(new Date(System.currentTimeMillis())) + executor + " " + operation + " " + cdk);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Random RANDOM = new Random();

    /**
     * 生成九位及以上CDK
     *
     * @return 生成的CDK
     */
    protected String genCDK() {
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
     * 运行CDK并记录log
     *
     * @param cdkey  待运行的CDK
     * @param player 执行者
     * @return 是否运行成功
     */
    public abstract boolean runCDKandLog(String cdkey, Player player);

    /**
     * 创建CDK
     *
     * @param command  CDK对应的命令
     * @param count    创建条数
     * @param once     是否一次性
     * @param executor 创建者
     * @return 是否创建成功
     */
    public abstract boolean createCDK(String command, int count, boolean once, String executor);


    /**
     * 导出
     *
     * @return 是否导出成功
     */
    public abstract boolean exportCDK();

    /**
     * 导为CSV
     *
     * @return 是否导出成功
     */
    public abstract boolean exportCSV();

}
