package core;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Villager;

public class Nexus {
	public final Block placeholder;
	public final Villager boss;
	public Nexus(Block placeholder) {
		this.placeholder = placeholder;
		placeholder.setType(Material.BEACON);
		boss = placeholder.getWorld().spawn(placeholder.getLocation().add(0, 1, 0), Villager.class);
	}
}
