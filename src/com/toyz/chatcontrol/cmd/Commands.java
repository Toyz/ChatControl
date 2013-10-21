package com.toyz.chatcontrol.cmd;

import com.toyz.chatcontrol.ChatControl;
import com.toyz.chatcontrol.perms.GlobalSettings;
import com.toyz.chatcontrol.tools.YMLHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Travis
 * Date: 10/4/13
 * Time: 6:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Commands implements CommandExecutor {

    private ChatControl plugin;
    private YMLHelper settings;
    private YMLHelper badwords;

    public Commands(ChatControl plugin, YMLHelper Settings, YMLHelper Badwords) {
        this.plugin = plugin;
        this.settings = Settings;
        this.badwords = Badwords;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("chatcontrol")) {
            if (strings.length == 0 || strings[0].toLowerCase().equals("help")) {
                if (commandSender instanceof Player) {
                    if (!commandSender.hasPermission("chatcontrol.help")) {
                        commandSender.sendMessage("You do not have permission to view help.");
                        return true;
                    }
                    commandSender.sendMessage(ChatColor.AQUA + " -> ChatControl Help <- ");
                    commandSender.sendMessage(ChatColor.RED + "Reload" + ChatColor.WHITE + " -> Allows you to reload chatcontrol");
                    commandSender.sendMessage(ChatColor.RED + "BadAdd {Word}" + ChatColor.WHITE + " -> Allows you too add a bad word (Replace {Word} with your word)");
                    commandSender.sendMessage(ChatColor.RED + "BadRemove {Word}" + ChatColor.WHITE + " -> Allows you too remove a bad word (Replace {Word} with your word)");
                    return true;
                }
                ConsoleCommandSender console = commandSender.getServer().getConsoleSender();
                console.sendMessage(ChatColor.AQUA + " -> ChatControl Help <- ");
                console.sendMessage(ChatColor.RED + "Reload" + ChatColor.WHITE + " -> Allows you to reload chatcontrol");
                console.sendMessage(ChatColor.RED + "BadAdd {Word}" + ChatColor.WHITE + " -> Allows you too add a bad word (Replace {Word} with your word)");
                console.sendMessage(ChatColor.RED + "BadRemove {Word}" + ChatColor.WHITE + " -> Allows you too remove a bad word (Replace {Word} with your word)");
                return true;
            }

            if (strings.length > 0 && strings[0].toLowerCase().equals("reload")) {
                Boolean doReload = false;
                if (commandSender instanceof Player) {
                    if (commandSender.hasPermission("chatcontrol.reload")) {
                        doReload = true;
                    } else {
                        commandSender.sendMessage("You do not have permission to reload.");
                        return true;
                    }
                } else {
                    doReload = true;
                }
                if (doReload) {
                    try {
                        settings.Reload();
                        badwords.Reload();
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (InvalidConfigurationException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    if (commandSender instanceof Player) {
                        commandSender.sendMessage("[ChatFilter] Reloaded.");
                    }
                    System.out.print("[ChatControl] Reloaded.");
                    return true;
                }
            }
            if (strings.length > 0 && strings[0].toLowerCase().equals("badadd")) {
                if (commandSender instanceof Player) {
                    if (!commandSender.hasPermission("chatcontrol.edit")) {
                        commandSender.sendMessage("You do not have permission to edit the worlds list.");
                        return true;
                    }
                }
                if (strings[1].length() <= 0) {
                    if (commandSender instanceof Player) {
                        commandSender.sendMessage("[ChatFilter] Must add a word /chatcontrol badadd word.");
                    } else {
                        System.out.print("[ChatControl] Must add a word /chatcontrol badadd word.");
                    }
                    return true;
                }
                String word = strings[1].toLowerCase();
                ArrayList<String> CurWords = (ArrayList<String>) badwords.GetValue(GlobalSettings.WordsSettings.BadWord);
                if (CurWords.contains(word)) {
                    if (commandSender instanceof Player) {
                        commandSender.sendMessage("[ChatFilter] This word is already in the filter.");
                    } else {
                        System.out.print("[ChatControl] This word is already in the filter.");
                    }
                    return true;
                }
                CurWords.add(word);
                if (commandSender instanceof Player) {
                    commandSender.sendMessage("[ChatFilter] Added the following word " + word + ".");
                }
                System.out.print("[ChatFilter] Added the following word " + word + ".");
                try {
                    badwords.Save();
                    badwords.Reload();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (InvalidConfigurationException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                return true;
            }

            if (strings.length > 0 && strings[0].toLowerCase().equals("badremove")) {
                if (commandSender instanceof Player) {
                    if (!commandSender.hasPermission("chatcontrol.edit")) {
                        commandSender.sendMessage("You do not have permission to edit the worlds list.");
                        return true;
                    }
                }
                if (strings[1].length() <= 0) {
                    if (commandSender instanceof Player) {
                        commandSender.sendMessage("[ChatFilter] Invalid Usage try /chatcontrol badremove word.");
                    } else {
                        System.out.print("[ChatControl] Invalid Usage try /chatcontrol badremove word.");
                    }
                    return true;
                }
                String word = strings[1].toLowerCase();
                ArrayList<String> CurWords = (ArrayList<String>) badwords.GetValue(GlobalSettings.WordsSettings.BadWord);
                if (!CurWords.contains(word)) {
                    if (commandSender instanceof Player) {
                        commandSender.sendMessage("[ChatFilter] Word do not exist in the filter.");
                    } else {
                        System.out.print("[ChatFilter] Word do not exist in the filter.");
                    }
                    return true;
                }
                CurWords.remove(word);
                if (commandSender instanceof Player) {
                    commandSender.sendMessage("[ChatFilter] Removed the following word " + word + ".");
                }
                System.out.print("[ChatFilter] Removed the following word " + word + ".");
                try {
                    badwords.Save();
                    badwords.Reload();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (InvalidConfigurationException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                return true;
            }
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
