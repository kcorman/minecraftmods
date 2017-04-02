package core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Enderman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class MyListener implements Listener{
	ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
    	Bukkit.broadcastMessage("Welcome to the server!");
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
    	if(Math.random() > .5) {
    		Location oldLoc = event.getBlock().getState().getLocation();
    		Location newLoc = oldLoc.add(0, 4, 0);
    		event.getPlayer().teleport(event.getPlayer().getLocation().add(0, 4, 0));
    		// newLoc.getBlock().setType(Material.GRAVEL);
    		event.setCancelled(true);
    		for(int i = 0;i<4;i++) {
    			Enderman em = event.getPlayer().getWorld().spawn(newLoc.add(0, i, i), Enderman.class);
        		exec.schedule(() -> {
        			em.damage(em.getHealth());
        		}, 3, TimeUnit.SECONDS);
    		}
    		
    		
    	}
    }
}
