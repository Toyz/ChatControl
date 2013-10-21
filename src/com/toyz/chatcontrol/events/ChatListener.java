package com.toyz.chatcontrol.events;

import com.toyz.chatcontrol.ChatControl;
import com.toyz.chatcontrol.perms.Filters;
import com.toyz.chatcontrol.tools.FilterHelper;
import com.toyz.chatcontrol.tools.YMLHelper;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Travis
 * Date: 10/4/13
 * Time: 6:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChatListener implements Listener {
    private final FilterHelper FilterHelper;
    public ChatControl plugin;
    public YMLHelper settings;
    public ChatListener(ChatControl instance, YMLHelper Settings, FilterHelper Badwords) {
        this.plugin = instance;
        this.settings = Settings;
        this.FilterHelper = Badwords;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerEvent(AsyncPlayerChatEvent chat) {
        Player p = chat.getPlayer();

        String Msg = chat.getMessage().toLowerCase();

        if (p.hasPermission("chatcontrol.bypass")) return;
        if (!(Boolean) settings.GetValue(Filters.PublicChat.Enable)) return;

        if ((Boolean) settings.GetValue(Filters.PublicChat.Filter)) {
            Msg = FilterHelper.CleanUpSentence(Msg);
        }

        if ((Boolean) settings.GetValue(Filters.PublicChat.NoCaps)) {
            Msg = FilterHelper.RemoveAllCaps(Msg);
        }


        chat.setMessage(Msg);
    }
}
