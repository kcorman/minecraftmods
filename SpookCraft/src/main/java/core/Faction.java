package core;

import org.bukkit.World;

public class Faction {
	int monstersToSpawn = 5;
	public Nexus nexus;
	public Class warriorClass;
	public Faction(Nexus nexus, Class warriorClass) {
		this.nexus = nexus;
		this.warriorClass = warriorClass;
	}
	
	public World getWorld() {
		return nexus.placeholder.getWorld();
	}
}
