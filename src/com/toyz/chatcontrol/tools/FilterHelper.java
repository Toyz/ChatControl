package com.toyz.chatcontrol.tools;

import com.toyz.chatcontrol.perms.GlobalSettings;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Travis
 * Date: 10/5/13
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class FilterHelper {
    private YMLHelper BadWords;
    private YMLHelper settings;
    private List<String> Words;

    public FilterHelper(YMLHelper Words, YMLHelper Settings){
        this.BadWords = Words;
        this.settings = Settings;
    }

    public String CleanUpSentence(String Msg){
        this.Words = (List<String>) this.BadWords.GetValue(GlobalSettings.WordsSettings.BadWord);
        String[] MsgWords = Msg.split(" ");
        List<String> Sentence = new ArrayList<String>();
        for(String w : MsgWords){
            String o = w;
            for (String i : Words) {
                w = w.replaceAll("[^A-Za-z0-9]", "");
                if(w.equalsIgnoreCase(i.toLowerCase()))
                {
                    w = StringUtils.leftPad("", w.length(), (String) settings.GetValue(GlobalSettings.ChatSettings.ReplaceCharacter));
                    break;
                }
                //Msg = Msg.replace(i, StringUtils.leftPad("", i.length(), (String) settings.GetValue("Chat.Filter.ReplaceChar")));
            }
            int c = w.length();
            String sub = o.substring(0, c);
            w = w + o.replace(sub, "");

            Sentence.add(w);
        }

        return StringUtils.join(Sentence, " ");
    }

    public String RemoveAllCaps(String Msg){
        if (Msg.length() > 0) {
            char first = Character.toUpperCase(Msg.charAt(0));
            Msg = first + Msg.substring(1);
        }

        return Msg;
    }
}
