package sample;

import org.bukkit.plugin.java.JavaPlugin;

public class SamplePlugin extends JavaPlugin {
	@Override
	public void onEnable() {
		super.onEnable();
		getServer().getPluginManager().registerEvents(new SampleListener(), this);
	}
}
