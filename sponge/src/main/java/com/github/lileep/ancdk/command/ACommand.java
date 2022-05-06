package com.github.lileep.ancdk.command;

import com.github.lileep.ancdk.AnCDK;
import com.github.lileep.ancdk.config.ConfigLoader;
import com.github.lileep.ancdk.lib.Reference;
import com.github.lileep.ancdk.util.I18nUtil;
import com.github.lileep.ancdk.util.TextUtil;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class ACommand {

    public final static CommandSpec CREATE =
            CommandSpec.builder()
                    .executor(((src, args) -> {
                        String command = args.<String>getOne("command").get();
                        int count = args.<Integer>getOne("count").get();
                        if (AnCDK.getInstance()
                                .getCdkHandler()
                                .createCDK(command, count, args.hasAny("once"), (src instanceof Player ? (((Player) src).getName()) : ("console")))) {
                            src.sendMessage(TextUtil.prefixedText(I18nUtil.getI18nText("Command", "createSuccess"), count));
                            return CommandResult.success();
                        }
                        src.sendMessage(TextUtil.prefixedText(I18nUtil.getI18nText("Command", "createFail")));
                        return CommandResult.empty();
                    }))
                    .arguments(
                            GenericArguments.flags().flag("-once").buildWith(GenericArguments.none()),
                            GenericArguments.integer(Text.of("count")),
                            GenericArguments.remainingJoinedStrings(Text.of("command"))
                    )
                    .permission(Reference.PERM_NODE_CREATE)
                    .build();

    public final static CommandSpec EXPORT_CSV =
            CommandSpec.builder()
                    .executor(((src, args) -> {
                        if (AnCDK.getInstance()
                                .getCdkHandler()
                                .exportCSV()) {
                            src.sendMessage(TextUtil.prefixedText(I18nUtil.getI18nText("Command", "exportCsvSuccess")));
                            return CommandResult.empty();
                        }
                        src.sendMessage(TextUtil.prefixedText(I18nUtil.getI18nText("Command", "exportCsvFail")));
                        return CommandResult.success();
                    }))
                    .permission(Reference.PERM_NODE_EXPORT)
                    .build();

    public final static CommandSpec EXPORT =
            CommandSpec.builder()
                    .executor(((src, args) -> {
                        if (AnCDK.getInstance()
                                .getCdkHandler()
                                .exportCDK()) {
                            src.sendMessage(TextUtil.prefixedText(I18nUtil.getI18nText("Command", "exportSuccess")));
                            return CommandResult.success();
                        }
                        src.sendMessage(TextUtil.prefixedText(I18nUtil.getI18nText("Command", "exportFail")));
                        return CommandResult.empty();
                    }))
                    .child(EXPORT_CSV, "csv")
                    .permission(Reference.PERM_NODE_EXPORT)
                    .build();

    public final static CommandSpec RELOAD =
            CommandSpec.builder()
                    .executor(((src, args) -> {
                        ConfigLoader.getInstance().reload();
                        src.sendMessage(TextUtil.prefixedText(I18nUtil.getI18nText("Command", "reloadSuccess")));
                        return CommandResult.success();
                    }))
                    .permission(Reference.PERM_NODE_RELOAD)
                    .build();

    public final static CommandSpec BASE =
            CommandSpec.builder()
                    .executor(((src, args) -> {
                        if (args.getOne("cdkey") != Optional.empty()) {
                            if (src instanceof Player && src.hasPermission(Reference.USER_NODE)) {
                                String cdkey = args.<String>getOne("cdkey").get();
                                if (AnCDK.getInstance()
                                        .getCdkHandler()
                                        .runCDKandLog(cdkey, (Player) src)) {
                                    src.sendMessage(TextUtil.prefixedText(I18nUtil.getI18nText("Command", "runSuccess")));
                                    return CommandResult.success();
                                }
                            }
                            src.sendMessage(TextUtil.prefixedText(I18nUtil.getI18nText("Command", "runFail")));
                            return CommandResult.empty();
                        }

                        if (src.hasPermission(Reference.PERM_NODE_ADMIN)) {
                            src.sendMessages(
                                    Text.of("§a==============================AnCDK=============================="),
                                    Text.of("§9/ancdk create <--once> [num] [command]    " + I18nUtil.getI18nText("Command", "help", "create")),
                                    Text.of("§9/ancdk export <csv>                       " + I18nUtil.getI18nText("Command", "help", "export")),
                                    Text.of("§9/ancdk reload                             " + I18nUtil.getI18nText("Command", "help", "reload")),
                                    Text.of("§9/ancdk [CDK]                              " + I18nUtil.getI18nText("Command", "help", "run")),
                                    Text.of("§a==============================AnCDK==============================")
                            );
                        } else {
                            src.sendMessages(
                                    Text.of("§a==============================AnCDK=============================="),
                                    Text.of("§9/ancdk [CDK]                              " + I18nUtil.getI18nText("Command", "help", "run")),
                                    Text.of("§a==============================AnCDK==============================")
                            );
                        }
                        return CommandResult.success();
                    }))
                    .arguments(
                            GenericArguments.optionalWeak(GenericArguments.remainingJoinedStrings(Text.of("cdkey")))
                    )
                    .child(CREATE, "create", "generate", "gen", "c")
                    .child(EXPORT, "export")
                    .child(RELOAD, "reload")
                    .build();

}
