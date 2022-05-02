package com.github.lileep.ancdk.util;

import com.github.lileep.ancdk.lib.Reference;
import org.spongepowered.api.text.Text;

public class TextUtil {

    public static Text prefixedText(String content){
        return Text.of(Reference.PREFIX + content);
    }

    public static Text prefixedText(String content, Object... args){
        return Text.of(Reference.PREFIX + String.format(content, args));
    }
}
