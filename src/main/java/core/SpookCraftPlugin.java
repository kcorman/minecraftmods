package core;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class SpookCraftPlugin extends JavaPlugin {
    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	Listener myListener = new MyListener();
    	getServer().getPluginManager().registerEvents(myListener, this);
    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }
}
