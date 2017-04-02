package core;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class MyListener implements Listener{
	
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
    	Bukkit.broadcastMessage("Welcome to the server!");
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
    	if(Math.random() > .5) {
    		event.setCancelled(true);
    	}
    }
}
