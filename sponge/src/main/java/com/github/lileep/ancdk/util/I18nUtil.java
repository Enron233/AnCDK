package com.github.lileep.ancdk.util;

import com.github.lileep.ancdk.config.ConfigLoader;
import org.checkerframework.checker.nullness.qual.NonNull;

public class I18nUtil {

    public static String getI18nText(@NonNull Object @NonNull... args){
        return ConfigLoader.getInstance().getLangNode().getNode(args).getString();
    }
}
