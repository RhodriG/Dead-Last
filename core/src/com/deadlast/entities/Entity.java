package com.deadlast.entities;

public abstract class Entity {
	
	String name;
	// Point postition;
	int scoreValue;
	
	public Entity(String name, int scoreValue) {
		this.name = name;
		this.scoreValue = scoreValue;
	}
	
	public abstract void delete();
	

}
