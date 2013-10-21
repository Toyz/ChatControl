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
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Travis
 * Date: 10/5/13
 * Time: 11:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandListener implements Listener {
    public ChatControl plugin;
    public YMLHelper settings;
    public FilterHelper FilterHelper;

    public CommandListener(ChatControl instance, YMLHelper Settings, FilterHelper FH) {
        this.plugin = instance;
        this.settings = Settings;
        this.FilterHelper = FH;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPreCommand(PlayerCommandPreprocessEvent event)
    {
        Player p = event.getPlayer();
        if (p.hasPermission("chatcontrol.bypass")) return;
        if (!(Boolean) settings.GetValue(Filters.PrivateChat.Enable)) return;

        String Username = " ";
        String command = event.getMessage().substring(1).split(" ")[0];

        if(command.toLowerCase().contains("msg"))
            Username = " " + event.getMessage().substring(1).split(" ")[1] + " ";

        if(command.toLowerCase().contains("msg")
                || command.toLowerCase().contains("r")
                || command.toLowerCase().contains("t")
                || command.equalsIgnoreCase("me")){
            String[] Params =  event.getMessage().substring(1).split(" ");

            String[] yourArray = Arrays.copyOfRange(Params, command.toLowerCase().contains("msg") ? 2 : 1, Params.length);
            String Msg =  StringUtils.join(yourArray, " ");

            if((Boolean) settings.GetValue(Filters.PrivateChat.Filter)){
               Msg = FilterHelper.CleanUpSentence(Msg.toLowerCase());
            }

            if ((Boolean) settings.GetValue(Filters.PrivateChat.NoCaps)) {
                Msg = FilterHelper.RemoveAllCaps(Msg.toLowerCase());
            }
            event.setMessage("/" + command + Username +  Msg);
        }


    }
}
