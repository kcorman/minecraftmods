package core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Enderman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class MyListener implements Listener{
	private int total = 0;
	private Block current = null;
	ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
    	Bukkit.broadcastMessage("Welcome to the server!");
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
	        		world.createExplosion(x, y, z, 10, true, true);
				}
			};
			runnable.runTaskLater(SpookCraftPlugin.plugin, 60);
    	}

    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent pme) {
    	/*Block b = pme.getFrom().getBlock();
    	if(null != current) {
    		if(!current.equals(b)) {
    			new Location(current.getWorld(), current.getX(), current.getY() - 1, current.getZ()).getBlock().setType(Material.GLOWSTONE);
    		}
    	}
    	current = b;*/
    }
    
    @EventHandler
    public void onEntityMove(EntityChangeBlockEvent e) {
    	
    }

}
