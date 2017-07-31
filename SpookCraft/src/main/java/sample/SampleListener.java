package sample;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SampleListener implements Listener {
	
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
    	Bukkit.broadcastMessage("Welcome to the server! " + event.getPlayer().getDisplayName());
    }
}
