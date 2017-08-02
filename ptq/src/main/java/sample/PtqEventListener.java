package sample;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class PtqEventListener implements Listener {
	private final PtqGame ptqGame;

	public PtqEventListener(PtqGame ptqGame) {
		super();
		this.ptqGame = ptqGame;
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if(e.getEntity().getEntityId() == ptqGame.getQueen().getEntity().getEntityId()) {
			Bukkit.broadcastMessage("The queen is DEAD! O no.");
			ptqGame.gameOver();
		}
	}
}
