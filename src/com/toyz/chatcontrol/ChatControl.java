package com.toyz.chatcontrol;

import com.toyz.chatcontrol.cmd.Commands;
import com.toyz.chatcontrol.events.BookListener;
import com.toyz.chatcontrol.events.ChatListener;
import com.toyz.chatcontrol.events.CommandListener;
import com.toyz.chatcontrol.events.SignListener;
import com.toyz.chatcontrol.perms.Filters;
import com.toyz.chatcontrol.perms.GlobalSettings;
import com.toyz.chatcontrol.tools.FilterHelper;
import com.toyz.chatcontrol.tools.YMLHelper;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * Created with IntelliJ IDEA.
 * User: Travis
 * Date: 10/4/13
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */
public final class ChatControl extends JavaPlugin {
    private YMLHelper settings;
    private YMLHelper Badwords;

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "ChatControl is starting up");

        getDataFolder().mkdir();
        File config = new File(getDataFolder() + File.separator + "config.yml");
        File badConfig = new File(getDataFolder() + File.separator + "Words.yml");
        PluginDescriptionFile pdf = getDescription();
        try {
            if (config.exists()) {
                settings = new YMLHelper(config);
                Badwords = new YMLHelper(badConfig);
            } else {
                config.createNewFile();
                settings = new YMLHelper(config);

                //Global
                settings.SetValue(GlobalSettings.ChatSettings.ReplaceCharacter, "*");
                settings.SetValue(GlobalSettings.ChatSettings.AllowMinWordWhiteList, true);
                settings.SetValue(GlobalSettings.ChatSettings.AllowSmileyFaces, true);
                settings.SetValue(GlobalSettings.ChatLengthSettings.MinLength, 5);
                settings.SetValue(GlobalSettings.ChatLengthSettings.MaxLength, -1);
                settings.SetValue(Filters.PublicChat.Filter, true);
                settings.SetValue(Filters.PublicChat.NoCaps, true);

                //Public
                settings.SetValue(Filters.PublicChat.Enable, true);
                settings.SetValue(Filters.PublicChat.Filter, true);
                settings.SetValue(Filters.PublicChat.NoCaps, true);

                //Private Chat
                settings.SetValue(Filters.PrivateChat.Enable, true);
                settings.SetValue(Filters.PrivateChat.Filter, true);
                settings.SetValue(Filters.PrivateChat.NoCaps, true);

                //Game Objects
                settings.SetValue(Filters.ItemObjects.Enable, true);
                settings.SetValue(Filters.ItemObjects.Filter, true);
                settings.SetValue(Filters.ItemObjects.NoCaps, true);

                settings.Save();

                badConfig.createNewFile();
                Badwords = new YMLHelper(badConfig);
                Badwords.SetValue(GlobalSettings.WordsSettings.BadWord, Arrays.asList(
                        "ass", "fuck", "shit", "bitch", "cunt", "dyke", "whore", "slut", "fag", "dildo",
                        "penis", "dick", "vagina", "clit", "breast", "tits", "boobs", "nipples"));
                Badwords.SetValue(GlobalSettings.WordsSettings.SmileyFaces, Arrays.asList(
                        "XD", ":D", ":P", "D:", "Q_Q", "O.O", "O_O", "O_o", "o_O", "O-O", "o-O", "O-o", "xD",
                        "X_X", "X_x", "x_X", "X-X", "x-X", "X-x"
                ));
                Badwords.SetValue(GlobalSettings.WordsSettings.WhiteListWords, Arrays.asList(
                        "hi", "bye", "ok", "k", "good", "fish", "rod", "hack", "hex", "ban", "kick",
                        "die", "dead", "prey", "pray", "kk", "fat", "weed", "drug", "cow", "pig", "bat", "duck",
                        "path", "pass", "help", "yell", "life", "live", "leaf", "tree", "lawn", "oink", "wolf", "cat",
                        "moo", "sh", "shh", "shhh", "mhm", "yes", "no", "may", "lay", "kay", "bed", "yard", "bush"
                ));
                Badwords.Save();
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        FilterHelper filterHelper = new FilterHelper(Badwords, settings);
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new ChatListener(this, settings, filterHelper), this);
        pm.registerEvents(new SignListener(this, settings, filterHelper), this);
        pm.registerEvents(new CommandListener(this, settings, filterHelper), this);
        pm.registerEvents(new BookListener(this, settings, filterHelper), this);
        getCommand("chatcontrol").setExecutor(new Commands(this, settings, Badwords));
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "ChatControl is shutting down");
    }
}
