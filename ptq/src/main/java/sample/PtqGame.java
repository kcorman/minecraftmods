package sample;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Villager;

public class PtqGame {
	private ManagedEntity<Villager> queen;
	public void start() {
		Bukkit.getScheduler().callSyncMethod(SamplePlugin.INSTANCE, () -> {
			Bukkit.broadcastMessage("world = " + Bukkit.getWorlds().get(0));
			// set up the queen
			Location spawnLoc = Bukkit.getWorlds().get(0).getSpawnLocation();
			// set a 5 x 5 pad of redstone under the spawn loc
			setTilePad(spawnLoc.add(0, 0, 0), Material.OBSIDIAN, 5, 1, 5);
			Location queenLoc = locAdd(spawnLoc, 40, 0, 0);
			setTilePad(queenLoc, Material.OBSIDIAN, 7, 1, 7);
			EntitySpawner spawner = new EntitySpawner();
			queen = spawner.spawnEntity(queenLoc.getWorld(), queenLoc, Villager.class);
			return null;
		});
	}
	
	private static Location findHighestNonAir(Location origin) {
		while(origin.getBlock().getType() != Material.AIR) {
			origin = origin.
		}
	}
	private static Location locAdd(Location origin, int x, int y, int z) {
		return new Location(origin.getWorld(), origin.getX() + x, origin.getY() + y, origin.getZ() + z);
	}
	private void setTilePad(Location center, Material newMat, int x, int y, int z) {
		for(int dx = 0;dx<x;dx++) {
			for(int dy = 0;dy<y;dy++) {
				for(int dz = 0;dz<z;dz++) {
					Location loc = locAdd(center, (dx - x/2), (dy - y/2), (dz - z/2));
					loc.getBlock().setType(newMat);
					loc.getBlock().getState().update(true);
				}
			}
		}
	}
	
	public void gameOver() {
		Bukkit.getOnlinePlayers().forEach(p -> {
			setTilePad(p.getLocation(), Material.LAVA, 5, 1, 5);
		});
	}
	public ManagedEntity<Villager> getQueen() {
		return queen;
	}
}
