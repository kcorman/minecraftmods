package sample;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class SampleListener implements Listener {
	private Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
    	// player move event is fired off every time a player moves at all, which
    	// is VERY frequently
    	// So we want to only take any action when we've detected that the player
    	// has actually moved onto a different block
    	PlayerInfo customInfo = playerInfoMap.get(e.getPlayer().getName());
    	if(customInfo == null) {
    		// If this is the first time the player has moved, we need to create a new
    		// entry in the map for them
    		customInfo = new PlayerInfo();
    		// initialize prevBlock as the block the player is standing on
    		customInfo.prevBlock = e.getPlayer().getLocation().getBlock();
    		playerInfoMap.put(e.getPlayer().getName(), customInfo);
    	}
    	// Player moved. Let's check if they are on a different block than the previous one
    	Block currentBlock = e.getPlayer().getLocation().getBlock();
    	if(!currentBlock.getLocation().equals(customInfo.prevBlock.getLocation())) {
    		// players block has changed
    		blockChanged(e.getPlayer(), currentBlock, customInfo.prevBlock);
    	} /* else the block hasn't changed */
    }
    
    private void blockChanged(Player player, Block oldBlock, Block newBlock) {
    	// oldBlock.setType(Material.ICE); If we wanted to change the block at the player's feet, this would work
    	// but we actually want to change the one they are standing on
    	Location underPlayer = oldBlock.getLocation().add(0, -1, 0);
    	underPlayer.getBlock().setType(Material.ICE);
    }
}
