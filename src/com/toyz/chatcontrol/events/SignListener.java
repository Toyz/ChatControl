package com.toyz.chatcontrol.events;

import com.toyz.chatcontrol.ChatControl;
import com.toyz.chatcontrol.perms.Filters;
import com.toyz.chatcontrol.tools.FilterHelper;
import com.toyz.chatcontrol.tools.YMLHelper;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Travis
 * Date: 10/4/13
 * Time: 10:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class SignListener implements Listener {
    public ChatControl plugin;
    public YMLHelper settings;
    public FilterHelper FilterHelper;

    public SignListener(ChatControl instance, YMLHelper Settings, FilterHelper Badwords) {
        this.plugin = instance;
        this.settings = Settings;
        this.FilterHelper = Badwords;
    }

    @EventHandler
    public void eventSignChanged(SignChangeEvent event) {
        Player p = event.getPlayer();
        if (p.hasPermission("chatcontrol.bypass")) return;
        if (!(Boolean) settings.GetValue(Filters.ItemObjects.Enable)) return;

        String[] lines = event.getLines();

        for (int i = 0; i < lines.length; i++) {
            String Msg = lines[i].toLowerCase();
            if ((Boolean) settings.GetValue(Filters.ItemObjects.Filter)) {
                Msg = FilterHelper.CleanUpSentence(Msg);
            }
            if ((Boolean) settings.GetValue(Filters.ItemObjects.NoCaps)) {
                Msg = FilterHelper.RemoveAllCaps(Msg);
            }

            event.setLine(i, Msg);
        }
    }

}
