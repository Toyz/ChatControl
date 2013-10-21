package com.toyz.chatcontrol.tools;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Travis
 * Date: 9/21/13
 * Time: 8:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class YMLHelper {
    private YamlConfiguration yc;
    private File config;

    public YMLHelper(File Config) throws IOException, InvalidConfigurationException {
        yc = new YamlConfiguration();
        this.config = Config;
        if (!Config.exists()) {
            Config.createNewFile();
        }

        yc.load(Config);
    }

    public Object GetValue(String Key) {
        return yc.get(Key);
    }

    public void SetValue(String Key, Object Value) {
        yc.set(Key, Value);
    }

    public void Reload() throws IOException, InvalidConfigurationException {
        yc = new YamlConfiguration();
        yc.load(this.config);
    }

    public void Save() throws IOException {
        yc.save(this.config);
    }
}
