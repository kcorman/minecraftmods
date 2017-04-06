package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class MyListener implements Listener{
	private int total = 0;
	private Map<Location, Material> oldMaterials = new HashMap<>();
	private Block current = null;
	ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
    	Bukkit.broadcastMessage("Welcome to the server!");
    }
    
    @EventHandler
    public void onPlayerEggThrow(PlayerEggThrowEvent event) {
    	// Bukkit.broadcastMessage("Unleashed entity " + event.getEgg().getName() + ", loc = " + event.getEgg().getLocation());
    	Location loc = event.getEgg().getLocation();
    	Collection<Entity> entities = loc.getWorld().getNearbyEntities(loc, 50, 50, 50);
    	Optional<Entity> zombie = entities.stream().filter(e -> e.getType() == EntityType.ZOMBIE && !e.isDead() && e.).findFirst();
    	Optional<Entity> enderman = entities.stream().filter(e -> e.getType() == EntityType.ENDERMAN).findFirst();
    	if(zombie.isPresent() && enderman.isPresent()) {
        	Bukkit.broadcastMessage("Zombie = " + zombie.get() + ", enderman = " + enderman.get());
        	Zombie z = (Zombie) zombie.get();
        	z.setTarget((Enderman)enderman.get());
    	}
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
    	total = 0;
    	if(Math.random() > .9){
    		event.setCancelled(true);
    		event.getBlock().setType(Material.FIRE);
    		final Location loc = event.getBlock().getLocation();
    		int x = loc.getBlockX(); int y = loc.getBlockY(); int z = loc.getBlockZ();
    		final World world = event.getBlock().getWorld();
    		Bukkit.broadcastMessage("World = " + world);
    		BukkitRunnable runnable = new BukkitRunnable() {
				
				@Override
				public void run() {
	    			Bukkit.broadcastMessage("World = " + world);
	        		world.createExplosion(x, y, z, 4, true, false);
				}
			};
			runnable.runTaskLater(SpookCraftPlugin.plugin, 60);
    	}

    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent pme) {
    	Block b = pme.getFrom().getBlock();
    	if(null != current) {
    		if(!current.equals(b)) {
    			// onBlockChange(b, current);
    		}
    	}
    	current = b;
    }
    
    int radius = 7;
    private void onBlockChange(Block prev, Block current) {
    	// Calculate all new blocks that need to be made into lava
    	int x = prev.getX(); int y = prev.getY(); int z = prev.getZ();
    	List<Location> newBlocks = new ArrayList<>();
    	int halfRad = radius/2;
    	for(int i = -halfRad;i<=halfRad;i++) {
    		for(int k = -halfRad;k<=halfRad;k++) {
    			if(Math.abs(i) <= 1 && Math.abs(k) <= 1) continue;
    			int localX = x + i;
    			int localZ = z + k;
    			Location loc = new Location(prev.getWorld(), localX, y, localZ);
    			newBlocks.add(loc);
    		}
    	}
    	Map<Location, Material> newMats = new HashMap<>();
    	for(Location loc : newBlocks) {
    		if(oldMaterials.containsKey(loc)) {
    			newMats.put(loc, oldMaterials.get(loc));
    			oldMaterials.remove(loc);
    		} else {
    			if(!loc.getBlock().getType().isSolid()) {
        			newMats.put(loc, loc.getBlock().getType());
            		loc.getBlock().setType(Material.FIRE);
    			}
    		}
    	}
    	Iterator<Map.Entry<Location, Material>> itr = oldMaterials.entrySet().iterator();
    	while(itr.hasNext()) {
    		Map.Entry<Location, Material> element = itr.next();
    		element.getKey().getBlock().setType(element.getValue());
    	}
    	oldMaterials = newMats;
    }
    
    @EventHandler
    public void onEntityMove(EntityChangeBlockEvent e) {
    	
    }

}
