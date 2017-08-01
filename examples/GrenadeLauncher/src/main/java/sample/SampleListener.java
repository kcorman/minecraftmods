package sample;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class SampleListener implements Listener {
	private Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
	
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
    	// if this was an arrow, we want to make an explosion where it landed
    	if(e.getEntity().getType().equals(EntityType.ARROW)) {
    		Location landed = null;
    		if(e.getHitBlock() != null) {
    			landed = e.getHitBlock().getLocation();
    		} else if (e.getHitEntity() != null) {
    			landed = e.getHitEntity().getLocation();
    		} else {
    		    Bukkit.broadcastMessage("That's weird! nothing seems to have been hit");
    		    return;
    		}
    		e.getEntity().getWorld().createExplosion(landed, 3);
    	}
    }
}
