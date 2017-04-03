package core;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SpookCraftPlugin extends JavaPlugin {
	public static Plugin plugin;
    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	plugin = this;
    	Listener myListener = new MyListener();
    	getServer().getPluginManager().registerEvents(myListener, this);
    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }
}
