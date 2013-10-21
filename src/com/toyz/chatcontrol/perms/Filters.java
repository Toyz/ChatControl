package com.toyz.chatcontrol.perms;

/**
 * Created with IntelliJ IDEA.
 * User: Travis
 * Date: 10/10/13
 * Time: 2:29 PM
 * To change this template use File | Settings | File Templates.
 */
public final class Filters {

    //Private Chat
    public static class PrivateChat implements iPerms
    {
        private static String Parent = "Filter.PrivateChat";
        public static String Enable = Parent + ".Enable";
        public static String Filter = Parent + ".Filter";
        public static String NoCaps = Parent + ".NoCaps";
    }

    //Public Chat
    public static class PublicChat implements iPerms
    {
        private static String Parent = "Filter.PublicChat";
        public static String Enable = Parent + ".Enable";
        public static String Filter = Parent + ".Filter";
        public static String NoCaps = Parent + ".NoCaps";
    }

    //Objects
    public static class ItemObjects  implements iPerms
    {
        private static String Parent = "Filter.Objects";
        public static String Enable = Parent + ".Enable";
        public static String Filter = Parent + ".Filter";
        public static String NoCaps = Parent + ".NoCaps";
    }
}
