package sample;

import org.bukkit.entity.Entity;

public class ManagedEntity<T extends Entity> {
	private T wrapped;
	public ManagedEntity(T entity) {
		this.wrapped = entity;
	}
	
	public T getEntity() {
		return wrapped;
	}
}
