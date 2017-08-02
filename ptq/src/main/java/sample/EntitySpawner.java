package sample;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class EntitySpawner {
	public <T extends Entity> ManagedEntity<T> spawnEntity(World world, Location loc, Class<T> clazz) {
		ManagedEntity<T> result = new ManagedEntity<>(world.spawn(loc, clazz));
		return result;
	}
}
