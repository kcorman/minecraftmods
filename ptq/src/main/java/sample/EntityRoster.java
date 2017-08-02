package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import org.bukkit.entity.Monster;

public class EntityRoster {
	private NavigableMap<Integer, List<Class<Monster>>> monsterMap = new TreeMap<>();
	private Random rand = new Random();
	public Class<Monster> entityForLevel(int level) {
		// TODO: Add some interesting randomness into this
		List<Class<Monster>> monstersForLevel = monsterMap.floorEntry(level).getValue();
		if(null == monstersForLevel) monstersForLevel = monsterMap.lastEntry().getValue();
		int idx = rand.nextInt(monstersForLevel.size());
		return monstersForLevel.get(idx);
	}

	public void registerEntity(int level, Class<Monster> entityClass) {
		List<Class<Monster>> existing = monsterMap.computeIfAbsent(level, l -> new ArrayList<Class<Monster>>());
		existing.add(entityClass);
	}
}
