package com.toyz.chatcontrol.events;

import com.toyz.chatcontrol.ChatControl;
import com.toyz.chatcontrol.tools.FilterHelper;
import com.toyz.chatcontrol.tools.YMLHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

/**
 * Created with IntelliJ IDEA.
 * User: Travis
 * Date: 10/5/13
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class BookListener implements Listener {
    public FilterHelper FilterHelper;
    public ChatControl plugin;
    public YMLHelper settings;
    public BookListener(ChatControl instance, YMLHelper Settings, FilterHelper Badwords) {
        this.plugin = instance;
        this.settings = Settings;
        this.FilterHelper = Badwords;
    }

    @EventHandler
    public void onPlayerEditBook(PlayerEditBookEvent event){
            BookMeta metaData = event.getNewBookMeta();
            for(int i = 1; i <= metaData.getPageCount(); i++){
                System.out.println(metaData.getPage(i));
                //metaData.setPage(i, metaData.getPage(i).replaceAll("(&([a-f0-9l-or]))", "\u00A7$2"));
            }
            event.setNewBookMeta(metaData);
    }
}
