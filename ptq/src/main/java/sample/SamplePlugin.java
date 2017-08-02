package sample;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SamplePlugin extends JavaPlugin {

	public static Plugin INSTANCE;
	@Override
	public void onEnable() {
		INSTANCE = this;
		super.onEnable();
		Bukkit.broadcastMessage("Enabling SamplePlugin PTQ");
		PtqGame game = new PtqGame();
		getServer().getPluginManager().registerEvents(new PtqEventListener(game), this);

		game.start();
	}
}
