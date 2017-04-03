package core;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SpookCraftPlugin extends JavaPlugin {
	public static Plugin plugin;
	Set<Entity> entities = new HashSet<>();
	Map<Entity, Location> currentLocMap = new HashMap<>();
    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	plugin = this;
    	Listener myListener = new MyListener();
    	getServer().getPluginManager().registerEvents(myListener, this);
    	BukkitRunnable runnable = new BukkitRunnable() {
			@Override
			public void run() {
				entities.clear();
				for(Player p : Bukkit.getOnlinePlayers()) {
					Location l = p.getLocation();
					entities.addAll(p.getWorld().getNearbyEntities(l, 200, 200, 200));
				}
				entities.forEach(e -> {
					if(e.getType() == EntityType.ZOMBIE) {
						doMobCheck(e);
					}
				});
			}
		};
		runnable.runTaskTimer(this, 10, 10);
    }
    
    private void doMobCheck(Entity entity) {
    	Location oldLoc = currentLocMap.get(entity);
    	if(null != oldLoc) {
    		if(!oldLoc.equals(entity.getLocation())) {
    			new Location(oldLoc.getWorld(), oldLoc.getX(), oldLoc.getY() - 1, oldLoc.getZ()).getBlock().setType(Material.GLASS);;
    		}
    	}
    	currentLocMap.put(entity, entity.getLocation());
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }
}
