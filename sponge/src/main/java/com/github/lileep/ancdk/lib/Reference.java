package com.github.lileep.ancdk.lib;

public class Reference {

    public static final String PLUGIN_ID = "ancdk";
    public static final String PLUGIN_NAME = "AnCDK";
    public static final String VERSION = "1.0";

    public static final String PREFIX = "§6[AnCDK]";

    public static final String CONSOLE_PREFIX = "console:";

    public static final String USER_NODE = "user";
    public static final String ADMIN_NODE = "admin";

    public static final String PERM_NODE_CREATE = PLUGIN_ID + "." + ADMIN_NODE + ".create";
    public static final String PERM_NODE_EXPORT = PLUGIN_ID + "." + ADMIN_NODE + ".export";
    public static final String PERM_NODE_RELOAD = PLUGIN_ID + "." + ADMIN_NODE + ".reload";

    public static final String PERM_NODE_USER = PLUGIN_ID + "." + USER_NODE;
    public static final String PERM_NODE_ADMIN = PLUGIN_ID + "." + ADMIN_NODE;


}
