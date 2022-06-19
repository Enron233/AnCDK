package com.xiaoan.ancdk;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;


public class Acommand implements CommandExecutor {
    public static AnCDK a = AnCDK.getIns();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            if (p.hasPermission("ancdk.admin")){
                p.sendMessage("§a==============================AnCDK==============================");
                p.sendMessage("§9/ancdk create [command] [num]    创建[num]个执行[command]命令的CDK");
                p.sendMessage("§9/ancdk export                    批量一键导出所有CDK");
                p.sendMessage("§9/ancdk reload                    重载配置文件");
                p.sendMessage("§9/ancdk [CDK]                     使用CDK");
                p.sendMessage("§a==============================AnCDK==============================");
            }else {
                p.sendMessage("§a==============================AnCDK==============================");
                p.sendMessage("§9/ancdk [CDK]                     使用CDK");
                p.sendMessage("§a==============================AnCDK==============================");
            }
        }else if (args[0].equalsIgnoreCase("create") && p.hasPermission("ancdk.admin")){
            if (args.length >=3){
                int num = Integer.parseInt(args[args.length - 1]);
                for (int i = 0; i < num; i++){
                    String key = GetCDK.getCDK();
                    AnCDK.getIns().getConfig().set(key + ".command", getCommand(args));
                    AnCDK.getIns().getConfig().set(key + ".op", true);
                    AnCDK.getIns().saveConfig();
                }
                p.sendMessage("§6设置成功！成功创建§c " + num + " §6张卡密, 详情请浏览配置文件");
            }else {
                p.sendMessage("§4参数不足！");
            }
        }else if (args.length == 1){
            if (args[0].equalsIgnoreCase("reload") && p.hasPermission("ancdk.admin")){
                a.reloadConfig();
                a.saveConfig();
                p.sendMessage("§6配置文件重载成功！");
                return true;
            }
            if (args[0].equalsIgnoreCase("export") && p.hasPermission("ancdk.admin")){
                try {
                    if (exportCDK()){
                        p.sendMessage("§6导出成功！请检查配置文件中export.yml");
                    }else {
                        p.sendMessage("§c在导出过程中发生错误！请检查！");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
            String input = args[0];
            for (String li : AnCDK.getIns().getConfig().getKeys(false)){
                if (input.equals(li)){
                    boolean resp = runCDK(li, p);
                    if (resp){
                        a.getConfig().set(li, null);
                        a.saveConfig();
                        p.sendMessage("§6命令执行成功！");
                    }
                    return true;
                }
            }
            p.sendMessage("§4CDK不存在！");
        }else {
            p.sendMessage("§4指令是不是打错了？？？");
        }
        return true;
    }
    private static String getCommand(String[] strs) {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= strs.length - 2; i++){
            result.append(strs[i]).append(" ");
        }
        return result.toString();
    }
    private static boolean runCDK(String cdk, Player player) {
        boolean result;
        String command = a.getConfig().getString(cdk + ".command").replace("{player}", player.getName());
        if (AnCDK.getIns().getConfig().getBoolean(cdk + ".op")){
            result = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }else {
            result = player.performCommand(command);
        }
        return result;
    }
    private static boolean exportCDK() throws IOException {
        Set<String> cdkList = AnCDK.getIns().getConfig().getKeys(false);
        int i = 1;
        for (String cdk : cdkList) {
            String path = AnCDK.getIns().getConfig().getString(cdk + ".command");
            AnCDK.filec.set(path + "." + i, cdk);
            AnCDK.filec.save(AnCDK.getIns().file);
            i ++;
        }
        return true;
    }
    private static void setLog(String cdk, Player user) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        AnCDK.uselog.set(cdk + ".user", user.getName());
        AnCDK.uselog.set(cdk + ".time", formatter.format(date));
        AnCDK.uselog.save(AnCDK.getIns().used);
    }
}
