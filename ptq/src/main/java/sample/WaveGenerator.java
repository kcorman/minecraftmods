package sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Monster;

public class WaveGenerator {
	private final EntitySpawner spawner;
	private final World world;
	private final Collection<Location> spawnLocations;
	
	public WaveGenerator(World world, Collection<Location> location) {
		this.world = world;
		this.spawnLocations = location;
		this.spawner = new EntitySpawner();
	}
	
	public List<ManagedEntity<Monster>> spawnWave(int level, EntityRoster roster, EntityRoster bossRoster) {
		List<ManagedEntity<Monster>> result = new ArrayList<>();
		for(int i = 0;i<20;i++) {
			Class<Monster> entityToSpawn = roster.entityForLevel(level);
			Location loc = spawnLocations.iterator().next(); // TODO should use a random loc
			ManagedEntity<Monster> monster = spawner.spawnEntity(world, loc, entityToSpawn);
			result.add(monster);
		}
		if(level % 5 == 0) {
			Class<Monster> entityToSpawn = bossRoster.entityForLevel(level);
			Location loc = spawnLocations.iterator().next(); // TODO should use a random loc
			ManagedEntity<Monster> monster = spawner.spawnEntity(world, loc, entityToSpawn);
			result.add(monster);
		}
		return result;
	}
}
