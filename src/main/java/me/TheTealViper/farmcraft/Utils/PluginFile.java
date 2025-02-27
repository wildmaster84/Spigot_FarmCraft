package me.TheTealViper.farmcraft.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginFile extends YamlConfiguration {   
   
    private File file;
    private String defaults;
    @SuppressWarnings("unused")
	private JavaPlugin plugin;
   
    /**
     * Creates new PluginFile, without defaults
     * @param plugin - Your plugin
     * @param fileName - Name of the file
     */
    public PluginFile(JavaPlugin plugin, String fileName) {
        this(plugin, fileName, null);
    }
   
    /**
     * Creates new PluginFile, with defaults
     * @param plugin - Your plugin
     * @param fileName - Name of the file
     * @param defaultsName - Name of the defaults
     */
    public PluginFile(JavaPlugin plugin, String fileName, String defaultsName) {
        this.plugin = plugin;
        this.defaults = defaultsName;
        this.file = new File(plugin.getDataFolder(), fileName);
        reload();
    }
   
    /**
     * Reload configuration
     */
    public void reload() {
       
        if (!file.exists()) {
           
            try {
            	if (defaults == null) {
	                file.getParentFile().mkdirs();
	                file.createNewFile();
            	} else {
            		Files.copy(getClass().getResourceAsStream("/" + defaults), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            	}
               
            } catch (IOException exception) {
                exception.printStackTrace();
                //plugin.getLogger().severe("Error while creating file " + file.getName());
            }
           
        }
       
        try {
            load(file);
           
//            if (defaults != null) {
//                InputStreamReader reader = new InputStreamReader(plugin.getResource(defaults));
//                FileConfiguration defaultsConfig = YamlConfiguration.loadConfiguration(reader);       
//               
//                setDefaults(defaultsConfig);
//                options().copyDefaults(true);
//               
//                reader.close();
//                save();
//            }
       
        } catch (IOException exception) {
            exception.printStackTrace();
            //plugin.getLogger().severe("Error while loading file " + file.getName());
           
        } catch (InvalidConfigurationException exception) {
            exception.printStackTrace();
            //plugin.getLogger().severe("Error while loading file " + file.getName());
           
        }
       
    }
   
    /**
     * Save configuration
     */
    public void save() {
       
        try {
            options().indent(2);
            save(file);
           
        } catch (IOException exception) {
            exception.printStackTrace();
            //plugin.getLogger().severe("Error while saving file " + file.getName());
        }
       
    }
   
}