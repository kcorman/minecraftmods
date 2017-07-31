package core;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SpookCraftPlugin extends JavaPlugin {
	public static Plugin plugin;
	List<Faction> factions = new ArrayList<>();
	List<SpookCraftEntity> gameMobs = new ArrayList<>();
	
	Map<Entity, Location> currentLocMap = new HashMap<>();
    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	plugin = this;
    	// Find the nexus placeholders
    	Location red = new Location(Bukkit.getWorld("world"), -1010, 78, -32);
    	Location blue = new Location(Bukkit.getWorld("world"), -1039, 74, -49);
    	Nexus redNexus = new Nexus(red.getBlock());
    	Nexus blueNexus = new Nexus(blue.getBlock());
    	factions.add(new Faction(redNexus, Zombie.class));
    	factions.add(new Faction(blueNexus, Spider.class));
    	// Task to spawn monsters every 40 seconds
    	BukkitRunnable spawnMonsterTask = new BukkitRunnable() {
			@Override
			public void run() {
				for(Faction f : factions) {
					// spawn some monsters for this faction
					Location loc = f.nexus.placeholder.getLocation();
					for(int i = 0;i<f.monstersToSpawn;i++) {
						Creature ent = (Creature) f.getWorld().spawn(loc, f.warriorClass);
						gameMobs.add(new SpookCraftEntity(ent, f));
					}
				}
			}
		};
		spawnMonsterTask.runTaskTimer(this, 100, 800);
		
		// now order monsters around
		BukkitRunnable orderMonstersTask = new BukkitRunnable() {
			@Override
			public void run() {
				for(SpookCraftEntity ent : gameMobs) {
					// TODO: We should probably support more than two factions
					// and just get the closest nexus
					Faction badGuys = factions.get(0);
					if(ent.faction.equals(badGuys)) {
						badGuys = factions.get(1);
					}
					// If there's no target, set it to be the enemy boss
					if(null == ent.entity.getTarget() || ent.entity.getTarget().equals(ent.faction.nexus.boss)) {
						ent.entity.setTarget(badGuys.nexus.boss);
					}
					// Now check to see if there's a closer enemy faction unit
					LivingEntity closest = ent.entity.getTarget();
					double distanceToClosest = closest.getLocation().distance(ent.entity.getLocation());
					for(SpookCraftEntity other : gameMobs) {
						if(other.entity.isDead()) {
							continue; // don't try to attack dead things
						}
						if(other.faction.equals(ent.faction)) {
							continue; // don't attack same faction or self
						}
						double distanceToOther = other.entity.getLocation().distance(other.entity.getLocation());
						if(distanceToOther < distanceToClosest) {
							distanceToClosest = distanceToOther;
							closest = other.entity;
						}
					}
					if(closest != ent.entity.getTarget()) {
						ent.entity.setTarget(closest);
					}
				}
			}
		};
		orderMonstersTask.runTaskTimer(this, 120, 40);
		
    }

	Set<Entity> entities = new HashSet<>();
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
    
    public static class SpookCraftEntity {
    	Creature entity;
    	Faction faction;
    	public SpookCraftEntity(Creature entity, Faction faction) {
    		this.entity = entity;
    		this.faction = faction;
    	}
    }
}
