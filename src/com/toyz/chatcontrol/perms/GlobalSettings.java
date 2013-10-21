package com.toyz.chatcontrol.perms;

/**
 * Created with IntelliJ IDEA.
 * User: Travis
 * Date: 10/10/13
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class GlobalSettings {
    public static class ChatSettings implements iPerms
    {
        private static String Parent = "Global.Settings";
        public static String ReplaceCharacter = Parent + ".ReplaceCharacter";
        public static String AllowSmileyFaces = Parent + ".AllowSmileyFaces";
        public static String AllowMinWordWhiteList = Parent + ".AllowMinWordWhiteList";
    }

    public static class ChatLengthSettings  implements iPerms
    {
        private static String Parent = ChatSettings.Parent + ".ChatLengths";
        public static String MinLength = Parent + ".Min";
        public static String MaxLength =  Parent + ".Max";
    }

    public static class WordsSettings implements iPerms{
        private static String Parent = "Words";
        public static String BadWord = Parent + ".BadWords";
        public static String WhiteListWords = Parent + ".WhiteListWords";
        public static String SmileyFaces = Parent + ".SmileyFaces";
    }
}
