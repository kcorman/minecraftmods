package sample;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class SampleListener implements Listener {
	private Set<Chunk> conqueredChunks = new HashSet<>();
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
    	// check if player is leaving current chunk
    	Chunk newChunk = e.getTo().getChunk();
    	Chunk oldChunk = e.getFrom().getChunk();
    	if(!e.getTo().getChunk().equals(e.getFrom().getChunk())) {
    		// we're moving to a different chunk. See if the previous one or the new one is conquered
    		// if neither is, then cancel the event. Otherwise let them pass
    		if(!conqueredChunks.contains(newChunk) && !conqueredChunks.contains(oldChunk)) {
    			e.setCancelled(true);
    		}
    	}
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
    	if(e.getBlock().getType() == Material.TORCH) {
    		conqueredChunks.add(e.getBlock().getChunk());
    	}
    }
}
